package qlsl.androiddesign.adapter.baseadapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

public abstract class BasePagerAdapter<T> extends PagerAdapter {

	protected BaseActivity activity;

	private List<T> list;

	private View convertView;

	public BasePagerAdapter(BaseActivity activity, List<T> list) {
		this.activity = activity;
		this.list = list;
	}

	/**
	 * 外部类可通过此函数传递对象进来<br/>
	 * 以应对在构造adapter后，达到某种条件时需要传值进来的情况<br/>
	 */
	@SuppressWarnings("hiding")
	public <T> BasePagerAdapter<?> transport(T... t) {
		return this;
	}

	public synchronized void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public List<T> getList() {
		return list;
	}

	public int getCount() {
		return list.size();
	}

	public T getItem(int position) {
		return list.get(position);
	}

	/**
	 * 获取MVC模式控件的子布局
	 */
	protected View getItemView(ViewGroup container, int item_resource) {
		View convertView = activity.getLayoutInflater().inflate(item_resource, null);
		container.addView(convertView);
		return convertView;
	}

	/**
	 * 获取MVC模式控件子布局的控件
	 */
	@SuppressWarnings("unchecked")
	protected <V extends View> V getView(View convertView, int id) {
		View childView = convertView.findViewById(id);
		return (V) childView;
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		convertView = (View) object;
	}

	public View getCurrentView() {
		return convertView;
	}

}
