package com.rhino.bjl.server.impl;

import com.rhino.bjl.bean.*;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.*;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Service
public class MainMessage implements IMainMessage {

    public static final Logger logger = Logger.getLogger(MainMessage.class.getSimpleName());
    //查询接口地址
    private String url = "http://47.244.48.105:8091/reet_tbl/getValueTypeCount";

    @Autowired
    private MainManageMapper mainManageMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;

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
        //统计大数小数
        int maxCount = 0;
        int minCount = 0;
        MaxMinBean maxMinBean = StringUtils.setMaxMin(zhuang1,zhuang2,zhuang3,xian1,xian2,xian3,maxCount,minCount);
        maxCount = maxMinBean.getMaxCount();
        minCount = maxMinBean.getMinCount();
        params.put("MAXCOUNT", maxCount);
        params.put("MINCOUNT", minCount);

        params.put("JISHUCOUNT", jishu);
        params.put("OUSHUCOUNT", oushu);
        params.put("LINGCOUNT", ling);
        if (mainManageMapper.saveReetData(params)) {
            //同步保存数据到redis 1小时
            redisCacheManager.hmset(roomId + "_" + juCount,params, AppConstans.REDIS_TIME);
            //服务器数据预测系统分析
            try {
                analyzeData(roomId,juCount,zhuangdian,xiandian);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 同步提交数据到服务器中
            boolean isSubmit = addReetToWeb(id,roomId,zhuangdian,xiandian,juCount,jishu,oushu,ling,maxCount,minCount);
            //下面为更新房间数据，统计的是房间总数。
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

    private void analyzeData(String roomId, String juCount, String zhuangdian, String xiandian) throws Exception{
        int point = Integer.parseInt(juCount);
        if (point > 4) {
            //查询出前3局数据，有限从Redis中获取
            int p1 = point - 4;
            HashMap<String, Object> map1 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p1);
            if (map1 == null) {
                map1 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p1);
                redisCacheManager.hmset(roomId + "_" + p1,map1,AppConstans.REDIS_TIME);
            } else {
                logger.error("从Redis取值map1");
            }
            if (map1 != null) {
                int p2 = point - 3;
                HashMap<String, Object> map2 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p2);
                if (map2 == null) {
                    map2 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p2);
                    redisCacheManager.hmset(roomId + "_" + p2,map2,AppConstans.REDIS_TIME);
                }
                if (map2 != null) {
                    int p3 = point - 2;
                    HashMap<String, Object> map3 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p3);
                    if (map3 == null) {
                        map3 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p3);
                        redisCacheManager.hmset(roomId + "_" + p3,map3,AppConstans.REDIS_TIME);
                    }
                    if (map3 != null) {
                        int p4 = point - 1;
                        HashMap<String, Object> map4 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p4);
                        if (map4 == null) {
                            map4 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p4);
                            redisCacheManager.hmset(roomId + "_" + p4,map4,AppConstans.REDIS_TIME);
                        }
                        if (map4 != null) {
                            //插入数据库
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            String id = UUID.randomUUID().toString();
                            params.put("ID", id);
                            params.put("ROOMID", roomId);
                            params.put("TIME", DateUtils.getDate5());
                            params.put("POINT", point);
                            Integer valueA = Integer.parseInt(zhuangdian);
                            Integer valueB = Integer.parseInt(xiandian);
                            String value;
                            if (valueA > valueB) {
                                value = "庄";
                            } else if (valueA == valueB) {
                                value = "和";
                            } else {
                                value = "闲";
                            }
                            params.put("VALUE", value);
                            //LSAB
                            Map<String,Object> sCheckResultMaxMin = checkMaxMin(map1,map2,map3,map4);
                            setCheckData(sCheckResultMaxMin,params,value,"MAXMINRESULTA","MAXMINRESULTB","MAXMINRESULTC","MAXMINRESULTTYPE","MAXMINRESULTAVALUE","MAXMINRESULTVALUE");
                            //MNAB
                            Map<String,Object> sCheckResultOldEven = checkOldEven(map1,map2,map3,map4);
                            setCheckData(sCheckResultOldEven,params,value,"OLDEVENRESULTA","OLDEVENRESULTB","OLDEVENRESULTC","OLDEVENRESULTTYPE","OLDEVENRESULTAVALUE","OLDEVENRESULTVALUE");
                            //MNLS
                            Map<String,Object> sCheckMnLs = checkMnLs(map1,map2,map3,map4);
                            setCheckData(sCheckMnLs,params,value,"MNLSRESULTA","MNLSRESULTB","MNLSRESULTC","MNLSRESULTTYPE","MNLSRESULTAVALUE","MNLSRESULTVALUE");
                            //MN
                            Map<String,Object> sCheckMn = checkMn(map1,map2,map3,map4);
                            setCheckData(sCheckMn,params,value,"MNRESULTA","MNRESULTB","MNRESULTC","MNRESULTTYPE","MNRESULTAVALUE","MNRESULTVALUE");
                            // LS
                            Map<String,Object> sCheckLs = checkLs(map1,map2,map3,map4);
                            setCheckData(sCheckLs,params,value,"LSRESULTA","LSRESULTB","LSRESULTC","LSRESULTTYPE","LSRESULTAVALUE","LSRESULTVALUE");
                            // AB
                            Map<String,Object> sCheckAb = checkAb(map1,map2,map3,map4);
                            setCheckData(sCheckAb,params,value,"ABRESULTA","ABRESULTB","ABRESULTC","ABRESULTTYPE","ABRESULTAVALUE","ABRESULTVALUE");
                            //数据准备结束，插入数据库
                            boolean isResult = mainManageMapper.saveReetAnaly(params);
                        }
                    }
                }
            }
        }
    }

    private void setCheckData(Map<String,Object> sCheckResultMaxMin,HashMap<String, Object> params,String value, String maxminresulta, String maxminresultb, String maxminresultc, String maxminresulttype, String maxminresultavalue, String maxminresultvalue) {
        MorphDynaBean maxMin = (MorphDynaBean) sCheckResultMaxMin.get("object");
        Integer maxMinResultA = (Integer) maxMin.get("a");
        Integer maxMinResultB = (Integer) maxMin.get("b");
        Integer maxMinResultC = (Integer) maxMin.get("c");
        params.put(maxminresulta, maxMinResultA);
        params.put(maxminresultb, maxMinResultB);
        params.put(maxminresultc, maxMinResultC);
        params.put(maxminresulttype, maxMin.get("type"));
        //大小值预测结果
        String maxMinR;
        if (maxMinResultA == 0 && maxMinResultB == 0 && maxMinResultC == 0) {
            params.put(maxminresultavalue, "未知");
            params.put(maxminresultvalue, "C");
        } else if(maxMinResultA == maxMinResultB && maxMinResultA >  maxMinResultC) {
            params.put(maxminresultavalue, "未知");
            params.put(maxminresultvalue, "C");
        } else if( maxMinResultA == maxMinResultC && maxMinResultA > maxMinResultB) {
            params.put(maxminresultavalue, "未知");
            params.put(maxminresultvalue, "C");
        } else if(maxMinResultB == maxMinResultC && maxMinResultC > maxMinResultA) {
            params.put(maxminresultavalue, "未知");
            params.put(maxminresultvalue, "C");
        } else {
            if (maxMinResultA > maxMinResultB) {
                if(maxMinResultA > maxMinResultC) {
                    maxMinR =  "庄";
                } else {
                    maxMinR = "和";
                }
            } else {
                if(maxMinResultB > maxMinResultC) {
                    maxMinR = "闲";
                } else {
                    maxMinR =  "和";
                }
            }
            params.put(maxminresultavalue, maxMinR);
            if("和".equals(value)) {
                params.put(maxminresultvalue, "C"); //结果是和记录为未知
            } else {
                if (maxMinR.equals(value)) {
                    params.put(maxminresultvalue, "A");
                } else {
                    params.put(maxminresultvalue, "B");
                }
            }
        }
    }

    private Map<String, Object> checkAb(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnAbList = new ArrayList<Map<String,Object>>();
        MapUtils.setAb(mnAbList,map1);
        MapUtils.setAb(mnAbList,map2);
        MapUtils.setAb(mnAbList,map3);
        MapUtils.setAb(mnAbList,map4);
        String json = JsonUtil.toJsonString(mnAbList);
        logger.error("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.error("服务器MNLS查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String, Object> checkLs(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnLsList = new ArrayList<Map<String,Object>>();
        MapUtils.setLs(mnLsList,map1);
        MapUtils.setLs(mnLsList,map2);
        MapUtils.setLs(mnLsList,map3);
        MapUtils.setLs(mnLsList,map4);
        String json = JsonUtil.toJsonString(mnLsList);
        logger.error("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.error("服务器MNLS查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String, Object> checkMn(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnLsList = new ArrayList<Map<String,Object>>();
        MapUtils.setMn(mnLsList,map1);
        MapUtils.setMn(mnLsList,map2);
        MapUtils.setMn(mnLsList,map3);
        MapUtils.setMn(mnLsList,map4);
        String json = JsonUtil.toJsonString(mnLsList);
        logger.error("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.error("服务器MNLS查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String, Object> checkMnLs(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnLsList = new ArrayList<Map<String,Object>>();
        MapUtils.setMnLs(mnLsList,map1);
        MapUtils.setMnLs(mnLsList,map2);
        MapUtils.setMnLs(mnLsList,map3);
        MapUtils.setMnLs(mnLsList,map4);
        String json = JsonUtil.toJsonString(mnLsList);
        logger.error("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.error("服务器MNLS查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String,Object> checkMaxMin(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> sMaxMinBeansList = new ArrayList<Map<String,Object>>();
        MapUtils.setLsAb(sMaxMinBeansList,map1);
        MapUtils.setLsAb(sMaxMinBeansList,map2);
        MapUtils.setLsAb(sMaxMinBeansList,map3);
        MapUtils.setLsAb(sMaxMinBeansList,map4);
        String json = JsonUtil.toJsonString(sMaxMinBeansList);
        logger.error("服务器大小数查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.error("服务器大小数查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String,Object> checkOldEven(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> sOldEvenBeanList = new ArrayList<Map<String,Object>>();
        MapUtils.setMnAb(sOldEvenBeanList,map1);
        MapUtils.setMnAb(sOldEvenBeanList,map2);
        MapUtils.setMnAb(sOldEvenBeanList,map3);
        MapUtils.setMnAb(sOldEvenBeanList,map4);
        String json = JsonUtil.toJsonString(sOldEvenBeanList);
        logger.error("服务器奇偶数查询数据：" + json);
        String oldEvenResutl = HttpUtils.post(url,json);
        logger.error("服务器奇偶数查询数据结果：" + oldEvenResutl);
        Map<String,Object> map = JsonUtil.getMap(oldEvenResutl);
        return map;
    }

    /**
     * 上传数据到服务器
     * @return
     */
    private boolean addReetToWeb(String id, String roomId, String zhuangdian, String xiandian, String juCount, int jishu, int oushu, int ling, int maxCount, int minCount) {
       boolean result = true;
       String value = "A";
       int valueA = Integer.parseInt(zhuangdian);
       int valueB = Integer.parseInt(xiandian);
        if(valueA > valueB) {
            value = "A";
        } else if (valueA < valueB) {
            value = "B";
        } else {
            value = "C";
        }
        SReetBean reetBean = new SReetBean();
        reetBean.setId(id);
        reetBean.setRoomId(roomId);
        reetBean.setValueA(valueA);
        reetBean.setValueB(valueB);
        reetBean.setValue(value);
        reetBean.setJiShuCount(jishu);
        reetBean.setOuShuCount(oushu);
        reetBean.setMaxCount(maxCount);
        reetBean.setMinCount(minCount);
        reetBean.setPoint(Integer.parseInt(juCount));
        reetBean.setLingCount(ling);
        String json = JsonUtil.getJsonString4JavaPOJO(reetBean);
        logger.error("小局数据插入服务器" + json);
        String urlResult = HttpUtils.post("http://47.244.48.105:8091/reet_tbl/addReet",json);
        System.out.println("小局数据插入服务器结果:" + urlResult);
        return result;
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
    public List<HashMap<String, Object>> findRoomList(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx,String trent) {
        String sql = "select * from room_tbl where USERID='" + userId+ "'";
        sql = getStringSql(qxqiang, dsqiang, lz, lx, trent,sql);
        sql = sql + " ORDER BY STRARTTIME DESC limit "+ start +"," + limit;
        return mainManageMapper.findRoomList(sql);
    }

    private String getStringSql(String qxqiang, String dsqiang, String lz, String lx,String trent, String sql) {
        if (trent.equals("庄")) {
            sql = sql + " AND ZHUANGCOUNT > XIANCOUNT + 7";
        } else if (trent.equals("闲")) {
            sql = sql + " AND XIANCOUNT > ZHUANGCOUNT + 7";
        } else if (trent.equals("中")) {
           // sql = sql + " AND XIANCOUNT - ZHUANGCOUNT > -7 AND XIANCOUNT - ZHUANGCOUNT < 7";
            sql = sql + " AND XIANCOUNT - ZHUANGCOUNT between -7 AND 7"; //MySQL 保护-7和7
        }
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
    public int findRoomListCount(int start, int limit,String userId,String qxqiang,String dsqiang,String lz,String lx,String trent) {
        String sql = "select Count(*) AS NUMBER from room_tbl where USERID='" + userId + "'";
        sql = getStringSql(qxqiang, dsqiang, lz, lx,trent, sql);
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
        //更新服务器数据
        Map<String,Object> map2 = new HashMap<String, Object>();
        map2.put("tRent",qiang);
        map2.put("heCount",roomMap.get("HECOUNT"));
        map2.put("roomId",roomId);
        String json = JsonUtil.toJsonString(map2);
        String rest = HttpUtils.post("http://47.244.48.105:8091/reet_tbl/updReet",json);
        System.out.println("更新服务器reet" + rest);
        //删除Redis
        Integer total = (Integer) roomMap.get("TOTALCOUNT");
        for(int i=1;i<total+1;i++) {
            redisCacheManager.del(roomId + "_" + total);
        }
        return mainManageMapper.updateReetByRoomId(map);
    }

    @Override
    public void findRoomzql(HashMap<String, Object> map, String roomId) {
        HashMap<String,Object> map2 = new HashMap<String, Object>();
        map2.put("ROOMID",roomId);
        List<HashMap<String,Object>> listMap = mainManageMapper.findRoomzql(map2);
        int mna=0,mnb=0,mnc=0, lsa=0,lsb=0,lsc=0,aba=0,abb=0,abc=0,mnlsa=0,mnlsb=0,mnlsc=0,
                mnaba=0,mnabb=0,mnabc=0,lsaba=0,lsabb=0,lsabc=0;
        for (int i=0;i<listMap.size();i++) {
            String mnstr = (String) listMap.get(i).get("MNRESULTVALUE");
            String lsstr = (String) listMap.get(i).get("LSRESULTVALUE");
            String abstr = (String) listMap.get(i).get("ABRESULTVALUE");
            String mnlsstr = (String) listMap.get(i).get("MNLSRESULTVALUE");
            String mnabstr = (String) listMap.get(i).get("OLDEVENRESULTVALUE");
            String lsabstr = (String) listMap.get(i).get("MAXMINRESULTVALUE");
            if (mnstr.equals("A")) {
                mna = mna + 1;
            } else if (mnstr.equals("B")) {
                mnb = mnb + 1;
            } else if (mnstr.equals("C")) {
                mnc = mnc + 1;
            }
            if (lsstr.equals("A")) {
                lsa = lsa + 1;
            } else if (lsstr.equals("B")) {
                lsb = lsb + 1;
            } else if (lsstr.equals("C")) {
                lsc = lsc + 1;
            }
            if (abstr.equals("A")) {
                aba = aba + 1;
            } else if (abstr.equals("B")) {
                abb = abb + 1;
            } else if (abstr.equals("C")) {
                abc = abc + 1;
            }
            if (mnlsstr.equals("A")) {
                mnlsa = mnlsa + 1;
            } else if (mnlsstr.equals("B")) {
                mnlsb = mnlsb + 1;
            } else if (mnlsstr.equals("C")) {
                mnlsc = mnlsc + 1;
            }
            if (mnabstr.equals("A")) {
                mnaba = mnaba + 1;
            } else if (mnabstr.equals("B")) {
                mnabb = mnabb + 1;
            } else if (mnabstr.equals("C")) {
                mnabc = mnabc + 1;
            }
            if (lsabstr.equals("A")) {
                lsaba = lsaba + 1;
            } else if (lsabstr.equals("B")) {
                lsabb = lsabb + 1;
            } else if (lsabstr.equals("C")) {
                lsabc = lsabc + 1;
            }
        }
        int mnToal = mna + mnb + mnc;
        String mn = "对：" + getPercent(mna,mnToal) + ";错：" + getPercent(mnb,mnToal) + ";未知：" + getPercent(mnc,mnToal);
        map.put("mn",mn);
        int lsToal = lsa + lsb + lsc;
        String ls = "对：" + getPercent(lsa,lsToal) + ";错：" + getPercent(lsb,lsToal) + ";未知：" + getPercent(lsc,lsToal);
        map.put("ls",ls);
        int abToal = aba + abb + abc;
        String ab = "对：" + getPercent(aba,abToal) + ";错：" + getPercent(abb,abToal) + ";未知：" + getPercent(abc,abToal);
        map.put("ab",ab);
        int mnlsToal = mnlsa + mnlsb + mnlsc;
        String mnls = "对：" + getPercent(mnlsa,mnlsToal) + ";错：" + getPercent(mnlsb,mnlsToal) + ";未知：" + getPercent(mnlsc,mnlsToal);
        map.put("mnls",mnls);
        int mnabToal = mnaba + mnabb + mnabc;
        String mnab = "对：" + getPercent(mnaba,mnabToal) + ";错：" + getPercent(mnabb,mnabToal) + ";未知：" + getPercent(mnabc,mnabToal);
        map.put("mnab",mnab);
        int lsabToal = lsaba + lsabb + lsabc;
        String lsab = "对：" + getPercent(lsaba,lsabToal) + ";错：" + getPercent(lsabb,lsabToal) + ";未知：" + getPercent(lsabc,lsabToal);
        map.put("lsab",lsab);
    }

    public String getPercent(double num, double total) {
        if (total == 0) {
            return "0.0%";
        }
        int scale = 1;
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num) + "%";
    }


    public boolean deleteReetByRoomId(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        return mainManageMapper.deleteReetByRoomId(map);
    }
}
