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
import qlsl.androiddesign.constant.SoftwareConstant;

/**
 * 公用图文设置列表适配器<br/>
 * 左图中文右方向,分界线缩进<br/>
 * 使用公用空数据标记位隐藏描述文本代替以右方向[desc]<br/>
 * 效果图见项目CommonImageAdapter截图<br/>
 */
public class CommonImageTextAdapter extends BaseAdapter<HashMap<String, Object>> {

	public CommonImageTextAdapter(BaseActivity activity, List<HashMap<String, Object>> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_common_image_text);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		ImageView iv_arrow = getView(convertView, R.id.iv_arrow);
		TextView tv_name = getView(convertView, R.id.tv_text);
		TextView tv_desc = getView(convertView, R.id.tv_desc);

		HashMap<String, Object> map = getItem(position);
		int icon = (Integer) map.get("icon");
		String text = (String) map.get("text");
		String desc = (String) map.get("desc");

		iv_icon.setImageResource(icon);
		tv_name.setText(text);
		tv_desc.setText(desc);

		if (SoftwareConstant.NULL.equals(desc)) {
			tv_desc.setVisibility(View.GONE);
			iv_arrow.setVisibility(View.VISIBLE);
		} else {
			tv_desc.setVisibility(View.VISIBLE);
			iv_arrow.setVisibility(View.GONE);
		}

		return convertView;
	}
}
