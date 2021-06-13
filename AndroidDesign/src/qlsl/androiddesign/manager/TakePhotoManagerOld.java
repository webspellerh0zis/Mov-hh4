package qlsl.androiddesign.manager;

import java.io.File;
import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.util.commonutil.BitmapUtils;

@SuppressLint("SimpleDateFormat")
public class TakePhotoManagerOld {

	/**
	 * 版本比较：是否是4.4及以上版本
	 */
	public static final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	private BaseActivity activity;

	private ImageView unBindView, bindView, leftBgView, rightBgView;

	public static final String ACCOUNT_DIR = Environment
			.getExternalStorageDirectory().getPath() + "/account/";

	public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";

	private static final String IMGPATH = ACCOUNT_DIR
			+ ACCOUNT_MAINTRANCE_ICON_CACHE;

	private static String IMAGE_FILE_NAME;

	private static String TMP_IMAGE_FILE_NAME;

	private String photoPath;

	private File file;

	private UploadFileListener uploadFileListener;

	private OnImageFinishedListener onImageFinishedListener;

	public TakePhotoManagerOld(BaseActivity activity, ImageView unBindView,
			OnImageFinishedListener onImageFinishedListener) {
		this.activity = activity;
		this.unBindView = unBindView;
		this.onImageFinishedListener = onImageFinishedListener;
		IMAGE_FILE_NAME = "img.jpeg";
		TMP_IMAGE_FILE_NAME = "tmp_img.jpeg";
	}

	public TakePhotoManagerOld(BaseActivity activity, ImageView bindView,
			UploadFileListener uploadFileListener) {
		this.activity = activity;
		this.bindView = bindView;
		this.uploadFileListener = uploadFileListener;
		IMAGE_FILE_NAME = "face.jpeg";
		TMP_IMAGE_FILE_NAME = "tmp_face.jpeg";
	}

	public TakePhotoManagerOld(BaseActivity activity, ImageView leftBgView,
			ImageView rightBgView, UploadFileListener uploadFileListener) {
		this.activity = activity;
		this.leftBgView = leftBgView;
		this.rightBgView = rightBgView;
		this.uploadFileListener = uploadFileListener;
		IMAGE_FILE_NAME = "bg.jpeg";
		TMP_IMAGE_FILE_NAME = "tmp_bg.jpeg";
	}

