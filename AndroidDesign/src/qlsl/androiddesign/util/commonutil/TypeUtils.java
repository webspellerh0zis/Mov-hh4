package qlsl.androiddesign.util.commonutil;

public class TypeUtils {

	/**
	 * 获取动态类型<br/>
	 */
	public static String getDynamicType(Integer type) {
		String typeStr = "未知";
		switch (type) {
		case 0:
			typeStr = "考试";
			break;
		case 1:
			typeStr = "考试";
			break;
		case 2:
			typeStr = "通知";
			break;
		case 3:
			typeStr = "辅导";
			break;
		case 4:
			typeStr = "表扬";
			break;
		case 5:
			typeStr = "协同";
			break;
		}
		return typeStr;
	}

	/**
	 * 获取科目排序号用于排序<br/>
	 */
	public static int getSubjectSort(String subject) {
		int sort = 13;
		if (subject.equals("语文")) {
			sort = 1;
		} else if (subject.equals("数学")) {
			sort = 2;
		} else if (subject.equals("英语")) {
			sort = 3;
		} else if (subject.equals("物理")) {
			sort = 4;
		} else if (subject.equals("化学")) {
			sort = 5;
		} else if (subject.equals("生物")) {
			sort = 6;
		} else if (subject.equals("政治")) {
			sort = 7;
		} else if (subject.equals("历史")) {
			sort = 8;
		} else if (subject.equals("地理")) {
			sort = 9;
		} else if (subject.equals("计算机")) {
			sort = 10;
		} else if (subject.equals("体育")) {
			sort = 11;
		} else if (subject.equals("其它")) {
			sort = 12;
		}
		return sort;
	}
}
