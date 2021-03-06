package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class WrapExpandableListView extends ExpandableListView {

	public WrapExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapExpandableListView(Context context) {
		super(context);
	}

	public WrapExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}