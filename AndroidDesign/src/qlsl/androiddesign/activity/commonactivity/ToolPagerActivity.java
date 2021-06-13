package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ToolPagerView;

public class ToolPagerActivity extends BaseActivity {

	private ToolPagerView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ToolPagerView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onDbSucceed(String method, Object values) {
		functionView.hideProgressBar();
		if (method.equals("queryToolPager")) {
			if (values != null) {
				functionView.showData(values);
			}
		} else if (method.equals("updateToolPager")) {
			super.onDbSucceed(method, values);
		} else if (method.equals("resetToolPager")) {
			super.onDbSucceed(method, values);
		}
	}

}
