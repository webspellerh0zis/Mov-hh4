package qlsl.androiddesign.popupwindow.basewindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.util.commonutil.Log;

@SuppressLint("ViewConstructor")
public class PopupWindow extends android.widget.PopupWindow {

	protected BaseActivity activity;

	public PopupWindow(BaseActivity activity) {
		this.activity = activity;
		outputCreatePopupWindowInfo();
	}

	private void outputCreatePopupWindowInfo() {
		Log.i("onCreate：PopupWindow：<br/>" + getClass().getName());
	}

	/**
	 * 显示在控件外部下面<br/>
	 */
	public void showAsDropDown(View anchor) {
		hideSoftInput();
		if (isShowing()) {
			dismiss();
		} else {
			super.showAsDropDown(anchor);
		}
	}
	
	/**
	 * 显示在parent对应的window根布局内部中间<br/>
	 */
	public void showAsCenter(View parent) {
		hideSoftInput();
		if (isShowing()) {
			dismiss();
		} else {
			showAtLocation(parent, Gravity.CENTER, 0, 0);
		}
	}

	/**
	 * 显示在parent对应的window根布局内部下面<br/>
	 */
	public void showAsBottom(View parent) {
		hideSoftInput();
		if (isShowing()) {
			dismiss();
		} else {
			showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		}
	}

	/**
	 * 显示在parent的正上方<br/>
	 * 限制：parent必须是布局的最下方的控件<br/>
	 */
	public void showAsTop(View parent) {
		hideSoftInput();
		if (isShowing()) {
			dismiss();
		} else {
			showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, parent.getHeight());
		}
	}

	/**
	 * 隐藏输入法<br/>
	 */
	private void hideSoftInput() {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}

}
