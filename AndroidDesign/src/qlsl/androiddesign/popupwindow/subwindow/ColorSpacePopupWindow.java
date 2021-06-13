package qlsl.androiddesign.popupwindow.subwindow;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;

@SuppressLint("ViewConstructor")
public class ColorSpacePopupWindow extends PopupWindow implements OnClickListener, OnSeekBarChangeListener {

	private Bitmap bitmap;

	private ImageView iv_ori;

	private SeekBar sb_guide1, sb_guide2, sb_guide3;

	public ColorSpacePopupWindow(BaseActivity activity, ImageView iv_ori, Bitmap bitmap) {
		super(activity);
		this.iv_ori = iv_ori;
		this.bitmap = bitmap;
		initView();
		setPopupWindowAttribute();
		doClickResetView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_color_space, null);
		setContentView(rootView);
		ImageView iv_reset = (ImageView) rootView.findViewById(R.id.iv_reset);
		sb_guide1 = (SeekBar) rootView.findViewById(R.id.sb_guide1);
		sb_guide2 = (SeekBar) rootView.findViewById(R.id.sb_guide2);
		sb_guide3 = (SeekBar) rootView.findViewById(R.id.sb_guide3);
		iv_reset.setOnClickListener(this);
		sb_guide1.setOnSeekBarChangeListener(this);
		sb_guide2.setOnSeekBarChangeListener(this);
		sb_guide3.setOnSeekBarChangeListener(this);
	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimTop);
		ColorDrawable colorDrawable = new ColorDrawable(0x20464646);
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_reset:
			doClickResetView();
			break;
		}
	}

	private void doClickResetView() {
		View rootView = getContentView();
		SeekBar sb_guide1 = (SeekBar) rootView.findViewById(R.id.sb_guide1);
		SeekBar sb_guide2 = (SeekBar) rootView.findViewById(R.id.sb_guide2);
		SeekBar sb_guide3 = (SeekBar) rootView.findViewById(R.id.sb_guide3);

		sb_guide1.setProgress(0);
		sb_guide2.setProgress(0);
		sb_guide3.setProgress(0);
		((TextView) ((ViewGroup) sb_guide1.getParent()).getChildAt(2)).setText(sb_guide1.getProgress() + "");
		((TextView) ((ViewGroup) sb_guide2.getParent()).getChildAt(2)).setText(sb_guide2.getProgress() + "");
		((TextView) ((ViewGroup) sb_guide3.getParent()).getChildAt(2)).setText(sb_guide3.getProgress() + "");

		iv_ori.setImageBitmap(bitmap);

		TextView tv_text = (TextView) getContentView().findViewById(R.id.tv_text);
		tv_text.setText("原图");
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2)).setText(progress + "");
		TextView tv_text = (TextView) getContentView().findViewById(R.id.tv_text);
		String text = null;
		switch (seekBar.getId()) {
		case R.id.sb_guide1:
			text = "对比度：" + progress;
			break;
		case R.id.sb_guide2:
			text = "亮度：" + progress;
			break;
		case R.id.sb_guide3:
			text = "饱和度：" + progress;
			break;
		}
		tv_text.setText(text);
		changeColorSpace();
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void changeColorSpace() {
		Bitmap bitmapDst = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		int progress1 = sb_guide1.getProgress();
		int progress2 = sb_guide2.getProgress();
		int progress3 = sb_guide3.getProgress();
		if (progress1 == 0 && progress2 == 0 && progress3 == 0) {
			doClickResetView();
			return;
		}
		float contrast = progress1 / 100f;
		float brightness = progress2 - 100;
		ColorMatrix matrix = new ColorMatrix();

		ColorMatrix matrix1 = new ColorMatrix();
		matrix1.set(new float[] { contrast, 0, 0, 0, 0, 0, contrast, 0, 0, 0, 0, 0, contrast, 0, 0, 0, 0, 0, 1, 0 });
		ColorMatrix matrix2 = new ColorMatrix();
		matrix2.set(
				new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		ColorMatrix matrix3 = new ColorMatrix();
		matrix3.setSaturation(progress3 / 100f);
		matrix.postConcat(matrix1);
		matrix.postConcat(matrix2);
		matrix.postConcat(matrix3);

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(matrix));
		Canvas canvas = new Canvas(bitmapDst);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		iv_ori.setImageBitmap(bitmapDst);
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
