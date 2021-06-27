package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.activity.commonactivity.MemberForgetPasswordActivity;
import qlsl.androiddesign.activity.commonactivity.MemberLoginActivity;
import qlsl.androiddesign.activity.commonactivity.MemberRegistActivity;
import qlsl.androiddesign.adapter.commonadapter.ForeignLoginGridAdapter;
import qlsl.androiddesign.view.baseview.FunctionView;

public class MemberLoginView extends FunctionView<MemberLoginActivity>implements OnItemClickListener {

	private EditText et_account, et_password;

	private GridView gridView;

	public MemberLoginView(MemberLoginActivity activity) {
		super(activity);
		setContentView(R.layout.activity_member_login_new);
	}

	protected void initView(View view) {
		setTitle("登录");
		setRightButtonText("注册");
		showRightButton();
		hideBackButton();
		setTitleBarBackgroundResource(R.color.text_color_title_login);
		setTitleBarTextColor(activity.getResources().getColor(R.color.white));
		et_account = findViewById(R.id.et_account);
		et_password = findViewById(R.id.et_password);
		gridView = findViewById(R.id.gridView);
	}

	protected void initData() {
		setGridViewData();
	}

	protected void initListener() {
		gridView.setOnItemClickListener(this);
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
		case R.id.btn_login:
			doClickLoginButton();
			break;
		case R.id.iv_open:
			doClickOpenView();
			break;
		case R.id.tv_forget_pwd:
			doClickForgetPwdView();
			break;
		}
	}

	private void doClickRightButton() {
		startActivity(MemberRegistActivity.class);
	}

	private void doClickLoginButton() {
//		String account = et_account.getText().toString();
//		String password = et_password.getText().toString();
//		if (TextUtils.isEmpty(account)) {
//			showToast("请输入邮箱或手机号");
//			return;
//		}
//		if (TextUtils.isEmpty(password)) {
//			showToast("请输入密码");
//			return;
//		}
//		if (!InputMatch.isEmail(account) && !InputMatch.isMobileNO(account)) {
//			showToast("邮箱或手机格式不正确");
//			return;
//		}
//		if (password.length() < 6) {
//			showToast("密码不得小于6位");
//			return;
//		}
//		MemberService.loginByEmail(account, password, this, activity);
		
		startActivity(MainActivity.class, R.anim.in_translate_top, R.anim.out_translate_top);
		activity.finish();
	}

	private void doClickOpenView() {
		if (et_password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
			et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		Editable etable = et_password.getText();
		Selection.setSelection(etable, etable.length());
	}

	private void doClickForgetPwdView() {
		startActivity(MemberForgetPasswordActivity.class);
	}

	private void setGridViewData() {
		Integer[] icons = new Integer[] { R.drawable.umeng_socialize_qq_on };
		String[] texts = new String[] { "" };
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int index = 0; index < icons.length; index++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icons[index]);
			map.put("text", texts[index]);
			list.add(map);
		}
		ForeignLoginGridAdapter adapter = new ForeignLoginGridAdapter(activity, list);
		gridView.setAdapter(adapter);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		LoginUtils.openOauthVerify(activity, position, 0);
		showToast("请填写相关账号");
	}

}
