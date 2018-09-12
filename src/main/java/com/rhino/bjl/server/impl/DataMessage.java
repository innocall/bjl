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
    private String[] countMap;

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
    public HashMap<String, Object> findReetList2(String start,String limit,String oneCount, String twoCount, String threeCount, String oneType, String twoType, String threeType, String one, String two, String three, String allCount, String pages, String threeType4) {
        HashMap<String, Object> maps = new HashMap<String, Object>();
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> allSize = new HashMap<String, Object>();//总条数
        HashMap<String, Object> zhangSize = new HashMap<String, Object>();
        HashMap<String, Object> xianSize = new HashMap<String, Object>();
        HashMap<String, Object> heSize = new HashMap<String, Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (StringUtils.isBlank(allCount) || !pattern.matcher(allCount).matches()) {
            allCount = "0";
        }
        String arrayOne[] = one.split("-"); //第一局单双数
        String arrayTwo[] = two.split("-"); //第二局单双数
        String arrayThree[] = three.split("-"); //第二局单双数
        String arrayOneCount[] = oneCount.split("-");
        String arrayTwoCount[] = twoCount.split("-");
        String arrayThreeCount[] = threeCount.split("-");
        if ((arrayOne.length > 1 && arrayThree.length > 1) || (arrayOneCount.length > 1 && arrayThreeCount.length > 1) ||
                (arrayOne.length > 1 && arrayThreeCount.length > 1) || (arrayOneCount.length > 1 && arrayThree.length > 1)) {
            //1、 查3局
            map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
            map = setCountMap(map,arrayTwo,"JISHUCOUNT2","OUSHUCOUNT2");
            map = setCountMap(map,arrayThree,"JISHUCOUNT3","OUSHUCOUNT3");
            map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
            map = setCountMap(map,arrayTwoCount,"MAXCOUNT2","MINCOUNT2");
            map = setCountMap(map,arrayThreeCount,"MAXCOUNT3","MINCOUNT3");
            map.put("VALUE1", oneType);
            map.put("VALUE2", twoType);
            map.put("VALUE3", threeType);
            map.put("TRENT", threeType4);
            map.put("start",Integer.parseInt(start));
            map.put("limit",Integer.parseInt(limit));
            map.put("allCount", Integer.valueOf(allCount).intValue());
            map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
            dataList = dataManageMapper.findMoreData2(map);
            map.put("VALUE","0"); //查询全部
            //查询所有条数
            allSize = dataManageMapper.findMoreDataAllCount2(map);
        } else {
            if ((arrayOne.length > 1 && arrayTwo.length > 1) || (arrayOneCount.length > 1 && arrayTwoCount.length > 1) ||
                    (arrayOneCount.length > 1 && arrayTwo.length > 1) || (arrayOne.length > 1 && arrayTwoCount.length > 1)) {
                //2、查2局
                map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
                map = setCountMap(map,arrayTwo,"JISHUCOUNT2","OUSHUCOUNT2");
                map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
                map = setCountMap(map,arrayTwoCount,"MAXCOUNT2","MINCOUNT2");
                map.put("VALUE1", oneType);
                map.put("VALUE2", twoType);
                map.put("TRENT", threeType4);
                map.put("start",Integer.parseInt(start));
                map.put("limit",Integer.parseInt(limit));
                map.put("allCount", Integer.valueOf(allCount).intValue());
                map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                dataList = dataManageMapper.findMoreData3(map);
                map.put("VALUE","0"); //查询全部
                //查询所有条数
                allSize = dataManageMapper.findMoreDataAllCount3(map);
            } else {
                if (arrayOne.length > 1 || arrayOneCount.length > 1) {
                    //查1局
                    map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
                    map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
                    map.put("VALUE1", oneType);
                    map.put("TRENT", threeType4);
                    map.put("start",Integer.parseInt(start));
                    map.put("limit",Integer.parseInt(limit));
                    map.put("allCount", Integer.valueOf(allCount).intValue());
                    map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                    dataList = dataManageMapper.findMoreData4(map);
                    map.put("VALUE","0"); //查询全部
                    //查询所有条数
                    allSize = dataManageMapper.findMoreDataAllCount4(map);
                }
            }
        }
        maps.put("dataList",dataList);
        maps.put("allSize",allSize.get("COUNT"));
        return maps;
    }

    @Override
    public List<HashMap<String, Object>> findReetListByMN(String jishu, String oushu, int start, int limit,String qiang) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("JISHUCOUNT",jishu);
        map3.put("OUSHUCOUNT",oushu);
        map3.put("start",start);
        map3.put("limit",limit);
        map3.put("qiang",qiang);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetListByMN(map3);
        return list;
    }

    @Override
    public int findReetListCountByMN(String jishu, String oushu, int start, int limit,String qiang) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("JISHUCOUNT",jishu);
        map3.put("OUSHUCOUNT",oushu);
        map3.put("qiang",qiang);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetListCountByMN(map3);
        return list.size();
    }

    @Override
    public List<HashMap<String, Object>> findReetByPai(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3,String qiang) {
        HashMap<String,Object> map3 = new HashMap<String, Object>();
        map3.put("XIAN1",xian1);
        map3.put("XIAN2",xian2);
        map3.put("XIAN3",xian3);
        map3.put("ZHUANG1",zhuang1);
        map3.put("ZHUANG2",zhuang2);
        map3.put("ZHUANG3",zhuang3);
        map3.put("qiang",qiang);
        List<HashMap<String, Object>> list =  dataManageMapper.findReetByPai(map3);
        return list;
    }

    @Override
    public HashMap<String, Object> findReetProbability(String oneCount, String twoCount, String threeCount, String oneType, String twoType, String threeType, String one, String two, String three, String allCount, String pages, String threeType4) {
        HashMap<String, Object> maps = new HashMap<String, Object>();
        HashMap<String, Object> allSize = new HashMap<String, Object>();//总条数
        HashMap<String, Object> zhangSize = new HashMap<String, Object>();
        HashMap<String, Object> xianSize = new HashMap<String, Object>();
        HashMap<String, Object> heSize = new HashMap<String, Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (StringUtils.isBlank(allCount) || !pattern.matcher(allCount).matches()) {
            allCount = "0";
        }
        String arrayOne[] = one.split("-"); //第一局单双数
        String arrayTwo[] = two.split("-"); //第二局单双数
        String arrayThree[] = three.split("-"); //第二局单双数
        String arrayOneCount[] = oneCount.split("-");
        String arrayTwoCount[] = twoCount.split("-");
        String arrayThreeCount[] = threeCount.split("-");
        if ((arrayOne.length > 1 && arrayThree.length > 1) || (arrayOneCount.length > 1 && arrayThreeCount.length > 1) ||
                (arrayOne.length > 1 && arrayThreeCount.length > 1) || (arrayOneCount.length > 1 && arrayThree.length > 1)) {
            //1、 查3局
            map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
            map = setCountMap(map,arrayTwo,"JISHUCOUNT2","OUSHUCOUNT2");
            map = setCountMap(map,arrayThree,"JISHUCOUNT3","OUSHUCOUNT3");
            map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
            map = setCountMap(map,arrayTwoCount,"MAXCOUNT2","MINCOUNT2");
            map = setCountMap(map,arrayThreeCount,"MAXCOUNT3","MINCOUNT3");
            map.put("VALUE1", oneType);
            map.put("VALUE2", twoType);
            map.put("VALUE3", threeType);
            map.put("TRENT", threeType4);
            map.put("allCount", Integer.valueOf(allCount).intValue());
            map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
            map.put("VALUE","0"); //查询全部
            //查询所有条数
            allSize = dataManageMapper.findMoreDataAllCount2(map);
            map.remove("VALUE");
            map.put("VALUE","1"); //查询全部
            zhangSize = dataManageMapper.findMoreDataAllCount2(map);
            map.remove("VALUE");
            map.put("VALUE","2"); //查询全部
            xianSize = dataManageMapper.findMoreDataAllCount2(map);
            map.remove("VALUE");
            map.put("VALUE","3"); //查询全部
            heSize = dataManageMapper.findMoreDataAllCount2(map);
        } else {
            if ((arrayOne.length > 1 && arrayTwo.length > 1) || (arrayOneCount.length > 1 && arrayTwoCount.length > 1) ||
                    (arrayOneCount.length > 1 && arrayTwo.length > 1) || (arrayOne.length > 1 && arrayTwoCount.length > 1)) {
                //2、查2局
                map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
                map = setCountMap(map,arrayTwo,"JISHUCOUNT2","OUSHUCOUNT2");
                map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
                map = setCountMap(map,arrayTwoCount,"MAXCOUNT2","MINCOUNT2");
                map.put("VALUE1", oneType);
                map.put("VALUE2", twoType);
                map.put("TRENT", threeType4);
                map.put("allCount", Integer.valueOf(allCount).intValue());
                map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                map.put("VALUE","0"); //查询全部
                //查询所有条数
                allSize = dataManageMapper.findMoreDataAllCount3(map);
                map.remove("VALUE");
                map.put("VALUE","1"); //查询全部
                zhangSize = dataManageMapper.findMoreDataAllCount3(map);
                map.remove("VALUE");
                map.put("VALUE","2"); //查询全部
                xianSize = dataManageMapper.findMoreDataAllCount3(map);
                map.remove("VALUE");
                map.put("VALUE","3"); //查询全部
                heSize = dataManageMapper.findMoreDataAllCount3(map);
            } else {
                if (arrayOne.length > 1 || arrayOneCount.length > 1) {
                    //查1局
                    map = setCountMap(map,arrayOne,"JISHUCOUNT1","OUSHUCOUNT1");
                    map = setCountMap(map,arrayOneCount,"MAXCOUNT1","MINCOUNT1");
                    map.put("VALUE1", oneType);
                    map.put("TRENT", threeType4);
                    map.put("allCount", Integer.valueOf(allCount).intValue());
                    map.put("pages", Integer.valueOf(allCount).intValue() * (Integer.valueOf(pages).intValue() - 1));
                    map.put("VALUE","0"); //查询全部
                    //查询所有条数
                    allSize = dataManageMapper.findMoreDataAllCount4(map);
                    map.remove("VALUE");
                    map.put("VALUE","1"); //查询全部
                    zhangSize = dataManageMapper.findMoreDataAllCount4(map);
                    map.remove("VALUE");
                    map.put("VALUE","2"); //查询全部
                    xianSize = dataManageMapper.findMoreDataAllCount4(map);
                    map.remove("VALUE");
                    map.put("VALUE","3"); //查询全部
                    heSize = dataManageMapper.findMoreDataAllCount4(map);
                }
            }
        }
        maps.put("allSize",allSize.get("COUNT"));
        maps.put("zhangSize",zhangSize.get("COUNT"));
        maps.put("xianSize",xianSize.get("COUNT"));
        maps.put("heSize",heSize.get("COUNT"));
        return maps;
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

    public HashMap<String, Object> setCountMap(HashMap<String,Object> map,String[] countMap,String key1,String key2) {
        if (countMap.length > 1) {
            map.put(key1, countMap[0]);
            map.put(key2, countMap[1]);
        } else {
            map.put(key1, "");
            map.put(key2, "");
        }
        return map;
    }
}
