package com.rhino.bjl.server.impl;

import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.DateUtils;
import com.rhino.bjl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @Override
    public String saveRoomData(String userMoney,String juCount,String userId,String zhuangCount,String xianCount,String heCount,String zhuangDuiCount1,String xianDuiCount) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        String id = UUID.randomUUID().toString();
        params.put("ID", id);
        params.put("USERID", userId);
        params.put("TOTALCOUNT", juCount);
        params.put("MONEY", userMoney);
        params.put("ZHUANGCOUNT", zhuangCount); //开庄数量
        params.put("XIANCOUNT", xianCount); //开闲数量
        params.put("HECOUNT", heCount); //开和数量
        params.put("ZHUANGDUICOUNT", zhuangDuiCount1); //庄对数量
        params.put("XIANDUICOUNT", xianDuiCount); //闲对数量
        params.put("STRARTTIME", DateUtils.getDate5()); //开始时间
       // params.put("ENDTIME", 0); //结束时间
        if (mainManageMapper.saveRoomData(params)) {
            return id;
        }
        return "";
    }

    @Override
    public String saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String userId, String roomId, String radio,String zhuangdian,String xiandian,String juCount) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        String id = UUID.randomUUID().toString();
        params.put("ID", id);
        params.put("USERID", userId);
        params.put("ROOMID", roomId);
        params.put("ZHUANG1", zhuang1);
        params.put("ZHUANG2", zhuang2);
        params.put("ZHUANG3", zhuang3);
        params.put("XIAN1", xian1);
        params.put("XIAN2", xian2);
        params.put("XIAN3", xian3);
        params.put("TOUZHUMONEY", touzhuMoney);
        params.put("TOUZHU", radio);
        params.put("ZHUANGVALUE", zhuangdian);
        params.put("XIANVALUE", xiandian);
        params.put("POINT", juCount);
        params.put("TIME", DateUtils.getDate5());
        if (mainManageMapper.saveReetData(params)) {
            return id;
        }
        return "";
    }

    @Override
    public boolean updateRoomData(String userMoney, String juCount, String userId, String zhuangCount, String xianCount, String heCount, String zhuangDuiCount1, String xianDuiCount, String roomId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("ID", roomId);
        params.put("USERID", userId);
        params.put("TOTALCOUNT", juCount);
        params.put("MONEY", userMoney);
        params.put("ZHUANGCOUNT", zhuangCount); //开庄数量
        params.put("XIANCOUNT", xianCount); //开闲数量
        params.put("HECOUNT", heCount); //开和数量
        params.put("ZHUANGDUICOUNT", zhuangDuiCount1); //庄对数量
        params.put("XIANDUICOUNT", xianDuiCount); //闲对数量
        params.put("ENDTIME", DateUtils.getDate5()); //结束时间
        return mainManageMapper.updateRoomData(params);
    }

    @Override
    public List<HashMap<String, Object>> findReetList(String category, String zhuang_1,String zhuang_2,String zhuang_3,String xian_1,String xian_2,String xian_3,String zhuangdianshu,String xiandianshu, String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT * FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category, zhuang_1,zhuang_2,zhuang_3,xian_1,xian_2,xian_3,zhuangdianshu,xiandianshu, startDate, endDate, query, sql);
        sql = sql + " ORDER BY TIME ASC limit "+ start +"," + limit;
        System.out.println("SQL=" + sql);
        List<HashMap<String, Object>> list =  mainManageMapper.findReetList(sql);
        List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
        if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
            ArrayList<String> als = new ArrayList<String>();
            ArrayList<String> alss = new ArrayList<String>();
            String []arr = query.split("-");
            for (int i=0;i<arr.length;i++) {
                String j = arr[i];
                if ("A".equals(j)) {
                    j = "1";
                } else  if ("J".equals(j)) {
                    j = "11";
                } else  if ("Q".equals(j)) {
                    j = "12";
                } else  if ("K".equals(j)) {
                    j = "13";
                }
                als.add(j);
            }
            for (int i =0 ;i<list.size();i++) {
                HashMap<String, Object> map = list.get(i);
                if (category.equals("全部")) {
                    alss.add((String) map.get("XIAN1"));
                    alss.add((String) map.get("XIAN2"));
                    alss.add((String) map.get("XIAN3"));
                    alss.add((String) map.get("ZHUANG1"));
                    alss.add((String) map.get("ZHUANG2"));
                    alss.add((String) map.get("ZHUANG3"));
                    alss.add((String) map.get("XIANVALUE"));
                    alss.add((String) map.get("ZHUANGVALUE"));
                } else if(category.equals("庄")){
                    alss.add((String) map.get("ZHUANG1"));
                    alss.add((String) map.get("ZHUANG2"));
                    alss.add((String) map.get("ZHUANG3"));
                    alss.add((String) map.get("ZHUANGVALUE"));
                } else if (category.equals("闲")) {
                    alss.add((String) map.get("XIAN1"));
                    alss.add((String) map.get("XIAN2"));
                    alss.add((String) map.get("XIAN3"));
                    alss.add((String) map.get("XIANVALUE"));
                }
                if (alss.containsAll(als)) {
                    list2.add(map);
                }
                alss.clear();
            }
        } else {
            return list;
        }
        return list2;
    }

    private String formatSqls(String category,String zhuang_1,String zhuang_2,String zhuang_3,String xian_1,String xian_2,String xian_3,String zhuangdianshu,String xiandianshu, String startDate, String endDate, String query, String sql) {
        sql = sql + " WHERE TIME BETWEEN '" + startDate +"' AND '" + endDate + "'";
        if (category.equals("全部")) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                String qu = StringUtils.formatSql(query);
                sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu +" OR XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR ZHUANGVALUE in " + qu +" OR XIANVALUE in " + qu +")";
            }
        } else if(category.equals("庄")){
            if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                String qu = StringUtils.formatSql(query);
                sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu + " OR ZHUANGVALUE in " + qu +")";
            }
        } else if (category.equals("闲")) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                String qu = StringUtils.formatSql(query);
                sql = sql + " AND (XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR XIANVALUE in " + qu +")";
            }
        }
        if ("单数".equals(zhuang_1)) {
            sql = sql + " AND (ZHUANG1 < 10 AND ZHUANG1%2!=0)";
        }else if ("双数".equals(zhuang_1)) {
            sql = sql + " AND (ZHUANG1 < 10 AND ZHUANG1%2=0)";
        }
        if ("单数".equals(zhuang_2)) {
            sql = sql + " AND (ZHUANG2 < 10 AND ZHUANG2%2!=0)";
        }else if ("双数".equals(zhuang_2)) {
            sql = sql + " AND (ZHUANG2 < 10 AND ZHUANG2%2=0)";
        }
        if ("单数".equals(zhuang_3)) {
            sql = sql + " AND (ZHUANG3 < 10 AND ZHUANG3%2!=0)";
        }else if ("双数".equals(zhuang_3)) {
            sql = sql + " AND (ZHUANG3 < 10 AND ZHUANG3%2=0)";
        }
        if ("单数".equals(xian_1)) {
            sql = sql + " AND (XIAN1 < 10 AND XIAN1%2!=0)";
        }else if ("双数".equals(xian_1)) {
            sql = sql + " AND (XIAN1 < 10 AND XIAN1%2=0)";
        }
        if ("单数".equals(xian_2)) {
            sql = sql + " AND (XIAN2 < 10 AND XIAN2%2!=0)";
        }else if ("双数".equals(xian_2)) {
            sql = sql + " AND (XIAN2 < 10 AND XIAN2%2=0)";
        }
        if ("单数".equals(xian_3)) {
            sql = sql + " AND (XIAN3 < 10 AND XIAN3%2!=0)";
        }else if ("双数".equals(xian_3)) {
            sql = sql + " AND (XIAN3 < 10 AND XIAN3%2=0)";
        }
        if ("单数".equals(zhuangdianshu)) {
            sql = sql + " AND (ZHUANGVALUE < 10 AND ZHUANGVALUE%2!=0)";
        }else if ("双数".equals(zhuangdianshu)) {
            sql = sql + " AND (ZHUANGVALUE < 10 AND ZHUANGVALUE%2=0)";
        }
        if ("单数".equals(xiandianshu)) {
            sql = sql + " AND (XIANVALUE < 10 AND XIANVALUE%2!=0)";
        }else if ("双数".equals(xiandianshu)) {
            sql = sql + " AND (XIANVALUE < 10 AND XIANVALUE%2=0)";
        }
      /*  if (category2.equals("全部")){

        } else if(category2.equals("单数")){
            sql = sql + " WHERE TIME BETWEEN '" + startDate +"' AND '" + endDate + "'";
            if (category.equals("全部")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu +" OR XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR ZHUANGVALUE in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND ((XIAN1 < 10 AND XIAN1%2!=0) OR (XIAN2 < 10 AND XIAN2%2!=0) OR (XIAN3 < 10 AND XIAN3%2!=0) OR XIANVALUE%2!=0 OR (ZHUANG1 < 10 AND ZHUANG1%2!=0) OR (ZHUANG2 < 10 AND ZHUANG2%2!=0) OR (ZHUANG3 < 10 AND ZHUANG3%2!=0) OR ZHUANGVALUE%2!=0)";
                } else {
                    sql = sql + " AND ((XIAN1 < 10 AND XIAN1%2!=0) OR (XIAN2 < 10 AND XIAN2%2!=0) OR (XIAN3 < 10 AND XIAN3%2!=0) OR XIANVALUE%2!=0 OR (ZHUANG1 < 10 AND ZHUANG1%2!=0) OR (ZHUANG2 < 10 AND ZHUANG2%2!=0) OR (ZHUANG3 < 10 AND ZHUANG3%2!=0) OR ZHUANGVALUE%2!=0)";
                }
            } else if(category.equals("庄")){
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu + " OR ZHUANGVALUE in " + qu +")" +
                            " AND ((ZHUANG1 < 10 AND ZHUANG1%2!=0) OR (ZHUANG2 < 10 AND ZHUANG2%2!=0) OR (ZHUANG3 < 10 AND ZHUANG3%2!=0) OR ZHUANGVALUE%2!=0)";
                } else {
                    sql = sql +  " AND ((ZHUANG1 < 10 AND ZHUANG1%2!=0) OR (ZHUANG2 < 10 AND ZHUANG2%2!=0) OR (ZHUANG3 < 10 AND ZHUANG3%2!=0)  OR ZHUANGVALUE%2!=0)";
                }
            } else if (category.equals("闲")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND ((XIAN1 < 10 AND XIAN1%2!=0) OR (XIAN2 < 10 AND XIAN2%2!=0) OR (XIAN3 < 10 AND XIAN3%2!=0) OR XIANVALUE%2!=0)";
                } else {
                    sql = sql +  " AND ((XIAN1 < 10 AND XIAN1%2!=0) OR (XIAN2 < 10 AND XIAN2%2!=0) OR (XIAN3 < 10 AND XIAN3%2!=0) OR XIANVALUE%2!=0)";
                }
            }
        } else if (category2.equals("双数")) {
            sql = sql + " WHERE TIME BETWEEN '" + startDate +"' AND '" + endDate + "'";
            if (category.equals("全部")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu +" OR XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR ZHUANGVALUE in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND ((XIAN1 < 10 AND XIAN1%2=0) OR (XIAN2 < 10 AND XIAN2%2=0) OR (XIAN3 < 10 AND XIAN3%2=0) OR XIANVALUE%2=0 OR (ZHUANG1 < 10 AND ZHUANG1%2=0) OR (ZHUANG2 < 10 AND ZHUANG2%2=0) OR (ZHUANG3 < 10 AND ZHUANG3%2=0) OR ZHUANGVALUE%2=0)";
                } else {
                    sql = sql + " AND ((XIAN1 < 10 AND XIAN1%2=0) OR (XIAN2 < 10 AND XIAN2%2=0) OR (XIAN3 < 10 AND XIAN3%2=0) OR XIANVALUE%2=0 OR (ZHUANG1 < 10 AND ZHUANG1%2=0) OR (ZHUANG2 < 10 AND ZHUANG2%2=0) OR (ZHUANG3 < 10 AND ZHUANG3%2=0) OR ZHUANGVALUE%2=0)";
                }
            } else if(category.equals("庄")){
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu + " OR ZHUANGVALUE in " + qu +")" +
                            " AND ((ZHUANG1 < 10 AND ZHUANG1%2=0) OR (ZHUANG2 < 10 AND ZHUANG2%2=0) OR (ZHUANG3 < 10 AND ZHUANG3%2=0) OR ZHUANGVALUE%2=0)";
                } else {
                    sql = sql +  " AND ((ZHUANG1 < 10 AND ZHUANG1%2=0) OR (ZHUANG2 < 10 AND ZHUANG2%2=0) OR (ZHUANG3 < 10 AND ZHUANG3%2=0) OR ZHUANGVALUE%2=0)";
                }
            } else if (category.equals("闲")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND ((XIAN1 < 10 AND XIAN1%2=0) OR (XIAN2 < 10 AND XIAN2%2=0) OR (XIAN3 < 10 AND XIAN3%2=0) OR XIANVALUE%2=0)";
                } else {
                    sql = sql + " AND ((XIAN1 < 10 AND XIAN1%2=0) OR (XIAN2 < 10 AND XIAN2%2=0) OR (XIAN3 < 10 AND XIAN3%2=0) OR XIANVALUE%2=0)";
                }
            }
        }*/
        return sql;
    }

    @Override
    public int findReetListCount(String category,String zhuang_1,String zhuang_2,String zhuang_3,String xian_1,String xian_2,String xian_3,String zhuangdianshu,String xiandianshu, String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT Count(*) AS NUMBER FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category,zhuang_1,zhuang_2,zhuang_3,xian_1,xian_2,xian_3,zhuangdianshu,xiandianshu, startDate, endDate, query, sql);
        return mainManageMapper.findReetListCount(sql);
    }

    @Override
    public List<HashMap<String, Object>> findReetByRoomId(String roomId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("ROOMID", roomId);
        return mainManageMapper.findReetByRoomId(params);
    }

    @Override
    public List<HashMap<String, Object>> findRoomList(int start, int limit,String userId) {
        String sql = "select * from room_tbl where USERID='" + userId;
        sql = sql + "' ORDER BY STRARTTIME DESC limit "+ start +"," + limit;
        return mainManageMapper.findRoomList(sql);
    }

    @Override
    public int findRoomListCount(int start, int limit,String userId) {
        String sql = "select Count(*) AS NUMBER from room_tbl where USERID='" + userId + "'";
        return mainManageMapper.findRoomListCount(sql);
    }

    @Override
    public boolean deleteRootById(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ID",roomId);
        deleteReetByRoomId(roomId);
        return mainManageMapper.deleteRootById(map);
    }

    @Override
    public int findCountReetByRoomId(String roomId) {
        String sql = "SELECT Count(*) AS NUMBER FROM reet_tbl WHERE ROOMID='" + roomId + "'";
        return mainManageMapper.findReetListCount(sql);
    }

    @Override
    public boolean deleteReetById(String id) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ID",id);
        return mainManageMapper.deleteReetById(map);
    }

    public boolean deleteReetByRoomId(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        return mainManageMapper.deleteReetByRoomId(map);
    }
}
