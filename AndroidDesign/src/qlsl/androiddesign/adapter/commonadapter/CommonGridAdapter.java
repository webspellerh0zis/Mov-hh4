package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlsl.androiddesign.appname.R;

public class CommonGridAdapter extends BaseAdapter<HashMap<String, Object>> {

	public CommonGridAdapter(BaseActivity activity,
			List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.common_griditem);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_name = getView(convertView, R.id.tv_text);

		HashMap<String, Object> map = getItem(position);
		int icon = (Integer) map.get("icon");
		String text = (String) map.get("text");

		iv_icon.setImageResource(icon);
		tv_name.setText(text);

		return convertView;
	}
}
