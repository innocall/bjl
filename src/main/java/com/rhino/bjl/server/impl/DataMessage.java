package com.rhino.bjl.server.impl;

import com.rhino.bjl.mapper.DataManageMapper;
import com.rhino.bjl.mapper.LoginManageMapper;
import com.rhino.bjl.server.IDataMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DataMessage implements IDataMessage{

    @Autowired
    private DataManageMapper dataManageMapper;

    @Override
    public List<HashMap<String, Object>> findReetList(String category, String one, String two) {
        List<HashMap<String ,Object>> dataList = new ArrayList<HashMap<String, Object>>();
        String arrayOne[] = one.split("-"); //第一局单双数
        String arrayTwo[] = two.split("-"); //第二局单双数
        String sql1 = "select * from reet_tbl where JISHUCOUNT = " + arrayOne[0] +" AND OUSHUCOUNT = " + arrayOne[1];
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("JISHUCOUNT",arrayOne[0]);
        map3.put("OUSHUCOUNT",arrayOne[1]);
        List<HashMap<String ,Object>> list = new ArrayList<HashMap<String, Object>>();
        if (category.equals("全部")) {
            list = dataManageMapper.findReetByCount(map3);
        } else {
            map3.put("VALUE",category);
            list = dataManageMapper.findReetByCounts(map3);
        }
        if (list != null) {
            List<HashMap<String ,Object>> dataList2 = new ArrayList<HashMap<String, Object>>();
            for (HashMap<String,Object> data:list) {
              dataList2 = getList(data,dataList2,arrayTwo);
            }
            if (dataList2 != null) {
                for (int i=0;i<dataList2.size();i++) {
                    HashMap<String,Object> data2 = dataList2.get(i);
                    if (data2 != null) {
                        HashMap<String,Object> map2 = new HashMap<String, Object>();
                        map2.put("ROOMID",data2.get("ROOMID"));
                        map2.put("POINT",Integer.parseInt(data2.get("POINT").toString()) + 1);
                        HashMap<String,Object> map1 = dataManageMapper.findReetByPoint(map2);
                        if(map1 != null) {
                            dataList.add(map1);
                        }
                    }
                }
            }
        }
        return dataList;
    }

    private  List<HashMap<String ,Object>> getList (HashMap<String,Object> data,List<HashMap<String ,Object>> dataList2,String arrayTwo[]) {
        HashMap<String,Object> map1 = new HashMap<String, Object>();
        map1.put("VALUE","和");
        map1.put("ROOMID",data.get("ROOMID"));
        map1.put("POINT",Integer.parseInt(data.get("POINT").toString()) + 1);
        HashMap<String,Object> map2 = dataManageMapper.findReetByValue(map1);
        if(map2 != null) {
            //为和，要查第三局是否为和
            getList(map2,dataList2,arrayTwo);
        } else {
            //下一局不为和
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("JISHUCOUNT",arrayTwo[0]);
            map.put("OUSHUCOUNT",arrayTwo[1]);
            map.put("VALUE",data.get("VALUE"));
            map.put("ROOMID",data.get("ROOMID"));
            map.put("POINT",Integer.parseInt(data.get("POINT").toString()) + 1);
            HashMap<String,Object> map3 = dataManageMapper.findReetType(map);
            if(map3 != null) {
                dataList2.add(map3);
            }
        }
        return dataList2;
    }
}
