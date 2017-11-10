package com.rhino.bjl.server.impl;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.mapper.LoginManageMapper;
import com.rhino.bjl.server.ILoginMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by wuxiaotie on 2017/6/23.
 */
@Service
public class LoginMessage implements ILoginMessage {

    @Autowired
    private LoginManageMapper loginManageMapper;

    public Object[] login(String username, String password) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("USERNAME", username);
        param.put("PASSWORD", password);
        HashMap<String, Object> result = loginManageMapper.getManageUser(param);
        if (result == null) {
            return new Object[] { 400, null }; // 用户不存在
        }
        ManageUser manageUser = new ManageUser();
        manageUser.setID((String) result.get("ID"));
        manageUser.setNAME((String) result.get("NAME"));
        manageUser.setPHONE((String) result.get("PHONE"));
        manageUser.setUSERNAME(username);
        return new Object[] { 200, manageUser };
    }

}
