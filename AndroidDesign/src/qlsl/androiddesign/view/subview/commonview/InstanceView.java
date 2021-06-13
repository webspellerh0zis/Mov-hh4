package qlsl.androiddesign.view.subview.commonview;

import static qlsl.androiddesign.util.commonutil.Log.isDebug;

import org.apache.log4j.Level;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.TextView;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import qlsl.androiddesign.activity.commonactivity.InstanceActivity;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.http.service.commonservice.ToolColorService;
import qlsl.androiddesign.method.AppMethod;
import qlsl.androiddesign.service.subservice.LogFloatService;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;

public class InstanceView extends FunctionView<InstanceActivity> {

	private TextView tv_version;

	public InstanceView(InstanceActivity activity) {
		super(activity);
		setContentView(R.layout.activity_instance);
	}

	protected void initView(View view) {
		hideTitleBar();
		setFitsSystemWindows(false);
		tv_version = (TextView) view.findViewById(R.id.tv_version);
	}

	protected void initData() {
		initLog4j();
		showVersionName();
		startFloatService();
		syncAppData();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		startActivity(MainActivity.class);
	}

	private void initLog4j() {
		String path = Log.getLogPath(activity);
		LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setFileName(path);
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		logConfigurator.setImmediateFlush(true);
		logConfigurator.configure();

		Log.notifyDataSetChanged(activity);

	}

	private void showVersionName() {
		String versionName = AppMethod.getVersionName();
		tv_version.setText("v" + versionName);
	}

	/**
	 * 调试模式下开启悬浮窗服务
	 */
	private void startFloatService() {
		if (isDebug) {
			startService(LogFloatService.class);
		}
	}

	private void syncAppData() {
		ToolColorService.queryToolColor(this, activity);
	}

}
