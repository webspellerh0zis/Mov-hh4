package qlsl.androiddesign.library.ocr;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * openCV工具类<br/>
 * 
 */
public class CVUtils {

	/**
	 * 旋转90度或180度,by yql at 20151114<br/>
	 * angle：只支持90,-90或180<br/>
	 * clockwise为true(false)表顺时针(逆时针)<br/>
	 */
	public static Mat rotation(Mat src, double angle, boolean clockwise) {
		int srcWidth = src.cols();
		int srcHeight = src.rows();

		int length = (int) Math.sqrt(srcWidth * srcWidth + srcHeight * srcHeight);
		Mat tempMat = new Mat(length, length, src.type());

		int roi_x = (length - srcWidth) / 2;
		int roi_y = (length - srcHeight) / 2;
		Rect rectTemp = new Rect(roi_x, roi_y, srcWidth, srcHeight);
		Mat subTemp = tempMat.submat(rectTemp);
		src.copyTo(subTemp);

		Mat matDst = new Mat();
		Mat matRotation = Imgproc.getRotationMatrix2D(new Point(length / 2, length / 2), angle, 1);
		Imgproc.warpAffine(tempMat, matDst, matRotation, new Size(length, length));

		Mat subDst = null;
		if (angle == 180) {
			Rect rectDst = new Rect(roi_x, roi_y, srcWidth, srcHeight);
			subDst = matDst.submat(rectDst);
		} else {
			Rect rectDst = new Rect(roi_y, roi_x, srcHeight, srcWidth);
			subDst = matDst.submat(rectDst);
		}
		return subDst;
	}
}
