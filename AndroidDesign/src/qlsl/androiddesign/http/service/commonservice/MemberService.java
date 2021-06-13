package qlsl.androiddesign.http.service.commonservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qlsl.androiddesign.appname.R;

import android.os.CountDownTimer;
import android.widget.Button;
import qlsl.androiddesign.activity.commonactivity.MainActivity;
import qlsl.androiddesign.constant.MessageConstant;
import qlsl.androiddesign.constant.WhatConstant;
import qlsl.androiddesign.entity.commonentity.User;
import qlsl.androiddesign.handler.subhandler.HttpHandler;
import qlsl.androiddesign.http.HttpListener;
import qlsl.androiddesign.http.service.baseservice.BaseService;
import qlsl.androiddesign.http.xutils.HttpProtocol;
import qlsl.androiddesign.manager.ActivityManager;
import qlsl.androiddesign.method.UserMethod;
import qlsl.androiddesign.thread.HandlerThread;
import qlsl.androiddesign.util.commonutil.EncryptHelper;
import qlsl.androiddesign.util.commonutil.Log;
import qlsl.androiddesign.view.baseview.FunctionView;

/**
 * 用户模块<br/>
 * 
 */
public class MemberService extends BaseService {

	private static String className = getClassName(MemberService.class);

	/**
	 * 邮箱登录兼容手机登录<br/>
	 */
	public static void loginByEmail(final String email, final String password, final FunctionView<?> functionView,
			final HttpListener listener) {
		functionView.setProgressBarText("正在登录…");
		final HttpHandler handler = getHandler(functionView, listener, className, "loginByEmail");
		new HandlerThread(className, "loginByEmail") {
			public void run() {
				final HttpProtocol protocol = new HttpProtocol();
				try {
					String encryptPwd = EncryptHelper.desEncode(password);
					JSONObject jo = protocol.setMethod("user_login").addParam("account", email)
							.addParam("password", encryptPwd).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					String token = data.getString("token");
					User user = JSON.toJavaObject(data.getJSONObject("user"), User.class);
					UserMethod.setToken(token);
					UserMethod.setPlatId(null);
					UserMethod.setUser(user);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {

						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 第三方登录<br/>
	 */
	public static void loginByPlatform(final String platformId, final String accessToken, final int platformType,
			final String nick, final String profile, final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在登录…");
		final HttpHandler handler = getHandler(functionView, listener, className, "loginByPlatform");
		new HandlerThread(className, "loginByPlatform") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_platformLogin").addParam("platformId", platformId)
							.addParam("platformType", platformType).addParam("accessToken", accessToken)
							.addParam("nick", nick).addParam("profile", profile).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					String token = data.getString("token");
					final String platId = data.getString("platId");
					UserMethod.setToken(token);
					UserMethod.setPlatId(platId);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							if (platId == null) {
								getUserInfo(functionView, listener);
							} else {

							}
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 绑定学生第三方账户<br/>
	 */
	public static void bindChildByPlatform(final String uid, final int loginType, final String username,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.showToast("绑定学生第三方账号正在开发中");
	}

	/**
	 * 获取用户信息<br/>
	 */
	public static void getUserInfo(final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "getUserInfo");
		new HandlerThread(className, "getUserInfo") {
			public void run() {
				final HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_load").get();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					User user = JSON.toJavaObject(data, User.class);
					UserMethod.setUser(user);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							if (UserMethod.hasRole()) {
								handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS,
										MessageConstant.MSG_LOGIN_SUCCESS);
							} else {

							}
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 更新角色及科目,以及用户信息缓存<br/>
	 */
	public static void updateUserRole(final Integer role, final Integer subjectId, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "updateUserRole");
		new HandlerThread(className, "updateUserRole") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_bindRole").addParam("role", role)
							.addParam("subjectId", subjectId).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					User user = UserMethod.getUser();
					user.setRole(role);
					user.setSubjectId(subjectId);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.startActivity(MainActivity.class, R.anim.in_translate_top,
									R.anim.out_translate_top);
							ActivityManager.getInstance().popActivitysUnderTheSpecify(MainActivity.class);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 关联学生账号<br/>
	 */
	public static void bindChildByAccount(final int relationType, final String email, final String password,
			final FunctionView<?> functionView, final HttpListener listener) {
		functionView.setProgressBarText("正在关联");
		final HttpHandler handler = getHandler(functionView, listener, className, "bindChildByAccount");
		new HandlerThread(className, "bindChildByAccount") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_bindStudent").addParam("account", email)
							.addParam("password", password).addParam("relationType", relationType).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					User user = JSON.toJavaObject(data.getJSONObject("user"), User.class);
					UserMethod.setUser(user);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.startActivity(MainActivity.class, R.anim.in_translate_top,
									R.anim.out_translate_top);
							ActivityManager.getInstance().popActivitysUnderTheSpecify(MainActivity.class);
						}
					});

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, "关联成功");

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 修改密码<br/>
	 */
	public static void resetPassword(final String oldPwd, final String newPwd, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "resetPassword");
		new HandlerThread(className, "resetPassword") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					String encryptOldPwd = EncryptHelper.desEncode(oldPwd);
					String encryptNewPwd = EncryptHelper.desEncode(newPwd);
					JSONObject jo = protocol.setMethod("user_changePwd").addParam("oldPwd", encryptOldPwd)
							.addParam("password", encryptNewPwd).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.showToast(MessageConstant.MSG_UPDATE_PWD_SUCCESS);
							functionView.activity.finish();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 忘记密码<br/>
	 */
	public static void resetPasswordBySMSCode(final String mobile, final String vcode, final String password,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "resetPasswordBySMSCode");
		new HandlerThread(className, "resetPasswordBySMSCode") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					String encryptPwd = EncryptHelper.desEncode(password);
					JSONObject jo = protocol.setMethod("user_resetPwd").addParam("mobile", mobile)
							.addParam("vcode", vcode).addParam("password", encryptPwd).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.showToast(MessageConstant.MSG_UPDATE_PWD_SUCCESS);
							functionView.activity.finish();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 发送验证码<br/>
	 */
	public static void requestSMSCode(final String mobile, final CountDownTimer sendCodeTimer, final Button btn_send,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "requestSMSCode");
		new HandlerThread(className, "requestSMSCode") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("message_sendSmsCode").addParam("mobile", mobile).post();
					if (isDataInvalid(jo, handler, protocol)) {
						functionView.activity.runOnUiThread(new Runnable() {

							public void run() {
								sendCodeTimer.cancel();
								btn_send.setText("发送验证码");
							}
						});
						return;
					}

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS, MessageConstant.MSG_SEND_SUCCESS);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 更新头像并更新用户信息缓存<br/>
	 */
	public static void updateHeadPhoto(final String headUrl, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "updateHeadPhoto");
		new HandlerThread(className, "updateHeadPhoto") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_update").addParam("avatar", headUrl).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					User user = UserMethod.getUser();
					user.setAvatar(headUrl);

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS,
							MessageConstant.MSG_UPDATE_SUCCESS);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 更新昵称并更新用户信息缓存<br/>
	 */
	public static void updateNickName(final String nick, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "updateNickName");
		new HandlerThread(className, "updateNickName") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_update").addParam("nick", nick).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					User user = UserMethod.getUser();
					user.setNick(nick);

					handler.sendMessage(protocol, WhatConstant.WHAT_NET_DATA_SUCCESS,
							MessageConstant.MSG_UPDATE_SUCCESS);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 第三方登录后绑定得一账户<br/>
	 */
	public static void bindAccount(final String platId, final String account, final String password,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "bindAccount");
		new HandlerThread(className, "bindAccount") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					JSONObject jo = protocol.setMethod("user_platformBind").addParam("platId", platId)
							.addParam("account", account).addParam("password", password).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					UserMethod.setPlatId(null);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							getUserInfo(functionView, listener);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 第三方登录后创建空记录账户<br/>
	 */
	public static void createAccount(final String platId, final FunctionView<?> functionView,
			final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "createAccount");
		new HandlerThread(className, "createAccount") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					Log.i("qlsl", "platId:" + platId);
					JSONObject jo = protocol.setMethod("user_platformCreate").addParam("platId", platId).post();
					Log.i("qlsl", jo);
					Log.i("qlsl", protocol.getUrl());
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}
					JSONObject data = jo.getJSONObject("data");
					String token = data.getString("token");
					UserMethod.setToken(token);
					UserMethod.setPlatId(null);

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							getUserInfo(functionView, listener);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

	/**
	 * 手机注册<br/>
	 */
	public static void registByMobile(final String mobile, final String vcode, final String password,
			final FunctionView<?> functionView, final HttpListener listener) {
		final HttpHandler handler = getHandler(functionView, listener, className, "registByMobile");
		new HandlerThread(className, "registByMobile") {
			public void run() {
				HttpProtocol protocol = new HttpProtocol();
				try {
					String encryptPwd = EncryptHelper.desEncode(password);
					JSONObject jo = protocol.setMethod("user_regist").addParam("mobile", mobile)
							.addParam("vcode", vcode).addParam("password", encryptPwd).post();
					if (isDataInvalid(jo, handler, protocol)) {
						return;
					}

					functionView.activity.runOnUiThread(new Runnable() {

						public void run() {
							functionView.activity.finish();
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(protocol, WhatConstant.WHAT_EXCEPITON, e);
				}
			};
		}.start();
	}

}