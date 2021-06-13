package qlsl.androiddesign.activity.commonactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import qlsl.androiddesign.activity.baseactivity.BaseSherlockFragmentActivity;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.util.commonutil.ScreenUtils;

public class SettingActivity extends BaseSherlockFragmentActivity implements OnItemClickListener {

	private ListView listView;

	private void Init() {
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			int statusHeight = ScreenUtils.getStatusHeight(this);
			findViewById(R.id.contentView).setPadding(0, statusHeight, 0, 0);
		}
		Init();
		setListAdapter();

	}

	private void setListAdapter() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] texts = getResources().getStringArray(R.array.setting);
		for (int x = 0; x < texts.length; x++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("text", texts[x]);
			list.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.listitem_setting, new String[] { "text" },
				new int[] { R.id.tv_item });
		listView.setAdapter(adapter);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_item1:
			break;
		}
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
		}
	}

	public void onBackPressed() {
		setResult(IntentCodeConstant.RESULT_CODE_SETTING);
		finish();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			startActivity(new Intent(this, BasicSettingActivity.class));
			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		}
	}
}
