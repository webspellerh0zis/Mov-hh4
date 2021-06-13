package qlsl.androiddesign.view.indicatorview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.qlsl.androiddesign.appname.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import qlsl.androiddesign.activity.baseactivity.BaseActivity;
import qlsl.androiddesign.util.commonutil.ImageUtils;

/**
 * 用户指引,宣传画控件(类似于Gallery效果)
 * 
 * @author savant-pan
 * 
 */
public class ImageIndicatorView extends RelativeLayout implements OnClickListener {

	/**
	 * ViewPager的适配器
	 */
	private MyPagerAdapter pageAdapter;
	/**
	 * ViewPager控件
	 */
	private ViewPager viewPager;
	/**
	 * 指示器容器
	 */
	private LinearLayout indicateLayout;

	/**
	 * 文本指示器
	 */
	private TextView tv_pager;

	/**
	 * 删除控件
	 */
	private ImageView iv_delete;

	/**
	 * 向左划箭头
	 */
	private Button leftButton;

	/**
	 * 向右划箭头
	 */
	private Button rightButton;

	/**
	 * 页面列表
	 */
	private List<View> viewList = new ArrayList<View>();

	private Handler refreshHandler;

	/**
	 * 滑动位置通知回调监听对象
	 */
	private OnItemChangeListener onItemChangeListener;

	/**
	 * 单个界面点击回调监听对象
	 */
	private OnItemClickListener onItemClickListener;
	/**
	 * 总页面条数
	 */
	private int totelCount = 0;
	/**
	 * 当前页索引
	 */
	private int currentIndex = 0;

	/**
	 * 圆形列表+箭头提示器
	 */
	public static final int INDICATE_ARROW_ROUND_STYLE = 0;

	/**
	 * 操作导引提示器
	 */
	public static final int INDICATE_USERGUIDE_STYLE = 1;

	/**
	 * 圆形列表
	 */
	public static final int INDICATE_ROUND_STYLE = 2;

	/**
	 * 没有圆形列表，没有箭头提示器
	 */
	public static final int INDICATE_PAGER_STYLE = 3;

	/**
	 * 没有圆形列表，没有箭头提示器<br/>
	 * 删除模式下非默认图片须设置非null的tag作为删除的判断<br/>
	 */
	public static final int INDICATE_PAGER_DELETE_STYLE = 4;

	/**
	 * INDICATOR样式
	 */
	private int indicatorStyle = INDICATE_ARROW_ROUND_STYLE;

	/**
	 * 最近一次划动时间
	 */
	private long refreshTime = 0l;

	/**
	 * 广告位置监听接口
	 */
	public interface OnItemChangeListener {
		void onPosition(int position, int totalCount);
	}

	/**
	 * 条目点击事件监听接口
	 */
	public interface OnItemClickListener {
		void OnItemClick(View view, int position);
	}

	public ImageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	public ImageIndicatorView(Context context) {
		super(context);
		this.init(context);
	}

