package qlsl.androiddesign.handler.basehandler;

import android.os.Message;
import qlsl.androiddesign.http.xutils.Protocol;

/**
 * 
 * 默认兼容xutils包中的Protocol，可修改兼容http包中的Protocol<br/>
 * by ylq updateAt 20150917<br/>
 * 
 */
public class Handler extends android.os.Handler {

	private Protocol protocol;

	public Handler() {

	}

	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		outputHandleReceiveMessageInfo(msg);
	}

	public void sendMessage(int what, Object object) {
		sendMessage(this.obtainMessage(what, object));
	}

	public void sendMessage(Protocol protocol, int what, Object object) {
		this.protocol = protocol;
		sendMessage(this.obtainMessage(what, object));
	}

	public void sendMessageDelayed(int what, Object object, long delayMillis) {
		sendMessageDelayed(this.obtainMessage(what, object), delayMillis);
	}

	private void outputHandleReceiveMessageInfo(Message msg) {
//		int what = msg.what;
//		Log.i("handleMessage：" + getClass().getName() + "<br/>what:" + what
//				+ "</br>object:" + "略");
	}

	public Protocol getProtocol() {
		return protocol;
	}

}
