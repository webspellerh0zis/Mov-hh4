package qlsl.androiddesign.handler.subhandler;

import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.basehandler.Handler;
import qlsl.androiddesign.util.commonutil.Log;

public class HttpHandler extends Handler {

	public HttpHandler(String method) {
		super();
	}

	public void handleMessage(android.os.Message msg, final String url, final String params, final String className,
			final String method) {
		super.handleMessage(msg);
		switch (msg.what) {
		case WhatConstant.WHAT_NET_DATA_SUCCESS:
			outputMessageInfo("WHAT_NET_DATA_SUCCESS", url, params, className, method);
			break;
		case WhatConstant.WHAT_NET_DATA_FAIL:
			outputMessageInfo("WHAT_NET_DATA_FAIL", url, params, className, method);
			break;
		case WhatConstant.WHAT_DB_DATA_SUCCESS:
			outputMessageInfo("WHAT_DB_DATA_SUCCESS", url, params, className, method);
			break;
		case WhatConstant.WHAT_DB_DATA_FAIL:
			outputMessageInfo("WHAT_DB_DATA_FAIL", url, params, className, method);
			break;
		case WhatConstant.WHAT_EXCEPITON:
			outputMessageInfo("WHAT_EXCEPITON", url, params, className, method);
			break;
		case WhatConstant.WHAT_CANCEL:
			outputMessageInfo("WHAT_CANCEL", url, params, className, method);
			break;
		}

	}

	private void outputMessageInfo(String status, String url, String params, String className, String method) {
		if (MessageConstant.MSG_UNKNOW.equals(url)) {
			Log.i("网络正在返回：" + method + "<br/>" + className + "<br/>url：" + url + "<br/>params：" + params
					+ "<br/>status：" + status);
		} else {
			Log.i("网络正在返回：" + method + "<br/>" + className + "<br/>url：<a href=\"" + url + "\">" + url
					+ "</a><br/>params：" + params + "<br/>status：" + status);
		}
	}
}
