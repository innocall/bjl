package com.rhino.bjl.mybatis.db;

import java.io.Serializable;
import java.util.HashMap;

public class LemonEntry implements Serializable {
	private static final long serialVersionUID = 3429850904659099295L;
	private HashMap<String, Object> iacMap = null;
	private int lErrorInt = 0;
	private long lErrorLong = 0L;
	private float dErrorFloat = 0.0F;
	private String strErrorString = "";

	public LemonEntry() {
	}

	public LemonEntry(HashMap<String, Object> iacMap) {
		this.iacMap = iacMap;
	}

	public HashMap<String, Object> getIacMap() {
		return this.iacMap;
	}

	public void setIacMap(HashMap<String, Object> iacMap) {
		this.iacMap = iacMap;
	}

	public void add(String key, Object value) {
		this.iacMap.put(key, value);
	}

	public String getValueAsString(String key) {
		Object objVal = getValueAsObject(key);
		if (objVal == null)
			return this.strErrorString;
		return objVal.toString();
	}

	public int getValueAsInt(String key) {
		Object objVal = getValueAsObject(key);
		if (objVal == null)
			return this.lErrorInt;

		if (objVal instanceof Number) {
			Number val = (Number) objVal;
			return val.intValue();
		}
		return this.lErrorInt;
	}

	public long getValueAsLong(String key) {
		Object objVal = getValueAsObject(key);
		if (objVal == null)
			return this.lErrorLong;

		if (objVal instanceof Number) {
			Number val = (Number) objVal;
			return val.longValue();
		}
		return this.lErrorLong;
	}

	public float getValueAsFloat(String key) {
		Object objVal = getValueAsObject(key);

		if (objVal == null)
			return this.dErrorFloat;

		if (objVal instanceof Number) {
			Number a = (Number) objVal;
			return a.floatValue();
		}
		return this.lErrorInt;
	}

	public Object getValueAsObject(String key) {
		return this.iacMap.get(key);
	}
}