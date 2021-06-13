package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

/**
 * 一条消息<br/>
 * 包括己方对对方，对方对己方<br/>
 *
 */
public class ChatMessage {

	/**
	 * 消息ID
	 */
	@DatabaseField(generatedId = true)
	private int id;

	/**
	 * 队列ID<br/>
	 * 一个ChatQueue对应多个ChatMessage<br/>
	 */
	@DatabaseField(canBeNull = false, foreign = true, columnName = "queueId")
	private ChatQueue chatQueue;

	/**
	 * 消息内容
	 */
	@DatabaseField(canBeNull = false)
	private String messageContent;

	/**
	 * 消息类型<br/>
	 * [0,1]代表[接收，发送]<br/>
	 */
	@DatabaseField(canBeNull = false)
	private Integer messageType;

	/**
	 * 发送或接收时间
	 */
	@DatabaseField(canBeNull = false)
	private long sendTime;

	/**
	 * 发送人ID(发送时为己方，接收时为对方)
	 */
	@DatabaseField
	private String senderId;

	/**
	 * 发送人(发送时为己方，接收时为对方)
	 */
	@DatabaseField
	private String senderName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ChatQueue getChatQueue() {
		return chatQueue;
	}

	public void setChatQueue(ChatQueue chatQueue) {
		this.chatQueue = chatQueue;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String toString() {
		return "messageContent:" + messageContent + "  messageType:" + messageType + "  sendTime:" + sendTime
				+ "  senderId:" + senderId + "  senderName:" + senderName;
	}
}
