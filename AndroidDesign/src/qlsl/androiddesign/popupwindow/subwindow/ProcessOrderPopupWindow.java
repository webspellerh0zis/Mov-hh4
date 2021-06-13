package qlsl.androiddesign.popupwindow.subwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.commonadapter.ProcessOrderListAdapter;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;
import qlsl.androiddesign.util.commonutil.AdapterViewUtils;

/**
 * 进程信息排序PopupWindow<br/>
 */
@SuppressLint("ViewConstructor")
public class ProcessOrderPopupWindow extends PopupWindow {

	private ListView listView;

	public ProcessOrderPopupWindow(BaseActivity activity) {
		super(activity);
		initView();
		setPopupWindowAttribute();
		initData();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_order_process, null);
		setContentView(rootView);
		listView = (ListView) rootView.findViewById(R.id.listView);
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimTop);
		ColorDrawable colorDrawable = new ColorDrawable(activity.getResources().getColor(R.color.window_default));
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
	}

	private void initData() {
		setListData();
	}

	private void setListData() {
		String[] keys = new String[] { "PID：", "UID：", "内存：", "进程：" };
		List<List<String>> list_value = new ArrayList<List<String>>();

		List<String> list_row = new ArrayList<String>();
		list_row.add("不限");
		list_row.add("升序");
		list_row.add("降序");
		for (int index = 0; index < keys.length; index++) {
			list_value.add(list_row);
		}

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;
		for (int index = 0; index < keys.length; index++) {
			map = new HashMap<String, Object>();
			map.put("type", keys[index]);
			map.put("value", list_value.get(index));
			list.add(map);
		}
		ProcessOrderListAdapter adapter = new ProcessOrderListAdapter(activity, listView, list);
		listView.setAdapter(adapter);
		AdapterViewUtils.setListViewHeight(listView);
	}

}
