package qlsl.androiddesign.entity.commonentity;

import java.io.Serializable;
import java.util.List;

import android.text.TextUtils;

/**
 * 评论<br/>
 * 
 */
public class CommentReply implements Serializable {

	private static final long serialVersionUID = 7428616624284204913L;

	private String id;

	private String userId;

	private String parentId;

	private String message;

	private String time;

	private String floor;

	private List<CommentReply> replys;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMessage() {
		if (message != null) {
			message = message.replaceAll("\n", "<br/>");
		}
		message = TextUtils.isEmpty(message) ? "暂无内容" : message;
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public List<CommentReply> getReplys() {
		return replys;
	}

	public void setReplys(List<CommentReply> replys) {
		this.replys = replys;
	}

	public String toString() {
		return "CommentReply";
	}

}
