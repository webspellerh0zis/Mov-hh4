package qlsl.androiddesign.service.subservice;

import com.qlsl.androiddesign.appname.R;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import qlsl.androiddesign.activity.commonactivity.LogActivity;
import qlsl.androiddesign.activity.commonactivity.MemoryActivity;
import qlsl.androiddesign.activity.commonactivity.ProcessActivity;
import qlsl.androiddesign.service.baseservice.BaseService;
import qlsl.androiddesign.view.quickaction.ActionItem;
import qlsl.androiddesign.view.quickaction.QuickAction;
import qlsl.androiddesign.view.quickaction.QuickAction.OnActionItemClickListener;

/**
 * 日志悬浮窗服务Service<br/>
 * 仅在调试模式Log.isDebug=true时(MainActivity:startFloatService)或搜索中输入密码才应该开启服务<br/>
 * 搜索功能尚未开发，开发时记得设置密码开启此服务<br/>
 */
public class LogFloatService extends BaseService
		implements OnClickListener, OnActionItemClickListener, OnTouchListener {

	private WindowManager wm;

	private WindowManager.LayoutParams wmParams;

	private float x, y, mTouchStartX, mTouchStartY, down_x, down_y;

	private boolean isFirstTouch;

	private View view;

	private View btn_float;

	private QuickAction quickAction;

	public void onCreate() {
		super.onCreate();
		initManager();
		initView();
		initListener();
	}

	public IBinder onBind(Intent intent) {
		super.onBind(intent);
		return null;
	}

	private void initManager() {
		wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		wmParams = new WindowManager.LayoutParams();
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
	}

	private void initView() {
		view = createFloatView(wm, wmParams, Gravity.CENTER, R.layout.window_float_log);
		btn_float = view.findViewById(R.id.btn_float);

		String[] actionItemNames = new String[] { "日志", "内存", "进程" };
		int[] actionItemIcons = new int[] { R.drawable.app_icon_default, R.drawable.app_icon_default,
				R.drawable.app_icon_default };
		quickAction = new QuickAction(this);
		quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
		for (int index = 0; index < actionItemNames.length; index++) {
			ActionItem actionItem = new ActionItem(index, actionItemNames[index],
					getResources().getDrawable(actionItemIcons[index]));
			actionItem.setSticky(true);
			quickAction.addActionItem(actionItem);
		}
	}

	private void initListener() {
		btn_float.setOnTouchListener(this);
		btn_float.setOnClickListener(this);
		quickAction.setOnActionItemClickListener(this);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			down_x = event.getRawX();
			down_y = event.getRawY();
			if (!isFirstTouch) {
				isFirstTouch = true;
				mTouchStartX = down_x;
				mTouchStartY = down_y;
				x = down_x;
				y = down_y;
			} else {
				mTouchStartX += (down_x - x);
				mTouchStartY += (down_y - y);
			}

			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getRawX();
			y = event.getRawY();
			wmParams.x = (int) (x - mTouchStartX);
			wmParams.y = (int) (y - mTouchStartY);
			wm.updateViewLayout(view, wmParams);
			break;

		case MotionEvent.ACTION_UP:
			float dis_x = Math.abs(event.getRawX() - down_x);
			float dis_y = Math.abs(event.getRawY() - down_y);
			if (dis_x < 5 && dis_y < 5) {
				btn_float.performClick();
			}
			break;
		}
		return true;
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_float:
			doClickFloatButton(view);
			break;

		}
	}

	private void doClickFloatButton(View view) {
		quickAction.showFromWindow(view);
	}

	public void onDestroy() {
		super.onDestroy();
		if (view != null) {
			wm.removeView(view);
		}
	}

	public void onItemClick(QuickAction source, int pos, int actionId) {
		switch (pos) {
		case 0:
			showLogView();
			break;
		case 1:
			showMemoryView();
			break;
		case 2:
			showProcessView();
			break;
		}
	}

	private void showLogView() {
		Intent intent = new Intent(this, LogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void showMemoryView() {
		Intent intent = new Intent(this, MemoryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void showProcessView() {
		Intent intent = new Intent(this, ProcessActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
