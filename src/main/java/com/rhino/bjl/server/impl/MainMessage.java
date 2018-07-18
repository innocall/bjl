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
    public boolean updateUserMoneyByUserId(String userId, float money) {
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
        params.put("JISHUCOUNT", "0");
        params.put("OUSHUCOUNT", "0");
        params.put("HESHUCOUNT", "0");
       // params.put("ENDTIME", 0); //结束时间
        if (mainManageMapper.saveRoomData(params)) {
            return id;
        }
        return "";
    }

    @Override
    public String saveReetData(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String touzhuMoney, String userId, String roomId, String radio,String zhuangdian,String xiandian,String juCount) {
        int jishu = 0;
        int oushu = 0;
        int ling = 0;
        HashMap<String,Object> params = new HashMap<String,Object>();
        zhuang1 = StringUtils.getCountFormat(zhuang1);
        zhuang2 = StringUtils.getCountFormat(zhuang2);
        zhuang3 = StringUtils.getCountFormat(zhuang3);
        xian1 = StringUtils.getCountFormat(xian1);
        xian2 = StringUtils.getCountFormat(xian2);
        xian3 = StringUtils.getCountFormat(xian3);
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
        //统计 一组牌 基数 偶数个数
        if (StringUtils.getCountType(zhuang1) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(zhuang1) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(zhuang1) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(zhuang2) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(zhuang2) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(zhuang2) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(zhuang3) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(zhuang3) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(zhuang3) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(xian1) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(xian1) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(xian1) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(xian2) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(xian2) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(xian2) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(xian3) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(xian3) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(xian3) == 2) {
            oushu = oushu + 1;
        }
      /*  单双数 不统计庄闲值
       if (StringUtils.getCountType(zhuangdian) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(zhuangdian) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(zhuangdian) == 2) {
            oushu = oushu + 1;
        }
        if (StringUtils.getCountType(xiandian) == 0) {
            ling = ling + 1;
        } else if (StringUtils.getCountType(xiandian) == 1) {
            jishu = jishu + 1;
        } else if (StringUtils.getCountType(xiandian) == 2) {
            oushu = oushu + 1;
        }*/
      if(Integer.parseInt(zhuangdian) > Integer.parseInt(xiandian)) {
          params.put("VALUE", "庄");
      } else if (Integer.parseInt(zhuangdian) < Integer.parseInt(xiandian)) {
          params.put("VALUE", "闲");
      } else {
          params.put("VALUE", "和");
      }
        params.put("JISHUCOUNT", jishu);
        params.put("OUSHUCOUNT", oushu);
        params.put("LINGCOUNT", ling);
        if (mainManageMapper.saveReetData(params)) {
            //更新房间的奇偶数，庄数，闲数，对数
            HashMap<String,Object> hashMap = findRoomById(roomId);
            jishu = Integer.parseInt(hashMap.get("JISHUCOUNT").toString()) + jishu;
            oushu = Integer.parseInt(hashMap.get("OUSHUCOUNT").toString()) + oushu;
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("JISHUCOUNT", jishu);
            map.put("OUSHUCOUNT", oushu);
            map.put("ID", roomId);
            boolean isParam = mainManageMapper.updateRoomCountData(map);
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
//        params.put("JISHUCOUNT", "0");
//        params.put("OUSHUCOUNT", "0");
//        params.put("HESHUCOUNT", "0");
        return mainManageMapper.updateRoomData(params);
    }

    @Override
    public List<HashMap<String, Object>> findReetList(String category, String jishu,String oushu,String ling, String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT * FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category, jishu,oushu,ling, startDate, endDate, query, sql);
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

    private String formatSqls(String category,String jishu,String oushu,String ling,String startDate, String endDate, String query, String sql) {
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
        if (org.apache.commons.lang.StringUtils.isNotBlank(jishu)) {
            sql = sql + " AND JISHUCOUNT =" + jishu;
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(oushu)) {
            sql = sql + " AND OUSHUCOUNT =" + oushu;
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(ling)) {
            sql = sql + " AND LINGCOUNT =" + ling;
        }
        return sql;
    }

    @Override
    public int findReetListCount(String category,String jishu,String oushu,String ling,String startDate, String endDate, String query, int start, int limit) {
        String sql = "SELECT Count(*) AS NUMBER FROM reet_tbl";
        //判断单 双
        sql = formatSqls(category,jishu,oushu,ling,startDate, endDate, query, sql);
        return mainManageMapper.findReetListCount(sql);
    }

    @Override
    public List<HashMap<String, Object>> findReetByRoomId(String roomId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("ROOMID", roomId);
        return mainManageMapper.findReetByRoomId(params);
    }

    @Override
    public List<HashMap<String, Object>> findRoomList(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx) {
        String sql = "select * from room_tbl where USERID='" + userId+ "'";
        sql = getStringSql(qxqiang, dsqiang, lz, lx, sql);
        sql = sql + " ORDER BY STRARTTIME DESC limit "+ start +"," + limit;
        return mainManageMapper.findRoomList(sql);
    }

    private String getStringSql(String qxqiang, String dsqiang, String lz, String lx, String sql) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(qxqiang)) {
            //庄闲强
            if (qxqiang.equals("庄强")) {
                sql = sql + " AND ZHUANGCOUNT > XIANCOUNT";
            } else  if (qxqiang.equals("闲强")) {
                sql = sql + " AND ZHUANGCOUNT < XIANCOUNT";
            }
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(dsqiang)) {
            //单双强
            if (dsqiang.equals("N强")) {
                sql = sql + " AND JISHUCOUNT > OUSHUCOUNT";
            } else  if (dsqiang.equals("M强")) {
                sql = sql + " AND JISHUCOUNT < OUSHUCOUNT";
            }
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(lz)) {
            //连庄数

        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(lx)) {
            //连闲数

        }
        return sql;
    }

    @Override
    public int findRoomListCount(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx) {
        String sql = "select Count(*) AS NUMBER from room_tbl where USERID='" + userId + "'";
        sql = getStringSql(qxqiang, dsqiang, lz, lx, sql);
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

    @Override
    public HashMap<String, Object> findRoomById(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        return mainManageMapper.findRoomById(map);
    }

    @Override
    public boolean updateReetByRoomId(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        HashMap<String ,Object> roomMap = findRoomById(roomId);
        int zhuang = (Integer) roomMap.get("ZHUANGCOUNT");
        int xian = (Integer) roomMap.get("XIANCOUNT");
        String qiang = "0";
        int s = zhuang - xian;
        if (s > 7) {
            qiang = "1";
        } else if (s < 8 && s > -8) {
            qiang = "0";
        } else {
            qiang = "2";
        }
        map.put("TRENT",qiang);
        map.put("HECOUNT",roomMap.get("HECOUNT"));
        return mainManageMapper.updateReetByRoomId(map);
    }

    public boolean deleteReetByRoomId(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        return mainManageMapper.deleteReetByRoomId(map);
    }
}
