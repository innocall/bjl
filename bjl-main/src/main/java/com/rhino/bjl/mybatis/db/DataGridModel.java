package com.rhino.bjl.mybatis.db;

import java.io.Serializable;

public class DataGridModel implements Serializable {
	private static final long serialVersionUID = 5300693791899290551L;
	private int page = 1;
	private int rows = 15;
	private String sort;
	private String order;
	private int offset = 0;

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getOffset() {
		this.offset = ((this.page - 1) * this.rows);
		if (this.offset < 0)
			this.offset = 0;
		return this.offset;
	}
}