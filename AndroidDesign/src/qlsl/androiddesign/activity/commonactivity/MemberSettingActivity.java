package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.entity.commonentity.UpdateInfo;
import qlsl.androiddesign.manager.VersionUpdateManager;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.MemberSettingView;

public class MemberSettingActivity extends BaseActivity {

	private MemberSettingView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new MemberSettingView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object object) {
		functionView.hideProgressBar();
		if (method.equals("getUpdateInfo")) {
			UpdateInfo updateInfo = (UpdateInfo) object;
			VersionUpdateManager manager = new VersionUpdateManager(functionView.activity);
			if (!manager.checkUpdate(updateInfo)) {
				functionView.showToast("您安装的已经是最新版本");
			}
		}
	}

}
