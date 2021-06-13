package qlsl.androiddesign.http.service.commonservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Debug;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.entity.commonentity.ProcessInfo;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 进程模块 <br/>
 * 
 */
public class ProcessService extends BaseService {

	private static String className = getClassName(ProcessService.class);

	/**
	 * 查询<br/>
	 */
	@SuppressLint("NewApi")
	public static void queryProcessList(final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在查询");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryProcessList");
		new HandlerThread(className, "queryProcessList") {
			public void run() {
				try {
					List<ProcessInfo> list = new ArrayList<ProcessInfo>();

					ActivityManager manager = (ActivityManager) functionView.activity
							.getSystemService(Context.ACTIVITY_SERVICE);
					List<RunningAppProcessInfo> appProcessList = manager.getRunningAppProcesses();

					for (RunningAppProcessInfo appProcessInfo : appProcessList) {
						int pid = appProcessInfo.pid;
						int uid = appProcessInfo.uid;
						String processName = appProcessInfo.processName;
						String[] pkgList = appProcessInfo.pkgList;
						int[] myMempid = new int[] { pid };
						Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(myMempid);
						int memSize = memoryInfo[0].dalvikPrivateDirty;

						ProcessInfo processInfo = new ProcessInfo();
						processInfo.setPid(pid);
						processInfo.setUid(uid);
						processInfo.setProcessName(processName);
						processInfo.setPkgList(pkgList);
						processInfo.setMemSize(memSize);
						processInfo.setImportance(appProcessInfo.importance);
						processInfo.setImportanceReasonCode(appProcessInfo.importanceReasonCode);
						if (appProcessInfo.importanceReasonComponent != null) {
							processInfo.setComponentPkg(appProcessInfo.importanceReasonComponent.getPackageName());
							processInfo.setComponentClass(appProcessInfo.importanceReasonComponent.getClassName());
						} else {
							processInfo.setComponentPkg("未知");
							processInfo.setComponentClass("未知");
						}
						processInfo.setImportanceReasonPid(appProcessInfo.importanceReasonPid);
						if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
							processInfo.setLastTrimLevel(appProcessInfo.lastTrimLevel + "");
						} else {
							processInfo.setLastTrimLevel("未知");
						}
						processInfo.setLru(appProcessInfo.lru);
						processInfo.setDescribeContents(appProcessInfo.describeContents());
						processInfo.setHashCode(appProcessInfo.hashCode());
						processInfo.setTransmitFlow(TrafficStats.getUidTxBytes(uid));
						processInfo.setReceiveFlow(TrafficStats.getUidRxBytes(uid));

						list.add(processInfo);
					}

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 搜索<br/>
	 */
	@SuppressLint("NewApi")
	public static void queryProcessBySearchKey(final String key, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在搜索");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryProcessBySearchKey");
		new HandlerThread(className, "queryProcessBySearchKey") {
			public void run() {
				try {
					List<ProcessInfo> list = new ArrayList<ProcessInfo>();

					ActivityManager manager = (ActivityManager) functionView.activity
							.getSystemService(Context.ACTIVITY_SERVICE);
					List<RunningAppProcessInfo> appProcessList = manager.getRunningAppProcesses();

					for (RunningAppProcessInfo appProcessInfo : appProcessList) {
						int pid = appProcessInfo.pid;
						int uid = appProcessInfo.uid;
						String processName = appProcessInfo.processName;
						String[] pkgList = appProcessInfo.pkgList;
						int[] myMempid = new int[] { pid };
						Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(myMempid);
						int memSize = memoryInfo[0].dalvikPrivateDirty;

						String pidStr = pid + "";
						String uidStr = uid + "";
						String memSizeStr = memSize + "";
						if (pidStr.contains(key) || uidStr.contains(key) || memSizeStr.contains(key)
								|| processName.contains(key)) {
							ProcessInfo processInfo = new ProcessInfo();
							processInfo.setPid(pid);
							processInfo.setUid(uid);
							processInfo.setProcessName(processName);
							processInfo.setPkgList(pkgList);
							processInfo.setMemSize(memSize);
							processInfo.setImportance(appProcessInfo.importance);
							processInfo.setImportanceReasonCode(appProcessInfo.importanceReasonCode);
							if (appProcessInfo.importanceReasonComponent != null) {
								processInfo.setComponentPkg(appProcessInfo.importanceReasonComponent.getPackageName());
								processInfo.setComponentClass(appProcessInfo.importanceReasonComponent.getClassName());
							} else {
								processInfo.setComponentPkg("未知");
								processInfo.setComponentClass("未知");
							}
							processInfo.setImportanceReasonPid(appProcessInfo.importanceReasonPid);
							if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
								processInfo.setLastTrimLevel(appProcessInfo.lastTrimLevel + "");
							} else {
								processInfo.setLastTrimLevel("未知");
							}
							processInfo.setLru(appProcessInfo.lru);
							processInfo.setDescribeContents(appProcessInfo.describeContents());
							processInfo.setHashCode(appProcessInfo.hashCode());
							processInfo.setTransmitFlow(TrafficStats.getUidTxBytes(uid));
							processInfo.setReceiveFlow(TrafficStats.getUidRxBytes(uid));

							list.add(processInfo);
						}

					}

					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 排序<br/>
	 */
	@SuppressLint("NewApi")
	public static void queryProcessByOrderKey(final List<Integer> selectPositions, final String key,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在排序");
		final HttpHandler handler = getHandler(functionView, listener, className, "queryProcessByOrderKey");
		new HandlerThread(className, "queryProcessByOrderKey") {
			public void run() {
				try {
					List<ProcessInfo> list = new ArrayList<ProcessInfo>();

					ActivityManager manager = (ActivityManager) functionView.activity
							.getSystemService(Context.ACTIVITY_SERVICE);
					List<RunningAppProcessInfo> appProcessList = manager.getRunningAppProcesses();

					for (RunningAppProcessInfo appProcessInfo : appProcessList) {
						int pid = appProcessInfo.pid;
						int uid = appProcessInfo.uid;
						String processName = appProcessInfo.processName;
						String[] pkgList = appProcessInfo.pkgList;
						int[] myMempid = new int[] { pid };
						Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(myMempid);
						int memSize = memoryInfo[0].dalvikPrivateDirty;

						String pidStr = pid + "";
						String uidStr = uid + "";
						String memSizeStr = memSize + "";
						if (pidStr.contains(key) || uidStr.contains(key) || memSizeStr.contains(key)
								|| processName.contains(key)) {
							ProcessInfo processInfo = new ProcessInfo();
							processInfo.setPid(pid);
							processInfo.setUid(uid);
							processInfo.setProcessName(processName);
							processInfo.setPkgList(pkgList);
							processInfo.setMemSize(memSize);
							processInfo.setImportance(appProcessInfo.importance);
							processInfo.setImportanceReasonCode(appProcessInfo.importanceReasonCode);
							if (appProcessInfo.importanceReasonComponent != null) {
								processInfo.setComponentPkg(appProcessInfo.importanceReasonComponent.getPackageName());
								processInfo.setComponentClass(appProcessInfo.importanceReasonComponent.getClassName());
							} else {
								processInfo.setComponentPkg("未知");
								processInfo.setComponentClass("未知");
							}
							processInfo.setImportanceReasonPid(appProcessInfo.importanceReasonPid);
							if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
								processInfo.setLastTrimLevel(appProcessInfo.lastTrimLevel + "");
							} else {
								processInfo.setLastTrimLevel("未知");
							}
							processInfo.setLru(appProcessInfo.lru);
							processInfo.setDescribeContents(appProcessInfo.describeContents());
							processInfo.setHashCode(appProcessInfo.hashCode());
							processInfo.setTransmitFlow(TrafficStats.getUidTxBytes(uid));
							processInfo.setReceiveFlow(TrafficStats.getUidRxBytes(uid));

							list.add(processInfo);
						}

					}

					Comparator<ProcessInfo> comparator = new Comparator<ProcessInfo>() {

						public int compare(ProcessInfo process1, ProcessInfo process2) {
							int pidPos = selectPositions.get(0);
							int uidPos = selectPositions.get(1);
							int memPos = selectPositions.get(2);
							int processPos = selectPositions.get(3);

							int pid1 = process1.getPid();
							int uid1 = process1.getUid();
							int mem1 = process1.getMemSize();
							int pid2 = process2.getPid();
							int uid2 = process2.getUid();
							int mem2 = process2.getMemSize();

							int pidResult = pidPos == 0 ? 0 : (pid1 == pid2 ? 0 : (pid1 > pid2 ? 1 : -1));
							int uidResult = uidPos == 0 ? 0 : (uid1 == uid2 ? 0 : (uid1 > uid2 ? 1 : -1));
							int memResult = memPos == 0 ? 0 : (mem1 == mem2 ? 0 : (mem1 > mem2 ? 1 : -1));
							int processResult = processPos == 0 ? 0
									: (process1.getProcessName()).compareTo(process2.getProcessName());

							int result = processPos != 0 ? processResult
									: (memPos != 0 ? memResult : (uidPos != 0 ? uidResult : pidResult));
							return result;
						}
					};

					int pidPos = selectPositions.get(0);
					int uidPos = selectPositions.get(1);
					int memPos = selectPositions.get(2);
					int processPos = selectPositions.get(3);
					int validPos = processPos != 0 ? processPos
							: (memPos != 0 ? memPos : (uidPos != 0 ? uidPos : pidPos));

					if (validPos == 1) {
						Collections.sort(list, comparator);
					} else if (validPos == 2) {
						Collections.sort(list, comparator);
						Collections.reverse(list);
					}
					handler.sendMessage(WhatConstant.WHAT_OTHER_DATA_SUCCESS, list);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}
}
