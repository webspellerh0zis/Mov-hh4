package qlsl.androiddesign.http.service.commonservice;

import java.sql.SQLException;
import java.util.List;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.db.DatabaseHelper;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.singleton.ToolColorUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具中的调色板<br/>
 * 更改后重启软件生效<br/>
 * 
 */
public class ToolColorService extends BaseService {

	private static String className = getClassName(ToolColorService.class);

	/**
	 * 查询<br/>
	 */
	public static void queryToolColor(final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandlerWithShowing(functionView, listener, className, "queryToolColor");
		new HandlerThread(className, "queryToolColor") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					List<ToolColor> list = dbHelper.getDao(ToolColor.class).queryForAll();
					if (list.size() > 0) {
						ToolColor toolColor = list.get(0);

						if (ToolColorUtils.get() == null) {
							ToolColorUtils.set(toolColor);
						}
						handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, toolColor);
					} else {
						handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, null);
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
	public static void updateToolColor(final ToolColor toolColor, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "updateToolColor");
		new HandlerThread(className, "updateToolColor") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolColor.class).update(toolColor);

					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, "更新成功，重启软件生效[颜色，大小功能关闭]");
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
	public static void resetToolColor(final ToolColor toolColor, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "resetToolColor");
		new HandlerThread(className, "resetToolColor") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolColor.class).delete(toolColor);
					dbHelper.getDao(ToolColor.class).executeRawNoArgs(DatabaseHelper.sql_tool_color);

					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, "重置成功，重启软件生效");
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
}
