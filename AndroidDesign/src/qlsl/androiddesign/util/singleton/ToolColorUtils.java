package qlsl.androiddesign.util.singleton;

import qlsl.androiddesign.db.othertable.ToolColor;

public class ToolColorUtils {

	private static ToolColor toolColor;

	public static ToolColor get() {
		return toolColor;
	}

	public static void set(ToolColor toolColor) {
		ToolColorUtils.toolColor = toolColor;
	}

}
