package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;

import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.qlsl.androiddesign.appname.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import qlsl.androiddesign.activity.commonactivity.OcrCameraActivity;
import qlsl.androiddesign.activity.commonactivity.OcrCornerActivity;
import qlsl.androiddesign.activity.commonactivity.OcrMainActivity;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 扫描主页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class OcrMainView extends FunctionView<OcrMainActivity> {

	public OcrMainView(OcrMainActivity activity) {
		super(activity);
		setContentView(R.layout.activity_ocr_main);
	}

	protected void initView(View view) {
		setTitle("扫描");
		setContentBarBackgroundColor(Color.parseColor("#1e1e1e"));
		setTitleBarBackgroundColor(Color.parseColor("#1e1e1e"));
	}

	protected void initData() {

	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_take_photo:
			doClickTakePhotoView();
			break;
		case R.id.btn_select_photo:
			doClickSelectPhotoView();
			break;
		}
	}

	private void doClickTakePhotoView() {
		startActivity(OcrCameraActivity.class);
	}

	private void doClickSelectPhotoView() {
		Intent intent = new Intent(activity, PhotoSelectorActivity.class);
		intent.putExtra(PhotoSelectorActivity.KEY_MAX, 1);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivityForResult(intent, IntentCodeConstant.REQUEST_CODE_PHOTO_PICKED);
	}

	@SuppressWarnings("unchecked")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentCodeConstant.REQUEST_CODE_PHOTO_PICKED) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					showToast("无法加载图片");
					return;
				}
				ArrayList<PhotoModel> selectPhotos = (ArrayList<PhotoModel>) bundle.getSerializable("photos");
				String picturePath = selectPhotos.get(0).getOriginalPath();
				Intent intent = new Intent(activity, OcrCornerActivity.class);
				intent.putExtra("path", picturePath);
				startActivity(intent);
			}
		}
	}

}
