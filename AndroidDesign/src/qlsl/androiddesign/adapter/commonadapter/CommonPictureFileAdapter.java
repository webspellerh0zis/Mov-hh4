package qlsl.androiddesign.adapter.commonadapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.PhotoBrowseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.util.commonutil.FileNameUtils;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * 公用本地图片加载宫格适配器<br/>
 * 数据直接来源于多选图片项目[照相或选取]<br/>
 * 使用null标记位显示唯一一张加号图片<br/>
 * 点击最末加号图片跳到多选图片项目<br/>
 */
public class CommonPictureFileAdapter extends BaseAdapter<PhotoModel> {

	public CommonPictureFileAdapter(BaseActivity activity, List<PhotoModel> list) {
		super(activity, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.griditem_picture);

		ImageView iv_grid_item = getView(convertView, R.id.iv_grid_item);
		ImageView iv_delete = getView(convertView, R.id.iv_delete);

		final PhotoModel photoModel = getItem(position);
		if (photoModel == null) {
			ImageUtils.rect(activity, null, iv_grid_item, R.drawable.app_icon_default);
			iv_delete.setVisibility(View.GONE);
		} else {
			ImageUtils.round(activity, new File(photoModel.getOriginalPath()), iv_grid_item);
			iv_delete.setVisibility(View.VISIBLE);
		}

		iv_grid_item.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				List<PhotoModel> list = new ArrayList<PhotoModel>();
				list.addAll(getList());
				list.remove(null);
				list.remove(null);
				if (photoModel == null) {
					list.remove(null);
					list.remove(null);
					Intent intent = new Intent(activity, PhotoSelectorActivity.class);
					intent.putExtra(PhotoSelectorActivity.KEY_MAX, 9);
					intent.putExtra(PhotoSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, (Serializable) list);
					intent.putExtra(PhotoSelectorActivity.KEY_SECOND_DIR, FileNameUtils.getGroupSecondDir());
					intent.putExtra(PhotoSelectorActivity.KEY_PREFIX_NAME,
							FileNameUtils.getGroupNoticePrefixName(activity));
					activity.startActivityForResult(intent, IntentCodeConstant.REQUEST_CODE_PHOTO_PICKED);
				} else {
					ArrayList<File> photos = new ArrayList<File>();
					for (PhotoModel photoModel : list) {
						photos.add(new File(photoModel.getOriginalPath()));
					}
					Intent intent = new Intent(activity, PhotoBrowseActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("list", photos);
					activity.startActivity(intent);
				}
			}
		});

		iv_delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				getList().remove(position);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}
}
