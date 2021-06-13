package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import qlsl.androiddesign.activity.commonactivity.ToolColorActivity;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.http.service.commonservice.ToolColorService;
import qlsl.androiddesign.util.commonutil.ColorUtils;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具调色板页<br/>
 */
public class ToolColorView extends FunctionView<ToolColorActivity> implements
		OnSeekBarChangeListener {

	private final String[] types = new String[] { "统一文本颜色", "统一标题背景", "统一内容背景" };

	private String[] values = new String[3];

	private ToggleButton tbtn_open_art, tbtn_show_single;

	private TextView tv_type, tv_color, tv_size;

	private View view_color;

	private SeekBar sb_alpha, sb_red, sb_green, sb_blue, sb_sleep;

	private ToolColor toolColor;

	private byte type;

	public ToolColorView(ToolColorActivity activity) {
		super(activity);
		setContentView(R.layout.activity_tool_color);
	}

	protected void initView(View view) {
		setTitle("文本设置");
		setRightButtonText("保存");
		showRightButton();
		addBorderView("重置");
		tbtn_open_art = (ToggleButton) view.findViewById(R.id.tbtn_open_art);
		tbtn_show_single = (ToggleButton) view
				.findViewById(R.id.tbtn_show_single);
		tv_type = (TextView) view.findViewById(R.id.tv_type);
		tv_color = (TextView) view.findViewById(R.id.tv_color);
		tv_size = (TextView) view.findViewById(R.id.tv_size);
		view_color = view.findViewById(R.id.view_color);
		sb_alpha = (SeekBar) view.findViewById(R.id.sb_alpha);
		sb_red = (SeekBar) view.findViewById(R.id.sb_red);
		sb_green = (SeekBar) view.findViewById(R.id.sb_green);
		sb_blue = (SeekBar) view.findViewById(R.id.sb_blue);
		sb_sleep = (SeekBar) view.findViewById(R.id.sb_sleep);
	}

	protected void initData() {
		ToolColorService.queryToolColor(this, activity);
	}

	protected void initListener() {
		sb_alpha.setOnSeekBarChangeListener(this);
		sb_red.setOnSeekBarChangeListener(this);
		sb_green.setOnSeekBarChangeListener(this);
		sb_blue.setOnSeekBarChangeListener(this);
		sb_sleep.setOnSeekBarChangeListener(this);
	}

	public <T> void showData(T... t) {
		if (t[0] instanceof ToolColor) {
			toolColor = (ToolColor) t[0];

			boolean isOpenArt = toolColor.getIsOpenArt();
			boolean isShowSingle = toolColor.getIsShowSingle();
			String textColor = toolColor.getTextColor();
			int size = toolColor.getTextSize();
			int sleep = toolColor.getSleep();

			int color = Color.parseColor(textColor);
			tbtn_open_art.setChecked(isOpenArt);
			tbtn_show_single.setChecked(isShowSingle);
			sb_alpha.setProgress(Color.alpha(color));
			sb_red.setProgress(Color.red(color));
			sb_green.setProgress(Color.green(color));
			sb_blue.setProgress(Color.blue(color));
			sb_sleep.setProgress(sleep);
			tv_size.setText(size + "");
			tv_size.setTextSize(size);
			tv_color.setText(textColor);
			view_color.setBackgroundColor(color);

			values[0] = textColor;
			values[1] = toolColor.getTitleBarColor();
			values[2] = toolColor.getContentBarColor();

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
		case R.id.btn_minus:
			doClickMinusButton();
			break;
		case R.id.btn_plus:
			doClickPlusButton();
			break;
		case R.id.iv_next:
			doClickNextView();
			break;
		}
	}

	private void doClickRightButton() {
		toolColor.setIsOpenArt(tbtn_open_art.isChecked());
		toolColor.setIsShowSingle(tbtn_show_single.isChecked());
		toolColor.setTextColor(values[0]);
		toolColor.setTitleBarColor(values[1]);
		toolColor.setContentBarColor(values[2]);
		toolColor.setTextSize(Integer.parseInt(tv_size.getText().toString()));
		toolColor.setSleep(sb_sleep.getProgress());
		ToolColorService.updateToolColor(toolColor, this, activity);
	}

	private void doClickBorderView() {
		DialogUtil.showDialog(this, "重置", "确定恢复为初始值？");
	}

	private void doClickMinusButton() {
		int size = Integer.parseInt(tv_size.getText().toString());
		if (size > 14) {
			size -= 1;
			tv_size.setText(size + "");
			tv_size.setTextSize(size);
		}
	}

	private void doClickPlusButton() {
		int size = Integer.parseInt(tv_size.getText().toString());
		if (size < 20) {
			size += 1;
			tv_size.setText(size + "");
			tv_size.setTextSize(size);
		}
	}

	private void doClickNextView() {
		if (toolColor == null) {
			showToast("正在查询数据，请稍后");
			return;
		}
		String nextText = null;
		String nextColor = null;
		if (type == 0) {
			nextText = types[1];
			nextColor = values[1];
			type = 1;
		} else if (type == 1) {
			nextText = types[2];
			nextColor = values[2];
			type = 2;
		} else if (type == 2) {
			nextText = types[0];
			nextColor = values[0];
			type = 0;
		}
		int color = Color.parseColor(nextColor);
		sb_alpha.setProgress(Color.alpha(color));
		sb_red.setProgress(Color.red(color));
		sb_green.setProgress(Color.green(color));
		sb_blue.setProgress(Color.blue(color));
		tv_type.setText(nextText);
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2))
				.setText(progress + "");
		if (seekBar != sb_sleep) {
			int color = Color.argb(sb_alpha.getProgress(),
					sb_red.getProgress(), sb_green.getProgress(),
					sb_blue.getProgress());
			String current_color = ColorUtils.toHexString(color);
			tv_color.setText(current_color);
			view_color.setBackgroundColor(color);
			values[type] = current_color;
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			ToolColorService.resetToolColor(toolColor, this, activity);
		}
	}

}
