package qlsl.androiddesign.adapter.commonadapter;

import java.io.File;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BasePagerAdapter;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * 兼容本地地址<br/>
 *
 */
public class PhotoBrowseFileAdapter extends BasePagerAdapter<File> {

	public PhotoBrowseFileAdapter(BaseActivity activity, List<File> list) {
		super(activity, list);
	}

	public View instantiateItem(ViewGroup container, int position) {
		View convertView = getItemView(container, R.layout.listitem_photo_browse);
		ImageView imageView = getView(convertView, R.id.photoView);

		File file = getItem(position);

		ImageUtils.rectOri(activity, file, imageView);

		return convertView;
	}

}
