package qlsl.androiddesign.util.commonutil;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

public class ActivityUtils {

	public static void SendEmail(Context context, String email) {
		Uri uri = Uri.parse("mailto:" + email);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		context.startActivity(intent);
	}

	public static void playVedio(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.setDataAndType(uri, "video/mp4");
		context.startActivity(it);
	}

	public static void openSite(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);

		context.startActivity(it);
	}

	public static void callNum(BaseActivity activity, String num) {
		if (!InputMatch.isNumeric(num)) {
			activity.showToast("无电话号码");
		} else {
			Uri uri = Uri.parse("tel:" + num);

			Intent it = new Intent(Intent.ACTION_DIAL, uri);
			activity.startActivity(it);
		}
	}

	public static void setSms(Context context, String num, String text) {
		Uri uri = Uri.parse("smsto:" + num);

		Intent it = new Intent(Intent.ACTION_SENDTO, uri);

		it.putExtra("sms_body", text);

		context.startActivity(it);
	}

	public static void playMp3(Context context, String path) {
		Intent it = new Intent(Intent.ACTION_VIEW);

		Uri uri = Uri.parse("file://" + path);

		it.setDataAndType(uri, "audio/mp3");

		context.startActivity(it);
	}

	public static void setGPS(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		context.startActivity(intent);
	}

	public static void startImgBrowse(Context context, String path) {

		if (!new File(path).exists()) {
			Toast.makeText(context, "文件不存在！	", Toast.LENGTH_LONG).show();
			return;
		}

		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + path), "image/*");
		context.startActivity(intent);
	}

	public static void startMarketReview(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
		context.startActivity(intent);
	}

}
