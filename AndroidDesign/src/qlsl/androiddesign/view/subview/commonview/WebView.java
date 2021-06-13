package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import qlsl.androiddesign.activity.commonactivity.WebActivity;
import qlsl.androiddesign.listener.WebViewLoadListener;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * Web网址浏览页<br/>
 * 需要传入的键：url<br/>
 * 传入的值类型： String<br/>
 * 传入的值含义：网址<br/>
 * 是否必传 ：是
 */
public class WebView extends FunctionView<WebActivity> {

	private android.webkit.WebView webView;

	public WebView(WebActivity activity) {
		super(activity);
		setContentView(R.layout.activity_web);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	protected void initView(View view) {
		setTitle("数据浏览");
		hideTitleBar();
		setImmersionBarColor(Color.parseColor("#459DF5"));
		webView = findViewById(R.id.webView);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		webView.getSettings().setUseWideViewPort(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setWebViewClient(new WebViewLoadListener());
		showProgressBar();
	}

	protected void initData() {
		setWebViewData();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

	private void setWebViewData() {
		String url = activity.getIntent().getStringExtra("url");
		webView.loadUrl(url);
	}

}
