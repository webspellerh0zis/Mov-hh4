package qlsl.androiddesign.popupwindow.subwindow;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.listener.OnTextChangeListener;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;
import qlsl.androiddesign.util.commonutil.ColorUtils;
import qlsl.androiddesign.util.commonutil.DensityUtils;

/**
 * 控件调试PopupWindow<br/>
 * 暂时只兼容TextView，Button，EditText，ImageView<br/>
 */
@SuppressLint("ViewConstructor")
public class WidgetPopupWindow extends PopupWindow implements OnClickListener {

	private View view;

	private EditText et_text_color, et_hint_color, et_bg_color;

	private TextView tv_size;

	public WidgetPopupWindow(BaseActivity activity, View view) {
		super(activity);
		this.view = view;
		initView();
		setPopupWindowAttribute();
		initData();

	}

	@SuppressLint("InflateParams")
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = null;
		if (view instanceof Button) {
			rootView = inflater.inflate(R.layout.window_widget_button, null);
			et_text_color = (EditText) rootView.findViewById(R.id.et_text_color);
			et_bg_color = (EditText) rootView.findViewById(R.id.et_bg_color);
			setCommonTextWidgetData(rootView);
		} else if (view instanceof EditText) {
			rootView = inflater.inflate(R.layout.window_widget_edittext, null);
			et_text_color = (EditText) rootView.findViewById(R.id.et_text_color);
			et_hint_color = (EditText) rootView.findViewById(R.id.et_hint_color);
			et_bg_color = (EditText) rootView.findViewById(R.id.et_bg_color);
			setCommonTextWidgetData(rootView);
		} else if (view instanceof TextView) {
			rootView = inflater.inflate(R.layout.window_widget_textview, null);
			et_text_color = (EditText) rootView.findViewById(R.id.et_text_color);
			setCommonTextWidgetData(rootView);
		} else if (view instanceof ImageView) {
			rootView = inflater.inflate(R.layout.window_widget_imageview, null);
		}
		setContentView(rootView);
		((TextView) rootView.findViewById(R.id.tv_right)).setOnClickListener(this);
		initCommonListener();
	}

	private void initCommonListener() {
		if (et_text_color != null) {
			et_text_color.addTextChangedListener(onTextColorChangeListener);
		}
		if (et_hint_color != null) {
			et_hint_color.addTextChangedListener(onHintColorChangeListener);
		}
		if (et_bg_color != null) {
			et_bg_color.addTextChangedListener(onBgColorChangeListener);
		}
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimTop);
		ColorDrawable colorDrawable = new ColorDrawable(activity.getResources().getColor(R.color.window_default));
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	private void initData() {

	}

	private void setCommonTextWidgetData(View rootView) {
		TextView tv_convert = (TextView) view;
		((TextView) rootView.findViewById(R.id.tv_title)).setText(tv_convert.getText());
		int spSize = Math.round(DensityUtils.px2sp(activity, tv_convert.getTextSize()));
		tv_size = ((TextView) rootView.findViewById(R.id.tv_size));
		tv_size.setText(spSize + "");
		tv_size.setTextSize(spSize);

		String textColor = ColorUtils.toHexString(tv_convert.getCurrentTextColor());
		et_text_color.setText(textColor);
		if (et_hint_color != null) {
			String hintColor = ColorUtils.toHexString(tv_convert.getCurrentHintTextColor());
			et_hint_color.setText(hintColor);
		}
		if (et_bg_color != null) {
			if (tv_convert.getBackground() instanceof ColorDrawable) {
				ColorDrawable cd = (ColorDrawable) tv_convert.getBackground();
				String bgColor = ColorUtils.toHexString(cd.getColor());
				et_bg_color.setText(bgColor);
			} else {
				((View) et_bg_color.getParent()).setVisibility(View.GONE);
			}

		}

		rootView.findViewById(R.id.btn_minus).setOnClickListener(this);
		rootView.findViewById(R.id.btn_plus).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_right:
			activity.showToast("正在开发中");
			break;
		case R.id.btn_minus:
			doClickMinusButton();
			break;
		case R.id.btn_plus:
			doClickPlusButton();
			break;
		}
	}

	private void doClickMinusButton() {
		int size = Integer.parseInt(tv_size.getText().toString());
		if (size > 14) {
			size -= 1;
			tv_size.setText(size + "");
			tv_size.setTextSize(size);
			((TextView) view).setTextSize(size);
		}
	}

	private void doClickPlusButton() {
		int size = Integer.parseInt(tv_size.getText().toString());
		if (size < 20) {
			size += 1;
			tv_size.setText(size + "");
			tv_size.setTextSize(size);
			((TextView) view).setTextSize(size);
		}
	}

	private OnTextChangeListener onTextColorChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(android.text.Editable s) {
			int color = ColorUtils.parseColor(s.toString());
			if (color != -1) {
				((TextView) view).setTextColor(color);
			}
		};

	};

	private OnTextChangeListener onHintColorChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(android.text.Editable s) {
			int color = ColorUtils.parseColor(s.toString());
			if (color != -1) {
				((TextView) view).setHintTextColor(color);
			}
		};

	};

	private OnTextChangeListener onBgColorChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(android.text.Editable s) {
			int color = ColorUtils.parseColor(s.toString());
			if (color != -1) {
				((TextView) view).setBackgroundColor(color);
			}
		};

	};
}
