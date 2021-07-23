package qlsl.androiddesign.util.commonutil;

import java.io.File;

import com.qlsl.androiddesign.appname.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import qlsl.androiddesign.transformation.CircleTransformation;
import qlsl.androiddesign.transformation.RoundTransformation;
import qlsl.androiddesign.transformation.TopRoundTransformation;

/**
 * 加载模式为fitCenter或fitXY,fitCenter常出现错一乱多局面<br/>
 * 加载一个或一类图时，应具有统一的大小和比例，fitXY模式下，由替换图决定或同时指定控件宽高<br/>
 * 指定宽或高且使用adjustViewBounds属性在fitXY模式下无效<br/>
 * 不方便指定宽高且想保持比例时建议使用替换图模式<br/>
 * rect,round模式下提供fitCenter加载模式，须慎用<br/>
 * circle模式下不提供fitCenter加载模式，由替换图或控件宽高决定控件大小<br/>
 */
public class ImageUtils {

	/**
	 * 矩形[fitXY]<br/>
	 */
	public static void rect(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().placeholder(R.drawable.iv_default)
				.error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 矩形[fitXY]<br/>
	 * 替换图<br/>
	 */
	public static void rect(Context context, String thumbUrl, ImageView imageView, int placeholderRes) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().placeholder(placeholderRes).error(placeholderRes)
				.into(imageView);
		config(context);
	}

	/**
	 * 矩形[fitXY]<br/>
	 * 文件<br/>
	 */
	public static void rect(Context context, File file, ImageView imageView) {
		Picasso.with(context).load(file).fit().placeholder(R.drawable.iv_default).error(R.drawable.iv_default)
				.into(imageView);
		config(context);
	}

	/**
	 * 矩形[fitCenter]<br/>
	 */
	public static void rectOri(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).placeholder(R.drawable.iv_default)
				.error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 矩形[fitCenter]<br/>
	 * 替换图<br/>
	 */
	public static void rectOri(Context context, String thumbUrl, ImageView imageView, int placeholderRes) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).placeholder(placeholderRes).error(placeholderRes)
				.into(imageView);
		config(context);
	}

	/**
	 * 矩形[fitCenter]<br/>
	 * 文件<br/>
	 */
	public static void rectOri(Context context, File file, ImageView imageView) {
		Picasso.with(context).load(file).placeholder(R.drawable.iv_default).error(R.drawable.iv_default)
				.into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitXY]<br/>
	 */
	public static void round(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().transform(new RoundTransformation())
				.placeholder(R.drawable.iv_default).error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 顶部圆角[fitXY]<br/>
	 */
	public static void roundTop(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().transform(new TopRoundTransformation())
				.placeholder(R.drawable.iv_default).error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitXY]<br/>
	 * 替换图<br/>
	 */
	public static void round(Context context, String thumbUrl, ImageView imageView, int placeholderRes) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().transform(new RoundTransformation())
				.placeholder(placeholderRes).error(placeholderRes).into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitXY]<br/>
	 * 文件<br/>
	 */
	public static void round(Context context, File file, ImageView imageView) {
		Picasso.with(context).load(file).fit().transform(new RoundTransformation()).placeholder(R.drawable.iv_default)
				.error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitCenter]<br/>
	 */
	public static void roundOri(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).transform(new RoundTransformation())
				.placeholder(R.drawable.iv_default).error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitCenter]<br/>
	 * 替换图<br/>
	 */
	public static void roundOri(Context context, String thumbUrl, ImageView imageView, int placeholderRes) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).transform(new RoundTransformation())
				.placeholder(placeholderRes).error(placeholderRes).into(imageView);
		config(context);
	}

	/**
	 * 圆角[fitCenter]<br/>
	 * 文件<br/>
	 */
	public static void roundOri(Context context, File file, ImageView imageView) {
		Picasso.with(context).load(file).transform(new RoundTransformation()).placeholder(R.drawable.iv_default)
				.error(R.drawable.iv_default).into(imageView);
		config(context);
	}

	/**
	 * 圆形<br/>
	 */
	public static void circle(Context context, String thumbUrl, ImageView imageView) {
		Picasso.with(context).load(convertThumbUrl(thumbUrl)).fit().transform(new CircleTransformation())
				.placeholder(R.drawable.iv_member_photo).error(R.drawable.iv_member_photo).into(imageView);
		config(context);
	}

	/**
	 * 圆形<br/>
	 * 文字<br/>
	 */
	@SuppressWarnings("deprecation")
	public static void circle(Context context, String thumbUrl, String text, ImageView imageView) {
		if (TextUtils.isEmpty(thumbUrl)) {
			Bitmap bitmap = BitmapUtils.TextToBitmap(text);
			BitmapDrawable drawable = new BitmapDrawable(bitmap);
			Picasso.with(context).load("http").fit().transform(new CircleTransformation()).placeholder(drawable)
					.error(drawable).into(imageView);
		} else {
			Picasso.with(context).load(thumbUrl).fit().transform(new CircleTransformation())
					.placeholder(R.drawable.iv_member_photo).error(R.drawable.iv_member_photo).into(imageView);
			config(context);
		}
	}

	/**
	 * 地址转换<br/>
	 */
	private static String convertThumbUrl(String thumbUrl) {
		if (TextUtils.isEmpty(thumbUrl)) {
			thumbUrl = "http";
		}
		return thumbUrl;
	}

	/**
	 * 配置<br/>
	 */
	@SuppressWarnings("deprecation")
	private static void config(Context context) {
		Picasso.with(context).setDebugging(false);
		Picasso.with(context).setIndicatorsEnabled(false);
	}

}
