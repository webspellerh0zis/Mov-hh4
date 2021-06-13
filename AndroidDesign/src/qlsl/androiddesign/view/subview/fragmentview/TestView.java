package qlsl.androiddesign.view.subview.fragmentview;

import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.commonactivity.TestActivity;
import qlsl.androiddesign.fragment.commonfragment.TestFragment;
import qlsl.androiddesign.view.baseview.FunctionView;

public class TestView extends FunctionView<TestActivity> {

	private TestFragment fragment;

	public TestView(TestFragment fragment, ViewGroup rootView, ViewGroup contentView) {
		super((TestActivity) fragment.getActivity());
		this.fragment = fragment;
		setContentView(rootView, contentView);
	}

	protected void initView(View view) {

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
