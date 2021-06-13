package qlsl.androiddesign.view.subview.commonview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.SendManagerActivity;
import qlsl.androiddesign.adapter.commonadapter.SendManagerAdapter;
import qlsl.androiddesign.properties.OrderedProperties;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.util.commonutil.InputMatch;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.quickaction.ActionItem;
import qlsl.androiddesign.view.quickaction.QuickAction;
import qlsl.androiddesign.view.quickaction.QuickAction.OnActionItemClickListener;

/**
 * Log日志的发送管理类,主要功能：<br/>
 * 预设收件人，添加收件人，修改收件人，删除收件人<br/>
 * 
 */
public class SendManagerView extends FunctionView<SendManagerActivity>implements OnActionItemClickListener {

	/**
	 * 存储日志接收者信息的文件的名称<br/>
	 * 文件路径在data/data/包名/files目录下<br/>
	 */
	private final String LOG_RECEIVER_FILE_NAME = "log-receiver.txt";

	private OrderedProperties properties;

	private ListView listView;

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	private QuickAction quickAction;

	private final String[] actionItemNames = new String[] { "编辑", "删除" };

	public SendManagerView(SendManagerActivity activity) {
		super(activity);
		setContentView(R.layout.activity_send_manager);
	}

	protected void initView(View view) {
		showRightButton();
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		listView = (ListView) view.findViewById(R.id.listView);
		quickAction = new QuickAction(activity, QuickAction.HORIZONTAL);
		quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
		for (int index = 0; index < actionItemNames.length; index++) {
			ActionItem actionItem = new ActionItem(index, actionItemNames[index]);
			actionItem.setSticky(true);
			quickAction.addActionItem(actionItem);
		}
	}

	protected void initData() {
		setTitle("发送管理");
		setRightButtonResource(R.drawable.btn_add1);
		presettingReceivers();
		updateListViewData();
	}

	/**
	 * 预设收件人<br/>
	 */
	private void presettingReceivers() {
		String file_dir = activity.getFilesDir().getAbsolutePath() + File.separator;
		File file = new File(file_dir + LOG_RECEIVER_FILE_NAME);
		properties = new OrderedProperties();
		try {
			if (!file.exists()) {
				String[] receivers = activity.getResources().getStringArray(R.array.presetting_log_receiver);
				for (String current_receiver : receivers) {
					String[] receiver = current_receiver.split(":");
					properties.put(receiver[0], receiver[1]);
				}
				synchToFile();
			} else {
				FileInputStream in = activity.openFileInput(LOG_RECEIVER_FILE_NAME);
				properties.load(in);
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void synchToFile() {
		try {
			FileOutputStream out = activity.openFileOutput(LOG_RECEIVER_FILE_NAME, Context.MODE_PRIVATE);
			properties.store(out, "收件人信息");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateListViewData() {
		SendManagerAdapter adapter = (SendManagerAdapter) listView.getAdapter();
		properties.updateList(list, "name", "email");
		if (adapter == null) {
			adapter = new SendManagerAdapter(activity, list);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	protected void initListener() {
		quickAction.setOnActionItemClickListener(this);
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
		case R.id.iv_menu:
			doClickMenuView(view);
			break;

		}
	}

	public void onClick(DialogInterface dialogInterface, int which) {
		if (which == -1) {
			AlertDialog dialog = (AlertDialog) dialogInterface;
			ViewGroup parent = (ViewGroup) dialog.findViewById(android.R.id.custom);
			ViewGroup rootView = (ViewGroup) parent.getChildAt(0);

			EditText et_name = (EditText) rootView.findViewById(R.id.et_name);
			EditText et_email = (EditText) rootView.findViewById(R.id.et_email);
			String name = et_name.getText().toString();
			String email = et_email.getText().toString();
			if (TextUtils.isEmpty(name)) {
				showToast("名字为空！");
				return;
			}
			if (TextUtils.isEmpty(email)) {
				showToast("邮箱为空！");
				return;
			}
			if (!InputMatch.isEmail(email)) {
				showToast("邮箱输入不合法！");
				return;
			}
			if (rootView.getTag().toString().equals("更新")) {
				remove();
			}
			properties.put(name, email);
			synchToFile();
			updateListViewData();
		}
	}

	private void doClickRightButton() {
		DialogUtil.showEditTextDialog(activity, "添加收件人", null, R.layout.dialog_receiver, "添加", null, null, 1);
	}

	private void doClickMenuView(View view) {
		quickAction.show(view);
	}

	public void onItemClick(QuickAction source, int pos, int actionId) {
		switch (pos) {
		case 0:
			doClickEditView();
			break;
		case 1:
			doClickDeleteView();
			break;
		}
	}

	private void doClickEditView() {
		ViewGroup parent = (ViewGroup) quickAction.getAnchor().getParent();
		TextView tv_name = (TextView) parent.findViewById(R.id.tv_name);
		TextView tv_email = (TextView) parent.findViewById(R.id.tv_email);
		String name = tv_name.getText().toString();
		String email = tv_email.getText().toString();
		DialogUtil.showEditTextDialog(activity, "更新收件人", null, R.layout.dialog_receiver, "更新",
				new String[] { name, email }, null, 1);
	}

	private void doClickDeleteView() {
		remove();
		synchToFile();
		updateListViewData();
	}

	private void remove() {
		ViewGroup parent = (ViewGroup) quickAction.getAnchor().getParent();
		TextView tv_name = (TextView) parent.findViewById(R.id.tv_name);
		String key = tv_name.getText().toString();
		properties.remove(key);
	}

}
