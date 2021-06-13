package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import qlsl.androiddesign.activity.commonactivity.MemberRegistActivity;
import qlsl.androiddesign.http.service.commonservice.MemberService;
import qlsl.androiddesign.util.commonutil.InputMatch;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 用户注册页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class MemberRegistView extends FunctionView<MemberRegistActivity> {

	private EditText et_phone, et_code, et_password;

	private Button btn_send;

	private boolean password = false;

	public MemberRegistView(MemberRegistActivity activity) {
		super(activity);
		setContentView(R.layout.activity_member_regist_real_new);
	}

	protected void initView(View view) {
		setTitle("注册");
		setTitleBarBackgroundResource(R.color.text_color_title_login);
		setTitleBarTextColor(activity.getResources().getColor(R.color.white));
		et_phone = findViewById(R.id.et_phone);
		et_code = findViewById(R.id.et_code);
		et_password = findViewById(R.id.et_password);
		btn_send = findViewById(R.id.btn_send);
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
		switch (view.getId()) {
		case R.id.btn_send:
			doClickSendButton();
			break;
		case R.id.btn_regist:
			doClickRegistButton();
			break;
		case R.id.iv_open:
			doClickOpenView();
			break;
		}
	}

	private void doClickSendButton() {
		if (!btn_send.getText().toString().equals("发送验证码")) {
			return;
		}
		String phone = et_phone.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			showToast("请输入手机号码");
			return;
		}
		if (!InputMatch.isMobileNO(phone)) {
			showToast("请输入正确格式的手机号码");
			return;
		}
		SendCodeTimer sendCodeTimer = new SendCodeTimer(60000, 1000);
		sendCodeTimer.start();
		MemberService.requestSMSCode(phone, sendCodeTimer, btn_send, this, activity);

	}

	private void doClickRegistButton() {
		String phone = et_phone.getText().toString();
		String code = et_code.getText().toString();
		String password = et_password.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			showToast("请输入手机号码");
			return;
		}
		if (!InputMatch.isMobileNO(phone)) {
			showToast("请输入正确格式的手机号码");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			showToast("请输入验证码");
			return;
		}
		if (!InputMatch.isNumeric(code)) {
			showToast("请输入数字验证码");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showToast("请输入密码");
			return;
		}
		MemberService.registByMobile(phone, code, password, this, activity);
	}

	private void doClickOpenView() {
		String digits = activity.getString(R.string.password_digits);
		DigitsKeyListener keyListener = DigitsKeyListener.getInstance(digits);
		if (!password) {
			et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		et_password.setKeyListener(keyListener);
		Editable etable = et_password.getText();
		Selection.setSelection(etable, etable.length());
		password = !password;
	}

	private class SendCodeTimer extends CountDownTimer {

		public SendCodeTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onTick(long millisUntilFinished) {
			btn_send.setText((millisUntilFinished / 1000) + "秒后重发");
		}

		public void onFinish() {
			btn_send.setText("发送验证码");
		}

	}

}
