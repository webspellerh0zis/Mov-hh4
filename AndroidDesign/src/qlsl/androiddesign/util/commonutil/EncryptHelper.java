package qlsl.androiddesign.util.commonutil;

import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.util.Base64;

/**
 * 加密辅助类
 * 
 * @author shay
 * @created 2015/4/27.
 */
public final class EncryptHelper {
	private static final String ALGORITHM_DES = "DES/CBC/PKCS7Padding";
	private final static String KEY = "e9dv3n1t";
	private final static String VI = "ac3f6n2d";
	private final static String CHARSET = "UTF-8";

	/**
	 * DES 加密
	 * 
	 * @param text
	 *            字符
	 * @param key
	 *            key
	 * @param vi
	 *            vi
	 * @return 加密字符
	 */
	@SuppressLint("TrulyRandom")
	public static String desEncode(String text, String key, String vi) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);

			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET));

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(vi.getBytes(CHARSET));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			String msg = Base64.encodeToString(
					cipher.doFinal(text.getBytes(CHARSET)), Base64.DEFAULT);
			msg = msg.replace("+", "-");
			msg = msg.replace("/", "_");
			msg = msg.replace("=", "~");

			return URLEncoder.encode(msg, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES 加密
	 * 
	 * @param text
	 *            字符
	 * @return 加密字符
	 */
	public static String desEncode(String text) {
		return desEncode(text, KEY, VI);
	}
}
