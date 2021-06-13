package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class WrapSpeedScrollView extends ScrollView {

	private static final int SCREEN_HEIGHT = 800;

	private static final float FLAG_SPEED_DEFAULT = 1f;

	private static final float FLAG_SPEED_FAST = 1.5f;

	private static final float FLAG_SPEED_FASTER = 2f;

	private static final float FLAG_SPEED_FASTEST = 2.5f;

	private float flag_speed = FLAG_SPEED_DEFAULT;

	public WrapSpeedScrollView(Context context) {
		super(context);
	}

	public WrapSpeedScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapSpeedScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initFlingSpeed();
	}

	/**
	 * 根据ScrollView内容的多少，初始化ScrollView的滑动速度<br/>
	 * 每屏高度按照SCREEN_HEIGHT指定的像素来确定<br/>
	 * 小于5屏 ，为FLAG_SPEED_DEFAULT倍速度<br/>
	 * 5-10屏 ，为FLAG_SPEED_FAST倍速度<br/>
	 * 10-15屏，为FLAG_SPEED_FASTER倍速度<br/>
	 * 大于15屏 ，为FLAG_SPEED_FASTEST倍速度<br/>
	 */
	private void initFlingSpeed() {
		int totalHeight = getChildAt(0).getMeasuredHeight();
		int num_height = totalHeight / SCREEN_HEIGHT;
		if (num_height < 5) {
			setFlingSpeed(FLAG_SPEED_DEFAULT);
		} else if (num_height >= 5 && num_height < 10) {
			setFlingSpeed(FLAG_SPEED_FAST);
		} else if (num_height >= 10 && num_height < 15) {
			setFlingSpeed(FLAG_SPEED_FASTER);
		} else if (num_height >= 15) {
			setFlingSpeed(FLAG_SPEED_FASTEST);
		}
	}

	public void fling(int velocityY) {
		super.fling((int) (velocityY * flag_speed));
	}

	public void setFlingSpeed(float flag_speed) {
		this.flag_speed = flag_speed;
	}

	public float getFliingSpeed() {
		return flag_speed;
	}
}
