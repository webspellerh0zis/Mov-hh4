package qlsl.androiddesign.util.commonutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 去除以下字符<br>
	 * \n 回车(\u000a)<br>
	 * \t 水平制表符(\u0009)<br>
	 * \s 空格(\u0008)<br>
	 * \r 换行(\u000d)<br>
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去除以下字符,及头尾去空<br>
	 * \t 水平制表符(\u0009)<br>
	 */
	public static String trim(String str) {
		String dest = "";
		if (str != null) {
			dest = str.replaceAll("(^[ |　]*|[ |　]*$)", "").trim();
		}
		return dest;
	}

	/**
	 * 去除以下字符<br>
	 * ["<br/>
	 * "]<br/>
	 */
	public static String replaceArray(String str) {
		String dest = "";
		if (str != null) {
			dest = str.replace("[\"", "").replace("\"]", "");
		}
		return dest;
	}
}
