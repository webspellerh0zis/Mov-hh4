package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.List;

import qlsl.androiddesign.activity.commonactivity.ProcessActivity;
import qlsl.androiddesign.activity.commonactivity.ProcessDetailActivity;
import qlsl.androiddesign.adapter.commonadapter.ProcessAdapter;
import qlsl.androiddesign.adapter.commonadapter.ProcessOrderGridAdapter;
import qlsl.androiddesign.entity.commonentity.ProcessInfo;
import qlsl.androiddesign.http.service.commonservice.ProcessService;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.Mode;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshListView;
import qlsl.androiddesign.popupwindow.subwindow.ProcessOrderPopupWindow;
import qlsl.androiddesign.view.baseview.FunctionView;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.qlsl.androiddesign.appname.R;

/**
 * 进程列表页<br/>
 * 
 */
public class ProcessView extends FunctionView<ProcessActivity> implements
		OnRefreshListener<ListView>, OnClickListener {

	private PullToRefreshListView refreshView;

	private ProcessOrderPopupWindow processWindow;

	private List<ProcessInfo> list = new ArrayList<ProcessInfo>();

	public ProcessView(ProcessActivity activity) {
		super(activity);
		setContentView(R.layout.activity_process);
	}

	protected void initView(View view) {
		setTitle("进程信息");
		showRightButton();
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		refreshView = findViewById(R.id.refreshListView);
		refreshView.setMode(Mode.PULL_FROM_START);

		processWindow = new ProcessOrderPopupWindow(activity);
	}

	protected void initData() {
		queryProcessList();
	}

	protected void initListener() {
		refreshView.setOnRefreshListener(this);
		activity.registerForContextMenu(refreshView.getRefreshableView());
	}

	@SuppressWarnings("unchecked")
	public <T> void showData(T... t) {
		if (t[0] instanceof List) {
			List<ProcessInfo> list_result = (List<ProcessInfo>) t[0];
			resetList(list_result);
			notifyDataSetChanged();
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_right:
			doClickRightButton();
			break;
		case R.id.btn_search:
			doClickSearchButton();
			break;
		case R.id.btn_ok:
			doClickOKButton(view);
			break;
		case R.id.tv_empty:
			doClickEmptyView();
			break;
		}
	}

	private void doClickRightButton() {
		processWindow.showAsDropDown(findViewById(R.id.viewgroup_common_title));
	}

	private void doClickSearchButton() {
		EditText et_search = findViewById(R.id.et_search);
		String key = et_search.getText().toString();
		ProcessService.queryProcessBySearchKey(key, this, activity);
	}

	private void doClickOKButton(View view) {
		processWindow.dismiss();
		List<Integer> selectPositions = new ArrayList<Integer>();
		EditText et_search = findViewById(R.id.et_search);
		ViewGroup filter_window = (ViewGroup) view.getParent().getParent();
		ListView listView = (ListView) filter_window
				.findViewById(R.id.listView);
		CheckBox checkBox = (CheckBox) filter_window
				.findViewById(R.id.checkBox);
		String key = checkBox.isChecked() ? et_search.getText().toString() : "";
		for (int index = 0, childCount = listView.getChildCount(); index < childCount; index++) {
			GridView gridView = (GridView) listView.getChildAt(index)
					.findViewById(R.id.gridView);
			ProcessOrderGridAdapter adapter = (ProcessOrderGridAdapter) gridView
					.getAdapter();
			selectPositions.add(adapter.getSelectPosition());
		}
		ProcessService.queryProcessByOrderKey(selectPositions, key, this,
				activity);
	}

	private void doClickEmptyView() {
		queryProcessList();
	}

	private void queryProcessList() {
		ProcessService.queryProcessList(this, activity);
	}

	private void resetList(List<ProcessInfo> list_result) {
		list.clear();
		list.addAll(list_result);
		refreshView.getRefreshableView().smoothScrollToPositionFromTop(0, 0);
	}

	private void notifyDataSetChanged() {
		BaseAdapter adapter = refreshView.getBaseAdapter();
		if (adapter == null) {
			adapter = new ProcessAdapter(activity, list);
			refreshView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		refreshView.setReleaseLabel(0, list.size(), 0, list.size());
		refreshView.onRefreshComplete();
	}

	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		queryProcessList();
	}

	public void onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = refreshView.getRefreshableView().getPositionForView(
				info.targetView);
		ProcessInfo processInfo = (ProcessInfo) refreshView.getAdapter()
				.getItem(position);
		switch (item.getItemId()) {
		case 0:
			if (processInfo.getPid() == android.os.Process.myPid()) {
				android.os.Process.killProcess(android.os.Process.myPid());
			} else {
				ActivityManager manager = (ActivityManager) activity
						.getSystemService(Context.ACTIVITY_SERVICE);
				manager.killBackgroundProcesses(processInfo.getProcessName());
				queryProcessList();
			}
			break;
		case 1:
			Intent intent = new Intent(activity, ProcessDetailActivity.class);
			intent.putExtra("processInfo", processInfo);
			startActivity(intent);
			break;
		}

	}

}
