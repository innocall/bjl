package com.rhino.bjl.mybatis.db;

import java.util.List;

public class PagerModel<T> {
	private List<T> items;
	private Long totals;
	private int pagesize = 10;

	public Long getTotals() {
		return this.totals;
	}

	public void setTotals(Long totals) {
		this.totals = totals;
	}

	public List<T> getItems() {
		return this.items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPagesize() {
		return this.pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
}