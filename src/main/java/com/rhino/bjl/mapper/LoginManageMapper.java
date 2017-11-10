package com.rhino.bjl.mapper;

import com.rhino.bjl.mybatis.db.LemonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Created by wuxiaotie on 2017/6/19.
 */
@Repository
public class LoginManageMapper {

    @Autowired
    private LemonDB<HashMap<String,Object>> lemonDB;

    public HashMap<String, Object> getManageUser(HashMap<String,Object> userIdMap) {
        return lemonDB.get("login",userIdMap);
    }

}
