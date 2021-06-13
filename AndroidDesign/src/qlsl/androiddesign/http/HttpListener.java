package qlsl.androiddesign.http;

public interface HttpListener {

	public void onDbSucceed(String method, Object values);

	public void onDbFaild(String method, Object values);

	public void onNetWorkSucceed(String method, Object values);

	public void onNetWorkFaild(String method, Object values);

	public void onOtherSucceed(String method, Object values);

	public void onOtherFaild(String method, Object values);

	public void onException(String className, String method, Exception e);

	public void onCancel(String method);

}
