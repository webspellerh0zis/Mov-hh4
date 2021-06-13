package qlsl.androiddesign.activity.commonactivity;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemberLoginView;

public class MemberLoginActivity extends BaseActivity {

	private MemberLoginView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemberLoginView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object values) {
		if (method.equals("loginByEmail")) {
			super.onNetWorkSucceed(method, values);
			startToHomePage();
		}
	}

	private void startToHomePage() {
		startActivity(MainActivity.class, R.anim.in_translate_top, R.anim.out_translate_top);
		finish();
	}

}
