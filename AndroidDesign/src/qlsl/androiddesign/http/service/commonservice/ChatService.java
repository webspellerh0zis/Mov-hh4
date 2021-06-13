package qlsl.androiddesign.http.service.commonservice;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.db.DatabaseHelper;
import qlsl.androiddesign.db.othertable.ChatMessage;
import qlsl.androiddesign.db.othertable.ChatQueue;
import qlsl.androiddesign.entity.commonentity.ReceiveMessage;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 消息模块 <br/>
 * 
 */
public class ChatService extends BaseService {

	private static String className = getClassName(ChatService.class);

	/**
	 * 查询聊天列表,并以时间倒序排列<br/>
	 * 暂不实现分页加载效果<br/>
	 */
	public synchronized static void queryChatQueueList(final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandlerWithoutShowing(functionView, listener, className, "queryChatQueueList");
		new HandlerThread(className, "queryChatQueueList") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					List<ChatQueue> list = dbHelper.getDao(ChatQueue.class).queryBuilder().orderBy("updateTime", false)
							.where().eq("queueStatus", 0).and().eq("userId", UserMethod.getUser().getId()).query();
					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, list);
				} catch (SQLException e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 接收新消息,并自动更新到队列表，消息表<br/>
	 */
	public static void receiveNewMessage(final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandlerWithoutShowing(functionView, listener, className, "receiveNewMessage");
		new HandlerThread(className, "receiveNewMessage") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					JSONObject jo = protocol.setMethod("message.receive").get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					final List<ReceiveMessage> receiveMsgs = JSONArray.parseArray(jo.getString("data"),
							ReceiveMessage.class);
					for (ReceiveMessage receiveMsg : receiveMsgs) {
						List<ChatQueue> quenes = dbHelper.getDao(ChatQueue.class).queryBuilder().where()
								.eq("userId", UserMethod.getUser().getId()).and()
								.eq("senderId", receiveMsg.getSender_id()).and().eq("queueType", receiveMsg.getType())
								.query();
						ChatQueue chatQueue = null;
						if (quenes.size() == 0) {
							chatQueue = new ChatQueue();
							chatQueue.setUpdateTime(receiveMsg.getSend_time());
							chatQueue.setNewMessage(receiveMsg.getContent());
							chatQueue.setUnReadMessageNo(1);
							chatQueue.setUserId(UserMethod.getUser().getId());
							chatQueue.setSenderId(receiveMsg.getSender_id() + "");
							chatQueue.setSenderName(receiveMsg.getSender());
							chatQueue.setQueueType(receiveMsg.getType());
							chatQueue.setQueueStatus(0);
							dbHelper.getDao(ChatQueue.class).create(chatQueue);

							// 待设置头像
						} else {
							chatQueue = quenes.get(0);
							chatQueue.setUpdateTime(receiveMsg.getSend_time());
							chatQueue.setNewMessage(receiveMsg.getContent());
							chatQueue.setUnReadMessageNo(chatQueue.getUnReadMessageNo() + 1);
							chatQueue.setQueueStatus(0);
							dbHelper.getDao(ChatQueue.class).update(chatQueue);
						}

						ChatMessage chatMessage = new ChatMessage();
						chatMessage.setChatQueue(chatQueue);
						chatMessage.setMessageContent(receiveMsg.getContent());
						chatMessage.setMessageType(0);
						chatMessage.setSendTime(receiveMsg.getSend_time());
						chatMessage.setSenderId(receiveMsg.getSender_id() + "");
						chatMessage.setSenderName(receiveMsg.getSender());
						dbHelper.getDao(ChatMessage.class).create(chatMessage);
					}

					Thread.sleep(5000);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							if (receiveMsgs.size() != 0) {
								queryChatQueueList(functionView, listener);
							}

							receiveNewMessage(functionView, listener);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 删除聊天队列<br/>
	 * 假删除，仅将状态置为不显示状态<br/>
	 * 有新消息时重置为显示状态<br/>
	 */
	public static void deleteChatQuene(final ChatQueue chatQueue, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandlerWithoutShowing(functionView, listener, className, "deleteChatQuene");
		new HandlerThread(className, "deleteChatQuene") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					chatQueue.setQueueStatus(4);
					dbHelper.getDao(ChatQueue.class).update(chatQueue);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.showToast("删除成功");
							queryChatQueueList(functionView, listener);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}
}
