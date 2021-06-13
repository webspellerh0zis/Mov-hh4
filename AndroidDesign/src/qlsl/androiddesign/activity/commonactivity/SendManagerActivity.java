package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.SendManagerView;

public class SendManagerActivity extends BaseActivity {

	private SendManagerView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new SendManagerView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
