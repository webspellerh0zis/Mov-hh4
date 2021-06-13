package qlsl.androiddesign.view.subview.fragmentview;

import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.subactivity.CopyActivity;
import qlsl.androiddesign.fragment.commonfragment.CopyFragment;
import qlsl.androiddesign.view.baseview.FunctionView;

public class CopyView extends FunctionView<CopyActivity> {

	public CopyView(CopyFragment fragment, ViewGroup rootView, ViewGroup contentView) {
		super((CopyActivity) fragment.getActivity());
		setContentView(rootView, contentView);
	}

	protected void initView(View view) {
		hideTitleBar();
	}

	protected void initData() {

	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

}
