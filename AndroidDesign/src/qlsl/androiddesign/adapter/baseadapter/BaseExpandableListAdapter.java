package qlsl.androiddesign.adapter.baseadapter;

import java.util.List;
import java.util.Map;

import android.util.SparseArray;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

public abstract class BaseExpandableListAdapter extends android.widget.BaseExpandableListAdapter {

	protected BaseActivity activity;

	private ExpandableListView listView;

	private List<? extends Map<String, ?>> groupList;

	private List<? extends List<? extends Map<String, ?>>> childList;

	public BaseExpandableListAdapter(BaseActivity activity, ExpandableListView listView,
			List<? extends Map<String, ?>> groupList, List<? extends List<? extends Map<String, ?>>> childList) {
		this.activity = activity;
		this.listView = listView;
		this.groupList = groupList;
		this.childList = childList;
	}

	/**
	 * 外部类可通过此函数传递对象进来<br/>
	 * 以应对在构造adapter后，达到某种条件时需要传值进来的情况<br/>
	 */
	public <T> qlsl.androiddesign.adapter.baseadapter.BaseExpandableListAdapter transport(T... t) {
		return this;
	}

	public synchronized void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public List<? extends Map<String, ?>> getGroupList() {
		return groupList;
	}

	public List<? extends List<? extends Map<String, ?>>> getChildList() {
		return childList;
	}

	public int getGroupCount() {
		return groupList.size();
	}

	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}

	/**
	 * 获取MVC模式控件的子布局
	 */
	protected View getItemView(View convertView, int item_resource) {
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(item_resource, null);
		}
		return convertView;
	}

	/**
	 * 获取MVC模式控件的子布局<br/>
	 * 适用于itemview随groupPosition变化的情况<br/>
	 */
	protected View getNewItemView(View convertView, int item_resource) {
		convertView = activity.getLayoutInflater().inflate(item_resource, null);
		return convertView;
	}

	/**
	 * 获取MVC模式控件子布局的控件，自动处理ViewHolder的缓存
	 */
	@SuppressWarnings("unchecked")
	protected <V extends View> V getView(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (V) childView;
	}

	/**
	 * 获取MVC模式控件子布局的控件，不使用ViewHolder进行处理
	 */
	@SuppressWarnings("unchecked")
	protected <V extends View> V getNewView(View convertView, int id) {
		return (V) convertView.findViewById(id);
	}

	protected void setActivated(ImageView iv_arrow, boolean isExpanded) {
		if (isExpanded) {
			iv_arrow.setActivated(true);
		} else {
			iv_arrow.setActivated(false);
		}
	}

	/**
	 * 展开所有listitem项
	 */
	public void expandGroup() {
		for (int index = 0; index < getGroupCount(); index++) {
			listView.expandGroup(index);
		}
	}

}
