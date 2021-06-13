package qlsl.androiddesign.http.xutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import qlsl.androiddesign.config.SystemConfig;
import qlsl.androiddesign.constant.BaseConstant;
import qlsl.androiddesign.constant.HttpKeyConstant;
import qlsl.androiddesign.constant.UrlConstant;
import qlsl.androiddesign.exception.ConstantsUninitializeException;
import qlsl.androiddesign.method.AppMethod;
import qlsl.androiddesign.method.HttpMethod;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.commonutil.Log;

/**
 * 基于xutils的网络请求封装类<br/>
 * by ylq at 20150921<br/>
 * 暂未作并发考虑<br/>
 * 
 */
public class HttpProtocolOld extends Protocol {

	private TreeMap<String, Object> params = new TreeMap<String, Object>();

	private HttpRequest.HttpMethod httpMethod;

	private static long tick;

	private static long lastTime;

	public HttpProtocolOld() {
		try {
			url = SystemConfig.getUrl();
		} catch (ConstantsUninitializeException e) {
			e.printStackTrace();
			outputException(e);
		}
	}

	public Protocol addParam(String key, Object value) {
		if (value instanceof Boolean) {
			value = (Boolean) value ? 1 : 0;
		}
		if (value != null) {
			params.put(key, value);
		}
		return this;
	}

	private void addSignedParams() {
		addParam(HttpKeyConstant.API_METHOD, getMethod());
		addParam(HttpKeyConstant.PARAM_TICK, Long.toString(tick));
		String token = BaseConstant.TOKEN;
		if (token == null) {
			token = UserMethod.getTokenFromProperty();
		}
		if (token != null) {
			addParam(HttpKeyConstant.PARAM_TOKEN, token);
		}
	}

