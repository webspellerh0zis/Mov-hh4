package qlsl.androiddesign.http.service.commonservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * **模块 <br/>
 * 
 */
public class CopyService extends BaseService {

	private static String className = getClassName(CopyService.class);

	public static void getVersionInfo(final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "getVersionInfo");
		new HandlerThread(className, "getVersionInfo") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("system_manifest").addParam("type", "30").get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					
					Object object = JSON.toJavaObject(jo, Object.class);
					
					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, object);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}
}
