package com.rhino.bjl.server.impl;

import com.rhino.bjl.bean.*;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.*;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class MainMessage implements IMainMessage {

    public static final Logger logger = Logger.getLogger(MainMessage.class.getSimpleName());

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
                logger.info("从Redis取值map1");
            }
            if (map1 != null) {
                int p2 = point - 3;
                HashMap<String, Object> map2 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p2);
                if (map2 == null) {
                    map2 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p2);
                    redisCacheManager.hmset(roomId + "_" + p2,map2,AppConstans.REDIS_TIME);
                }else {
                    logger.info("从Redis取值map2");
                }
                if (map2 != null) {
                    int p3 = point - 2;
                    HashMap<String, Object> map3 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p3);
                    if (map3 == null) {
                        map3 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p3);
                        redisCacheManager.hmset(roomId + "_" + p3,map3,AppConstans.REDIS_TIME);
                    }else {
                        logger.info("从Redis取值map3");
                    }
                    if (map3 != null) {
                        int p4 = point - 1;
                        HashMap<String, Object> map4 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p4);
                        if (map4 == null) {
                            map4 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p4);
                            redisCacheManager.hmset(roomId + "_" + p4,map4,AppConstans.REDIS_TIME);
                        }else {
                            logger.info("从Redis取值map4");
                        }
                        if (map4 != null) {
                            Map<String,Object> sCheckResultOldEven = checkOldEven(map1,map2,map3,map4);//MNAB
                            Map<String,Object> sCheckResultMaxMin = checkMaxMin(map1,map2,map3,map4);//LSAB
                            //MNLS/MN/LS/AB  查四组

                            MorphDynaBean maxMin = (MorphDynaBean) sCheckResultMaxMin.get("object"); //LSAB
                            MorphDynaBean oldEven = (MorphDynaBean) sCheckResultOldEven.get("object");//MNAB
                            //插入数据库
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            String id = UUID.randomUUID().toString();
                            params.put("ID", id);
                            params.put("ROOMID", roomId);
                            params.put("TIME", DateUtils.getDate5());
                            params.put("POINT", point);
                            Integer maxMinResultA = (Integer) maxMin.get("a");
                            Integer maxMinResultB = (Integer) maxMin.get("b");
                            Integer maxMinResultC = (Integer) maxMin.get("c");
                            params.put("MAXMINRESULTA", maxMinResultA);
                            params.put("MAXMINRESULTB", maxMinResultB);
                            params.put("MAXMINRESULTC", maxMinResultC);
                            params.put("MAXMINRESULTTYPE", maxMin.get("type"));
                            Integer oldevenResultA = (Integer) oldEven.get("a");
                            Integer oldevenResultB = (Integer) oldEven.get("b");
                            Integer oldevenResultC = (Integer) oldEven.get("c");
                            params.put("OLDEVENRESULTA", oldevenResultA);
                            params.put("OLDEVENRESULTB", oldevenResultB);
                            params.put("OLDEVENRESULTC", oldevenResultC);
                            params.put("OLDEVENRESULTTYPE", oldEven.get("type"));
                            //大小值预测结果
                            String maxMinR,oldEvenR,value;
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
                            params.put("MAXMINRESULTAVALUE", maxMinR);
                            //奇偶值预测结果
                            if (oldevenResultA > oldevenResultB) {
                                if(oldevenResultA > oldevenResultC) {
                                    oldEvenR = "庄";
                                } else {
                                    oldEvenR = "和";
                                }
                            } else {
                                if(oldevenResultB > oldevenResultC) {
                                    oldEvenR = "闲";
                                } else {
                                    oldEvenR = "和";
                                }
                            }
                            params.put("OLDEVENRESULTAVALUE", oldEvenR);
                            Integer valueA = Integer.parseInt(zhuangdian);
                            Integer valueB = Integer.parseInt(xiandian);
                            if (valueA > valueB) {
                                value = "庄";
                            } else if (valueA == valueB) {
                                value = "和";
                            } else {
                                value = "闲";
                            }
                            params.put("VALUE", value);
                            if (maxMinR.equals(value)) {
                                params.put("MAXMINRESULTVALUE", "A");
                            } else {
                                params.put("MAXMINRESULTVALUE", "B");
                            }
                            if (oldEvenR.equals(value)) {
                                params.put("OLDEVENRESULTVALUE", "A");
                            } else {
                                params.put("OLDEVENRESULTVALUE", "B");
                            }
                            //数据准备结束，插入数据库
                            boolean isResult = mainManageMapper.saveReetAnaly(params);
                        }
                    }
                }
            }
        }
    }

    private Map<String,Object> checkMaxMin(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> sMaxMinBeansList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sMaxMinBean1 = new HashMap<String, Object>();
        sMaxMinBean1.put("minCount",map1.get("MINCOUNT"));
        sMaxMinBean1.put("maxCount",map1.get("MAXCOUNT"));
        sMaxMinBean1.put("valueA",map1.get("ZHUANGVALUE"));
        sMaxMinBean1.put("valueB",map1.get("XIANVALUE"));
        sMaxMinBeansList.add(sMaxMinBean1);
        Map<String,Object> sMaxMinBean2 = new HashMap<String, Object>();
        sMaxMinBean2.put("minCount",map2.get("MINCOUNT"));
        sMaxMinBean2.put("maxCount",map2.get("MAXCOUNT"));
        sMaxMinBean2.put("valueA",map2.get("ZHUANGVALUE"));
        sMaxMinBean2.put("valueB",map2.get("XIANVALUE"));
        sMaxMinBeansList.add(sMaxMinBean2);
        Map<String,Object> sMaxMinBean3 = new HashMap<String, Object>();
        sMaxMinBean3.put("minCount",map3.get("MINCOUNT"));
        sMaxMinBean3.put("maxCount",map3.get("MAXCOUNT"));
        sMaxMinBean3.put("valueA",map3.get("ZHUANGVALUE"));
        sMaxMinBean3.put("valueB",map3.get("XIANVALUE"));
        sMaxMinBeansList.add(sMaxMinBean3);
        Map<String,Object> sMaxMinBean4 = new HashMap<String, Object>();
        sMaxMinBean4.put("minCount",map4.get("MINCOUNT"));
        sMaxMinBean4.put("maxCount",map4.get("MAXCOUNT"));
        sMaxMinBean4.put("valueA",map4.get("ZHUANGVALUE"));
        sMaxMinBean4.put("valueB",map4.get("XIANVALUE"));
        sMaxMinBeansList.add(sMaxMinBean4);
        String json = JsonUtil.toJsonString(sMaxMinBeansList);
        //System.out.println("服务器查询数据：" + json);
        logger.info("服务器大小数查询数据：" + json);
        String maxMinResutl = HttpUtils.post("http://47.244.48.105:8091/reet_tbl/getValueTypeByB",json);
        logger.info("服务器大小数查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String,Object> checkOldEven(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> sOldEvenBeanList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sOldEvenBean1 = new HashMap<String, Object>();
        sOldEvenBean1.put("jiShuCount",map1.get("JISHUCOUNT"));
        sOldEvenBean1.put("ouShuCount",map1.get("OUSHUCOUNT"));
        sOldEvenBean1.put("valueA",map1.get("ZHUANGVALUE"));
        sOldEvenBean1.put("valueB",map1.get("XIANVALUE"));
        sOldEvenBeanList.add(sOldEvenBean1);
        Map<String,Object> sOldEvenBean2 = new HashMap<String, Object>();
        sOldEvenBean2.put("jiShuCount",map2.get("JISHUCOUNT"));
        sOldEvenBean2.put("ouShuCount",map2.get("OUSHUCOUNT"));
        sOldEvenBean2.put("valueA",map2.get("ZHUANGVALUE"));
        sOldEvenBean2.put("valueB",map2.get("XIANVALUE"));
        sOldEvenBeanList.add(sOldEvenBean2);
        Map<String,Object> sOldEvenBean3 = new HashMap<String, Object>();
        sOldEvenBean3.put("jiShuCount",map3.get("JISHUCOUNT"));
        sOldEvenBean3.put("ouShuCount",map3.get("OUSHUCOUNT"));
        sOldEvenBean3.put("valueA",map3.get("ZHUANGVALUE"));
        sOldEvenBean3.put("valueB",map3.get("XIANVALUE"));
        sOldEvenBeanList.add(sOldEvenBean3);
        Map<String,Object> sOldEvenBean4 = new HashMap<String, Object>();
        sOldEvenBean4.put("jiShuCount",map4.get("JISHUCOUNT"));
        sOldEvenBean4.put("ouShuCount",map4.get("OUSHUCOUNT"));
        sOldEvenBean4.put("valueA",map4.get("ZHUANGVALUE"));
        sOldEvenBean4.put("valueB",map4.get("XIANVALUE"));
        sOldEvenBeanList.add(sOldEvenBean4);
        String json = JsonUtil.toJsonString(sOldEvenBeanList);
        logger.info("服务器奇偶数查询数据：" + json);
        String oldEvenResutl = HttpUtils.post("http://47.244.48.105:8091/reet_tbl/getValueTypeByA",json);
        logger.info("服务器奇偶数查询数据结果：" + oldEvenResutl);
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
        logger.info("小局数据插入服务器" + json);
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
            sql = sql + " AND XIANCOUNT - ZHUANGCOUNT > -7 AND XIANCOUNT - ZHUANGCOUNT < 7";
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
        return mainManageMapper.updateReetByRoomId(map);
    }

    public boolean deleteReetByRoomId(String roomId) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("ROOMID",roomId);
        return mainManageMapper.deleteReetByRoomId(map);
    }
}
