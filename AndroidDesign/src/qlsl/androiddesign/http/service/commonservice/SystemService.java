package qlsl.androiddesign.http.service.commonservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.entity.commonentity.UpdateInfo;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.method.HttpMethod;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.JsonUtil;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 系统模块<br/>
 * 
 */
public class SystemService extends BaseService {

	private static String className = getClassName(SystemService.class);

	/**
	 * 获取软件版本的更新信息<br/>
	 * 为软件第一个网络接口的调用<br/>
	 * 是否传递token都能正常获取数据<br/>
	 */
	public static void getUpdateInfo(final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandlerWithoutShowing(functionView, listener, className, "getUpdateInfo");
		new HandlerThread(className, "getUpdateInfo") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("system_manifest").addParam("type", "30").get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					UpdateInfo updateInfo = JSON.toJavaObject(data, UpdateInfo.class);
					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, updateInfo);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 请求并格式化Json数据<br/>
	 */
	public static void queryJsonData(final String url, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "queryJsonData");
		new HandlerThread(className, "queryJsonData") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setUrl(url).request();
					if (jo == null) {
						if (HttpMethod.isNetworkConnect()) {
							handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL,
									MessageConstant.MSG_SERVER_FAILED);
						} else {
							handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL,
									MessageConstant.MSG_CLIENT_FAILED);
						}
						return;
					}

					String result = JsonUtil.formatJson(jo.toJSONString(), "    ");
					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, result);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

}
