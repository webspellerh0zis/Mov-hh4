package qlsl.androiddesign.fragment.commonfragment;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.TabView3;

public class FragmentTab3 extends TabFragment {

	private TabView3 functionView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (functionView == null) {
			ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.tab3, null);
			functionView = new TabView3(this, onCreateView(inflater), contentView);
		}
		ViewGroup parent = (ViewGroup) functionView.view.getParent();
		if (parent != null) {
			parent.removeView(functionView.view);
		}
		return functionView.view;
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onTabResume() {

	}

	public void onTabPause() {

	}
}
