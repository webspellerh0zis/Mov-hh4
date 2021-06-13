package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import qlsl.androiddesign.activity.commonactivity.ToolPagerActivity;
import qlsl.androiddesign.db.othertable.ToolPager;
import qlsl.androiddesign.http.service.commonservice.ToolPagerService;
import qlsl.androiddesign.util.commonutil.DialogUtil;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具分页<br/>
 */
public class ToolPagerView extends FunctionView<ToolPagerActivity> implements
		OnSeekBarChangeListener {

	private final int poor_pager = 15;

	private final int poor_total = 100;

	private ToggleButton tbtn;

	private SeekBar sb_pager, sb_total;

	private ToolPager toolPager;

	public ToolPagerView(ToolPagerActivity activity) {
		super(activity);
		setContentView(R.layout.activity_tool_pager);
	}

	protected void initView(View view) {
		setTitle("分页设置");
		setRightButtonText("保存");
		showRightButton();
		addBorderView("重置");
		tbtn = (ToggleButton) view.findViewById(R.id.tbtn);
		sb_pager = (SeekBar) view.findViewById(R.id.sb_pager);
		sb_total = (SeekBar) view.findViewById(R.id.sb_total);
	}

	protected void initData() {
		ToolPagerService.queryToolPager(this, activity);
	}

	protected void initListener() {
		sb_pager.setOnSeekBarChangeListener(this);
		sb_total.setOnSeekBarChangeListener(this);
	}

	public <T> void showData(T... t) {
		if (t[0] instanceof ToolPager) {
			toolPager = (ToolPager) t[0];
			boolean isOpen = toolPager.getIsOpen();
			int pager = toolPager.getPager();
			int total = toolPager.getTotal();
			tbtn.setChecked(isOpen);
			sb_pager.setProgress(pager - poor_pager);
			sb_total.setProgress(total - poor_total);
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
		}
	}

	private void doClickRightButton() {
		toolPager.setIsOpen(tbtn.isChecked());
		toolPager.setPager(sb_pager.getProgress() + poor_pager);
		toolPager.setTotal(sb_total.getProgress() + poor_total);
		ToolPagerService.updateToolPager(toolPager, this, activity);

	}

	private void doClickBorderView() {
		DialogUtil.showDialog(this, "重置", "确定恢复为初始值？");
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar == sb_pager) {
			((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2))
					.setText(progress + poor_pager + "");
		} else if (seekBar == sb_total) {
			((TextView) ((ViewGroup) seekBar.getParent()).getChildAt(2))
					.setText(progress + poor_total + "");
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == -1) {
			ToolPagerService.resetToolPager(toolPager, this, activity);
		}
	}

}
