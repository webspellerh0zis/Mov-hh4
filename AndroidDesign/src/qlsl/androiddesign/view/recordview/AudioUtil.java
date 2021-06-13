package qlsl.androiddesign.view.recordview;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

public class AudioUtil {

	private String mAudioPath;
	private boolean mIsRecording;
	private boolean mIsPlaying;
	private MediaRecorder mRecorder;
	private MediaPlayer mPlayer;

	/**
	 * 设置音频路径<br/>
	 */
	public void setAudioPath(String path) {
		this.mAudioPath = path;
	}

	/**
	 * 开始录音<br/>
	 */
	public void recordAudio() throws IllegalStateException, IOException {
		if (mAudioPath != null && !mAudioPath.equals("")) {
			initRecorder();
			mRecorder.prepare();
			mRecorder.start();
		}
	}

	/**
	 * 获取音量:[0,6]<br/>
	 */
	public int getVolumn() {
		int volumn = 0;
		if (mRecorder != null && mIsRecording) {
			volumn = mRecorder.getMaxAmplitude();
			if (volumn != 0) {
				volumn = (int) (10 * Math.log(volumn) / Math.log(10)) / 7;
			}
		}
		return volumn;
	}

	/**
	 * 初始化录音器<br/>
	 */
	@SuppressWarnings("deprecation")
	private void initRecorder() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile(mAudioPath);
		mIsRecording = true;
	}

	/**
	 * 停止录音<br/>
	 */
	public void stopRecord() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			mIsRecording = false;
		}
	}

	/**
	 * 播放录音<br/>
	 */
	public void startPlay() {
		if (mAudioPath != null && !mAudioPath.equals("")) {
			mPlayer = new MediaPlayer();
			try {
				mPlayer.setDataSource(mAudioPath);
				mPlayer.prepare();
				mPlayer.start();
				mIsPlaying = true;
				mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					public void onCompletion(MediaPlayer mp) {
						mp.release();
						mPlayer = null;
						mIsPlaying = false;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取播放状态<br/>
	 */
	public boolean isPlaying() {
		return mIsPlaying;
	}

}
