package qlsl.androiddesign.adapter.subadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;


public class CopyAdapter<T> extends BaseAdapter<T> {

	public CopyAdapter(BaseActivity activity, List<T> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
//		convertView = getItemView(convertView, R.layout.listitem_copy);

		TextView tv_text = getView(convertView, R.id.tv_text);

		T t = getItem(position);

		tv_text.setText(t.toString());

		return convertView;
	}
}
