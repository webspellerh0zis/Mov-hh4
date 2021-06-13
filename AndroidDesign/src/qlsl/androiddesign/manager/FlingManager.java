package qlsl.androiddesign.manager;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.callback.OnVerticalFlingListener;

/**
 * 控件竖直滑动手势管理<br/>
 * 待改进，暂无使用<br/>
 *
 */
public class FlingManager implements OnGestureListener, OnTouchListener {

	private static final int MIN_DISTANCE = 50;

	private BaseActivity activity;

	private GestureDetector detector;

	private OnVerticalFlingListener listener;

	private View receiveTouchView;

	public FlingManager(BaseActivity activity, OnVerticalFlingListener listener) {
		this.activity = activity;
		this.listener = listener;
		detector = new GestureDetector(activity, this);
		View rootGroup = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
		rootGroup.setOnTouchListener(this);
	}

	public FlingManager(BaseActivity activity, OnVerticalFlingListener listener, View touchView) {
		this.activity = activity;
		this.listener = listener;
		detector = new GestureDetector(activity, this);
		detector.setIsLongpressEnabled(true);
		touchView.setOnTouchListener(this);
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onShowPress(MotionEvent e) {

	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (e1 != null && e2 != null) {
			if (e2.getAction() == MotionEvent.ACTION_UP) {
				float destY = e2.getY() - e1.getY();
				if (destY > MIN_DISTANCE) {
					listener.onFlingDown();
					return true;
				} else if (destY < -MIN_DISTANCE) {
					listener.onFlingUp();
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public void onLongPress(MotionEvent e) {
		activity.showToast("上下滑动切换题型");
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1 != null && e2 != null) {
			float destY = e2.getY() - e1.getY();
			if (destY > MIN_DISTANCE) {
				listener.onFlingDown();
				return true;
			} else if (destY < -MIN_DISTANCE) {
				listener.onFlingUp();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
		boolean isTransmitTouch = detector.onTouchEvent(event);
		if (isTransmitTouch && receiveTouchView != null) {
			receiveTouchView.dispatchTouchEvent(event);
		}
		return isTransmitTouch;
	}

	public View getReceiveTouchView() {
		return receiveTouchView;
	}

	public void setReceiveTouchView(View receiveTouchView) {
		this.receiveTouchView = receiveTouchView;
	}

}
