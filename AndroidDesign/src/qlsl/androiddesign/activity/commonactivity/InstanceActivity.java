package qlsl.androiddesign.activity.commonactivity;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.entity.commonentity.UpdateInfo;
import qlsl.androiddesign.http.service.commonservice.MemberService;
import qlsl.androiddesign.http.service.commonservice.ToolSpeechService;
import qlsl.androiddesign.manager.ExitManager;
import qlsl.androiddesign.manager.VersionUpdateManager;
import qlsl.androiddesign.manager.VersionUpdateManager.OnCancelListener;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.service.subservice.LogFloatService;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.InstanceView;

public class InstanceActivity extends BaseActivity implements OnCancelListener {

	private InstanceView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new InstanceView(this);
		functionView.setProgressBarText("正在初始化数据");
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onNetWorkSucceed(String method, Object values) {
		functionView.hideProgressBar();
		if (method.equals("getUpdateInfo")) {
			UpdateInfo updateInfo = (UpdateInfo) values;
			VersionUpdateManager manager = new VersionUpdateManager(this);
			manager.setOnCancelListener(this);
			if (!manager.checkUpdate(updateInfo)) {
				forceLogin();
			}
		} else if (method.equals("getUserInfo")) {
			startToHomePage();
		}
	}

	public void onNetWorkFaild(String method, Object values) {
		super.onNetWorkFaild(method, values);
		functionView.hideProgressBar();
		if ((!MessageConstant.MSG_MEMBER_INVALID.equals(values))
				&& (!MessageConstant.MSG_LOGIN_INVALID.equals(values))) {
			stopService(LogFloatService.class);
			ExitManager.ExitDelayed();
		}
	}

	public void onDbSucceed(String method, Object values) {
		if (method.equals("queryToolColor")) {
			ToolSpeechService.queryToolSpeech(functionView, this);
		} else if (method.equals("queryToolSpeech")) {
			// SystemService.getUpdateInfo(functionView, this);

			forceLogin();
		}
	}

	/**
	 * 强制登陆<br/>
	 */
	private void forceLogin() {
		String token = UserMethod.getTokenFromProperty();
		String platId = UserMethod.getPlatIdFromProperty();
		if (token != null && platId == null) {
			MemberService.getUserInfo(functionView, this);
		} else {
			startActivity(MemberLoginActivity.class);
			finish();
		}
	}

	private void startToHomePage() {
		startActivity(MainActivity.class, R.anim.in_translate_top, R.anim.out_translate_top);
	}

	public void cancel() {
		forceLogin();
	}

}
