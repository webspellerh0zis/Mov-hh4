package qlsl.androiddesign.view.commonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import qlsl.androiddesign.callback.OnVerticalFlingListener;

/**
 * 水平滑动兼容竖直滑动的ViewPager,by ylq at 20160121<br/>
 *
 */
public class VerticalViewPager extends ViewPager {

	private static final int MIN_VERTICAL_DISTANCE = 50;

	private static final int MAX_HORIZONTAL_DISTANCE = 200;

	private float downX, downY;

	private OnVerticalFlingListener listener;

	public VerticalViewPager(Context context) {
		super(context);
	}

	public VerticalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		int lastItem = getCurrentItem();
		boolean horizontalTouch = super.onTouchEvent(event);
		int currentItem = getCurrentItem();
		if (lastItem == currentItem && listener != null) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				float currentX = event.getX();
				float currentY = event.getY();
				float flingHorizontal = Math.abs(currentX - downX);
				float flingVertical = currentY - downY;

				if (flingVertical > MIN_VERTICAL_DISTANCE && flingHorizontal < MAX_HORIZONTAL_DISTANCE) {
					listener.onFlingDown();
				} else if (flingVertical < -MIN_VERTICAL_DISTANCE && flingHorizontal < MAX_HORIZONTAL_DISTANCE) {
					listener.onFlingUp();
				}
				break;
			}
		}

		return horizontalTouch;
	}

	public void setOnVerticalFlingListener(OnVerticalFlingListener listener) {
		this.listener = listener;
	}

}
