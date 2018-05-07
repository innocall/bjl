package com.rhino.bjl.mapper;

import com.rhino.bjl.mybatis.db.LemonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class DataManageMapper {

    @Autowired
    private LemonDB<HashMap<String,Object>> lemonDB;


    public List<HashMap<String, Object>> getListBySql(String sql1) {
        return lemonDB.getListBySql(sql1);
    }


    public HashMap<String, Object> findReetType(HashMap<String, Object> map) {
        return lemonDB.get("findReetType", map);
    }

    public HashMap<String, Object> findReetByPoint(HashMap<String, Object> map2) {
        return lemonDB.get("findReetByPoint", map2);
    }

    public List<HashMap<String, Object>> findReetByCount(HashMap<String, Object> map3) {
        return lemonDB.getList("findReetByCount",map3);
    }

    public List<HashMap<String, Object>> findReetByCounts(HashMap<String, Object> map3) {
        return lemonDB.getList("findReetByCounts",map3);
    }

    public HashMap<String, Object> findReetByValue(HashMap<String, Object> map4) {
        return lemonDB.get("findReetByValue", map4);
    }
}
