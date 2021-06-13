package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemoryView;

public class MemoryActivity extends BaseActivity {

	private MemoryView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemoryView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
