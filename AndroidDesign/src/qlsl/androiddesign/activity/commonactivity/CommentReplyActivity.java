package qlsl.androiddesign.activity.commonactivity;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.CommentReplyView;

public class CommentReplyActivity extends BaseActivity {

	private CommentReplyView functionView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		functionView = new CommentReplyView(this);
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

}
