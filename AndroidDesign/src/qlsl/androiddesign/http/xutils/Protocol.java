package qlsl.androiddesign.http.xutils;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.http.callback.RequestCallBack;

import qlsl.androiddesign.exception.SoftwareException;

public abstract class Protocol {

	/**
	 * 图片键前缀
	 */
	public static final String KEY_PICTURE = "picture";

	/**
	 * 音频键前缀
	 */
	public static final String KEY_AUDIO = "audio";

	/**
	 * 服务
	 */
	protected String service;

	/**
	 * method
	 */
	protected String method;

	/**
	 * 基本url
	 */
	protected String url;

	public enum FileType {
		PICTURE, AUDIO
	}

	public String getService() {
		return service;
	}

	public Protocol setService(String service) {
		this.service = service;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public Protocol setMethod(String method) {
		this.method = method;
		return this;
	}

	public Protocol setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * 同步请求,必须异步调用<br/>
	 * GET请求<br/>
	 */
	public abstract JSONObject get() throws SoftwareException;

	/**
	 * 同步请求,必须异步调用<br/>
	 * POST请求<br/>
	 */
	public abstract JSONObject post() throws SoftwareException;

	/**
	 * 同步请求,必须异步调用<br/>
	 * GET直接请求url<br/>
	 */
	public abstract JSONObject request() throws SoftwareException;

	/**
	 * 异步请求<br/>
	 * 上传文件<br/>
	 */
	public abstract <T> void uploadFile(File file, FileType type, RequestCallBack<T> callBack) throws SoftwareException;

	/**
	 * 异步请求<br/>
	 * 批量上传文件<br/>
	 */
	public abstract <T> void uploadFiles(List<File> files, FileType type, RequestCallBack<T> callBack)
			throws SoftwareException;

	/**
	 * 添加参数<br/>
	 */
	public abstract Protocol addParam(String name, Object value);

	/**
	 * 获取基本url与参数的拼接字符串<br/>
	 * 只能在网络请求返回之后调用，才能获取准确的参数<br/>
	 * 
	 */
	public abstract String getUrl();

	/**
	 * 获取参数<br/>
	 * 只能在网络请求返回之后调用，才能获取准确的参数<br/>
	 */
	public abstract Object getParams();

}
