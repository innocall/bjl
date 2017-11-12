package com.rhino.bjl.server.impl;

import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.IMainMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MainMessage implements IMainMessage {

    @Autowired
    private MainManageMapper mainManageMapper;

    @Override
    public HashMap<String, Object> findParamMsgByUserId(String userId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("USER_ID", userId);
        return mainManageMapper.findParamMsgByUserId(params);
    }

    @Override
    public boolean updateUserMoneyByUserId(String userId, int money) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("USER_ID", userId);
        params.put("USER_MONEY", money);
        return mainManageMapper.updateUserMoneyByUserId(params);
    }

    @Override
    public boolean updateZhuangMoneyByUserId(String userId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("USER_ID", userId);
        params.put("ZHUANG_MONEY", 0);
        return mainManageMapper.updateZhuangMoneyByUserId(params);
    }
}
