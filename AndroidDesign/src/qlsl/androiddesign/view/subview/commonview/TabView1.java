package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab1;
import qlsl.androiddesign.view.baseview.FunctionView;

public class TabView1 extends FunctionView<MainActivity> {

	public TabView1(FragmentTab1 fragment, ViewGroup rootView, ViewGroup contentView) {
		super((MainActivity) fragment.getActivity());
		setContentView(rootView, contentView);
	}

	protected void initView(View view) {
		setTitle(activity.getResources().getString(R.string.tab1));
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
