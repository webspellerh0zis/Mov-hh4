package qlsl.androiddesign.method;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.util.commonutil.SPUtils;

public class AppMethod {

	/**
	 * 手机信息
	 */
	private static String userAgent;

	/**
	 * 获取手机信息
	 * 
	 */
	public static String getUserAgent() {
		if (TextUtils.isEmpty(userAgent)) {
			StringBuilder ua = new StringBuilder("Dayeasy-EBook");
			PackageInfo appInfo = AppMethod.getPackageInfo();
			if (appInfo != null) {
				ua.append("/").append(appInfo.versionName).append("_")
						.append(appInfo.versionCode);
			}
			ua.append("/android ").append(android.os.Build.VERSION.RELEASE);
			ua.append("/").append(android.os.Build.MODEL);
			ua.append("/").append(AppMethod.getAppId());
			userAgent = ua.toString();
		}
		return userAgent;
	}

	/**
	 * 获取App唯一标识
	 * 
	 */
	public static String getAppId() {
		Object uniqueID = SPUtils.get(SoftwareApplication.getInstance(),
				SPUtils.APP_UUID, null);
		if (uniqueID == null) {
			uniqueID = gainUUID();
			SPUtils.put(SoftwareApplication.getInstance(), SPUtils.APP_UUID,
					uniqueID);
		}
		return uniqueID.toString();
	}

	/**
	 * 获取UUID
	 * 
	 * @return 32UUID小写字符串
	 */
	private static String gainUUID() {
		String strUUID = UUID.randomUUID().toString();
		strUUID = strUUID.replaceAll("-", "").toLowerCase();
		return strUUID;
	}

	/**
	 * 获取版本信息<br/>
	 */
	public static String getVersionInfo() {
		String versionInfo = "当前版本信息不详";
		try {
			SoftwareApplication application = SoftwareApplication.getInstance();
			PackageManager manager = application.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					application.getPackageName(), 0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			versionInfo = "应用版本信息：" + "versionName=" + versionName
					+ "      versionCode=" + versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionInfo;
	}

	/**
	 * 获取App安装包信息
	 * 
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
		if (info == null) {
			info = new PackageInfo();
		}
		return info;
	}

	/**
	 * 获取版本号<br/>
	 */
	public static String getVersionName() {
		String versionInfo = "当前版本信息不详";
		try {
			SoftwareApplication application = SoftwareApplication.getInstance();
			PackageManager manager = application.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					application.getPackageName(), 0);
			versionInfo = info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionInfo;
	}

	/**
	 * 获取版本代码<br/>
	 */
	public static int getVersionCode() {
		int versionCode = 0;
		try {
			SoftwareApplication application = SoftwareApplication.getInstance();
			PackageManager manager = application.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					application.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取网络传递参数的MD5加密字符串<br/>
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static boolean isLogin(BaseActivity activity) {
		Integer member_id = (Integer) SPUtils.get(activity, SPUtils.MEMBER_ID,
				-1);
		if (member_id == null || member_id == -1) {
			return false;
		} else {
			return true;
		}
	}

}
