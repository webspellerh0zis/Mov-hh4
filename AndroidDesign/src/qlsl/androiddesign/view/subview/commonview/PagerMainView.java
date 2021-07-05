package qlsl.androiddesign.view.subview.commonview;

import static qlsl.androiddesign.constant.BasicSettingConstant.showTabToast;
import static qlsl.androiddesign.constant.BasicSettingConstant.showTitleTop;

import java.util.ArrayList;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.adapter.baseadapter.FragmentPagerAdapter;
import qlsl.androiddesign.constant.BasicSettingConstant;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab1;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab2;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab3;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab4;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab5;
import qlsl.androiddesign.fragment.commonfragment.PagerMainFragment;
import qlsl.androiddesign.fragment.commonfragment.TabFragment;
import qlsl.androiddesign.method.BaseMethod;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.commonview.StaticViewPager;
import qlsl.androiddesign.view.rippleview.Titanic;
import qlsl.androiddesign.view.rippleview.TitanicTextView;

public class PagerMainView extends FunctionView<MainActivity> {

	private StaticViewPager viewPager;

	private ViewGroup tabParent;

	private List<BaseFragment> list;

	private int preIndex;

	public PagerMainView(PagerMainFragment fragment, ViewGroup rootView, ViewGroup contentView) {
		super((MainActivity) fragment.getActivity());
		setContentView(rootView, contentView);
	}

	public void onResume() {
		int index = viewPager.getCurrentItem();
		onTabChanged(index);
		resumeTabView(index);
	}

	public void onPause() {
		int index = viewPager.getCurrentItem();
		TabFragment fragment = (TabFragment) list.get(index);
		if (fragment.getFunctionView() != null) {
			fragment.onTabPause();
		}
	}

	public void initView(View view) {
		hideTitleBar();
		viewPager = findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(5);
		tabParent = findViewById(R.id.tab_parent);
	}

	public void initData() {
		init();
	}

	public void initListener() {
		viewPager.setOnPageChangeListener(pageListener);
	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		if (view.getParent() == tabParent) {
			int index = tabParent.indexOfChild(view);
			viewPager.setCurrentItem(index);
		} else {
			int index = viewPager.getCurrentItem();
			list.get(index).onClick(view);
		}
	}

