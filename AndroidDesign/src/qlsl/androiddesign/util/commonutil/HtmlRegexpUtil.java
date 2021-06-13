package qlsl.androiddesign.util.commonutil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;
import android.util.Log;
import qlsl.androiddesign.constant.UrlConstant;

public class HtmlRegexpUtil {
	private final static String regxpForHtml = "<([^>]*)>";

	private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>";

	private final static List<String> allowTags = Arrays.asList(
			new String[] { "img", "strong", "br", "sub", "sup", "u", "table", "tbody", "tr", "td", "span", "em" });
	private final static List<String> hasTags = Arrays
			.asList(new String[] { "img", "strong", "sub", "sup", "u", "table", "tbody", "tr", "td", "span", "em" });

	private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\"";

	/**
	 * js渲染所需脚本
	 */
	private static final String LatexScript = "<script src=\"" + UrlConstant.STATIC_SERVER
			+ "/web_formula/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script><script>(function () {MathJax.Hub.Config({messageStyle: 'none',menuSettings: { zoom: '' },showMathMenu: false,showMathMenuMSIE: false,tex2jax: {inlineMath: [[\"\\\\(\", \"\\\\)\"],[\"\\\\[\", \"\\\\]\"],[\"\\\\$\", \"\\\\$\"]]}});MathJax && MathJax.Hub.Typeset()})()</script>";

	// http://static.dayez.net/web_formula/MathJax.js
	/**
	 * html模板
	 */
	private static final String htmlTemp = "<!DOCTYPE html><head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1\" /><title></title><style type=\"text/css\">div img{vertical-align: middle;margin-left:10px;}div .MathJax_Display{display: inline;} strong{font-weight: bold;} </style></head><body><div style=\"font-size:%spx\">%s</div></body></html>";

	// private static final String htmlTemp =
	// "<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"utf-8\"
	// /><meta name=\"renderer\" content=\"webkit\" /><meta name=\"viewport\"
	// content=\"width=device-width,initial-scale=1,maximum-scale=1\"
	// /><title></title><style type=\"text/css\">div img{vertical-align:
	// middle;margin-left:10px;}div .MathJax_Display{display:
	// inline;}</style></head><body><div
	// style=\"font-size:%spx\">%s</div></body></html>";

