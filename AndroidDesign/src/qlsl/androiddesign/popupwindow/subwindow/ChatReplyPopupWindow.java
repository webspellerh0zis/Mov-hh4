package qlsl.androiddesign.popupwindow.subwindow;

import java.io.File;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.http.xutils.Protocol.FileType;
import qlsl.androiddesign.listener.OnTextChangeListener;
import qlsl.androiddesign.listener.OnUploadFileListener;
import qlsl.androiddesign.manager.TakePhotoManager;
import qlsl.androiddesign.manager.TakePhotoManager.OnFinishedCropListener;
import qlsl.androiddesign.popupwindow.basewindow.PopupWindow;
import qlsl.androiddesign.util.commonutil.FileNameUtils;
import qlsl.androiddesign.view.recordview.RecordButton;
import qlsl.androiddesign.view.recordview.RecordButton.OnFinishedRecordListener;

@SuppressLint("ViewConstructor")
public class ChatReplyPopupWindow extends PopupWindow
		implements OnClickListener, OnDismissListener, OnFinishedCropListener, OnFinishedRecordListener {

	private SelectPhotoPopupWindow photoWindow;

	private TakePhotoManager photoManager;

	private ImageView reply_speak, reply_photo;

	private EditText reply_content;

	private RecordButton reply_record;

	private Button reply_send;

	private View.OnClickListener onClickSendListener;

	private OnUploadSuccessListener onSuccesslistener;

	public ChatReplyPopupWindow(BaseActivity activity, View.OnClickListener onClickSendListener,
			OnUploadSuccessListener onSuccesslistener) {
		super(activity);
		this.onClickSendListener = onClickSendListener;
		this.onSuccesslistener = onSuccesslistener;
		initView();
		setPopupWindowAttribute();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = inflater.inflate(R.layout.window_chat_reply, null);
		setContentView(rootView);
		reply_speak = (ImageView) rootView.findViewById(R.id.reply_speak);
		reply_content = (EditText) rootView.findViewById(R.id.reply_content);
		reply_record = (RecordButton) rootView.findViewById(R.id.reply_record);
		reply_photo = (ImageView) rootView.findViewById(R.id.reply_photo);
		reply_send = (Button) rootView.findViewById(R.id.reply_send);

		photoWindow = new SelectPhotoPopupWindow(activity, this);
		photoWindow.hideLookButton();
		photoManager = new TakePhotoManager(activity, this);
		photoManager.setOutputParams(7, 8, 320, 366);

		reply_speak.setOnClickListener(this);
		reply_content.addTextChangedListener(onTextChangeListener);
		reply_record.setOnFinishedRecordListener(this);
		reply_photo.setOnClickListener(this);
		reply_send.setOnClickListener(this);
		setOnDismissListener(this);

	}

	private void setPopupWindowAttribute() {
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setAnimationStyle(R.style.AnimBottom);
		ColorDrawable colorDrawable = new ColorDrawable(0x7F7F7F7F);
		setBackgroundDrawable(colorDrawable);
		setOutsideTouchable(true);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.reply_speak:
			doClickSpeakView();
			break;
		case R.id.reply_photo:
			doClickPhotoView();
			showAsBottom(activity.findViewById(R.id.common_root));
			break;
		case R.id.reply_send:
			onClickSendListener.onClick(view);
			showAsBottom(activity.findViewById(R.id.common_root));
			break;
		case R.id.btn_take_photo:
			doClickTakePhotoButton();
			break;
		case R.id.btn_pick_photo:
			doClickPickPhotoButton();
			break;
		}
	}

	private void doClickSpeakView() {
		if (reply_content.getVisibility() == View.VISIBLE) {
			reply_content.setVisibility(View.GONE);
			reply_record.setVisibility(View.VISIBLE);
			reply_speak.setImageResource(R.drawable.icon_keyboard_btn);
		} else {
			reply_content.setVisibility(View.VISIBLE);
			reply_record.setVisibility(View.GONE);
			reply_speak.setImageResource(R.drawable.icon_audio_msg_btn);
		}
	}

	private void doClickPhotoView() {
		photoWindow.showAsBottom(activity.findViewById(R.id.common_root));
		photoManager.setPhotoName(FileNameUtils.getAccountSecondDir(), FileNameUtils.getAccountPrefixName(activity));
	}

	private void doClickTakePhotoButton() {
		photoManager.takePhoto();
	}

	private void doClickPickPhotoButton() {
		photoManager.selectPhoto();
	}

	public void hideSpeakView() {
		reply_speak.setVisibility(View.GONE);
	}

	public void hidePhotoView() {
		reply_photo.setVisibility(View.GONE);
	}

	public void setHint(String hint) {
		reply_content.setHint(hint);
	}

	public boolean isReplyType() {
		return reply_content.getHint().toString().contains("回复");
	}

	public String getText() {
		return reply_content.getText().toString();
	}

	public void setRecordName(String secondDir, String prefixName) {
		reply_record.setRecordName(secondDir, prefixName);
	}

	public void onDismiss() {
		reply_content.setHint("请输入");
		reply_content.setText(null);
		reply_content.setVisibility(View.VISIBLE);
		reply_record.setVisibility(View.GONE);
		reply_speak.setImageResource(R.drawable.icon_audio_msg_btn);
	}

	private OnTextChangeListener onTextChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(android.text.Editable s) {
			if (s.length() == 0) {
				reply_photo.setVisibility(View.VISIBLE);
				reply_send.setVisibility(View.GONE);
			} else {
				reply_photo.setVisibility(View.GONE);
				reply_send.setVisibility(View.VISIBLE);
			}
		};

	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		photoManager.onActivityResult(requestCode, resultCode, data);
	}

	public void onFinishedCrop(File file, Bitmap bitmap) {
		dismiss();
		new HttpProtocol().uploadFile(file, FileType.PICTURE, listener);
	}

	public void onFinishedRecord(File file, int recordTime) {
		dismiss();
		new HttpProtocol().uploadFile(file, FileType.AUDIO, listener);
	}

	private OnUploadFileListener listener = new OnUploadFileListener() {

		public void onSuccess(List<String> urls, FileType type) {
			onSuccesslistener.onSuccess(urls, type);
		}
	};

	public interface OnUploadSuccessListener {
		public void onSuccess(List<String> urls, FileType type);
	}

}
