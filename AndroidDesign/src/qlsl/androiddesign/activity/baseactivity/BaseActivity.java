package qlsl.androiddesign.activity.baseactivity;

import java.io.File;

import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.service.baseservice.BaseService;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.util.commonutil.SDCardUtils;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.rippleview.Typefaces;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.qlsl.androiddesign.appname.R;
import com.umeng.socialize.sso.UMSsoHandler;

/**
 * Activity的基类<br/>
 * 一：用于提供Activity的公用函数及管理各个Activity<br/>
 * 二：回调FunctionView子类中的周期函数及点击事件<br/>
 */
public abstract class BaseActivity extends BaseSherlockFragmentActivity
		implements HttpListener, DialogInterface.OnClickListener {

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean("needForceExit", true)) {
			Log.d("检测到窗口在重新创建，正在阻止创建并安全退出");
			Toast.makeText(this, "程序异常，正在逐一退出，请稍等", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onRestart() {
		super.onRestart();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onRestart();
		}
	}

	public void onStart() {
		super.onStart();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onStart();
		}
	}

	public void onResume() {
		super.onResume();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onResume();
		}
	}

	public void onPause() {
		super.onPause();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onPause();
		}
	}

	public void onStop() {
		super.onStop();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onStop();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onDestroy();
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onSaveInstanceState(outState);
		}
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@SuppressWarnings("unused")
	public void startActivityForResult(Class<? extends Activity> destClass,
			int requestCode) {
		Intent intent = new Intent(this, destClass);
		super.startActivityForResult(intent, requestCode);
		if (false) {
			overridePendingTransition(R.anim.in_translate_top,
					R.anim.out_translate_top);
		}

	}

	@SuppressWarnings("unused")
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		if (false) {
			overridePendingTransition(R.anim.in_translate_top,
					R.anim.out_translate_top);
		}
	}

	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		ssoCallback(arg0, arg1, arg2);
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onActivityResult(arg0, arg1, arg2);
		}
	}

	/**
	 * 使用SSO授权必须添加如下代码
	 */
	private void ssoCallback(int requestCode, int resultCode, Intent data) {
try{
		UMSsoHandler ssoHandler = SoftwareApplication.getInstance()
				.getUMShareService().getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
}catch(Excetion e){
SoftwareApplication.getInstance().getUMShareService().getConfig()
					.cleanListeners();
}
	}

	@SuppressWarnings("unused")
	public void startActivity(Class<? extends Activity> destClass) {
		Intent intent = new Intent(this, destClass);
		super.startActivity(intent);
		if (false) {
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_left_out);
		}

	}

	@SuppressWarnings("unused")
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if (false) {
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_left_out);
		}
	}

	public void startActivity(Class<? extends Activity> destClass,
			int enterAnim, int exitAnim) {
		Intent intent = new Intent(this, destClass);
		super.startActivity(intent);
		overridePendingTransition(enterAnim, exitAnim);

	}

	public void startActivity(Intent intent, int enterAnim, int exitAnim) {
		super.startActivity(intent);
		overridePendingTransition(enterAnim, exitAnim);

	}

	public void startService(Class<? extends BaseService> destClass) {
		Intent intent = new Intent(this, destClass);
		super.startService(intent);
	}

	public ComponentName startService(Intent intent) {
		return super.startService(intent);
	}

	public void stopService(Class<? extends BaseService> destClass) {
		Intent intent = new Intent(this, destClass);
		stopService(intent);
	}

	public void showToast(String text) {
		showLongToast(text);
	}

	public void showToastOnUiThread(final String text) {
		runOnUiThread(new Runnable() {

			public void run() {
				showToast(text);
			}
		});
	}

	public void showLongToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setBackgroundResource(R.drawable.bg_toast);
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			toast.show();
		}
	}

	public void showShortToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setBackgroundResource(R.drawable.bg_toast);
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			toast.show();
		}
	}

	public void showNoDataToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setOrientation(LinearLayout.HORIZONTAL);
			toastView.setGravity(Gravity.CENTER_VERTICAL);
			toastView.setBackgroundResource(R.drawable.bg_toast);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.iv_no_data_icon);
			toastView.addView(imageView, 0);
			toast.show();
		}
	}

	public void showScreenShotToast(Bitmap bitmap) {
		if (bitmap != null) {
			String text = "正在截屏";
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setOrientation(LinearLayout.VERTICAL);
			toastView.setBackgroundResource(R.drawable.bg_toast);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout.LayoutParams params_text = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams params_img = new LinearLayout.LayoutParams(
					160, 240);
			params_text.setMargins(0, 10, 0, 10);
			params_img.setMargins(0, 0, 0, 20);
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			textView.setLayoutParams(params_text);
			textView.setGravity(Gravity.CENTER);
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(params_img);
			imageView.setImageBitmap(bitmap);
			toastView.addView(imageView, 1);
			toast.show();
		} else {
			showToast("截屏失败");
		}
	}

	public void showEnterToast(String text, int resId) {
		if (!TextUtils.isEmpty(text)) {
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setOrientation(LinearLayout.HORIZONTAL);
			toastView.setGravity(Gravity.CENTER_VERTICAL);
			toastView.setBackgroundResource(R.drawable.bg_toast);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(resId);
			toastView.addView(imageView, 0);
			toast.show();
		}
	}

	public void showExitToast(String text, int resId) {
		if (!TextUtils.isEmpty(text)) {
			ToolSpeechUtils.startSpeaking(text, 0);
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			toastView.setOrientation(LinearLayout.HORIZONTAL);
			toastView.setGravity(Gravity.CENTER_VERTICAL);
			toastView.setBackgroundResource(R.drawable.bg_toast);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			TextView textView = (TextView) toastView.getChildAt(0);
			setTypeface(textView);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(resId);
			toastView.addView(imageView, 0);
			toast.show();
		}
	}

	public void setTypeface(TextView textView) {
		ToolColor toolColor = ToolColorUtils.get();
		if (toolColor == null) {
			textView.setTypeface(Typefaces.get(this));
		} else {
			if (toolColor.getIsOpenArt()) {
				textView.setTypeface(Typefaces.get(this));
			}
			// textView.setTextColor(Color.parseColor(toolColor.getTextColor()));
			// textView.setTextSize(toolColor.getTextSize());
		}
	}

	public void saveScreenShotImage(Bitmap bitmap) {
		if (!SDCardUtils.isSDCardEnable()) {
			showToast("您的设备没有存储卡，不能保存图片");
		} else {
			String path = SDCardUtils.getSDCardPath() + "DCIM/qlsl/ScreenShot/";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = path + System.currentTimeMillis() + ".jpg";
			SDCardUtils.saveImage(bitmap, path);
			showToast("图片已保存在DCIM/qlsl/ScreenShot中！");
		}

	}

	public File saveBitmapToFile(Bitmap bitmap) {
		if (!SDCardUtils.isSDCardEnable()) {
			return null;
		} else {
			String path = SDCardUtils.getSDCardPath() + "DCIM/qlsl/ScreenShot/";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = path + System.currentTimeMillis() + ".jpg";
			SDCardUtils.saveImage(bitmap, path);
			File newFile = new File(path);
			return newFile;
		}

	}

	public void cropPhoto(String path) {
		Uri uri = Uri.parse("file://" + path);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, IntentCodeConstant.CROP_PHOTO);
	}

	public void cropPhoto(String path, int aspectX, int aspectY, int outputX,
			int outputY) {
		Uri uri = Uri.parse("file://" + path);
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
		startActivityForResult(intent, IntentCodeConstant.CROP_PHOTO);
	}

	/**
	 * 获取当前版本信息<br/>
	 * 每次发布新版本之前，请手动修改updateTime为发布时间<br/>
	 */
	public String getVersionInfo() {
		String updateTime = "2015-02-10";
		String versionInfo = "当前版本信息不详";
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			versionInfo = "versionName=" + versionName + "  versionCode="
					+ versionCode + "  updateTime=" + updateTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionInfo;
	}

	public abstract FunctionView<?> getFunctionView();

	public void onClick(View view) {
		getFunctionView().onClick(view);
	}

	public void onDbSucceed(String method, Object values) {
		showToast(values.toString());
	}

	public void onDbFaild(String method, Object values) {
		showNoDataToast(values.toString());
	}

	public void onNetWorkSucceed(String method, Object values) {
		showToast(values.toString());
	}

	public void onNetWorkFaild(String method, Object values) {
		showNoDataToast(values.toString());
	}

	public void onOtherSucceed(String method, Object values) {
		showToast(values.toString());
	}

	public void onOtherFaild(String method, Object values) {
		showNoDataToast(values.toString());
	}

	public void onException(String className, String method, Exception e) {
		String error = "网络连接出现异常：" + "\n    异常运行窗口：" + getClass().getName()
				+ "\n    异常所在位置：" + className + "\n    捕获异常函数：" + method
				+ "\n    " + e;
		Log.e(error);
		showLongToast(error);
	}

	public void onCancel(String method) {

	}

	public void onClick(DialogInterface dialog, int which) {
		getFunctionView().onClick(dialog, which);
	}

}
