package qlsl.androiddesign.util.singleton;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.db.othertable.ToolSpeech;

public class ToolSpeechUtils {

	private static SpeechSynthesizer mTts;

	private static ToolSpeech toolSpeech;

	/**
	 * 单例获取SpeechSynthesizer<br/>
	 * 后期可配置是否开启语音以及配置相关参数<br/>
	 */
	private static SpeechSynthesizer getSpeechSynthesizer() {
		if (mTts == null) {
			int speaker = 10;
			int stream = 3;
			int speed = 1;
			int pitch = 55;
			int volume = 60;

			if (toolSpeech != null) {
				speaker = toolSpeech.getSpeaker();
				stream = toolSpeech.getStream();
				speed = toolSpeech.getSpeed();
				pitch = toolSpeech.getPitch();
				volume = toolSpeech.getVolume();
			}

			String[] speakers = SoftwareApplication.getInstance()
					.getResources().getStringArray(R.array.speech_speaker);
			String[] streams = SoftwareApplication.getInstance().getResources()
					.getStringArray(R.array.speech_stream);

			mTts = SpeechSynthesizer.createSynthesizer(
					SoftwareApplication.getInstance(), null);
			// 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
			mTts.setParameter(SpeechConstant.VOICE_NAME, speakers[speaker]);// 设置发音人(xiaoqi,;xiaomei,港语；xiaorong,粤语)
			mTts.setParameter(SpeechConstant.STREAM_TYPE, streams[stream]);// 流类型
			mTts.setParameter(SpeechConstant.SPEED, speed + "");// 设置语速
			mTts.setParameter(SpeechConstant.VOLUME, volume + "");// 设置音量，范围0~100
			mTts.setParameter(SpeechConstant.ENGINE_TYPE,
					SpeechConstant.TYPE_CLOUD); // 设置云端
			mTts.setParameter(SpeechConstant.PITCH, pitch + "");
			// 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
			// 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
			// 如果不需要保存合成音频，注释该行代码
			// mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
			// "./sdcard/iflytek.pcm");
			// 3.开始合成
			// 合成监听器
		}
		return mTts;
	}

	/**
	 * 静态播放<br/>
	 * 非语音设置中的播放<br/>
	 * type:[0,1]对应[自动播放,点击播放]<br/>
	 */
	public static void startSpeaking(String text, int type) {
		boolean isAutoPlay = true;
		boolean isClickPlay = true;
		if (toolSpeech != null) {
			isAutoPlay = toolSpeech.getIsAutoPlay();
			isClickPlay = toolSpeech.getIsClickPlay();
		}
		if (type == 0) {
			if (isAutoPlay) {
				getSpeechSynthesizer().startSpeaking(text, mSynListener);
			}
		} else if (type == 1) {
			if (isClickPlay) {
				getSpeechSynthesizer().startSpeaking(text, mSynListener);
			}
		}
	}

	/**
	 * 动态播放<br/>
	 * 语音设置中的播放<br/>
	 */
	public static void startSpeaking(String text, ToolSpeech toolSpeech) {
		int speaker = toolSpeech.getSpeaker();
		int stream = toolSpeech.getStream();
		int speed = toolSpeech.getSpeed();
		int pitch = toolSpeech.getPitch();
		int volume = toolSpeech.getVolume();

		String[] speakers = SoftwareApplication.getInstance().getResources()
				.getStringArray(R.array.speech_speaker);
		String[] streams = SoftwareApplication.getInstance().getResources()
				.getStringArray(R.array.speech_stream);

		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(
				SoftwareApplication.getInstance(), null);
		// 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, speakers[speaker]);// 设置发音人(xiaoqi,;xiaomei,港语；xiaorong,粤语)
		mTts.setParameter(SpeechConstant.STREAM_TYPE, streams[stream]);// 流类型
		mTts.setParameter(SpeechConstant.SPEED, speed + "");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, volume + "");// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
		mTts.setParameter(SpeechConstant.PITCH, pitch + "");
		// 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
		// 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
		// 如果不需要保存合成音频，注释该行代码
		// mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
		// "./sdcard/iflytek.pcm");
		// 3.开始合成
		mTts.startSpeaking(text, mSynListener);
		// 合成监听器
	}

	private static SynthesizerListener mSynListener = new SynthesizerListener() {
		// 会话结束回调接口，没有错误时，error为null
		public void onCompleted(SpeechError error) {

		}

		// 缓冲进度回调
		// percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
		}

		// 开始播放
		public void onSpeakBegin() {
		}

		// 暂停播放
		public void onSpeakPaused() {
		}

		// 播放进度回调
		// percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}

		// 恢复播放回调接口
		public void onSpeakResumed() {
		}

		// 会话事件回调接口
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}
	};

	public static ToolSpeech get() {
		return toolSpeech;
	}

	public static void set(ToolSpeech toolSpeech) {
		ToolSpeechUtils.toolSpeech = toolSpeech;
		mTts = null;
	}

}
