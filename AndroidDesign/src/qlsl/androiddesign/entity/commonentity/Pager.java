package qlsl.androiddesign.entity.commonentity;

import java.io.Serializable;

public class Pager implements Serializable {

	private static final long serialVersionUID = 8092671304634984363L;

	private int totalCount;

	private int pageSize;

	private int pageNo;

	private int totalPage;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String toString() {
		return getClass().getSimpleName() + "<br/>[totalCount=" + totalCount
				+ ",<br/>totalPage=" + totalPage + ",<br/>pageNo=" + pageNo
				+ ",<br/>pageSize" + pageSize + "]";
	}

}
