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
	public static void setText(TextView tv, String text1, int start1, int end1, int sp1, int color1, String text2,
			int start2, int end2, int sp2, int color2) {
		ColorStateList colorStateList1 = ColorStateList.valueOf(color1);
		ColorStateList colorStateList2 = ColorStateList.valueOf(color2);
		SpannableStringBuilder spanBuilder1 = new SpannableStringBuilder(text1);
		spanBuilder1.setSpan(
				new TextAppearanceSpan(null, 0, DensityUtils.sp2px(tv.getContext(), sp1), colorStateList1, null),
				start1, end1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		SpannableStringBuilder spanBuilder2 = new SpannableStringBuilder(text2);
		spanBuilder2.setSpan(
				new TextAppearanceSpan(null, 0, DensityUtils.sp2px(tv.getContext(), sp2), colorStateList2, null),
				start2, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		spanBuilder1.append(spanBuilder2);
		tv.setText(spanBuilder1);
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
