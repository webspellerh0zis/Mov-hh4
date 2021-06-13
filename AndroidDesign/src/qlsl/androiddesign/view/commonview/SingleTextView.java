package qlsl.androiddesign.view.commonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import qlsl.androiddesign.thread.ViewDrawThread;
import qlsl.androiddesign.util.singleton.ToolColorUtils;

/**
 * 单字顺显的TextView<br/>
 */
public class SingleTextView extends TextView {

	/**
	 * 定时刷新
	 */
	private ViewDrawThread drawThread;

	/**
	 * 文本
	 */
	private String text;

	/**
	 * 单字顺显
	 */
	private int textIndex;

	public SingleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onPause() {
		stopDrawThread();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			textIndex++;
			int length = text.length();
			if (length > 0 && textIndex <= length) {
				setText(text.substring(0, textIndex));
			} else {
				stopDrawThread();
			}
		};
	};

	protected void onDraw(Canvas canvas) {
		if (textIndex > 0) {
			super.onDraw(canvas);
		}
	};

	public void startDrawThread() {
		boolean isShowSingle = ToolColorUtils.get().getIsShowSingle();
		if (isShowSingle) {
			setEnabled(false);
			text = getText().toString();
			textIndex = 0;
			drawThread = new ViewDrawThread(handler, ToolColorUtils.get()
					.getSleep(), 800);
			drawThread.setFlag(true);
			drawThread.start();
		} else {
			text = getText().toString();
			textIndex = 1;
			setText(text);
		}
	}

	public void stopDrawThread() {
		if (drawThread != null) {
			setEnabled(true);
			drawThread.setFlag(false);
			drawThread = null;
		}
		textIndex = 1;
		setText(text);

	}

}
