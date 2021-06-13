package qlsl.androiddesign.adapter.baseadapter;

import java.util.List;

import android.support.v4.app.FragmentManager;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

	private List<BaseFragment> list;

	public FragmentPagerAdapter(FragmentManager manager, List<BaseFragment> list) {
		super(manager);
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public BaseFragment getItem(int position) {
		return list.get(position);
	}

}
