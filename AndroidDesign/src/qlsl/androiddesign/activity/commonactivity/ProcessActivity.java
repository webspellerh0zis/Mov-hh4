package qlsl.androiddesign.activity.commonactivity;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshListView;
import qlsl.androiddesign.manager.ScrollHideManager;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ProcessView;

public class ProcessActivity extends BaseActivity {

	private ProcessView functionView;

	private ScrollHideManager manager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ProcessView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onOtherSucceed(String method, Object values) {
		if (method.equals("queryProcessList")) {
			functionView.showData(values);
		} else if (method.equals("queryProcessBySearchKey")) {
			functionView.showData(values);
		} else if (method.equals("queryProcessByOrderKey")) {
			functionView.showData(values);
		}
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (manager == null) {
			manager = new ScrollHideManager((PullToRefreshListView) findViewById(R.id.refreshListView),
					findViewById(R.id.searchView));
		}
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("操作");
		menu.add(0, 0, 0, "杀掉进程");
		menu.add(0, 1, 0, "查看详情");
	}

	public boolean onContextItemSelected(MenuItem item) {
		functionView.onContextItemSelected(item);
		return super.onContextItemSelected(item);
	}

}
