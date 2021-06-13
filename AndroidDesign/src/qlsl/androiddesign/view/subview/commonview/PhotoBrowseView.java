package qlsl.androiddesign.view.subview.commonview;

import java.io.File;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.PhotoBrowseActivity;
import qlsl.androiddesign.adapter.baseadapter.BasePagerAdapter;
import qlsl.androiddesign.adapter.commonadapter.PhotoBrowseAdapter;
import qlsl.androiddesign.adapter.commonadapter.PhotoBrowseFileAdapter;
import qlsl.androiddesign.listener.OnPageChangeListener;
import qlsl.androiddesign.view.baseview.FunctionView;
import qlsl.androiddesign.view.quickaction.ActionItem;
import qlsl.androiddesign.view.quickaction.QuickAction;
import qlsl.androiddesign.view.quickaction.QuickAction.OnActionItemClickListener;

/**
 * 相片浏览页<br/>
 * 需要传入的键：name，position,list<br/>
 * 传入的值类型：String,int，List < String >或List< File ><br/>
 * 传入的值含义：名称，位置，网络图片表或本地图片表<br/>
 * 是否必传 ：否，否，是
 */
public class PhotoBrowseView extends FunctionView<PhotoBrowseActivity>
		implements OnClickListener, OnActionItemClickListener {

	private final String[] actionItemNames = new String[] { "截屏", "分享" };

	private final int[] actionItemIcons = new int[] { R.drawable.menu_icon1, R.drawable.menu_icon2 };

	private ViewPager viewPager;

	private TextView tv_num;

	private List<?> list;

	private QuickAction quickAction;

	public PhotoBrowseView(PhotoBrowseActivity activity) {
		super(activity);
		setContentView(R.layout.activity_photo_browse);
	}

	@SuppressWarnings("unchecked")
	protected void initView(View view) {
		Intent intent = activity.getIntent();
		String title = intent.getStringExtra("name");
		int position = intent.getIntExtra("position", 0);
		list = (List<Object>) intent.getSerializableExtra("list");

		hideTitleBar();
		TextView tv_title = (TextView) view.findViewById(R.id.photo_title_view);
		tv_title.setText(title == null ? "相片浏览" : title);

		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		tv_num = (TextView) view.findViewById(R.id.tv_num);
		quickAction = new QuickAction(activity, QuickAction.VERTICAL);
		quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
		for (int index = 0; index < actionItemNames.length; index++) {
			ActionItem actionItem = new ActionItem(index, actionItemNames[index],
					activity.getResources().getDrawable(actionItemIcons[index]));
			actionItem.setSticky(true);
			quickAction.addActionItem(actionItem);
		}

		setViewPagerData(position);
	}

	protected void initData() {

	}

	protected void initListener() {
		viewPager.setOnPageChangeListener(onPageChangeListener);
		quickAction.setOnActionItemClickListener(this);
	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.photo_back_view:
			activity.finish();
			break;
		case R.id.photo_right_view:
			doClickRightButton(view);
			break;

		}
	}

	@SuppressWarnings("unchecked")
	private void setViewPagerData(int position) {
		BasePagerAdapter<?> adapter = null;
		if (list.size() != 0) {
			if (list.get(0) instanceof String) {
				adapter = new PhotoBrowseAdapter(activity, (List<String>) list);
			} else if (list.get(0) instanceof File) {
				adapter = new PhotoBrowseFileAdapter(activity, (List<File>) list);
			}
		}
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
		int size = list.size();
		if (size != 0) {
			tv_num.setText((position + 1) + "/" + list.size());
		} else {
			tv_num.setText("0/0");
		}
	}

	private void doClickRightButton(View view) {
		quickAction.show(view);
	}

	public void onItemClick(QuickAction source, int pos, int actionId) {
		switch (pos) {
		case 0:
			doClickScreenShotView();
			break;
		case 1:
			doClickShareView();
			break;
		}
	}

	private void doClickScreenShotView() {

	}

	private void doClickShareView() {

	}

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int position) {
			super.onPageSelected(position);
			tv_num.setText((position + 1) + "/" + list.size());
		}
	};

}
