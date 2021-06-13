package qlsl.androiddesign.util.commonutil;

import android.view.View;
import android.view.ViewGroup;

public class ViewOutUtils {

	public static void outputAllView(ViewGroup rootGroup) {
		for (int index = 0; index < rootGroup.getChildCount(); index++) {
			View view = rootGroup.getChildAt(index);
			Log.i("view", "index：" + index + "<br/>view：" + view + "<br/>tag： " + view.getTag());
			if (view instanceof ViewGroup) {
				outputAllView((ViewGroup) view);
			}
		}
	}
}
