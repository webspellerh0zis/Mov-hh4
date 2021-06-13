package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.OcrDeskewActivity;
import qlsl.androiddesign.http.service.commonservice.OcrService;
import qlsl.androiddesign.popupwindow.subwindow.ColorSpacePopupWindow;
import qlsl.androiddesign.util.commonutil.BitmapCompressor;
import qlsl.androiddesign.util.commonutil.BitmapUtils;
import qlsl.androiddesign.util.commonutil.ScreenUtils;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.photoview.PhotoView;
import qlsl.androiddesign.view.photoview.PhotoViewAttacher.OnMatrixChangedListener;

/**
 * OCR倾斜矫正页面<br/>
 * 需要传入的键：path<br/>
 * 传入的值类型：String<br/>
 * 传入的值含义：图片路径<br/>
 * 是否必传 ：是
 */
public class OcrDeskewView extends FunctionView<OcrDeskewActivity> implements OnMatrixChangedListener {

	private static final int WIDTH_DEST = 780;

	private static final byte DIRECTION_TOP = -1;

	private static final byte DIRECTION_LEFT = -2;

	private static final byte DIRECTION_RIGHT = -3;

	private static final byte DIRECTION_BOTTOM = -4;

	private PhotoView iv_src;

	private TextView tv_size;

	private ColorSpacePopupWindow colorWindow;

	private Bitmap bitmap, bitmapSpace, bitmapTop, bitmapBottom, bitmapLeft, bitmapRight;

	private byte direction = DIRECTION_TOP;

	public OcrDeskewView(OcrDeskewActivity activity) {
		super(activity);
		setContentView(R.layout.activity_ocr_deskew);
	}

	protected void initView(View view) {
		hideTitleBar();
		iv_src = findViewById(R.id.iv_src);
		tv_size = findViewById(R.id.tv_size);
		findViewById(R.id.tv_guide1).setActivated(true);
	}

	protected void initData() {
		setImageViewData();
	}

	protected void initListener() {
		iv_src.setOnMatrixChangeListener(this);
	}

	public <T> void showData(T... t) {
		if (t[0] instanceof Bitmap) {
			bitmapSpace = (Bitmap) t[0];
			colorWindow.setBitmap(bitmapSpace);
			syncSpaceWindow();
			tv_size.setText(bitmapSpace.getWidth() + " × " + bitmapSpace.getHeight() + "px");
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_guide1:
			doClickGuideView(view);
			break;
		case R.id.tv_guide2:
			doClickGuideView(view);
			break;
		case R.id.tv_guide3:
			doClickGuideView(view);
			break;
		case R.id.tv_guide4:
			doClickGuideView(view);
			break;
		case R.id.tv_guide5:
			doClickGuideView(view);
			break;
		case R.id.tv_guide6:
			doClickGuideView(view);
			break;
		case R.id.iv_finish:
			doClickFinishView();
			break;
		case R.id.iv_set:
			doClickSetView();
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

	private void setImageViewData() {
		String path = activity.getIntent().getStringExtra("path");
		bitmap = BitmapFactory.decodeFile(path);
		colorWindow = new ColorSpacePopupWindow(activity, iv_src, bitmap);
		initRotationBitmap();
		tv_size.setText(bitmap.getWidth() + " × " + bitmap.getHeight() + "px");
	}

	private void doClickGuideView(View view) {
		ViewGroup parent = (ViewGroup) view.getParent();
		int position = parent.indexOfChild(view);
		for (int childIndex = 0, childCount = parent.getChildCount(); childIndex < childCount; childIndex++) {
			View currentView = parent.getChildAt(childIndex);
			if (childIndex == position) {
				currentView.setActivated(true);
			} else {
				currentView.setActivated(false);
			}
		}
		notifyGuideViewUpdate(position);
	}

	private void syncGuideView() {
		ViewGroup parent = findViewById(R.id.layout_guide);
		int position = 0;
		for (int childIndex = 0, childCount = parent.getChildCount(); childIndex < childCount; childIndex++) {
			View currentView = parent.getChildAt(childIndex);
			if (currentView.isActivated()) {
				position = childIndex;
				break;
			}
		}
		notifyGuideViewUpdate(position);
	}

	private void notifyGuideViewUpdate(int position) {
		if (position == 0) {
			showShortToast("自动");
			colorWindow.setBitmap(bitmap);
			syncSpaceWindow();
			tv_size.setText(bitmap.getWidth() + " × " + bitmap.getHeight() + "px");
		} else if (position == 1) {
			showShortToast("无增强");
			OcrService.laplaceGradient(bitmap, this, activity);
		} else if (position == 2) {
			showShortToast("增强");
			OcrService.filter(bitmap, this, activity);
		} else if (position == 3) {
			showShortToast("增强并锐化");
			OcrService.filterAndGradient(bitmap, this, activity);
		} else if (position == 4) {
			showShortToast("灰度模式");
			OcrService.grayModel(bitmap, this, activity);
		} else if (position == 5) {
			showShortToast("黑白文档");
			OcrService.threshold(bitmap, this, activity);
		}
	}

	private void syncSpaceWindow() {
		colorWindow.changeColorSpace();
	}

	private void doClickFinishView() {
		activity.finish();
	}

	private void doClickSetView() {
		colorWindow.showAsTop(findViewById(R.id.layout_bottom));
	}

	private void doClickRotationLeftView() {
		rotationView(-90);
		syncGuideView();
	}

	private void doClickRotationRightView() {
		rotationView(90);
		syncGuideView();
	}

	public void rotationView(int degree) {
		boolean clockwise = degree > 0;
		switch (direction) {
		case DIRECTION_TOP:
			bitmap = clockwise ? bitmapRight : bitmapLeft;
			direction = clockwise ? DIRECTION_RIGHT : DIRECTION_LEFT;
			break;
		case DIRECTION_BOTTOM:
			bitmap = clockwise ? bitmapLeft : bitmapRight;
			direction = clockwise ? DIRECTION_LEFT : DIRECTION_RIGHT;
			break;
		case DIRECTION_LEFT:
			bitmap = clockwise ? bitmapTop : bitmapBottom;
			direction = clockwise ? DIRECTION_TOP : DIRECTION_BOTTOM;
			break;
		case DIRECTION_RIGHT:
			bitmap = clockwise ? bitmapBottom : bitmapTop;
			direction = clockwise ? DIRECTION_BOTTOM : DIRECTION_TOP;
			break;
		}
	}

	private void doClickOKView() {
		activity.showToast("正在保存图片");
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scale = (float) WIDTH_DEST / width;
		iv_src.zoomTo(scale, width >> 1, height >> 1);
		Bitmap bitmapCurrent = BitmapUtils.getBitmapFromView(iv_src);
		BitmapCompressor.copyDataToFileByPng(activity, bitmapCurrent, "ocr", "deskew");
		activity.showToast("保存图片成功");
	}

	private void initRotationBitmap() {
		int maxWidth = ScreenUtils.getScreenWidth(activity);
		float scale = 1;
		float preScale = (float) maxWidth / bitmap.getHeight();
		scale = preScale < 1 ? preScale : scale;
		bitmapTop = BitmapUtils.rotationBitmap(bitmap, 0);
		bitmapLeft = BitmapUtils.rotationBitmap(bitmap, -90, scale);
		bitmapRight = BitmapUtils.rotationBitmap(bitmap, 90, scale);
		bitmapBottom = BitmapUtils.rotationBitmap(bitmap, 180);
	}

	public void onMatrixChanged(RectF rect) {
		int width = (int) rect.width();
		int height = (int) rect.height();
		tv_size.setText(width + " × " + height + "px");
	}

}
