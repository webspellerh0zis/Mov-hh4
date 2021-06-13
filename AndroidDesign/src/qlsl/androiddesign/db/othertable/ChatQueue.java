package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

/**
 * 聊天队列<br/>
 * 在用户与好友间建立一个消息容器<br/>
 */
public class ChatQueue {

	public ChatQueue() {

	}

	/**
	 * 队列ID
	 */
	@DatabaseField(generatedId = true)
	private Integer id;

	/**
	 * 用户ID<br/>
	 * 核心字段之一，用于判断是否为同一队列<br/>
	 */
	@DatabaseField(canBeNull = false)
	private Long userId;

	/**
	 * 对方ID<br/>
	 * 教师：群发发送时为班级id<br/>
	 * 学生：群发接收时为教师id<br/>
	 * 学生：批阅接收时为试卷id<br/>
	 * 核心字段之一，用于判断是否为同一队列<br/>
	 */
	@DatabaseField(canBeNull = false)
	private String senderId;

	/**
	 * 对方名称<br/>
	 * 班级名称或教师名称或试卷名称<br/>
	 */
	@DatabaseField
	private String senderName;

	/**
	 * 队列类型<br/>
	 * [0,1,2,3,4]代表[一对一，改错通知，全班通知，线上答题通知，试卷批阅通知]<br/>
	 * 改错通知已删除<br/>
	 * 核心字段之一，用于判断是否为同一队列<br/>
	 */
	@DatabaseField(canBeNull = false)
	private Integer queueType;

	/**
	 * 更新时间
	 */
	@DatabaseField
	private Long updateTime;

	/**
	 * 最新消息
	 */
	@DatabaseField
	private String newMessage;

	/**
	 * 未读数量
	 */
	@DatabaseField
	private Integer unReadMessageNo;

	/**
	 * 队列状态<br/>
	 * [0,4]代表[未删除，删除]<br/>
	 */
	@DatabaseField
	private Integer queueStatus;

	/**
	 * 队列头像<br/>
	 */
	@DatabaseField
	private String headUrl;

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getQueueType() {
		return queueType;
	}

	public void setQueueType(Integer queueType) {
		this.queueType = queueType;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
	}

	public Integer getUnReadMessageNo() {
		return unReadMessageNo;
	}

	public void setUnReadMessageNo(Integer unReadMessageNo) {
		this.unReadMessageNo = unReadMessageNo;
	}

	public Integer getQueueStatus() {
		return queueStatus;
	}

	public void setQueueStatus(Integer queueStatus) {
		this.queueStatus = queueStatus;
	}

	public String toString() {
		return "id:" + id + "  userId:" + userId + "  senderId:" + senderId + "  senderName:" + senderName
				+ "  queueType:" + queueType + "  updateTime:" + updateTime + "  newMessage:" + newMessage
				+ "  unReadMessageNo:" + unReadMessageNo + "  queueStatus:" + queueStatus + "  headUrl:" + headUrl;
	}

}
