package qlsl.androiddesign.fragment.commonfragment;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MenuRightView;

public class MenuRightFragment extends BaseFragment {

	private MenuRightView functionView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (functionView == null) {
			ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_right, null);
			functionView = new MenuRightView(this, onCreateView(inflater), contentView);
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

}
