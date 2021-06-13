package qlsl.androiddesign.view.indicatorview;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * Network ImageIndicatorView, by urls
 * 
 * @author steven-pan
 * 
 */
public class IndicatorView extends ImageIndicatorView {

	public IndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IndicatorView(Context context) {
		super(context);
	}

	public void setupLayoutWithDefaultView() {
		clear();
		ImageView pageItem = new ImageView(getContext());
		pageItem.setAdjustViewBounds(true);
		pageItem.setImageResource(R.drawable.iv_default);
		addViewItem(pageItem);
	}

	/**
	 * 无图片时占位
	 */
	public void setupLayoutByPicasso(final List<String> urlList) {
		clear();
		int size = urlList.size();
		if (size > 0) {
			for (int index = 0; index < size; index++) {
				final ImageView pageItem = new ImageView(getContext());
				pageItem.setAdjustViewBounds(true);
				ImageUtils.rect(getContext(), urlList.get(index), pageItem);
				addViewItem(pageItem);
			}
		} else {
			final ImageView pageItem = new ImageView(getContext());
			pageItem.setAdjustViewBounds(true);
			pageItem.setImageResource(R.drawable.iv_default_indicator);
			addViewItem(pageItem);
		}
	}

	/**
	 * 无图片时不占位
	 */
	public void setupLayoutByPicassoUnPlaceHolder(final List<String> urlList) {
		clear();
		int size = urlList.size();
		if (size > 0) {
			for (int index = 0; index < size; index++) {
				final ImageView pageItem = new ImageView(getContext());
				pageItem.setAdjustViewBounds(true);
				ImageUtils.rect(getContext(), urlList.get(index), pageItem);
				addViewItem(pageItem);
			}
		}
	}

	/**
	 * 建立size个默认图片<br/>
	 */
	public void setupLayoutByDefaultResource(final int size) {
		clear();
		for (int index = 0; index < size; index++) {
			final ImageView pageItem = new ImageView(getContext());
			pageItem.setScaleType(ScaleType.FIT_XY);
			ImageUtils.rect(getContext(), "", pageItem);
			addViewItem(pageItem);
		}

	}

}
