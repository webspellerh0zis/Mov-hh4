package qlsl.androiddesign.view.baseview;

import java.io.File;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.MemberLoginActivity;
import qlsl.androiddesign.activity.commonactivity.MemberRegistActivity;
import qlsl.androiddesign.activity.commonactivity.TestActivity;
import qlsl.androiddesign.service.baseservice.BaseService;
import qlsl.androiddesign.util.commonutil.DensityUtils;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FunctionView子类的基类<br/>
 * 用于将BaseActivity(BaseFragment)事件处理(网络事件和界面事件)分离化<br/>
 * BaseActivity(BaseFragment)子类处理网络事件，FunctionView子类处理界面事件<br/>
 * <br/>
 * 周期函数说明：<br/>
 * 周期函数包括：onResume(),onPause(),onStop(),onDestroy()<br/>
 * 在FunctionView的子类中选择性覆盖<br/>
 * 用于处理BaseActivity子类和BaseFragment子类中的周期函数<br/>
 * 即周期在FunctionView的子类中去管理，不在BaseActivity子类及BaseFragment子类中去管理<br/>
 * 在持有FunctionView对象的BaseActivity或BaseFragment子类中，不论是否覆盖周期函数，
 * FunctionView子类周期函数都会被调用<br/>
 * 调用时间：<br/>
 * 当BaseActivity或BaseFragment中的对应周期函数隐性触发时调用<br/>
 * 
 */
