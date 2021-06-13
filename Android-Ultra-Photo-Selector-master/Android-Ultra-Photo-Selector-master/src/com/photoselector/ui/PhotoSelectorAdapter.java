package com.photoselector.ui;

import java.util.ArrayList;

import com.photoselector.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoItem.onItemClickListener;
import com.photoselector.ui.PhotoItem.onPhotoItemCheckedListener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

/**
 * 
 * @author Aizaz AZ
 *
 */

public class PhotoSelectorAdapter extends BaseAdapter<PhotoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Activity activity, ArrayList<PhotoModel> models) {
		super(activity, models);
	}

	public PhotoSelectorAdapter(Activity activity, ArrayList<PhotoModel> models, int screenWidth,
			onPhotoItemCheckedListener listener, onItemClickListener mCallback, OnClickListener cameraListener) {
		this(activity, models);
		setItemWidth(screenWidth);
		this.listener = listener;
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
	}

	/** 设置每一个Item的宽高 */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = activity.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PhotoItem item = null;
		convertView = getItemView(convertView, R.layout.griditem_photo_selector);
		ViewGroup vg = (ViewGroup) convertView;
		if (position == 0) {
			vg.getChildAt(0).setLayoutParams(itemLayoutParams);
			vg.getChildAt(0).setVisibility(View.VISIBLE);
			if (vg.getChildCount() > 1) {
				vg.getChildAt(1).setVisibility(View.GONE);
			}
		} else {
			if (vg.getChildCount() < 2) {
				item = new PhotoItem(activity, listener);
				item.setLayoutParams(itemLayoutParams);
				vg.addView(item);
			} else {
				item = (PhotoItem) vg.getChildAt(1);
			}
			item.setImageDrawable(getItem(position));
			item.setSelected(getItem(position).isChecked());
			item.setOnClickListener(mCallback, position);

			vg.getChildAt(0).setVisibility(View.GONE);
			vg.getChildAt(1).setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}
