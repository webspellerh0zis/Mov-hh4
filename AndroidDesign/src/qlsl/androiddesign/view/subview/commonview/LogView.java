package qlsl.androiddesign.view.subview.commonview;

import static qlsl.androiddesign.util.commonutil.Log.isOutPhone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.EditText;
import qlsl.androiddesign.activity.commonactivity.LogActivity;
import qlsl.androiddesign.activity.commonactivity.LogSettingActivity;
import qlsl.androiddesign.activity.commonactivity.SendManagerActivity;
import qlsl.androiddesign.adapter.commonadapter.LogEmailAdapter;
import qlsl.androiddesign.library.email.MultiMailsender;
import qlsl.androiddesign.library.email.MultiMailsender.MultiMailSenderInfo;
import qlsl.androiddesign.listener.WebViewLoadListener;
import qlsl.androiddesign.properties.OrderedProperties;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.util.commonutil.InputMatch;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.quickaction.ActionItem;
import qlsl.androiddesign.view.quickaction.QuickAction;
import qlsl.androiddesign.view.quickaction.QuickAction.OnActionItemClickListener;

public class LogView extends FunctionView<LogActivity> implements OnActionItemClickListener {

	/**
	 * 存储日志接收者信息的文件的名称<br/>
	 * 文件路径在data/data/包名/files目录下<br/>
	 */
	private final String LOG_RECEIVER_FILE_NAME = "log-receiver.txt";

	private OrderedProperties properties;

	private WebView webView;

	private QuickAction quickAction;

	private final String[] actionItemNames = new String[] { "发送日志", "发送管理", "搜索标签", "日志设置", "清除日志" };

	private final int[] actionItemIcons = new int[] { R.drawable.app_icon_default, R.drawable.app_icon_default,
			R.drawable.app_icon_default, R.drawable.app_icon_default, R.drawable.app_icon_default };

	public LogView(LogActivity activity) {
		super(activity);
		setContentView(R.layout.activity_log);
	}

	@SuppressLint("SetJavaScriptEnabled")
	protected void initView(View view) {
		setTitle("程序日志");
		setRightButtonResource(R.drawable.btn_menu);
		showRightButton();
		setTitleBarBackgroundResource(R.drawable.common_title_view);
		setContentBarBackgroundResource(R.drawable.common_content_view);
		webView = (WebView) view.findViewById(R.id.webView);
		if (isOutPhone) {
			webView.setBackgroundColor(activity.getResources().getColor(R.color.bg_log));
		} else {
			webView.setBackgroundResource(R.drawable.common_content_view);
		}
		webView.setHorizontalScrollBarEnabled(false);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setWebViewClient(new WebViewLoadListener());

		quickAction = new QuickAction(activity, QuickAction.VERTICAL);
		quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
		for (int index = 0; index < actionItemNames.length; index++) {
			ActionItem actionItem = new ActionItem(index, actionItemNames[index],
					activity.getResources().getDrawable(actionItemIcons[index]));
			actionItem.setSticky(true);
			quickAction.addActionItem(actionItem);
		}
	}

	protected void initData() {
		showLogInfo();
		presettingReceivers();
	}

	private void showLogInfo() {
		String url = Log.getLogPath(activity);
		File file = new File(url);
		if (file.exists()) {
			webView.setVisibility(View.VISIBLE);
			webView.loadUrl("file://" + url);
		} else {
			webView.setVisibility(View.GONE);
		}
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
			doClickRightView(view);
			break;

		}
	}

	public void onClick(DialogInterface dialogInterface, int which) {
		if (which == -1) {
			String url = Log.getLogPath(activity);
			File file = new File(url);
			if (file.exists()) {
				AlertDialog dialog = (AlertDialog) dialogInterface;
				EditText et_name = (EditText) dialog.findViewById(R.id.et_name);
				EditText et_email = (EditText) dialog.findViewById(R.id.et_email);
				EditText et_title = (EditText) dialog.findViewById(R.id.et_title);
				EditText et_content = (EditText) dialog.findViewById(R.id.et_content);

				String name = et_name.getText().toString();
				String email = et_email.getText().toString();
				String title = et_title.getText().toString();
				String content = et_content.getText().toString();

				if (InputMatch.isEmail(email)) {
					if (TextUtils.isEmpty(name)) {
						name = "姓名";
					}
					if (TextUtils.isEmpty(title)) {
						title = "标题";
					}
					sendLogFileToEmail(file, name, email, title, content);
				} else {
					showToast("邮箱输入不合法！");
				}
			} else {
				showToast("Log日志文件不存在!");
			}
		}
	}

	/**
	 * 后台发送含附件(Log日志)的邮件<br/>
	 */
	private void sendLogFileToEmail(final File file, final String name, final String email, final String title,
			final String content) {
		new Thread() {
			public void run() {
				Log.i("mail", "正在发送邮件：Log日志<br/>文件：" + file);
				MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
				mailInfo.setMailServerHost("smtp.qq.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("3301436616@qq.com");
				mailInfo.setPassword("cunonzupwfgscida");
				mailInfo.setFromAddress("3301436616@qq.com");
				mailInfo.setToAddress(email);
				mailInfo.setSubject("Log日志-" + name + "-" + title);
				mailInfo.setContent(content);
				mailInfo.setFiles(new File[] { file });
				MultiMailsender mailSender = new MultiMailsender();
				mailSender.sendMailtoMultiReceiver(mailInfo);

			};
		}.start();

	}

	private void doClickRightView(View view) {
		quickAction.show(view);
	}

	public void onItemClick(QuickAction source, int pos, int actionId) {
		switch (pos) {
		case 0:
			doClickSendView();
			break;
		case 1:
			doClickSendManagerView();
			break;
		case 2:
			showToast("正在开发中");
			break;
		case 3:
			doClickLogSettingView();
			break;
		case 4:
			diClickClearLogView();
			break;
		}
	}

	private void doClickSendView() {
		if (webView.getVisibility() == View.VISIBLE) {
			List<Map<String, Object>> list = properties.getList("name", "email");
			LogEmailAdapter adapter = new LogEmailAdapter(activity, list);
			DialogUtil.showEditTextDialog(activity, "发送", null, R.layout.dialog_send_log, null, null, adapter, 1);
		}
	}

	private void doClickSendManagerView() {
		startActivity(SendManagerActivity.class);
	}

	private void doClickLogSettingView() {
		Intent intent = new Intent(activity, LogSettingActivity.class);
		activity.startActivity(intent);
	}

	private void diClickClearLogView() {
		boolean isSucceed = Log.deleteLogFile(activity);
		if (isSucceed) {
			showLogInfo();
		} else {
			showToast("清除失败！");
		}
	}
}
