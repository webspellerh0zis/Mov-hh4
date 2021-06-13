package qlsl.androiddesign.util.commonutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 文件操作工具类<br/>
 * 可以使用Environment中的DIRECTION常量<br/>
 * @Author: leo [http://www.zxjava.com]
 * @Date: 2011-8-11
 */
@SuppressLint("DefaultLocale")
public class FileUtils {

	public static File FilePhotoFront, FilePhotoAfter;

	public enum ActionCategory {
		UPLOAD, DOWNLOAD
	}

	public enum FileCategory {
		PICTURE, AUDIO, VIDEO, DOC, APK, OTHER
	}

	public static final String SDCARD_PATH = SDCardUtils.getSDCardPath()
			+ "pinghu";

	public static final String DOWNLOAD_PATH = "/download/";

	public static final String UPLOAD_PATH = "/upload/";

	public static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

	public static String getSDPATH() {
		return SDCARD_PATH;
	}

	private FileUtils() {

	}

	static {
		FilePhotoFront = createSDDir(DOWNLOAD_PATH + "photo/PhotoFront");
		FilePhotoAfter = createSDDir(DOWNLOAD_PATH + "photo/PhotoAfter");
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		String dirName = fileName.substring(0, fileName.lastIndexOf("/"));
		createSDDir(dirName);

		File file = new File(SDCARD_PATH + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(SDCARD_PATH + dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public static File getSDFile(String filePath) {
		File file = new File(SDCARD_PATH + filePath);
		return file;
	}

	/**
	 * 判断SD卡上的文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(SDCARD_PATH + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream input) throws IOException {
		File file = null;
		OutputStream output = null;
		try {
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return file;
	}

	/**
	 * 根据文件分类获取存储路径
	 * 
	 * @Author: leo
	 * @Date: 2011-8-11
	 * 
	 * @param fileCategory
	 * @return
	 */
	public static String getFileSavePathByCategory(FileCategory fileCategory,
			ActionCategory actionCategory) {
		String CURRENT_PATH = "";
		if (actionCategory == ActionCategory.DOWNLOAD) {
			CURRENT_PATH = DOWNLOAD_PATH;
		} else if (actionCategory == ActionCategory.UPLOAD) {
			CURRENT_PATH = UPLOAD_PATH;
		}
		if (fileCategory == FileCategory.DOC) {
			return CURRENT_PATH + "doc/";
		} else if (fileCategory == FileCategory.VIDEO) {
			return CURRENT_PATH + "video/";
		} else if (fileCategory == FileCategory.AUDIO) {
			return CURRENT_PATH + "audio/";
		} else if (fileCategory == FileCategory.APK) {
			return CURRENT_PATH + "apk/";
		} else if (fileCategory == FileCategory.OTHER) {
			return CURRENT_PATH + "other/";
		} else if (fileCategory == FileCategory.PICTURE) {
			return CURRENT_PATH + "picture/";
		}
		return CURRENT_PATH + "other/";
	}

	/**
	 * 获取文件大小
	 * 
	 * @Author: leo
	 * @Date: 2011-8-11
	 * 
	 * @param fileSize
	 * @return
	 */
	public static String getFileSize(int fileSize) {
		DecimalFormat df = new DecimalFormat("0.00");

		double size = 0d;
		if (fileSize < 1024) {
			return size + " Bytes";
		} else if (fileSize > 1024 && fileSize < 1048576) {
			size = (double) fileSize / 1024;
			return df.format(size) + " KB";
		} else if (fileSize > 1048576 && fileSize < 1073741824) {
			size = (double) fileSize / 1048576;
			return df.format(size) + " MB";
		} else {
			size = (double) fileSize / 1073741824;
			return df.format(size) + " GB";
		}
	}

	/**
	 * 打开文件
	 * 
	 * @Author: leo
	 * @Date: 2011-8-11
	 * 
	 * @param file
	 */
	public static void openFile(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		context.startActivity(intent);
	}

	/**
	 * 获取文件 MimeType
	 * 
	 * @Author: leo
	 * @Date: 2011-8-11
	 * 
	 * @param file
	 * @return
	 */
	public static String getMIMEType(File file) {
		String type = "/*";
		String name = file.getName();
		String suffix = name
				.substring(name.lastIndexOf(".") + 1, name.length())
				.toLowerCase();
		if (suffix.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else if (suffix.equals("doc") || suffix.equals("docx")) {
			type = "application/msword";
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			type = "application/vnd.ms-excel";
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
			type = "application/vnd.ms-powerpoint";
		} else if (suffix.equals("pdf")) {
			type = "application/pdf";
		} else if (suffix.equals("txt")) {
			type = "text/plain";
		}
		return type;
	}

}