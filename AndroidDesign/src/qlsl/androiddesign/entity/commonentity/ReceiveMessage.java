package qlsl.androiddesign.entity.commonentity;

/**
 * 接收消息<br/>
 * 仅用于接口<br/>
 */
public class ReceiveMessage {

	/**
	 * 发送人ID
	 */
	private Long sender_id;

	/**
	 * 发送人
	 */
	private String sender;

	/**
	 * 科目ID<br/>
	 * 发送人为学生时为null<br/>
	 */
	private Integer subject_id;

	/**
	 * 科目<br/>
	 * 发送人为学生时为null<br/>
	 */
	private String subject;

	/**
	 * 消息类型<br/>
	 * [0,1,2,3,4]代表[一对一，改错通知，全班通知，线上答题通知，试卷批阅通知]<br/>
	 * 改错通知已删除<br/>
	 */
	private Integer type;

	/**
	 * 发送时间
	 */
	private Long send_time;

	/**
	 * 消息内容
	 */
	private String content;

	public Long getSender_id() {
		return sender_id;
	}

	public void setSender_id(Long sender_id) {
		this.sender_id = sender_id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSend_time() {
		return send_time;
	}

	public void setSend_time(Long send_time) {
		this.send_time = send_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "sender_id:" + sender_id + "  sender:" + sender + "  subject_id:" + subject_id + "  subject:" + subject
				+ "  type:" + type + "  send_time:" + send_time + "  content:" + content;
	}
}
