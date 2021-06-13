package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.ListView;
import qlsl.androiddesign.activity.commonactivity.ToolColorActivity;
import qlsl.androiddesign.activity.commonactivity.ToolPagerActivity;
import qlsl.androiddesign.activity.commonactivity.ToolSettingActivity;
import qlsl.androiddesign.activity.commonactivity.ToolSpeechActivity;
import qlsl.androiddesign.adapter.commonadapter.ToolSettingAdapter;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 实用工具中的设置<br/>
 * 
 */
public class ToolSettingView extends FunctionView<ToolSettingActivity> {

	private ListView listView;

	public ToolSettingView(ToolSettingActivity activity) {
		super(activity);
		setContentView(R.layout.activity_tool_setting);
	}

	protected void initView(View view) {
		setTitle("基本设置");
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
		case R.id.list_item:
			doClickListItem(view);
			break;

		}
	}

	private void setListViewData() {
		int[] icons = new int[] { R.drawable.iv_qchc_icon,
				R.drawable.iv_xtsz_icon, R.drawable.iv_wdxc_icon };
		String[] names = new String[] { "文本设置", "语音设置", "分页设置" };
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int index = 0; index < names.length - 1; index++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icons[index]);
			map.put("text", names[index]);
			list.add(map);
		}
		ToolSettingAdapter adapter = new ToolSettingAdapter(activity, list);
		listView.setAdapter(adapter);
	}

	private void doClickListItem(View view) {
		int position = listView.getPositionForView(view);
		switch (position) {
		case 0:
			startActivity(ToolColorActivity.class);
			break;
		case 1:
			startActivity(ToolSpeechActivity.class);
			break;
		case 2:
			startActivity(ToolPagerActivity.class);
			break;
		}
	}

}
