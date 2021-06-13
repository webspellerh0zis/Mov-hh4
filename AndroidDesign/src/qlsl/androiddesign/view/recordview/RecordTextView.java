package qlsl.androiddesign.view.recordview;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import qlsl.androiddesign.view.commonview.TextView;

/**
 * 长按录音按钮<br/>
 * 
 */
public class RecordTextView extends TextView implements OnClickListener {

	private static final String FIRST_DIR = "audio/";
	private static final String SUFFIX_NAME = ".amr";
	private String secondDir;
	private String prefixName;

	private static final int MIN_INTERVAL_TIME = 2 * 1000;
	private static final int MAX_INTERVAL_TIME = 5 * 60 * 1000;

	private String mFileName;
	private OnFinishedRecordListener mFinishedListerer;
	private long mStartTime;

	private AudioUtil mAudioUtil;

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss");

	private Timer recordTimer;

	private TimerTask recordTimerTask;

	public RecordTextView(Context context) {
		super(context);
		init();
	}

	public RecordTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RecordTextView(Context context, AttributeSet attrs) {
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
		mAudioUtil = new AudioUtil();
		setOnClickListener(this);
	}

	public void onClick(View view) {
		try {
			if (isActivated()) {
				setActivated(false);
				setVisibility(View.GONE);
				finishRecord();
			} else {
				setActivated(true);
				startRecording();
				startTimer();
			}
		} catch (Exception e) {
			e.printStackTrace();
			setActivated(false);
			setVisibility(View.GONE);
			Toast.makeText(getContext(), "录音失败", Toast.LENGTH_SHORT).show();
		}
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
	}

	private void stopRecording() {
		if (mAudioUtil != null) {
			mAudioUtil.stopRecord();
		}
	}

	private void finishRecord() {
		stopRecording();

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

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				long extra = System.currentTimeMillis() - mStartTime;
				Date date_extra = new Date(extra);
				Date date_max = new Date(MAX_INTERVAL_TIME);
				String time_extra = format.format(date_extra);
				String time_max = format.format(date_max);
				setText(time_extra + "/" + time_max);

				if (extra == MAX_INTERVAL_TIME) {
					setActivated(false);
					setVisibility(View.GONE);
					finishRecord();
				}
			}
		}
	};

	private void startTimer() {
		recordTimer = new Timer();
		recordTimerTask = new TimerTask() {

			public void run() {
				handler.sendEmptyMessage(1);
			}
		};
		mStartTime = System.currentTimeMillis();
		recordTimer.schedule(recordTimerTask, 1000, 1000);
	}

	private void stopTimer() {
		if (recordTimer != null) {
			recordTimer.cancel();
			recordTimer = null;
		}
		if (recordTimerTask != null) {
			recordTimerTask.cancel();
			recordTimerTask = null;
		}
	}

	protected void onVisibilityChanged(View changedView, int visibility) {
		if (visibility == View.GONE) {
			stopTimer();
		} else if (visibility == View.VISIBLE) {
			setText(null);
		}
		super.onVisibilityChanged(changedView, visibility);
	}

	public interface OnFinishedRecordListener {
		public void onFinishedRecord(File file, int recordTime);
	}
}
