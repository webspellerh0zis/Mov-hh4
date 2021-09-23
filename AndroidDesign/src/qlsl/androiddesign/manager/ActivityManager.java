package qlsl.androiddesign.manager;

import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;

/**
 * activity自定义栈<br/>
 * 主要用于跨级返回的需求<br/>
 * stack本身会存在重复添加同一个activity的现象<br/>
 * 其里功能需要在任何activity的创建，返回同步到自定义栈的前提下才能正常运行<br/>
 * 同步添加，移除后，则不会出现重复activity的现象<br/>
 * 能够在任何位置直接获取当前activity对象<br/>
 */
public class ActivityManager {

	private Stack<Activity> activityStack = new Stack<Activity>();

	private static ActivityManager instance = new ActivityManager();

	public synchronized static ActivityManager getInstance() {
		return instance;
	}

	/**
	 * 移除当前activity<br/>
	 */
	public void popActivity() {
		Activity currentActivity = currentActivity();
		if (currentActivity == null) {
			return;
		}
		currentActivity.finish();
	}

	/**
	 * 移除activity<br/>
	 * 仅限在框架上层activity监听到调用finish后同步移除栈信息<br/>
	 */
	public void removeActivity(Activity currentActivity) {
		activityStack.remove(currentActivity);
		currentActivity = null;
		outputStackInfo();
	}

	/**
	 * 移除指定的activity<br/>
	 */
	public void popActivity(Class<? extends Activity> destClass) {
		for (Iterator<Activity> iterator = activityStack.iterator(); iterator.hasNext();) {
			Activity currentActivity = iterator.next();
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				iterator.remove();
				currentActivity.finish();
				return;
			}
		}
	}

	/**
	 * 跳转到新的activity<br/>
	 * 不需要持有Context对象<br/>
	 */
	public void startActivity(Class<? extends Activity> destClass) {
		Activity currentActivity = currentActivity();
		if (currentActivity == null) {
			return;
		}
		Intent intent = new Intent(currentActivity, destClass);
		currentActivity.startActivity(intent);
	}

	/**
	 * 跳转到新的activity<br/>
	 * 不需要持有Context对象<br/>
	 */
	public void startActivity(Class<? extends Activity> destClass, Bundle bundle) {
		Activity currentActivity = currentActivity();
		Intent intent = new Intent(currentActivity, destClass);
		intent.putExtra("bundle", bundle);
		currentActivity.startActivity(intent);
	}

	/**
	 * 判断栈中是否有该元素<br/>
	 */
	private boolean contains(Class<? extends Activity> destClass) {
		for (Iterator<Activity> iterator = activityStack.iterator(); iterator.hasNext();) {
			Activity currentActivity = iterator.next();
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 移除到指定的activity(不包含指定)<br/>
	 * 用于跨级返回<br/>
	 */
	public void popToActivity(Class<? extends Activity> destClass) {
		if (!contains(destClass)) {
			return;
		}
		while (true) {
			Activity currentActivity = currentActivity();
			if (currentActivity == null) {
				return;
			}
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				return;
			} else {
				currentActivity.finish();
			}
		}
	}

	/**
	 * 移除到指定的activity(不包含指定)<br/>
	 * 用于跨级返回并且传值，同application中的跨级传值，但不需要销毁<br/>
	 */
	public void popToActivity(Class<? extends Activity> destClass, Bundle bundle) {
		while (true) {
			Activity currentActivity = currentActivity();
			if (currentActivity == null) {
				return;
			}
			currentActivity.finish();
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				Intent intent = new Intent();
				intent.putExtra("bundle", bundle);
				Activity activity = currentActivity();
				if (activity == null) {
					return;
				}
                                intent.setClass(activity, destClass);
				activity.startActivity(intent);
				return;
			}
		}
	}

        /**
	 * 移除到指定的activity(不包含指定)<br/>
	 * 用于跨级返回并且传值，同application中的跨级传值，但不需要销毁<br/>
         * 并携带旧传递数据(destPreviousClass至destClass)<br/>
	 */
	public void popToActivityWithData(Class<? extends Activity> destClass,
			Bundle bundle) {
		while (true) {
			Activity currentActivity = currentActivity();
			if (currentActivity == null) {
				return;
			}
			currentActivity.finish();
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				Intent intent = currentActivity.getIntent();
				intent.putExtra("bundle", bundle);
				Activity activity = currentActivity();
				if (activity == null) {
					return;
				}
				intent.setClass(activity, destClass);
				activity.startActivityForResult(intent,100);
				return;
			}
		}
	}


	/**
	 * 移除指定activity以下的所有窗口(不包含指定)<br/>
	 * 用于清空不可逆窗口<br/>
	 */
	public void popActivitysUnderTheSpecify(Class<? extends Activity> destClass) {
		if (!contains(destClass)) {
			return;
		}
		boolean isStartFinish = false;
		int index = getActivitySize() - 1;
		while (true && index >= 0) {
			Activity currentActivity = activityStack.get(index);
			if (currentActivity == null) {
				return;
			}
			if (currentActivity.getLocalClassName().equals(destClass.getName())) {
				isStartFinish = true;
			} else if (isStartFinish) {
				currentActivity.finish();
			}
			index--;
		}

	}

	/**
	 * 获取当前的activity<br/>
	 */
	public Activity currentActivity() {
		if (activityStack.size() == 0) {
			return null;
		}
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 获取当前的上一个activity<br/>
	 */
	public Activity previousActivity() {
		Activity activity = activityStack.get(getActivitySize() - 2);
		return activity;
	}

	/**
	 * 添加新的activity<br/>
	 */
	public void pushActivity(Activity activity) {
		activityStack.add(activity);
		outputStackInfo();
	}

	/**
	 * 移除所有activity<br/>
	 */
	public void popAllActivity() {
		while (true) {
			if (activityStack.size() != 0) {
				popActivity();
			} else {
				return;
			}
		}
	}

	/**
	 * 获取Activity数量<br/>
	 */
	public int getActivitySize() {
		return activityStack.size();
	}

	/**
	 * 输出栈信息<br/>
	 */
	private void outputStackInfo() {
//		Log.i("stack", activityStack);
	}

	/**
	 * 不持有上下文时显示Toast<br/>
	 */
	public void showToast(String text) {
		BaseActivity activity = (BaseActivity) currentActivity();
		activity.showToast(text);
	}

	public String toString() {
		return activityStack.toString();
	}

}
