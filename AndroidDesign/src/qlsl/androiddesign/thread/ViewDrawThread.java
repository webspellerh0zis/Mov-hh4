package qlsl.androiddesign.thread;

import android.os.Handler;

public class ViewDrawThread extends Thread {
	private int sleepSpan = 0;
	private int delay = 0;
	private boolean flag = true;
	private Handler handler = null;

	public ViewDrawThread(Handler handler, int sleepSpan, int delay) {
		this.handler = handler;
		this.sleepSpan = sleepSpan;
		this.delay = delay;
	}

	public void run() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (flag) {
			handler.sendEmptyMessage(0);
			try {
				Thread.sleep(sleepSpan);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean getFlag() {
		return flag;
	}

}
