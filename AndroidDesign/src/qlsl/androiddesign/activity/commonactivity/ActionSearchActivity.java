package qlsl.androiddesign.activity.commonactivity;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import qlsl.androiddesign.activity.baseactivity.BaseSherlockFragmentActivity;

public class ActionSearchActivity extends BaseSherlockFragmentActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_search);
	}

	public void onClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

}
