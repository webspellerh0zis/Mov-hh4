package qlsl.androiddesign.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.constant.BaseConstant;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.method.BaseMethod;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.util.singleton.ToolPagerUtils;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;

@SuppressLint("NewApi")
public class ExitManager {

	public static long currentBackPressedTime = 0;

	private static final int BACK_PRESSED_INTERVAL = 2000;

	public static void onBackPressed(Context context) {
		BaseActivity activity = (BaseActivity) context;
		if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
			currentBackPressedTime = System.currentTimeMillis();
			String[] texts = new String[] { "主人，你忍心离开我么", "叫你抛弃我，叫你抛弃我", "抛弃孩子的主人不是好主人!", "答应我，你会回来的!", "对酒当歌，人生几何" };
			showExitToast(activity, 1, 15, texts);
		} else {
			String[] texts = new String[] { "北岭有雁  羽若雪兮\n朔风哀哀  比翼南飞\n一折羽兮  奈之若何\n朔风凛凛  终不离兮",
					"杳杳灵风  绵绵长归\n悠悠我思  永与愿违\n万劫无期  何时来飞",
					"皑如山上雪，皎若云间月\n闻君有两意，故来相决绝\n今日斗酒会，明旦沟水头\n躞蹀御沟上，沟水东西流\n凄凄复凄凄，嫁取不须啼\n愿得一心人，白头不相离\n竹竿何袅袅，鱼尾何徒徒\n男儿重意气，何用钱刀为",
					"实澹泊而寡欲兮\n独怡乐而长吟\n声皦皦而弥厉兮\n似贞士之介心", "成礼兮会鼓\n传芭兮代舞\n姱女倡兮容与\n春兰兮秋菊\n长无绝兮终古" };
			showExitToast(activity, 16, 30, texts);
			Exit();
		}

	}

	private static void showExitToast(BaseActivity activity, int minValue, int maxValue, String[] texts) {
		Resources resources = activity.getResources();
		int index = BaseMethod.getRandomValue(minValue, maxValue);
		String resName = "iv_exit_icon_" + index;
		int resId = resources.getIdentifier(activity.getPackageName() + ":drawable/" + resName, null, null);
		int textIndex = index % texts.length;
		activity.showExitToast(texts[textIndex], resId);
	}

	/**
	 * 退出<br/>
	 * 主线程中调用<br/>
	 */
	public static void Exit() {
		clearStaticData();
		ActivityManager.getInstance().popAllActivity();
	}

	/**
	 * 单例设计类必须清除静态数据<br/>
	 */
	public static void clearStaticData() {
		UserMethod.setUser(null);
		BaseConstant.TOKEN = null;
		ToolColorUtils.set(null);
		ToolSpeechUtils.set(null);
		ToolPagerUtils.set(null);
		HttpProtocol.clearStaticData();
	}

	/**
	 * 延迟退出<br/>
	 * 主线程中调用<br/>
	 */
	public static void ExitDelayed() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Exit();
			}
		}, 3000);

	}

}
