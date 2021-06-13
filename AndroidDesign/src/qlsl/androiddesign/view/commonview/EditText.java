package qlsl.androiddesign.view.commonview;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.popupwindow.subwindow.WidgetPopupWindow;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;
import qlsl.androiddesign.view.rippleview.Typefaces;

/**
 * 点击弹出输入法，点击关闭输入法<br/>
 * 艺术字体EditText<br/>
 * 开启点击事件<br/>
 * 清除按钮<br/>
 */
public class EditText extends android.widget.EditText implements OnLongClickListener {

	private Drawable mRightDrawable;

	private int inputMethodType;

	public EditText(Context context) {
		super(context);
		init();
	}

	public EditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		setClickable(true);
	}

	public void setTypeface(Typeface tf) {
		ToolColor toolColor = ToolColorUtils.get();
		if (toolColor == null) {
			super.setTypeface(Typefaces.get(getContext()));
		} else {
			if (toolColor.getIsOpenArt()) {
				super.setTypeface(Typefaces.get(getContext()));
			}
			// int textColor = Color.parseColor(toolColor.getTextColor());
			// super.setHintTextColor(textColor);
			// super.setTextColor(textColor);
			// super.setTextSize(toolColor.getTextSize());
		}
	}

	public boolean performClick() {
		toggleSoftInput();
		ToolSpeechUtils.startSpeaking(getText().toString(), 1);
		return super.performClick();
	}

	public void setClickable(boolean clickable) {
		super.setClickable(true);
	}

	private void toggleSoftInput() {
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodType == 0) {
			imm.showSoftInput(this, 0);
			setInputMethodType(1);
		} else {
			imm.hideSoftInputFromWindow(getWindowToken(), 0);
			setInputMethodType(0);
		}
	}

	private void setInputMethodType(int inputMethodType) {
		this.inputMethodType = inputMethodType;
	}

	private void init() {
		mRightDrawable = getCompoundDrawables()[2];
		if (mRightDrawable == null) {
			mRightDrawable = getResources().getDrawable(R.drawable.iv_clear);
		}
		setClearDrawableVisible(false);
		setOnFocusChangeListener(new FocusChangeListenerImpl());
		addTextChangedListener(new TextWatcherImpl());
		if (Log.isDebug) {
			setOnLongClickListener(this);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
					&& (event.getX() < (getWidth() - getPaddingRight()));
			if (isClean) {
				setText("");
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private class FocusChangeListenerImpl implements OnFocusChangeListener {

		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				boolean isVisible = getText().toString().length() >= 1;
				setClearDrawableVisible(isVisible);
			} else {
				setClearDrawableVisible(false);
			}
		}

	}

	private class TextWatcherImpl implements TextWatcher {

		public void afterTextChanged(Editable s) {
			boolean isVisible = getText().toString().length() >= 1;
			setClearDrawableVisible(isVisible);
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

	}

	protected void setClearDrawableVisible(boolean isVisible) {
		Drawable rightDrawable;
		if (isVisible) {
			rightDrawable = mRightDrawable;
		} else {
			rightDrawable = null;
		}
		setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable,
				getCompoundDrawables()[3]);
	}

	public void setShakeAnimation() {
		setAnimation(shakeAnimation(5));
	}

	public Animation shakeAnimation(int CycleTimes) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
		translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

	public boolean onLongClick(View v) {
		BaseActivity activity = (BaseActivity) getContext();
		if (activity != null) {
			WidgetPopupWindow widgetWindow = new WidgetPopupWindow(activity, this);
			widgetWindow.showAsCenter(v);
		}
		return false;
	}
}
