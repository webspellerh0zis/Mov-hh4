package qlsl.androiddesign.activity.baseactivity;

import java.lang.reflect.Method;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.qlsl.androiddesign.appname.R;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.activity.commonactivity.SettingActivity;
import qlsl.androiddesign.constant.BasicSettingConstant;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.util.commonutil.Log;

/**
 * Activity的基类<br/>
 * 根据BasicSettingConstant中的配置及SP存储值自动初始化主题和方向<br/>
 * 自动设置，不提供公共函数<br/>
 * 
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class BaseSherlockFragmentActivity extends SherlockFragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		outputCreateActivityInfo();
		setTheme();
		super.onCreate(savedInstanceState);
		setTransparentNoticeBar();
		pushActivityToStack();
		setOriention();
	}

	protected void onResume() {
		super.onResume();
		outputResumeActivityInfo();
	}

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		outputNewIntentActivityInfo();
	}

	private void outputCreateActivityInfo() {
		Log.i("onCreate：activity：<br/>" + getLocalClassName());
	}

	private void outputResumeActivityInfo() {
		Log.i("onResume：activity：<br/>" + getLocalClassName());
	}

	private void outputNewIntentActivityInfo() {
		Log.i("onNewIntent：activity：<br/>" + getLocalClassName());
	}

	private void setTransparentNoticeBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	private void pushActivityToStack() {
		ActivityManager.getInstance().pushActivity(this);
	}

	private void setTheme() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean showNoticeBar = sp.getBoolean(getString(R.string.showNoticeBar), BasicSettingConstant.showNoticeBar);
		boolean showActionBar = sp.getBoolean(getString(R.string.showActionBar), BasicSettingConstant.showActionBar);

		if (showActionBar && showNoticeBar) {
			setTheme(R.style.Sherlock___Theme);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else if (!showActionBar && showNoticeBar) {
			setTheme(android.R.style.Theme_NoTitleBar);
		} else if (!showNoticeBar) {
			setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		}

	}

	private void setOriention() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean screenOrientation = sp.getBoolean(getString(R.string.screenOrientation),
				BasicSettingConstant.screenOrientation);
		setRequestedOrientation(screenOrientation ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
				: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		setIconEnable(menu, true);
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_item1:
			Intent intent = new Intent(this, SettingActivity.class);
			startActivityForResult(intent, IntentCodeConstant.REQUEST_CODE_MAIN);
			break;
		}
		return true;
	}

	/**
	 * 作用：设置选项菜单项的图标显示<br>
	 * 说明：函数功能无效
	 */
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class.forName("com.actionbarsherlock.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
			m.setAccessible(true);
			m.invoke(menu, enable);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void finish() {
		super.finish();
		ActivityManager.getInstance().removeActivity(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
if (event.getAction() == KeyEvent.ACTION_UP) {
			if (!getLocalClassName().equals(MainActivity.class.getName())) {
				ActivityManager.getInstance().popActivity();
				return false;
			}
}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
