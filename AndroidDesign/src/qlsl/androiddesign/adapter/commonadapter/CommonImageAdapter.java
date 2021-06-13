package qlsl.androiddesign.adapter.commonadapter;

import java.io.Serializable;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.PhotoBrowseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.constant.SoftwareConstant;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * 公用异步图片加载宫格适配器<br/>
 * 使用公用空数据标记位显示唯一一张无图时的文字说明[text]<br/>
 * 效果图见项目同名截图<br/>
 */
public class CommonImageAdapter extends BaseAdapter<String> {

	/**
	 * 无图时的默认文字<br/>
	 */
	private String text;

	public CommonImageAdapter(BaseActivity activity, List<String> list) {
		super(activity, list);
	}

	public CommonImageAdapter(BaseActivity activity, List<String> list, String text) {
		super(activity, list);
		this.text = text;
	}

	public View getView(final int position, View convertView, final ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_common_image);

		ImageView iv_grid_item = getView(convertView, R.id.iv_grid_item);
		TextView tv_grid_item = getView(convertView, R.id.tv_grid_item);

		final String thumbUrl = getItem(position);

		if (SoftwareConstant.NULL.equals(thumbUrl)) {
			iv_grid_item.setVisibility(View.GONE);
			tv_grid_item.setVisibility(View.VISIBLE);
			tv_grid_item.setText(text);
		} else {
			iv_grid_item.setVisibility(View.VISIBLE);
			tv_grid_item.setVisibility(View.GONE);
			ImageUtils.rectOri(activity, thumbUrl, iv_grid_item);
		}

		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (!SoftwareConstant.NULL.equals(thumbUrl)) {
					Intent intent = new Intent(activity, PhotoBrowseActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("list", (Serializable) getList());
					activity.startActivity(intent);
				}
			}
		});

		return convertView;
	}

}
