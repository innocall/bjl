package com.rhino.bjl.server.impl;

import com.rhino.bjl.mapper.DataManageMapper;
import com.rhino.bjl.mapper.LoginManageMapper;
import com.rhino.bjl.server.IDataMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DataMessage implements IDataMessage{

    @Autowired
    private DataManageMapper dataManageMapper;

    @Override
    public List<HashMap<String, Object>> findReetList(String category, String one, String two) {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        String arrayOne[] = one.split("-"); //第一局单双数
        String arrayTwo[] = two.split("-"); //第二局单双数
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("JISHUCOUNT1",arrayOne[0]);
        map.put("OUSHUCOUNT1",arrayOne[1]);
        map.put("JISHUCOUNT2",arrayTwo[0]);
        map.put("OUSHUCOUNT2",arrayTwo[1]);
        map.put("VALUE",category);
        dataList = dataManageMapper.findMoreData(map);
        return dataList;
    }

    @Override
    public List<HashMap<String, Object>> findReetList2(String oneType, String twoType, String threeType, String one, String two, String three,String allCount,String pages,String threeType4) {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map = new HashMap<String, Object>();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (StringUtils.isBlank(allCount) || !pattern.matcher(allCount).matches()) {
            allCount = "0";
        }
        String arrayOne[] = one.split("-"); //第一局单双数
        if (StringUtils.isNotBlank(two)) {
            if (StringUtils.isNotBlank(three)) {
                //查询第四届结果
                String arrayTwo[] = two.split("-"); //第二局单双数
                String arrayThree[] = three.split("-"); //第二局单双数
                map.put("JISHUCOUNT1",arrayOne[0]);
                map.put("OUSHUCOUNT1",arrayOne[1]);
                map.put("JISHUCOUNT2",arrayTwo[0]);
                map.put("OUSHUCOUNT2",arrayTwo[1]);
                map.put("JISHUCOUNT3",arrayThree[0]);
                map.put("OUSHUCOUNT3",arrayThree[1]);
                map.put("VALUE1",oneType);
                map.put("VALUE2",twoType);
                map.put("VALUE3",threeType);
                map.put("TRENT",threeType4);
                map.put("allCount",Integer.valueOf(allCount).intValue());
                map.put("pages",Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                dataList = dataManageMapper.findMoreData2(map);
            } else {
                //查询第三局结果
                String arrayTwo[] = two.split("-"); //第二局单双数
                map.put("JISHUCOUNT1",arrayOne[0]);
                map.put("OUSHUCOUNT1",arrayOne[1]);
                map.put("JISHUCOUNT2",arrayTwo[0]);
                map.put("OUSHUCOUNT2",arrayTwo[1]);
                map.put("VALUE1",oneType);
                map.put("VALUE2",twoType);
                map.put("TRENT",threeType4);
                map.put("allCount",Integer.valueOf(allCount).intValue());
                map.put("pages",Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                dataList = dataManageMapper.findMoreData3(map);
            }
        } else {
            //查询第一局的下一届结果
            map.put("JISHUCOUNT1",arrayOne[0]);
            map.put("OUSHUCOUNT1",arrayOne[1]);
            map.put("VALUE1",oneType);
            map.put("TRENT",threeType4);
            map.put("allCount",Integer.valueOf(allCount).intValue());
            map.put("pages",Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
            dataList = dataManageMapper.findMoreData4(map);
        }
        return dataList;
    }

    @Override
    public List<HashMap<String, Object>> findReetListByMN(String jishu, String oushu, int start, int limit) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("JISHUCOUNT",jishu);
        map3.put("OUSHUCOUNT",oushu);
        map3.put("start",start);
        map3.put("limit",limit);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetListByMN(map3);
        return list;
    }

    @Override
    public int findReetListCountByMN(String jishu, String oushu, int start, int limit) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("JISHUCOUNT",jishu);
        map3.put("OUSHUCOUNT",oushu);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetListCountByMN(map3);
        return list.size();
    }

    @Override
    public List<HashMap<String, Object>> findReetByPai(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("XIAN1",xian1);
        map3.put("XIAN2",xian2);
        map3.put("XIAN3",xian3);
        map3.put("ZHUANG1",zhuang1);
        map3.put("ZHUANG2",zhuang2);
        map3.put("ZHUANG3",zhuang3);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetByPai(map3);
        return list;
    }


    public List<HashMap<String, Object>> findReetList2(String category, String one, String two) {
        List<HashMap<String ,Object>> dataList = new ArrayList<HashMap<String, Object>>();
        String arrayOne[] = one.split("-"); //第一局单双数
        String arrayTwo[] = two.split("-"); //第二局单双数
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
