package qlsl.androiddesign.method;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BaseMethod {

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	private static final double EARTH_RADIUS = 6378.137;

	/**
	 * 根据经纬度获取两点的距离
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static void setListViewHeight(ListView lv) {
		int totalHeight = 0;
		ListAdapter listAdapter = lv.getAdapter();
		if (null == listAdapter) {
			return;
		}
		ViewGroup.LayoutParams lp = lv.getLayoutParams();
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, lv);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight() + lv.getDividerHeight() + 10;// 统计所有子项的总高度
		}
		lp.height = totalHeight;
		lv.setLayoutParams(lp);
	}

	/**
	 * 获取屏幕的分辨率
	 */
	@SuppressWarnings("deprecation")
	public static int[] getWindowPx(Activity activity) {
		int[] values = null;
		Display display = activity.getWindowManager().getDefaultDisplay();
		values = new int[] { display.getWidth(), display.getHeight() };
		return values;

	}

	// 设置图片上的 移动点
	@SuppressWarnings("deprecation")
	public static void showImgCount(RadioGroup viewGroup, int count, Activity activity, Drawable buttonDrawable,
			Drawable BackgroundDrawable) {
		RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(10, 10);
		viewGroup.removeAllViews();
		for (int i = 0; i < count; i++) {
			RadioButton radioButton = new RadioButton(activity);
			radioButton.setButtonDrawable(buttonDrawable);
			radioButton.setBackgroundDrawable(BackgroundDrawable);
			radioButton.setText("");
			radioButton.setGravity(Gravity.CENTER);
			layoutParams.setMargins(2, 2, 2, 2);
			radioButton.setLayoutParams(layoutParams);
			viewGroup.addView(radioButton);
			if (i == 0) {
				radioButton.setChecked(true);
			}
		}
	}

	/***
	 * 代码改变某控件的大小
	 */
	public static void setLayoutHeightAndWidth(int weight, int height, View viewGroup) {
		LayoutParams gLayoutParams = viewGroup.getLayoutParams();
		if (weight != 0) {
			gLayoutParams.width = weight;
		}
		if (height != 0) {
			gLayoutParams.height = height;
		}
		viewGroup.setLayoutParams(gLayoutParams);
	}

	// 获取wifi名称
	public static String getWifiName(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getSSID();
	}

	// 获取wifi信息
	public static WifiInfo getWifiInfo(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo();
	}

	// public static GeoPoint getGeoPointByLocation(Location location) {
	//
	// GeoPoint geoPoint = null;
	// if (location != null) {
	// geoPoint = new GeoPoint((int) (location.getLatitude() * 1E6),
	// (int) (location.getLongitude() * 1E6));
	// }
	//
	// return geoPoint;
	// }

	// public static Location getLocationByGeoPoint(GeoPoint geoPoint) {
	// Location location = null;
	// if (geoPoint != null) {
	// location = new Location("");
	// location.setLatitude(geoPoint.getLatitudeE6() / 1E6);
	// location.setLongitude(geoPoint.getLongitudeE6() / 1E6);
	// }
	// return location;
	// }
	//
	// public static Location getLocationByHotspot(WuxiHotspot hotspot,
	// boolean isSat) {
	// Location location = new Location("");
	// if (isSat) {
	// location.setLatitude(hotspot.getLag());
	// location.setLongitude(hotspot.getLng());
	// return location;
	// } else {
	// location.setLatitude(hotspot.getLagoff());
	// location.setLongitude(hotspot.getLngoff());
	// return location;
	// }
	// }
	//
	// public static GeoPoint getGeoPointByHotspot(WuxiHotspot hotspot,
	// boolean isSat) {
	// GeoPoint geoPoint = null;
	// if (isSat) {
	// if (hotspot.getLag() != null && hotspot.getLng() != null) {
	// geoPoint = new GeoPoint((int) (hotspot.getLag() * 1E6),
	// (int) (hotspot.getLng() * 1E6));
	// }
	// return geoPoint;
	// } else {
	// if (hotspot.getLagoff() != null && hotspot.getLngoff() != null) {
	// geoPoint = new GeoPoint((int) (hotspot.getLagoff() * 1E6),
	// (int) (hotspot.getLngoff() * 1E6));
	// }
	// return geoPoint;
	// }
	// }
	//
	// public static Location getLocationByMyHotspot(MyHotspot hotspot,
	// boolean isSat) {
	// Location location = new Location("");
	// if (isSat) {
	// location.setLatitude(Double.valueOf(hotspot.getLat()));
	// location.setLongitude(Double.valueOf(hotspot.getLon()));
	// return location;
	// } else {
	// location.setLatitude(Double.valueOf(hotspot.getLatOff()));
	// location.setLongitude(Double.valueOf(hotspot.getLonOff()));
	// return location;
	// }
	// }
	//
	// public static GeoPoint getGeoPointByMyHotspot(MyHotspot hotspot,
	// boolean isSat) {
	// GeoPoint geoPoint = null;
	// if (isSat) {
	// geoPoint = new GeoPoint(
	// (int) (Double.valueOf(hotspot.getLat()) * 1E6),
	// (int) (Double.valueOf(hotspot.getLon()) * 1E6));
	// return geoPoint;
	// } else {
	// geoPoint = new GeoPoint(
	// (int) (Double.valueOf(hotspot.getLatOff()) * 1E6),
	// (int) (Double.valueOf(hotspot.getLonOff()) * 1E6));
	//
	// return geoPoint;
	// }
	// }
	// 获取ip
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// 获取mac地址
	public static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getMacAddress();
	}

	/**
	 * 判断String是否为空
	 */
	public static boolean isEmpty(String txt) {

		try {
			if (null == txt) {
				return true;
			}
			if (txt.length() == 0) {
				return true;
			}
			txt = txt.toLowerCase().trim();
			if (txt.contains("null")) {
				return true;
			}
			if (txt.equals("null")) {
				return true;
			}
			if (txt.equals("")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

		return false;
	}

	/**
	 * 判断String是否为空
	 */
	public static boolean isDoubleEmpty(Double txt) {

		try {
			if (null == txt) {
				return true;
			}
			if (txt == 0.0d) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

		return false;
	}

	// 获取MD5码
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getNextDay() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	@SuppressLint("SimpleDateFormat")
	public static Date getNextDay(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		Date nextDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return nextDate;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getNextNDay(int n) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);// 把日期往后增加n天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 判断String是否为"yyyyMMdd"格式的日期<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean isValidDate(String date) {
		if (TextUtils.isEmpty(date)) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			sdf.setLenient(false);
			sdf.parse(date);
			return true;
		} catch (java.text.ParseException e) {
			return false;
		}
	}

	/**
	 * 把date转为"yyyyMMdd"格式的日期<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseDate(String date) {
		if (TextUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			sdf.setLenient(false);
			return sdf.parse(date);
		} catch (java.text.ParseException e) {
			return null;
		}
	}

	/**
	 * 把date转为"yyyyMMdd"格式的日期<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseDateExact(String date) {
		if (TextUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			sdf.setLenient(false);
			return sdf.parse(date);
		} catch (java.text.ParseException e) {
			return null;
		}
	}

	/**
	 * 把date字符串转为"yyyy-MM-dd"格式的日期<br/>
	 * 可以指定分隔符<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseDate(String date, char devider) {
		if (TextUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + devider + "MM" + devider + "dd");
		try {
			sdf.setLenient(false);
			return sdf.parse(date);
		} catch (java.text.ParseException e) {
			return null;
		}
	}

	/**
	 * 把date日期转为"yyyy-MM-dd"格式的字符串<br/>
	 * 可以指定分隔符<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(Date date, char devider) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + devider + "MM" + devider + "dd");
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 验证字符串是否是email
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher m = pattern.matcher(str.trim());
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	// 获取应该用的版本号,,当前应用 packageName=c.getPackageName();
	public static String getAppVersion(Context c, String packageName) {
		String result = null;

		PackageInfo pInfo = null;
		try {
			pInfo = c.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return result;
		}
		result = pInfo.versionName;
		return result;
	}

	// private static final char[] legalChars =
	// "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
	// .toCharArray();

	/** Base64 encode the given data */

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		else if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else
			switch (c) {
			case '+':
				return 62;
			case '/':
				return 63;
			case '=':
				return 0;
			default:
				throw new RuntimeException("unexpected code: " + c);
			}
	}

	/**
	 * Decodes the given Base64 encoded String to a new byte array. The byte
	 * array holding the decoded data is returned.
	 */

	public static String decode(String s) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException ex) {
			System.err.println("Error while decoding BASE64: " + ex.toString());
		}
		return new String(decodedBytes);
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;

		int len = s.length();

		while (true) {
			while (i < len && s.charAt(i) <= ' ')
				i++;

			if (i == len)
				break;

			int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
					+ (decode(s.charAt(i + 3)));

			os.write((tri >> 16) & 255);
			if (s.charAt(i + 2) == '=')
				break;
			os.write((tri >> 8) & 255);
			if (s.charAt(i + 3) == '=')
				break;
			os.write(tri & 255);
			i += 4;
		}
	}

	/**
	 * 获取指定范围内随机数,包括端点,适用于正负数区间,交叉区间及独数区间<br/>
	 */
	public static int getRandomValue(int minValue, int maxValue) {
		if (minValue > maxValue) {
			return -1;
		}
		Random random = new Random();
		int value = random.nextInt(maxValue - minValue + 1) + minValue;
		return value;
	}

        public static String toUpperCaseForWeek(String src) {
		String dst = null;
		if (src.equals("0")) {
			dst = "日";
		} else if (src.equals("1")) {
			dst = "一";
		} else if (src.equals("2")) {
			dst = "二";
		} else if (src.equals("3")) {
			dst = "三";
		} else if (src.equals("4")) {
			dst = "四";
		} else if (src.equals("5")) {
			dst = "五";
		} else if (src.equals("6")) {
			dst = "六";
		} else if (src.equals("7")) {
			dst = "日";
		}
		return "星期" + dst;
	}

        /**
	 * 获取今天日期<br/>
	 * 格式：yyyy年M月dd日<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDay() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M月dd日");
		String dateString = formatter.format(date);
		return dateString;
	}

        /**
	 * 获取今天星期几<br/>
	 */
	public static String getCurrentWeek() {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String week = String.valueOf(c.get(Calendar.DAY_OF_WEEK)-1);
		return toUpperCaseForWeek(week);
	}
}
