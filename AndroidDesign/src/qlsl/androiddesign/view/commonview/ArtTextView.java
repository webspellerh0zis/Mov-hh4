package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.popupwindow.subwindow.WidgetPopupWindow;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.view.rippleview.Typefaces;

/**
 * 艺术字体TextView<br/>
 * 不自动播放语音<br/>
 */
public class ArtTextView extends android.widget.TextView implements OnLongClickListener {

	public ArtTextView(Context context) {
		super(context);
		init();
	}

	public ArtTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ArtTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if (Log.isDebug) {
			setOnLongClickListener(this);
		}
	}

	public void setTypeface(Typeface tf) {
		ToolColor toolColor = ToolColorUtils.get();
		if (toolColor == null) {
			super.setTypeface(Typefaces.get(getContext()));
		} else {
			if (toolColor.getIsOpenArt()) {
				super.setTypeface(Typefaces.get(getContext()));
			}
			// super.setTextColor(Color.parseColor(toolColor.getTextColor()));
			// super.setTextSize(toolColor.getTextSize());
		}
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
