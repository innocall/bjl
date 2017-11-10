package com.rhino.bjl.mybatis.db;

import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public abstract interface LemonDB<T>
{
  public abstract boolean insert(String paramString, Object paramObject);

  public abstract boolean insertDynamic(String paramString, HashMap<String, Object> paramHashMap);

  public abstract long insertDynamicRInt(String paramString, HashMap<String, Object> paramHashMap);

  public abstract void insertBatch(String paramString, List<Object> paramList);

  public abstract void insertBatch(String paramString, List<Object> paramList, int paramInt);

  public abstract boolean update(String paramString, Object paramObject);

  public abstract boolean updateDynamic(String paramString1, String paramString2, HashMap<String, Object> paramHashMap);

  public abstract boolean updateDynamic(String paramString, HashMap<String, Object> paramHashMap1, HashMap<String, Object> paramHashMap2);

  public abstract boolean delete(String paramString, Serializable paramSerializable);

  public abstract boolean delete(String paramString, Object paramObject);

  public abstract boolean deleteDynamic(String paramString, HashMap<String, Object> paramHashMap);

  public abstract void deleteBatchDynamic(String paramString1, String paramString2, List<? extends Object> paramList);

  public abstract T get(String paramString, Object paramObject);

  public abstract List<T> getList(String paramString);

  public abstract List<T> getList(String paramString, Object paramObject);

  public abstract List<T> getListPaginated(String paramString, Object paramObject, int paramInt1, int paramInt2);

  public abstract PagerModel<T> getPaginated(String paramString, Object paramObject, int paramInt1, int paramInt2);

  public abstract HashMap<String, Object> getDataGrid(String paramString, DataGridModel paramDataGridModel);

  public abstract HashMap<String, Object> getDataGrid(String paramString, DataGridModel paramDataGridModel, HashMap<String, Object> paramHashMap);

  public abstract List<T> getListBySql(String paramString);

  public abstract SqlSession getSession();

  public abstract Connection getConnection();

  public abstract LemonEntry getIACEntry(String paramString, Object paramObject);

  public abstract List<LemonEntry> getIACEntryList(String paramString);

  public abstract List<LemonEntry> getIACEntryList(String paramString, Object paramObject);

  public abstract List<LemonEntry> getIACEntryListPaginated(String paramString, Object paramObject, int paramInt1, int paramInt2);

  public abstract PagerModel<LemonEntry> getIACEntryPaginated(String paramString, Object paramObject, int paramInt1, int paramInt2);
}