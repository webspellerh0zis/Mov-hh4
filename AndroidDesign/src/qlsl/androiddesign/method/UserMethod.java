package qlsl.androiddesign.method;

import android.text.TextUtils;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.constant.BaseConstant;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.util.commonutil.SPUtils;

/**
 * 对单例User的主要操作封装类<br/>
 * user设计：<br/>
 * 在内存中单例获取,不进行离线的文件缓存<br/>
 * 使用强制登陆模式，所以从内存中获取的user不可能为null<br/>
 * 故无需在主页及其后进行登陆的判断和后续的未登录提示操作<br/>
 * 注册入口只在Web端，前端不提供注册入口<br/>
 * 删除账号功能非用户权限<br/>
 * 前端提供查看用户信息功能<br/>
 * 账号信息修改权限：只提供修改密码和更换头像<br/>
 * 关于免登陆：<br/>
 * 登陆后保存token以避免下次重复登陆<br/>
 * 初始化数据判断是否登陆，以token值为判断依据<br/>
 * 未登录时，跳到登陆，保存token(文件及内存)及登陆数据中的用户信息(内存),获取并设置班级信息(内存)<br/>
 * 已登陆时，进入首页，之前获取用户信息设置到内存，获取并设置班级信息(内存)<br/>
 * 登录中的用户信息与获取的用户信息的字段差别为是否返回token的差别,其它相同<br/>
 * 即登录与用户信息保持token差别，班级信息需单独获取<br/>
 */
public class UserMethod {

	public static final byte ROLE_STUDENT = 2;

	public static final byte ROLE_TEACHER = 4;

	public static final byte ROLE_PARENT = 1;

	/**
	 * 单例user
	 */
	private static User user;

	/**
	 * 缓存token到属性文件中并更新BaseConstant中的内存token<br/>
	 */
	public static void setToken(String token) {
		SPUtils.put(SoftwareApplication.getInstance(), SPUtils.MEMBER_TOKEN, token);
		BaseConstant.TOKEN = token;
	}

	/**
	 * 缓存platId到属性文件中<br/>
	 */
	public static void setPlatId(String platId) {
		SPUtils.put(SoftwareApplication.getInstance(), SPUtils.MEMBER_PLAT_ID, platId);
	}

	/**
	 * 从属性文件中获取token<br/>
	 */
	public static String getTokenFromProperty() {
		Object token = SPUtils.get(SoftwareApplication.getInstance(), SPUtils.MEMBER_TOKEN, null);
		if (token == null || TextUtils.isEmpty(token.toString())) {
			return null;
		} else {
			return token.toString();
		}
	}

	/**
	 * 从属性文件中获取platId<br/>
	 */
	public static String getPlatIdFromProperty() {
		Object platId = SPUtils.get(SoftwareApplication.getInstance(), SPUtils.MEMBER_PLAT_ID, null);
		if (platId == null || TextUtils.isEmpty(platId.toString())) {
			return null;
		} else {
			return platId.toString();
		}
	}

	/**
	 * 设置当前用户<br/>
	 */
	public static void setUser(User user) {
		UserMethod.user = user;
	}

	/**
	 * 获取当前用户<br/>
	 * 说明：不可能为null,无需判断null值问题<br/>
	 */
	public static User getUser() {
		return user;
	}

	/**
	 * 判断是否为学生<br/>
	 */
	public static boolean isStudent() {
		return (user.getRole() & 2) > 0;
	}

	/**
	 * 判断是否为教师<br/>
	 */
	public static boolean isTeacher() {
		return (user.getRole() & 4) > 0;
	}

	/**
	 * 判断是否为家长<br/>
	 */
	public static boolean isParent() {
		return (user.getRole() & 1) > 0;
	}

	/**
	 * 判断是否为学生<br/>
	 */
	public static boolean isStudent(int role) {
		return (role & 2) > 0;
	}

	/**
	 * 判断是否为教师<br/>
	 */
	public static boolean isTeacher(int role) {
		return (role & 4) > 0;
	}

	/**
	 * 判断是否为家长<br/>
	 */
	public static boolean isParent(int role) {
		return (role & 1) > 0;
	}

	/**
	 * 判断是否为圈主<br/>
	 */
	public static boolean isOwner(String managerId) {
		return user.getId().toString().equals(managerId);
	}

	/**
	 * 判断是否有角色<br/>
	 * 登录后及获取用户信息后判断<br/>
	 */
	public static boolean hasRole() {
		if (isStudent() || isTeacher() || isParent()) {
			return true;
		}
		return false;
	}

}
