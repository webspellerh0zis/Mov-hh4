package qlsl.androiddesign.util.commonutil;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;

public class MapUtils {

	private static MapInstalled mapInstalled;

	public static MapInstalled getMapInstalled() {
		return mapInstalled;
	}

	public static void init(final Context context) {
		if (mapInstalled == null) {
			mapInstalled = new MapInstalled();
			new Thread(new Runnable() {

				public void run() {
					List<PackageInfo> list = context.getPackageManager()
							.getInstalledPackages(0);
					for (PackageInfo packageInfo : list) {
						String packageName = packageInfo.packageName;
						if ("com.baidu.BaiduMap".equals(packageName)) {
							mapInstalled.setInstalledBaidu(true);
						} else if ("com.autonavi.minimap".equals(packageName)) {
							mapInstalled.setInstalledGaode(true);
						}
					}
				}
			}).start();
		}
	}

	public static class MapInstalled {

		boolean isInstalledBaidu;

		boolean isInstalledGaode;

		public boolean isInstalledBaidu() {
			return isInstalledBaidu;
		}

		public void setInstalledBaidu(boolean isInstalledBaidu) {
			this.isInstalledBaidu = isInstalledBaidu;
		}

		public boolean isInstalledGaode() {
			return isInstalledGaode;
		}

		public void setInstalledGaode(boolean isInstalledGaode) {
			this.isInstalledGaode = isInstalledGaode;
		}

	}
}
