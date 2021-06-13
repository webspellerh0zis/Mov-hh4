package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import qlsl.androiddesign.activity.commonactivity.ToolSpeechActivity;
import qlsl.androiddesign.db.othertable.ToolSpeech;
import qlsl.androiddesign.http.service.commonservice.ToolSpeechService;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具语音设置<br/>
 */
public class ToolSpeechView extends FunctionView<ToolSpeechActivity> implements
		OnSeekBarChangeListener {

	private ToggleButton tbtn_autoplay, tbtn_clickplay;

	private Spinner sp_speaker, sp_stream;

	private EditText et_content;

	private SeekBar sb_speed, sb_pitch, sb_volume;

	private ToolSpeech toolSpeech;

	public ToolSpeechView(ToolSpeechActivity activity) {
		super(activity);
		setContentView(R.layout.activity_tool_speech);
	}

	protected void initView(View view) {
		setTitle("语音设置");
		setRightButtonText("保存");
		showRightButton();
		addBorderView("重置");
		setContentBarBackgroundColor(activity.getResources().getColor(
				R.color.light_white));
		tbtn_autoplay = (ToggleButton) view.findViewById(R.id.tbtn_autoplay);
		tbtn_clickplay = (ToggleButton) view.findViewById(R.id.tbtn_clickplay);
		sp_speaker = (Spinner) view.findViewById(R.id.sp_speaker);
		sp_stream = (Spinner) view.findViewById(R.id.sp_stream);
		et_content = (EditText) view.findViewById(R.id.et_content);
		sb_speed = (SeekBar) view.findViewById(R.id.sb_speed);
		sb_pitch = (SeekBar) view.findViewById(R.id.sb_pitch);
		sb_volume = (SeekBar) view.findViewById(R.id.sb_volume);
	}

	protected void initData() {
		ToolSpeechService.queryToolSpeech(this, activity);
	}

	protected void initListener() {
		sb_speed.setOnSeekBarChangeListener(this);
		sb_pitch.setOnSeekBarChangeListener(this);
		sb_volume.setOnSeekBarChangeListener(this);
	}

	public <T> void showData(T... t) {
		if (t[0] instanceof ToolSpeech) {
			toolSpeech = (ToolSpeech) t[0];

			boolean isAutoplay = toolSpeech.getIsAutoPlay();
			boolean isClickplay = toolSpeech.getIsClickPlay();
			int speaker = toolSpeech.getSpeaker();
			int stream = toolSpeech.getStream();
			int speed = toolSpeech.getSpeed();
			int pitch = toolSpeech.getPitch();
			int volume = toolSpeech.getVolume();

			tbtn_autoplay.setChecked(isAutoplay);
			tbtn_clickplay.setChecked(isClickplay);
			sp_speaker.setSelection(speaker);
			sp_stream.setSelection(stream);
			sb_speed.setProgress(speed);
			sb_pitch.setProgress(pitch);
			sb_volume.setProgress(volume);
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_right:
			doClickRightButton();
			break;
		case R.id.iv_border:
			doClickBorderView();
			break;
		case R.id.btn_random:
			doClickRandomButton();
			break;
		case R.id.btn_read:
			doClickReadButton();
			break;
		}
	}

	private void doClickRightButton() {
		toolSpeech.setIsAutoPlay(tbtn_autoplay.isChecked());
		toolSpeech.setIsClickPlay(tbtn_clickplay.isChecked());
		toolSpeech.setSpeaker(sp_speaker.getSelectedItemPosition());
		toolSpeech.setStream(sp_stream.getSelectedItemPosition());
		toolSpeech.setSpeed(sb_speed.getProgress());
		toolSpeech.setPitch(sb_pitch.getProgress());
		toolSpeech.setVolume(sb_volume.getProgress());
		ToolSpeechService.updateToolSpeech(toolSpeech, this, activity);
	}

	private void doClickBorderView() {
		DialogUtil.showDialog(this, "重置", "确定恢复为初始值？");
	}

	private void doClickRandomButton() {

	}

	private void doClickReadButton() {
		ToolSpeech toolSpeech = new ToolSpeech();
		toolSpeech.setSpeaker(sp_speaker.getSelectedItemPosition());
		toolSpeech.setStream(sp_stream.getSelectedItemPosition());
		toolSpeech.setSpeed(sb_speed.getProgress());
		toolSpeech.setPitch(sb_pitch.getProgress());
		toolSpeech.setVolume(sb_volume.getProgress());
		ToolSpeechUtils.startSpeaking(et_content.getText().toString(),
				toolSpeech);
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2))
				.setText(progress + "");
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			ToolSpeechService.resetToolSpeech(toolSpeech, this, activity);
		}
	}
}
