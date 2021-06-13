package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.filter.LogEmailFilter;

public class LogEmailAdapter extends BaseAdapter<Map<String, Object>> {

	private LogEmailFilter filter;

	private EditText et_email;

	public LogEmailAdapter(BaseActivity activity, List<Map<String, Object>> list) {
		super(activity, list);
	}

	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_log_email);
		TextView tv_name = getView(convertView, R.id.tv_name);
		TextView tv_email = getView(convertView, R.id.tv_email);

		String name = (String) getItem(position).get("name");
		String email = (String) getItem(position).get("email");

		tv_name.setText(name);
		tv_email.setText(email);

		convertView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String email = (String) getItem(position).get("email");
				et_email.setText(email);

			}
		});

		return convertView;
	}

	public Filter getFilter() {
		if (filter == null) {
			filter = new LogEmailFilter(this);
		}
		return filter;
	}

	public <T> void transport(T... t) {
		super.transport(t);
		et_email = (EditText) t[0];
	}

}
