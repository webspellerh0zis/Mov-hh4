package qlsl.androiddesign.listener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.JsonFormatActivity;

/**
 * WebView加载监听<br/>
 */
public class WebViewLoadListener extends WebViewClient {

	/**
	 * 兼容公式显示<br/>
	 */
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if ("0".equals(view.getTag() + "")) {
			BaseActivity activity = (BaseActivity) view.getContext();
			activity.getFunctionView().resetProgressBarText();
			activity.getFunctionView().showProgressBar();
		}
		super.onPageStarted(view, url, favicon);
	}

	/**
	 * 兼容公式显示<br/>
	 */
	public void onPageFinished(WebView view, String url) {
		BaseActivity activity = (BaseActivity) view.getContext();
		activity.getFunctionView().hideProgressBar();
		super.onPageFinished(view, url);
	}

	/**
	 * 兼容日志记录中的接口数据查看<br/>
	 * 兼容网址浏览页<br/>
	 */
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (view.getContext().getClass().getSimpleName().equals("WebActivity")) {
			view.loadUrl(url);
			return true;
		}
		Intent intent = new Intent(view.getContext(), JsonFormatActivity.class);
		intent.putExtra("url", url);
		view.getContext().startActivity(intent);
		return true;
	}

	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		BaseActivity activity = (BaseActivity) view.getContext();
		activity.getFunctionView().hideProgressBar();
		super.onReceivedError(view, errorCode, description, failingUrl);
	}

}
