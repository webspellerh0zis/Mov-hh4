package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * 破除completionThreshold位数限制,从0开始<br/>
 * completionThreshold设置无效<br/>
 * 
 */
public class AutoCompleteTextView extends android.widget.AutoCompleteTextView {

	public AutoCompleteTextView(Context context) {
		super(context);
	}

	public AutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean enoughToFilter() {
		return true;
	}

	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		showListPopWindow();
	}

	public boolean performClick() {
		showListPopWindow();
		return false;
	}

	private void showListPopWindow() {
		CharSequence text = getText();
		if (!TextUtils.isEmpty(text)) {
			performFiltering(text, KeyEvent.KEYCODE_UNKNOWN);
		} else {
			String emptyText = "";
			setText(emptyText);
			performFiltering(emptyText, KeyEvent.KEYCODE_UNKNOWN);
		}
	}

}
