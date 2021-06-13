package qlsl.androiddesign.view.rippleview;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	private static Typeface typeface;

	public static Typeface get(Context context, String assetPath) {
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}

	private static Typeface getDefault(Context context) {
		String assetPath = "fonts/font_ksj.ttf";
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}

	/**
	 * 单例获取默认艺术字体<br/>
	 * 后期需要配置是否开启艺术字体，关闭则返回null<br/>
	 */
	public static Typeface get(Context context) {
		if (typeface == null) {
			typeface = getDefault(context);
		}
		return typeface;
	}
}