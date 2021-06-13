package qlsl.androiddesign.fragment.commonfragment;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.TabView4;

public class FragmentTab4 extends TabFragment {

	private TabView4 functionView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (functionView == null) {
			ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.tab4, null);
			functionView = new TabView4(this, onCreateView(inflater), contentView);
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
