package com.rhino.bjl.mybatis.batis;

import org.apache.ibatis.session.RowBounds;

public class LemonRowBounds extends RowBounds {
	private int totals = 0;

	public LemonRowBounds() {
	}

	public LemonRowBounds(int offset, int limit) {
		super(offset, limit);
	}

	public int getTotals() {
		return this.totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}
}