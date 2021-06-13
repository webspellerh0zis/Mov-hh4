package qlsl.androiddesign.util.commonutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.format.Time;

/**
 * 时间友好化工具类 by ylq at 20160503<br/>
 *
 */
@SuppressLint("SimpleDateFormat")
public class TimeFormatUtils {

	/**
	 * 时间友好化,传入时间格式须为 yyyy-MM-dd HH:mm:ss<br/>
	 */
	public static String format(String time) {
		return format(strToDateLong(time));
	}

	/**
	 * 时间格式化<br/>
	 */
	private static String format(long time) {
		Time time_now = new Time();
		time_now.setToNow();
		Time time_dst = new Time();
		time_dst.set(time);

		String formatStr = null;
		if (time_now.year != time_dst.year) {
			formatStr = "yyyy-MM-dd HH:mm";
		} else if (time_now.yearDay - time_dst.yearDay >= 2) {
			formatStr = "MM-dd HH:mm";
		} else if (time_now.yearDay == time_dst.yearDay) {
			int hour_dif = time_now.hour - time_dst.hour;
			int minute_dif = time_now.minute - time_dst.minute;
			if (hour_dif == 0) {
				if (minute_dif <= 1) {
					return "刚刚";
				} else {
					return minute_dif + "分钟前";
				}
			} else if (hour_dif == 1 || hour_dif == -23) {
				int minutes_dif = 60 + minute_dif;
				if (minutes_dif <= 59) {
					return minutes_dif + "分钟前";
				}
			}

			formatStr = "HH:mm";
		} else {
			formatStr = "HH:mm";
		}

		if (time_dst.year == time_now.year && time_dst.yearDay == time_now.yearDay) {
			return "今天 " + formatDateLong(time, formatStr);
		} else if ((time_dst.year == time_now.year) && ((time_now.yearDay - time_dst.yearDay) == 1)) {
			return "昨天 " + formatDateLong(time, formatStr);
		} else {
			return formatDateLong(time, formatStr);
		}
	}

	/**
	 * 时间戳格式化<br/>
	 */
	private static String formatDateLong(long time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(time);
		return sdf.format(date);
	}

	/**
	 * 将长时间格式字符串yyyy-MM-dd HH:mm:ss转换为时间戳<br/>
	 * 
	 */
	private static long strToDateLong(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

}
