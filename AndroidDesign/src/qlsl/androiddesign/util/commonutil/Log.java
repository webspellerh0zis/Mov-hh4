package qlsl.androiddesign.util.commonutil;

import java.io.File;

import org.apache.log4j.Logger;

import com.qlsl.androiddesign.appname.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.VersionActivity;
import qlsl.androiddesign.service.subservice.LogFloatService;

/**
 * Log统一管理类<br/>
 * 日志文件存储在data/data/包名/files文件夹下<br/>
 * 对应的设置在drawable/log_setting中<br/>
 */
public class Log {

	/**
	 * 默认的输出标签
	 */
	public static final String TAG = "输出";

	/**
	 * true为调试模式，false为发布模式
	 */
	public static boolean isDebug = true;

	/**
	 * true为输出手机模式，false为输出控制台模式<br/>
	 * 手机模式 ：手机日志有颜色变化，控制台有额外颜色标签<br/>
	 * 控制台模式 ：手机日志无颜色变化，控制台无额外颜色标签<br/>
	 */
	public static boolean isOutPhone = true;

	private Log() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static void v(Object msg) {
		if (isDebug) {
			recordLogVerbose(TAG, msg);
		}
	}

	public static void d(Object msg) {
		if (isDebug) {
			recordLogDebug(TAG, msg);
		}
	}

	public static void i(Object msg) {
		if (isDebug) {
			recordLogInfo(TAG, msg);
		}
	}

	public static void w(Object msg) {
		if (isDebug) {
			recordLogWarn(TAG, msg);
		}
	}

	public static void e(Object msg) {
		if (isDebug) {
			recordLogError(TAG, msg);
		}
	}

	public static void v(String tag, Object msg) {
		if (isDebug) {
			recordLogVerbose(tag, msg);
		}
	}

	public static void d(String tag, Object msg) {
		if (isDebug) {
			recordLogDebug(tag, msg);
		}
	}

	public static void i(String tag, Object msg) {
		if (isDebug) {
			recordLogInfo(tag, msg);
		}
	}

	public static void w(String tag, Object msg) {
		if (isDebug) {
			recordLogWarn(tag, msg);
		}
	}

	public static void e(String tag, Object msg) {
		if (isDebug) {
			recordLogError(tag, msg);
		}
	}

	private static void recordLogVerbose(String tag, Object msg) {
		if (msg == null) {
			msg = "null";
		}
		if (isOutPhone) {
			msg = "<br/><font color=white>" + msg + "</font><br/>";
		} else {
			msg = "\n" + msg.toString().replace("<br/>", "\n") + "\n";
		}
		Logger.getLogger(tag).info(msg);
	}

	private static void recordLogDebug(String tag, Object msg) {
		if (msg == null) {
			msg = "null";
		}
		if (isOutPhone) {
			msg = "<br/><font color=green>" + msg + "</font><br/>";
		} else {
			msg = "\n" + msg.toString().replace("<br/>", "\n") + "\n";
		}
		Logger.getLogger(tag).debug(msg);
	}

	private static void recordLogInfo(String tag, Object msg) {
		if (msg == null) {
			msg = "null";
		}
		if (isOutPhone) {
			msg = "<br/><font color=cyan>" + msg + "</font><br/>";
		} else {
			msg = "\n" + msg.toString().replace("<br/>", "\n") + "\n";
		}
		Logger.getLogger(tag).info(msg);
	}

	private static void recordLogWarn(String tag, Object msg) {
		if (msg == null) {
			msg = "null";
		}
		if (isOutPhone) {
			msg = "<br/><font color=yellow>" + msg + "</font><br/>";
		} else {
			msg = "\n" + msg.toString().replace("<br/>", "\n") + "\n";
		}
		Logger.getLogger(tag).warn(msg);
	}

	private static void recordLogError(String tag, Object msg) {
		if (msg == null) {
			msg = "null";
		}
		if (isOutPhone) {
			msg = "<br/><font color=red>" + msg + "</font><br/>";
		} else {
			msg = "\n" + msg.toString().replace("<br/>", "\n") + "\n";
		}
		Logger.getLogger(tag).error(msg);
	}

	public static String getLogPath(Context context) {
		String path = context.getFilesDir().getAbsolutePath() + File.separator
				+ "log.html";
		return path;
	}

	public static boolean deleteLogFile(Context context) {
		File file = new File(getLogPath(context));
		return file.delete();

	}

	public static void notifyDataSetChanged(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		isDebug = sp
				.getBoolean(context.getString(R.string.debugModel), isDebug);
		isOutPhone = sp.getBoolean(
				context.getString(R.string.outputPhoneModel), isOutPhone);
	}

	public static void setDebugModel(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putBoolean(context.getString(R.string.debugModel), true);
		editor.commit();

		notifyDataSetChanged(context);
	}

	/**
	 * 在高级搜索中输入特定的密码将开发模式切换成调试模式<br/>
	 * 用处：提供日志查看功能以打通开发者与测试者之间的沟通桥梁<br/>
	 * 发布模式时，日志查看悬浮窗会自动关闭。但发布模式下仍要提供切换成调试模式的入口<br/>
	 * 这个入口不对用户提供，只对运营，测试及开发者提供<br/>
	 * 需要主动告知运营及测试打开软件调试模式的密码<br/>
	 * 以在查看日志过程中发现软件的bug<br/>
	 * 默认的打开调试模式密码为qlslylq<br/>
	 */
	public static void checkDebugModel(BaseActivity activity, String password) {
		if (password.equals("qlslylq")) {
			if (!isDebug) {
				setDebugModel(activity);
				if (isDebug) {
					activity.startService(LogFloatService.class);
					activity.showToast("已成功切换成调试模式!");
				} else {
					activity.showToast("切换失败，请稍后再试!");
				}
			} else {
				activity.showToast("开发模式为调试模式！");
			}
			activity.startActivity(VersionActivity.class);
		}
	}
}
