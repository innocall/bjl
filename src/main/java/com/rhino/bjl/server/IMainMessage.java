package com.rhino.bjl.server;

import java.util.HashMap;

public interface IMainMessage {
    HashMap<String, Object> findParamMsgByUserId(String userId);

    boolean updateUserMoneyByUserId(String userId, int money);

    boolean updateZhuangMoneyByUserId(String userId);
}