	/**
	 * <br>
	 * 功能简述:4.4及以上从相册选择照片 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void selectImageUriAfterKikat() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		activity.startActivityForResult(intent,
				IntentCodeConstant.SELECET_PHOTO_AFTER_KIKAT);
	}

	/**
	 * <br>
	 * 功能简述:4.4以下从相册选照片并剪切 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 */
	private void cropImageUri() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent,
				IntentCodeConstant.SELECT_PHOTO);

	}

	/**
	 * <br>
	 * 功能简述: 4.4及以上选取照片后剪切方法 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param uri
	 */
	private void cropImageUriAfterKikat(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true); // 返回数据bitmap
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent,
				IntentCodeConstant.SET_ALBUM_PHOTO_KITKAT);
	}

	/**
	 * <br>
	 * 功能简述:对拍照的图片剪切 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param uri
	 */
	private void cameraCropImageUri(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("scale", true);
		// if (mIsKitKat) {
		intent.putExtra("return-data", true);
		// } else {
		// intent.putExtra("return-data", false);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		// }
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		activity.startActivityForResult(intent, IntentCodeConstant.CROP_PHOTO);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentCodeConstant.SELECT_PHOTO) {
			if (resultCode == Activity.RESULT_OK && null != data) {
				File file = new File(IMGPATH, TMP_IMAGE_FILE_NAME);
				Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(file));
				if (bindView != null) {
					Bitmap bitmap_round = BitmapUtils.toRoundBitmap(bitmap);
					bindView.setImageBitmap(bitmap_round);
				}
				if (leftBgView != null && rightBgView != null) {
					leftBgView.setImageBitmap(bitmap);
					rightBgView.setImageBitmap(bitmap);
				}
				setPhotoFile(bitmap, file);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				activity.showToast("取消头像设置");
			}
		} else if (requestCode == IntentCodeConstant.SELECET_PHOTO_AFTER_KIKAT) {
			if (resultCode == Activity.RESULT_OK && null != data) {
				photoPath = getPath(activity, data.getData());
				cropImageUriAfterKikat(Uri.fromFile(new File(photoPath)));
			} else if (resultCode == Activity.RESULT_CANCELED) {
				activity.showToast("取消头像设置");
			}
		} else if (requestCode == IntentCodeConstant.SET_ALBUM_PHOTO_KITKAT) {
			File file = new File(IMGPATH, TMP_IMAGE_FILE_NAME);
			Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(file));
			if (bindView != null) {
				Bitmap bitmap_round = BitmapUtils.toRoundBitmap(bitmap);
				bindView.setImageBitmap(bitmap_round);
			}
			if (leftBgView != null && rightBgView != null) {
				leftBgView.setImageBitmap(bitmap);
				rightBgView.setImageBitmap(bitmap);
			}
			setPhotoFile(bitmap, file);

			// Bitmap bitmap = data.getParcelableExtra("data");
			// bindView.setImageBitmap(bitmap);
		} else if (requestCode == IntentCodeConstant.TAKE_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {
				cameraCropImageUri(Uri.fromFile(new File(IMGPATH,
						IMAGE_FILE_NAME)));
			} else {
				activity.showToast("取消头像设置");
			}
		} else if (requestCode == IntentCodeConstant.CROP_PHOTO) {
			// 拍照的设置头像 不考虑版本
			Bitmap bitmap = null;
			// if (mIsKitKat) { //高版本
			// if (null != data) {
			// bitmap = data.getParcelableExtra("data");
			// showLoading();
			// mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
			// } else { //高版本不能通过“data”获取到图片数据的就用uri
			// if (resultCode == RESULT_OK) {
			// bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH,
			// IMAGE_FILE_NAME)));
			// showLoading();
			// mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
			// }
			// }
			// } else { //低版本
			if (resultCode == Activity.RESULT_OK && null != data) {
				File file = new File(IMGPATH, IMAGE_FILE_NAME);
				bitmap = decodeUriAsBitmap(Uri.fromFile(file));
				if (bindView != null) {
					Bitmap bitmap_round = BitmapUtils.toRoundBitmap(bitmap);
					bindView.setImageBitmap(bitmap_round);
				}
				if (leftBgView != null && rightBgView != null) {
					leftBgView.setImageBitmap(bitmap);
					rightBgView.setImageBitmap(bitmap);
				}
				setPhotoFile(bitmap, file);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				activity.showToast("取消头像设置");
			} else {
				activity.showToast("头像设置失败");
			}
		}
	}

	/**
	 * <br>
	 * 功能简述: <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * <br>
	 * 功能简述:4.4及以上获取图片的方法 <br>
	 * 功能详细描述: <br>
	 * 注意:
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	/**
	 * 选取图片<br/>
	 * 兼容4.3以上，以下版本<br/>
	 */
	public void pickPhoto() {
		if (mIsKitKat) {
			selectImageUriAfterKikat();
		} else {
			cropImageUri();
		}
	}

	/**
	 * 照相<br/>
	 */
	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(IMGPATH, IMAGE_FILE_NAME);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivityForResult(intent,
				IntentCodeConstant.TAKE_PHOTO);

	}

	/**
	 * 获取相片文件<br/>
	 */
	public File getPhotoFile() {
		return file;
	}

	/**
	 * 设置头像<br/>
	 */
	private void setPhotoFile(Bitmap bitmap, File file) {
		this.file = file;
		if (uploadFileListener != null) {
			uploadFileListener.uploadFile(file);
		}
		if (onImageFinishedListener != null) {
			onImageFinishedListener.onImageFinished(unBindView, bitmap, file);
		}
	}

	public interface UploadFileListener {

		public abstract void uploadFile(File file);

	}

	public interface OnImageFinishedListener {

		public abstract void onImageFinished(ImageView unBindView,
				Bitmap bitmap, File file);

	}

}