package qlsl.androiddesign.activity.commonactivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.constant.BasicSettingConstant;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.fragment.commonfragment.FragmentBar1;
import qlsl.androiddesign.fragment.commonfragment.FragmentBar2;
import qlsl.androiddesign.fragment.commonfragment.FragmentBar3;
import qlsl.androiddesign.fragment.commonfragment.MenuFragment;
import qlsl.androiddesign.fragment.commonfragment.MenuRightFragment;
import qlsl.androiddesign.fragment.commonfragment.PagerMainFragment;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.manager.ExitManager;
import qlsl.androiddesign.service.subservice.LogFloatService;
import qlsl.androiddesign.service.subservice.MediaFloatService;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 首页：底部选项卡+左右侧滑菜单<br/>
 * 选项卡三种实现方案主要类：PagerMainFragment，TabMainFragment，WrapMainFragment<br/>
 * 暂时只做了PagerMainFragment中的沉浸式标题栏适配<br/>
 * 默认使用PagerMainFragment方案<br/>
 * 可一键切换:替换当前类中的PagerMainFragment即可<br/>
 */
public class MainActivity extends BaseActivity {

	private SlidingMenu slidingMenu;

	private PagerMainFragment mainFragment;

	private MenuFragment menuFragment;

	private MenuRightFragment menuRightFragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().popActivitysUnderTheSpecify(MainActivity.class);
		setContentView(R.layout.content_frame);
		setActionBarTab();
		setSlidingMenu();
	}

	public FunctionView<?> getFunctionView() {
		return null;
	}

	private void setActionBarTab() {
		boolean showTabTop = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("showTabTop",BasicSettingConstant.showTabTop);
		if (getSupportActionBar() == null) {
			return;
		} else {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setLogo(R.drawable.btn_menu);
		}

		if (showTabTop) {
			initActionBarTab();
		}
	}

	private void initActionBarTab() {
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab bar1 = getSupportActionBar().newTab().setText(getString(R.string.bar1));
		Tab bar2 = getSupportActionBar().newTab().setText(getString(R.string.bar2));
		Tab bar3 = getSupportActionBar().newTab().setText(getString(R.string.bar3));
		bar1.setTabListener(new FragmentBar1());
		bar2.setTabListener(new FragmentBar2());
		bar3.setTabListener(new FragmentBar3());
		getSupportActionBar().addTab(bar1);
		getSupportActionBar().addTab(bar2);
		getSupportActionBar().addTab(bar3);
	}

	/**
	 * 配置滑动菜单
	 */
	private void setSlidingMenu() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean showTabBottom = sp.getBoolean("showTabBottom", BasicSettingConstant.showTabBottom);
		boolean showLeftMenu = sp.getBoolean("showLeftMenu", BasicSettingConstant.showLeftMenu);
		boolean showRightMenu = sp.getBoolean("showRightMenu", BasicSettingConstant.showRightMenu);

		// 初始化带底部Tab的主界面视图
		if (showTabBottom) {
			mainFragment = new PagerMainFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment, "main").commit();
		}

		// 初始化滑动菜单
		if (showLeftMenu || showRightMenu) {
			initSlidingMenu(showLeftMenu, showRightMenu);
		}

		// 设置左滑菜单的视图界面
		if (showLeftMenu) {
			setMenu();
		}

		// 设置右滑菜单的视图界面
		if (showRightMenu) {
			setSecondaryMenu();
		}
	}

	private void initSlidingMenu(boolean showLeftMenu, boolean showRightMenu) {
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shape_shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		slidingMenu.setMode(getMode(showLeftMenu, showRightMenu));
		slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);
	}

	private int getMode(boolean showLeftMenu, boolean showRightMenu) {
		if (showLeftMenu && !showRightMenu) {
			return SlidingMenu.LEFT;
		} else if (!showLeftMenu && showRightMenu) {
			return SlidingMenu.RIGHT;
		} else if (showLeftMenu && showRightMenu) {
			return SlidingMenu.LEFT_RIGHT;
		}
		return SlidingMenu.LEFT;
	}

	private void setMenu() {
		slidingMenu.setMenu(R.layout.menu_frame);
		menuFragment = new MenuFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment, "menu").commit();
	}

	private void setSecondaryMenu() {
		slidingMenu.setSecondaryMenu(R.layout.menu_frame_two);
		menuRightFragment = new MenuRightFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two, menuRightFragment, "menu_right").commit();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (slidingMenu != null) {
				slidingMenu.toggle();
			}
			break;
		case R.id.menu_item1:
			Intent intent = new Intent(this, SettingActivity.class);
			startActivityForResult(intent, IntentCodeConstant.REQUEST_CODE_MAIN);
			break;
		}
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_menu:
			if (slidingMenu != null) {
				slidingMenu.toggle();
			}
			return;
		case R.id.btn_common:
			return;

		}

		if (mainFragment != null) {
			mainFragment.onClick(v);
		}
		if (menuFragment != null) {
			menuFragment.onClick(v);
		}
		if (menuRightFragment != null) {
			menuRightFragment.onClick(v);
		}

	}

	public void onBackPressed() {
		if (slidingMenu == null || !slidingMenu.isMenuShowing()) {
			ExitManager.onBackPressed(this);
		} else {
			slidingMenu.showContent();
		}

	}

	@SuppressLint("NewApi")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == IntentCodeConstant.RESULT_CODE_SETTING) {
			// recreate();
		} else {
			if (mainFragment != null) {
				mainFragment.onActivityResult(requestCode, resultCode, data);
			}
			if (menuFragment != null) {
				menuFragment.onActivityResult(requestCode, resultCode, data);
			}
			if (menuRightFragment != null) {
				menuRightFragment.onActivityResult(requestCode, resultCode, data);
			}
		}
	}

	public void onDestroy() {
		super.onDestroy();
		ExitManager.clearStaticData();
		stopFloatService();
		/**
		 * 清理垃圾
		 */
		System.gc();

		/**
		 * 杀掉进程，会阻止某些OnDestroy函数的正常调用<br/>
		 * 如果有推送等后台服务，就不要杀掉进程，但要主动清理静态数据<br/>
		 * 不杀掉进程，log4j需要在application之后的界面中初始化<br/>
		 * 所以application和IntanceActivity都有对log4j初始化<br/>
		 */
		// System.exit(0);
	}

	private void stopFloatService() {
		stopService(LogFloatService.class);
		stopService(MediaFloatService.class);
	}

	public void showTabWidget() {
		mainFragment.showTabWidget();
	}

	public void hideTabWidget() {
		mainFragment.hideTabWidget();
	}
	
	public BaseFragment getMainFragment() {
		return mainFragment;
	}

}