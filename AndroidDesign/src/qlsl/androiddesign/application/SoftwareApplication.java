package qlsl.androiddesign.application;

import static qlsl.androiddesign.util.commonutil.Log.TAG;
import static qlsl.androiddesign.util.commonutil.Log.isDebug;
import static qlsl.androiddesign.util.commonutil.Log.isOutPhone;

import org.apache.log4j.Level;
import org.opencv.android.OpenCVLoader;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import qlsl.androiddesign.constant.AppConstant;
import qlsl.androiddesign.constant.BaseConstant;
import qlsl.androiddesign.constant.UrlConstant;
import qlsl.androiddesign.handler.subhandler.CrashHandler;
import qlsl.androiddesign.util.commonutil.AppUtils;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.util.commonutil.MapUtils;

public class SoftwareApplication extends Application {

	/**
	 * 单例，耗资源
	 */
	private static SoftwareApplication application;

	/**
	 * 分享
	 */
	private UMSocialService shareService, loginService;

	/**
	 * 传值，为短生命周期的一次性变量<br/>
	 * 生命周期在[传递，接收]之间，接收后销毁(NULL)<br/>
	 * 长生命周期的共享变量请另行定义，本变量不用作共享功能<br/>
	 * 用处：从较远的activity跨级返回到某个activity时用<br/>
	 * 使用自定义栈中的跨级传值效果相同，但不需销毁<br/>
	 */
	private Bundle bundle;

	public UMSocialService getUMShareService() {
		return shareService;
	}

	public UMSocialService getUMLoginService() {
		return loginService;
	}

	public void onCreate() {
		super.onCreate();
		initInstance();
		initCrashHandler();
		initLog4j();
		outputApplicationInfo();
		initConstant();
		initSDK();
		initMap();
	}

	/**
	 * 一次性传值<br/>
	 */
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * 接收一次性传值<br/>
	 */
	public Bundle getBundle() {
		return bundle;
	}

	private void initInstance() {
		application = this;
	}

	public static SoftwareApplication getInstance() {
		return application;
	}

	private void outputApplicationInfo() {
		String runModel = isDebug ? "调试模式" : "发布模式";
		String outputModel = isOutPhone ? "手机模式" : "控制台模式";
		String outputDefaultTag = TAG;
		Log.i("正在启动应用：" + AppUtils.getAppName(this) + "<br/>应用程序包名：" + getPackageName() + "<br/>" + getVersionInfo()
				+ "<br/>程序运行模式：" + runModel + "<br/>日志输出模式：" + outputModel + "<br/>默认输出标签：" + outputDefaultTag);
		Log.i("onCreate：application：<br/>" + getClass().getName());
		String path = Log.getLogPath(this);
		Log.i("Log", "initLog：application：<br/>url：" + path);

	}
	
	/**
	 * 初始化错误收集器
	 */
	private void initCrashHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		crashHandler.sendPreviousReportsToServer();
	}

	private void initLog4j() {
		String path = Log.getLogPath(this);
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(path);
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();

		Log.notifyDataSetChanged(this);

	}

	private void initConstant() {
		BaseConstant.init(AppConstant.APP_ID, AppConstant.APP_CODE, UrlConstant.RES_URL);
		BaseConstant.initHttp(UrlConstant.BASE_URL, true);

	}

	private void initSDK() {
		initUmengConfig();// 友盟
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=560e3306"); // 讯飞语音,APPID需要替换
		OpenCVLoader.initDebug();// openCV
	}

	private void initUmengConfig() {
		SocializeConstants.SHOW_ERROR_CODE = true;
		shareService = UMServiceFactory.getUMSocialService("com.umeng.share");
		// shareService.getConfig().setSsoHandler(new SinaSsoHandler());
		// shareService.getConfig().setSsoHandler(new TencentWBSsoHandler());
		shareService.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);

		// 已经有免登陆机制，不再提供免授权功能，注销时重新授权
		loginService = UMServiceFactory.getUMSocialService("com.umeng.login");
		// loginService.getConfig().setSsoHandler(new SinaSsoHandler());
		// loginService.getConfig().setSsoHandler(new TencentWBSsoHandler());
		shareService.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
	}

	private void initMap() {
		MapUtils.init(this);
	}

	private String getVersionInfo() {
		String versionInfo = "当前版本信息不详";
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			versionInfo = "应用版本信息：" + "versionName=" + versionName + "      versionCode=" + versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionInfo;
	}

	public void onTerminate() {
		super.onTerminate();
		outputTerminateInfo();
	}

	public void onLowMemory() {
		super.onLowMemory();
		outputLowMemoryInfo();
	}

	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		outputTrimMemoryInfo(level);
	}

	private void outputTerminateInfo() {
		Log.d("onTerminate：application：<br/>" + getClass().getName());
	}

	private void outputLowMemoryInfo() {
		Log.d("onLowMemory：application：<br/>" + getClass().getName());
	}

	private void outputTrimMemoryInfo(int level) {
		Log.d("onTrimMemory[" + level + "]：application：<br/>" + getClass().getName());
	}

}