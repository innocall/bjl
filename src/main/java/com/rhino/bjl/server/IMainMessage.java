package com.rhino.bjl.server;

import java.util.HashMap;
import java.util.List;

public interface IMainMessage {
    HashMap<String, Object> findParamMsgByUserId(String userId);

    boolean updateUserMoneyByUserId(String userId, int money);

    boolean updateZhuangMoneyByUserId(String userId);

    String saveRoomData(String userMoney, String juCount,String userId,String zhuangCount,String xianCount,String heCount,String zhuangDuiCount1,String xianDuiCount);

    String saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String id, String roomId, String radio,String zhuangdian,String xiandian,String juCount);

    boolean updateRoomData(String userMoney, String juCount, String id, String zhuangCount, String xianCount, String heCount, String zhuangDuiCount1, String xianDuiCount, String roomId);

    List<HashMap<String,Object>> findReetList(String category, String zhuang_1,String zhuang_2,String zhuang_3,String xian_1,String xian_2,String xian_3,String zhuangdianshu,String xiandianshu, String startDate, String endDate, String query, int start, int limit);

    int findReetListCount(String category,String zhuang_1,String zhuang_2,String zhuang_3,String xian_1,String xian_2,String xian_3,String zhuangdianshu,String xiandianshu, String startDate, String endDate, String query, int start, int limit);

    List<HashMap<String,Object>> findReetByRoomId(String roomId);

    List<HashMap<String,Object>> findRoomList(int start, int limit,String userId);

    int findRoomListCount(int start, int limit,String userId);

    boolean deleteRootById(String roomId);

    int findCountReetByRoomId(String roomId);

    boolean deleteReetById(String id);

    HashMap<String,Object> findRoomById(String roomId);
}
