package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

public class ToolColor {

	public ToolColor() {

	}

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField
	private Boolean isOpenArt;

	@DatabaseField
	private Boolean isShowSingle;

	@DatabaseField
	private String textColor;

	@DatabaseField
	private String titleBarColor;

	@DatabaseField
	private String contentBarColor;

	@DatabaseField
	private Integer textSize;

	@DatabaseField
	private Integer sleep;

	public Boolean getIsOpenArt() {
		return isOpenArt;
	}

	public void setIsOpenArt(Boolean isOpenArt) {
		this.isOpenArt = isOpenArt;
	}

	public Boolean getIsShowSingle() {
		return isShowSingle;
	}

	public void setIsShowSingle(Boolean isShowSingle) {
		this.isShowSingle = isShowSingle;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTitleBarColor() {
		return titleBarColor;
	}

	public void setTitleBarColor(String titleBarColor) {
		this.titleBarColor = titleBarColor;
	}

	public String getContentBarColor() {
		return contentBarColor;
	}

	public void setContentBarColor(String contentBarColor) {
		this.contentBarColor = contentBarColor;
	}

	public Integer getTextSize() {
		return textSize;
	}

	public void setTextSize(Integer textSize) {
		this.textSize = textSize;
	}

	public Integer getSleep() {
		return sleep;
	}

	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}

	public String toString() {
		return "isOpenArt:" + isOpenArt;
	}
}
