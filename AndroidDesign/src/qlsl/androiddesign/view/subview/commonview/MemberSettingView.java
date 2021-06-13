package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import qlsl.androiddesign.activity.commonactivity.AboutActivity;
import qlsl.androiddesign.activity.commonactivity.MemberLoginActivity;
import qlsl.androiddesign.activity.commonactivity.MemberResetPasswordActivity;
import qlsl.androiddesign.activity.commonactivity.MemberSettingActivity;
import qlsl.androiddesign.activity.commonactivity.ToolSettingActivity;
import qlsl.androiddesign.adapter.commonadapter.MemberSettingAdapter;
import qlsl.androiddesign.http.service.commonservice.SystemService;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.manager.DataCleanManager;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 个人中心设置页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class MemberSettingView extends FunctionView<MemberSettingActivity> {

	private ListView listView;

	public MemberSettingView(MemberSettingActivity activity) {
		super(activity);
		setContentView(R.layout.activity_member_setting);
	}

	protected void initView(View view) {
		setTitle("设置");
		listView = findViewById(R.id.listView);
	}

	protected void initData() {
		setListViewData();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.list_item:
			doClickListItem(view);
			break;
		}
	}

	private void setListViewData() {
		String[] names = new String[] { "基本设置", "清理缓存", "密码修改", "版本更新", "关于我们", "退出账户" };
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int index = 0; index < names.length; index++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text", names[index]);
			list.add(map);
		}
		MemberSettingAdapter adapter = new MemberSettingAdapter(activity, list);
		listView.setAdapter(adapter);
	}

	private void doClickListItem(View view) {
		int position = listView.getPositionForView(view);
		if (position == 0) {
			startActivity(ToolSettingActivity.class);
		} else if (position == 1) {
			showToast("正在清除缓存数据");
			DataCleanManager.cleanCacheData(activity);
			showToast("清除成功");
		} else if (position == 2) {
			startActivity(MemberResetPasswordActivity.class);
		} else if (position == 3) {
			SystemService.getUpdateInfo(this, activity);
		} else if (position == 4) {
			startActivity(AboutActivity.class);
		} else if (position == 5) {
			DialogUtil.showDialog(this, "注销登录", "确定退出当前登录用户?");
		}
	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			UserMethod.setUser(null);
			UserMethod.setToken(null);
			ActivityManager.getInstance().popAllActivity();
			startActivity(MemberLoginActivity.class);
		}
	}

}
