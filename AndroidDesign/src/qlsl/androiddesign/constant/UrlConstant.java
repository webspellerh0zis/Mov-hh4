package qlsl.androiddesign.constant;

public interface UrlConstant extends MessageConstant {
	
	/**
	 * 静态服务器
	 */
	String STATIC_SERVER="http://static.dayez.net";

	/**
	 * 测试服务器基本url
	 */
//	String BASE_URL = "http://open.v3.deyi.com/v3/";

	/**
	 * 测试服务器时间戳url
	 */
//	String TICK_URL = "http://open.v3.deyi.com/ticks";

	/**
	 * 测试服务器图片上传url<br/>
	 */
//	String URL_FILE_PHOTO_SERVER = "http://file.dayez.net/uploader?type=1";

	/**
	 * 测试服务器音频上传url<br/>
	 */
//	String URL_FILE_AUDIO_SERVER = "http://file.dayez.net/uploader?type=6";

	// ---------------分界线---------------

	/**
	 * 线上服务器基本url
	 */
	 String BASE_URL = "http://open.dayeasy.net/v3/";

	/**
	 * 线上服务器时间戳url
	 */
	 String TICK_URL = "http://open.dayeasy.net/ticks";

	/**
	 * 线上服务器图片上传<br/>
	 */
	 String URL_FILE_PHOTO_SERVER = "http://file.dayeasy.net/uploader?type=1";

	/**
	 * 线上服务器音频上传<br/>
	 */
	 String URL_FILE_AUDIO_SERVER = "http://file.dayeasy.net/uploader?type=6";

	// ---------------分界线---------------

	/**
	 * 资源url
	 */
	String RES_URL = "";

	/**
	 * 框架测试url
	 */

	String EXAMPLE_URL = "http://web.wzta.gov.cn/api/hotspot/list.jspx";

	// ---------------分界线---------------

	/**
	 * 火车站到站查询URL
	 */
	String SEARCH_TRAIN_STATION_URL = "http://op.juhe.cn/onebox/train/query_ab";

	/**
	 * 火车余票查询URL
	 */
	String SEARCH_TRAIN_TICKET_URL = "http://apis.juhe.cn/train/yp";

	/**
	 * 长途汽车站到站查询URL
	 */
	String SEARCH_LONG_BUS_URL = "http://op.juhe.cn/onebox/bus/query_ab";

	/**
	 * 长途汽车站信息查询URL
	 */
	String SEARCH_LONG_BUS_STATION_URL = "http://op.juhe.cn/onebox/bus/query";

	/**
	 * 非常准航空查询URL
	 */
	String SEARCH_FLIGHT_STATION_URL = "http://apis.juhe.cn/plan/bc";

	/**
	 * 航空票价查询URL
	 */
	String SEARCH_FLIGHT_PRICE_URL = "http://op.juhe.cn/flight/fd";

	/**
	 * 公交站台经纬度查询URL
	 */
	String SEARCH_BUS_COORDINATE_URL = "http://api.juheapi.com/bus/stat";

	/**
	 * 公交换乘查询URL
	 */
	String SEARCH_BUS_STATION_URL = "http://api.juheapi.com/bus/transfer";

	// ---------------分界线---------------

}
