package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BasePagerAdapter;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * 兼容网络地址<br/>
 *
 */
public class PhotoBrowseAdapter extends BasePagerAdapter<String> {

	public PhotoBrowseAdapter(BaseActivity activity, List<String> list) {
		super(activity, list);
	}

	public View instantiateItem(ViewGroup container, int position) {
		View convertView = getItemView(container, R.layout.listitem_photo_browse);
		ImageView imageView = getView(convertView, R.id.photoView);

		String url = getItem(position);

		ImageUtils.rectOri(activity, url, imageView);

		return convertView;
	}

}
