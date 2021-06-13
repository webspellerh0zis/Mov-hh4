package qlsl.androiddesign.listener;

import android.view.View;
import qlsl.androiddesign.entity.commonentity.Pager;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase;
import qlsl.androiddesign.library.pulltorefresh.PullToRefreshBase.OnRefreshListener2;

public abstract class OnPullRefreshListener<T extends View> implements
		OnRefreshListener2<T> {

	public void onPullDownToRefresh(PullToRefreshBase<T> refreshView) {
		onPullDown();
	}

	public void onPullUpToRefresh(PullToRefreshBase<T> refreshView) {
		onPullUp();
	}

	public void onPullUp(PullToRefreshBase<T> refreshView, Pager pager) {
		int pageNo = pager.getPageNo();
		int totalCount = pager.getTotalCount();
		int totalPage = pager.getTotalPage();
		if (pageNo < totalPage) {
			nextPager();
		} else {
			refreshView.onLoadComplete(totalPage, totalCount);
		}
	}

	public abstract void onPullDown();

	public abstract void onPullUp();

	public abstract void nextPager();

}
