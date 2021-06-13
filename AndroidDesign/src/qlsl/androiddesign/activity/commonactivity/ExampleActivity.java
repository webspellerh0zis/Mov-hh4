package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ExampleView;

public class ExampleActivity extends BaseActivity {

	private ExampleView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ExampleView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object object) {
		if (method.equals("showNetworkImages")) {
			functionView.showData(object);
		}
	}

	public void onNetWorkFaild(String method, Object object) {
		super.onNetWorkFaild(method, object);
		if (method.equals("showNetworkImages")) {
			functionView.showNoData(object);
		}
	}

}
