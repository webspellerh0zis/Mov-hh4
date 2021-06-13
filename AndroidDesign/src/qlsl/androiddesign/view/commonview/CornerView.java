package qlsl.androiddesign.view.commonview;

import java.util.ArrayList;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.http.service.commonservice.OcrService;
import qlsl.androiddesign.util.commonutil.BitmapUtils;

/**
 * 角点检测View<br/>
 */
@SuppressLint({ "HandlerLeak", "ClickableViewAccessibility" })
public class CornerView extends View {

	private static final byte DIRECTION_TOP = -1;

	private static final byte DIRECTION_LEFT = -2;

	private static final byte DIRECTION_RIGHT = -3;

	private static final byte DIRECTION_BOTTOM = -4;

	private static final int WHAT_INVALIDATE = -1;

	private static final int radius = 25;

	private List<Point> realPoints = new ArrayList<Point>();

	private List<Point> largestPoints = new ArrayList<Point>();

	private List<Point> currentPoints = realPoints;

	private Paint paint, paintCircle, paintText;

	private Bitmap bitmap, bitmapTop, bitmapBottom, bitmapLeft, bitmapRight;

	private boolean hasZoom;

	private byte direction = DIRECTION_TOP;

	private double scaleX = 1;

	private double scaleY = 1;

	private String filename;

	public CornerView(Context context) {
		super(context);
		init();
	}

