package qlsl.androiddesign.fragment.commonfragment;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.fragment.basefragment.BaseFragment;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.subview.commonview.PagerMainView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
/**
 * MainActivity各Fragment生命周期说明：<br/>
 * MainFragment中的onResume,onPause等是在进入或退出MainActivity时执行 <br/>
 * FragmentTab 中的onTabResume,onTabPause等是在FragmentTab之间切换时,在进入或置后MainActivity时调用<br/>
 * 
 * 使用ViewPager+Fragment实现选项卡<br/>
 * 会预加载下一个Fragment，但不会重复加载<br/>
 * 重新制定的周期函数在TabFragment中<br/>
 * LazyViewPager是实现不预加载的ViewPager,并未完成<br/>
 *
 */
public class PagerMainFragment extends BaseFragment {

	private PagerMainView functionView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (functionView == null) {
			ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_main_pager, null);
			functionView = new PagerMainView(this, onCreateView(inflater), contentView);
		}
		ViewGroup parent = (ViewGroup) functionView.view.getParent();
		if (parent != null) {
			parent.removeView(functionView.view);
		}
		return functionView.view;
	}

	public FunctionView<?> getFunctionView() {
		return functionView;
	}

	public void showTabWidget() {
		functionView.showTabWidget();
	}

	public void hideTabWidget() {
		functionView.hideTabWidget();
	}

}