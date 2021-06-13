package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class MemberCenterAdapter extends BaseAdapter<HashMap<String, Object>> {

	public MemberCenterAdapter(BaseActivity activity, List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_member_center);

		TextView tv_name = getView(convertView, R.id.tv_text);

		HashMap<String, Object> map = getItem(position);
		String text = (String) map.get("text");

		tv_name.setText(text);

		return convertView;
	}
}
