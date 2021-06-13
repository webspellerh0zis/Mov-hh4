package qlsl.androiddesign.view.commonview;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.util.AttributeSet;
import qlsl.androiddesign.thread.ViewDrawThread;

/**
 * 单字顺显纵向TextView<br/>
 * 规则：以\n分割，每列字数相同<br/>
 * 用于绝句诗体<br/>
 */
public class PoemTextView extends TextView {

	/**
	 * 定时刷新
	 */
	private ViewDrawThread drawThread;

	/**
	 * 画笔
	 */
	private Paint paint;

	/**
	 * 文本
	 */
	private String text;

	/**
	 * 单字宽高
	 */
	private int fontWidth, fontHeight;

	/**
	 * 控件宽高
	 */
	private int width, height;

	/**
	 * 字体间距
	 */
	private int horizontalSpacing, verticalSpacing;

	/**
	 * 单字顺显
	 */
	private int textIndex;

	public PoemTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = getPaint();
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.poem);
		horizontalSpacing = (int) typedArray.getDimension(
				R.styleable.poem_poemHorizontalSpacing, 0);
		verticalSpacing = (int) typedArray.getDimension(
				R.styleable.poem_poemVerticalSpacing, 0);
		text = typedArray.getString(R.styleable.poem_poemText);
		float textSize = typedArray.getDimension(R.styleable.poem_poemTextSize,
				22);
		int textColor = typedArray.getColor(R.styleable.poem_poemTextColor,
				0xFF000000);
		typedArray.recycle();
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.LEFT);
		initFontParams();
	}

	public void onPause() {
		setText(null);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			invalidate();
		};
	};

	public void setText(String text) {
		this.text = text;
		stopDrawThread();
		if (text != null) {
			initTextParams();
		} else {
			invalidate();
		}
	}

	public String getText() {
		return text;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawText(canvas, text);
	}

	private void drawText(Canvas canvas, String text) {
		if (text == null) {
			return;
		}
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		int paddingRight = getPaddingRight();

		int startX = width - fontWidth - paddingRight;
		int startY = paddingTop + fontHeight;
		int length = text.length();

		for (int i = 0; i < length && i < textIndex; i++) {
			char ch = text.charAt(i);
			if (ch == '\n') {
				startX -= (fontWidth + verticalSpacing);
				startY = paddingTop + fontHeight;
			} else {
				if (startY + paddingBottom > height) {
					startX -= (fontWidth + verticalSpacing);
					startY = paddingTop + fontHeight;
					i--;
				} else {
					canvas.drawText(String.valueOf(ch), startX, startY, paint);
					startY += (fontHeight + horizontalSpacing);
				}
			}
		}
		textIndex++;
		if (textIndex > length) {
			stopDrawThread();
		}
	}

	private void initFontParams() {
		float[] widths = new float[1];
		paint.getTextWidths("宽", widths);
		fontWidth = (int) Math.ceil(widths[0]);
		FontMetrics fm = paint.getFontMetrics();
		fontHeight = (int) (Math.ceil(fm.bottom - fm.top));
	}

	private void initTextParams() {
		int length = text.length();
		int textColumns = 1;
		for (int i = 0; i < length; i++) {
			if (text.charAt(i) == '\n') {
				textColumns++;
			}
		}
		width = (fontWidth + verticalSpacing) * textColumns - verticalSpacing
				+ getPaddingLeft() + getPaddingRight();
		height = (fontHeight + horizontalSpacing) * (length / textColumns)
				- horizontalSpacing + getPaddingTop() + getPaddingBottom();
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = width;
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = height;
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	public void startDrawThread() {
		if (isStop()) {
			textIndex = 0;
			drawThread = new ViewDrawThread(handler, 700, 1000);
			drawThread.setFlag(true);
			drawThread.start();
			requestLayout();
		}
	}

	public void stopDrawThread() {
		if (drawThread != null) {
			drawThread.setFlag(false);
			drawThread = null;
		}
	}

	public boolean isStop() {
		return drawThread == null;
	}

}