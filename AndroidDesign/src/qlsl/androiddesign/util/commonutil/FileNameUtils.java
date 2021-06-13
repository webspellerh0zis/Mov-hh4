package qlsl.androiddesign.util.commonutil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

/**
 * 获取账户，其他模块的二级目录，前缀名<br/>
 * 
 */
public class FileNameUtils {

	/**
	 * 创建新的图片文件<br/>
	 * 短时间内若调用本方法多次，prefixName最好不同<br/>
	 * 以避免系统运行速度过快的情况下，时间戳相同导致文件覆盖的问题<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getFilePath(BaseActivity activity, String secondDir, String prefixName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String imgName = prefixName + "_" + timestamp + ".jpeg";

		File file = new File(CacheUtils.getCacheDirectory(activity, true, "picture/" + secondDir), imgName);

		return file.getAbsolutePath();
	}

	/**
	 * 获取账户二级目录<br/>
	 */
	public static String getAccountSecondDir() {
		return "account";
	}

	/**
	 * 获取账户文件前缀名<br/>
	 */
	public static String getAccountPrefixName(BaseActivity activity) {
		return "account";
	}

	/**
	 * 获取圈子二级目录<br/>
	 */
	public static String getGroupSecondDir() {
		return "group";
	}

	/**
	 * 获取圈子文件前缀名<br/>
	 */
	public static String getGroupNoticePrefixName(BaseActivity activity) {
		return "notice";
	}

}
