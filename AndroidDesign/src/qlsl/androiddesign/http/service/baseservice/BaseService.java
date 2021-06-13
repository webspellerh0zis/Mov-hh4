package qlsl.androiddesign.http.service.baseservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSONObject;
import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.InstanceActivity;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.activity.commonactivity.MemberLoginActivity;
import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.constant.UrlConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.basehandler.Handler;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.xutils.Protocol;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshAdapterViewBase;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.method.AppMethod;
import qlsl.androiddesign.method.HttpMethod;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 网络连接Service的基类，提供了两种客户端与服务器的连接方式<br/>
 * 网络返回后，对结果进行回调<br/>
 * 网络连接开始后，自动执行进度条的显示<br/>
 * 网络连接回调后，自动执行进度条的隐藏<br/>
 * 默认兼容xutils包中的Protocol，可修改兼容http包中的Protocol<br/>
 * by ylq updateAt 20150917<br/>
 */
public class BaseService {

	protected static HttpHandler getHandler(final FunctionView<?> functionView, final HttpListener listener,
			final String params, final String className, final String method) {
		showProgressBar(functionView);
		HttpHandler handler = new HttpHandler(method) {
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg, UrlConstant.BASE_URL, params, className, method);
				showEmptyView(functionView, msg.what);
				switch (msg.what) {
				case WhatConstant.WHAT_NET_DATA_SUCCESS:
					listener.onNetWorkSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_NET_DATA_FAIL:
					listener.onNetWorkFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_SUCCESS:
					listener.onDbSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_FAIL:
					listener.onDbFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_SUCCESS:
					listener.onOtherSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_FAIL:
					listener.onOtherFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_EXCEPITON:
					listener.onException(className, method, (Exception) msg.obj);
					break;
				case WhatConstant.WHAT_CANCEL:
					listener.onCancel(method);
					break;
				}
				hideProgressBar(functionView);
			};
		};
		return handler;
	}

	/**
	 * 获取HttpHandler<br/>
	 * 自动控制ProgressBar的显示与隐藏<br/>
	 */
	protected static HttpHandler getHandler(final FunctionView<?> functionView, final HttpListener listener,
			final String className, final String method) {
		showProgressBar(functionView);
		HttpHandler handler = new HttpHandler(method) {
			public void handleMessage(android.os.Message msg) {
				Protocol protocol = getProtocol();
				if (protocol != null) {
					// super.handleMessage(msg, UrlConstant.BASE_URL +
					// protocol.getMethod(), protocol.getParams() + "",
					// className, method);
					super.handleMessage(msg, protocol.getUrl(), "略", className, method);
				} else {
					super.handleMessage(msg, MessageConstant.MSG_UNKNOW, MessageConstant.MSG_UNKNOW, className, method);
				}
				showEmptyView(functionView, msg.what);
				switch (msg.what) {
				case WhatConstant.WHAT_NET_DATA_SUCCESS:
					listener.onNetWorkSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_NET_DATA_FAIL:
					listener.onNetWorkFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_SUCCESS:
					listener.onDbSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_FAIL:
					listener.onDbFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_SUCCESS:
					listener.onOtherSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_FAIL:
					listener.onOtherFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_EXCEPITON:
					listener.onException(className, method, (Exception) msg.obj);
					break;
				case WhatConstant.WHAT_CANCEL:
					listener.onCancel(method);
					break;
				}
				hideProgressBar(functionView);
			};
		};
		return handler;
	}

	/**
	 * 获取HttpHandler<br/>
	 * ProgressBar保持显示，不自动隐藏<br/>
	 */
	protected static HttpHandler getHandlerWithShowing(final FunctionView<?> functionView, final HttpListener listener,
			final String className, final String method) {
		showProgressBar(functionView);
		HttpHandler handler = new HttpHandler(method) {
			public void handleMessage(android.os.Message msg) {
				Protocol protocol = getProtocol();
				if (protocol != null) {
					// super.handleMessage(msg, UrlConstant.BASE_URL +
					// protocol.getMethod(), protocol.getParams() + "",
					// className, method);
					super.handleMessage(msg, protocol.getUrl(), "略", className, method);
				} else {
					super.handleMessage(msg, MessageConstant.MSG_UNKNOW, MessageConstant.MSG_UNKNOW, className, method);
				}
				showEmptyView(functionView, msg.what);
				switch (msg.what) {
				case WhatConstant.WHAT_NET_DATA_SUCCESS:
					listener.onNetWorkSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_NET_DATA_FAIL:
					listener.onNetWorkFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_SUCCESS:
					listener.onDbSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_FAIL:
					listener.onDbFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_SUCCESS:
					listener.onOtherSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_FAIL:
					listener.onOtherFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_EXCEPITON:
					listener.onException(className, method, (Exception) msg.obj);
					break;
				case WhatConstant.WHAT_CANCEL:
					listener.onCancel(method);
					break;
				}

			};
		};
		return handler;
	}

	/**
	 * 获取HttpHandler<br/>
	 * ProgressBar不自动显示，不自动隐藏<br/>
	 */
	protected static HttpHandler getHandlerWithoutShowing(final FunctionView<?> functionView,
			final HttpListener listener, final String className, final String method) {
		HttpHandler handler = new HttpHandler(method) {
			public void handleMessage(android.os.Message msg) {
				Protocol protocol = getProtocol();
				if (protocol != null) {
					// super.handleMessage(msg, UrlConstant.BASE_URL +
					// protocol.getMethod(), protocol.getParams() + "",
					// className, method);
					super.handleMessage(msg, protocol.getUrl(), "略", className, method);
				} else {
					super.handleMessage(msg, MessageConstant.MSG_UNKNOW, MessageConstant.MSG_UNKNOW, className, method);
				}
				showEmptyView(functionView, msg.what);
				switch (msg.what) {
				case WhatConstant.WHAT_NET_DATA_SUCCESS:
					listener.onNetWorkSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_NET_DATA_FAIL:
					listener.onNetWorkFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_SUCCESS:
					listener.onDbSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_DB_DATA_FAIL:
					listener.onDbFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_SUCCESS:
					listener.onOtherSucceed(method, msg.obj);
					break;
				case WhatConstant.WHAT_OTHER_DATA_FAIL:
					listener.onOtherFaild(method, msg.obj);
					break;
				case WhatConstant.WHAT_EXCEPITON:
					listener.onException(className, method, (Exception) msg.obj);
					break;
				case WhatConstant.WHAT_CANCEL:
					listener.onCancel(method);
					break;
				}

			};
		};
		return handler;
	}

	private static void showProgressBar(FunctionView<?> functionView) {
		functionView.showProgressBar();
	}

	private static void hideProgressBar(FunctionView<?> functionView) {
		functionView.hideProgressBar();
		functionView.resetProgressBarText();
	}

	private static void showEmptyView(FunctionView<?> functionView, int what) {
		TextView tv_empty = functionView.findViewById(R.id.tv_empty);
		if (tv_empty == null) {
			return;
		}
		tv_empty.setText(functionView.activity.getString(R.string.no_data));
		ViewGroup parentView = functionView.findViewById(R.id.emptyParentView);
		View childView = parentView.getChildAt(0);
		if (childView instanceof PullToRefreshAdapterViewBase) {
			PullToRefreshAdapterViewBase<?> refreshView = (PullToRefreshAdapterViewBase<?>) childView;
			if (what == WhatConstant.WHAT_NET_DATA_FAIL) {
				tv_empty.setText(MessageConstant.MSG_CLIENT_FAILED);
				refreshView.onRefreshComplete();
			}
			refreshView.setEmptyView(tv_empty);
		} else if (childView instanceof AdapterView) {
			AdapterView<?> adapterView = (AdapterView<?>) childView;
			if (what == WhatConstant.WHAT_NET_DATA_FAIL) {
				tv_empty.setText(MessageConstant.MSG_CLIENT_FAILED);
			}
			adapterView.setEmptyView(tv_empty);
		}
	}

	public interface OnCallBack<T> {
		public void onSuccess(T t);
	}

	public static InputStream getInputStream(String urlPath) {

		InputStream inputStream = null;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestProperty("Charsert", HTTP.UTF_8);

			if (conn.getContentLength() > 0) {
				inputStream = conn.getInputStream();
			}
		} catch (Exception e) {

		}

		return inputStream;
	}

	public static String getInputStreamString(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		int b;
		StringBuffer sb = new StringBuffer();
		try {
			while ((b = inputStream.read()) != -1) {
				if ((char) b == '\n') {
					sb.append(" ");
				} else {
					sb.append((char) b);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	/**
	 * 静态获取BaseService子类的路径<br/>
	 * 以定位Log信息的具体位置<br/>
	 */
	protected static String getClassName(final Class<? extends BaseService> destClass) {
		return new Object() {
			public String getClassName() {
				return destClass.getName();
			}
		}.getClassName();
	}

	@SuppressLint("SimpleDateFormat")
	protected static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentDate = new Date(System.currentTimeMillis());
		String date = sdf.format(currentDate);
		return date;
	}

	public static boolean checkLogin(final FunctionView<?> functionView) {
		if (!AppMethod.isLogin(functionView.activity)) {
			DialogUtil.showLoginDialog(functionView);
			return false;
		}
		return true;
	}

	/**
	 * 判断网络返回的JsonObject数据是否无效<br/>
	 * 并自动进行失败处理<br/>
	 */
	protected static boolean isDataInvalid(JSONObject jo, Handler handler, Protocol protocol) {
		if (jo == null) {
			if (HttpMethod.isNetworkConnect()) {
				handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL, MessageConstant.MSG_SERVER_FAILED);
			} else {
				handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL, MessageConstant.MSG_CLIENT_FAILED);
			}
			return true;
		} else if (!jo.getBooleanValue("status")) {
			String message = jo.getString("message");
			handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL, message);
			if (message.equals(MessageConstant.MSG_MEMBER_INVALID)
					|| message.equals(MessageConstant.MSG_LOGIN_INVALID)) {
				final ActivityManager manager = ActivityManager.getInstance();
				manager.currentActivity().runOnUiThread(new Runnable() {

					public void run() {
						UserMethod.setToken(null);
						manager.popToActivity(MainActivity.class);
						manager.startActivity(MemberLoginActivity.class);
						manager.popActivity(MainActivity.class);
						manager.popActivity(InstanceActivity.class);
					}
				});
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断上传文件网络返回的JsonObject数据是否无效<br/>
	 * 并自动进行失败处理<br/>
	 */
	protected static boolean isFileInvalid(JSONObject jo, Handler handler, Protocol protocol) {
		if (jo == null) {
			handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL, MessageConstant.MSG_CLIENT_FAILED);
			return true;
		} else if (jo.getInteger("state") != 1) {
			handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_FAIL, MessageConstant.MSG_UPLOAD_FILE_FAILED);
			return true;
		}
		return false;
	}

}
