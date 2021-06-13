package qlsl.androiddesign.http.service.commonservice;

import java.sql.SQLException;
import java.util.List;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.db.DatabaseHelper;
import qlsl.androiddesign.db.othertable.ToolSpeech;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.singleton.ToolSpeechUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具中的语音设置<br/>
 * 更改后马上生效<br/>
 * 
 */
public class ToolSpeechService extends BaseService {

	private static String className = getClassName(ToolSpeechService.class);

	/**
	 * 查询<br/>
	 */
	public static void queryToolSpeech(final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandlerWithShowing(functionView, listener,
				className, "queryToolSpeech");
		new HandlerThread(className, "queryToolSpeech") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					List<ToolSpeech> list = dbHelper.getDao(ToolSpeech.class)
							.queryForAll();

					if (list.size() > 0) {
						ToolSpeech toolSpeech = list.get(0);
						ToolSpeechUtils.set(toolSpeech);
						handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
								toolSpeech);
					} else {
						handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
								null);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}.start();
	}

	/**
	 * 更新<br/>
	 */
	public static void updateToolSpeech(final ToolSpeech toolSpeech,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener,
				className, "updateToolSpeech");
		new HandlerThread(className, "updateToolSpeech") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolSpeech.class).update(toolSpeech);

					ToolSpeechUtils.set(toolSpeech);

					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
							"更新成功");
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.activity.finish();
						}
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}.start();
	}

	/**
	 * 重置<br/>
	 */
	public static void resetToolSpeech(final ToolSpeech toolSpeech,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener,
				className, "resetToolSpeech");
		new HandlerThread(className, "resetToolSpeech") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolSpeech.class).delete(toolSpeech);
					dbHelper.getDao(ToolSpeech.class).executeRawNoArgs(
							DatabaseHelper.sql_tool_speech);

					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
							"重置成功");
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.activity.finish();
							queryToolSpeech(functionView, listener);
						}
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}.start();

	}
}
