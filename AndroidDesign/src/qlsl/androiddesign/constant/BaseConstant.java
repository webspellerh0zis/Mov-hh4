package qlsl.androiddesign.constant;

/**
 * 
 * @author xuh
 * @Description: 基本常量类，项目需有一个常量类继承此类，并初始化相关参数
 * @ClassName: BaseConstant.java
 * @date 2014-9-25 上午10:58:30
 * @说明 代码版权归 杭州天迈网络科技有限公司 所有
 */
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

	/**
	 * @Author: xuh
	 * @Description: 初始化项目必用参数
	 * @Date:2014-9-25上午11:41:07
	 * @param appId
	 * @param appCode
	 * @param resUrl
	 * @return void
	 * @说明 代码版权归 杭州天迈网络科技有限公司 所有
	 */
	public static void init(String appId, String appCode, String resUrl) {
		APPID = appId;
		APPCODE = appCode;
		RES_URL = resUrl;
	}

	/**
	 * @Author: xuh
	 * @Description: 初始化http相关参数
	 * @Date:2014-9-25上午11:56:27
	 * @return void
	 * @说明 代码版权归 杭州天迈网络科技有限公司 所有
	 */
	public static void initHttp(String url, boolean isSign) {
		URL = url;
		mustSign = isSign;
	}

	/**
	 * @Author: xuh
	 * @Description: 初始化socket相关参数
	 * @Date:2014-9-25上午11:56:42
	 * @return void
	 * @说明 代码版权归 杭州天迈网络科技有限公司 所有
	 */
	public static void initSocket(String url, String address, String port) {
		LEZIYOU_URL = url;
		ADDRESS_STRING = address;
		PORT_STRING = port;
	}
}
