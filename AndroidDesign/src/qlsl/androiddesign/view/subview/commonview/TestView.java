package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import qlsl.androiddesign.activity.commonactivity.TestActivity;
import qlsl.androiddesign.view.baseview.FunctionView;

public class TestView extends FunctionView<TestActivity> {

	public TestView(TestActivity activity) {
		super(activity);
		setContentView(R.layout.activity_copy);
	}

	protected void initView(View view) {
		setTitle("");
	}

	protected void initData() {

	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

}
