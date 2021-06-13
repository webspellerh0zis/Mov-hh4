package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.popupwindow.subwindow.WidgetPopupWindow;
import qlsl.androiddesign.util.commonutil.Log;

/**
 * 提供控件调试功能的ImageView<br/>
 * 暂未使用<br/>
 *
 */
public class ImageView extends android.widget.ImageView implements OnLongClickListener {

	public ImageView(Context context) {
		super(context);
		init();
	}

	public ImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if (Log.isDebug) {
			setOnLongClickListener(this);
		}
	}

	public boolean onLongClick(View v) {
		BaseActivity activity = (BaseActivity) getContext();
		if (activity != null) {
			WidgetPopupWindow widgetWindow = new WidgetPopupWindow(activity, this);
			widgetWindow.showAsDropDown(v);
		}
		return false;
	}
}
