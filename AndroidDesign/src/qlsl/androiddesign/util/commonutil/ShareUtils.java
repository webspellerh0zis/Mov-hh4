package qlsl.androiddesign.util.commonutil;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.graphics.Bitmap;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.KeyConstant;

public class ShareUtils {

	/**
	 * 设置非信息分享内容<br/>
	 */
	private static void setShareContent(BaseActivity activity, String title, String content, Bitmap bitmap) {
		UMSocialService service = SoftwareApplication.getInstance().getUMShareService();
		addWXPlatform(activity);
		addQQQZonePlatform(activity);

		UMImage image = new UMImage(activity, bitmap);

		WeiXinShareContent content1 = new WeiXinShareContent();
		content1.setShareContent(content);
		content1.setTitle(title);
		content1.setShareMedia(image);

		CircleShareContent content2 = new CircleShareContent();
		content2.setShareContent(content);
		content2.setTitle(title);
		content2.setShareMedia(image);

		QZoneShareContent content3 = new QZoneShareContent();
		content3.setShareContent(content);
		content3.setTitle(title);
		content3.setShareMedia(image);

		QQShareContent content4 = new QQShareContent();
		content4.setShareContent(content);
		content4.setTitle(title);
		content4.setShareMedia(image);

		TencentWbShareContent content5 = new TencentWbShareContent();
		content5.setShareContent(content);
		content5.setTitle(title);
		content5.setShareMedia(image);

		SinaShareContent content6 = new SinaShareContent();
		content6.setShareContent(content);
		content6.setTitle(title);
		content6.setShareMedia(image);

		service.setShareMedia(content1);
		service.setShareMedia(content2);
		service.setShareMedia(content3);
		service.setShareMedia(content4);
		service.setShareMedia(content5);
		service.setShareMedia(content6);

	}

	/**
	 * 非信息分享
	 */
	public static void openShare(BaseActivity activity, String title, String content, Bitmap bitmap) {
		setShareContent(activity, title, content, bitmap);
		SoftwareApplication.getInstance().getUMShareService().openShare(activity, new OnSnsPostListener(activity));
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

	/**
	 * 回调时机，次数不稳定，待修改<br/>
	 * 如，回调腾讯微博时才会连之前的微信回调一起调用<br/>
	 * 
	 */
	private static class OnSnsPostListener implements SnsPostListener {

		private BaseActivity activity;

		private OnSnsPostListener(BaseActivity activity) {
			this.activity = activity;
		}

		public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
			if (eCode == StatusCode.ST_CODE_SUCCESSED) {

			}
		}

		public void onStart() {

		}

	}

}
