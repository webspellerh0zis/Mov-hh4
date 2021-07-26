package qlsl.androiddesign.util.commonutil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

/**
 * 文本大小工具类<br/>
 */
public class TextSizeUtils {

	/**
	 * TextView中设置部分文本的大小与颜色<br/>
	 */
	public static void setText(TextView tv, String text, int start, int end, int sp, int color) {
		ColorStateList colorStateList = ColorStateList.valueOf(color);
		SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
		spanBuilder.setSpan(
				new TextAppearanceSpan(null, 0, DensityUtils.sp2px(tv.getContext(), sp), colorStateList, null), start,
				end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		tv.setText(spanBuilder);
	}

	/**
	 * TextView中设置部分文本的大小与颜色<br/>
	 */
	public static void setText(TextView tv, String text, String sub, int sp, int color) {
		if (!TextUtils.isEmpty(text) && text.contains(sub)) {
			int start = text.indexOf(sub);
			int end = start + sub.length();
			ColorStateList colorStateList = ColorStateList.valueOf(color);
			SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
			spanBuilder.setSpan(
					new TextAppearanceSpan(null, 0, DensityUtils.sp2px(tv.getContext(), sp), colorStateList, null),
					start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			tv.setText(spanBuilder);
		}
	}

	/**
	 * 获取设置了部分文本大小与颜色的文本<br/>
	 */
	public static SpannableStringBuilder getText(Context context, String text, String sub, int sp, int color) {
		if (!TextUtils.isEmpty(text) && text.contains(sub)) {
			int start = text.indexOf(sub);
			int end = start + sub.length();
			ColorStateList colorStateList = ColorStateList.valueOf(color);
			SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
			spanBuilder.setSpan(new TextAppearanceSpan(null, 0, DensityUtils.sp2px(context, sp), colorStateList, null),
					start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			return spanBuilder;
		}
		return new SpannableStringBuilder(text);
	}

}
