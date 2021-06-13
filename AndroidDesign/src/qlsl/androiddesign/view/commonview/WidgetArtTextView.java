package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;
import qlsl.androiddesign.view.rippleview.Typefaces;

/**
 * 控件调试模块中的艺术字体TextView，禁止了长按时的调试缩略窗<br/>
 * 不强制开启或关闭点击，默认关闭，不能播放语音，不会影响itemView点击，开启后能够播放<br/>
 * 自动播放语音<br/>
 */
public class WidgetArtTextView extends android.widget.TextView {

	public WidgetArtTextView(Context context) {
		super(context);
	}

	public WidgetArtTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WidgetArtTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setTypeface(Typeface tf) {
		ToolColor toolColor = ToolColorUtils.get();
		if (toolColor == null) {
			super.setTypeface(Typefaces.get(getContext()));
		} else {
			if (toolColor.getIsOpenArt()) {
				super.setTypeface(Typefaces.get(getContext()));
			}
			// super.setTextColor(Color.parseColor(toolColor.getTextColor()));
			// super.setTextSize(toolColor.getTextSize());
		}
	}

	public boolean performClick() {
		ToolSpeechUtils.startSpeaking(getText().toString(), 1);
		return super.performClick();
	}

}
