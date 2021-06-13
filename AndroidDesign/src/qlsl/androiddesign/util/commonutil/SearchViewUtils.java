package qlsl.androiddesign.util.commonutil;

import com.qlsl.androiddesign.appname.R;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchViewUtils {

	/**
	 * 隐藏SearchView左边的搜索icon<br/>
	 * 以及设置SearchView的基本属性<br/>
	 * 如果要改变SearchView的其他属性<br/>
	 * 须首先调用ViewOutUtils.outputAllView(searchView)方法以确定SearchView中每个子控件的id<br/>
	 */
	public static void hideLeftIcon(SearchView searchView) {
		searchView.setSubmitButtonEnabled(false);

		int id_search_src_text = searchView.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView tv_text = (TextView) searchView
				.findViewById(id_search_src_text);
		tv_text.setTextColor(searchView.getContext().getResources()
				.getColor(R.color.text_color_edit_text));
		tv_text.setHintTextColor(searchView.getContext().getResources()
				.getColor(R.color.text_color_edit_hint));

		int id_search_mag_icon = searchView.getContext().getResources()
				.getIdentifier("android:id/search_mag_icon", null, null);
		ImageView search_mag_icon = (ImageView) searchView
				.findViewById(id_search_mag_icon);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) search_mag_icon
				.getLayoutParams();
		params.width = 0;
		params.height = 0;

	}
}
