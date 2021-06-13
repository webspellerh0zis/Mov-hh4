package qlsl.androiddesign.manager;

import java.io.File;
import java.io.FileInputStream;

import com.qlsl.androiddesign.appname.R;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import qlsl.androiddesign.application.SoftwareApplication;

/**
 * 对AudioView的播放处理工具类<br/>
 * 对控件进行一次性修改,必须使用非缓存的AdapterView<br/>
 * 播放完成后,音频时长控件不重置<br/>
 * 自动判断播放或停止<br/>
 */
public class AudioViewManager implements OnPreparedListener, OnErrorListener,
		OnCompletionListener {

	private MediaPlayer player;

	private View audioView;

	private ImageView audio_play;

	private TextView audio_time;

	private String audioUrl;

	private Boolean isRelease;

	public AudioViewManager() {
		player = new MediaPlayer();
		player.setOnPreparedListener(this);
		player.setOnErrorListener(this);
		player.setOnCompletionListener(this);
	}

	/**
	 * 播放或停止,自动判断<br/>
	 */
	public void play(View audioView, String audioUrl) {
		if (this.audioView == audioView) {
			if (isPlaying()) {
				stop();
			} else {
				play();
			}
		} else {
			if (isPlaying()) {
				stop();
			}
			this.audioView = audioView;
			audio_play = (ImageView) audioView.findViewById(R.id.audio_play);
			audio_time = (TextView) audioView.findViewById(R.id.audio_time);
			this.audioUrl = audioUrl;
			play();
		}

	}

	/**
	 * 播放<br/>
	 */
	@SuppressWarnings("resource")
	private void play() {
		try {
			startAnimation();
			player.reset();
			if (audioUrl.contains("http:")) {
				player.setDataSource(audioUrl);
			} else {
				File file = new File(audioUrl);
				FileInputStream fis = new FileInputStream(file);
				player.setDataSource(fis.getFD());
			}
			player.prepare();
			player.start();
			isRelease = false;
		} catch (Exception e) {
			e.printStackTrace();
			showErrorToast();
			stop();
		}
	}

	/**
	 * 停止<br/>
	 */
	private void stop() {
		try {
			player.stop();
			stopAnimation();
			isRelease = true;
		} catch (Exception e) {
			showErrorToast();
		}
	}

	/**
	 * 停止播放<br/>
	 */
	public void onStop() {
		if (isPlaying()) {
			stop();
		}
	}

	/**
	 * 获取播放状态<br/>
	 */
	private boolean isPlaying() {
		if (isRelease == null || isRelease) {
			return false;
		}
		return player.isPlaying();
	}

	/**
	 * 播放动画<br/>
	 */
	private void startAnimation() {
		AnimationDrawable animation = (AnimationDrawable) audio_play
				.getBackground();
		animation.start();
	}

	/**
	 * 停止播放动画<br/>
	 */
	private void stopAnimation() {
		if (audio_play != null) {
			AnimationDrawable animation = (AnimationDrawable) audio_play
					.getBackground();
			animation.stop();
			animation.selectDrawable(0);
		}
	}

	public void onCompletion(MediaPlayer player) {
		// player.release();
		stopAnimation();
		isRelease = true;
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		showErrorToast();
		stop();
		return false;
	}

	public void onPrepared(MediaPlayer player) {
		int seconds = player.getDuration() / 1000;
		int min = seconds / 60;
		int seconds_extra = seconds % 60;
		String time = min + "\'" + seconds_extra + "\"";
		if (seconds_extra == 0) {
			time = min + "\'";
		} else if (min == 0) {
			time = seconds + "\"";
		}
		audio_time.setText(time);
	}

	private void showErrorToast() {
		Toast.makeText(SoftwareApplication.getInstance(), "播放失败",
				Toast.LENGTH_LONG).show();
	}
}
