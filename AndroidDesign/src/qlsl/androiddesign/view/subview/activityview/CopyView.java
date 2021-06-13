package qlsl.androiddesign.view.subview.activityview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import qlsl.androiddesign.activity.subactivity.CopyActivity;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * ****页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class CopyView extends FunctionView<CopyActivity> {

	public CopyView(CopyActivity activity) {
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
