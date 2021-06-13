package qlsl.androiddesign.activity.baseactivity;

import java.lang.reflect.Method;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import qlsl.androiddesign.constant.BasicSettingConstant;

/**
 * 上层属性设置activity<br/>
 * 其子类不自动加入到自定义栈中,不会存在跨级返回问题<br/>
 * 暂未做沉浸式标题栏的布局适配<br/>
 * 思路：需要根据手机版本动态加载不同标题栏布局,并实现标题栏函数化<br/>
 */
public abstract class BaseSherlockPreferenceActivity extends SherlockPreferenceActivity {

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

	protected void onCreate(Bundle savedInstanceState) {
		setTheme();
		super.onCreate(savedInstanceState);
		setTransparentNoticeBar();
		setOriention();
	}

	@SuppressLint("InlinedApi")
	private void setTransparentNoticeBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	public void setTitle(String title) {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(title);
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		}
	}

}
