package qlsl.androiddesign.view.subview.commonview;

import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.OcrCornerActivity;
import qlsl.androiddesign.activity.commonactivity.OcrDeskewActivity;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.commonview.CornerView;

/**
 * OCR角点检测页面<br/>
 * 需要传入的键：path<br/>
 * 传入的值类型：String <br/>
 * 传入的值含义：图片路径<br/>
 * 是否必传 ：是
 */
public class OcrCornerView extends FunctionView<OcrCornerActivity>implements OnSeekBarChangeListener {

	private SeekBar seekBar;

	private CornerView cornerView;

	public OcrCornerView(OcrCornerActivity activity) {
		super(activity);
		setContentView(R.layout.activity_ocr_corner);
	}

	protected void initView(View view) {
		hideTitleBar();
		seekBar = findViewById(R.id.sb_threshold);
		cornerView = findViewById(R.id.cornerView);
	}

	protected void initData() {
		setCornerViewData();
	}

	protected void initListener() {
		seekBar.setOnSeekBarChangeListener(this);
	}

	@SuppressWarnings("unchecked")
	public <T> void showData(T... t) {
		if (t[0] instanceof List) {
			List<Point> list = (List<Point>) t[0];
			cornerView.setPoints(list.get(0), list.get(1), list.get(2), list.get(3));
		} else if (t[0] instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) t[0];
			Bitmap bitmap = (Bitmap) map.get("bitmap");
			double scale = (Double) map.get("scale");
			cornerView.preQueryPixelPoint(bitmap, scale);
		} else if (t[0] instanceof String) {
			String path = (String) t[0];
			Intent intent = new Intent(activity, OcrDeskewActivity.class);
			intent.putExtra("path", path);
			startActivity(intent);
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_check:
			doClickCheckView();
			break;
		case R.id.iv_finish:
			doClickFinishView();
			break;
		case R.id.iv_convert:
			doClickConvertView();
			break;
		case R.id.iv_rotation_left:
			doClickRotationLeftView();
			break;
		case R.id.iv_rotation_right:
			doClickRotationRightView();
			break;
		case R.id.iv_ok:
			doClickOKView();
			break;
		}

	}

	private void setCornerViewData() {
		String filename = activity.getIntent().getStringExtra("path");
		cornerView.setFileName(filename);
	}

	private void doClickCheckView() {
		cornerView.queryPixelPoint();
	}

	private void doClickFinishView() {
		activity.finish();
	}

	private void doClickConvertView() {
		cornerView.convertPoints();
	}

	private void doClickRotationLeftView() {
		cornerView.rotationView(-90);
	}

	private void doClickRotationRightView() {
		cornerView.rotationView(90);
	}

	private void doClickOKView() {
		cornerView.deskewView();
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2)).setText(progress + "");
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

}
