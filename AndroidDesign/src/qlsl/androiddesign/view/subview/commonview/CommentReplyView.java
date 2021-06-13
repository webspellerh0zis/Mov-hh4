package qlsl.androiddesign.view.subview.commonview;

import com.qlsl.androiddesign.appname.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.CommentReplyActivity;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.adapter.commonadapter.CommentReplyAdapter;
import qlsl.androiddesign.entity.commonentity.Comment;
import qlsl.androiddesign.entity.commonentity.CommentReply;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 评论回复列表页<br/>
 * 需要传入的键：comment<br/>
 * 传入的值类型：Comment<br/>
 * 传入的值含义：评论<br/>
 * 是否必传 ：是
 */
public class CommentReplyView extends FunctionView<CommentReplyActivity>implements OnClickListener {

	private ListView listView;

	private Comment item;

	public CommentReplyView(CommentReplyActivity activity) {
		super(activity);
		setContentView(R.layout.activity_comment_reply);
	}

	protected void initView(View view) {
		setTitle("评论回复");
		listView = (ListView) view.findViewById(R.id.listView);
		TextView emptyView = (TextView) view.findViewById(R.id.tv_empty);
		listView.setEmptyView(emptyView);
		item = (Comment) activity.getIntent().getSerializableExtra("comment");
	}

	protected void initData() {
		notifyDataSetChanged();
	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {

	}

	@SuppressWarnings("unchecked")
	private void notifyDataSetChanged() {
		BaseAdapter<CommentReply> adapter = (BaseAdapter<CommentReply>) listView.getAdapter();
		if (adapter == null) {
			adapter = new CommentReplyAdapter(activity, item);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

}
