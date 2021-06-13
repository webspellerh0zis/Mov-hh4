package qlsl.androiddesign.view.indicatorview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 调节轮播图自动，手动滑动时的速度<br/>
 *
 */
public class ViewPagerScroll extends Scroller {

	public ViewPagerScroll(Context context) {
		super(context);
	}

	public ViewPagerScroll(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		if (duration == 200) {
			super.startScroll(startX, startY, dx, dy, 600);
		} else {
			super.startScroll(startX, startY, dx, dy, 60);
		}
	}

}
