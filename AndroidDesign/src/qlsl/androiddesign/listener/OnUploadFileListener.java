package qlsl.androiddesign.listener;

import static qlsl.androiddesign.http.xutils.Protocol.KEY_AUDIO;
import static qlsl.androiddesign.http.xutils.Protocol.KEY_PICTURE;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.widget.Toast;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.http.xutils.Protocol.FileType;
import qlsl.androiddesign.manager.ActivityManager;

/**
 * 上传图片,音频等文件的公用回调<br/>
 * 
 */
public abstract class OnUploadFileListener extends RequestCallBack<String> {

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
		String key = JSONArray.parseArray(jo.getString("keys")).toArray()[0].toString();
		List<String> urls = new ArrayList<String>();
		for (Object object : array) {
			urls.add(object.toString());
		}
		if (key.contains(KEY_PICTURE)) {
			onSuccess(urls, FileType.PICTURE);
		} else if (key.contains(KEY_AUDIO)) {
			onSuccess(urls, FileType.AUDIO);
		}
	}

	public void onLoading(long total, long current, boolean isUploading) {
		if (isUploading) {
			ActivityManager.getInstance().showToast("正在上传文件：" + current * 100 / total + "%");
		}
	}

	public void onFailure(HttpException error, String msg) {
		Toast.makeText(SoftwareApplication.getInstance(), "上传失败：" + msg, Toast.LENGTH_SHORT).show();
	}

	public abstract void onSuccess(List<String> urls, FileType type);

}
