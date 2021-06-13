package qlsl.androiddesign.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class GuidePageChangeListener implements OnPageChangeListener {

	private TextView[] tv_guides;

	private ImageView iv_cursor;

	private int currIndex;

	private float one;

	public GuidePageChangeListener(TextView[] tv_guides, ImageView iv_cursor, float offset, int bmpW) {
		this.tv_guides = tv_guides;
		this.iv_cursor = iv_cursor;
		one = offset * 2 + bmpW;
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageSelected(int index) {
		doPageSelected(index);
	}

	public void doPageSelected(int index) {
		Animation animation = new TranslateAnimation(currIndex * one, index * one, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(200);
		iv_cursor.startAnimation(animation);
		currIndex = index;
		tv_guides[index].performClick();
	}
}
