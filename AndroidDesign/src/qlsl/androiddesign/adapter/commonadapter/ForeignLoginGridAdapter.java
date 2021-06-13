package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class ForeignLoginGridAdapter extends BaseAdapter<HashMap<String, Object>> {

	public ForeignLoginGridAdapter(BaseActivity activity, List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_foreign_login);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_text = getView(convertView, R.id.tv_text);

		HashMap<String, Object> map = getItem(position);
		int resId = (Integer) map.get("icon");
		String text = (String) map.get("text");

		iv_icon.setImageResource(resId);
		tv_text.setText(text);

		return convertView;
	}

}
