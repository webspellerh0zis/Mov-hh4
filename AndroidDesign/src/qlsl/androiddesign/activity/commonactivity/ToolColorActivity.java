package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ToolColorView;

public class ToolColorActivity extends BaseActivity {

	private ToolColorView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ToolColorView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onDbSucceed(String method, Object values) {
		functionView.hideProgressBar();
		if (method.equals("queryToolColor")) {
			if (values != null) {
				functionView.showData(values);
			}
		} else if (method.equals("updateToolColor")) {
			super.onDbSucceed(method, values);
		} else if (method.equals("resetToolColor")) {
			super.onDbSucceed(method, values);
		}
	}

}
