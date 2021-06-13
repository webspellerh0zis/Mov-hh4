package qlsl.androiddesign.fragment.commonfragment;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.qlsl.androiddesign.appname.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentBar3 extends Fragment implements TabListener {

	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.bar3, null);
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onClick(View v) {

	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.content_frame, this);
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(this);
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

}
