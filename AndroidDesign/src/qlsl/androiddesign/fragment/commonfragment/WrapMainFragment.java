package qlsl.androiddesign.fragment.commonfragment;

import static qlsl.androiddesign.constant.BasicSettingConstant.showTabToast;
import static qlsl.androiddesign.constant.BasicSettingConstant.showTitleTop;

import java.util.ArrayList;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.FragmentPagerAdapter;
import qlsl.androiddesign.constant.BasicSettingConstant;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.method.BaseMethod;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.baseview.WrapFragmentTabHost;
import qlsl.androiddesign.view.rippleview.Titanic;
import qlsl.androiddesign.view.rippleview.TitanicTextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
/**
 * MainActivity各Fragment生命周期说明：<br/>
 * MainFragment中的onResume,onPause等是在进入或退出MainActivity时执行 <br/>
 * FragmentTab 中的onResume,onPause等是在FragmentTab之间切换时,在进入或退出MainActivity时调用<br/>
 * 
 * 使用FragmentTabHost+ViewPager+Fragment实现选项卡<br/>
 * 不会预加载下一个Fragment，不会重复加载<br/>
 * 嫁接调用的周期函数在FunctionView中<br/>
 * 中和方案，滑动有背景，不建议使用<br/>
 */
public class WrapMainFragment extends BaseFragment implements OnTabChangeListener {

	private WrapFragmentTabHost tabHost;

