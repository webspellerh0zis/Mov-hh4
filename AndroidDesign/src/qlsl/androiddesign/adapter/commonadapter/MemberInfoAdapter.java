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
import qlsl.androiddesign.util.commonutil.ImageUtils;

public class MemberInfoAdapter extends BaseAdapter<HashMap<String, Object>> {

	public MemberInfoAdapter(BaseActivity activity, List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_member_info_new);

		TextView tv_text = getView(convertView, R.id.tv_text);
		TextView tv_value = getView(convertView, R.id.tv_value);
		ImageView iv_value = getView(convertView, R.id.iv_value);

		HashMap<String, Object> map = getItem(position);
		String text = (String) map.get("text");
		String value = (String) map.get("value");

		tv_text.setText(text);
		if (position == 0) {
			tv_value.setVisibility(View.GONE);
			iv_value.setVisibility(View.VISIBLE);
			ImageUtils.rect(activity, value, iv_value, R.drawable.avatar_default);
		} else {
			tv_value.setVisibility(View.VISIBLE);
			iv_value.setVisibility(View.GONE);
			tv_value.setText(value);
		}

		return convertView;
	}
}
