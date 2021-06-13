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

	public StaticViewPager(Context context) {
		super(context);
	}

	public StaticViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
}
