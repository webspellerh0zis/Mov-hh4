package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 兼容子控件水平滑动的ScrollView<br/>
 *
 */
public class WrapScrollView extends ScrollView {

	private GestureDetector mGestureDetector;

	@SuppressWarnings("deprecation")
	public WrapScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new OnGestureListener());
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	private class OnGestureListener extends SimpleOnGestureListener {

		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			double angle = Math.atan2(Math.abs(distanceY), Math.abs(distanceX));
			if ((180 * angle) / Math.PI < 180) {
				return false;
			}
			return false;
		}
	}

}
