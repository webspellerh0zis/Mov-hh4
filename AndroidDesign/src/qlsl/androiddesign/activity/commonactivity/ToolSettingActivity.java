package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ToolSettingView;

public class ToolSettingActivity extends BaseActivity {

	private ToolSettingView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ToolSettingView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
