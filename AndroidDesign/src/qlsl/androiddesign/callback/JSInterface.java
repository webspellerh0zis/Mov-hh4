package qlsl.androiddesign.callback;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import qlsl.androiddesign.activity.commonactivity.PhotoBrowseActivity;

/**
 * JS调Android通信接口<br/>
 * addJavascriptInterface最好在Client加载监听前调用，否则图片点击可能不会回调<br/>
 */
public class JSInterface {

	private Context context;

	private WebView webView;

	public JSInterface(Context context, WebView webView) {
		this.context = context;
		this.webView = webView;
		webView.addJavascriptInterface(this, "interface");
	}

	/**
	 * 添加图片点击事件<br/>
	 */
	public void addWebImageClickListener() {
		webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{" + "    objs[i].onclick=function()  " + "    {  "
				+ "        window.interface.doClickWebImage(this.src);  " + "    }  " + "}" + "})()");
	}

	/**
	 * Web图片点击事件<br/>
	 * 增加注解以适应高版本设备<br/>
	 */
	@JavascriptInterface
	public void doClickWebImage(String imgUrl) {
		ArrayList<String> urls = new ArrayList<String>();
		urls.add(imgUrl);
		Intent intent = new Intent(context, PhotoBrowseActivity.class);
		intent.putExtra("list", urls);
		context.startActivity(intent);
	}
}
