package qlsl.androiddesign.view.commonview;

import com.qlsl.androiddesign.appname.R;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.callback.JSInterface;
import qlsl.androiddesign.listener.WebViewLoadListener;

/**
 * 显示数学公式的控件<br/>
 * 使用本地引擎解析，引擎在assets中[katex,MathJax,themes]<br/>
 * katex快速，mathjax全面<br/>
 * katex难以支持方程组，mathjax支持,默认使用mathjax<br/>
 * 暂时仅适应ViewPager，若需适应其它父控件，分离后修改onTouchEvent及onGlobalLayout<br/>
 * 内部文本对齐方式暂未解决<br/>
 */
public class MathView extends WebView implements OnGlobalLayoutListener, OnLongClickListener {

	private String mText;

	private int mEngine;

	private boolean hasSettingClick;

	private JSInterface jsInterface;

	@SuppressWarnings("deprecation")
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	public MathView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		getSettings().setJavaScriptEnabled(true);
		getSettings().setRenderPriority(RenderPriority.HIGH);
		getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		getSettings().setUseWideViewPort(false);
		setHorizontalScrollBarEnabled(false);
		setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		getSettings().setDefaultTextEncodingName("utf-8");
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		TypedArray mTypeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MathView, 0, 0);

		try {
			setEngine(mTypeArray.getInteger(R.styleable.MathView_engine, 1));
			setText(mTypeArray.getString(R.styleable.MathView_text));
		} finally {
			mTypeArray.recycle();
		}

		// getViewTreeObserver().addOnGlobalLayoutListener(this);
		jsInterface = new JSInterface(getContext(), this);
		setWebViewClient(loadListener);

		setOnLongClickListener(this);

		/** 内部滑动WebView，边缘滑动ViewPager */
		setClickable(true);
		setLongClickable(true);

	}

	/**
	 * 获取引擎数据块<br/>
	 */
	private Chunk getChunk() {
		String TEMPLATE_KATEX = "katex";
		String TEMPLATE_MATHJAX = "mathjax";
		String template = TEMPLATE_MATHJAX;
		AndroidTemplates loader = new AndroidTemplates(getContext());
		switch (mEngine) {
		case Engine.KATEX:
			template = TEMPLATE_KATEX;
			break;
		case Engine.MATHJAX:
			template = TEMPLATE_MATHJAX;
			break;
		}

		return new Theme(loader).makeChunk(template);
	}

	/**
	 * 设置文本<br/>
	 */
	public void setText(String text) {
		mText = text;
		Chunk chunk = getChunk();

		String TAG_FORMULA = "formula";
		chunk.set(TAG_FORMULA, mText);
		loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
	}

	/**
	 * 获取文本<br/>
	 */
	public String getText() {
		return mText;
	}

	/**
	 * 设置解析引擎<br/>
	 */
	public void setEngine(int engine) {
		switch (engine) {
		case Engine.KATEX:
			mEngine = Engine.KATEX;
			break;
		case Engine.MATHJAX:
			mEngine = Engine.MATHJAX;
			break;
		default:
			mEngine = Engine.MATHJAX;
			break;
		}
	}

	public static class Engine {
		final public static int KATEX = 0;
		final public static int MATHJAX = 1;
	}

	/**
	 * 动态设置是否能点击，触摸调用，轻量级<br/>
	 * 主动传递事件到上层,默认使用<br/>
	 * false时不能兼容图片点击，不可用<br/>
	 */
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEventOld(MotionEvent event) {
		if (!hasSettingClick) {
			if (getMeasuredHeightAndState() > getHeight() || getMeasuredWidthAndState() > getWidth()) {
				setClickable(true);
				setLongClickable(true);
			} else {
				setClickable(false);
				setLongClickable(false);
				if (event != null) {
					View parent = (View) getParent().getParent();
					parent.onTouchEvent(event);
				}
			}
			hasSettingClick = true;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 动态设置是否能点击，触摸调用，轻量级<br/>
	 * 主动传递事件到上层,默认使用<br/>
	 * 能够兼容图片点击<br/>
	 * 不安全，频繁调用onTouchEvent会导致强退，不可用<br/>
	 */
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEventNew(MotionEvent event) {
		setClickable(true);
		setLongClickable(true);
		if (event != null && getMeasuredHeightAndState() <= getHeight() && getMeasuredWidthAndState() <= getWidth()) {
			View parent = (View) getParent().getParent();
			parent.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 静态设置是否能点击，百次回调，重量级<br/>
	 * 不需要传递事件到上层<br/>
	 */
	public void onGlobalLayout() {
		if (getMeasuredHeightAndState() > getHeight() || getMeasuredWidthAndState() > getWidth()) {
			setClickable(true);
			setLongClickable(true);
		} else {
			setClickable(false);
			setLongClickable(false);
		}
	}

	/**
	 * WebView加载监听<br/>
	 */
	private WebViewLoadListener loadListener = new WebViewLoadListener() {

		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			jsInterface.addWebImageClickListener();
		};
	};

	public boolean onLongClick(View v) {
		BaseActivity activity = (BaseActivity) getContext();
		// activity.showToast("上下滑动切换题型");
		activity.showToast("会当凌绝顶~一览众山小~");
		return false;
	}

}