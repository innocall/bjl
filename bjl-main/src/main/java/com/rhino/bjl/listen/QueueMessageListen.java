package com.rhino.bjl.listen;

import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.impl.ActiveMqMessage;
import com.rhino.bjl.utils.*;
import net.sf.ezmorph.bean.MorphDynaBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.*;

public class QueueMessageListen implements MessageListener {

    public static final Logger logger = Logger.getLogger(QueueMessageListen.class);

    //查询接口地址
    private String url = "http://47.244.48.105:8091/reet_tbl/getValueTypeCount";

    @Autowired
    private MainManageMapper mainManageMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage)(message);
            String msg = tm.getText();
            logger.info("消息处理:" + msg);
            HashMap<String, Object> params = (HashMap<String, Object>) JsonUtil.getMap(msg);
            String roomId = (String) params.get("roomId");
            String juCount = (String) params.get("juCount");
            String zhuangdian = (String) params.get("zhuangdian");
            String xiandian = (String) params.get("xiandian");
            analyzeData(roomId,juCount,zhuangdian,xiandian);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analyzeData(String roomId, String juCount, String zhuangdian, String xiandian) throws Exception{
        int point = Integer.parseInt(juCount);
        //查询出前3局数据，有限从Redis中获取
        int p1 = point - 4;
        HashMap<String, Object> map1 = (HashMap<String, Object>) redisCacheManager.hmget(roomId + "_" + p1);
        if (map1 == null) {
            map1 =  mainManageMapper.findReetByRoomIdAndPoint(roomId,p1);
            redisCacheManager.hmset(roomId + "_" + p1,map1, AppConstans.REDIS_TIME);
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
                        int valueA = Integer.parseInt(zhuangdian);
                        int valueB = Integer.parseInt(xiandian);
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
                        setCheckData(sCheckResultMaxMin,params,"MAXMINRESULTA","MAXMINRESULTB","MAXMINRESULTC","MAXMINRESULTTYPE","MAXMINRESULTAVALUE","MAXMINRESULTVALUE");
                        //MNAB
                        Map<String,Object> sCheckResultOldEven = checkOldEven(map1,map2,map3,map4);
                        setCheckData(sCheckResultOldEven,params,"OLDEVENRESULTA","OLDEVENRESULTB","OLDEVENRESULTC","OLDEVENRESULTTYPE","OLDEVENRESULTAVALUE","OLDEVENRESULTVALUE");
                        //MNLS
                        Map<String,Object> sCheckMnLs = checkMnLs(map1,map2,map3,map4);
                        setCheckData(sCheckMnLs,params,"MNLSRESULTA","MNLSRESULTB","MNLSRESULTC","MNLSRESULTTYPE","MNLSRESULTAVALUE","MNLSRESULTVALUE");
                        //MN
                        Map<String,Object> sCheckMn = checkMn(map1,map2,map3,map4);
                        setCheckData(sCheckMn,params,"MNRESULTA","MNRESULTB","MNRESULTC","MNRESULTTYPE","MNRESULTAVALUE","MNRESULTVALUE");
                        // LS
                        Map<String,Object> sCheckLs = checkLs(map1,map2,map3,map4);
                        setCheckData(sCheckLs,params,"LSRESULTA","LSRESULTB","LSRESULTC","LSRESULTTYPE","LSRESULTAVALUE","LSRESULTVALUE");
                        // AB
                        Map<String,Object> sCheckAb = checkAb(map1,map2,map3,map4);
                        setCheckData(sCheckAb,params,"ABRESULTA","ABRESULTB","ABRESULTC","ABRESULTTYPE","ABRESULTAVALUE","ABRESULTVALUE");
                        // V
                        // Map<String,Object> sCheckV = checkV(map1,map2,map3,map4);
                        // setCheckData(sCheckV,params,value,"VRESULTA","VRESULTB","VRESULTC","VRESULTTYPE","VRESULTAVALUE","VRESULTVALUE");
                        // LSV
                        Map<String,Object> sCheckLSV = checkLsV(map1,map2,map3,map4);
                        setCheckData(sCheckLSV,params,"LSVRESULTA","LSVRESULTB","LSVRESULTC","LSVRESULTTYPE","LSVRESULTAVALUE","LSVRESULTVALUE");
                        // LSV
                        Map<String,Object> sCheckMnV = checkLsMnV(map1,map2,map3,map4);
                        setCheckData(sCheckMnV,params,"MNVRESULTA","MNVRESULTB","MNVRESULTC","MNVRESULTTYPE","MNVRESULTAVALUE","MNVRESULTVALUE");
                        //数据准备结束，插入数据库
                        boolean isResult = mainManageMapper.saveReetAnaly(params);
                        if (!isResult) {
                            logger.info("保存失败" );
                        }
                    }
                }
            }
        }
    }

    private Map<String, Object> checkLsMnV(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnAbList = new ArrayList<Map<String,Object>>();
        MapUtils.setMnV(mnAbList,map1);
        MapUtils.setMnV(mnAbList,map2);
        MapUtils.setMnV(mnAbList,map3);
        MapUtils.setMnV(mnAbList,map4);
        String json = JsonUtil.toJsonString(mnAbList);
        logger.info("服务器MNV查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器MNV查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String, Object> checkLsV(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnAbList = new ArrayList<Map<String,Object>>();
        MapUtils.setLsV(mnAbList,map1);
        MapUtils.setLsV(mnAbList,map2);
        MapUtils.setLsV(mnAbList,map3);
        MapUtils.setLsV(mnAbList,map4);
        String json = JsonUtil.toJsonString(mnAbList);
        logger.info("服务器LSV查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器LSV查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private Map<String, Object> checkV(HashMap<String, Object> map1, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4) {
        List<Map<String,Object>> mnAbList = new ArrayList<Map<String,Object>>();
        MapUtils.setV(mnAbList,map1);
        MapUtils.setV(mnAbList,map2);
        MapUtils.setV(mnAbList,map3);
        MapUtils.setV(mnAbList,map4);
        String json = JsonUtil.toJsonString(mnAbList);
        logger.info("服务器V查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器V查询数据结果：" + maxMinResutl);
        Map<String,Object> map = JsonUtil.getMap(maxMinResutl);
        return map;
    }

    private void setCheckData(Map<String,Object> sCheckResultMaxMin,HashMap<String, Object> params,String maxminresulta, String maxminresultb, String maxminresultc, String maxminresulttype, String maxminresultavalue, String maxminresultvalue) {
        MorphDynaBean maxMin = (MorphDynaBean) sCheckResultMaxMin.get("object");
        int maxMinResultA = (Integer) maxMin.get("a");
        int maxMinResultB = (Integer) maxMin.get("b");
        int maxMinResultC = (Integer) maxMin.get("c");
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
            String value = (String) params.get("VALUE");
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
        logger.info("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器MNLS查询数据结果：" + maxMinResutl);
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
        logger.info("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器MNLS查询数据结果：" + maxMinResutl);
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
        logger.info("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器MNLS查询数据结果：" + maxMinResutl);
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
        logger.info("服务器MNLS查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器MNLS查询数据结果：" + maxMinResutl);
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
        logger.info("服务器大小数查询数据：" + json);
        String maxMinResutl = HttpUtils.post(url,json);
        logger.info("服务器大小数查询数据结果：" + maxMinResutl);
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
        logger.info("服务器奇偶数查询数据：" + json);
        String oldEvenResutl = HttpUtils.post(url,json);
        logger.info("服务器奇偶数查询数据结果：" + oldEvenResutl);
        Map<String,Object> map = JsonUtil.getMap(oldEvenResutl);
        return map;
    }
}
