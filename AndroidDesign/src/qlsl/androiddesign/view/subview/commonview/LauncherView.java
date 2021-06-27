package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import qlsl.androiddesign.activity.commonactivity.InstanceActivity;
import qlsl.androiddesign.activity.commonactivity.LauncherActivity;
import qlsl.androiddesign.util.commonutil.SPUtils;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.indicatorview.ImageIndicatorView.OnItemChangeListener;
import qlsl.androiddesign.view.indicatorview.IndicatorView;

/**
 * 引导页<br/>
 */
public class LauncherView extends FunctionView<LauncherActivity> implements OnItemChangeListener {

	/**
	 * 引导页
	 */
	private IndicatorView indicatorView;

	/**
	 * 启动
	 */
	private View iv_launcher;

	public LauncherView(LauncherActivity activity) {
		super(activity);
		setContentView(R.layout.activity_launcher);
	}

	protected void initView(View view) {
		hideTitleBar();
		setFitsSystemWindows(false);
		indicatorView = (IndicatorView) view.findViewById(R.id.net_indicate_view);
		indicatorView.setIndicateStyle(IndicatorView.INDICATE_ROUND_STYLE);
		iv_launcher = view.findViewById(R.id.iv_launcher);
	}

	protected void initData() {
		Boolean isFirstStart = (Boolean) SPUtils.get(activity, SPUtils.IS_FIRST_START, true);
		if (isFirstStart) {
			Integer[] resArray = new Integer[] { R.drawable.suggest_01, R.drawable.suggest_02, R.drawable.suggest_03 };
			indicatorView.setupLayoutByDrawable(resArray);
			indicatorView.show();
		} else {
			startActivity(InstanceActivity.class);
			activity.finish();
		}

	}

	protected void initListener() {
		indicatorView.setOnItemChangeListener(this);
	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_launcher:
			doClickLauncherView();
			break;
		}
	}

	private void doClickLauncherView() {
		SPUtils.put(activity, SPUtils.IS_FIRST_START, false);
		startActivity(InstanceActivity.class);
		activity.finish();
	}

	public void onPosition(int position, int totalCount) {
		if (position == totalCount - 1) {
			iv_launcher.setVisibility(View.VISIBLE);
		} else {
			iv_launcher.setVisibility(View.GONE);
		}
	}

}
