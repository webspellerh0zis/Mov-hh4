package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;

public class MemberStepFirstGridAdapter extends BaseAdapter<HashMap<String, Object>> {

	private GridView gridView;

	private Spinner spinner;

	private int selectPosition;

	public MemberStepFirstGridAdapter(BaseActivity activity, GridView gridView, Spinner spinner,
			List<HashMap<String, Object>> list) {
		super(activity, list);
		this.gridView = gridView;
		this.spinner = spinner;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_member_step_first);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		final ImageView iv_select = getView(convertView, R.id.iv_select);
		TextView tv_text = getView(convertView, R.id.tv_text);

		HashMap<String, Object> map = getItem(position);
		int resId = (Integer) map.get("icon");
		String text = (String) map.get("text");

		if (position == selectPosition) {
			iv_select.setVisibility(View.VISIBLE);
		} else {
			iv_select.setVisibility(View.GONE);
		}
		if (selectPosition == 1) {
			spinner.setEnabled(true);
		} else {
			spinner.setEnabled(false);
		}

		iv_icon.setImageResource(resId);
		tv_text.setText(text);

		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				selectPosition = position;
				gridView.invalidateViews();
			}
		});

		return convertView;
	}

	public int getSelectPosition() {
		return selectPosition;
	}
}
