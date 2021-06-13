package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.PhotoBrowseView;

public class PhotoBrowseActivity extends BaseActivity {

	private PhotoBrowseView functionView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new PhotoBrowseView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
