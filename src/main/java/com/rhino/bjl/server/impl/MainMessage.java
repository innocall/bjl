package com.rhino.bjl.server.impl;

import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.DateUtils;
import com.rhino.bjl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String userId, String roomId, String radio,String zhuangdian,String xiandian) {
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
        params.put("TIME", DateUtils.getDate5());
        return mainManageMapper.saveReetData(params);
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
    public List<HashMap<String, Object>> findReetList(String category, String category2, String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT * FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category, category2, startDate, endDate, query, sql);
        sql = sql + " ORDER BY TIME ASC limit "+ start +"," + limit;
        System.out.println("SQL=" + sql);
        return mainManageMapper.findReetList(sql);
    }

    private String formatSqls(String category, String category2, String startDate, String endDate, String query, String sql) {
        if (category2.equals("全部")){
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
        } else if(category2.equals("单数")){
            sql = sql + " WHERE TIME BETWEEN '" + startDate +"' AND '" + endDate + "'";
            if (category.equals("全部")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu +" OR XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR ZHUANGVALUE in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND (XIAN1%2!=0 OR XIAN2%2!=0 OR XIAN3%2!=0 OR XIANVALUE%2!=0 OR ZHUANG1%2!=0 OR ZHUANG2%2!=0 OR ZHUANG3%2!=0 OR ZHUANGVALUE%2!=0)";
                } else {
                    sql = sql + " AND (XIAN1%2!=0 OR XIAN2%2!=0 OR XIAN3%2!=0 OR XIANVALUE%2!=0 OR ZHUANG1%2!=0 OR ZHUANG2%2!=0 OR ZHUANG3%2!=0 OR ZHUANGVALUE%2!=0)";
                }
            } else if(category.equals("庄")){
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu + " OR ZHUANGVALUE in " + qu +")" +
                            " AND (ZHUANG1%2!=0 OR ZHUANG2%2!=0 OR ZHUANG3%2!=0 OR ZHUANGVALUE%2!=0)";
                } else {
                    sql = sql +  " AND (ZHUANG1%2!=0 OR ZHUANG2%2!=0 OR ZHUANG3%2!=0 OR ZHUANGVALUE%2!=0)";
                }
            } else if (category.equals("闲")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND (XIAN1%2!=0 OR XIAN2%2!=0 OR XIAN3%2!=0 OR XIANVALUE%2!=0)";
                } else {
                    sql = sql +  " AND (XIAN1%2!=0 OR XIAN2%2!=0 OR XIAN3%2!=0 OR XIANVALUE%2!=0)";
                }
            }
        } else if (category2.equals("双数")) {
            sql = sql + " WHERE TIME BETWEEN '" + startDate +"' AND '" + endDate + "'";
            if (category.equals("全部")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu +" OR XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR ZHUANGVALUE in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND (XIAN1%2=0 OR XIAN2%2=0 OR XIAN3%2=0 OR XIANVALUE%2=0 OR ZHUANG1%2=0 OR ZHUANG2%2=0 OR ZHUANG3%2=0 OR ZHUANGVALUE%2=0)";
                } else {
                    sql = sql + " AND (XIAN1%2=0 OR XIAN2%2=0 OR XIAN3%2=0 OR XIANVALUE%2=0 OR ZHUANG1%2=0 OR ZHUANG2%2=0 OR ZHUANG3%2=0 OR ZHUANGVALUE%2=0)";
                }
            } else if(category.equals("庄")){
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (ZHUANG1 in " + qu +" OR ZHUANG2 in " + qu +" OR ZHUANG3 in " + qu + " OR ZHUANGVALUE in " + qu +")" +
                            " AND (ZHUANG1%2=0 OR ZHUANG2%2=0 OR ZHUANG3%2=0 OR ZHUANGVALUE%2=0)";
                } else {
                    sql = sql +  " AND (ZHUANG1%2=0 OR ZHUANG2%2=0 OR ZHUANG3%2=0 OR ZHUANGVALUE%2=0)";
                }
            } else if (category.equals("闲")) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(query)) {
                    String qu = StringUtils.formatSql(query);
                    sql = sql + " AND (XIAN1 in " + qu +" OR XIAN2 in " + qu +" OR XIAN3 in " + qu +" OR XIANVALUE in " + qu +")" +
                            " AND (XIAN1%2=0 OR XIAN2%2=0 OR XIAN3%2=0 OR XIANVALUE%2=0)";
                } else {
                    sql = sql + " AND (XIAN1%2=0 OR XIAN2%2=0 OR XIAN3%2=0 OR XIANVALUE%2=0)";
                }
            }
        }
        return sql;
    }

    @Override
    public int findReetListCount(String category, String category2, String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT Count(*) AS NUMBER FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category, category2, startDate, endDate, query, sql);
        return mainManageMapper.findReetListCount(sql);
    }
}
