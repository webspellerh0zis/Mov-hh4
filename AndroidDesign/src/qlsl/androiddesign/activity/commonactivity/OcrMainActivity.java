package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.OcrMainView;

public class OcrMainActivity extends BaseActivity {

	private OcrMainView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new OcrMainView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
