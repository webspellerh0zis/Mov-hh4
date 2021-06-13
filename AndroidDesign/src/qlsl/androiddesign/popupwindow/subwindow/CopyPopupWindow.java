package qlsl.androiddesign.popupwindow.subwindow;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;

/**
 * **PopupWindow<br/>
 */
@SuppressLint("ViewConstructor")
public class CopyPopupWindow extends PopupWindow {

	public CopyPopupWindow(BaseActivity activity) {
		super(activity);
		initView();
		setPopupWindowAttribute();
		initData();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_copy, null);
		setContentView(rootView);
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimTop);
		ColorDrawable colorDrawable = new ColorDrawable(activity.getResources().getColor(R.color.window_default));
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
	}

	private void initData() {

	}

}
