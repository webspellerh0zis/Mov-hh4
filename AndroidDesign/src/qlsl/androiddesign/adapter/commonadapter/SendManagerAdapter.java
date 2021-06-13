package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class SendManagerAdapter
		extends
			BaseAdapter<Map<String, Object>> {

	public SendManagerAdapter(BaseActivity activity,
			List<Map<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_send_manager);
		TextView tv_name = getView(convertView, R.id.tv_name);
		TextView tv_email = getView(convertView, R.id.tv_email);

		String name = (String) getItem(position).get("name");
		String email = (String) getItem(position).get("email");

		tv_name.setText(name);
		tv_email.setText(email);

		return convertView;
	}

}