	public CornerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CornerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_INVALIDATE:
				invalidate();
				break;
			}
		};
	};

	private void init() {
		paint = new Paint();
		paint.setColor(getContext().getResources().getColor(R.color.cyan));
		paint.setStrokeWidth(2);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paintCircle = new Paint();
		paintCircle.setColor(getContext().getResources().getColor(R.color.light_cyan));
		paintCircle.setStrokeWidth(2);
		paintCircle.setStyle(Style.FILL);
		paintCircle.setAntiAlias(true);
		paintText = new Paint();
		paintText.setColor(getContext().getResources().getColor(R.color.blue));
		paintText.setTextSize(16);
		paintText.setTextAlign(Align.CENTER);
		for (int index = 0; index < 9; index++) {
			Point point = new Point(-radius, -radius);
			realPoints.add(point);
		}
		for (int index = 0; index < 9; index++) {
			Point point = new Point(-radius, -radius);
			largestPoints.add(point);
		}
		updateCenterPoints(realPoints);
		updateCenterPoints(largestPoints);
		setTag(null);
	}

	protected void onDraw(Canvas canvas) {
		drawBitmap(canvas);
		drawLine(canvas);
		drawCircle(canvas);
		drawText(canvas);
		super.onDraw(canvas);
	}

	private void drawLine(Canvas canvas) {
		canvas.drawLine(currentPoints.get(0).x, currentPoints.get(0).y, currentPoints.get(2).x, currentPoints.get(2).y,
				paint);
		canvas.drawLine(currentPoints.get(0).x, currentPoints.get(0).y, currentPoints.get(5).x, currentPoints.get(5).y,
				paint);
		canvas.drawLine(currentPoints.get(2).x, currentPoints.get(2).y, currentPoints.get(7).x, currentPoints.get(7).y,
				paint);
		canvas.drawLine(currentPoints.get(5).x, currentPoints.get(5).y, currentPoints.get(7).x, currentPoints.get(7).y,
				paint);
	}

	private void drawCircle(Canvas canvas) {
		for (Point point : currentPoints) {
			canvas.drawCircle(point.x, point.y, radius, paint);
			canvas.drawCircle(point.x, point.y, radius - 3, paintCircle);
		}
	}

	private void drawText(Canvas canvas) {
		canvas.drawText("top", currentPoints.get(1).x, currentPoints.get(1).y, paintText);
		canvas.drawText("left", currentPoints.get(3).x, currentPoints.get(3).y, paintText);
		canvas.drawText("right", currentPoints.get(4).x, currentPoints.get(4).y, paintText);
		canvas.drawText("bottom", currentPoints.get(6).x, currentPoints.get(6).y, paintText);
	}

	private void drawBitmap(Canvas canvas) {
		if (filename != null) {
			zoomMat();
		}
		if (bitmap != null) {
			int left = (getWidth() - bitmap.getWidth()) >> 1;
			int top = (getHeight() - bitmap.getHeight()) >> 1;
			canvas.drawBitmap(bitmap, left, top, paint);
		}
	}

	private void updateCenterPoints(List<Point> list) {
		updateCenterPoint(list.get(0), list.get(2), list.get(1));
		updateCenterPoint(list.get(5), list.get(7), list.get(6));
		updateCenterPoint(list.get(0), list.get(5), list.get(3));
		updateCenterPoint(list.get(2), list.get(7), list.get(4));
	}

	private void updateCenterPoint(Point start, Point end, Point center) {
		int centerX = ((end.x - start.x) >> 1) + start.x;
		int centerY = ((end.y - start.y) >> 1) + start.y;
		center.set(centerX, centerY);
	}

	private void updateSidePoint(int position) {
		Point center = currentPoints.get(position);
		if (position == 3) {
			Point start = currentPoints.get(0);
			Point end = currentPoints.get(5);
			start.set(center.x, start.y);
			end.set(center.x, end.y);
		} else if (position == 4) {
			Point start = currentPoints.get(2);
			Point end = currentPoints.get(7);
			start.set(center.x, start.y);
			end.set(center.x, end.y);
		} else if (position == 1 || position == 6) {
			Point start = currentPoints.get(position - 1);
			Point end = currentPoints.get(position + 1);
			start.set(start.x, center.y);
			end.set(end.x, center.y);
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			doActionDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doActionMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doActionUp(event);
			break;
		}
		return true;
	}

	private void doActionDown(MotionEvent event) {
		int downX = (int) event.getX();
		int downY = (int) event.getY();
		Point downPoint = new Point(downX, downY);
		for (int position = 0, size = currentPoints.size(); position < size; position++) {
			checkDrag(position, downPoint);
		}
	}

	private void doActionMove(MotionEvent event) {
		Object tag = getTag();
		if (tag != null) {
			int position = Integer.parseInt(tag.toString());
			int currentX = (int) event.getX();
			int currentY = (int) event.getY();
			int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();
			int bitmapX = (getWidth() - bitmapWidth) >> 1;
			int bitmapY = (getHeight() - bitmapHeight) >> 1;
			if (currentX < bitmapX || currentX > bitmapX + bitmapWidth || currentY < bitmapY
					|| currentY > bitmapY + bitmapHeight) {
				return;
			}
			Point point = currentPoints.get(position);
			if (position == 1 || position == 6 || position == 3 || position == 4) {
				point.set(currentX, currentY);
				updateSidePoint(position);
			} else {
				point.set(currentX, currentY);
			}
			updateCenterPoints(currentPoints);
			notifyInvalidate();
		}
	}

	private void doActionUp(MotionEvent event) {
		setTag(null);
	}

	private void checkDrag(int position, Point downPoint) {
		if (getTag() == null) {
			Point point = currentPoints.get(position);
			if (downPoint.x > point.x - radius && downPoint.x < point.x + radius && downPoint.y > point.y - radius
					&& downPoint.y < point.y + radius) {
				setTag(position + "");
			}
		}
	}

	public void setFileName(String filename) {
		this.filename = filename;
		hasZoom = false;
		notifyInvalidate();
	}

	private void initRotationBitmap() {
		int padding = radius << 1;
		int maxWidth = getWidth() - padding;
		double scale = 1;
		double preScale = (double) maxWidth / bitmap.getHeight();
		scale = preScale < 1 ? preScale : scale;
		scaleX = scaleY * scale;
		bitmapTop = BitmapUtils.rotationBitmap(bitmap, 0);
		bitmapLeft = BitmapUtils.rotationBitmap(bitmap, -90, (float) scale);
		bitmapRight = BitmapUtils.rotationBitmap(bitmap, 90, (float) scale);
		bitmapBottom = BitmapUtils.rotationBitmap(bitmap, 180);
	}

	private void zoomMat() {
		if (!hasZoom) {
			hasZoom = true;
			BaseActivity activity = (BaseActivity) getContext();
			int padding = radius << 1;
			int maxWidth = getWidth() - padding;
			int maxHeight = getHeight() - padding;
			OcrService.scaleImage(filename, maxWidth, maxHeight, activity.getFunctionView(), activity);
		}
	}

	public void preQueryPixelPoint(Bitmap bitmap, double scaleY) {
		this.bitmap = bitmap;
		this.scaleY = scaleY;
		initRotationBitmap();
		resetLargestPoints();
		queryPixelPoint();
	}

	private void resetLargestPoints() {
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		int left = (getWidth() - bitmapWidth) >> 1;
		int top = (getHeight() - bitmapHeight) >> 1;
		int right = left + bitmapWidth;
		int bottom = top + bitmapHeight;

		largestPoints.get(0).set(left, top);
		largestPoints.get(2).set(right, top);
		largestPoints.get(5).set(left, bottom);
		largestPoints.get(7).set(right, bottom);
		updateCenterPoints(largestPoints);
	}

	public void queryPixelPoint() {
		BaseActivity activity = (BaseActivity) getContext();
		TextView tv_threshold = (TextView) activity.findViewById(R.id.tv_threshold);
		String threshold_str = tv_threshold.getText().toString();
		int threshold = Integer.parseInt(threshold_str);
		OcrService.queryPixelPoint(threshold, bitmap, activity.getFunctionView(), activity);
	}

	public void setPoints(Point pointLeftTop, Point pointRightTop, Point pointLeftBottom, Point pointRightBottom) {
		if (Math.abs(pointLeftTop.x - pointRightTop.x) < Math.abs(pointLeftTop.y - pointRightTop.y)) {
			Point pointLeftTopCopy = new Point(pointLeftTop);
			Point pointRightTopCopy = new Point(pointRightTop);
			Point pointLeftBottomCopy = new Point(pointLeftBottom);
			Point pointRightBottomCopy = new Point(pointRightBottom);

			pointLeftBottom.set(pointLeftTopCopy.x, pointLeftTopCopy.y);
			pointLeftTop.set(pointRightTopCopy.x, pointRightTopCopy.y);
			pointRightTop.set(pointRightBottomCopy.x, pointRightBottomCopy.y);
			pointRightBottom.set(pointLeftBottomCopy.x, pointLeftBottomCopy.y);
		}
		int left = (getWidth() - bitmap.getWidth()) >> 1;
		int top = (getHeight() - bitmap.getHeight()) >> 1;
		pointLeftTop.set(pointLeftTop.x + left, pointLeftTop.y + top);
		pointRightBottom.set(pointRightBottom.x + left, pointRightBottom.y + top);
		pointRightTop.set(pointRightTop.x + left, pointRightTop.y + top);
		pointLeftBottom.set(pointLeftBottom.x + left, pointLeftBottom.y + top);

		realPoints.set(0, pointLeftTop);
		realPoints.set(2, pointRightTop);
		realPoints.set(5, pointLeftBottom);
		realPoints.set(7, pointRightBottom);
		updateCenterPoints(realPoints);
		currentPoints = realPoints;

		notifyInvalidate();
	}

	public void convertPoints() {
		if (currentPoints == realPoints) {
			currentPoints = largestPoints;
		} else if (currentPoints == largestPoints) {
			currentPoints = realPoints;
		}
		notifyInvalidate();
	}

	public void deskewView() {
		BaseActivity activity = (BaseActivity) getContext();
		int left = (getWidth() - bitmap.getWidth()) >> 1;
		int top = (getHeight() - bitmap.getHeight()) >> 1;
		double scale = scaleY;
		if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
			scale = scaleX;
		}
		org.opencv.core.Point pointLeftTop = new org.opencv.core.Point((currentPoints.get(0).x - left) / scale,
				(currentPoints.get(0).y - top) / scale);
		org.opencv.core.Point pointRightBottom = new org.opencv.core.Point((currentPoints.get(7).x - left) / scale,
				(currentPoints.get(7).y - top) / scale);
		org.opencv.core.Point pointRightTop = new org.opencv.core.Point((currentPoints.get(2).x - left) / scale,
				(currentPoints.get(2).y - top) / scale);
		org.opencv.core.Point pointLeftBottom = new org.opencv.core.Point((currentPoints.get(5).x - left) / scale,
				(currentPoints.get(5).y - top) / scale);

		OcrService.perspectiveTransform(filename, getAngle(), pointLeftTop, pointRightTop, pointLeftBottom,
				pointRightBottom, activity.getFunctionView(), activity);
	}

	public void rotationView(int degree) {
		boolean clockwise = degree > 0;
		switch (direction) {
		case DIRECTION_TOP:
			bitmap = clockwise ? bitmapRight : bitmapLeft;
			direction = clockwise ? DIRECTION_RIGHT : DIRECTION_LEFT;
			break;
		case DIRECTION_BOTTOM:
			bitmap = clockwise ? bitmapLeft : bitmapRight;
			direction = clockwise ? DIRECTION_LEFT : DIRECTION_RIGHT;
			break;
		case DIRECTION_LEFT:
			bitmap = clockwise ? bitmapTop : bitmapBottom;
			direction = clockwise ? DIRECTION_TOP : DIRECTION_BOTTOM;
			break;
		case DIRECTION_RIGHT:
			bitmap = clockwise ? bitmapBottom : bitmapTop;
			direction = clockwise ? DIRECTION_BOTTOM : DIRECTION_TOP;
			break;
		}
		resetLargestPoints();
		currentPoints = largestPoints;
		notifyInvalidate();
		queryPixelPoint();
	}

	public int getAngle() {
		int angle = 0;
		switch (direction) {
		case DIRECTION_TOP:
			angle = 0;
			break;
		case DIRECTION_BOTTOM:
			angle = 180;
			break;
		case DIRECTION_LEFT:
			angle = 90;
			break;
		case DIRECTION_RIGHT:
			angle = -90;
			break;
		}
		return angle;
	}

	public void notifyInvalidate() {
		handler.sendEmptyMessage(WHAT_INVALIDATE);
	}

}
