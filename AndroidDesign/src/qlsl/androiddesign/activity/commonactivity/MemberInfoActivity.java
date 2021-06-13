package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemberInfoView;

public class MemberInfoActivity extends BaseActivity {

	private MemberInfoView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemberInfoView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object values) {
		super.onNetWorkSucceed(method, values);
		if (method.equals("updateHeadPhoto")) {
			functionView.setListViewData();
		}
	}

}
