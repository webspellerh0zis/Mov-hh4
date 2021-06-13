package qlsl.androiddesign.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 向导控件集中在标题栏中间的[非向导控件均分]的变换监听器<br/>
 */
public class GuidePageCenterChangeListener implements OnPageChangeListener {

	private TextView[] tv_guides;

	private ImageView iv_cursor;

	public int currIndex;

	private float one;

	public GuidePageCenterChangeListener(TextView[] tv_guides,
			ImageView iv_cursor, float offset) {
		this.tv_guides = tv_guides;
		this.iv_cursor = iv_cursor;
		one = offset;
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageSelected(int index) {
		doPageSelected(index);
	}

	public void doPageSelected(int index) {
		Animation animation = new TranslateAnimation((currIndex - 1) * one,
				(index - 1) * one, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(200);
		iv_cursor.startAnimation(animation);
		currIndex = index;
		tv_guides[index].performClick();
	}

}
