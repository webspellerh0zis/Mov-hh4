package qlsl.androiddesign.view.subview.commonview;

import java.util.List;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import qlsl.androiddesign.activity.commonactivity.OcrCameraActivity;
import qlsl.androiddesign.activity.commonactivity.OcrDeskewActivity;
import qlsl.androiddesign.http.service.commonservice.OcrService;
import qlsl.androiddesign.view.baseview.FunctionView;

public class OcrCameraView extends FunctionView<OcrCameraActivity> implements CvCameraViewListener2 {

	private static final int TIME_INTERVAL = 3 * 1000;

	private JavaCameraView cameraView;

	private Mat matOri;

	private long timeStart;

	private boolean isFinish;

	public OcrCameraView(OcrCameraActivity activity) {
		super(activity);
		setContentView(R.layout.activity_ocr_camera);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			matOri = (Mat) msg.obj;
			OcrService.queryPixelPoint(matOri, OcrCameraView.this, activity);
		};
	};

	public void onResume() {
		if (isFinish) {
			isFinish = false;
			cameraView.enableView();
		}

	};

	public void onPause() {
		cameraView.disableView();
	}

	public void onDestroy() {
		cameraView.disableView();
	}

	protected void initView(View view) {
		hideTitleBar();
		cameraView = findViewById(R.id.cameraView);
		cameraView.enableView();
	}

	protected void initData() {

	}

	protected void initListener() {
		cameraView.setCvCameraViewListener(this);
	}

	@SuppressWarnings("unchecked")
	public <T> void showData(T... t) {
		if (t[0] instanceof List) {
			List<Point> list = (List<Point>) t[0];
			OcrService.perspectiveTransform(matOri, list.get(0), list.get(1), list.get(2), list.get(3),
					OcrCameraView.this, activity);
		} else if (t[0] instanceof String) {
			String path = (String) t[0];
			Intent intent = new Intent(activity, OcrDeskewActivity.class);
			intent.putExtra("path", path);
			startActivity(intent);
			activity.finish();
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

	public void onCameraViewStarted(int width, int height) {
		timeStart = System.currentTimeMillis();
	}

	public void onCameraViewStopped() {

	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		Mat matOri = inputFrame.rgba();
		if (!isFinish && System.currentTimeMillis() - timeStart > TIME_INTERVAL) {
			Mat matCopy = matOri.clone();
			Message msg = new Message();
			msg.obj = matCopy;
			handler.sendMessage(msg);
			isFinish = true;
		}
		return matOri;
	}

}
