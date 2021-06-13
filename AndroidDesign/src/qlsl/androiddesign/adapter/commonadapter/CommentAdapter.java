package qlsl.androiddesign.adapter.commonadapter;

import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.activity.commonactivity.CommentReplyActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.entity.commonentity.Comment;
import qlsl.androiddesign.entity.commonentity.CommentReply;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.util.commonutil.ImageUtils;

public class CommentAdapter extends BaseAdapter<Comment> {

	public CommentAdapter(BaseActivity activity, List<Comment> list) {
		super(activity, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_comment);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_text = getView(convertView, R.id.tv_text);
		TextView tv_time = getView(convertView, R.id.tv_time);
		TextView tv_content = getView(convertView, R.id.tv_content);

		final Comment item = getItem(position);
		Map<String, User> users = item.getUsers();
		User user_send = users.get(item.getUserId());
		String url = user_send.getAvatar();
		String name = user_send.getName();
		String content = item.getMessage();
		String time = item.getTime();
		final List<CommentReply> replys = item.getReplys();

		ImageUtils.circle(activity, url, iv_icon);
		tv_text.setText(name);
		tv_time.setText(time);
		tv_content.setText(content);

		convertView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (replys != null && replys.size() > 0) {
					Intent intent = new Intent(activity, CommentReplyActivity.class);
					intent.putExtra("comment", item);
					activity.startActivity(intent);
				}
			}
		});

		return convertView;
	}

}
