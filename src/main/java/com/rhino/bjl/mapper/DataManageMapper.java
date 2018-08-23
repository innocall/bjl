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

    public List<HashMap<String, Object>> findMoreData(HashMap<String, Object> map) {
        return lemonDB.getList("findMoreData",map);
    }

    public List<HashMap<String, Object>> findMoreData2(HashMap<String, Object> map) {
        return lemonDB.getList("findMoreData2",map);
    }

    public List<HashMap<String, Object>> findMoreData3(HashMap<String, Object> map) {
        return lemonDB.getList("findMoreData3",map);
    }

    public List<HashMap<String, Object>> findMoreData4(HashMap<String, Object> map) {
        return lemonDB.getList("findMoreData4",map);
    }

    public List<HashMap<String, Object>> findReetListByMN(HashMap<String, Object> map3) {
        return lemonDB.getList("findReetListByMN",map3);
    }

    public List<HashMap<String, Object>> findReetListCountByMN(HashMap<String, Object> map3) {
        return lemonDB.getList("findReetListCountByMN",map3);
    }

    public List<HashMap<String, Object>> findReetByPai(HashMap<String, Object> map3) {
        return lemonDB.getList("findReetByPai",map3);
    }
}
