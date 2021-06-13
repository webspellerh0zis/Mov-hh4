package qlsl.androiddesign.activity.commonactivity;

import static qlsl.androiddesign.util.commonutil.Log.isDebug;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseSherlockPreferenceActivity;
import qlsl.androiddesign.service.subservice.LogFloatService;
import qlsl.androiddesign.util.commonutil.Log;

public class LogSettingActivity extends BaseSherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	private void init() {
		addPreferencesFromResource(R.drawable.preference_log_setting);
		setContentView(R.layout.common_root_preference);
		setTitle("日志设置");
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void onPause() {
		super.onPause();
		Log.notifyDataSetChanged(this);
		if (isDebug) {
			startService(new Intent(this, LogFloatService.class));
		} else {
			stopService(new Intent(this, LogFloatService.class));
		}
	}

}
