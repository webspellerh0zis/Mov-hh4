package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.OcrCornerView;

public class OcrCornerActivity extends BaseActivity {

	private OcrCornerView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		functionView = new OcrCornerView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onOtherSucceed(String method, Object values) {
		if (method.equals("scaleImage")) {
			functionView.showData(values);
		} else if (method.equals("queryPixelPoint")) {
			functionView.showData(values);
		} else if (method.equals("deskewBitmap")) {
			functionView.showData(values);
		}
	}

}
