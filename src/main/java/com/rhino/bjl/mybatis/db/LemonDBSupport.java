package com.rhino.bjl.mybatis.db;

import com.rhino.bjl.mybatis.batis.LemonRowBounds;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class LemonDBSupport<T> extends SqlSessionDaoSupport implements LemonDB<T> {
	public boolean insert(String key, Object object) {
		int res = getSqlSession().insert(key, object);
		return (res > 0);
	}

	public boolean delete(String key, Serializable id) {
		int res = getSqlSession().delete(key, id);
		return (res > 0);
	}

	public T get(String key, Object params) {
		return getSqlSession().selectOne(key, params);
	}

	public List<T> getList(String key) {
		return getSqlSession().selectList(key);
	}

	public List<T> getList(String key, Object params) {
		return getSqlSession().selectList(key, params);
	}

	public boolean update(String key, Object object) {
		int res = getSqlSession().update(key, object);
		return (res > 0);
	}

	public List<T> getListPaginated(String key, Object params, int offset,
			int limit) {
		RowBounds rowBound = new RowBounds(offset, limit);
		if (params == null)
			params = new Object();
		return getSqlSession().selectList(key, params, rowBound);
	}

	public PagerModel<T> getPaginated(String key, Object params, int offset,
			int limit) {
		long totals = 0L;
		PagerModel pm = new PagerModel();
		try {
			if (params == null)
				params = new Object();
			LemonRowBounds rowBound = new LemonRowBounds(offset, limit);
			List resList = getSqlSession().selectList(key, params, rowBound);
			totals = rowBound.getTotals();
			pm.setItems(resList);
			pm.setTotals(Long.valueOf(totals));
			pm.setPagesize(limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pm;
	}

	public Connection getConnection() {
		return getSqlSession().getConnection();
	}

	public List<LemonEntry> getIACEntryList(String key, Object params) {
		List mapList = getSqlSession().selectList(key, params);
		return setIACEntry(mapList);
	}

	private List<LemonEntry> setIACEntry(List<HashMap<String, Object>> mapList) {
		List entryList = null;
		if (mapList != null) {
			entryList = new ArrayList();
			LemonEntry entry = null;
			int count = mapList.size();
			for (int i = 0; i < count; ++i) {
				HashMap resMap = (HashMap) mapList.get(i);
				entry = new LemonEntry();
				entry.setIacMap(resMap);
				entryList.add(entry);
			}
		}
		return entryList;
	}

	public LemonEntry getIACEntry(String key, Object params) {
		HashMap map = (HashMap) getSqlSession().selectOne(key, params);
		LemonEntry entry = null;
		if (map != null)
			entry = new LemonEntry(map);

		return entry;
	}

	public List<LemonEntry> getIACEntryList(String key) {
		return getIACEntryList(key, null);
	}

	public List<LemonEntry> getIACEntryListPaginated(String key, Object params,
													 int offset, int limit) {
		List mapList = getListPaginated(key, params, offset, limit);
		return setIACEntry(mapList);
	}

	public PagerModel<LemonEntry> getIACEntryPaginated(String key, Object params,
													   int offset, int limit) {
		PagerModel pm = getPaginated(key, params, offset, limit);
		PagerModel pmEntry = null;
		if (pm != null) {
			pmEntry = new PagerModel();
			pmEntry.setTotals(pm.getTotals());
			pmEntry.setPagesize(pm.getPagesize());
			List mapList = pm.getItems();
			pmEntry.setItems(setIACEntry(mapList));
		}
		return pmEntry;
	}

	public boolean insertDynamic(String tableName,
			HashMap<String, Object> params) {
		return insert("CommonMapper.insertDynamic",
				createInsertDynamic(tableName, params));
	}

	private HashMap<String, Object> createInsertDynamic(String tableName,
			HashMap<String, Object> params) {
		HashMap saveParams = null;
		if (params != null) {
			saveParams = new HashMap();
			List columnName = new ArrayList();
			List columnValue = new ArrayList();
			Iterator iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String paramKey = (String) iter.next();
				columnName.add(paramKey);
				columnValue.add(params.get(paramKey));
			}
			saveParams.put("tableName", tableName);
			saveParams.put("columnName", columnName);
			saveParams.put("columnValue", columnValue);
		}
		return saveParams;
	}

	public long insertDynamicRInt(String tableName,
			HashMap<String, Object> params) {
		HashMap saveParams = createInsertDynamic(tableName, params);
		boolean res = insert("CommonMapper.insertDynamicRInt", saveParams);
		if (res)
			return ((Long) saveParams.get("PK")).longValue();
		return 0L;
	}

	public SqlSession getSession() {
		SqlSessionTemplate st = (SqlSessionTemplate) getSqlSession();
		SqlSession sessrion = st.getSqlSessionFactory().openSession(
				ExecutorType.BATCH, false);
		return sessrion;
	}

	public void insertBatch(String key, List<Object> paramsList) {
		SqlSession session = getSession();
		try {
			if (paramsList != null) {
				int count = paramsList.size();
				for (int i = 0; i < count; ++i)
					session.insert(key, paramsList.get(i));
			}

			session.commit();
		} finally {
			session.close();
		}
	}

	public void insertBatch(String sql, List<Object> paramsList, int batchNum) {
		Connection conn = null;
		ResultSet set = null;
		PreparedStatement pstmt = null;
		try {
			if (set != null) {
				set.close();
				set = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn == null)
				return;
			conn.close();
			conn = null;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public boolean updateDynamic(String tableName, String pkName,
			HashMap<String, Object> params) {
		return update("CommonMapper.updateDynamic",
				createUpdateDynamicParams(tableName, pkName, params));
	}

	private HashMap<String, Object> createUpdateDynamicParams(String tableName,
			String pkName, HashMap<String, Object> params) {
		HashMap updateParams = new HashMap();
		List conditionList = new ArrayList();

		HashMap temp = null;
		if ((pkName != null) && (!("".equals(pkName)))) {
			temp = new HashMap();
			temp.put("conditionName", pkName);
			temp.put("conditionValue", params.get(pkName));
			conditionList.add(temp);
		}
		List columnList = new ArrayList();
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			temp = new HashMap();
			String key = (String) iter.next();
			if (key.equalsIgnoreCase(pkName))
				continue;
			temp.put("columnName", key);
			temp.put("columnValue", params.get(key));
			columnList.add(temp);
		}
		updateParams.put("tableName", tableName);
		updateParams.put("conditionList", conditionList);
		updateParams.put("columnList", columnList);
		return updateParams;
	}

	public boolean updateDynamic(String tableName,
			HashMap<String, Object> conditionMap, HashMap<String, Object> params) {
		return update("CommonMapper.updateDynamic",
				createUpdateDynamicParams(tableName, conditionMap, params));
	}

	private HashMap<String, Object> createUpdateDynamicParams(String tableName,
			HashMap<String, Object> conditionMap, HashMap<String, Object> params) {
		HashMap updateParams = new HashMap();
		List conditionList = new ArrayList();
		Iterator keyIter = conditionMap.keySet().iterator();
		HashMap temp = null;
		while (keyIter.hasNext()) {
			temp = new HashMap();
			String key = (String) keyIter.next();
			temp.put("conditionName", key);
			temp.put("conditionValue", conditionMap.get(key));
			conditionList.add(temp);
		}
		List columnList = new ArrayList();
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			temp = new HashMap();
			String key = (String) iter.next();
			temp.put("columnName", key);
			temp.put("columnValue", params.get(key));
			columnList.add(temp);
		}
		updateParams.put("tableName", tableName);
		updateParams.put("conditionList", conditionList);
		updateParams.put("columnList", columnList);
		return updateParams;
	}

	public boolean delete(String key, Object params) {
		int res = getSqlSession().delete(key, params);
		return (res > 0);
	}

	public boolean deleteDynamic(String tableName,
			HashMap<String, Object> params) {
		HashMap deleteParams = createDeleteDynamic(tableName, params);
		return delete("CommonMapper.deleteDynamic", deleteParams);
	}

	private HashMap<String, Object> createDeleteDynamic(String tableName,
			HashMap<String, Object> params) {
		HashMap deleteParams = new HashMap();
		if (params != null) {
			List columnName = new ArrayList();
			Iterator iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String paramKey = (String) iter.next();
				columnName.add(paramKey);
				deleteParams.put(paramKey, params.get(paramKey));
			}
			deleteParams.put("tableName", tableName);
			deleteParams.put("columnName", columnName);
		}
		return deleteParams;
	}

	public void deleteBatchDynamic(String tableName, String pkName,
			List<? extends Object> pkList) {
		HashMap params = createDeleteBatchDynamic(tableName, pkName, pkList);
		delete("CommonMapper.deleteBatchDynamic", params);
	}

	private HashMap<String, Object> createDeleteBatchDynamic(String tableName,
			String pkName, List<? extends Object> pkList) {
		HashMap deleteParams = new HashMap();
		if (pkList != null) {
			deleteParams.put("tableName", tableName);
			deleteParams.put("pkName", pkName);
			deleteParams.put("pkList", pkList);
		}
		return deleteParams;
	}

	public List<T> getListBySql(String sql) {
		Map params = new HashMap();
		params.put("SQL", sql);
		return getList("CommonMapper.selectBySql", params);
	}

	public HashMap<String, Object> getDataGrid(String key, DataGridModel dm) {
		String order = dm.getOrder();
		String sort = dm.getSort();
		HashMap params = new HashMap();

		if ((sort != null) && (!("".equals(sort))))
			params.put("sort", sort);
		if ((order != null) && (!("".equals(order))))
			params.put("order", order);
		PagerModel pm = getPaginated(key, params, dm.getOffset(), dm.getRows());
		HashMap resMap = new HashMap();
		if (pm != null) {
			resMap.put("total", pm.getTotals());
			resMap.put("rows", pm.getItems());
		}
		return resMap;
	}

	public HashMap<String, Object> getDataGrid(String key, DataGridModel dm,
			HashMap<String, Object> params) {
		String order = dm.getOrder();
		String sort = dm.getSort();
		if ((sort != null) && (!("".equals(sort))))
			params.put("sort", sort);
		if ((order != null) && (!("".equals(order))))
			params.put("order", order);
		PagerModel pm = getPaginated(key, params, dm.getOffset(), dm.getRows());
		HashMap resMap = new HashMap();
		if (pm != null) {
			resMap.put("total", pm.getTotals());
			resMap.put("rows", pm.getItems());
		}
		return resMap;
	}
}