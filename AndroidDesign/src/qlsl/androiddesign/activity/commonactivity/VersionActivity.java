package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.VersionView;

public class VersionActivity extends BaseActivity {

	private VersionView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new VersionView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
