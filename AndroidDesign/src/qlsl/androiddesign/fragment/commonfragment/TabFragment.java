package qlsl.androiddesign.fragment.commonfragment;

import qlsl.androiddesign.fragment.basefragment.BaseFragment;

/**
 * 用于解决使用ViewPager+Fragment时的周期混乱问题<br/>
 * 需要时在SubFragment中覆盖以下函数并禁用Fragment本身的周期函数<br/>
 *
 */
public abstract class TabFragment extends BaseFragment {

	/**
	 * 可操作状态<br/>
	 */
	public void onTabResume() {

	}

	/**
	 * 暂停状态<br/>
	 */
	public void onTabPause() {

	}
}
