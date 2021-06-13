package qlsl.androiddesign.view.subview.commonview;

import java.util.ArrayList;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.adapter.commonadapter.ChatQueneAdapter;
import qlsl.androiddesign.db.othertable.ChatQueue;
import qlsl.androiddesign.fragment.commonfragment.FragmentTab1;
import qlsl.androiddesign.http.service.commonservice.ChatService;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.Mode;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshSwipeListView;
import qlsl.androiddesign.listener.SwipeListViewListener;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.listview.swipe.SwipeListView;

/**
 * 聊天队列页，暂无使用<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class ChatQueneView extends FunctionView<MainActivity>implements OnRefreshListener<SwipeListView> {

	private FragmentTab1 fragment;

	private PullToRefreshSwipeListView refreshView;

	private List<ChatQueue> list = new ArrayList<ChatQueue>();

	public ChatQueneView(FragmentTab1 fragment, ViewGroup rootView, ViewGroup contentView) {
		super((MainActivity) fragment.getActivity());
		this.fragment = fragment;
		setContentView(rootView, contentView);
	}

	protected void initView(View view) {
		setTitle("消息");
		refreshView = (PullToRefreshSwipeListView) view.findViewById(R.id.refreshListView);
		refreshView.setMode(Mode.PULL_FROM_START);
	}

	protected void initData() {
		// ChatService.queryChatQueueList(this, fragment);
		// ChatService.receiveNewMessage(this, fragment);
	}

	protected void initListener() {
		refreshView.setOnRefreshListener(this);
		refreshView.getRefreshableView().setSwipeListViewListener(swipeListViewListener);
	}

	@SuppressWarnings("unchecked")
	public <T> void showData(T... t) {
		if (t[0] instanceof List) {
			List<ChatQueue> list_result = (List<ChatQueue>) t[0];
			list.clear();
			list.addAll(list_result);
			notofyDataSetChanged();
		}
	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_delete:
			doClickDeleteView(view);
			break;
		}
	}

	private void doClickDeleteView(View view) {
		refreshView.getRefreshableView().closeOpenedItems();
		int position = refreshView.getRefreshableView().getPositionForView(view);
		ChatQueue chatQueue = (ChatQueue) refreshView.getAdapter().getItem(position);
		ChatService.deleteChatQuene(chatQueue, this, fragment);
	}

	private void notofyDataSetChanged() {
		BaseAdapter adapter = refreshView.getBaseAdapter();
		if (adapter == null) {
			adapter = new ChatQueneAdapter(activity, list);
			refreshView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		refreshView.setReleaseLabel(0, list.size(), 0, list.size());
		refreshView.onRefreshComplete();
	}

	public void onRefresh(PullToRefreshBase<SwipeListView> refreshView) {
		ChatService.queryChatQueueList(this, fragment);
	}

	private SwipeListViewListener swipeListViewListener = new SwipeListViewListener() {

		public void onClickFrontView(int position) {
			showToast("onClickFrontView:" + position);
		}

	};

}
