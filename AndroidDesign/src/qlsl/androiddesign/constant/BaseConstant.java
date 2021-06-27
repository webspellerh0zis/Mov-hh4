package qlsl.androiddesign.constant;

public class BaseConstant implements SoftwareConstant {
	public final static int MSG_NET_DATA_SUCCESS = 0x100;
	public final static int MSG_NET_DATA_FAIL = 0x101;
	public final static int MSG_DB_DATA_SUCCESS = 0x201;
	public final static int MSG_DB_DATA_FAIL = 0x202;
	public final static int MSG_EXCEPITON = 0x301;

	public static String APPID;
	public static String APPCODE;
	public static String TOKEN;

	/**
	 * socket相关
	 */
	public static String LEZIYOU_URL;
	public static String ADDRESS_STRING;
	public static String PORT_STRING;

	/**
	 * http接口请求地址前缀
	 */
	public static String URL;

	/**
	 * 是否需要加密签名
	 */
	public static boolean mustSign;

	/**
	 * 图片下载地址前缀
	 */
	public static String RES_URL;

	public static void init(String appId, String appCode, String resUrl) {
		APPID = appId;
		APPCODE = appCode;
		RES_URL = resUrl;
	}

	public static void initHttp(String url, boolean isSign) {
		URL = url;
		mustSign = isSign;
	}

	public static void initSocket(String url, String address, String port) {
		LEZIYOU_URL = url;
		ADDRESS_STRING = address;
		PORT_STRING = port;
	}
}
