package qlsl.androiddesign.thread;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.subhandler.HttpHandler;

/**
 * HandlerThread的基类<br/>
 * 
 */
public class HandlerThread extends android.os.HandlerThread {

	private HttpHandler handler;

	public HandlerThread(String className, String method) {
		super(method);
		outputThreadInfo(className, method);
	}

	public HandlerThread(String name, int priority) {
		super(name, priority);
	}

	public HandlerThread(String className, String method, HttpHandler handler) {
		super(method);
		this.handler = handler;
		outputThreadInfo(className, method);
	}

	private void outputThreadInfo(String className, String method) {
		// Log.i("正在请求网络：" + method + "<br/>" + className + "<br/>url："
		// + UrlConstant.BASE_URL);
	}

	public void run() {

	}

	/**
	 * 取消加载<br/>
	 * 未验证有效性<br/>
	 */
	public void cancel() {
		handler.sendMessage(WhatConstant.WHAT_CANCEL, null);
		getLooper().quit();
	}

}
