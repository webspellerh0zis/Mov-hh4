package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Debug;
import android.view.View;
import android.widget.ListView;
import qlsl.androiddesign.activity.commonactivity.MemoryActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.adapter.commonadapter.MemoryAdapter;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.quickaction.ActionItem;
import qlsl.androiddesign.view.quickaction.QuickAction;
import qlsl.androiddesign.view.quickaction.QuickAction.OnActionItemClickListener;

/**
 * 内存使用页<br/>
 *
 */
public class MemoryView extends FunctionView<MemoryActivity>implements OnActionItemClickListener {

	private ListView listView;

	private QuickAction quickAction;

	private final String[] actionItemNames = new String[] { "申请内存", "释放内存" };

	private final int[] actionItemIcons = new int[] { R.drawable.app_icon_default, R.drawable.app_icon_default };

	public MemoryView(MemoryActivity activity) {
		super(activity);
		setContentView(R.layout.activity_memory);
	}

	protected void initView(View view) {
		setTitle("内存使用");
		setRightButtonResource(R.drawable.btn_menu);
		showRightButton();
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		listView = findViewById(R.id.listView);
		quickAction = new QuickAction(activity, QuickAction.VERTICAL);
		quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
		for (int index = 0; index < actionItemNames.length; index++) {
			ActionItem actionItem = new ActionItem(index, actionItemNames[index],
					activity.getResources().getDrawable(actionItemIcons[index]));
			actionItem.setSticky(true);
			quickAction.addActionItem(actionItem);
		}
	}

	protected void initData() {
		setListViewData();

	}

	protected void initListener() {
		quickAction.setOnActionItemClickListener(this);
	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_right:
			doClickRightView(view);
			break;

		}
	}

	@SuppressLint("NewApi")
	private void setListViewData() {
		ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		Debug.MemoryInfo debugMemoryInfo = new Debug.MemoryInfo();
		Debug.getMemoryInfo(debugMemoryInfo);

		float rate = 1024 * 1024;
		float debugRate = 1024;
		String unit = "M";

		String appMaxLimit = activityManager.getMemoryClass() * 1000000f / rate + unit;
		String appTotalMem = memoryInfo.totalMem / rate + unit;
		String appAvailMem = memoryInfo.availMem / rate + unit;
		String appThreshold = memoryInfo.threshold / rate + unit;
		String appLowMemory = memoryInfo.lowMemory ? "是" : "否";

		String totalPss = debugMemoryInfo.getTotalPss() / debugRate + unit;
		String totalPrivateDirty = debugMemoryInfo.getTotalPrivateDirty() / debugRate + unit;
		String totalSharedDirty = debugMemoryInfo.getTotalSharedDirty() / debugRate + unit;
		String totalSwappablePss = "未知";
		String totalPrivateClean = "未知";
		String totalSharedClean = "未知";
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			totalSwappablePss = debugMemoryInfo.getTotalSwappablePss() / debugRate + unit;
			totalPrivateClean = debugMemoryInfo.getTotalPrivateClean() / debugRate + unit;
			totalSharedClean = debugMemoryInfo.getTotalSharedClean() / debugRate + unit;
		}

		String runMaxMemory = Runtime.getRuntime().maxMemory() / rate + unit;
		String runTotalMemory = Runtime.getRuntime().totalMemory() / rate + unit;
		String runFreeMemory = Runtime.getRuntime().freeMemory() / rate + unit;

		String nativeHeapSize = Debug.getNativeHeapSize() / rate + unit;
		String nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() / rate + unit;
		String nativeHeapFreeSize = Debug.getNativeHeapFreeSize() / rate + unit;

		String[] values = new String[] { appMaxLimit, appTotalMem, appAvailMem, appThreshold, appLowMemory, totalPss,
				totalSwappablePss, totalPrivateClean, totalPrivateDirty, totalSharedClean, totalSharedDirty,
				runMaxMemory, runTotalMemory, runFreeMemory, nativeHeapSize, nativeHeapAllocatedSize,
				nativeHeapFreeSize };

		BaseAdapter<?> adapter = (BaseAdapter<?>) listView.getAdapter();
		if (adapter == null) {
			String[] names = new String[] { "appMaxLimit", "appTotalMem", "appAvailMem", "appThreshold", "appLowMemory",
					"totalPss", "totalSwappablePss", "totalPrivateClean", "totalPrivateDirty", "totalSharedClean",
					"totalSharedDirty", "runMaxMemory", "runTotalMemory", "runFreeMemory", "nativeHeapSize",
					"nativeHeapAllocatedSize", "nativeHeapFreeSize" };

			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			for (int index = 0; index < names.length; index++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", names[index]);
				map.put("value", values[index]);
				list.add(map);
			}
			adapter = new MemoryAdapter(activity, list);
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

	private void doClickRightView(View view) {
		quickAction.show(view);
	}

	public void onItemClick(QuickAction source, int pos, int actionId) {
		switch (pos) {
		case 0:
			@SuppressWarnings("unused")
			Object object = new Object();
			setListViewData();
			break;
		case 1:
			System.gc();
			setListViewData();
			break;
		}
	}

}
