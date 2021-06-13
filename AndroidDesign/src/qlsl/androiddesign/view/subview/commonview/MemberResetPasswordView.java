package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import qlsl.androiddesign.activity.commonactivity.MemberResetPasswordActivity;
import qlsl.androiddesign.http.service.commonservice.MemberService;
import qlsl.androiddesign.util.commonutil.InputMatch;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 重置密码页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class MemberResetPasswordView extends FunctionView<MemberResetPasswordActivity>implements OnClickListener {

	/**
	 * 旧密码，新密码
	 */
	private EditText et_old_pwd, et_new_pwd;

	/**
	 * 是否不可见
	 */
	private boolean password_new = true;

	/**
	 * 是否不可见
	 */
	private boolean password_old = true;

	public MemberResetPasswordView(MemberResetPasswordActivity activity) {
		super(activity);
		setContentView(R.layout.activity_member_reset_password_new);
	}

	protected void initView(View view) {
		setTitle("密码修改");
		et_old_pwd = (EditText) view.findViewById(R.id.et_old_pwd);
		et_new_pwd = (EditText) view.findViewById(R.id.et_new_pwd);
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
		case R.id.btn_ok:
			doClickOKView();
			break;
		case R.id.iv_open_old:
			doClickOpenOldView();
			break;
		case R.id.iv_open_new:
			doClickOpenNewView();
			break;
		}
	}

	private void doClickOKView() {
		String old_pwd = et_old_pwd.getText().toString();
		String new_pwd = et_new_pwd.getText().toString();
		Log.checkDebugModel(activity, old_pwd);
		if (TextUtils.isEmpty(old_pwd)) {
			showToast("请输入旧密码");
			return;
		}
		if (TextUtils.isEmpty(new_pwd)) {
			showToast("请输入新密码");
			return;
		}
		if (old_pwd.length() < 6 || new_pwd.length() < 6) {
			showToast("密码长度不得小于6位");
			return;
		}
		if (InputMatch.isEnsurePassword(old_pwd, new_pwd)) {
			showToast("旧密码与新密码相同");
			return;
		}
		MemberService.resetPassword(old_pwd, new_pwd, this, activity);
	}

	/**
	 * 点击后显示数字页<br/>
	 * 无法回到字母页<br/>
	 */
	private void doClickOpenOldView() {
		String digits = activity.getString(R.string.password_digits);
		DigitsKeyListener keyListener = DigitsKeyListener.getInstance(digits);
		if (!password_old) {
			et_old_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			et_old_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		et_old_pwd.setKeyListener(keyListener);
		Editable etable = et_old_pwd.getText();
		Selection.setSelection(etable, etable.length());
		password_old = !password_old;
	}

	/**
	 * 点击后显示数字页<br/>
	 * 无法回到字母页<br/>
	 */
	private void doClickOpenNewView() {
		String digits = activity.getString(R.string.password_digits);
		DigitsKeyListener keyListener = DigitsKeyListener.getInstance(digits);
		if (!password_new) {
			et_new_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			et_new_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		et_new_pwd.setKeyListener(keyListener);
		Editable etable = et_new_pwd.getText();
		Selection.setSelection(etable, etable.length());
		password_new = !password_new;
	}
}