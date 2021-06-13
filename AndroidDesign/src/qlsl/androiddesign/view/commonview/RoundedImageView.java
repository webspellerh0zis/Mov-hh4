package qlsl.androiddesign.view.commonview;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by luoyong on 2015/5/8<br/>
 * 圆形图片控件<br/>
 */
public class RoundedImageView extends ImageView {

	/**
	 * 目标图片画笔
	 */
	private Paint paint;

	/**
	 * 内间距画笔
	 */
	private Paint paintBorder;

	/**
	 * 内间距
	 */
	private int borderWidth;

	/**
	 * 画布大小
	 */
	private int canvasSize;

	public RoundedImageView(final Context context) {
		this(context, null);
	}

	public RoundedImageView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.roundedImageViewStyle);
	}

	@SuppressLint("Recycle")
	public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setAntiAlias(true);
		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);

		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);

		if (attributes.getBoolean(R.styleable.RoundedImageView_border, true)) {
			int defaultBorderSize = (int) (4 * getContext().getResources().getDisplayMetrics().density + 0.5f);
			setBorderWidth(
					attributes.getDimensionPixelOffset(R.styleable.RoundedImageView_border_width, defaultBorderSize));
			setBorderColor(attributes.getColor(R.styleable.RoundedImageView_border_color, Color.parseColor("#1E1E20")));
		}

		if (attributes.getBoolean(R.styleable.RoundedImageView_shadow, false)) {
			addShadow();
		}
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		this.requestLayout();
		this.invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}

	public void addShadow() {
		setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		Bitmap bitmap = drawableToBitmap(getDrawable());
		if (bitmap != null) {
			canvasSize = canvas.getWidth();
			if (canvas.getHeight() < canvasSize) {
				canvasSize = canvas.getHeight();
			}
			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bitmap, canvasSize, canvasSize, false),
					Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			paint.setShader(shader);
			int circleCenter = (canvasSize - (borderWidth * 2)) / 2;
			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth,
					((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f, paintBorder);
			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth,
					((canvasSize - (borderWidth * 2)) / 2) - 4.0f, paint);
		}
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else {
			result = canvasSize;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else {
			result = canvasSize;
		}
		return (result + 2);
	}

	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		} else if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
