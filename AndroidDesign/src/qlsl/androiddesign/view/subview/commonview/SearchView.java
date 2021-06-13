package qlsl.androiddesign.view.subview.commonview;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import qlsl.androiddesign.activity.commonactivity.SearchActivity;
import qlsl.androiddesign.adapter.commonadapter.SearchAdapter;
import qlsl.androiddesign.entity.commonentity.Pager;
import qlsl.androiddesign.http.service.commonservice.SearchService;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.Mode;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshListView;
import qlsl.androiddesign.listener.OnRefreshListener;
import qlsl.androiddesign.listener.OnTextChangeListener;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 搜索页[教师，学生，家长]<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class SearchView extends FunctionView<SearchActivity>implements OnItemSelectedListener, OnItemClickListener {

	private EditText et_search;

	private Spinner spinner;

	private PullToRefreshListView refreshView;

	private List<Group> list = new ArrayList<Group>();

	private Pager pager;

	private boolean isFirst = true;

	public SearchView(SearchActivity activity) {
		super(activity);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setContentView(R.layout.activity_search_immersion);
		} else {
			setContentView(R.layout.activity_search);
		}
	}

	protected void initView(View view) {
		hideTitleBar();
		et_search = findViewById(R.id.et_search);
		spinner = findViewById(R.id.spinnerView);
		refreshView = findViewById(R.id.refreshListView);
		refreshView.setEmptyView(findViewById(R.id.tv_empty));
		refreshView.setMode(Mode.PULL_FROM_END);
	}

	protected void initData() {
		setSpinnerData();
	}

	protected void initListener() {
		et_search.addTextChangedListener(textChangeListener);
		spinner.setOnItemSelectedListener(this);
		refreshView.setOnRefreshListener(refreshListener);
		refreshView.setOnItemClickListener(this);
	}

	@SuppressWarnings("unchecked")
	public <T> void showData(T... t) {
		if (t[0] instanceof HashMap) {
			HashMap<String, Object> map = (HashMap<String, Object>) t[0];
			List<Group> list_result = (List<Group>) map.get("list");
			pager = (Pager) map.get("pager");
			if (pager.getPageNo() == 0) {
				resetList(list_result);
			} else {
				list.addAll(list_result);
			}
			notifyDataSetChanged();
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_search:
			doClickSearchButton(view);
			break;
		}
	}

	private void doClickSearchButton(View view) {
		Button btn_search = (Button) view;
		String text = btn_search.getText().toString();
		if (text.equals("取消")) {
			activity.finish();
		} else {
			searchGroup(0);
		}
	}

	private void searchGroup(int page) {
		String keyword = et_search.getText().toString();
		String selectItem = spinner.getSelectedItem().toString();
		int type = -1;
		if (selectItem.equals("全部")) {
			type = -1;
		} else if (selectItem.equals("同事圈")) {
			type = 1;
		} else if (selectItem.equals("班级圈")) {
			type = 0;
		} else if (selectItem.equals("分享圈")) {
			type = 2;
		}
		Log.checkDebugModel(activity, keyword);
		SearchService.searchGroup(keyword, type, page, this, activity);
	}

	private void setSpinnerData() {
		String[] array = null;
		if (UserMethod.isTeacher()) {
			array = activity.getResources().getStringArray(R.array.group_type_teacher);
		} else {
			array = activity.getResources().getStringArray(R.array.group_type_student);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.common_listitem_spinner,
				R.id.tv_text, array);
		spinner.setAdapter(adapter);
		adapter.setDropDownViewResource(R.layout.common_listitem_spinner_dropdown);

	}

	private void resetList(List<Group> list_result) {
		list.clear();
		list.addAll(list_result);
		refreshView.getRefreshableView().smoothScrollToPositionFromTop(0, 0);
	}

	private void notifyDataSetChanged() {
		BaseAdapter adapter = refreshView.getBaseAdapter();
		if (adapter == null) {
			adapter = new SearchAdapter(activity, list);
			refreshView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		refreshView.setReleaseLabel(pager.getPageNo(), list.size(), pager.getTotalPage(), pager.getTotalCount());
		refreshView.onRefreshComplete();
	}

	private OnTextChangeListener textChangeListener = new OnTextChangeListener() {

		public void afterTextChanged(android.text.Editable s) {
			Button btn_search = findViewById(R.id.btn_search);
			if (TextUtils.isEmpty(s)) {
				btn_search.setText("取消");
				btn_search.setActivated(false);
			} else {
				btn_search.setText("搜索");
				btn_search.setActivated(true);
			}
		};
	};

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (!isFirst) {
			searchGroup(0);
		}
		isFirst = false;
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	private OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {

		public void onRefresh() {
			super.onRefresh(refreshView, pager);
		}

		public void nextPager() {
			searchGroup(pager.getPageNo() + 1);
		}
	};

}
