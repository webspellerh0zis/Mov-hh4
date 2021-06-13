package qlsl.androiddesign.handler.subhandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.library.email.MultiMailsender;
import qlsl.androiddesign.library.email.MultiMailsender.MultiMailSenderInfo;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.properties.OrderedProperties;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.util.commonutil.NetUtils;

/**
 * 
 * 
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理 这样当未捕获异常发生时，就可以做些异常处理操作 例如：收集异常信息，发送错误报告 等。
 * 
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * 
 * 文件保存在data/data/包名/files下，即卸载程序后会自动清除<br/>
 */
public class CrashHandler implements UncaughtExceptionHandler {
	/** Debug Log Tag */
	private final String TAG = "CrashHandler";
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** 使用Properties来保存设备的信息和错误堆栈信息 */
	private Properties mDeviceCrashInfo = new OrderedProperties();
	private final String PACKAGE_NAME = "packageName";
	private final String VERSION_NAME = "versionName";
	private final String VERSION_CODE = "versionCode";
	private final String STACK_TRACE = "STACK_TRACE";
	/** 错误报告文件的扩展名 */
	private final String CRASH_REPORTER_EXTENSION = "report.txt";

	private CrashHandler() {

	}

	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		ActivityManager manager = ActivityManager.getInstance();
		int sleep = 3000;
		if (manager.getActivitySize() == 1) {
			sleep = 2000;
		} else {
			handleException(ex);
		}
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			Log.e(TAG, "Error : " + e);
		}
		// 强制退出(不能阻止重启)
		manager.popAllActivity();
		android.os.Process.killProcess(android.os.Process.myPid());

	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		final String msg = ex.getLocalizedMessage();
		Log.e(msg);
		new Thread() {
			public void run() {
				if (mContext instanceof BaseActivity) {
					Looper.prepare();
					BaseActivity activity = (BaseActivity) mContext;
					activity.showExitToast("发现异常，正在安全退出", R.drawable.iv_exit_icon_1);
					Looper.loop();
				} else if (mContext instanceof SoftwareApplication) {
					Looper.prepare();
					Toast.makeText(mContext, "发现异常，正在安全退出", Toast.LENGTH_LONG).show();
					Looper.loop();
				}
			}
		}.start();
		collectCrashDeviceInfo(mContext);
		saveCrashInfoToFile(ex);
		sendCrashReportsToServer(mContext);
		return true;
	}

	/**
	 * 收集程序崩溃的设备信息
	 * 
	 */
	public void collectCrashDeviceInfo(Context ctx) {
		StringBuffer sb = new StringBuffer();
		sb.append("正在收集设备信息：<br/>");
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(PACKAGE_NAME, pi.packageName);
				mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode + "");
			}
			String SDK_VERSION = android.os.Build.VERSION.RELEASE;
			mDeviceCrashInfo.put("系统版本：", SDK_VERSION + "");
			sb.append("SDK_VERSION:" + SDK_VERSION + "<br/>");
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info:" + e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), field.get(null) + "");
				sb.append(field.getName() + " : " + field.get(null) + "<br/>");
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info:" + e);
			}
		}
		Log.d(TAG, sb.toString());
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 */
	@SuppressLint("SimpleDateFormat")
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		String result = info.toString();
		Log.e(result);
		printWriter.close();
		mDeviceCrashInfo.put(STACK_TRACE, result);
		try {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
			String deviceName = mDeviceCrashInfo.getProperty("MANUFACTURER", "Unknow");
			String fileName = timestamp + "-" + deviceName + "-" + CRASH_REPORTER_EXTENSION;
			FileOutputStream trace = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
			mDeviceCrashInfo.store(trace, new String("异常报告".getBytes(), "ISO-8859-1"));
			trace.flush();
			trace.close();
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing report file:" + e);
		}
		return null;
	}

	/**
	 * 把错误报告发送给服务器,包含新产生的和以前没发送的.
	 * 
	 */
	private void sendCrashReportsToServer(Context context) {
		if (NetUtils.isConnected(context)) {
			File[] files = getCrashReportFiles(context);
			if (files != null && files.length > 0) {
				sendReportFileToEmail(files);
			}
		}
	}

	/**
	 * 获取错误报告文件
	 * 
	 */
	private File[] getCrashReportFiles(Context ctx) {
		File filesDir = ctx.getFilesDir();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(CRASH_REPORTER_EXTENSION);
			}
		};
		return filesDir.listFiles(filter);
	}

	@SuppressWarnings("unused")
	private void postReport(File file) {

	}

	private void sendReportFileToEmail(final File[] files) {
		new Thread() {
			public void run() {
				Log.d("mail", "正在发送邮件：异常报告<br/>文件列表：" + Arrays.asList(files));
				MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
				mailInfo.setMailServerHost("smtp.qq.com");
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("3301436616@qq.com");
				mailInfo.setPassword("cunonzupwfgscida");
				mailInfo.setFromAddress("3301436616@qq.com");
				mailInfo.setToAddress("2939143482@qq.com");
				mailInfo.setSubject("异常报告");
				mailInfo.setContent("收到一份来自用户后台发送的异常报告，请查收!");
				mailInfo.setFiles(files);
				String[] receivers = new String[] {};
				mailInfo.setReceivers(receivers);
				mailInfo.setCcs(receivers);
				MultiMailsender mailSender = new MultiMailsender();
				mailSender.sendMailtoMultiReceiver(mailInfo);

				deleteLogFiles(files);
			};
		}.start();

	}

	private void deleteLogFiles(File[] files) {
		Log.d("mail", "正在删除文件：异常报告");
		for (File file : files) {
			file.delete();
		}
	}

	/**
	 * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
	 */
	public void sendPreviousReportsToServer() {
		sendCrashReportsToServer(mContext);
	}

}
