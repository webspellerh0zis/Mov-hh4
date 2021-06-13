package qlsl.androiddesign.fragment.basefragment;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * Fragment的基类<br/>
 * 一：用于提供Fragment的公用函数及管理各个Fragment<br/>
 * 二：回调FunctionView子类中的周期函数及点击事件<br/>
 */
public abstract class BaseFragment extends Fragment implements HttpListener {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 设置所有BaseFragment子类的根布局为layout中的common_root.xml布局文件<br/>
	 */
	protected ViewGroup onCreateView(LayoutInflater inflater) {
		outputCreateFragmentInfo();
		ViewGroup rootView = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			rootView = (ViewGroup) inflater.inflate(R.layout.common_root_immersion, null);
		} else {
			rootView = (ViewGroup) inflater.inflate(R.layout.common_root, null);
		}
		return rootView;
	}

	/**
	 * 设置所有BaseFragment子类的根布局为layout中的common_root.xml布局文件<br/>
	 */
	protected ViewGroup onCreateViewIgnoreImmersion(LayoutInflater inflater) {
		outputCreateFragmentInfo();
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.common_root, null);
		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onActivityResult(arg0, arg1, arg2);
		}
	}

	public void onResume() {
		super.onResume();
		outputResumeFragmentInfo();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onResume();
		}
	}

	private void outputCreateFragmentInfo() {
		Log.i("onCreateView：fragment：<br/>" + getClass().getName());
	}

	private void outputResumeFragmentInfo() {
		Log.i("onResume：fragment：<br/>" + getClass().getName());
	}

	public void onPause() {
		super.onPause();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onPause();
		}
	}

	public void onStop() {
		super.onStop();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onStop();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onDestroy();
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onSaveInstanceState(outState);
		}
	}

	public void onStart() {
		super.onStart();
		FunctionView<?> functionView = getFunctionView();
		if (functionView != null) {
			functionView.onStart();
		}
	}

	public abstract FunctionView<?> getFunctionView();

	public void onClick(View view) {
		getFunctionView().onClick(view);
	}

	public void onDbSucceed(String arg0, Object arg1) {

	}

	public void onDbFaild(String method, Object values) {
		((BaseActivity) getActivity()).showNoDataToast(values.toString());
	}

	public void onNetWorkSucceed(String method, Object object) {

	}

	public void onNetWorkFaild(String method, Object values) {
		((BaseActivity) getActivity()).showNoDataToast(values.toString());
	}

	public void onOtherSucceed(String method, Object values) {

	}

	public void onOtherFaild(String method, Object values) {
		((BaseActivity) getActivity()).showNoDataToast(values.toString());
	}

	public void onException(String className, String method, Exception e) {
		String error = "网络连接出现异常：" + "\n    异常运行窗口：" + getClass().getName() + "\n    异常所在位置：" + className
				+ "\n    捕获异常函数：" + method + "\n    " + e;
		Log.i(error);
	}

	public void onCancel(String method) {

	}

	public String toString() {
		return getClass().getName();
	}

}
