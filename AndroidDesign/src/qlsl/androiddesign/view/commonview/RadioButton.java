package qlsl.androiddesign.view.commonview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.view.rippleview.Typefaces;

public class RadioButton extends android.widget.RadioButton {

	public RadioButton(Context context) {
		super(context);
	}

	public RadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RadioButton(Context context, AttributeSet attrs, int defStyle) {
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

}
