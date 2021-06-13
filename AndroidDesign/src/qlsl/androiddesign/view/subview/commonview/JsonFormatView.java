package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.JsonFormatActivity;
import qlsl.androiddesign.http.service.commonservice.SystemService;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * Json数据浏览页<br/>
 * 需要传入的键：url<br/>
 * 传入的值类型： String<br/>
 * 传入的值含义：网络地址<br/>
 * 是否必传 ：是
 */
public class JsonFormatView extends FunctionView<JsonFormatActivity> {

	private TextView tv_text;

	public JsonFormatView(JsonFormatActivity activity) {
		super(activity);
		setContentView(R.layout.activity_json_format);
	}

	protected void initView(View view) {
		setTitle("数据浏览");
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		tv_text = findViewById(R.id.tv_text);
	}

	protected void initData() {
		queryJsonData();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {
		if (t[0] instanceof String) {
			String formatJson = (String) t[0];
			tv_text.setText(formatJson);
		}
	}

	public void onClick(View view) {

	}

	private void queryJsonData() {
		String url = activity.getIntent().getStringExtra("url");
		SystemService.queryJsonData(url, this, activity);
	}

}
