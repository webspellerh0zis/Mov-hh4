package qlsl.androiddesign.manager;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.util.commonutil.CacheUtils;
import qlsl.androiddesign.util.commonutil.CameraUtils;

/**
 * 暂时兼容4.4以下终端 at 20150824 by ylq<br/>
 * 增加保护路径控制，更新于20160225<br/>
 * 为避免出现恢复出厂后无法保存裁剪图片的情况，采用分离模式<br/>
 * 操作分离：照相与裁剪分离<br/>
 * 文件分离：照相后的保存图片与裁剪后的创建图片分成两个文件<br/>
 * 
 * 流程：<br/>
 * 照相：创建空的缓存文件，传递uri,照相后文件自动写入数据，缓存文件中获取uri,传递到裁剪函数，进入裁剪流程<br/>
 * 选取：选取照片，intent中获取uri,传递到裁剪函数，进入裁剪流程<br/>
 * 裁剪：设置比例，大小，返回数据等进行裁剪，从intent中获取裁剪后的bitmap,生成正式文件，回调<br/>
 * 
 * 照相不能返回数据，只能从文件中取得，File中创建uri<br/>
 * 选取会自动返回数据，Intent中获取uri<br/>
 * 裁剪可以选择返回数据,Intent中获取bitmap<br/>
 * 
 * 注意：裁剪不要传递文件uri,这是引起无法保存裁剪图片情况的最大原因<br/>
 * 操作分离(照相裁剪分离)：不需要传递uri,此时存在bitmap，但不存在裁剪后的file<br/>
 * 操作合成(照相裁剪合成)：照相裁剪使用同一张图片，为文件合成模式，易出现无法保存情况<br/>
 * 
 * Bmob使用本地相同文件名不同大小上传云端为不同文件路径的文件机制<br/>
 * 方案一：使用文件名覆盖机制，后裁剪图片覆盖之前图片，减少内存消耗<br/>
 * 方案二：使用文件名区分机制，后裁剪图片不会覆盖之前，增加内存消耗<br/>
 * 方案一Bug：文件名相同，文件大小相同时不会上传,大小相同的概率取决于BmobSDK判断的大小单位级别<br/>
 * 综上，使用方案二，使用时间戳扩展文件名<br/>
 * 
 * 文件名采取三层式：一级目录+二级目录+三级名称<br/>
 * 一级目录：文件类型，图片，音乐等<br/>
 * 二级目录：使用类别，账户，栏目等<br/>
 * 三级名称：img_crop_2015_08_25_12_40_59或img_take_2015_08_25_12_40_59<br/>
 * 
 * 文件存储路径:存储卡缓存目录(不存在则存于设备缓存目录),例：<br/>
 * /sdcard/Android/data/包名/cache/picture/account/
 * user123_account_crop_2015_08_25_12_40_59. jpeg <br/>
 * /sdcard/Android/data/包名/cache/picture/account/
 * user123_account_take_2015_08_25_12_40_59. jpeg <br/>
 * 
 * /sdcard/Android/data/包名/cache/picture/channel13/
 * user123_channel13_crop_2015_08_25_12_40_59. jpeg <br/>
 * /sdcard/Android/data/包名/cache/picture/channel13/
 * user123_channel13_take_2015_08_25_12_40_59. jpeg <br/>
 */
@SuppressLint("SimpleDateFormat")
public class TakePhotoManager {

	private static final String FIRST_DIR = "picture/";// 一级目录名

	private static final String TYPE_CROP_NAME = "crop";// 裁剪类型名

	private static final String TYPE_TAKE_NAME = "take";// 照相类型名

	private static final String SUFFIX_NAME = ".jpeg";// 文件后缀名

	private String secondDir;// 二级目录名

	private String imgName;// 裁剪图全名

	private String tmpImgName;// 照相图全名

	private int aspectX, aspectY, outputX, outputY;// 裁剪参数

	private Activity activity;

	private OnFinishedCropListener listener;

	private boolean isProtectedDir = true;

	public TakePhotoManager(Activity activity, OnFinishedCropListener listener) {
		this.activity = activity;
		this.listener = listener;
		setOutputParams(1, 1, 320, 320);
	}

	public TakePhotoManager(boolean isProtectedDir, Activity activity, OnFinishedCropListener listener) {
		this.isProtectedDir = isProtectedDir;
		this.activity = activity;
		this.listener = listener;
		setOutputParams(1, 1, 320, 320);
	}

	/**
	 * 根据二级目录，文件前缀生成文件名<br/>
	 * 不用包含下斜杠与分隔符<br/>
	 */
	public void setPhotoName(String secondDir, String prefixName) {
		this.secondDir = secondDir;

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		imgName = prefixName + "_" + TYPE_CROP_NAME + "_" + timestamp + SUFFIX_NAME;
		tmpImgName = prefixName + "_" + TYPE_TAKE_NAME + "_" + timestamp + SUFFIX_NAME;
	}

	/**
	 * 设置裁剪参数<br/>
	 */
	public void setOutputParams(int aspectX, int aspectY, int outputX, int outputY) {
		this.aspectX = aspectX;
		this.aspectY = aspectY;
		this.outputX = outputX;
		this.outputY = outputY;
	}

	/**
	 * 照相<br/>
	 */
	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(getSaveDirectory(), tmpImgName);
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent, IntentCodeConstant.TAKE_PHOTO);
	}

	/**
	 * 从相册选取照片 <br>
	 * 
	 */
	public void selectPhoto() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		activity.startActivityForResult(intent, IntentCodeConstant.SELECT_PHOTO);
	}

	/**
	 * 裁剪<br>
	 */
	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		activity.startActivityForResult(intent, IntentCodeConstant.CROP_PHOTO);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == IntentCodeConstant.SELECT_PHOTO) {
			if (data == null) {
				return;
			}
			Uri uri = data.getData();
			cropPhoto(uri);
		} else if (requestCode == IntentCodeConstant.TAKE_PHOTO) {
			File file = new File(getSaveDirectory(), tmpImgName);
			if (file.exists() && file.length() > 0) {
				Uri uri = Uri.fromFile(file);
				cropPhoto(uri);
			} else {
				Toast.makeText(activity, "文件中没有数据", Toast.LENGTH_LONG).show();
			}
		} else if (requestCode == IntentCodeConstant.CROP_PHOTO) {
			if (data == null) {
				return;
			}
			Bitmap bitmap = data.getExtras().getParcelable("data");
			File file = copyDataToFile(bitmap);
			listener.onFinishedCrop(file, bitmap);
		}
	}

	/**
	 * 保存bitmap中的内容到File<br/>
	 */
	public File copyDataToFile(Bitmap bitmap) {
		File file = new File(getSaveDirectory(), imgName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(activity, "写入文件失败", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(activity, "写入文件失败", Toast.LENGTH_LONG).show();
		}
		return file;
	}

	/**
	 * 获取保存目录<br/>
	 */
	private File getSaveDirectory() {
		File file = null;
		if (isProtectedDir) {
			file = CacheUtils.getCacheDirectory(activity, true, FIRST_DIR + secondDir);
		} else {
			file = CameraUtils.getCameraDirectory(activity, FIRST_DIR + secondDir);
		}
		return file;
	}

	public interface OnFinishedCropListener {

		public abstract void onFinishedCrop(File file, Bitmap bitmap);

	}

}