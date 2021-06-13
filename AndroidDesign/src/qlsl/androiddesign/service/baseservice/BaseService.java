package qlsl.androiddesign.service.baseservice;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import qlsl.androiddesign.util.commonutil.Log;

public abstract class BaseService extends Service {

	public void onCreate() {
		super.onCreate();
		outputCreateServiceInfo();
	}

	public IBinder onBind(Intent intent) {
		outputOnBindServiceInfo();
		return null;
	}

	public boolean onUnbind(Intent intent) {
		outputOnUnbindServiceInfo();
		return super.onUnbind(intent);
	}

	public void onDestroy() {
		super.onDestroy();
		outputonDestroyServiceInfo();
	}

	/**
	 * 创建悬浮窗
	 */
	public View createFloatView(WindowManager wm,
			WindowManager.LayoutParams wmParams, int gravity, int resource) {
		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = gravity;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.format = PixelFormat.RGBA_8888;

		View view = LayoutInflater.from(getApplicationContext()).inflate(
				resource, null);
		wm.addView(view, wmParams);
		return view;
	}

	private void outputCreateServiceInfo() {
		Log.i("onCreate：Service：<br/>" + getClass().getName());
	}

	private void outputOnBindServiceInfo() {
		Log.i("OnBind：Service：<br/>" + getClass().getName());
	}

	private void outputOnUnbindServiceInfo() {
		Log.i("OnUnbind：Service：<br/>" + getClass().getName());
	}

	private void outputonDestroyServiceInfo() {
		Log.i("onDestroy：Service：<br/>" + getClass().getName());
	}

}
