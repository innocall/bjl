package com.rhino.bjl.server;

import java.util.HashMap;
import java.util.List;

public interface IMainMessage {
    HashMap<String, Object> findParamMsgByUserId(String userId);

    boolean updateUserMoneyByUserId(String userId, float money);

    boolean updateZhuangMoneyByUserId(String userId);

    String saveRoomData(String userMoney, String juCount,String userId,String zhuangCount,String xianCount,String heCount,String zhuangDuiCount1,String xianDuiCount);

    String saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String id, String roomId, String radio,String zhuangdian,String xiandian,String juCount);

    boolean updateRoomData(String userMoney, String juCount, String id, String zhuangCount, String xianCount, String heCount, String zhuangDuiCount1, String xianDuiCount, String roomId);

    List<HashMap<String,Object>> findReetList(String category, String jishu,String oushu,String ling, String startDate, String endDate, String query, int start, int limit);

    int findReetListCount(String category,String jishu,String oushu,String ling, String startDate, String endDate, String query, int start, int limit);

    List<HashMap<String,Object>> findReetByRoomId(String roomId);

    List<HashMap<String,Object>> findRoomList(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx);

    int findRoomListCount(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx);

    boolean deleteRootById(String roomId);

    int findCountReetByRoomId(String roomId);

    boolean deleteReetById(String id);

    HashMap<String,Object> findRoomById(String roomId);

    boolean updateReetByRoomId(String roomId);
}
