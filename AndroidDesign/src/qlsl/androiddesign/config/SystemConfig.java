package qlsl.androiddesign.config;

import qlsl.androiddesign.constant.BaseConstant;
import qlsl.androiddesign.exception.ConstantsUninitializeException;
import qlsl.androiddesign.method.BaseMethod;

public class SystemConfig {

	public static String getUrl() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.URL)) {
			throw new ConstantsUninitializeException("URL");
		} else {
			return BaseConstant.URL;
		}
	}

	public static String getLeziyouUrl() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.LEZIYOU_URL)) {
			throw new ConstantsUninitializeException("LEZIYOU_URL");
		} else {
			return BaseConstant.LEZIYOU_URL;
		}
	}

	public static String getAddress() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.ADDRESS_STRING)) {
			throw new ConstantsUninitializeException("ADDRESS");
		} else {
			return BaseConstant.ADDRESS_STRING;
		}
	}

	public static int getPort() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.PORT_STRING)) {
			throw new ConstantsUninitializeException("PORT");
		} else {
			return Integer.valueOf(BaseConstant.PORT_STRING);
		}
	}

	public static String getResUrl() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.RES_URL)) {
			throw new ConstantsUninitializeException("RES_URL");
		} else {
			return BaseConstant.RES_URL;
		}
	}

	public static String getAppId() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.APPID)) {
			throw new ConstantsUninitializeException("APPID");
		} else {
			return BaseConstant.APPID;
		}
	}

	public static String getAppCode() throws ConstantsUninitializeException {
		if (BaseMethod.isEmpty(BaseConstant.APPCODE)) {
			throw new ConstantsUninitializeException("APPCODE");
		} else {
			return BaseConstant.APPCODE;
		}
	}

	public static boolean needSign() {
		return BaseConstant.mustSign;
	}

}
