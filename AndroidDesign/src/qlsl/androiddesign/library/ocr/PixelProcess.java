package qlsl.androiddesign.library.ocr;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

/**
 * 通过Robert算子检测边缘的方法检测角点<br/>
 * 查找方向：四点方向的查找需要统一基准<br/>
 * 以左上点先纵向再横向的统一基准推导其它三点方向<br/>
 */
public class PixelProcess {

	private static int COLOR_GAP_MIN;

	private static int COLOR_GAP_MAX;

	static {
		resetColor();
	}

	/**
	 * 检测角点<br/>
	 * 耗时，需在异线程中调用<br/>
	 */
	public static List<Point> queryPixelPoint(Bitmap srcBitmap, int mainColor) {
		int width = srcBitmap.getWidth();
		int height = srcBitmap.getHeight();
		List<Point> list = new ArrayList<Point>();
		Point pointLeftTop = getLeftTopPoint(mainColor, srcBitmap, width, height);
		Point pointRightTop = getRightTopPoint(mainColor, srcBitmap, width, height);
		Point pointLeftBottom = getLeftBottomPoint(mainColor, srcBitmap, width, height);
		Point pointRightBottom = getRightBottomPoint(mainColor, srcBitmap, width, height);
		list.add(pointLeftTop);
		list.add(pointRightTop);
		list.add(pointLeftBottom);
		list.add(pointRightBottom);
		return list;
	}

	private static Point getLeftTopPoint(int mainColor, Bitmap srcBitmap, int width, int height) {
		Point point = new Point();
		for (int i = 1; i < width - 2; i++) {
			for (int j = 1; j < height - 2; j++) {
				int redT = Color.red(mainColor);
				int greenT = Color.green(mainColor);
				int blueT = Color.blue(mainColor);

				int color = srcBitmap.getPixel(i, j);
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				int colorM = srcBitmap.getPixel(i - 1, j);
				int redM = Color.red(colorM);
				int greenM = Color.green(colorM);
				int blueM = Color.blue(colorM);

				int colorN = srcBitmap.getPixel(i, j - 1);
				int redN = Color.red(colorN);
				int greenN = Color.green(colorN);
				int blueN = Color.blue(colorN);

				int colorK = srcBitmap.getPixel(i - 1, j - 1);
				int redK = Color.red(colorK);
				int greenK = Color.green(colorK);
				int blueK = Color.blue(colorK);

				int tempR = Math.abs(red - redM) + Math.abs(red - redN) + Math.abs(red - redK);
				int tempG = Math.abs(green - greenM) + Math.abs(green - greenN) + Math.abs(green - greenK);
				int tempB = Math.abs(blue - blueM) + Math.abs(blue - blueN) + Math.abs(blue - blueK);
				int gapR = Math.abs(red - redT);
				int gapG = Math.abs(green - greenT);
				int gapB = Math.abs(blue - blueT);

				if (tempR + tempG + tempB > COLOR_GAP_MIN && gapR + gapG + gapB < COLOR_GAP_MAX) {
					point.set(i, j);
					return point;
				}
			}
		}
		return point;
	}

	private static Point getRightTopPoint(int mainColor, Bitmap srcBitmap, int width, int height) {
		Point point = new Point();
		for (int j = 1; j < height - 2; j++) {
			for (int i = width - 2; i > 2; i--) {
				int redT = Color.red(mainColor);
				int greenT = Color.green(mainColor);
				int blueT = Color.blue(mainColor);

				int color = srcBitmap.getPixel(i, j);
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				int colorM = srcBitmap.getPixel(i + 1, j);
				int redM = Color.red(colorM);
				int greenM = Color.green(colorM);
				int blueM = Color.blue(colorM);

				int colorN = srcBitmap.getPixel(i, j - 1);
				int redN = Color.red(colorN);
				int greenN = Color.green(colorN);
				int blueN = Color.blue(colorN);

				int colorK = srcBitmap.getPixel(i + 1, j - 1);
				int redK = Color.red(colorK);
				int greenK = Color.green(colorK);
				int blueK = Color.blue(colorK);

				int tempR = Math.abs(red - redM) + Math.abs(red - redN) + Math.abs(red - redK);
				int tempG = Math.abs(green - greenM) + Math.abs(green - greenN) + Math.abs(green - greenK);
				int tempB = Math.abs(blue - blueM) + Math.abs(blue - blueN) + Math.abs(blue - blueK);
				int gapR = Math.abs(red - redT);
				int gapG = Math.abs(green - greenT);
				int gapB = Math.abs(blue - blueT);

				if (tempR + tempG + tempB > COLOR_GAP_MIN && gapR + gapG + gapB < COLOR_GAP_MAX) {
					point.set(i, j);
					return point;
				}
			}
		}
		return point;
	}

