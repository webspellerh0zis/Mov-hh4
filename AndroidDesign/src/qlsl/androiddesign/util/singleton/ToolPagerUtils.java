package qlsl.androiddesign.util.singleton;

import qlsl.androiddesign.db.othertable.ToolPager;

public class ToolPagerUtils {

	private static ToolPager toolPager;

	public static ToolPager get() {
		return toolPager;
	}

	public static void set(ToolPager toolPager) {
		ToolPagerUtils.toolPager = toolPager;
	}

}
