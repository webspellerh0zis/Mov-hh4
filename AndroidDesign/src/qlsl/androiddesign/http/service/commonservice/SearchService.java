package qlsl.androiddesign.http.service.commonservice;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import qlsl.androiddesign.constant.SoftwareConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.entity.commonentity.Pager;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.PagerUtils;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 搜索模块 <br/>
 * 
 */
public class SearchService extends BaseService {

	private static String className = getClassName(SearchService.class);

	public static void searchGroup(final String keyword, final int type, final int page,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "searchGroup");
		new HandlerThread(className, "searchGroup") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("group_search").addParam("keyword", keyword)
							.addParam("type", type).addParam("page", page).addParam("size", SoftwareConstant.PAGER_SIZE)
							.get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					HashMap<String, Object> map = new HashMap<String, Object>();
					List<Group> list = JSONArray.parseArray(jo.getString("data"), Group.class);
					Pager pager = PagerUtils.createPager(page, jo);
					map.put("list", list);
					map.put("pager", pager);

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, map);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}
}