	private static Point getLeftBottomPoint(int mainColor, Bitmap srcBitmap, int width, int height) {
		Point point = new Point();
		for (int j = height - 2; j > 0; j--) {
			for (int i = 1; i < width - 2; i++) {
				int redT = Color.red(mainColor);
				int greenT = Color.green(mainColor);
				int blueT = Color.blue(mainColor);

				int color = srcBitmap.getPixel(i, j);
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				int colorM = srcBitmap.getPixel(i - 1, j);
				int redM = Color.red(colorM);
				int greenM = Color.green(colorM);
				int blueM = Color.blue(colorM);

				int colorN = srcBitmap.getPixel(i, j + 1);
				int redN = Color.red(colorN);
				int greenN = Color.green(colorN);
				int blueN = Color.blue(colorN);

				int colorK = srcBitmap.getPixel(i - 1, j + 1);
				int redK = Color.red(colorK);
				int greenK = Color.green(colorK);
				int blueK = Color.blue(colorK);

				int tempR = Math.abs(red - redM) + Math.abs(red - redN) + Math.abs(red - redK);
				int tempG = Math.abs(green - greenM) + Math.abs(green - greenN) + Math.abs(green - greenK);
				int tempB = Math.abs(blue - blueM) + Math.abs(blue - blueN) + Math.abs(blue - blueK);
				int gapR = Math.abs(red - redT);
				int gapG = Math.abs(green - greenT);
				int gapB = Math.abs(blue - blueT);

				if (tempR + tempG + tempB > COLOR_GAP_MIN && gapR + gapG + gapB < COLOR_GAP_MAX) {
					point.set(i, j);
					return point;
				}
			}
		}
		return point;
	}

	private static Point getRightBottomPoint(int mainColor, Bitmap srcBitmap, int width, int height) {
		Point point = new Point();
		for (int i = width - 2; i > 0; i--) {
			for (int j = height - 2; j > 0; j--) {
				int redT = Color.red(mainColor);
				int greenT = Color.green(mainColor);
				int blueT = Color.blue(mainColor);

				int color = srcBitmap.getPixel(i, j);
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				int colorM = srcBitmap.getPixel(i + 1, j);
				int redM = Color.red(colorM);
				int greenM = Color.green(colorM);
				int blueM = Color.blue(colorM);

				int colorN = srcBitmap.getPixel(i, j + 1);
				int redN = Color.red(colorN);
				int greenN = Color.green(colorN);
				int blueN = Color.blue(colorN);

				int colorK = srcBitmap.getPixel(i + 1, j + 1);
				int redK = Color.red(colorK);
				int greenK = Color.green(colorK);
				int blueK = Color.blue(colorK);

				int tempR = Math.abs(red - redM) + Math.abs(red - redN) + Math.abs(red - redK);
				int tempG = Math.abs(green - greenM) + Math.abs(green - greenN) + Math.abs(green - greenK);
				int tempB = Math.abs(blue - blueM) + Math.abs(blue - blueN) + Math.abs(blue - blueK);
				int gapR = Math.abs(red - redT);
				int gapG = Math.abs(green - greenT);
				int gapB = Math.abs(blue - blueT);

				if (tempR + tempG + tempB > COLOR_GAP_MIN && gapR + gapG + gapB < COLOR_GAP_MAX) {
					point.set(i, j);
					return point;
				}
			}
		}
		return point;
	}

	public static void resetColor() {
		COLOR_GAP_MIN = 64;
		COLOR_GAP_MAX = 64;
	}

	public static void setColor(int min_color, int max_color) {
		COLOR_GAP_MIN = min_color;
		COLOR_GAP_MAX = max_color;
	}

	public static int getMinColor() {
		return COLOR_GAP_MIN;
	}

	public static int getMaxColor() {
		return COLOR_GAP_MAX;
	}

}
