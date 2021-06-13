package qlsl.androiddesign.util.commonutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

/**
 * 图片压缩工具类
 * 
 * @author touch_ping 2015-1-5 下午1:29:59
 */
public class BitmapCompressor {
	/**
	 * 质量压缩<br/>
	 * 不会减少像素即宽高<br/>
	 * 但像素中的透明信息会消失，变为完全不透明<br/>
	 * 文件大小减小但不能保证在规定的maxkb以内<br/>
	 * 最小为去除所有透明信息的文件大小<br/>
	 * 在去除所有透明信息后再压缩，文件大小不会变<br/>
	 */
	public static Bitmap compressBitmap(Bitmap image, int maxkb) {
		// L.showlog(压缩图片);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		// Log.i(test,原始大小 + baos.toByteArray().length);
		while (baos.toByteArray().length / 1024 > maxkb) { // 循环判断如果压缩后图片是否大于(maxkb)50kb,大于继续压缩
			// Log.i(test,压缩一次!);
			if (options - 10 <= 0) {
				break;
			}
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		if (!image.isRecycled()) {
			image.recycle();
			System.gc();
		}
		// Log.i(test,压缩后大小 + baos.toByteArray().length);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中

		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

		try {
			baos.close();
			isBm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 质量压缩<br/>
	 */
	public static Bitmap compressBitmap(Bitmap image) {
		int maxkb = 113;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > maxkb) {
			if (options - 10 <= 0) {
				break;
			}
			baos.reset();
			options -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);

		}
		if (!image.isRecycled()) {
			image.recycle();
			System.gc();
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

		try {
			baos.close();
			isBm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	 * 官网：获取压缩后的图片
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 官网：获取压缩后的图片
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filepath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filepath, options);
	}

	/**
	 * 从File中获取压缩后的bitmap<br/>
	 * 最低宽高，值越大宽高越大清晰度越高(小于原图),但内存越大，影响加载速度<br/>
	 * 可能会增加内存或减少内存,可能会内存溢出<br/>
	 * 优点：小图放大后依然清晰,故即使文件大小没超标准也建议使用此方法<br/>
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filepath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小）并存入options，但解析的bitmap为null
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		options.inSampleSize = calculateInSampleSize(options, 768, 970);
		if (options.inSampleSize == 1) {
			options.inSampleSize = 2;
		}
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);
		return bitmap;
	}

	public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] data = baos.toByteArray();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	/**
	 * 计算压缩比例值(改进版 by touch_ping)
	 * 
	 * 原版2>4>8...倍压缩 当前2>3>4...倍压缩
	 * 
	 * @param options
	 *            解析图片的配置信息
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		final int picheight = options.outHeight;
		final int picwidth = options.outWidth;

		int targetheight = picheight;
		int targetwidth = picwidth;
		int inSampleSize = 1;

		if (targetheight > reqHeight || targetwidth > reqWidth) {
			while (targetheight >= reqHeight && targetwidth >= reqWidth) {
				inSampleSize += 1;
				targetheight = picheight / inSampleSize;
				targetwidth = picwidth / inSampleSize;
			}
		}

		return inSampleSize;
	}

	/**
	 * 保存bitmap中的内容到File<br/>
	 * 短时间内若调用本方法多次，prefixName最好不同<br/>
	 * 以避免系统运行速度过快的情况下，时间戳相同导致文件覆盖的问题<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static File copyDataToFile(BaseActivity activity, Bitmap bitmap, String secondDir, String prefixName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String imgName = prefixName + "_" + timestamp + ".jpeg";

		File file = new File(CacheUtils.getCacheDirectory(activity, true, "picture/" + secondDir), imgName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		}
		return file;
	}

	@SuppressLint("SimpleDateFormat")
	public static File copyDataToFileByPng(BaseActivity activity, Bitmap bitmap, String secondDir, String prefixName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String imgName = prefixName + "_" + timestamp + ".png";

		File file = new File(CacheUtils.getCacheDirectory(activity, true, "picture/" + secondDir), imgName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		}
		return file;
	}

	@SuppressLint("SimpleDateFormat")
	public static File copyDataToVisableFile(BaseActivity activity, Bitmap bitmap, String prefixName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String imgName = prefixName + "_" + timestamp + ".jpeg";

		String dictoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "photo";
		File dictory = new File(dictoryPath);
		if (!dictory.exists()) {
			dictory.mkdirs();
		}
		File file = new File(dictory, imgName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		}
		return file;
	}

	@SuppressLint("SimpleDateFormat")
	public static File copyDataToVisableFileByPng(BaseActivity activity, Bitmap bitmap, String prefixName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String imgName = prefixName + "_" + timestamp + ".png";

		String dictoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "photo";
		File dictory = new File(dictoryPath);
		if (!dictory.exists()) {
			dictory.mkdirs();
		}
		File file = new File(dictory, imgName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		} catch (IOException e) {
			e.printStackTrace();
			activity.showToast("写入文件失败");
		}
		return file;
	}

	/**
	 * 获取位图的占用内存数(KB)<br/>
	 * 占用内存：显示时的占用内存，质量压缩前后不会减少<br/>
	 * 文件内存：存储图片的内存，与压缩有关<br/>
	 * bitmap占用内存约为文件内存20倍<br/>
	 */
	@SuppressLint("NewApi")
	public static long getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount() / 1024;
		}
		return bitmap.getRowBytes() * bitmap.getHeight() / 1024;

	}

}
