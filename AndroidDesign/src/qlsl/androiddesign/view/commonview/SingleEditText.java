package qlsl.androiddesign.view.commonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.widget.TextView;
import qlsl.androiddesign.listener.OnTextChangeListener;
import qlsl.androiddesign.thread.ViewDrawThread;

/**
 * 单字顺显的EditText<br/>
 */
public class SingleEditText extends EditText {

	/**
	 * 睡眠时间
	 */
	private int sleepSpan = 400;

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

	/**
	 * 最大长度
	 */
	private int maxLength;

	/**
	 * 字数提示
	 */
	private TextView tv_sum;

	public SingleEditText(Context context, AttributeSet attrs) {
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

	public void startDrawThread(String text, TextView tv_sum, int maxLength) {
		this.text = text;
		this.tv_sum = tv_sum;
		this.maxLength = maxLength;
		InputFilter[] filters = { new LengthFilter(maxLength) };
		setFilters(filters);
		setText(text);
		tv_sum.setText(text.length() + "/" + maxLength);
		addTextChangedListener(onTextChangeListener);
		setEnabled(false);
		textIndex = 0;
		drawThread = new ViewDrawThread(handler, sleepSpan, 800);
		drawThread.setFlag(true);
		drawThread.start();
	}

	public void stopDrawThread() {
		if (drawThread != null) {
			setEnabled(true);
			drawThread.setFlag(false);
			drawThread = null;
		}
		textIndex = 1;
		String nowText = getText().toString();
		if (text.contains(nowText)) {
			setText(text);
		} else {
			setText(nowText);
		}
	}

	private OnTextChangeListener onTextChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(Editable s) {
			tv_sum.setText(s.length() + "/" + maxLength);
		}
	};

	public int getSleepSpan() {
		return sleepSpan;
	}

	public void setSleepSpan(int sleepSpan) {
		this.sleepSpan = sleepSpan;
	}

}
