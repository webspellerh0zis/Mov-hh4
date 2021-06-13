package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemberForgetPasswordView;

public class MemberForgetPasswordActivity extends BaseActivity {

	private MemberForgetPasswordView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemberForgetPasswordView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