	private void setViewPagerData() {
		list = new ArrayList<BaseFragment>();
		FragmentTab1 fragmentTab1 = new FragmentTab1();
		FragmentTab2 fragmentTab2 = new FragmentTab2();
		FragmentTab3 fragmentTab3 = new FragmentTab3();
		FragmentTab4 fragmentTab4 = new FragmentTab4();
		FragmentTab5 fragmentTab5 = new FragmentTab5();
		list.add(fragmentTab1);
		list.add(fragmentTab2);
		list.add(fragmentTab3);
		list.add(fragmentTab4);
		list.add(fragmentTab5);
		FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager(), list);
		viewPager.setAdapter(pagerAdapter);
	}

	private void startLauncherAnimation() {
		TextView tv_launcher = findViewById(R.id.tv_launcher);
		Animation animation_launcher = new AlphaAnimation(0f, 1f);
		animation_launcher.setDuration(1500);
		animation_launcher.setRepeatMode(Animation.REVERSE);
		animation_launcher.setRepeatCount(Animation.INFINITE);
		tv_launcher.startAnimation(animation_launcher);
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("unused")
	private void init() {
		startLauncherAnimation();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
		boolean showNoticeBar = sp.getBoolean(activity.getString(R.string.showNoticeBar),
				BasicSettingConstant.showNoticeBar);
		boolean showActionBar = sp.getBoolean(activity.getString(R.string.showActionBar),
				BasicSettingConstant.showActionBar);
		if (((!showActionBar && showNoticeBar) || (showActionBar && !showNoticeBar)
				|| (!showActionBar && !showNoticeBar)) && showTitleTop) {
			activity.findViewById(R.id.fragment_main_title_bar).setVisibility(View.VISIBLE);
		}
		setViewPagerData();
		showEnterToast();
	}

	private void showEnterToast() {
		Resources resources = activity.getResources();
		int index = BaseMethod.getRandomValue(1, 15);
		String resName = "iv_enter_icon_" + index;
		int resId = resources.getIdentifier(resName, "drawable", activity.getPackageName());
		String[] texts = new String[] { "主人，欢迎光临我的世界", "主人，欢迎来到我的地盘", "前方高能，注意隐蔽", "Welcome back!", "我的地盘我做主" };
		int textIndex = index % texts.length;
		activity.showEnterToast(texts[textIndex], resId);
	}

	private void onTabChanged(int index) {
		showTabWidget();
		rotateTabIcon(index);
		rippleTabText(index);
		showTabToast(index);
	}

	private void resumeTabView(int index) {
		TabFragment fragment = (TabFragment) list.get(index);
		if (fragment.getFunctionView() != null) {
			fragment.onTabResume();
		}

		if (index == -1) {
			viewPager.setCanScroll(false);
		} else {
			viewPager.setCanScroll(true);
		}
	}

	private void pauseTabView() {
		TabFragment fragment = (TabFragment) list.get(preIndex);
		if (fragment.getFunctionView() != null) {
			fragment.onTabPause();
		}
	}

	/**
	 * 底部Tab图标旋转或抖动<br/>
	 * 旋转： R.anim.tab_rotate<br/>
	 * 抖动： R.anim.tab_shake<br/>
	 */
	private void rotateTabIcon(int index) {
		View tab_iv_icon = tabParent.getChildAt(index).findViewById(R.id.tab_iv_icon);
		Animation animation_tab = AnimationUtils.loadAnimation(activity, R.anim.tab_rotate);
		animation_tab.reset();
		animation_tab.setFillEnabled(true);
		animation_tab.setFillAfter(true);
		stopRotateTabIcon(index);
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
	private void stopRotateTabIcon(int index) {
		for (int childIndex = 0, childCount = tabParent.getChildCount(); childIndex < childCount; childIndex++) {
			View tabView = tabParent.getChildAt(childIndex);
			tabView.findViewById(R.id.tab_iv_icon).clearAnimation();
			if (childIndex == index) {
				tabView.setSelected(true);
			} else {
				tabView.setSelected(false);
			}
		}
	}

	/**
	 * 控制tab_indicator中tab_toast的显示与隐藏<br/>
	 * 
	 */
	private void showTabToast(int index) {
		if (!showTabToast) {
			return;
		}
		for (int childIndex = 0, childCount = tabParent.getChildCount(); childIndex < childCount; childIndex++) {
			TextView currentTabToast = (TextView) tabParent.getChildAt(childIndex).findViewById(R.id.tab_toast);
			if (index == childIndex) {
				currentTabToast.setVisibility(View.VISIBLE);
			} else {
				currentTabToast.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 底部Tab文本产生波纹
	 */
	private void rippleTabText(int index) {
		TitanicTextView tab_tv_text = (TitanicTextView) tabParent.getChildAt(index).findViewById(R.id.tab_tv_text);
		tab_tv_text.setTextColor(activity.getResources().getColorStateList(R.drawable.tab_textcolor));
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
		stopRotateTabIcon(viewPager.getCurrentItem());
		stopRippleTabText();
	}

	public void showTabWidget() {
		tabParent.setVisibility(View.VISIBLE);
	}

	public void hideTabWidget() {
		tabParent.setVisibility(View.GONE);
	}

	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		int position = viewPager.getCurrentItem();
		BaseFragment currentFragment = list.get(position);
		currentFragment.onActivityResult(arg0, arg1, arg2);
	}

	private qlsl.androiddesign.listener.OnPageChangeListener pageListener = new qlsl.androiddesign.listener.OnPageChangeListener() {

		public void onPageSelected(int index) {
			onTabChanged(index);
			pauseTabView();
			resumeTabView(index);
			preIndex = index;
		};
	};

}
