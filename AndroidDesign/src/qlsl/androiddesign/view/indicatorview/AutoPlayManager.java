package qlsl.androiddesign.view.indicatorview;

import java.lang.reflect.Field;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;

/**
 * Auto BrocastManager for ImageIndicatorView
 * 
 * @author steven-pan
 * 
 */
public class AutoPlayManager {

	/**
	 * 自动播放标志位，默认播放
	 */
	private boolean broadcastEnable = false;
	/**
	 * 自动播放启动默认时间
	 */
	private static final long DEFAULT_STARTMILS = 2 * 1000;
	/**
	 * 自动播放间隔默认时间
	 */
	private static final long DEFAULT_INTEVALMILS = 3 * 1000;
	/**
	 * 启动时间ms
	 */
	private long startMils = DEFAULT_STARTMILS;
	/**
	 * 间隔ms
	 */
	private long intevalMils = DEFAULT_INTEVALMILS;
	/**
	 * 向右
	 */
	private final static int RIGHT = 0;
	/**
	 * 向左
	 */
	private final static int LEFT = 1;

	/**
	 * 当前方向
	 */
	private int direction = RIGHT;

	/**
	 * 自动播放默认次数（无限）
	 */
	private static final int DEFAULT_TIMES = -1;

	/**
	 * 自动播放次数
	 */
	private int broadcastTimes = DEFAULT_TIMES;

	/**
	 * 自动播放次数记数
	 */
	private int timesCount = 0;

	/**
	 * 循环播放
	 */
	private Handler broadcastHandler = null;

	/**
	 * target ImageIndicatorView
	 */
	private ImageIndicatorView mImageIndicatorView = null;

	public AutoPlayManager(ImageIndicatorView imageIndicatorView) {
		this.mImageIndicatorView = imageIndicatorView;
		broadcastHandler = new BroadcastHandler();
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			ViewPagerScroll scroller = new ViewPagerScroll(imageIndicatorView.getViewPager().getContext(),
					new AccelerateInterpolator());
			field.set(imageIndicatorView.getViewPager(), scroller);
		} catch (Exception e) {
		}
	}

	/**
	 * 设置自动播放启动时间和间隔
	 * 
	 * @param startMils
	 *            启动时间ms(>2, 默认为8s)
	 * @param intevelMils
	 *            间隔ms(默认为3s)
	 */
	public void setBroadcastTimeIntevel(long startMils, long intevelMils) {
		this.startMils = startMils;
		this.intevalMils = intevelMils;
	}

	/**
	 * 设置自动播放开关
	 * 
	 * @param flag
	 *            打开或关闭
	 */
	public void setBroadcastEnable(boolean flag) {
		this.broadcastEnable = flag;
	}

	/**
	 * 设置循环播放次数
	 * 
	 * @param times
	 *            循环播放次数
	 */
	public void setBroadCastTimes(int times) {
		this.broadcastTimes = times;
	}

	/**
	 * 启动循环播放
	 */
	public void loop() {
		if (broadcastEnable) {
			startTask();
		}
	}

	private void updateViewPager() {
		if (broadcastEnable) {
			if (System.currentTimeMillis() - mImageIndicatorView.getRefreshTime() < 2 * 1000) {// 最近一次划动间隔小于2s
				return;
			}
			if ((broadcastTimes != DEFAULT_TIMES) && (timesCount > broadcastTimes)) {// 循环次数用完
				return;
			}

			if (direction == RIGHT) {// roll right
				if (mImageIndicatorView.getCurrentIndex() < mImageIndicatorView.getTotalCount()) {
					if (mImageIndicatorView.getCurrentIndex() == mImageIndicatorView.getTotalCount() - 1) {
						timesCount++;// 循环次数次数加1
						direction = LEFT;
					} else {
						mImageIndicatorView.getViewPager().setCurrentItem(mImageIndicatorView.getCurrentIndex() + 1,
								true);
					}
				}
			} else {// roll left
				if (mImageIndicatorView.getCurrentIndex() >= 0) {
					if (mImageIndicatorView.getCurrentIndex() == 0) {
						direction = RIGHT;
					} else {
						mImageIndicatorView.getViewPager().setCurrentItem(mImageIndicatorView.getCurrentIndex() - 1,
								true);
					}
				}
			}

		}
	}

	private void startTask() {
		java.util.Timer timer = new java.util.Timer(true);
		TimerTask task = new TimerTask() {
			public void run() {
				Message message = Message.obtain();
				message.what = 1;
				broadcastHandler.sendMessage(message);
			}
		};

		timer.schedule(task, startMils, intevalMils);

	}

	@SuppressLint("HandlerLeak")
	private class BroadcastHandler extends Handler {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				updateViewPager();
			}
		}
	}

	public void stopHandler() {
		broadcastHandler.removeCallbacksAndMessages(null);
	}

	public void onResume() {
		setBroadcastEnable(true);
	}

	public void onPause() {
		setBroadcastEnable(false);
	}

	public void onDestroy() {
		stopHandler();
	}

}
