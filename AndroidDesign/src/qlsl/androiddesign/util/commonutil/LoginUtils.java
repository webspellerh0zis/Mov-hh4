package qlsl.androiddesign.util.commonutil;

import java.util.Map;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.KeyConstant;
import qlsl.androiddesign.http.service.commonservice.MemberService;

/**
 * 第三方登陆操作类，基于友盟实现<br/>
 * 由于不存在重复登录情况，故不免授权，每次注销登陆，也不进行免授权<br/>
 */
public class LoginUtils {

	/**
	 * 授权验证<br/>
	 * loginType:[0,1,2]代表[QQ登录,新浪微博登录，微信登录]<br/>
	 * operateType:[0,1]代表[登录，绑定]<br/>
	 */
	private static void doOauthVerify(final BaseActivity activity, final int loginType, final int operateType) {
		UMSocialService loginService = SoftwareApplication.getInstance().getUMLoginService();
		SHARE_MEDIA loginTypeReal = null;
		if (loginType == 0) {
			loginTypeReal = SHARE_MEDIA.QQ;
		} else if (loginType == 1) {
			loginTypeReal = SHARE_MEDIA.SINA;
		}
		loginService.doOauthVerify(activity, loginTypeReal, new UMAuthListener() {

			public void onStart(SHARE_MEDIA arg0) {
				activity.showToast("开始授权");
			}

			public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
				activity.showToast("授权失败，请稍后再试");
			}

			public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
				activity.showToast("授权成功");
				getPlatformInfo(activity, loginType, operateType, arg0.getString("uid"),
						arg0.getString("access_token"));
			}

			public void onCancel(SHARE_MEDIA arg0) {
				activity.showToast("取消授权");
			}
		});
	}

	/**
	 * 获取用户信息<br/>
	 * type:[0,1,2]代表[QQ登录,新浪微博登录，微信登录]<br/>
	 * operateType:[0,1]代表[登录，绑定]<br/>
	 */
	private static void getPlatformInfo(final BaseActivity activity, final int loginType, final int operateType,
			final String uid, final String access_token) {
		UMSocialService loginService = SoftwareApplication.getInstance().getUMLoginService();
		SHARE_MEDIA loginTypeReal = null;
		if (loginType == 0) {
			loginTypeReal = SHARE_MEDIA.QQ;
		} else if (loginType == 1) {
			loginTypeReal = SHARE_MEDIA.SINA;
		}
		loginService.getPlatformInfo(activity, loginTypeReal, new UMDataListener() {

			public void onStart() {
				activity.showToast("正在获取用户信息");
			}

			public void onComplete(int status, Map<String, Object> map) {
				if (status == 200 && map != null) {
					// StringBuilder sb = new StringBuilder();
					// Set<String> keys = map.keySet();
					// for (String key : keys) {
					// sb.append(key + "=" + map.get(key).toString() + "\r\n");
					// }

					String username = (String) map.get("screen_name");
					String profile = (String) map.get("profile_image_url");

					if (operateType == 0) {
						MemberService.loginByPlatform(uid, access_token, 0, username, profile,
								activity.getFunctionView(), activity);
					} else if (operateType == 1) {
						MemberService.bindChildByPlatform(uid, loginType, username, activity.getFunctionView(),
								activity);
					}

					activity.showToast("获取用户信息成功");
				} else {
					activity.showToast("获取用户信息失败");
				}
			}
		});
	}

	/**
	 * 打开授权验证<br/>
	 * loginType:[0,1,2]代表[QQ登录,新浪微博登录，微信登录]<br/>
	 * operateType:[0,1]代表[登录，绑定]<br/>
	 */
	public static void openOauthVerify(final BaseActivity activity, final int loginType, final int operateType) {
		addQQQZonePlatform(activity);
		addWXPlatform(activity);
		doOauthVerify(activity, loginType, operateType);
	}

	/**
	 * 添加微信平台分享<br/>
	 * appId与appSecret需要换掉<br/>
	 * 
	 */
	private static void addWXPlatform(BaseActivity activity) {
		String appId = KeyConstant.APP_ID_WX;
		String appSecret = KeyConstant.APP_SECRET_WX;
		UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
		wxHandler.addToSocialSDK();
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * 添加QQ平台分享<br/>
	 * appId,appKey,targetUrl暂为私用，需要换掉<br/>
	 */
	private static void addQQQZonePlatform(BaseActivity activity) {
		String appId = KeyConstant.APP_ID_QQ;
		String appKey = KeyConstant.APP_KEY_QQ;
		String targetUrl = KeyConstant.TARGET_URL;
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId, appKey);
		qqSsoHandler.setTargetUrl(targetUrl);
		qqSsoHandler.addToSocialSDK();
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}
}
