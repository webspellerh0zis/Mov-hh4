package qlsl.androiddesign.entity.commonentity;

import java.io.Serializable;

/**
 * 用户信息实体类<br/>
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7328972076759340693L;

	private Long id;

	private String nick;

	private String name;

	private String avatar;

	private Integer role;

	private String code;

	private String studentNum;

	private Integer subjectId;

	private String subjectName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String toString() {
		return "nick=" + nick + "  name=" + name + "  role=" + role + "  studentNum=" + studentNum + "  code:" + code;
	}

}
