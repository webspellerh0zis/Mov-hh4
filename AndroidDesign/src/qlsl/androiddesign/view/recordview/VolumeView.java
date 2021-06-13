package qlsl.androiddesign.view.recordview;

import com.qlsl.androiddesign.appname.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 录音的音量控制控件<br/>
 */
public class VolumeView extends ImageView {

	private Paint mPaint;

	private int[] volumeImages = new int[] { R.drawable.chat_icon_voice1,
			R.drawable.chat_icon_voice2, R.drawable.chat_icon_voice3,
			R.drawable.chat_icon_voice4, R.drawable.chat_icon_voice5,
			R.drawable.chat_icon_voice6 };

	public VolumeView(Context context) {
		super(context);
		init(context);
	}

	public VolumeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		setVolumeValue(0);
	}

	public void setVolumeValue(int volume) {
		if (volume < 0) {
			volume = 0;
		} else if (volume > 5) {
			volume = 5;
		}
		setImageResource(volumeImages[volume]);
	}

}
