package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ProcessDetailView;

public class ProcessDetailActivity extends BaseActivity {

	private ProcessDetailView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ProcessDetailView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
