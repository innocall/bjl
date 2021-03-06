package com.rhino.bjl.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils {

    public static void setMnLs(List<Map<String,Object>> list,HashMap<String, Object> map) {
        Map<String,Object> mnLsBean = new HashMap<String, Object>();
        mnLsBean.put("minCount",map.get("MINCOUNT"));
        mnLsBean.put("maxCount",map.get("MAXCOUNT"));
        mnLsBean.put("jiShuCount",map.get("JISHUCOUNT"));
        mnLsBean.put("ouShuCount",map.get("OUSHUCOUNT"));
        list.add(mnLsBean);
    }

    public static void setLsAb(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sMaxMinBean = new HashMap<String, Object>();
        sMaxMinBean.put("minCount",map.get("MINCOUNT"));
        sMaxMinBean.put("maxCount",map.get("MAXCOUNT"));
        sMaxMinBean.put("valueA",map.get("ZHUANGVALUE"));
        sMaxMinBean.put("valueB",map.get("XIANVALUE"));
        list.add(sMaxMinBean);
    }

    public static void setMnAb(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sOldEvenBean1 = new HashMap<String, Object>();
        sOldEvenBean1.put("jiShuCount",map.get("JISHUCOUNT"));
        sOldEvenBean1.put("ouShuCount",map.get("OUSHUCOUNT"));
        sOldEvenBean1.put("valueA",map.get("ZHUANGVALUE"));
        sOldEvenBean1.put("valueB",map.get("XIANVALUE"));
        list.add(sOldEvenBean1);
    }

    public static void setMn(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> mnLsBean = new HashMap<String, Object>();
        mnLsBean.put("jiShuCount",map.get("JISHUCOUNT"));
        mnLsBean.put("ouShuCount",map.get("OUSHUCOUNT"));
        list.add(mnLsBean);
    }

    public static void setLs(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> mnLsBean = new HashMap<String, Object>();
        mnLsBean.put("minCount",map.get("MINCOUNT"));
        mnLsBean.put("maxCount",map.get("MAXCOUNT"));
        list.add(mnLsBean);
    }

    public static void setAb(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sOldEvenBean1 = new HashMap<String, Object>();
        sOldEvenBean1.put("valueA",map.get("ZHUANGVALUE"));
        sOldEvenBean1.put("valueB",map.get("XIANVALUE"));
        list.add(sOldEvenBean1);
    }

    public static void setV(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sOldEvenBean1 = new HashMap<String, Object>();
        String v = (String) map.get("VALUE");
        v = getStringValue(v);
        sOldEvenBean1.put("value",v);
        list.add(sOldEvenBean1);
    }

    public static void setLsV(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sMaxMinBean = new HashMap<String, Object>();
        sMaxMinBean.put("minCount",map.get("MINCOUNT"));
        sMaxMinBean.put("maxCount",map.get("MAXCOUNT"));
        String v = (String) map.get("VALUE");
        v = getStringValue(v);
        sMaxMinBean.put("value",v);
        list.add(sMaxMinBean);
    }

    private static String getStringValue(String v) {
        if (v.equals("庄")) {
            v = "A";
        } else if (v.equals("闲")) {
            v = "B";
        } else {
            v = "C";
        }
        return v;
    }

    public static void setMnV(List<Map<String, Object>> list, HashMap<String, Object> map) {
        Map<String,Object> sMaxMinBean = new HashMap<String, Object>();
        sMaxMinBean.put("jiShuCount",map.get("JISHUCOUNT"));
        sMaxMinBean.put("ouShuCount",map.get("OUSHUCOUNT"));
        String v = (String) map.get("VALUE");
        v = getStringValue(v);
        sMaxMinBean.put("value",v);
        list.add(sMaxMinBean);
    }
}
