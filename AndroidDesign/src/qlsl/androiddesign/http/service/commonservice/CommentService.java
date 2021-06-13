package qlsl.androiddesign.http.service.commonservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import qlsl.androiddesign.constant.SoftwareConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.entity.commonentity.Comment;
import qlsl.androiddesign.entity.commonentity.CommentReply;
import qlsl.androiddesign.entity.commonentity.Pager;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.PagerUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 评论模块 <br/>
 * 
 */
public class CommentService extends BaseService {

	private static String className = getClassName(CommentService.class);

	/**
	 * 查询评论列表<br/>
	 */
	public static void queryCommentList(final int page, final String dynamicId, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在查询评论");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryCommentList");
		new HandlerThread(className, "queryCommentList") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("comment_list").addParam("sourceId", dynamicId)
							.addParam("page", page + "").addParam("size", SoftwareConstant.PAGER_SIZE).get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					HashMap<String, Object> map = new HashMap<String, Object>();
					JSONObject data = jo.getJSONObject("data");

					Map<String, User> users = JSON.parseObject(data.getString("users"),
							new TypeReference<Map<String, User>>() {
					});

					List<Comment> list = JSONArray.parseArray(data.getString("comments"), Comment.class);
					for (Comment item : list) {
						item.setUsers(users);
					}

					ArrayList<HashMap<String, Comment>> groupList = new ArrayList<HashMap<String, Comment>>();
					ArrayList<ArrayList<HashMap<String, CommentReply>>> childList = new ArrayList<ArrayList<HashMap<String, CommentReply>>>();
					for (Comment group : list) {
						HashMap<String, Comment> map_group = new HashMap<String, Comment>();
						map_group.put("group", group);
						groupList.add(map_group);

						List<CommentReply> groupChildList = group.getReplys();
						ArrayList<HashMap<String, CommentReply>> currentChildList = new ArrayList<HashMap<String, CommentReply>>();

						for (CommentReply child : groupChildList) {
							HashMap<String, CommentReply> map_child = new HashMap<String, CommentReply>();
							map_child.put("child", child);
							currentChildList.add(map_child);
						}

						childList.add(currentChildList);
					}

					Pager pager = PagerUtils.createPager(page, data.getInteger("commentCount"));
					map.put("groupList", groupList);
					map.put("childList", childList);
					map.put("pager", pager);

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, map);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 评论或回复评论<br/>
	 * replyId:[null,非null]代表[评论，回复]<br/>
	 * sourceId为源id,非发布号<br/>
	 */
	public static void createComment(final String sourceId, final String message, final String replyId,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在发布评论");
		final HttpHandler handler = getHandlerWithShowing(functionView, listener, className, "createComment");
		new HandlerThread(className, "createComment") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("comment_send").addParam("sourceId", sourceId)
							.addParam("message", message).addParam("replyId", replyId).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							if (replyId == null) {
								functionView.showToast("评论成功");
							} else {
								functionView.showToast("回复成功");
							}
							queryCommentList(0, sourceId, functionView, listener);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}
}
