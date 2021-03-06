package com.photoselector.ui;

import java.util.ArrayList;
import java.util.List;

import com.photoselector.model.PhotoModel;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

/**
 * BaseAdapter的基类<br/>
 * 一：统一实现BaseAdapter子类的某些需实现函数<br/>
 * 二：封装BaseAdapter的子布局设置功能，ViewHolder缓存功能<br/>
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter implements Filterable {

	protected Activity activity;

	private List<T> list;

	public BaseAdapter(Activity activity, List<T> list) {
		this.activity = activity;
		this.list = list;
		if (list == null) {
			this.list = new ArrayList<T>();
		}
		this.list.add(0, null);
	}

	/**
	 * 外部类可通过此函数传递对象进来<br/>
	 * 以应对在构造adapter后，达到某种条件时需要传值进来的情况<br/>
	 */
	@SuppressWarnings("hiding")
	public <T> void transport(T... t) {

	}

	public synchronized void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public List<T> getItems() {
		return list;
	}

	public int getCount() {
		return list.size();
	}

	public T getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
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
	 * 获取MVC模式控件的子布局
	 */
	protected View getItemViewUn(View convertView, int item_resource) {
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

	public Filter getFilter() {
		return null;
	}

	public void update(List<T> models) {
		if (list == null) {
			return;
		}
		this.list.clear();
		for (T t : models) {
			this.list.add(t);
		}
		list.add(0, null);
		notifyDataSetChanged();
	}
}
