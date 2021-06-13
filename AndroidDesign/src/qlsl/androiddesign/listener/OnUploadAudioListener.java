package qlsl.androiddesign.listener;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.manager.ActivityManager;

/**
 * 上传音频的回调<br/>
 * 
 */
public abstract class OnUploadAudioListener extends RequestCallBack<String> {

	public void onSuccess(ResponseInfo<String> responseInfo) {
		ActivityManager.getInstance().showToast("上传成功");
		String responseStr = responseInfo.result;
		JSONObject jo = JSONObject.parseObject(responseStr);
		if (jo == null) {
			ActivityManager.getInstance().showToast(MessageConstant.MSG_CLIENT_FAILED);
			return;
		}
		Integer state = jo.getInteger("state");
		if (state == 0) {
			String msg = jo.getString("msg");
			ActivityManager.getInstance().showToast(msg);
			return;
		}
		Object[] array = JSONArray.parseArray(jo.getString("urls")).toArray();
		List<String> urls = new ArrayList<String>();
		for (Object object : array) {
			urls.add(object.toString());
		}
		onSuccess(urls);
	}

	public void onLoading(long total, long current, boolean isUploading) {
		if (isUploading) {
			ActivityManager.getInstance().showToast("正在上传音频：" + current * 100 / total + "%");
		}
	}

	public void onFailure(HttpException error, String msg) {
		ActivityManager.getInstance().showToast("上传失败：" + msg);
	}

	public abstract void onSuccess(List<String> urls);

}
