package qlsl.androiddesign.util.commonutil;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * DCIM中的Camera工具类<br/>
 * at 20160225 by ylq<br/>
 */
public class CameraUtils {

	/**
	 * 获取外部或内部存储卡路径<br/>
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	/**
	 * 获取Camera目录<br/>
	 * 
	 */
	public static File getCameraDirectory(Context context) {
		String path = getSDCardPath() + "DCIM" + File.separator + "Camera";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取Camera二级目录<br/>
	 * 
	 */
	public static File getCameraDirectory(Context context, String dirName) {
		File file = new File(getCameraDirectory(context), dirName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

}
