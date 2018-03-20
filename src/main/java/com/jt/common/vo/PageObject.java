package com.jt.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * VO:(Value Object)对象 1）封装当前页实体数据 2）封装分页信息
 */
public class PageObject<T> implements Serializable {
	private static final long serialVersionUID = 6780580291247550747L;
	
	// 数据集合，有可以是商品也有可以是角色
	private List<T> records;
	// 当前页码
	private Integer pageCurrent = 1;
	// 页面大小（每页显示数量）
	private Integer pageSize = 3;
	// 总页数
	private Integer pageCount;
	// 总记录数
	private Integer rowCount;

	

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		int n = rowCount / pageSize;
		pageCount = rowCount % pageSize == 0 ? n : n + 1;
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public String toString() {
		return "PageObject [data=" + records + ", pageCurrent=" + pageCurrent + ", pageSize=" + pageSize + ", pageCount="
				+ pageCount + ", rowCount=" + rowCount + "]";
	}
}