	/**
	 * 替换特殊字符[>,<,",&]<br/>
	 * 耗时操作<br/>
	 */
	public static String replaceSpecialChar(String input) {
		StringBuffer output = new StringBuffer(input.length());
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
			case '<':
				output.append("&lt;");
				break;
			case '>':
				output.append("&gt;");
				break;
			case '"':
				output.append("&quot;");
				break;
			case '&':
				output.append("&amp;");
				break;
			default:
				output.append(c);
				break;
			}

		}
		return (output.toString());
	}

	/**
	 * 判断是否包含特殊字符[>,<,",&]<br/>
	 * 耗时操作<br/>
	 */
	public static boolean hasSpecialChar(String input) {
		boolean flag = false;
		if (!TextUtils.isEmpty(input)) {
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String replaceHtmlTag(String str, String beforeTag, String tagAttrib, String startTag,
			String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 剔除文字中<img>标签
	 * 
	 */
	public static String filterHtmlimg(String str) {
		Pattern pattern = Pattern.compile(regxpForImgTag);
		Matcher matcher = pattern.matcher(str);
		String s = null;
		boolean result1 = matcher.find();
		while (result1) {
			s = matcher.group(1);
			result1 = matcher.find();
		}
		return s;
	}

	public static String filterHtmsrc(String str) {
		Pattern pattern = Pattern.compile(regxpForImaTagSrcAttrib);
		Matcher matcher = pattern.matcher(str);
		String d = null;
		boolean result1 = matcher.find();
		while (result1) {
			d = matcher.group(1);
			result1 = matcher.find();
		}
		return d;
	}

	/**
	 * 题干Html代码过滤及其处理
	 *
	 */
	public static String formatBody(String body) {
		body = clearHtml(body);
		body = formatImage(body);
		return body.replaceAll("&nbsp;", " ");
	}

	/**
	 * 清除标签
	 *
	 */
	public static String clearHtml(String body) {
		String regex = "</?([a-zA-Z0-9]+)[^>]*>";
		Matcher matcher = Pattern.compile(regex).matcher(body);
		while (matcher.find()) {
			String tag = matcher.group(1);
			if (tag != null && allowTags.contains(tag.toLowerCase()))
				continue;
			body = body.replace(matcher.group(), "");
		}
		return body.replaceAll("<table>", "<table border=\"1\">");
	}

	/**
	 * 图片路径处理
	 *
	 */
	public static String formatImage(String body) {
		String regex = "<img[^<]+src=[\'\"]([^\'\"]+?)[\'\"][^>]*>";
		Matcher matcher = Pattern.compile(regex).matcher(body);
		while (matcher.find()) {
			String src = matcher.group(1);
			body = body.replace(src, "" + src);
		}
		return body;
	}

	/**
	 * 处理公式解析
	 *
	 * @param body
	 *            html源代码
	 * @param height
	 *            图片高度(js渲染时无效)
	 * @param isJsLoad
	 *            是否使用js渲染
	 * @return 解析之后的Html代码
	 */
	public static String findLatex(String body, int height, boolean isJsLoad) {
		String regex = "\\\\[\\[\\(\\$]([\\w\\W]+)\\\\[\\]\\)\\$]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(body);
		boolean isMatch = matcher.find();
		Log.i("body", body);
		Log.i("isMatch", isMatch ? "true" : "false");
		if (!isMatch)
			return body += LatexScript;
		if (isJsLoad) {
			body += LatexScript;
		} else {
			String img = "<img style=\"height:%spx\" src=\"%s\" />";
			Matcher imgMatcher = pattern.matcher(body);
			while (imgMatcher.find()) {
				String temp1 = imgMatcher.group(1);
				String temp = null;
				try {
					temp = URLEncoder.encode(temp1, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				body = body.replace(imgMatcher.group(),
						String.format(img, height, "http://api.dayez.net/question/latex?latex=" + temp));
			}
		}
		return body;
	}

	/**
	 * 判断String中是否有公式
	 * 
	 */
	public static boolean isLatex(String body) {
		String regex = "\\\\[\\[\\(\\$]([\\w\\W]+)\\\\[\\]\\)\\$]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(body);
		boolean isMatch = matcher.find();
		return isMatch;
	}

	public static boolean isHtml(String body) {
		String regex = "</?([a-zA-Z0-9]+)[^>]*>";
		Matcher matcher = Pattern.compile(regex).matcher(body);
		while (matcher.find()) {
			String tag = matcher.group(1);
			if (tag != null && hasTags.contains(tag.toLowerCase())) {
				return true;
			} else {
			}
		}
		return false;
	}

	/**
	 * 处理公式解析
	 *
	 */
	public static String findLatex(String body, int height) {
		return findLatex(body, height, false);
	}

	/**
	 * 处理公式解析
	 *
	 * @param body
	 *            html源代码
	 * @param isJsLoad
	 *            是否使用js渲染
	 * @return 解析之后的Html
	 */
	public static String findLatex(String body, boolean isJsLoad) {
		return findLatex(body, 20, isJsLoad);
	}

	/**
	 * html格式化处理 包括去标签(除img)、处理图片链接、公式解析以及控制字体大小
	 *
	 * @param html
	 *            html源代码
	 * @param fontSize
	 *            字体大小
	 * @param loadLatex
	 *            是否加载公式(需要javascript支持)
	 * @return 格式化之后的html代码
	 */
	public static String htmlFormat(String html, int fontSize, boolean loadLatex) {
		html = formatBody(html);
		if (loadLatex) {
			html = findLatex(html, fontSize, true);
		}
		html = String.format(htmlTemp, fontSize, html);
		Log.i("html", html);
		return html;
	}

	/**
	 * html格式化处理(加载公式) 包括去标签(除img)、处理图片链接、公式解析以及控制字体大小
	 *
	 * @param html
	 *            html源代码
	 * @param fontSize
	 *            字体大小
	 * @return 格式化之后的html代码
	 */
	public static String htmlFormat(String html, int fontSize) {
		return htmlFormat(html, fontSize, true);
	}

	/**
	 * html格式化处理(加载公式) 包括去标签(除img)、处理图片链接、公式解析以及控制字体大小
	 *
	 * @param html
	 *            html源代码
	 * @param loadLatex
	 *            是否加载公式(需要javascript支持)
	 * @return 格式化之后的html代码
	 */
	public static String htmlFormat(String html, boolean loadLatex) {
		return htmlFormat(html, 22, loadLatex);
	}

	public static String htmlImplant(String html) {
		return htmlFormat(html, 22);
	}

	public static String htmlAnswerImplant(String html) {
		return htmlFormat(html, 18);
	}

	public static String htmlAnswer(String html) {
		return htmlFormat(html, 15);
	}

	/**
	 * 找到图片后缀的正则
	 * 
	 */
	public static String replaceAllPNG(String str) {
		String regex = "^.*?\\.(jpg|jpeg|bmp|gif)$ ";
		String replace = ".jpg";
		Pattern patregex = Pattern.compile(regex);
		Pattern patreplace = Pattern.compile(replace);
		Matcher matcher = patregex.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcher.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patreplace.matcher(matcher.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, matcherForAttrib.group(1));
			}
			matcher.appendReplacement(sb, sbreplace.toString());
			result = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
