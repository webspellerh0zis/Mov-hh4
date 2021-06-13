package qlsl.androiddesign.util.commonutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputMatch {

	/**
	 * 判断字符串是否为6位数数字密码
	 */
	public static boolean isPassword(String password) {
		Pattern p = Pattern.compile("[0-9]{6}");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * 判断两个字符串是否相同
	 */
	public static boolean isEnsurePassword(String password,
			String ensurePassword) {
		if (password.equals(ensurePassword)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是手机号<br/>
	 * 修改兼容183号段,by ylq at 20160201<br/>
	 */
	public static boolean isMobileNO(String mobiles) {
		// 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		// 联通：130、131、132、152、155、156、185、186
		// 电信：133、153、180、189、（1349卫通）
		// 准确匹配正确号段的11位手机号
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断字符串是否是邮箱
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		// String str =
		// "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断字符串是否为数字
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
