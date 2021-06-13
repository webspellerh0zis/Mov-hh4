package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

public class ToolPager {

	public ToolPager() {

	}

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField
	private Boolean isOpen;

	@DatabaseField
	private Integer pager;

	@DatabaseField
	private Integer total;

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getPager() {
		return pager;
	}

	public void setPager(Integer pager) {
		this.pager = pager;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
