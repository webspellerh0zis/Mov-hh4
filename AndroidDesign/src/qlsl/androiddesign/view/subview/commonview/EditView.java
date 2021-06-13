package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.EditActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.commonview.SingleEditText;

/**
 * 编辑页<br/>
 * 需要传入的键：title,content,maxLength,rightText<br/>
 * 传入的值类型： String,String,int,String<br/>
 * 传入的值含义：标题，内容，最大长度,右按钮文本<br/>
 * 是否必传 ：是，是，是，否
 */
public class EditView extends FunctionView<EditActivity> {

	private int maxLength = 40;

	private SingleEditText et_content;

	private TextView tv_sum;

	public EditView(EditActivity activity) {
		super(activity);
		setContentView(R.layout.activity_edit);
	}

	public void onPause() {
		et_content.onPause();
	}

	protected void initView(View view) {
		Intent intent = activity.getIntent();
		setTitle(intent.getStringExtra("title"));
		String rightText = intent.getStringExtra("rightText");
		setRightButtonText(rightText == null ? "保存" : rightText);
		showRightButton();
		et_content = (SingleEditText) view.findViewById(R.id.et_content);
		tv_sum = (TextView) view.findViewById(R.id.tv_sum);
		maxLength = intent.getIntExtra("maxLength", maxLength);
	}

	protected void initData() {
		et_content.setSleepSpan(80);
		et_content.startDrawThread(activity.getIntent().getStringExtra("content"), tv_sum, maxLength);
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_right:
			doClickRightButton();
			break;
		}
	}

	private void doClickRightButton() {
		String title = activity.getIntent().getStringExtra("title");
		String content = et_content.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("未输入");
			return;
		}

		if (title.contains("****")) {

		}
	}

}
