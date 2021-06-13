package qlsl.androiddesign.util.commonutil;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;

public class ChineseUtil {
	private static Random random = null;

	private static Random getRandomInstance() {
		if (random == null) {
			random = new Random(new Date().getTime());
		}
		return random;
	}

	@SuppressLint("UseValueOf")
	public static String getChinese() {
		String str = null;
		int highPos, lowPos;
		Random random = getRandomInstance();
		highPos = 176 + Math.abs(random.nextInt(39));
		lowPos = 161 + Math.abs(random.nextInt(93));
		byte[] b = new byte[2];
		b[0] = new Integer(highPos).byteValue();
		b[1] = new Integer(lowPos).byteValue();
		try {
			str = new String(b, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFixedLengthChinese(int length) {
		String str = "";
		for (int i = length; i > 0; i--) {
			str = str + ChineseUtil.getChinese();
		}
		return str;
	}

	public static String getRandomLengthChiness(int start, int end) {
		String str = "";
		int length = new Random().nextInt(end + 1);
		if (length < start) {
			str = getRandomLengthChiness(start, end);
		} else {
			for (int i = 0; i < length; i++) {
				str = str + getChinese();
			}
		}
		return str;
	}

}
