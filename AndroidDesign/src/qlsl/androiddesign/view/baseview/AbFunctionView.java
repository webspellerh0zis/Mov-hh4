package qlsl.androiddesign.view.baseview;

import android.view.View;
import android.view.ViewGroup;

/**
 * FunctionView基类的父类<br/>
 * 给FunctionView的子类提供公有对象和抽象函数<br/>
 * 
 */
public abstract class AbFunctionView<BaseActivity> {

	/**
	 * BaseActivity及BaseFragment的根布局控件，对应layout中的common_root.xml布局文件<br/>
	 */
	public ViewGroup view;

	/**
	 * FunctionView子类所对应的BaseActivity对象<br/>
	 */
	public BaseActivity activity;

	public AbFunctionView(BaseActivity activity) {
		this.activity = activity;
	}

	/**
	 * 用于控件的查找<br/>
	 * 
	 * @param view
	 *            根布局控件，对应layout中的common_root.xml布局文件<br/>
	 */
	protected abstract void initView(View view);

	/**
	 * 用于初始化数据<br/>
	 */
	protected abstract void initData();

	/**
	 * 用于设置控件的非onClickListener事件监听<br/>
	 * onClickListener事件建议在布局中使用onClick="onClick"属性,属性值onClick不要更改<br/>
	 * onClickListener事件处理在onClick函数中，函数名onClick不要更改<br/>
	 * 非onClickListener事件在initListener中初始化，在相应覆盖函数中处理<br/>
	 */
	protected abstract void initListener();
	
	



}
