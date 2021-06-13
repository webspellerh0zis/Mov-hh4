package qlsl.androiddesign.service.subservice;

import java.io.IOException;
import java.text.DecimalFormat;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import qlsl.androiddesign.constant.AppConstant;
import qlsl.androiddesign.service.baseservice.BaseService;

/**
 * 
 * 音频播放服务<br/>
 * 启动悬浮窗后台播放音频<br/>
 * 需要通过Intent传递String类型的名为path的对象进来<br/>
 */
public class MediaFloatService extends BaseService implements
		OnCompletionListener, OnSeekBarChangeListener, OnPreparedListener,
		OnClickListener, OnErrorListener {

	private WindowManager wm;

	private WindowManager.LayoutParams wmParams;

	private MediaPlayer mediaPlayer;

	private View view;

	private TextView tv_time;

	private SeekBar seekBar;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mediaPlayer.isPlaying()) {
				int progress = mediaPlayer.getCurrentPosition();
				seekBar.setProgress(progress);
				tv_time.setText(getTime(progress));
				sendEmptyMessageDelayed(1, 1000);
			}
		}
	};

	public void onCreate() {
		super.onCreate();
		initManager();
		initView();
		initListener();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			String path = intent.getStringExtra("path");
			if (TextUtils.isEmpty(path)) {
				path = "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3";
				startPlay(path);
				// Toast.makeText(getApplicationContext(), "暂无音频",
				// Toast.LENGTH_LONG).show();
			} else {
				startPlay(AppConstant.RES_URL + path);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void initManager() {
		wm = (WindowManager) getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		wmParams = new WindowManager.LayoutParams();
		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mediaPlayer = new MediaPlayer();
	}

	private void initView() {
		view = createFloatView(wm, wmParams, Gravity.BOTTOM,
				R.layout.window_float_media);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		seekBar = (SeekBar) view.findViewById(R.id.seekBar);
	}

	private void initListener() {
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		seekBar.setOnSeekBarChangeListener(this);
		View btn_play = view.findViewById(R.id.btn_play);
		View btn_stop = view.findViewById(R.id.btn_stop);
		btn_play.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
	}

	private void startPlay(String path) {
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepareAsync();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
		}
		if (wm != null) {
			wm.removeView(view);
		}
		handler.removeMessages(1);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	private static String getTime(int lengh) {
		lengh = lengh / 1000;
		DecimalFormat format = new DecimalFormat("00");
		int hour = lengh / 60 / 60;
		int min = lengh / 60 % 60;
		int sec = lengh % 60;
		String time = format.format(hour) + ":" + format.format(min) + ":"
				+ format.format(sec);
		return time;
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_play:
			doClickPlayButton(view);
			break;
		case R.id.btn_stop:
			doClickStopButton();
			break;
		}
	}

	private void doClickPlayButton(View view) {
		if (mediaPlayer.isPlaying()) {
			view.setActivated(true);
			mediaPlayer.pause();
		} else {
			view.setActivated(false);
			mediaPlayer.start();
			handler.sendEmptyMessage(1);
		}
	}

	private void doClickStopButton() {
		stopService(new Intent(MediaFloatService.this, MediaFloatService.class));
	}

	public void onCompletion(MediaPlayer mp) {
		stopSelf();
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (fromUser) {
			mediaPlayer.seekTo(progress);
			handler.sendEmptyMessage(1);
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
		int duration = mediaPlayer.getDuration();
		seekBar.setMax(duration);
		seekBar.setProgress(0);
		tv_time.setText("00:00:00");
		handler.sendEmptyMessage(1);
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		Toast.makeText(getApplicationContext(), "播放失败", Toast.LENGTH_LONG)
				.show();
		return false;
	}

}
