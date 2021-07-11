package qlsl.androiddesign.view.commonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动的ViewPager<br/>
 *
 */
public class StaticViewPager extends ViewPager {

	private boolean canScroll = false;  

	public StaticViewPager(Context context) {
		super(context);
	}

	public StaticViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setCanScroll(boolean canScroll) {
		this.canScroll = canScroll;
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent arg0) {
		if (canScroll) {
			return super.onTouchEvent(arg0);
		}
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (canScroll) {
			return super.onInterceptTouchEvent(arg0);
		}
		return false;
	}
}
