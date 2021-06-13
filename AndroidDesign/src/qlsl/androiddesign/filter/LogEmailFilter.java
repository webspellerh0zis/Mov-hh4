package qlsl.androiddesign.filter;

import java.util.List;
import java.util.Map;

import android.widget.Filter;
import qlsl.androiddesign.adapter.commonadapter.LogEmailAdapter;

public class LogEmailFilter extends Filter {

	private LogEmailAdapter adapter;

	public LogEmailFilter(LogEmailAdapter adapter) {
		this.adapter = adapter;
	}

	protected FilterResults performFiltering(CharSequence charSequence) {
		FilterResults filterResults = new FilterResults();
		List<Map<String, Object>> list = adapter.getList();
		filterResults.values = list;
		filterResults.count = list.size();
		return filterResults;
	}

	protected void publishResults(CharSequence arg0, FilterResults results) {
		if (results.count > 0) {
			adapter.notifyDataSetChanged();
		} else {
			adapter.notifyDataSetInvalidated();
		}

	}

}
