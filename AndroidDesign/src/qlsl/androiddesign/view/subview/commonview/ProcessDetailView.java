package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.ListView;
import qlsl.androiddesign.activity.commonactivity.ProcessDetailActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.adapter.commonadapter.ProcessDetailAdapter;
import qlsl.androiddesign.entity.commonentity.ProcessInfo;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 进程详情页<br/>
 * 需要传入的键：processInfo<br/>
 * 传入的值类型： ProcessInfo<br/>
 * 传入的值含义：进程信息<br/>
 * 是否必传 ：是
 */
public class ProcessDetailView extends FunctionView<ProcessDetailActivity> {

	private ListView listView;

	public ProcessDetailView(ProcessDetailActivity activity) {
		super(activity);
		setContentView(R.layout.activity_process_detail);
	}

	protected void initView(View view) {
		setTitle("进程详情");
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		listView = findViewById(R.id.listView);
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

	}

	private void setListViewData() {
		ProcessInfo processInfo = (ProcessInfo) activity.getIntent().getSerializableExtra("processInfo");

		StringBuffer sb = new StringBuffer();
		for (String pkg : processInfo.getPkgList()) {
			sb.append(pkg + "\n");
		}
		sb.deleteCharAt(sb.length() - 1);

		long transmitFlow = processInfo.getTransmitFlow();
		long receiveFlow = processInfo.getReceiveFlow();
		String transmitFlowStr = processInfo.getTransmitFlow() + "";
		String receiveFlowStr = processInfo.getReceiveFlow() + "";
		if (transmitFlow == -1) {
			transmitFlowStr = "未知";
		} else {
			transmitFlowStr = (transmitFlow / 1024f) + "KB";
		}
		if (receiveFlow == -1) {
			receiveFlowStr = "未知";
		} else {
			receiveFlowStr = (receiveFlow / 1024f) + "KB";
		}

		String[] values = new String[] { processInfo.getPid() + "", processInfo.getUid() + "",
				processInfo.getMemSize() / 1024f + "M", processInfo.getProcessName(), sb.toString(),
				processInfo.getImportance() + "", processInfo.getImportanceReasonCode() + "",
				processInfo.getComponentPkg(), processInfo.getComponentClass(),
				processInfo.getImportanceReasonPid() + "", processInfo.getLastTrimLevel(), processInfo.getLru() + "",
				processInfo.getDescribeContents() + "", processInfo.getHashCode() + "", transmitFlowStr,
				receiveFlowStr };

		BaseAdapter<?> adapter = (BaseAdapter<?>) listView.getAdapter();
		if (adapter == null) {
			String[] names = new String[] { "pid", "uid", "memSize", "processName", "pkgList", "importance",
					"importanceReasonCode", "componentPkg", "componentClass", "importanceReasonPid", "lastTrimLevel",
					"lru", "describeContents", "hashCode", "transmitFlow", "receiveFlow" };

			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			for (int index = 0; index < names.length; index++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", names[index]);
				map.put("value", values[index]);
				list.add(map);
			}
			adapter = new ProcessDetailAdapter(activity, list);
			listView.setAdapter(adapter);
		} else {
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) adapter.getList();
			for (int index = 0, size = list.size(); index < size; index++) {
				HashMap<String, Object> map = list.get(index);
				map.put("value", values[index]);
			}
			adapter.notifyDataSetChanged();
		}
	}

}
