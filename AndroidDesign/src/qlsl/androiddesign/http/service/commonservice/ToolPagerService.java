package qlsl.androiddesign.http.service.commonservice;

import java.sql.SQLException;
import java.util.List;

import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.db.DatabaseHelper;
import qlsl.androiddesign.db.othertable.ToolPager;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.singleton.ToolPagerUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 工具中的分页设置<br/>
 * 更改后马上生效<br/>
 * 
 */
public class ToolPagerService extends BaseService {

	private static String className = getClassName(ToolPagerService.class);

	/**
	 * 查询<br/>
	 */
	public static void queryToolPager(final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandlerWithShowing(functionView,
				listener, className, "queryToolPager");
		new HandlerThread(className, "queryToolPager") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					List<ToolPager> list = dbHelper.getDao(ToolPager.class)
							.queryForAll();

					if (list.size() > 0) {
						ToolPager toolPager = list.get(0);
						ToolPagerUtils.set(toolPager);
						handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
								toolPager);
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
	public static void updateToolPager(final ToolPager toolPager,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener,
				className, "updateToolPager");
		new HandlerThread(className, "updateToolPager") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolPager.class).update(toolPager);

					ToolPagerUtils.set(toolPager);

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
	public static void resetToolPager(final ToolPager toolPager,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener,
				className, "resetToolPager");
		new HandlerThread(className, "resetToolPager") {
			public void run() {
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
				try {
					dbHelper.getDao(ToolPager.class).delete(toolPager);
					dbHelper.getDao(ToolPager.class).executeRawNoArgs(
							DatabaseHelper.sql_tool_pager);

					handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS,
							"重置成功");
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.activity.finish();
							queryToolPager(functionView, listener);
						}
					});
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}.start();

	}
}