	private ViewPager viewPager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("onCreateView：fragment：\n" + getClass().getName());
		return inflater.inflate(R.layout.fragment_main_wrap, container, false);
	}

	public FunctionView<?> getFunctionView() {
		return null;
	}

	private void startLauncherAnimation() {
		TextView tv_launcher = (TextView) getActivity().findViewById(R.id.tv_launcher);
		Animation animation_launcher = new AlphaAnimation(0f, 1f);
		animation_launcher.setDuration(1500);
		animation_launcher.setRepeatMode(Animation.REVERSE);
		animation_launcher.setRepeatCount(Animation.INFINITE);
		tv_launcher.startAnimation(animation_launcher);
	}

	@SuppressWarnings("unused")
	@SuppressLint("InflateParams")
	private void Init() {
		BaseActivity activity = (BaseActivity) getActivity();
		FragmentManager manager = activity.getSupportFragmentManager();

		setRetainInstance(true);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
		boolean showNoticeBar = sp.getBoolean(getString(R.string.showNoticeBar), BasicSettingConstant.showNoticeBar);
		boolean showActionBar = sp.getBoolean(getString(R.string.showActionBar), BasicSettingConstant.showActionBar);
		if (((!showActionBar && showNoticeBar) || (showActionBar && !showNoticeBar)
				|| (!showActionBar && !showNoticeBar)) && showTitleTop) {
			activity.findViewById(R.id.fragment_main_title_bar).setVisibility(View.VISIBLE);
		}

		viewPager = (ViewPager) activity.findViewById(R.id.real_tabcontent);
		tabHost = (WrapFragmentTabHost) activity.findViewById(android.R.id.tabhost);
		tabHost.setup(activity, manager, R.id.real_tabcontent);

		View tab1_indicator = activity.getLayoutInflater().inflate(R.layout.tab1_indicator, null);
		View tab2_indicator = activity.getLayoutInflater().inflate(R.layout.tab2_indicator, null);
		View tab3_indicator = activity.getLayoutInflater().inflate(R.layout.tab3_indicator, null);
		View tab4_indicator = activity.getLayoutInflater().inflate(R.layout.tab4_indicator, null);
		View tab5_indicator = activity.getLayoutInflater().inflate(R.layout.tab5_indicator, null);

		List<BaseFragment> fragments = new ArrayList<BaseFragment>();
		FragmentTab1 fragmentTab1 = new FragmentTab1();
		FragmentTab2 fragmentTab2 = new FragmentTab2();
		FragmentTab3 fragmentTab3 = new FragmentTab3();
		FragmentTab4 fragmentTab4 = new FragmentTab4();
		FragmentTab5 fragmentTab5 = new FragmentTab5();
		fragments.add(fragmentTab1);
		fragments.add(fragmentTab2);
		fragments.add(fragmentTab3);
		fragments.add(fragmentTab4);
		fragments.add(fragmentTab5);

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(tab1_indicator), fragmentTab1, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(tab2_indicator), fragmentTab2, null);
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(tab3_indicator), fragmentTab3, null);
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(tab4_indicator), fragmentTab4, null);
		tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator(tab5_indicator), fragmentTab5, null);

		viewPager.setOffscreenPageLimit(fragments.size() - 1);
		FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(manager, fragments);
		viewPager.setAdapter(pagerAdapter);

		tabHost.setOnTabChangedListener(this);
		viewPager.setOnPageChangeListener(pageListener);
	}

	private void showEnterToast() {
		BaseActivity activity = (BaseActivity) getActivity();
		Resources resources = activity.getResources();
		int index = BaseMethod.getRandomValue(1, 15);
		String resName = "iv_enter_icon_" + index;
		int resId = resources.getIdentifier(resName, "drawable", activity.getPackageName());
		String[] texts = new String[] { "主人，欢迎光临我的世界", "主人，欢迎来到我的地盘", "前方高能，注意隐蔽", "Welcome back!", "我的地盘我做主" };
		int textIndex = index % texts.length;
		activity.showEnterToast(texts[textIndex], resId);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		startLauncherAnimation();
		Init();
		showEnterToast();
	}

	public void onResume() {
		super.onResume();
		onTabChanged("tab1");
	}

	public void onClick(View view) {
		String currentTag = tabHost.getCurrentTabTag();
		BaseFragment currentFragment = (BaseFragment) getFragmentManager().findFragmentByTag(currentTag);
		currentFragment.onClick(view);
	}

	public void onTabChanged(String tabTag) {
		notifyViewPagerChanged();
		rotateTabIcon();
		rippleTabText();
		showTabToast();
	}

	private void notifyViewPagerChanged() {
		int position = tabHost.getCurrentTab();
		viewPager.setCurrentItem(position);
	}

	/**
	 * 底部Tab图标旋转或抖动<br/>
	 * 旋转： R.anim.tab_rotate<br/>
	 * 抖动： R.anim.tab_shake<br/>
	 */
	private void rotateTabIcon() {
		View tab_iv_icon = tabHost.getCurrentTabView().findViewById(R.id.tab_iv_icon);
		Animation animation_tab = AnimationUtils.loadAnimation(getActivity(), R.anim.tab_rotate);
		animation_tab.reset();
		animation_tab.setFillEnabled(true);
		animation_tab.setFillAfter(true);
		stopRotateTabIcon();
		startRotateTabIcon(tab_iv_icon, animation_tab);
	}

	/**
	 * 启动底部Tab图标抖动或旋转
	 */
	private void startRotateTabIcon(View tab_iv_icon, Animation animation) {
		tab_iv_icon.startAnimation(animation);
	}

	/**
	 * 停止底部Tab图标抖动或旋转
	 */
	private void stopRotateTabIcon() {
		TabWidget tabWidget = tabHost.getTabWidget();
		for (int index = 0; index < tabWidget.getChildCount(); index++) {
			tabWidget.getChildAt(index).findViewById(R.id.tab_iv_icon).clearAnimation();
		}
	}

	/**
	 * 控制tab_indicator中tab_toast的显示与隐藏<br/>
	 * 
	 */
	private void showTabToast() {
		if (!showTabToast) {
			return;
		}
		TextView selectedTabToast = (TextView) tabHost.getCurrentTabView().findViewById(R.id.tab_toast);
		int count = tabHost.getTabWidget().getChildCount();
		for (int index = 0; index < count; index++) {
			TextView currentTabToast = (TextView) tabHost.getTabWidget().getChildTabViewAt(index)
					.findViewById(R.id.tab_toast);
			if (currentTabToast == selectedTabToast) {
				currentTabToast.setVisibility(View.VISIBLE);
			} else {
				currentTabToast.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 底部Tab文本产生波纹
	 */
	private void rippleTabText() {
		TitanicTextView tab_tv_text = (TitanicTextView) tabHost.getCurrentTabView().findViewById(R.id.tab_tv_text);
		tab_tv_text.setTextColor(getActivity().getResources().getColorStateList(R.drawable.tab_textcolor));
		stopRippleTabText();
		startRippleTabText(tab_tv_text);
	}

	/**
	 * 启动底部Tab文本产生波纹
	 */
	private void startRippleTabText(TitanicTextView textView) {
		Titanic.getInstance().start(textView);
	}

	/**
	 * 停止底部Tab文本产生波纹
	 */
	private void stopRippleTabText() {
		Titanic.getInstance().cancel();
	}

	public void onStop() {
		super.onStop();
		stopRotateTabIcon();
		stopRippleTabText();
	}

	public void showTabWidget() {
		tabHost.setVisibility(View.VISIBLE);
	}

	public void hideTabWidget() {
		tabHost.setVisibility(View.GONE);
	}

	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		String currentTag = tabHost.getCurrentTabTag();
		BaseFragment currentFragment = (BaseFragment) getFragmentManager().findFragmentByTag(currentTag);
		currentFragment.onActivityResult(arg0, arg1, arg2);
	}

	private qlsl.androiddesign.listener.OnPageChangeListener pageListener = new qlsl.androiddesign.listener.OnPageChangeListener() {

		public void onPageSelected(int index) {
			tabHost.setCurrentTab(index);
		};
	};

}