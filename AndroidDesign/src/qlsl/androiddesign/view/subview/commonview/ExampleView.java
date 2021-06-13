package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import qlsl.androiddesign.activity.commonactivity.ExampleActivity;
import qlsl.androiddesign.view.baseview.FunctionView;

public class ExampleView extends FunctionView<ExampleActivity> {

	public ExampleView(ExampleActivity activity) {
		super(activity);
		setContentView(R.layout.activity_example);
	}

	protected void initView(View view) {
		setTitle("ExampleActivity");
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
