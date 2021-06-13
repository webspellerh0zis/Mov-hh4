package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.JsonFormatView;

public class JsonFormatActivity extends BaseActivity {

	private JsonFormatView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new JsonFormatView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object values) {
		if (method.equals("queryJsonData")) {
			functionView.showNoData(values);
		}
	}

}
