package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.AboutView;

public class AboutActivity extends BaseActivity {

	private AboutView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new AboutView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
