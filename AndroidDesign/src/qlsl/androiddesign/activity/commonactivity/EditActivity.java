package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.EditView;

public class EditActivity extends BaseActivity {

	private EditView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new EditView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object values) {
		super.onNetWorkSucceed(method, values);
		functionView.setRefresh(true);
		finish();
	}

}
