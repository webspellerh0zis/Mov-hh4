package qlsl.androiddesign.fragment.commonfragment;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.fragmentview.TestView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class TestFragment extends TabFragment {

	private TestView functionView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (functionView == null || true) {
			ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_test, null);
			functionView = new TestView(this, onCreateView(inflater), contentView);
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
