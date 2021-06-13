package qlsl.androiddesign.adapter.commonadapter;

import java.io.Serializable;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.PhotoBrowseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.util.commonutil.ImageUtils;

public class PhotoGridAdapter extends BaseAdapter<String> {

	public PhotoGridAdapter(BaseActivity activity, List<String> list) {
		super(activity, list);
	}

	public View getView(final int position, View convertView, final ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_photo);

		ImageView iv_grid_item = getView(convertView, R.id.iv_grid_item);

		String thumbUrl = getItem(position);

		ImageUtils.rectOri(activity, thumbUrl, iv_grid_item);

		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(activity, PhotoBrowseActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("list", (Serializable) getList());
				activity.startActivity(intent);

			}
		});

		return convertView;
	}

}
