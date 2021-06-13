package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemberResetPasswordView;

public class MemberResetPasswordActivity extends BaseActivity {

	private MemberResetPasswordView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemberResetPasswordView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}