public abstract class FunctionView<T extends BaseActivity> extends
		AbFunctionView<T> implements DialogInterface.OnClickListener {

	/**
	 * 标题栏公用标题控件
	 */
	private TextView tv_title;

	/**
	 * 标题栏公用返回按钮，右边按钮
	 */
	private Button btn_back, btn_right;

	/**
	 * 内容栏公用进度条控件
	 */
	private View progressBar;

	public FunctionView(T activity) {
		super(activity);
	}

	/**
	 * 设置所有BaseActivity子类的根布局为layout中的common_root.xml布局文件<br/>
	 */
	private void setRootView() {
		view = (ViewGroup) activity.getLayoutInflater().inflate(
				R.layout.common_root_fit, null);
		activity.setContentView(view);
	}

	/**
	 * 设置BaseActivity子类的内容布局
	 */
	public void setContentView(int resource) {
		setRootView();
		View contentView = activity.getLayoutInflater().inflate(resource, null);
		((ViewGroup) view.findViewById(R.id.viewgroup_common_content)).addView(
				contentView, getLayoutParams());
		init("activity");
	}

	/**
	 * 设置BaseFragment子类的根布局和内容布局<br/>
	 * 根布局在BaseFragment中设置为layout中的common_root.xml布局文件<br/>
	 */
	public void setContentView(ViewGroup rootView, ViewGroup contentView) {
		view = rootView;
		((ViewGroup) view.findViewById(R.id.viewgroup_common_content)).addView(
				contentView, getLayoutParams());
		init("fragment");
	}

	private LayoutParams getLayoutParams() {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return params;
	}

	/**
	 * 查找和添加公有控件，初始化数据及监听<br/>
	 */
	private void init(String type) {
		initCommonView(type);
		initView(view);
		initData();
		initListener();
	}

	/**
	 * 查找公有控件<br/>
	 */
	private void initCommonView(String type) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		btn_back = (Button) view.findViewById(R.id.btn_back);
		btn_right = (Button) view.findViewById(R.id.btn_right);
		progressBar = view.findViewById(R.id.viewgroup_progressbar);
		initDefaultSetting(type);
		initCommonListener(btn_back);

	}

	/**
	 * 根据入口的不同(Activity,Fragment)初始化默认的标题栏显示规则
	 */
	private void initDefaultSetting(String type) {
		if (type.equals("activity")) {
			showBackButton();
			hideRightButton();
		} else if (type.equals("fragment")) {
			hideBackButton();
			hideRightButton();
		}
	}

	/**
	 * 初始化公有监听<br/>
	 */
	private void initCommonListener(View btn_back) {
		if (btn_back != null) {
			btn_back.setOnClickListener(new BackButtonClickListener());
		}
	}

	/**
	 * 显示标题栏<br/>
	 */
	public void showTitleBar() {
		view.findViewById(R.id.viewgroup_common_title).setVisibility(
				View.VISIBLE);
	}

	/**
	 * 隐藏标题栏<br/>
	 */
	public void hideTitleBar() {
		view.findViewById(R.id.viewgroup_common_title).setVisibility(View.GONE);
	}

	/**
	 * 隐藏标题栏和通知栏<br/>
	 */
	public void hideTitleAndImmersionBar() {
		view.findViewById(R.id.viewgroup_common_title).setVisibility(View.GONE);
		setFitsSystemWindows(false);
	}

	/**
	 * 隐藏标题栏和通知栏,并且根据是否支持沉浸式自适应内上边距<br/>
	 */
	public void hideTitleImmersionBarAndFitPadding() {
		hideTitleAndImmersionBar();
		View customTitleView = findViewById(R.id.customTitleView);
		int dp2px = DensityUtils.dp2px(activity, 5);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			customTitleView.setPadding(dp2px,
					DensityUtils.dip2px(activity, activity.getResources()
							.getDimension(R.dimen.dimen_48_dip)), dp2px, dp2px);
		} else {
			customTitleView.setPadding(dp2px, dp2px, dp2px, dp2px);
		}
	}

	/**
	 * 设置通知栏背景色<br/>
	 */
	public void setImmersionBarColor(int color) {
		view.setBackgroundColor(color);
	}

	/**
	 * 全屏/非全屏切换<br/>
	 */
	public void reverseTitleBar() {
		View titleBar = view.findViewById(R.id.viewgroup_common_title);
		if (titleBar.getVisibility() == View.VISIBLE) {
			titleBar.setVisibility(View.GONE);
			setFitsSystemWindows(false);
			hideSystemUI();
			view.setBackgroundResource(0);
		} else {
			titleBar.setVisibility(View.VISIBLE);
			setFitsSystemWindows(true);
			showSystemUI();
			view.setBackgroundResource(R.drawable.common_title_view);
		}
	}

	@SuppressLint("InlinedApi")
	private void hideSystemUI() {
		activity.getWindow()
				.getDecorView()
				.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
								| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_FULLSCREEN
								| View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	@SuppressLint("InlinedApi")
	private void showSystemUI() {
		activity.getWindow()
				.getDecorView()
				.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	/**
	 * 设置标题栏的标题
	 */
	public void setTitle(String name) {
		tv_title.setText(name);
	}

	public void setTitleBarBackgroundResource(int resid) {
		findViewById(R.id.viewgroup_common_title).setBackgroundResource(resid);
		view.setBackgroundResource(resid);
	}

	public void setTitleBarBackgroundColor(int color) {
		findViewById(R.id.viewgroup_common_title).setBackgroundColor(color);
		view.setBackgroundColor(color);
	}

	public void setTitleBarTextColor(int color) {
		tv_title.setTextColor(color);
		btn_right.setTextColor(color);
	}

	public void setTitleBarTextSize(float size) {
		tv_title.setTextSize(size);
	}

	public void setFitsSystemWindows(boolean fit) {
		view.setFitsSystemWindows(fit);
	}

	public void setContentBarBackgroundResource(int resid) {
		view.findViewById(R.id.viewgroup_common_content).setBackgroundResource(
				resid);
	}

	public void setContentBarBackgroundColor(int color) {
		view.findViewById(R.id.viewgroup_common_content).setBackgroundColor(
				color);
	}

	public void setProgressBarText(String text) {
		((TextView) progressBar.findViewById(R.id.tv_progressbar))
				.setText(text);
	}

	public void resetProgressBarText() {
		((TextView) progressBar.findViewById(R.id.tv_progressbar))
				.setText(activity.getResources().getString(
						R.string.progressbar_text));
	}

	/**
	 * 设置标题栏返回按钮的资源图片
	 */
	public void setBackButtonResource(int resid) {
		btn_back.setBackgroundResource(resid);
	}

	/**
	 * 设置标题栏返回按钮的文本
	 */
	public void setBackButtonText(String text) {
		btn_back.setBackgroundColor(activity.getResources().getColor(
				R.color.transparent));
		btn_back.setText(text);
	}

	/**
	 * 设置标题栏右边按钮的资源图片
	 */
	public void setRightButtonResource(int resid) {
		btn_right.setBackgroundResource(resid);
	}

	/**
	 * 设置标题栏右边按钮的文本
	 */
	public void setRightButtonText(String text) {
		btn_right.setBackgroundColor(activity.getResources().getColor(
				R.color.transparent));
		btn_right.setText(text);
	}

	/**
	 * 设置标题栏右边按钮的文本和图片
	 */
	public void setRightButtonText(String text, int leftResid) {
		btn_right.setBackgroundColor(activity.getResources().getColor(
				R.color.transparent));
		btn_right.setText(text);
		Drawable leftDrawable = activity.getResources().getDrawable(leftResid);
		btn_right.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null,
				null, null);
	}

	/**
	 * 在标题栏右边按钮左边再加上一个图片控件
	 */
	public void addBorderView(int resid) {
		View view_border = activity.getLayoutInflater().inflate(
				R.layout.imageview_border, null);
		view_border.setBackgroundResource(resid);
		ViewGroup titleView = (ViewGroup) view.findViewById(R.id.title_view);
		titleView.addView(view_border, titleView.getChildCount() - 1);
	}

	/**
	 * 在标题栏右边文本控件左边再加上一个文本控件
	 */
	public void addBorderView(String text) {
		View view_border = activity.getLayoutInflater().inflate(
				R.layout.textview_border, null);
		((TextView) view_border.findViewById(R.id.iv_border)).setText(text);
		ViewGroup titleView = (ViewGroup) view.findViewById(R.id.title_view);
		titleView.addView(view_border, titleView.getChildCount() - 1);
	}

	/**
	 * 获取内容栏根布局<br/>
	 */
	public ViewGroup getContentView() {
		ViewGroup parent = (ViewGroup) view
				.findViewById(R.id.viewgroup_common_content);
		return (ViewGroup) parent.getChildAt(1);
	}

	/**
	 * 获取RightButton<br/>
	 */
	public Button getRightButton() {
		return btn_right;
	}

	/**
	 * 获取BorderView<br/>
	 */
	public View getBorderView() {
		return view.findViewById(R.id.iv_border);
	}

	/**
	 * 显示标题栏的返回按钮
	 */
	public void showBackButton() {
		btn_back.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏标题栏的返回按钮
	 */
	public void hideBackButton() {
		btn_back.setVisibility(View.INVISIBLE);
	}

	/**
	 * 显示标题栏的右边按钮
	 */
	public void showRightButton() {
		btn_right.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏标题栏的右边按钮
	 */
	public void hideRightButton() {
		btn_right.setVisibility(View.INVISIBLE);
	}

	/**
	 * 显示进度条及隐藏输入法<br/>
	 */
	public void showProgressBar() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				hideSoftInput();
				progressBar.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * 显示输入法<br/>
	 */
	public void showSoftInput(EditText et_target) {
		et_target.requestFocus();
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et_target, 0);
	}

	/**
	 * 隐藏输入法<br/>
	 */
	public void hideSoftInput() {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	/**
	 * 隐藏进度条<br/>
	 */
	public void hideProgressBar() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				progressBar.setVisibility(View.GONE);
			}
		});
	}

	public void startActivity(Class<? extends Activity> destClass) {
		activity.startActivity(destClass);

	}

	public void startActivity(Intent intent) {
		activity.startActivity(intent);

	}

	public void startActivity(Class<? extends Activity> destClass,
			int enterAnim, int exitAnim) {
		activity.startActivity(destClass, enterAnim, exitAnim);

	}

	public void startActivity(Intent intent, int enterAnim, int exitAnim) {
		activity.startActivity(intent, enterAnim, exitAnim);

	}

	public void startActivityForResult(Class<? extends Activity> destClass,
			int requestCode) {
		activity.startActivityForResult(destClass, requestCode);

	}

	public void startActivityForResult(Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);

	}

	public void startTestActivity(String password) {
		if (password.equals("test")) {
			startActivity(TestActivity.class);
		}
	}

	public void startService(Class<? extends BaseService> destClass) {
		activity.startService(destClass);
	}

	public ComponentName startService(Intent intent) {
		return activity.startService(intent);
	}

	public void stopService(Class<? extends BaseService> destClass) {
		activity.stopService(destClass);
	}

	public void showToast(String text) {
		activity.showToast(text);
	}

	public void showToastOnUiThread(String text) {
		activity.showToastOnUiThread(text);
	}

	public void showLongToast(String text) {
		activity.showLongToast(text);
	}

	public void showShortToast(String text) {
		activity.showShortToast(text);
	}

	public void showScreenShotToast(Bitmap bitmap) {
		activity.showScreenShotToast(bitmap);
	}

	public void saveScreenShotImage(Bitmap bitmap) {
		activity.saveScreenShotImage(bitmap);
	}

	public File saveBitmapToFile(Bitmap bitmap) {
		return activity.saveBitmapToFile(bitmap);
	}

	public void cropPhoto(String path) {
		activity.cropPhoto(path);
	}

	public void cropPhoto(String path, int aspectX, int aspectY, int outputX,
			int outputY) {
		activity.cropPhoto(path, aspectX, aspectY, outputX, outputY);
	}

	@SuppressWarnings("unchecked")
	public <V extends View> V findViewById(int id) {
		V currentView = (V) view.findViewById(id);
		return currentView;
	}

        /**
	 * 查找指定序号的同名id控件<br/>
	 * 序号从1开始<br/>
	 */
	public <V extends View> V findViewById(int id, int order) {
		return findViewById(view, id, order, new AtomicInteger(0));
	}

        /**
	 * 查找指定序号的同名id控件<br/>
	 * 序号从1开始<br/>
	 */
	public <V extends View> V findViewById(ViewGroup rootGroup, int id, int order) {
		return findViewById(rootGroup, id, order, new AtomicInteger(0));
	}

	@SuppressWarnings("unchecked")
	private <V extends View> V findViewById(ViewGroup rootGroup, int id, int order, AtomicInteger order_num) {
		for (int childIndex = 0, childCount = rootGroup.getChildCount(); childIndex < childCount; childIndex++) {
			View childView = rootGroup.getChildAt(childIndex);
			if (childView.getId() == id) {
				if (order_num.incrementAndGet() == order) {
					return (V) childView;
				}
			}
			if (childView instanceof ViewGroup) {
				View resultView = findViewById((ViewGroup) childView, id, order, order_num);
				if (resultView == null) {
					continue;
				} else {
					return (V) resultView;
				}
			}
		}
		return null;
	}

	public void setRefresh(boolean refresh) {
		Intent intent = new Intent();
		intent.putExtra("refresh", refresh);
		activity.setResult(0, intent);
	}

	public boolean hasRefresh(Intent intent) {
		if (intent != null && intent.getBooleanExtra("refresh", false)) {
			return true;
		}
		return false;
	}

        public boolean isEmpty(CharSequence src) {
		return TextUtils.isEmpty(src);
	}

	/**
	 * 标题栏返回按钮监听类<br/>
	 * 
	 */
	private class BackButtonClickListener implements OnClickListener {

		public void onClick(View view) {
			activity.finish();
		}

	}

	/**
	 * 重置返回按钮的点击事件<br/>
	 */
	public void setBackButtonClickListener(OnClickListener onClickListener) {
		btn_back.setOnClickListener(onClickListener);
	}

	/**
	 * 重置右边按钮的点击事件<br/>
	 */
	public void setRightButtonClickListener(OnClickListener onClickListener) {
		btn_right.setOnClickListener(onClickListener);
	}

	/**
	 * 周期函数<br/>
	 * 恢复可视化状态<br/>
	 */
	public void onRestart() {

	}

	/**
	 * 周期函数<br/>
	 * 可视化状态<br/>
	 */
	public void onStart() {

	}

	/**
	 * 周期函数<br/>
	 * 界面可操作状态<br/>
	 */
	public void onResume() {

	}

	/**
	 * 周期函数<br/>
	 * 暂停状态<br/>
	 */
	public void onPause() {

	}

	/**
	 * 周期函数<br/>
	 * 停止状态<br/>
	 */
	public void onStop() {

	}

	/**
	 * 周期函数<br/>
	 * 销毁状态<br/>
	 */
	public void onDestroy() {

	}

	/**
	 * 周期函数<br/>
	 * 保存状态<br/>
	 */
	public void onSaveInstanceState(Bundle outState) {

	}

	/**
	 * 回调函数<br/>
	 * 返回Activity<br/>
	 */
	public void onActivityResult(int arg0, int arg1, Intent arg2) {

	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			startActivity(MemberRegistActivity.class);
		} else if (which == -2) {
			startActivity(MemberLoginActivity.class);
		}
	}

	/**
	 * 显示数据:<br/>
	 * 用于BaseActivity(BaseFragment)子类对FunctionView子类的网络数据传递<br/>
	 * 待传递对象：onNetWorkComplete函数中的object对象<br/>
	 * 待接收对象：showData函数中的t容器对象<br/>
	 */
	@SuppressWarnings("hiding")
	public abstract <T extends Object> void showData(T... t);

	/**
	 * 提示没有数据:<br/>
	 * 用于BaseActivity(BaseFragment)子类对FunctionView子类的网络返回无数据提示<br/>
	 * 待传递对象：onNetWorkComplete函数中的object对象<br/>
	 * 待接收对象：showNoData函数中的t容器对象<br/>
	 */
	@SuppressWarnings("hiding")
	public abstract <T extends Object> void showNoData(T... t);

	/**
	 * 控件的onClickListener的触发函数<br/>
	 * 对应控件的属性：onClick="onClick"<br/>
	 */
	public abstract void onClick(View view);

}
