package com.rhino.bjl.server;

import java.util.HashMap;

public interface IMainMessage {
    HashMap<String, Object> findParamMsgByUserId(String userId);

    boolean updateUserMoneyByUserId(String userId, int money);

    boolean updateZhuangMoneyByUserId(String userId);

    String saveRoomData(String userMoney, String juCount,String userId,String zhuangCount,String xianCount,String heCount,String zhuangDuiCount1,String xianDuiCount);

    boolean saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String id, String roomId, String radio,String zhuangdian,String xiandian);

    boolean updateRoomData(String userMoney, String juCount, String id, String zhuangCount, String xianCount, String heCount, String zhuangDuiCount1, String xianDuiCount, String roomId);
}
