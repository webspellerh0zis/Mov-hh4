package qlsl.androiddesign.activity.commonactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.OcrCameraView;

public class OcrCameraActivity extends BaseActivity {

	private OcrCameraView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		functionView = new OcrCameraView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onOtherSucceed(String method, Object values) {
		if (method.equals("queryPixelPoint")) {
			functionView.showData(values);
		} else if (method.equals("deskewBitmap")) {
			functionView.showData(values);
		}
	}

	public void onOtherFaild(String method, Object values) {
		super.onOtherFaild(method, values);
		if (method.equals("deskewBitmap")) {
			finish();
		}
	}

}
