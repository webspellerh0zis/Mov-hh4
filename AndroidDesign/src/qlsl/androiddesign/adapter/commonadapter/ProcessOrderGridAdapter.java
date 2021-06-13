package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class ProcessOrderGridAdapter extends BaseAdapter<String> {

	/**
	 * 用于选中后重绘<br/>
	 */
	private ListView listView;

	/**
	 * 选中位置
	 */
	private int selectPosition;

	public ProcessOrderGridAdapter(BaseActivity activity, ListView listView,
			List<String> list) {
		super(activity, list);
		this.listView = listView;
	}

	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_process_order);

		TextView tv_filter = getView(convertView, R.id.tv_filter);

		String value = getItem(position);

		tv_filter.setText(value);

		if (position == selectPosition) {
			convertView.setActivated(true);
		} else {
			convertView.setActivated(false);
		}

		tv_filter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				selectPosition = position;
				listView.invalidateViews();
			}
		});

		return convertView;
	}

	public int getSelectPosition() {
		return selectPosition;
	}
}
