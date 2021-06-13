package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.AboutActivity;
import qlsl.androiddesign.method.AppMethod;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 关于我们页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class AboutView extends FunctionView<AboutActivity> {

	public AboutView(AboutActivity activity) {
		super(activity);
		setContentView(R.layout.activity_about);
	}

	protected void initView(View view) {
		setTitle("关于我们");
	}

	protected void initData() {
		setVersionInfo();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

	private void setVersionInfo() {
		TextView tv_version = findViewById(R.id.tv_version);
		String versionName = AppMethod.getVersionName();
		tv_version.setText("版本  " + versionName);
	}

}
