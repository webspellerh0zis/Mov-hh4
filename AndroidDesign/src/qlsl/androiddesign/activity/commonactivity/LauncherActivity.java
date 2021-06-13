package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.LauncherView;

public class LauncherActivity extends BaseActivity {

	private LauncherView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new LauncherView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
