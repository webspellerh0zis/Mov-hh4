package qlsl.androiddesign.popupwindow.subwindow;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;

@SuppressLint("ViewConstructor")
public class SelectPhotoPopupWindow extends PopupWindow implements
		OnClickListener {

	private View.OnClickListener onClickListener;

	public SelectPhotoPopupWindow(BaseActivity activity,
			View.OnClickListener onClickListener) {
		super(activity);
		initView(onClickListener);
		setPopupWindowAttribute();
	}

	private void initView(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_select_photo, null);
		setContentView(rootView);
		View btn_take_photo = rootView.findViewById(R.id.btn_take_photo);
		View btn_pick_photo = rootView.findViewById(R.id.btn_pick_photo);
		View btn_look = rootView.findViewById(R.id.btn_look);
		View btn_cancel = rootView.findViewById(R.id.btn_cancel);
		btn_take_photo.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_look.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimBottom);
		ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
	}

	public void onClick(View view) {
		dismiss();
		onClickListener.onClick(view);
	}

	public void hideLookButton() {
		getContentView().findViewById(R.id.btn_look).setVisibility(View.GONE);
	}

}
