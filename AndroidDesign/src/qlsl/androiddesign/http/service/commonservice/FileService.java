package qlsl.androiddesign.http.service.commonservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.BitmapCompressor;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 文件模块 <br/>
 * 
 */
public class FileService extends BaseService {

	private static String className = getClassName(FileService.class);

	/**
	 * 压缩文件<br/>
	 * 宽高压缩+质量压缩<br/>
	 * 暂时不能限定到指定文件大小(KB)以内，待优化<br/>
	 */
	public static void compressFiles(final List<File> pictureFiles, final String secondDir, final String suffix,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在压缩文件");
		final HttpHandler handler = getHandlerWithShowing(functionView, listener, className, "compressFiles");
		new HandlerThread(className, "compressFiles") {
			public void run() {
				List<File> compressFiles = new ArrayList<File>();
				for (int index = 0, size = pictureFiles.size(); index < size; index++) {
					File file = pictureFiles.get(index);
					Bitmap sampleBitmap = BitmapCompressor.decodeSampledBitmapFromFile(file.getAbsolutePath());
					Bitmap compressBitmap = BitmapCompressor.compressBitmap(sampleBitmap);
					File compressFile = BitmapCompressor.copyDataToFile(functionView.activity, compressBitmap,
							secondDir, suffix + index);
					compressFiles.add(compressFile);
				}
				handler.sendMessage(WhatConstant.WHAT_DB_DATA_SUCCESS, compressFiles);
			};
		}.start();
	}
}
