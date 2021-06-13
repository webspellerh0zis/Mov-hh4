package qlsl.androiddesign.activity.commonactivity;

import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseSherlockPreferenceActivity;

public class BasicSettingActivity extends BaseSherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	private void init() {
		addPreferencesFromResource(R.drawable.preference_basic_setting);
		setContentView(R.layout.common_root_preference);
		setTitle("基本设置");
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

}
