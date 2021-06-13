package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class ProcessDetailAdapter extends BaseAdapter<HashMap<String, Object>> {

	public ProcessDetailAdapter(BaseActivity activity, List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_process_detail);

		TextView tv_name = getView(convertView, R.id.tv_name);
		TextView tv_value = getView(convertView, R.id.tv_value);

		HashMap<String, Object> map = getItem(position);
		String name = (String) map.get("name");
		String value = (String) map.get("value");

		tv_name.setText(name);
		tv_value.setText(value);

		return convertView;
	}
}
