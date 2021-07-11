package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.ListView;
import qlsl.androiddesign.activity.commonactivity.VersionActivity;
import qlsl.androiddesign.adapter.commonadapter.VersionAdapter;
import qlsl.androiddesign.util.commonutil.AppUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

public class VersionView extends FunctionView<VersionActivity> {

	private ListView listView;

	public VersionView(VersionActivity activity) {
		super(activity);
		setContentView(R.layout.activity_version);
	}

	protected void initView(View view) {
		setTitle("版本信息");
		setRightButtonText("历史记录");
		showRightButton();
		listView = (ListView) view.findViewById(R.id.listView);
	}

	protected void initData() {
		setListViewData();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_right:
			doClickRightButton();
			break;
		}
	}

	private void doClickRightButton() {
		showToast("正在开发中");
	}

	private void setListViewData() {
		String[] names = new String[] { "appName", "package", "versionCode",
				"versionName", "更新人", "发布人", "开发团队", "更新时间", "发布时间", "更新内容",
				"更新描述", "安装包大小" };
		String[] values = new String[] {
				AppUtils.getAppName(activity),
				activity.getPackageName(),
				AppUtils.getVersionCode(activity),
				AppUtils.getVersionName(activity),
				"qlslylq",
				"qlslylq",
				"qlslylq",
				"2014-12-30——2016-07-03",
				"2016-07-03",
				"初始上传",
				"16.1MB" };
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int index = 0; index < names.length; index++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", names[index]);
			map.put("value", values[index]);
			list.add(map);
		}
		VersionAdapter adapter = new VersionAdapter(activity, list);
		listView.setAdapter(adapter);
	}

}
