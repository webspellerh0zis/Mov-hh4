package qlsl.androiddesign.util.commonutil;

public class DoubleClick {
	private static long lastClickTime;
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
