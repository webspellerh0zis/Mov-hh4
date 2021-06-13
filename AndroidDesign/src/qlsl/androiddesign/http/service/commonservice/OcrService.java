package qlsl.androiddesign.http.service.commonservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.library.ocr.CVUtils;
import qlsl.androiddesign.library.ocr.ColorBlobDetector;
import qlsl.androiddesign.library.ocr.ImageProcess;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.CacheUtils;
import qlsl.androiddesign.util.commonutil.ScreenUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * Ocr模块 <br/>
 * 
 */
public class OcrService extends BaseService {

	private static String className = getClassName(OcrService.class);

	/**
	 * 缩放图片<br/>
	 */
	public static void scaleImage(final String filename, final double maxWidth, final double maxHeight,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在缩放图片");
		final HttpHandler handler = getHandler(functionView, listener, className, "scaleImage");
		new HandlerThread(className, "scaleImage") {
			public void run() {
				try {
					Mat matOri = Imgcodecs.imread(filename, 2 | 4);
					Mat matRgba = new Mat();
					Imgproc.cvtColor(matOri, matRgba, Imgproc.COLOR_BGR2RGBA);
					double bitmapWidth = matOri.cols();
					double bitmapHeight = matOri.rows();
					double sx = maxWidth / bitmapWidth;
					double sy = maxHeight / bitmapHeight;
					double scale = 1;
					Bitmap bitmap = null;
					if (sx < 1 || sy < 1) {
						scale = sx < sy ? sx : sy;
						Mat matDst = new Mat();
						Imgproc.resize(matRgba, matDst, new Size(bitmapWidth * scale, bitmapHeight * scale), scale,
								scale, Imgproc.INTER_AREA);
						bitmap = Bitmap.createBitmap(matDst.cols(), matDst.rows(), Config.ARGB_8888);
						Utils.matToBitmap(matDst, bitmap);
					} else {
						bitmap = Bitmap.createBitmap(matOri.cols(), matOri.rows(), Config.ARGB_8888);
						Utils.matToBitmap(matOri, bitmap);
					}

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("bitmap", bitmap);
					map.put("scale", scale);

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, map);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_FAIL, "不支持此图片");
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.activity.finish();
						}
					});
				}
			};
		}.start();
	}

	/**
	 * 基于openCV的边缘检测<br/>
	 * 算法限制:形状必须是规则平行四边形<br/>
	 * 且相邻两个角点不能在屏幕水平或竖直线上<br/>
	 */
	public static void queryPixelPoint(final int threshold, final Bitmap bitmap, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在检测");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryPixelPoint");
		new HandlerThread(className, "queryPixelPoint") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Mat matClone = matOri.clone();
					Mat lines = new Mat();
					Mat cannyMat = new Mat();
					Imgproc.Canny(matClone, cannyMat, threshold, threshold * 3);
					Imgproc.HoughLinesP(cannyMat, lines, 1, Math.PI / 180, 50, 50, 10);

					List<Point> list = new ArrayList<Point>();
					Point pointLeft = new Point(bitmap.getWidth() - 1, 0);
					Point pointTop = new Point(0, bitmap.getHeight() - 1);
					Point pointBottom = new Point(0, 0);
					Point pointRight = new Point(0, 0);

					list.add(pointLeft);
					list.add(pointTop);
					list.add(pointBottom);
					list.add(pointRight);

					for (int row = 0; row < lines.rows(); row++) {
						for (int col = 0; col < lines.cols(); col++) {
							double[] vec = lines.get(row, col);
							int x1 = (int) vec[0];
							int y1 = (int) vec[1];
							int x2 = (int) vec[2];
							int y2 = (int) vec[3];
							boolean h = x1 < x2;
							boolean v = y1 < y2;
							int leftX = h ? x1 : x2;
							int leftY = h ? y1 : y2;
							int rightX = h ? x2 : x1;
							int rightY = h ? y2 : y1;
							int topX = v ? x1 : x2;
							int topY = v ? y1 : y2;
							int bottomX = v ? x2 : x1;
							int bottomY = v ? y2 : y1;
							if (leftX < pointLeft.x) {
								pointLeft.set(leftX, leftY);
							}
							if (rightX > pointRight.x) {
								pointRight.set(rightX, rightY);
							}
							if (topY < pointTop.y) {
								pointTop.set(topX, topY);
							}
							if (bottomY > pointBottom.y) {
								pointBottom.set(bottomX, bottomY);
							}
						}
					}
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 角点检测<br/>
	 */
	public static void queryPixelPointByTouch(final Point point, final int threshold, final Bitmap bitmap,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在检测");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryPixelPoint");
		new HandlerThread(className, "queryPixelPoint") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Mat matClone = matOri.clone();
					ColorBlobDetector mDetector = new ColorBlobDetector();

					int cols = matClone.cols();
					int rows = matClone.rows();

					int xOffset = (ScreenUtils.getScreenWidth(functionView.activity) - cols) / 2;
					int yOffset = (ScreenUtils.getScreenHeight(functionView.activity) - rows) / 2;

					int x = (int) point.x - xOffset;
					int y = (int) point.y - yOffset;

					if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) {
						return;
					}

					Mat matHsv = new Mat();
					int centerX = ScreenUtils.getScreenWidth(functionView.activity) >> 1;
					int centerY = ScreenUtils.getScreenHeight(functionView.activity) >> 1;
					Rect rect = new Rect();
					rect.width = 2;
					rect.height = 2;
					rect.x = centerX - 1;
					rect.y = centerY - 1;

					rect.x = (x > 4) ? x - 4 : 0;
					rect.y = (y > 4) ? y - 4 : 0;
					rect.width = (x + 4 < cols) ? x + 4 - rect.x : cols - rect.x;
					rect.height = (y + 4 < rows) ? y + 4 - rect.y : rows - rect.y;

					Mat subMat = matClone.submat(rect);

					Imgproc.cvtColor(subMat, matHsv, Imgproc.COLOR_RGB2HSV_FULL);
					Scalar scalar = Core.sumElems(matHsv);
					int pointCount = rect.width * rect.height;
					for (int i = 0; i < scalar.val.length; i++) {
						scalar.val[i] /= pointCount;
					}

					mDetector.setHsvColor(scalar);
					mDetector.process(matClone);
					List<MatOfPoint> contours = mDetector.getContours();

					List<Point> list = new ArrayList<Point>();
					Point pointLeft = new Point(bitmap.getWidth() - 1, 0);
					Point pointTop = new Point(0, bitmap.getHeight() - 1);
					Point pointBottom = new Point(0, 0);
					Point pointRight = new Point(0, 0);

					list.add(pointLeft);
					list.add(pointTop);
					list.add(pointBottom);
					list.add(pointRight);

					for (MatOfPoint matOfPoint : contours) {
						List<org.opencv.core.Point> points = matOfPoint.toList();
						for (org.opencv.core.Point point : points) {
							int currentX = (int) point.x;
							int currentY = (int) point.y;

							if (point.x < pointLeft.x) {
								pointLeft.set(currentX, currentY);
							}
							if (point.x > pointRight.x) {
								pointRight.set(currentX, currentY);
							}
							if (point.y < pointTop.y) {
								pointTop.set(currentX, currentY);
							}
							if (point.y > pointBottom.y) {
								pointBottom.set(currentX, currentY);
							}
						}
					}

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 基于openCV的边缘检测改成的角点检测,用于照相机预处理<br/>
	 */
	public static void queryPixelPoint(final Mat matOri, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在检测");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryPixelPoint");
		new HandlerThread(className, "queryPixelPoint") {
			public void run() {
				try {
					Mat matDst = new Mat();
					Mat matLines = new Mat();
					Imgproc.Canny(matOri, matDst, 95, 285);
					Imgproc.HoughLinesP(matDst, matLines, 1, Math.PI / 180, 100, 50, 10);

					List<org.opencv.core.Point> list = new ArrayList<org.opencv.core.Point>();
					org.opencv.core.Point pointLeft = new org.opencv.core.Point(matOri.cols() - 1, 0);
					org.opencv.core.Point pointTop = new org.opencv.core.Point(0, matOri.rows() - 1);
					org.opencv.core.Point pointBottom = new org.opencv.core.Point(matOri.cols() >> 1, 0);
					org.opencv.core.Point pointRight = new org.opencv.core.Point(0, matOri.rows() >> 1);

					list.add(pointLeft);
					list.add(pointTop);
					list.add(pointBottom);
					list.add(pointRight);

					for (int row = 0; row < matLines.rows(); row++) {
						for (int col = 0; col < matLines.cols(); col++) {
							double[] vec = matLines.get(row, col);
							double x1 = vec[0];
							double y1 = vec[1];
							double x2 = vec[2];
							double y2 = vec[3];
							boolean h = x1 < x2;
							boolean v = y1 < y2;
							double leftX = h ? x1 : x2;
							double leftY = h ? y1 : y2;
							double rightX = h ? x2 : x1;
							double rightY = h ? y2 : y1;
							double topX = v ? x1 : x2;
							double topY = v ? y1 : y2;
							double bottomX = v ? x2 : x1;
							double bottomY = v ? y2 : y1;
							if (leftX < pointLeft.x) {
								pointLeft.x = leftX;
								pointLeft.y = leftY;
							}
							if (rightX > pointRight.x) {
								pointRight.x = rightX;
								pointRight.y = rightY;
							}
							if (topY < pointTop.y) {
								pointTop.x = topX;
								pointTop.y = topY;
							}
							if (bottomY > pointBottom.y) {
								pointBottom.x = bottomX;
								pointBottom.y = bottomY;
							}
						}
					}
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 基于openCV的角点检测,用于照相机预处理<br/>
	 */
	public static void queryPixelPointNew(final Mat matOri, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在检测");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryPixelPoint");
		new HandlerThread(className, "queryPixelPoint") {
			public void run() {
				try {
					Mat matClone = matOri.clone();
					ColorBlobDetector mDetector = new ColorBlobDetector();

					Mat matHsv = new Mat();
					int centerX = ScreenUtils.getScreenWidth(functionView.activity) >> 1;
					int centerY = ScreenUtils.getScreenHeight(functionView.activity) >> 1;
					Rect rect = new Rect();
					rect.width = 200;
					rect.height = 200;
					rect.x = centerX - 100;
					rect.y = centerY - 100;
					Mat subMat = matClone.submat(rect);

					Imgproc.cvtColor(subMat, matHsv, Imgproc.COLOR_RGB2HSV_FULL);
					Scalar scalar = Core.sumElems(matHsv);
					int pointCount = rect.width * rect.height;
					for (int i = 0; i < scalar.val.length; i++) {
						scalar.val[i] /= pointCount;
					}

					mDetector.setHsvColor(scalar);
					mDetector.process(matClone);
					List<MatOfPoint> contours = mDetector.getContours();

					List<org.opencv.core.Point> list = new ArrayList<org.opencv.core.Point>();
					org.opencv.core.Point pointLeft = new org.opencv.core.Point(matOri.cols() - 1, 0);
					org.opencv.core.Point pointTop = new org.opencv.core.Point(0, matOri.rows() - 1);
					org.opencv.core.Point pointBottom = new org.opencv.core.Point(matOri.cols() >> 1, 0);
					org.opencv.core.Point pointRight = new org.opencv.core.Point(0, matOri.rows() >> 1);

					list.add(pointLeft);
					list.add(pointTop);
					list.add(pointBottom);
					list.add(pointRight);

					for (MatOfPoint matOfPoint : contours) {
						List<org.opencv.core.Point> points = matOfPoint.toList();
						for (org.opencv.core.Point point : points) {
							int currentX = (int) point.x;
							int currentY = (int) point.y;

							if (point.x < pointLeft.x) {
								pointLeft.x = currentX;
								pointLeft.y = currentY;
							}
							if (point.x > pointRight.x) {
								pointRight.x = currentX;
								pointRight.y = currentY;
							}
							if (point.y < pointTop.y) {
								pointTop.x = currentX;
								pointTop.y = currentY;
							}
							if (point.y > pointBottom.y) {
								pointBottom.x = currentX;
								pointBottom.y = currentY;
							}
						}
					}
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 透视变换,照相机中使用<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static void perspectiveTransform(final Mat matOri, final org.opencv.core.Point pointLeftTop,
			final org.opencv.core.Point pointRightTop, final org.opencv.core.Point pointLeftBottom,
			final org.opencv.core.Point pointRightBottom, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在矫正");
		final HttpHandler handler = getHandler(functionView, listener, className, "deskewBitmap");
		new HandlerThread(className, "deskewBitmap") {
			public void run() {
				try {
					Mat matClone = matOri.clone();

					org.opencv.core.Point[] src_array = new org.opencv.core.Point[4];
					org.opencv.core.Point[] dst_array = new org.opencv.core.Point[4];

					double dx = Math.sqrt(Math.pow(Math.abs(pointLeftTop.x - pointRightTop.x), 2)
							+ Math.pow(Math.abs(pointLeftTop.y - pointRightTop.y), 2));
					double dy = Math.sqrt(Math.pow(Math.abs(pointLeftTop.x - pointLeftBottom.x), 2)
							+ Math.pow(Math.abs(pointLeftTop.y - pointLeftBottom.y), 2));
					src_array[0] = pointLeftTop;
					src_array[1] = pointRightTop;
					src_array[2] = pointLeftBottom;
					src_array[3] = pointRightBottom;

					int rows = 0;
					int cols = 0;
					if (Math.abs(pointRightTop.x - pointLeftTop.x) > Math.abs(pointRightTop.y - pointLeftTop.y)) {
						dst_array[0] = new org.opencv.core.Point(0, 0);
						dst_array[1] = new org.opencv.core.Point(dx, 0);
						dst_array[2] = new org.opencv.core.Point(0, dy);
						dst_array[3] = new org.opencv.core.Point(dx, dy);
						cols = (int) dx;
						rows = (int) dy;
					} else {
						dst_array[0] = new org.opencv.core.Point(0, dx);
						dst_array[1] = new org.opencv.core.Point(0, 0);
						dst_array[2] = new org.opencv.core.Point(dy, dx);
						dst_array[3] = new org.opencv.core.Point(dy, 0);
						cols = (int) dy;
						rows = (int) dx;
					}
					if (rows == 0 || cols == 0) {
						handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_FAIL, "无法裁剪");
						return;
					}

					MatOfPoint2f src_point = new MatOfPoint2f(src_array);
					MatOfPoint2f dst_point = new MatOfPoint2f(dst_array);

					Mat matWarp = new Mat(new Size(2, 3), CvType.CV_32F);
					matWarp = Imgproc.getPerspectiveTransform(src_point, dst_point);
					Mat matDst = Mat.zeros(matClone.rows(), matClone.cols(), matClone.type());
					Imgproc.warpPerspective(matClone, matDst, matWarp, matDst.size());

					int rowMax = matDst.rows();
					int colMax = matDst.cols();
					int rowEnd = rows < rowMax ? rows : rowMax;
					int colEnd = cols < colMax ? cols : colMax;

					Mat matCrop = matDst.submat(0, rowEnd, 0, colEnd);
					double scale = 780d / colEnd;
					double dHeight = rowEnd * scale;
					double maxHeight = 780d * 2.5d;
					if (dHeight > maxHeight) {
						dHeight = maxHeight;
					}
					Mat matOut = null;
					if (scale < 1) {
						matOut = new Mat();
						Imgproc.resize(matCrop, matOut, new Size(780, dHeight), 0, 0, Imgproc.INTER_AREA);
					} else if (scale > 1) {
						matOut = new Mat();
						Imgproc.resize(matCrop, matOut, new Size(780, dHeight), 0, 0, Imgproc.INTER_LINEAR);
					} else {
						matOut = matCrop;
					}
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
					String timestamp = dateFormat.format(date);
					String imgName = "corner" + "_" + timestamp + ".png";

					String dictoryPath = CacheUtils.getCacheDirectory(functionView.activity, true, "picture/" + "ocr")
							.getAbsolutePath();
					String cornerFileName = dictoryPath + "/" + imgName;

					Imgcodecs.imwrite(cornerFileName, matOut);

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, cornerFileName);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 透视变换，角点检测页使用<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static void perspectiveTransform(final String filename, final int angle,
			final org.opencv.core.Point pointLeftTop, final org.opencv.core.Point pointRightTop,
			final org.opencv.core.Point pointLeftBottom, final org.opencv.core.Point pointRightBottom,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在矫正");
		final HttpHandler handler = getHandler(functionView, listener, className, "deskewBitmap");
		new HandlerThread(className, "deskewBitmap") {
			public void run() {
				try {
					Mat matOri = Imgcodecs.imread(filename, 2 | 4);
					Mat matDest = matOri;
					if (angle != 0) {
						matDest = CVUtils.rotation(matOri, angle, angle == 90 ? false : true);
					}

					org.opencv.core.Point[] src_array = new org.opencv.core.Point[4];
					org.opencv.core.Point[] dst_array = new org.opencv.core.Point[4];

					double dx = Math.sqrt(Math.pow(Math.abs(pointLeftTop.x - pointRightTop.x), 2)
							+ Math.pow(Math.abs(pointLeftTop.y - pointRightTop.y), 2));
					double dy = Math.sqrt(Math.pow(Math.abs(pointLeftTop.x - pointLeftBottom.x), 2)
							+ Math.pow(Math.abs(pointLeftTop.y - pointLeftBottom.y), 2));
					src_array[0] = pointLeftTop;
					src_array[1] = pointRightTop;
					src_array[2] = pointLeftBottom;
					src_array[3] = pointRightBottom;

					int rows = 0;
					int cols = 0;
					if (Math.abs(pointRightTop.x - pointLeftTop.x) > Math.abs(pointRightTop.y - pointLeftTop.y)) {
						dst_array[0] = new org.opencv.core.Point(0, 0);
						dst_array[1] = new org.opencv.core.Point(dx, 0);
						dst_array[2] = new org.opencv.core.Point(0, dy);
						dst_array[3] = new org.opencv.core.Point(dx, dy);
						cols = (int) dx;
						rows = (int) dy;
					} else {
						dst_array[0] = new org.opencv.core.Point(0, dx);
						dst_array[1] = new org.opencv.core.Point(0, 0);
						dst_array[2] = new org.opencv.core.Point(dy, dx);
						dst_array[3] = new org.opencv.core.Point(dy, 0);
						cols = (int) dy;
						rows = (int) dx;
					}
					if (rows == 0 || cols == 0) {
						handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_FAIL, "无法裁剪");
						return;
					}

					MatOfPoint2f src_point = new MatOfPoint2f(src_array);
					MatOfPoint2f dst_point = new MatOfPoint2f(dst_array);

					Mat matWarp = new Mat(new Size(2, 3), CvType.CV_32F);
					matWarp = Imgproc.getPerspectiveTransform(src_point, dst_point);
					Mat matDst = Mat.zeros(matDest.rows(), matDest.cols(), matDest.type());
					Imgproc.warpPerspective(matDest, matDst, matWarp, matDst.size());

					int rowMax = matDst.rows();
					int colMax = matDst.cols();
					int rowEnd = rows < rowMax ? rows : rowMax;
					int colEnd = cols < colMax ? cols : colMax;

					Mat matCrop = matDst.submat(0, rowEnd, 0, colEnd);
					double scale = 780d / colEnd;
					Mat matOut = null;
					if (scale < 1) {
						matOut = new Mat();
						Imgproc.resize(matCrop, matOut, new Size(780, rowEnd * scale), scale, scale,
								Imgproc.INTER_AREA);
					} else if (scale > 1) {
						matOut = new Mat();
						Imgproc.resize(matCrop, matOut, new Size(780, rowEnd * scale), scale, scale,
								Imgproc.INTER_LINEAR);
					} else {
						matOut = matCrop;
					}
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
					String timestamp = dateFormat.format(date);
					String imgName = "corner" + "_" + timestamp + ".png";

					String dictoryPath = CacheUtils.getCacheDirectory(functionView.activity, true, "picture/" + "ocr")
							.getAbsolutePath();
					String cornerFileName = dictoryPath + "/" + imgName;

					Imgcodecs.imwrite(cornerFileName, matOut);

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, cornerFileName);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 无增强<br/>
	 */
	public static void laplaceGradient(final Bitmap bitmap, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在锐化");
		final HttpHandler handler = getHandler(functionView, listener, className, "laplaceGradient");
		new HandlerThread(className, "laplaceGradient") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Mat matGray = new Mat();
					Mat matDst = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Mat matClone = matOri.clone();
					Imgproc.GaussianBlur(matClone, matClone, new Size(3, 3), 0, 0);
					Imgproc.cvtColor(matClone, matGray, Imgproc.COLOR_RGB2GRAY);
					Imgproc.Laplacian(matGray, matDst, matGray.type(), 3, 1, 0);

					Bitmap bitmapDst = Bitmap.createBitmap(matDst.cols(), matDst.rows(), Config.ARGB_8888);
					Utils.matToBitmap(matDst, bitmapDst);
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, bitmapDst);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();

	}

	/**
	 * 增强<br/>
	 */
	public static void filter(final Bitmap bitmap, final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在增强");
		final HttpHandler handler = getHandler(functionView, listener, className, "medianFilter");
		new HandlerThread(className, "medianFilter") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Mat matFilter = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Bitmap bitmapDst = Bitmap.createBitmap(matOri.cols(), matOri.rows(), Config.ARGB_8888);
					Utils.matToBitmap(matOri, bitmapDst);
					ImageProcess process = new ImageProcess();
					bitmapDst = process.laplaceGradient(bitmap);

					Utils.bitmapToMat(bitmapDst, matFilter);
					Imgproc.GaussianBlur(matFilter, matFilter, new Size(3, 3), 0, 0);
					Utils.matToBitmap(matFilter, bitmapDst);

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, bitmapDst);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();

	}

	/**
	 * 增强并锐化<br/>
	 */
	public static void filterAndGradient(final Bitmap bitmap, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在增强并锐化");
		final HttpHandler handler = getHandler(functionView, listener, className, "filterAndGradient");
		new HandlerThread(className, "filterAndGradient") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Bitmap bitmapDst = Bitmap.createBitmap(matOri.cols(), matOri.rows(), Config.ARGB_8888);
					Utils.matToBitmap(matOri, bitmapDst);
					ImageProcess process = new ImageProcess();
					bitmapDst = process.laplaceGradient(bitmap);

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, bitmapDst);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();

	}

	/**
	 * 灰度模式<br/>
	 */
	public static void grayModel(final Bitmap bitmap, final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在切换灰度模式");
		final HttpHandler handler = getHandler(functionView, listener, className, "grayModel");
		new HandlerThread(className, "grayModel") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Mat matGray = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Imgproc.cvtColor(matOri, matGray, Imgproc.COLOR_RGB2GRAY);
					Bitmap bitmapDst = Bitmap.createBitmap(matGray.cols(), matGray.rows(), Config.ARGB_8888);
					Utils.matToBitmap(matGray, bitmapDst);
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, bitmapDst);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();

	}

	/**
	 * 黑白文档<br/>
	 */
	public static void threshold(final Bitmap bitmap, final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在阈值化");
		final HttpHandler handler = getHandler(functionView, listener, className, "threshold");
		new HandlerThread(className, "threshold") {
			public void run() {
				try {
					Mat matOri = new Mat();
					Mat matGray = new Mat();
					Mat matBlur = new Mat();
					Mat matDst = new Mat();
					Utils.bitmapToMat(bitmap, matOri);
					Bitmap bitmapDst = Bitmap.createBitmap(matOri.cols(), matOri.rows(), Config.ARGB_8888);
					Mat matClone = matOri.clone();
					Imgproc.GaussianBlur(matClone, matBlur, new Size(3, 3), 0, 0);
					Imgproc.cvtColor(matBlur, matGray, Imgproc.COLOR_RGB2GRAY);
					Imgproc.adaptiveThreshold(matGray, matDst, 128, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
							Imgproc.THRESH_BINARY, 3, 3);
					Utils.matToBitmap(matDst, bitmapDst);
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, bitmapDst);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();

	}

}
