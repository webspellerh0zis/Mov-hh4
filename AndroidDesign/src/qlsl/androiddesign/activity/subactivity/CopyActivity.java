package qlsl.androiddesign.activity.subactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.activityview.CopyView;

public class CopyActivity extends BaseActivity {

	private CopyView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new CopyView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
