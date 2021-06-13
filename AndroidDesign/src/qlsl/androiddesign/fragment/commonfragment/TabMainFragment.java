package qlsl.androiddesign.fragment.commonfragment;

import static qlsl.androiddesign.constant.BasicSettingConstant.showTabToast;
import static qlsl.androiddesign.constant.BasicSettingConstant.showTitleTop;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import qlsl.androiddesign.constant.BasicSettingConstant;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.method.BaseMethod;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FragmentTabHost;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.rippleview.Titanic;
import qlsl.androiddesign.view.rippleview.TitanicTextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
/**
 * MainActivity各Fragment生命周期说明：<br/>
 * MainFragment中的onResume,onPause等是在进入或退出MainActivity时执行 <br/>
 * FragmentTab 中的onResume,onPause等是在FragmentTab之间切换时,在进入或退出MainActivity时调用<br/>
 * 
 * 使用FragmentTabHost+Fragment实现选项卡<br/>
 * 不会重复加载Fragment<br/>
 * 嫁接调用的周期函数在FunctionView中<br/>
 */
public class TabMainFragment extends BaseFragment implements OnTabChangeListener {

	private FragmentTabHost tabHost;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("onCreateView：fragment：\n" + getClass().getName());
		return inflater.inflate(R.layout.fragment_main_tab, container, false);
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

	@SuppressLint("InflateParams")
	@SuppressWarnings("unused")
	private void Init() {
		setRetainInstance(true);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		boolean showNoticeBar = sp.getBoolean(getString(R.string.showNoticeBar), BasicSettingConstant.showNoticeBar);
		boolean showActionBar = sp.getBoolean(getString(R.string.showActionBar), BasicSettingConstant.showActionBar);
		if (((!showActionBar && showNoticeBar) || (showActionBar && !showNoticeBar)
				|| (!showActionBar && !showNoticeBar)) && showTitleTop) {
			getActivity().findViewById(R.id.fragment_main_title_bar).setVisibility(View.VISIBLE);
		}

		tabHost = (FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
		tabHost.setup(getActivity(), getFragmentManager(), R.id.real_tab_content);

		View tab1_indicator = getActivity().getLayoutInflater().inflate(R.layout.tab1_indicator, null);
		View tab2_indicator = getActivity().getLayoutInflater().inflate(R.layout.tab2_indicator, null);
		View tab3_indicator = getActivity().getLayoutInflater().inflate(R.layout.tab3_indicator, null);
		View tab4_indicator = getActivity().getLayoutInflater().inflate(R.layout.tab4_indicator, null);
		View tab5_indicator = getActivity().getLayoutInflater().inflate(R.layout.tab5_indicator, null);

		TitanicTextView tab_tv_text1 = (TitanicTextView) tab1_indicator.findViewById(R.id.tab_tv_text);
		TitanicTextView tab_tv_text2 = (TitanicTextView) tab2_indicator.findViewById(R.id.tab_tv_text);
		TitanicTextView tab_tv_text3 = (TitanicTextView) tab3_indicator.findViewById(R.id.tab_tv_text);
		TitanicTextView tab_tv_text4 = (TitanicTextView) tab4_indicator.findViewById(R.id.tab_tv_text);
		TitanicTextView tab_tv_text5 = (TitanicTextView) tab5_indicator.findViewById(R.id.tab_tv_text);

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(tab1_indicator), FragmentTab1.class, null);

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(tab2_indicator), FragmentTab2.class, null);

		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(tab3_indicator), FragmentTab3.class, null);

		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(tab4_indicator), FragmentTab4.class, null);

		tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator(tab5_indicator), FragmentTab5.class, null);

		tabHost.setOnTabChangedListener(this);
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
		rotateTabIcon();
		rippleTabText();
		showTabToast();
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

}