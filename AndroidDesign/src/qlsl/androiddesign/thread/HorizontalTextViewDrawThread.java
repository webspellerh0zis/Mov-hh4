package qlsl.androiddesign.thread;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import qlsl.androiddesign.view.commonview.HorizontalTextView;

@SuppressLint("WrongCall")
public class HorizontalTextViewDrawThread extends Thread {
	private int sleepSpan = 200;
	private boolean flag = true;
	private HorizontalTextView view;
	private SurfaceHolder surfaceHolder = null;

	public HorizontalTextViewDrawThread(HorizontalTextView view,
			SurfaceHolder surfaceHolder) {
		this.view = view;
		this.surfaceHolder = surfaceHolder;
	}

	public void run() {
		Canvas c;
		while (flag) {
			c = null;
			try {
				c = surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder) {
					try {
						view.onDraw(c);
					} catch (Exception e) {
					}
				}
			} finally {
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
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
}
