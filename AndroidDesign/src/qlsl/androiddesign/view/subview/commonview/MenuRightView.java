package qlsl.androiddesign.view.subview.commonview;

import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.fragment.commonfragment.MenuRightFragment;
import qlsl.androiddesign.view.baseview.FunctionView;

public class MenuRightView extends FunctionView<MainActivity> {

	public MenuRightView(MenuRightFragment fragment, ViewGroup rootView, ViewGroup contentView) {
		super((MainActivity) fragment.getActivity());
		setContentView(rootView, contentView);
	}

	public void initView(View view) {
		hideTitleBar();
	}

	public void initData() {

	}

	public void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

}
