package qlsl.androiddesign.method;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.HttpKeyConstant;

public class HttpMethod {

	/**
	 * 判断网络是否连接并可用<br/>
	 */
	public static boolean isNetworkConnect() {
		boolean netStatus = false;
		try {
			ConnectivityManager connectManager = (ConnectivityManager) SoftwareApplication.getInstance()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();
			if (activeNetworkInfo != null) {
				if (activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
					netStatus = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return netStatus;
	}

	/**
	 * 获取签名之后的字符串<br/>
	 */
	public static String getSignStr(String paramsStr) {
		String beforeSignStr = paramsStr + "+" + HttpKeyConstant.VALUE_SIGN_SUFFIX;
		return getMD5Str(beforeSignStr);
	}

	/**
	 * 获取网络连接参数的拼接字符串<br/>
	 */
	public static String getParamsStr(SortedMap<String, Object> mapParam) {
		StringBuilder sb = new StringBuilder();
		if (mapParam == null || mapParam.size() == 0) {
			return sb.toString();
		}
		Set<Map.Entry<String, Object>> entrySet = mapParam.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			try {
				sb.append(entry.getKey()).append("=").append(URLDecoder.decode(entry.getValue() + "", "utf-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 获取MD5<br/>
	 */
	public static String getMD5Str(String beforeSignStr) {
		if (TextUtils.isEmpty(beforeSignStr)) {
			return beforeSignStr;
		}
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(beforeSignStr.getBytes());

			byte byteData[] = md.digest();

			StringBuilder hexString = new StringBuilder();
			for (byte aByteData : byteData) {
				String hex = Integer.toHexString(0xff & aByteData);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
