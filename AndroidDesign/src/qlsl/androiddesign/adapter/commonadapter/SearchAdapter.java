package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

/**
 * 搜索适配器<br/>
 */
public class SearchAdapter<T> extends BaseAdapter<T> {

	public SearchAdapter(BaseActivity activity, List<T> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_group_search);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_name = getView(convertView, R.id.tv_name);
		TextView tv_owner = getView(convertView, R.id.tv_owner);
		TextView tv_num = getView(convertView, R.id.tv_num);

		return convertView;
	}
}
