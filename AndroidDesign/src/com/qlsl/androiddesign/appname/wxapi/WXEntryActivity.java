package com.qlsl.androiddesign.appname.wxapi;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;
import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.constant.KeyConstant;
import qlsl.androiddesign.view.baseview.FunctionView;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, KeyConstant.APP_ID_WX, false);
		api.handleIntent(getIntent(), this);
	}

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	/**
	 * 微信发送请求到第三方应用时，会回调到该方法
	 */
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			showToast("COMMAND_GETMESSAGE_FROM_WX");
			goToGetMsg();
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			showToast("COMMAND_SHOWMESSAGE_FROM_WX");
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		}
	}

	/**
	 * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	 */
	public void onResp(BaseResp resp) {
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "取消分享";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "授权拒绝";
			break;
		}
		showToast(result);
		finish();
	}

	private void goToGetMsg() {
		// Intent intent = new Intent(this, GetFromWXActivity.class);
		// intent.putExtras(getIntent());
		// startActivity(intent);
		// finish();
	}

	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		// WXMediaMessage wxMsg = showReq.message;
		// WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		//
		// StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
		// msg.append("description: ");
		// msg.append(wxMsg.description);
		// msg.append("\n");
		// msg.append("extInfo: ");
		// msg.append(obj.extInfo);
		// msg.append("\n");
		// msg.append("filePath: ");
		// msg.append(obj.filePath);
		//
		// Intent intent = new Intent(this, ShowFromWXActivity.class);
		// intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		// intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		// intent.putExtra(Constants.ShowMsgActivity.BAThumbData,
		// wxMsg.thumbData);
		// startActivity(intent);
		// finish();
	}

	public FunctionView<?> getFunctionView() {
		return null;
	}
}