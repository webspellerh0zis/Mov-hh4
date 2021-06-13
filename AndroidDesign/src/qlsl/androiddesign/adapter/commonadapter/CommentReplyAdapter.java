package qlsl.androiddesign.adapter.commonadapter;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.entity.commonentity.Comment;
import qlsl.androiddesign.entity.commonentity.CommentReply;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.util.commonutil.ImageUtils;

public class CommentReplyAdapter extends BaseAdapter<CommentReply> {

	private Comment item;

	public CommentReplyAdapter(BaseActivity activity, Comment item) {
		super(activity, item.getReplys());
		this.item = item;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_comment);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_text = getView(convertView, R.id.tv_text);
		TextView tv_time = getView(convertView, R.id.tv_time);
		TextView tv_content = getView(convertView, R.id.tv_content);
		TextView tv_reply = getView(convertView, R.id.tv_reply);
		ImageView iv_content = getView(convertView, R.id.iv_content);
		final View audioView = getView(convertView, R.id.audioView);

		final CommentReply commentReply = getItem(position);
		String single = " ";
		final User user = item.getUsers().get(item.getUserId());
		final User userReply = item.getUsers().get(commentReply.getUserId());
		String username = user.getName();
		String username_reply = userReply.getName();
		String text = username_reply + single + "回复" + single + username;
		String photoUrl = userReply.getAvatar();
		String content = commentReply.getMessage();
		String time = commentReply.getTime();

		tv_reply.setVisibility(View.GONE);

		tv_content.setText(content);
		tv_content.setVisibility(View.VISIBLE);
		iv_content.setVisibility(View.GONE);
		audioView.setVisibility(View.GONE);

		tv_text.setText(text);
		tv_time.setText(time);

		ImageUtils.circle(activity, photoUrl, username_reply, iv_icon);

		return convertView;
	}

}
