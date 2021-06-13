package qlsl.androiddesign.listener;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebViewProgressListener extends WebChromeClient {

	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
	}
}
