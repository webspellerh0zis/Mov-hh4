package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.OcrDeskewView;

public class OcrDeskewActivity extends BaseActivity {

	private OcrDeskewView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		functionView = new OcrDeskewView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onOtherSucceed(String method, Object values) {
		if (method.equals("laplaceGradient")) {
			functionView.showData(values);
		} else if (method.equals("medianFilter")) {
			functionView.showData(values);
		} else if (method.equals("filterAndGradient")) {
			functionView.showData(values);
		} else if (method.equals("grayModel")) {
			functionView.showData(values);
		} else if (method.equals("threshold")) {
			functionView.showData(values);
		}
	}

}
