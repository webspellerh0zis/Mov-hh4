package qlsl.androiddesign.listener;

import android.widget.AbsListView;
import qlsl.androiddesign.entity.commonentity.Pager;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.OnRefreshListener2;

public abstract class OnRefreshListener<T extends AbsListView> implements
		OnRefreshListener2<T> {

	public void onPullDownToRefresh(PullToRefreshBase<T> refreshView) {
		onRefresh();
	}

	public void onPullUpToRefresh(PullToRefreshBase<T> refreshView) {
		onRefresh();
	}

	public void onRefresh(PullToRefreshBase<T> refreshView, Pager pager) {
		int pageNo = pager.getPageNo();
		int totalCount = pager.getTotalCount();
		int totalPage = pager.getTotalPage();
		if (pageNo < totalPage) {
			nextPager();
		} else {
			refreshView.onLoadComplete(totalPage, totalCount);
		}
	}

	public abstract void onRefresh();

	public abstract void nextPager();

}
