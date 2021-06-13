package qlsl.androiddesign.view.commonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import qlsl.androiddesign.thread.HorizontalTextViewDrawThread;

/**
 * 单字顺显横向TextView<br/>
 * 
 */
@SuppressLint({ "DrawAllocation", "ViewConstructor" })
public class HorizontalTextView extends SurfaceView implements
		SurfaceHolder.Callback {

	private HorizontalTextViewDrawThread viewDrawThread = null;

	private String text;

	private TextPaint paint;

	private int index;

	private int width;

	public HorizontalTextView(Context context, String text) {
		super(context);
		getHolder().addCallback(this);
		this.text = text;
		paint = new TextPaint();
		paint.setTextSize(25);
		paint.setColor(Color.CYAN);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));
		viewDrawThread = new HorizontalTextViewDrawThread(this, getHolder());
	}

	public void onDraw(Canvas canvas) {
		StaticLayout layout = null;
		if (index < text.length()) {
			layout = new StaticLayout(text.substring(0, ++index), paint, width,
					Alignment.ALIGN_NORMAL, 1.5F, 0.0F, true);
		} else {
			layout = new StaticLayout(text, paint, width,
					Alignment.ALIGN_NORMAL, 1.5F, 0.0F, true);
		}

		canvas.translate(0, 0);
		layout.draw(canvas);

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		width = getWidth();
		if (viewDrawThread == null) {
			viewDrawThread = new HorizontalTextViewDrawThread(this,
					getHolder());
		}
		viewDrawThread.setFlag(true);
		viewDrawThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		viewDrawThread.setFlag(false);
		while (retry) {
			try {
				viewDrawThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		viewDrawThread = null;
	}

}