package qlsl.androiddesign.library.citypicker;

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

/**
 * 城市选择<br/>
 * 
 */
@SuppressLint("ViewConstructor")
public class CityPopupWindow extends PopupWindow implements OnClickListener {

	private CityPicker cityPicker;

	private OnCitySelectListener selectListener;

	public CityPopupWindow(BaseActivity activity,
			OnCitySelectListener selectListener) {
		super(activity);
		this.selectListener = selectListener;
		initView();
		setPopupWindowAttribute();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_city_picker, null);
		cityPicker = (CityPicker) rootView.findViewById(R.id.citypicker);
		setContentView(rootView);
		View btn_ok = rootView.findViewById(R.id.btn_ok);
		View btn_cancel = rootView.findViewById(R.id.btn_cancel);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.citypopwindow_anim_style);
		ColorDrawable colorDrawable = new ColorDrawable(activity.getResources()
				.getColor(R.color.translucence));
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
	}

	public void onClick(View view) {
		dismiss();
		switch (view.getId()) {
		case R.id.btn_ok:
			performOnSelect();
			break;

		}
	}

	private void performOnSelect() {
		if (selectListener != null) {
			String city = cityPicker.getCity_string();
			selectListener.onCitySelect(city);
		}
	}

	/**
	 * 选择监听
	 */
	public interface OnCitySelectListener {

		public void onCitySelect(String city);

	}

}
