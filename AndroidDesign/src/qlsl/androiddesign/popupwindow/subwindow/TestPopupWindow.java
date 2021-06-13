package qlsl.androiddesign.popupwindow.subwindow;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;

/**
 * 测试PopupWindow<br/>
 */
@SuppressLint("ViewConstructor")
public class TestPopupWindow extends PopupWindow {

	private TextView tv_test;

	public TestPopupWindow(BaseActivity activity, TextView tv_test) {
		super(activity);
		this.tv_test = tv_test;
		initView();
		setPopupWindowAttribute();
		initData();

	}

	@SuppressLint("InflateParams")
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_test, null);
		setContentView(rootView);
		rootView.findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				activity.showToast("改变颜色");
				tv_test.setTextColor(activity.getResources().getColor(R.color.blue));
			}
		});
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
