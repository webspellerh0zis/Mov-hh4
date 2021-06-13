package qlsl.androiddesign.view.recordview;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import qlsl.androiddesign.util.commonutil.DensityUtils;
import qlsl.androiddesign.view.commonview.Button;

/**
 * 长按录音按钮<br/>
 * 
 */
public class RecordButton extends Button {

	private static final String FIRST_DIR = "audio/";
	private static final String SUFFIX_NAME = ".amr";
	private String secondDir;
	private String prefixName;

	private static final int MIN_INTERVAL_TIME = 2000;
	private static final int MAX_INTERVAL_TIME = 5 * 60 * 1000;

	private ViewGroup startView, cancelView;
	private VolumeView volumeView;
	private TextView timeView;
	private boolean mIsCancel = false;

	private String mFileName;
	private OnFinishedRecordListener mFinishedListerer;
	private long mStartTime;
	private Dialog mRecordDialog;

	private AudioUtil mAudioUtil;
	private ObtainDecibelThread mThread;
	private RecordTimer recordTimer;
	private Handler mVolumeHandler;
	private int mYpositon = 0;

	private boolean isTimeFinished;

	public RecordButton(Context context) {
		super(context);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setRecordName(String secondDir, String prefixName) {
		this.secondDir = secondDir;
		this.prefixName = prefixName;
	}

	/**
	 * 根据二级目录，文件前缀生成文件名<br/>
	 * 不用包含下斜杠与分隔符<br/>
	 */
	@SuppressLint("SimpleDateFormat")
	private void setRecordName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(date);
		String basePath = getContext().getFilesDir().getAbsolutePath() + "/"
				+ FIRST_DIR + secondDir;
		File file = new File(basePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		mFileName = basePath + "/" + prefixName + "_" + timestamp + SUFFIX_NAME;
	}

	public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
		mFinishedListerer = listener;
	}

	private void init() {
		mVolumeHandler = new ShowVolumeHandler();
		mAudioUtil = new AudioUtil();
		int[] location = new int[2];
		getLocationOnScreen(location);
		mYpositon = location[1];
	}

	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				initDialogAndStartRecord();
				startView.setVisibility(View.VISIBLE);
				cancelView.setVisibility(View.GONE);
				break;
			case MotionEvent.ACTION_UP:
				stopCountTimer();
				if (isTimeFinished) {
					isTimeFinished = false;
					return true;
				}
				if (!mIsCancel) {
					finishRecord();
				} else {
					cancelRecord();
				}
				mIsCancel = false;
				break;
			case MotionEvent.ACTION_MOVE:
				if (isTimeFinished) {
					return true;
				}
				if (event.getY() < mYpositon) {
					mIsCancel = true;
					cancelView.setVisibility(View.VISIBLE);
					startView.setVisibility(View.GONE);
					stopCountTimer();
				}
				break;
			}
		} catch (Exception e) {
			Toast.makeText(getContext(), "录音失败", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	private View createContentView() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.dialog_record, null);
		startView = (ViewGroup) view.findViewById(R.id.record_start_view);
		volumeView = (VolumeView) view.findViewById(R.id.record_volume_view);
		timeView = (TextView) view.findViewById(R.id.record_time_view);
		cancelView = (ViewGroup) view.findViewById(R.id.record_cancel_view);
		return view;
	}

	private void initDialogAndStartRecord() throws IllegalStateException,
			IOException {
		mStartTime = System.currentTimeMillis();
		if (mRecordDialog == null) {
			mRecordDialog = new Dialog(getContext(), R.style.Dialog);
			mRecordDialog.setContentView(createContentView());
			WindowManager.LayoutParams params = mRecordDialog.getWindow()
					.getAttributes();
			int px = DensityUtils.dip2px(getContext(), 200);
			params.width = px;
			params.height = px;
			mRecordDialog.getWindow().setAttributes(params);
			mRecordDialog.setOnDismissListener(onDismiss);
		}
		startRecording();
		mRecordDialog.show();
	}

	private void startRecording() throws IllegalStateException, IOException {
		if (TextUtils.isEmpty(secondDir) || TextUtils.isEmpty(prefixName)) {
			Toast.makeText(getContext(), "没有指定二级目录与前缀名", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		setRecordName();
		mAudioUtil.setAudioPath(mFileName);
		mAudioUtil.recordAudio();
		mThread = new ObtainDecibelThread();
		mThread.start();
		recordTimer = new RecordTimer(MAX_INTERVAL_TIME, 1000);
		recordTimer.start();
	}

	private void stopRecording() {
		if (mThread != null) {
			mThread.exit();
			mThread = null;
		}
		if (mAudioUtil != null) {
			mAudioUtil.stopRecord();
		}
	}

	private void stopCountTimer() {
		if (recordTimer != null) {
			recordTimer.cancel();
			recordTimer = null;
		}
	}

	private void finishRecord() {
		stopRecording();
		mRecordDialog.dismiss();

		long intervalTime = System.currentTimeMillis() - mStartTime;
		if (intervalTime < MIN_INTERVAL_TIME) {
			Toast.makeText(getContext(), "时间太短！", Toast.LENGTH_SHORT).show();
			File file = new File(mFileName);
			file.delete();
			return;
		}

		if (mFinishedListerer != null) {
			mFinishedListerer.onFinishedRecord(new File(mFileName),
					(int) ((System.currentTimeMillis() - mStartTime) / 1000));
		}
	}

	private void cancelRecord() {
		stopRecording();
		mRecordDialog.dismiss();

		Toast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT).show();
		File file = new File(mFileName);
		file.delete();
	}

	private class ObtainDecibelThread extends Thread {

		private volatile boolean running = true;

		public void exit() {
			running = false;
		}

		public void run() {
			while (running) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (mAudioUtil == null || !running) {
					break;
				}

				int volumn = mAudioUtil.getVolumn();

				if (volumn != 0)
					mVolumeHandler.sendEmptyMessage(volumn);

			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener() {

		public void onDismiss(DialogInterface dialog) {
			stopRecording();
		}
	};

	@SuppressLint("HandlerLeak")
	class ShowVolumeHandler extends Handler {

		public void handleMessage(Message msg) {
			volumeView.setVolumeValue(msg.what);
		}
	}

	private class RecordTimer extends CountDownTimer {

		public RecordTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onTick(long millisUntilFinished) {
			if (millisUntilFinished <= 10000) {
				timeView.setText("还剩" + millisUntilFinished / 1000 + "秒");
			}
		}

		public void onFinish() {
			timeView.setText("手指上滑，取消发送");
			isTimeFinished = true;
			if (!mIsCancel) {
				finishRecord();
			} else {
				cancelRecord();
			}
			mIsCancel = false;
		}

	}

	public interface OnFinishedRecordListener {
		public void onFinishedRecord(File file, int recordTime);
	}

}