	/**
	 * @param context
	 */
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.indicator_view, this);
		this.viewPager = (ViewPager) findViewById(R.id.viewpager_image);
		this.indicateLayout = (LinearLayout) findViewById(R.id.indicater_layout);
		this.tv_pager = (TextView) findViewById(R.id.tv_pager);
		this.iv_delete = (ImageView) findViewById(R.id.iv_delete);
		this.leftButton = (Button) findViewById(R.id.left_button);
		this.rightButton = (Button) findViewById(R.id.right_button);

		this.leftButton.setVisibility(View.GONE);
		this.rightButton.setVisibility(View.GONE);

		this.viewPager.setOnPageChangeListener(new PageChangeListener());

		final ArrowClickListener arrowClickListener = new ArrowClickListener();
		this.leftButton.setOnClickListener(arrowClickListener);
		this.rightButton.setOnClickListener(arrowClickListener);

		iv_delete.setOnClickListener(this);

		this.refreshHandler = new ScrollIndicateHandler(ImageIndicatorView.this);
	}

	/**
	 * 取ViewPager实例
	 * 
	 * @return
	 */
	public ViewPager getViewPager() {
		return viewPager;
	}

	/**
	 * 取当前位置Index值
	 */
	public int getCurrentIndex() {
		return this.currentIndex;
	}

	/**
	 * 取总VIEW数目
	 */
	public int getTotalCount() {
		return this.totelCount;
	}

	/**
	 * 取最近一次刷新时间
	 */
	public long getRefreshTime() {
		return this.refreshTime;
	}

	/**
	 * 添加单个View
	 * 
	 * @param view
	 */
	public void addViewItem(View view) {
		final int position = viewList.size();
		view.setOnClickListener(new ItemClickListener(position));
		this.viewList.add(view);
	}

	public void clear() {
		this.viewList.clear();
	}

	/**
	 * 条目点击事件监听类
	 */
	private class ItemClickListener implements View.OnClickListener {
		private int position = 0;

		public ItemClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View view) {
			if (onItemClickListener != null) {
				onItemClickListener.OnItemClick(view, position);
			}
		}
	}

	/**
	 * 设置显示图片Drawable数组
	 * 
	 * @param resArray
	 *            Drawable数组
	 */
	public void setupLayoutByDrawable(final Integer resArray[]) {
		if (resArray == null)
			throw new NullPointerException();

		this.setupLayoutByDrawable(Arrays.asList(resArray));
	}

	/**
	 * 设置显示图片Drawable列表
	 * 
	 * @param resList
	 *            Drawable列表
	 */
	public void setupLayoutByDrawable(final List<Integer> resList) {
		if (resList == null)
			throw new NullPointerException();

		final int len = resList.size();
		if (len > 0) {
			for (int index = 0; index < len; index++) {
				final ImageView pageItem = new ImageView(getContext());
				pageItem.setBackgroundResource(resList.get(index));
				addViewItem(pageItem);
			}
		}
	}

	/**
	 * 设置当前显示项
	 * 
	 * @param index
	 *            postion
	 */
	public void setCurrentItem(int index) {
		this.currentIndex = index;
	}

	/**
	 * 设置指示器样式，默认为INDICATOR_ARROW_ROUND_STYLE
	 * 
	 * @param style
	 *            INDICATOR_USERGUIDE_STYLE或INDICATOR_ARROW_ROUND_STYLE
	 */
	public void setIndicateStyle(int style) {
		this.indicatorStyle = style;
	}

	/**
	 * 添加位置监听回调
	 * 
	 * @param onGuideListener
	 */
	public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
		if (onItemChangeListener == null) {
			throw new NullPointerException();
		}
		this.onItemChangeListener = onItemChangeListener;
	}

	/**
	 * 设置条目点击监听对象
	 * 
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	/**
	 * 显示
	 */
	public void show() {
		this.totelCount = viewList.size();
		final LayoutParams params = (LayoutParams) indicateLayout.getLayoutParams();
		if (INDICATE_USERGUIDE_STYLE == this.indicatorStyle) {// 操作指引
			params.bottomMargin = 45;
		}

		this.indicateLayout.setLayoutParams(params);
		this.indicateLayout.removeAllViews();
		// 初始化指示器
		for (int index = 0; index < this.totelCount; index++) {
			final View indicater = new ImageView(getContext());
			LinearLayout.LayoutParams indicaterLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			indicaterLayoutParams.setMargins(5, 0, 5, 0);
			indicater.setLayoutParams(indicaterLayoutParams);

			this.indicateLayout.addView(indicater, index);
		}
		if (refreshHandler != null) {
			refreshHandler.sendEmptyMessage(currentIndex);
		}
		// 为ViewPager配置数据
		pageAdapter = new MyPagerAdapter(this.viewList);
		this.viewPager.setAdapter(pageAdapter);
		this.viewPager.setCurrentItem(currentIndex, false);
	}

	/**
	 * 箭头点击事件处理
	 */
	private class ArrowClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			if (view == leftButton) {
				if (currentIndex >= (totelCount - 1)) {
					return;
				} else {
					viewPager.setCurrentItem(currentIndex + 1, true);
				}
			} else {
				if (totelCount <= 0) {
					return;
				} else {
					viewPager.setCurrentItem(currentIndex - 1, true);
				}
			}
		}
	}

	/**
	 * 页面变更监听
	 */
	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int index) {
			currentIndex = index;

			refreshHandler.sendEmptyMessage(index);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 设置图片轮播的当前Indicator焦点 2014-12-31 by ylq
	 */
	private void setIndicatorFocus() {
		if (totelCount > 0) {
			tv_pager.setText((currentIndex + 1) + "/" + totelCount);
		} else {
			tv_pager.setText("0/0");
		}
		if (indicatorStyle == INDICATE_PAGER_DELETE_STYLE) {
			iv_delete.setVisibility(View.VISIBLE);
		}

		if (indicatorStyle == INDICATE_PAGER_STYLE || indicatorStyle == INDICATE_PAGER_DELETE_STYLE) {
			tv_pager.setVisibility(View.VISIBLE);
			indicateLayout.setVisibility(View.GONE);
		} else {
			tv_pager.setVisibility(View.GONE);
			indicateLayout.setVisibility(View.VISIBLE);
			for (int index = 0; index < totelCount; index++) {
				final ImageView imageView = (ImageView) this.indicateLayout.getChildAt(index);
				if (this.currentIndex == index) {
					imageView.setImageResource(R.drawable.indicator_image_light);
				} else {
					imageView.setImageResource(R.drawable.indicator_image_dark);
				}
			}
		}
	}

	/**
	 * 刷新提示器
	 */
	protected void refreshIndicateView() {
		this.refreshTime = System.currentTimeMillis();

		setIndicatorFocus();

		if (INDICATE_USERGUIDE_STYLE == this.indicatorStyle || INDICATE_ROUND_STYLE == this.indicatorStyle
				|| INDICATE_PAGER_STYLE == this.indicatorStyle || INDICATE_PAGER_DELETE_STYLE == this.indicatorStyle) {// 操作指引或圆形列表或文本指示不显示箭头
			this.leftButton.setVisibility(View.GONE);
			this.rightButton.setVisibility(View.GONE);
		} else {// 显示箭头各状态
			if (totelCount <= 1) {
				leftButton.setVisibility(View.GONE);
				rightButton.setVisibility(View.GONE);
			} else if (totelCount == 2) {
				if (currentIndex == 0) {
					leftButton.setVisibility(View.VISIBLE);
					rightButton.setVisibility(View.GONE);
				} else {
					leftButton.setVisibility(View.GONE);
					rightButton.setVisibility(View.VISIBLE);
				}
			} else {
				if (currentIndex == 0) {
					leftButton.setVisibility(View.VISIBLE);
					rightButton.setVisibility(View.GONE);
				} else if (currentIndex == (totelCount - 1)) {
					leftButton.setVisibility(View.GONE);
					rightButton.setVisibility(View.VISIBLE);
				} else {
					leftButton.setVisibility(View.VISIBLE);
					rightButton.setVisibility(View.VISIBLE);
				}
			}
		}
		if (this.onItemChangeListener != null) {// 页面改更了
			try {
				this.onItemChangeListener.onPosition(this.currentIndex, this.totelCount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setIndicateBackgroundResource(int resid) {
		indicateLayout.setBackgroundResource(resid);
	}

	/**
	 * 设置文本指示器的位置<br/>
	 */
	public void setIndicateGravity(int verb1, int verb2) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(verb1);
		params.addRule(verb2);
		tv_pager.setLayoutParams(params);
	}

	public List<View> getViewList() {
		return viewList;
	}

	public View getItem(int position) {
		return viewList.get(position);
	}

	public void notifyDataSetChanged() {
		pageAdapter.notifyDataSetChanged();
	}

	public MyPagerAdapter getAdapter() {
		return pageAdapter;
	}

	public class MyPagerAdapter extends PagerAdapter {
		private List<View> pageViews = new ArrayList<View>();

		public MyPagerAdapter(List<View> pageViews) {
			this.pageViews = pageViews;
		}

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.setPrimaryItem(container, position, object);
		}
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_delete:
			int position = viewPager.getCurrentItem();
			ImageView imageView = (ImageView) viewList.get(position);
			Object tag = imageView.getTag();
			if (tag != null) {
				imageView.setTag(null);
				ImageUtils.rect(getContext(), "", imageView);
			} else {
				BaseActivity activity = (BaseActivity) getContext();
				activity.showToast("还未添加图片");
			}
			break;

		}
	}
}

class ScrollIndicateHandler extends Handler {
	private ImageIndicatorView scrollIndicateView;

	public ScrollIndicateHandler(ImageIndicatorView scrollIndicateView) {
		this.scrollIndicateView = scrollIndicateView;

	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (this.scrollIndicateView != null) {
			scrollIndicateView.refreshIndicateView();
		}
	}

}
