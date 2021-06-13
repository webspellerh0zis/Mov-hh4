package qlsl.androiddesign.util.commonutil;

import com.qlsl.androiddesign.appname.R;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.view.baseview.FunctionView;

public class DialogUtil {

	/**
	 * 显示下拉列表EditTextDialog<br/>
	 * activity回调<br/>
	 */
	public static void showEditTextDialog(BaseActivity activity, String title,
			String msg, int resource, String tag, String[] texts,
			BaseAdapter<?> adapter, int childIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setMessage(msg);
		ViewGroup rootView = (ViewGroup) LayoutInflater.from(activity).inflate(
				resource, null);
		for (int index = 0; index < rootView.getChildCount(); index++) {
			EditText et = (EditText) rootView.getChildAt(index);
			if (texts != null) {
				et.setText(texts[index]);
			}
			if (childIndex == index) {
				if (adapter != null) {
					AutoCompleteTextView actv = (AutoCompleteTextView) et;
					adapter.transport(et);
					actv.setAdapter(adapter);
				}
			}
		}
		rootView.setTag(tag);
		builder.setView(rootView);
		builder.setCancelable(true);
		builder.setPositiveButton("确定", (OnClickListener) activity);
		builder.setNegativeButton("取消", (OnClickListener) activity);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 显示下拉列表EditTextDialog<br/>
	 * functionView回调<br/>
	 */
	public static void showEditTextDialog(FunctionView<?> functionView,
			String title, String msg, int resource, String tag, String[] texts,
			BaseAdapter<?> adapter, int childIndex) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				functionView.activity);
		builder.setTitle(title);
		builder.setMessage(msg);
		ViewGroup rootView = (ViewGroup) LayoutInflater.from(
				functionView.activity).inflate(resource, null);
		for (int index = 0; index < rootView.getChildCount(); index++) {
			EditText et = (EditText) rootView.getChildAt(index);
			if (texts != null) {
				et.setText(texts[index]);
			}
			if (childIndex == index) {
				if (adapter != null) {
					AutoCompleteTextView actv = (AutoCompleteTextView) et;
					adapter.transport(et);
					actv.setAdapter(adapter);
				}
			}
		}
		rootView.setTag(tag);
		builder.setView(rootView);
		builder.setCancelable(true);
		builder.setPositiveButton("确定", (OnClickListener) functionView);
		builder.setNegativeButton("取消", (OnClickListener) functionView);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 显示EditTextDialog<br/>
	 * functionView回调<br/>
	 */
	public static void showEditTextDialog(FunctionView<?> functionView,
			String title, String msg, String hint, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				functionView.activity);
		builder.setTitle(title);
		builder.setMessage(msg);
		View rootView = LayoutInflater.from(functionView.activity).inflate(
				R.layout.common_dialog_edit_text, null);
		EditText et_content = (EditText) rootView.findViewById(R.id.et_content);
		if (!TextUtils.isEmpty(hint)) {
			et_content.setHint(hint);
		}
		if (!TextUtils.isEmpty(text)) {
			et_content.setText(text);
		}
		builder.setView(rootView);
		builder.setCancelable(true);
		builder.setPositiveButton("确定", (OnClickListener) functionView);
		builder.setNegativeButton("取消", (OnClickListener) functionView);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 显示确定，取消按钮对话框<br/>
	 */
	public static void showDialog(FunctionView<?> functionView, String title,
			String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				functionView.activity);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(true);
		builder.setPositiveButton("确定", (OnClickListener) functionView);
		builder.setNegativeButton("取消", (OnClickListener) functionView);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 显示注册，登陆按钮对话框<br/>
	 */
	public static void showLoginDialog(FunctionView<?> functionView) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				functionView.activity);
		builder.setTitle(functionView.activity.getResources().getString(
				R.string.app_name));
		builder.setMessage("未登录");
		builder.setCancelable(true);
		builder.setPositiveButton("注册", (OnClickListener) functionView);
		builder.setNegativeButton("登陆", (OnClickListener) functionView);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
