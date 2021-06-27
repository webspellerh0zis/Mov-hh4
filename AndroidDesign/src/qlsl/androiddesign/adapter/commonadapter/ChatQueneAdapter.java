package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.db.othertable.ChatQueue;

public class ChatQueneAdapter extends BaseAdapter<ChatQueue> {

	public ChatQueneAdapter(BaseActivity activity, List<ChatQueue> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_chat_queue);
		@SuppressWarnings("unused")
		ViewGroup front_view = getView(convertView, R.id.front_view);
		@SuppressWarnings("unused")
		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_name = getView(convertView, R.id.tv_name);
		TextView tv_content = getView(convertView, R.id.tv_content);
		TextView tv_time = getView(convertView, R.id.tv_time);

		ChatQueue chatQueue = getItem(position);

		Integer queneType = chatQueue.getQueueType();
		String name = chatQueue.getSenderName();
		String content = chatQueue.getNewMessage();
		Long time = chatQueue.getUpdateTime();

		tv_name.setText(name);
		tv_content.setText(content);

		if (queneType == 0) {

		} else if (queneType == 1) {

		} else if (queneType == 2) {

		} else if (queneType == 3) {

		} else if (queneType == 4) {

		}

		return convertView;
	}

}