	private RequestParams convertParams() {
		RequestParams requestParams = new RequestParams();
		Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (httpMethod == HttpRequest.HttpMethod.GET) {
				requestParams.addQueryStringParameter(key, value + "");
			} else if (httpMethod == HttpRequest.HttpMethod.POST) {
				requestParams.addBodyParameter(key, value + "");
			}
		}
		return requestParams;
	}

	public void updateTimestamp() {
		if (tick > 0) {
			long tickNow = System.currentTimeMillis();
			tick += (tickNow - lastTime) * 10000;
			lastTime = tickNow;
		} else {
			HttpUtils http = createDefaultHttpUtils();
			try {
				ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.GET, UrlConstant.TICK_URL);
				String responseStr = responseStream.readString();
				tick = Long.parseLong(responseStr);
				lastTime = System.currentTimeMillis();
			} catch (HttpException e) {
				e.printStackTrace();
				outputException(e);
			} catch (IOException e) {
				e.printStackTrace();
				outputException(e);
			}
		}
	}

	/**
	 * 同步请求,必须异步调用<br/>
	 * GET请求<br/>
	 */
	public JSONObject get() {
		httpMethod = HttpRequest.HttpMethod.GET;
		updateTimestamp();
		return requestGet();
	}

	/**
	 * 同步请求,必须异步调用<br/>
	 * POST请求<br/>
	 */
	public JSONObject post() {
		httpMethod = HttpRequest.HttpMethod.POST;
		updateTimestamp();
		return requestPost();
	}

	/**
	 * 异步请求<br/>
	 * 上传文件<br/>
	 */
	public <T> void uploadFile(File file, FileType type, RequestCallBack<T> callBack) {
		httpMethod = HttpRequest.HttpMethod.POST;
		updateTimestamp();
		requestUploadFile(file, type, callBack);
	}

	/**
	 * 异步请求<br/>
	 * 批量上传文件<br/>
	 */
	public <T> void uploadFiles(List<File> files, FileType type, RequestCallBack<T> callBack) {
		if (files.size() == 0) {
			return;
		}
		httpMethod = HttpRequest.HttpMethod.POST;
		updateTimestamp();
		requestUploadFiles(files, type, callBack);
	}

	public JSONObject request() {
		HttpUtils http = createDefaultHttpUtils();
		try {
			ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.GET, url);
			String responseStr = responseStream.readString();
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			return jsonObject;
		} catch (HttpException e) {
			e.printStackTrace();
			outputException(e);
		} catch (IOException e) {
			e.printStackTrace();
			outputException(e);
		}
		return null;
	}

	public JSONObject requestGet() {
		HttpUtils http = createDefaultHttpUtils();
		try {
			ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.GET, url, prepareRequestParams());
			String responseStr = responseStream.readString();
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			return jsonObject;
		} catch (HttpException e) {
			e.printStackTrace();
			outputException(e);
		} catch (IOException e) {
			e.printStackTrace();
			outputException(e);
		}
		return null;
	}

	public JSONObject requestPost() {
		HttpUtils http = createDefaultHttpUtils();
		try {
			ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST, url, prepareRequestParams());
			String responseStr = responseStream.readString();
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			return jsonObject;
		} catch (HttpException e) {
			e.printStackTrace();
			outputException(e);
		} catch (IOException e) {
			e.printStackTrace();
			outputException(e);
		}
		return null;
	}

	public <T> void requestUploadFile(File file, FileType type, RequestCallBack<T> callBack) {
		HttpUtils http = createDefaultHttpUtils();
		RequestParams requestParams = new RequestParams();
		try {
			String url = null;
			String key_suffix = null;
			if (type == FileType.PICTURE) {
				url = UrlConstant.URL_FILE_PHOTO_SERVER;
				key_suffix = KEY_PICTURE;
			} else if (type == FileType.AUDIO) {
				url = UrlConstant.URL_FILE_AUDIO_SERVER;
				key_suffix = KEY_AUDIO;
			}
			requestParams.addBodyParameter(key_suffix + 0, new FileInputStream(file), file.length(), file.getName(),
					"application/octet-stream");
			http.send(HttpRequest.HttpMethod.POST, url, requestParams, callBack);
		} catch (Exception e) {
			e.printStackTrace();
			outputException(e);
		}
	}

	public <T> void requestUploadFiles(List<File> files, FileType type, RequestCallBack<T> callBack) {
		HttpUtils http = createDefaultHttpUtils();
		RequestParams requestParams = new RequestParams();
		try {
			String url = null;
			String key_suffix = null;
			if (type == FileType.PICTURE) {
				url = UrlConstant.URL_FILE_PHOTO_SERVER;
				key_suffix = KEY_PICTURE;
			} else if (type == FileType.AUDIO) {
				url = UrlConstant.URL_FILE_AUDIO_SERVER;
				key_suffix = KEY_AUDIO;
			}
			for (int index = 0; index < files.size(); index++) {
				requestParams.addBodyParameter(key_suffix + index, new FileInputStream(files.get(index)),
						files.get(index).length(), files.get(index).getName(), "application/octet-stream");
			}
			http.send(HttpRequest.HttpMethod.POST, url, requestParams, callBack);
		} catch (Exception e) {
			e.printStackTrace();
			outputException(e);
		}
	}

	private RequestParams prepareRequestParams() {
		addSignedParams();
		RequestParams requestParams = convertParams();
		String paramsStr = HttpMethod.getParamsStr(params);
		String signStr = HttpMethod.getSignStr(paramsStr);
		if (httpMethod == HttpRequest.HttpMethod.GET) {
			requestParams.addQueryStringParameter(HttpKeyConstant.PARAM_SIGN, signStr);
			requestParams.addQueryStringParameter(HttpKeyConstant.PARAM_PARTNER, HttpKeyConstant.VALUE_PARTNER);
		} else if (httpMethod == HttpRequest.HttpMethod.POST) {
			requestParams.addBodyParameter(HttpKeyConstant.PARAM_SIGN, signStr);
			requestParams.addBodyParameter(HttpKeyConstant.PARAM_PARTNER, HttpKeyConstant.VALUE_PARTNER);
		}
		addParam(HttpKeyConstant.PARAM_SIGN, signStr);
		addParam(HttpKeyConstant.PARAM_PARTNER, HttpKeyConstant.VALUE_PARTNER);
		requestParams.addHeader("Connection", "Keep-Alive");
		requestParams.addHeader("User-Agent", AppMethod.getUserAgent());
		return requestParams;
	}

	private HttpUtils createDefaultHttpUtils() {
		HttpUtils http = new HttpUtils();
		http.configDefaultHttpCacheExpiry(1000 * 10);
		http.configRequestRetryCount(1);
		return http;
	}

	/**
	 * 输出网络错误详情<br/>
	 */
	private void outputException(Exception e) {
		Log.e(getClass().getName() + "<br/>" + e);
	}

	/**
	 * 获取基本url与参数的拼接字符串<br/>
	 * 只能在网络请求返回之后调用，才能获取准确的参数<br/>
	 */
	public String getUrl() {
		if (params.size() == 0) {
			return url;
		} else {
			StringBuffer sb = new StringBuffer(url + "?");
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				sb.append(key + "=" + value + "&");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
	}

	/**
	 * 获取参数<br/>
	 * 只能在网络请求返回之后调用，才能获取准确的参数<br/>
	 */
	public Object getParams() {
		return params;
	}
	
	/**
	 * 清除静态数据<br/>
	 */
	public static void clearStaticData() {
		tick = 0;
		lastTime = 0;
	}

}
