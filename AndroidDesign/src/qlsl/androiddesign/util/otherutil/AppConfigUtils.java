package qlsl.androiddesign.util.otherutil;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import qlsl.androiddesign.application.SoftwareApplication;

public class AppConfigUtils {

	/**
	 * 网络是否连接
	 * 
	 * @return 是否连接
	 */
	public boolean isConnected() {
		return Utils.isConnected(SoftwareApplication.getInstance());
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return 安装包信息
	 */
	public static PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			SoftwareApplication application = SoftwareApplication.getInstance();
			info = application.getPackageManager().getPackageInfo(
					application.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 获取App唯一标识
	 * 
	 * @return 唯一标示
	 */
	public static String getAppId() {
		String uniqueID = getProperty(Constants.APP_UUID);
		if (Utils.isEmpty(uniqueID)) {
			uniqueID = Utils.gainUUID();
			setProperty(Constants.APP_UUID, uniqueID);
		}
		return uniqueID;
	}

	public String getPath(String path) {
		return AppConfig.BASE_PATH + path;
	}

	/**
	 * 设置键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setProperty(String key, String value) {
		AppConfig.getInstance(SoftwareApplication.getInstance())
				.set(key, value);
	}

	/**
	 * 获取键
	 * 
	 * @param key
	 *            键名
	 * @return 值
	 */
	public static String getProperty(String key) {
		return AppConfig.getInstance(SoftwareApplication.getInstance())
				.get(key);
	}
}
