package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.TestView;

public class TestActivity extends BaseActivity {

	private TestView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new TestView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
