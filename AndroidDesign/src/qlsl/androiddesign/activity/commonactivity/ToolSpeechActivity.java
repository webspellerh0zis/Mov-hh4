package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.ToolSpeechView;

public class ToolSpeechActivity extends BaseActivity {

	private ToolSpeechView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new ToolSpeechView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void onDbSucceed(String method, Object values) {
		functionView.hideProgressBar();
		if (method.equals("queryToolSpeech")) {
			if (values != null) {
				functionView.showData(values);
			}
		} else if (method.equals("updateToolSpeech")) {
			super.onDbSucceed(method, values);
		} else if (method.equals("resetToolSpeech")) {
			super.onDbSucceed(method, values);
		}
	}

}
