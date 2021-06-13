package qlsl.androiddesign.view.subview.commonview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.qlsl.androiddesign.appname.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import qlsl.androiddesign.activity.commonactivity.EditActivity;
import qlsl.androiddesign.activity.commonactivity.MemberInfoActivity;
import qlsl.androiddesign.adapter.commonadapter.MemberInfoAdapter;
import qlsl.androiddesign.constant.IntentCodeConstant;
import qlsl.androiddesign.http.service.commonservice.MemberService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.http.xutils.Protocol.FileType;
import qlsl.androiddesign.listener.OnUploadFileListener;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.util.commonutil.FileNameUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 个人信息页<br/>
 * 需要传入的键：<br/>
 * 传入的值类型： <br/>
 * 传入的值含义：<br/>
 * 是否必传 ：
 */
public class MemberInfoView extends FunctionView<MemberInfoActivity> {

	private ListView listView;

	public MemberInfoView(MemberInfoActivity activity) {
		super(activity);
		setContentView(R.layout.activity_member_info_new);
	}

	public void onResume() {
		setListViewData();
	}

	protected void initView(View view) {
		setTitle("个人信息");
		listView = findViewById(R.id.listView);
	}

	protected void initData() {

	}

	protected void initListener() {

	}

	public <T> void showData(T... t) {

	}

	public <T> void showNoData(T... t) {

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.list_item:
			doClickListItem(view);
			break;
		}
	}

	public void setListViewData() {
		String[] names = null;
		String[] values = null;
		String photoUrl = UserMethod.getUser().getAvatar();
		String nickName = UserMethod.getUser().getNick();
		nickName = nickName == null ? "未设置" : nickName;

		if (UserMethod.isTeacher()) {
			names = new String[] { "头像", "昵称" };
			values = new String[] { photoUrl, nickName };
		} else if (UserMethod.isStudent()) {
			names = new String[] { "头像", "昵称", "家长" };
			values = new String[] { photoUrl, nickName, null };
		} else if (UserMethod.isParent()) {
			names = new String[] { "头像", "昵称", "学生" };
			// values = new String[] { photoUrl, nickName, null };
			values = new String[] { photoUrl, nickName, "绑定" };
		}

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int index = 0; index < names.length; index++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text", names[index]);
			map.put("value", values[index]);
			list.add(map);
		}
		MemberInfoAdapter adapter = new MemberInfoAdapter(activity, list);
		listView.setAdapter(adapter);
	}

	private void doClickListItem(View view) {
		int position = listView.getPositionForView(view);
		if (position == 0) {
			Intent intent = new Intent(activity, PhotoSelectorActivity.class);
			intent.putExtra(PhotoSelectorActivity.KEY_MAX, 1);
			intent.putExtra(PhotoSelectorActivity.KEY_SECOND_DIR, FileNameUtils.getAccountSecondDir());
			intent.putExtra(PhotoSelectorActivity.KEY_PREFIX_NAME, FileNameUtils.getAccountPrefixName(activity));
			startActivityForResult(intent, IntentCodeConstant.REQUEST_CODE_PHOTO_PICKED);
		} else if (position == 1) {
			TextView tv_value = (TextView) listView.getChildAt(1).findViewById(R.id.tv_value);
			Intent intent = new Intent(activity, EditActivity.class);
			intent.putExtra("title", "编辑昵称");
			intent.putExtra("content", tv_value.getText().toString());
			intent.putExtra("maxLength", 10);
			startActivity(intent);
		} else if (position == 2) {
			TextView tv_text = (TextView) listView.getChildAt(1).findViewById(R.id.tv_text);
		

		}
	}

	@SuppressWarnings("unchecked")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentCodeConstant.REQUEST_CODE_PHOTO_PICKED) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					showToast("无法加载图片");
					return;
				}
				List<PhotoModel> selectPhotos = (List<PhotoModel>) bundle
						.getSerializable(PhotoSelectorActivity.KEY_RESULT);
				if (selectPhotos.size() > 0) {
					String path = selectPhotos.get(0).getOriginalPath();
					cropPhoto(path);
				}
			}
		} else if (requestCode == IntentCodeConstant.CROP_PHOTO) {
			if (data == null) {
				return;
			}
			Bitmap bitmap = data.getExtras().getParcelable("data");
			File file = saveBitmapToFile(bitmap);
			new HttpProtocol().uploadFile(file, FileType.PICTURE, listener);
		}
	};

	private OnUploadFileListener listener = new OnUploadFileListener() {

		public void onSuccess(List<String> urls, FileType type) {
			MemberService.updateHeadPhoto(urls.get(0), MemberInfoView.this, activity);
		}
	};

}
