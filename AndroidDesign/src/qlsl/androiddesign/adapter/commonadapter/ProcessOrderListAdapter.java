package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class ProcessOrderListAdapter extends BaseAdapter<HashMap<String, Object>> {

	/**
	 * 用于选中后重绘<br/>
	 */
	private ListView listView;

	public ProcessOrderListAdapter(BaseActivity activity, ListView listView, List<HashMap<String, Object>> list) {
		super(activity, list);
		this.listView = listView;
	}

	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_process_order);

		TextView tv_key = getView(convertView, R.id.tv_key);
		GridView gridView = getView(convertView, R.id.gridView);

		HashMap<String, Object> map = getItem(position);
		String type = (String) map.get("type");
		List<String> value = (List<String>) map.get("value");

		tv_key.setText(type);

		BaseAdapter<String> adapter = (BaseAdapter<String>) gridView.getAdapter();
		if (adapter == null) {
			adapter = new ProcessOrderGridAdapter(activity, listView, value);
			gridView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}

		return convertView;
	}

}
