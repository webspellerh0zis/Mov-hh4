package qlsl.androiddesign.manager;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshListView;

/**
 * 滑动隐藏搜索框<br/>
 * setMarginTop交替使用导致卡顿问题未解决<br/>
 */
public class ScrollHideManager implements OnTouchListener, AnimatorListener {

	/**
	 * 高度误差偏移
	 */
	private static final int offset = 14;

	private PullToRefreshListView refreshView;
	private AbsListView listView;
	private View searchView;

	private boolean hasHide;
	private boolean isRunAnim;
	private float downY;

	public ScrollHideManager(PullToRefreshListView refreshView, View searchView) {
		this.refreshView = refreshView;
		this.listView = refreshView.getRefreshableView();
		this.searchView = searchView;
		setMarginTop(searchView.getHeight() + offset);
//		refreshView.getRefreshableView().setOnTouchListener(this);
	}

	public ScrollHideManager(AbsListView listView, View searchView) {
		this.listView = listView;
		this.searchView = searchView;
		setMarginTop(searchView.getHeight() + offset);
//		listView.setOnTouchListener(this);
	}

	@SuppressLint({ "ClickableViewAccessibility" })
	public boolean onTouch(View v, MotionEvent event) {
		float currentY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = currentY;
			break;
		case MotionEvent.ACTION_MOVE:
			float dY = Math.abs(currentY - downY);
			boolean down = currentY > downY ? true : false;
			boolean isUp = dY > 10 && !hasHide && !down;
			boolean isDown = dY > 10 && hasHide && down;
			downY = currentY;
			if (isUp && !isRunAnim) {
				isRunAnim = true;
				float[] f = new float[2];
				f[0] = 0F;
				f[1] = -searchView.getHeight() - 10;
				ObjectAnimator animator1 = ObjectAnimator.ofFloat(searchView, "translationY", f);
				animator1.setInterpolator(new AccelerateDecelerateInterpolator());
				animator1.setDuration(200);
				animator1.start();
				animator1.addListener(this);
				setMarginTop(0);
			} else if (isDown && !isRunAnim) {
				isRunAnim = true;
				float[] f = new float[2];
				f[0] = -searchView.getHeight() - 10;
				f[1] = 0F;
				ObjectAnimator animator1 = ObjectAnimator.ofFloat(searchView, "translationY", f);
				animator1.setDuration(200);
				animator1.setInterpolator(new AccelerateDecelerateInterpolator());
				animator1.start();
				animator1.addListener(this);
				setMarginTop(searchView.getHeight() + offset);
			}
			break;
		}
		return false;
	}

	public void onAnimationStart(Animator arg0) {

	}

	public void onAnimationRepeat(Animator arg0) {

	}

	public void onAnimationEnd(Animator arg0) {
		isRunAnim = false;
		hasHide = !hasHide;
	}

	public void onAnimationCancel(Animator arg0) {

	}

	public void setMarginTop(int top) {
		if (refreshView != null) {
			LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) refreshView.getLayoutParams();
			layoutParam.setMargins(0, top, 0, 0);
			refreshView.setLayoutParams(layoutParam);
		} else if (listView != null) {
			LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) listView.getLayoutParams();
			layoutParam.setMargins(0, top, 0, 0);
			listView.setLayoutParams(layoutParam);
		}
	}

}
