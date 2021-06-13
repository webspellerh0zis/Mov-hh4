package qlsl.androiddesign.adapter.commonadapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qlsl.androiddesign.appname.R;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseExpandableListAdapter;
import qlsl.androiddesign.entity.commonentity.Comment;
import qlsl.androiddesign.entity.commonentity.CommentReply;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.util.commonutil.ImageUtils;

public class CommentExpAdapter extends BaseExpandableListAdapter {

	public CommentExpAdapter(BaseActivity activity, ExpandableListView listView,
			List<? extends Map<String, ?>> groupList, List<? extends List<? extends Map<String, ?>>> childList) {
		super(activity, listView, groupList, childList);
	}

	@SuppressWarnings("unchecked")
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_comment_group);

		ImageView iv_icon = getView(convertView, R.id.iv_icon);
		TextView tv_text = getView(convertView, R.id.tv_text);
		TextView tv_time = getView(convertView, R.id.tv_time);
		TextView tv_content = getView(convertView, R.id.tv_content);

		HashMap<String, Comment> map = (HashMap<String, Comment>) getGroup(groupPosition);
		Comment item = map.get("group");
		Map<String, User> users = item.getUsers();
		User user_send = users.get(item.getUserId());
		String url = "";
		String name = "未知";
		if (user_send != null) {
			url = user_send.getAvatar();
			name = user_send.getName();
		}
		String content = item.getMessage();
		String time = item.getTime();

		ImageUtils.circle(activity, url, iv_icon);
		tv_text.setText(name);
		tv_time.setText(time);
		tv_content.setText(Html.fromHtml(content));

		return convertView;

	}

	@SuppressWarnings("unchecked")
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		convertView = getItemView(convertView, R.layout.listitem_comment_child);
		TextView tv_content = getView(convertView, R.id.tv_content);

		HashMap<String, CommentReply> map = (HashMap<String, CommentReply>) getChild(groupPosition, childPosition);
		CommentReply item = map.get("child");
		String content = item.getMessage();

		HashMap<String, Comment> map_group = (HashMap<String, Comment>) getGroup(groupPosition);
		Comment item_group = map_group.get("group");
		Map<String, User> users = item_group.getUsers();

		User user_send = users.get(item.getUserId());
		String name = "未知";
		if (user_send != null) {
			name = user_send.getName();
		}

		String text = name + " " + content;
		if (content.length() > 2) {
			if (!content.substring(0, 2).equals("回复")) {
				text = name + " ： " + content;
			} else if (content.substring(0, 2).equals("回复")) {
				String suffix = "<font color=#B3B3B3>回复</font>";
				String subContent = content.substring(2);
				text = name + " " + suffix + " " + subContent;
			}
		}
		tv_content.setText(Html.fromHtml(text));

		return convertView;
	}

}