package qlsl.androiddesign.util.commonutil;

import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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

}
