package qlsl.androiddesign.util.commonutil;

import java.util.HashMap;
import java.util.Locale;

public class ColorUtils {

	public static final int BLACK = 0xFF000000;
	public static final int DKGRAY = 0xFF444444;
	public static final int GRAY = 0xFF888888;
	public static final int LTGRAY = 0xFFCCCCCC;
	public static final int WHITE = 0xFFFFFFFF;
	public static final int RED = 0xFFFF0000;
	public static final int GREEN = 0xFF00FF00;
	public static final int BLUE = 0xFF0000FF;
	public static final int YELLOW = 0xFFFFFF00;
	public static final int CYAN = 0xFF00FFFF;
	public static final int MAGENTA = 0xFFFF00FF;
	public static final int TRANSPARENT = 0;

	private static final HashMap<String, Integer> sColorNameMap;

	static {
		sColorNameMap = new HashMap<String, Integer>();
		sColorNameMap.put("black", BLACK);
		sColorNameMap.put("darkgray", DKGRAY);
		sColorNameMap.put("gray", GRAY);
		sColorNameMap.put("lightgray", LTGRAY);
		sColorNameMap.put("white", WHITE);
		sColorNameMap.put("red", RED);
		sColorNameMap.put("green", GREEN);
		sColorNameMap.put("blue", BLUE);
		sColorNameMap.put("yellow", YELLOW);
		sColorNameMap.put("cyan", CYAN);
		sColorNameMap.put("magenta", MAGENTA);
		sColorNameMap.put("aqua", 0xFF00FFFF);
		sColorNameMap.put("fuchsia", 0xFFFF00FF);
		sColorNameMap.put("darkgrey", DKGRAY);
		sColorNameMap.put("grey", GRAY);
		sColorNameMap.put("lightgrey", LTGRAY);
		sColorNameMap.put("lime", 0xFF00FF00);
		sColorNameMap.put("maroon", 0xFF800000);
		sColorNameMap.put("navy", 0xFF000080);
		sColorNameMap.put("olive", 0xFF808000);
		sColorNameMap.put("purple", 0xFF800080);
		sColorNameMap.put("silver", 0xFFC0C0C0);
		sColorNameMap.put("teal", 0xFF008080);

	}

	/**
	 * 将十进制颜色转为"#AARRGGBB"格式String<br/>
	 */
	public static String toHexString(int color) {
		String hexColor = Integer.toHexString(color).toUpperCase();
		StringBuffer sb = new StringBuffer();
		for (int x = 0, count = 8 - hexColor.length(); x < count; x++) {
			sb.append("0");
		}
		return "#" + sb.toString() + hexColor;
	}

	/**
	 * 解析颜色字符串转为颜色值<br/>
	 * 无法识别则返回-1<br/>
	 */
	public static int parseColor(String colorString) {
		try {
			int length = colorString.length();
			if (length > 2) {
				if (colorString.charAt(0) == '#') {
					// Use a long to avoid rollovers on #ffXXXXXX
					long color = Long.parseLong(colorString.substring(1), 16);
					if (length == 7) {
						// Set the alpha value
						color |= 0x00000000ff000000;
					} else if (length != 9) {
						return -1;
					}
					return (int) color;
				} else {
					Integer color = sColorNameMap.get(colorString.toLowerCase(Locale.ROOT));
					if (color != null) {
						return color;
					}
				}
			}
		} catch (Exception e) {
		}
		return -1;
	}
}
