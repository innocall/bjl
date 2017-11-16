package com.rhino.bjl.mapper;

import com.rhino.bjl.mybatis.db.LemonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wuxiaotie on 2017/6/19.
 */
@Repository
public class MainManageMapper {

    @Autowired
    private LemonDB<HashMap<String,Object>> lemonDB;


    public HashMap<String, Object> findParamMsgByUserId(HashMap<String, Object> params) {
        return lemonDB.get("findParamMsgByUserId", params);
    }

    public boolean updateUserMoneyByUserId(HashMap<String, Object> params) {
        return lemonDB.update("updateUserMoneyByUserId",params);
    }

    public boolean updateZhuangMoneyByUserId(HashMap<String, Object> params) {
        return lemonDB.update("updateZhuangMoneyByUserId",params);
    }

    public boolean saveRoomData(HashMap<String, Object> params) {
        return lemonDB.insert("saveRoomData", params);
    }

    public boolean saveReetData(HashMap<String, Object> params) {
        return lemonDB.insert("saveReetData", params);
    }

    public boolean updateRoomData(HashMap<String, Object> params) {
        return  lemonDB.updateDynamic("room_tbl", "ID", params);
    }

    public List<HashMap<String, Object>> findReetList(String sql) {
        return lemonDB.getListBySql(sql);
    }

    public int findReetListCount(String sql) {
        int i = 0;
        List<HashMap<String, Object>> list = lemonDB.getListBySql(sql);
        if (list != null && list.size() > 0) {
            Long j = (Long) list.get(0).get("NUMBER");
            i = j.intValue();
        }
        return i;
    }

    public List<HashMap<String, Object>> findReetByRoomId(HashMap<String, Object> params) {
        return lemonDB.getList("findReetByRoomId",params);
    }
}
