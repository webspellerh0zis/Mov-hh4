package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.entity.commonentity.ProcessInfo;

public class ProcessAdapter extends BaseAdapter<ProcessInfo> {

	public ProcessAdapter(BaseActivity activity, List<ProcessInfo> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_process);

		TextView tv_pid = getView(convertView, R.id.tv_pid);
		TextView tv_uid = getView(convertView, R.id.tv_uid);
		TextView tv_memory = getView(convertView, R.id.tv_memory);
		TextView tv_process = getView(convertView, R.id.tv_process);

		ProcessInfo processInfo = getItem(position);
		int pid = processInfo.getPid();
		int uid = processInfo.getUid();
		int memory = processInfo.getMemSize();
		String processName = processInfo.getProcessName();

		tv_pid.setText(pid + "");
		tv_uid.setText(uid + "");
		tv_memory.setText(memory / 1024f + "M");
		tv_process.setText(processName);

		tv_process.setMovementMethod(LinkMovementMethod.getInstance());

		return convertView;
	}
}
