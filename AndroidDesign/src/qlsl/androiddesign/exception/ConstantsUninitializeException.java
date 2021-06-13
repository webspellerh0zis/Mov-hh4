package qlsl.androiddesign.exception;

/**
 * 
 * @author xuh
 * @Description: BaseConstants未继承并被初始化而造成的异常
 * @ClassName: ConstantsUninitializeException.java
 * @date 2014-9-25 上午11:21:29
 * @说明 代码版权归 杭州天迈网络科技有限公司 所有
 */
public class ConstantsUninitializeException extends SoftwareException {

	/**
	 * @Fields serialVersionUID : 序列化ID
	 */
	private static final long serialVersionUID = -6682254255045169391L;

	public ConstantsUninitializeException() {
		super("you can not use the protocol before init the BaseConstants");
	}

	public ConstantsUninitializeException(String params) {
		super(params + " is null");
	}

}
