package com.rhino.bjl.server.impl;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.bean.MaxMinBean;
import com.rhino.bjl.mapper.DataManageMapper;
import com.rhino.bjl.mapper.LoginManageMapper;
import com.rhino.bjl.mapper.MainManageMapper;
import com.rhino.bjl.server.ILoginMessage;
import com.rhino.bjl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wuxiaotie on 2017/6/23.
 */
@Service
public class LoginMessage implements ILoginMessage {

    @Autowired
    private LoginManageMapper loginManageMapper;

    @Autowired
    private MainManageMapper mainManageMapper;

    @Autowired
    private DataManageMapper dataManageMapper;

    public Object[] login(String username, String password) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("USERNAME", username);
        param.put("PASSWORD", password);
        HashMap<String, Object> result = loginManageMapper.getManageUser(param);
        if (result == null) {
            return new Object[] { 400, null }; // 用户不存在
        }
        ManageUser manageUser = new ManageUser();
        manageUser.setID((String) result.get("ID"));
        manageUser.setNAME((String) result.get("NAME"));
        manageUser.setPHONE((String) result.get("PHONE"));
        manageUser.setUSERNAME(username);
        return new Object[] { 200, manageUser };
    }

    @Override
    public boolean reetTbl() {
        String sql = "SELECT * FROM reet_tbl";
        List<HashMap<String,Object>> list = mainManageMapper.findReetList(sql);
        for (HashMap<String,Object> hashMap:list) {
            //更新数据
            HashMap<String,Object> params = new HashMap<String,Object>();
            //1、判断输赢
            String value = "";
            String zhuangDian = hashMap.get("ZHUANGVALUE").toString();
            String xianDian = hashMap.get("XIANVALUE").toString();
            int zhuang = Integer.parseInt(zhuangDian);
            int xian = Integer.parseInt(xianDian);
            if (zhuang > xian) {
                value = "庄";
            } else if (zhuang == xian) {
                value = "和";
            } else if (zhuang < xian) {
                value = "闲";
            }
            params.put("VALUE",value);
            //2、判断奇数偶数
            String zhuang1 = hashMap.get("ZHUANG1").toString();
            String zhuang2 = hashMap.get("ZHUANG2").toString();
            String zhuang3 = hashMap.get("ZHUANG3").toString();
            String xian1 = hashMap.get("XIAN1").toString();
            String xian2 = hashMap.get("XIAN2").toString();
            String xian3 = hashMap.get("XIAN3").toString();
            //统计 一组牌 基数 偶数个数
            int jishu = 0;
            int oushu = 0;
            int ling = 0;
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
            params.put("JISHUCOUNT", jishu);
            params.put("OUSHUCOUNT", oushu);
            params.put("LINGCOUNT", ling);
            params.put("ID", hashMap.get("ID").toString());
            mainManageMapper.updateReetData(params);
           /* //更新房间的奇偶数，庄数，闲数，对数
            String roomId = hashMap.get("ROOMID").toString();
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("ROOMID",roomId);
            HashMap<String,Object> roomMap = mainManageMapper.findRoomById(map);
            jishu = Integer.parseInt(roomMap.get("JISHUCOUNT").toString()) + jishu;
            oushu = Integer.parseInt(roomMap.get("OUSHUCOUNT").toString()) + oushu;
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("JISHUCOUNT", jishu);
            map.put("OUSHUCOUNT", oushu);
            map.put("ID", roomId);
            boolean isParam = mainManageMapper.updateRoomCountData(map);
            return id;*/
        }
        return true;
    }

    @Override
    public boolean roomTbl() {
        String sql = "select * from room_tbl";
        List<HashMap<String,Object>> list = mainManageMapper.findRoomList(sql);
        for (HashMap<String,Object> hashMap:list) {
            HashMap<String,Object> params = new HashMap<String,Object>();
            //查询所有小局
            String roomId = hashMap.get("ID").toString();
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("ROOMID",roomId);
            List<HashMap<String,Object>> listSmall = mainManageMapper.findReetByRoomId(map);
            int jiShu = 0;
            int ouShu = 0;
            for (HashMap<String,Object> reet:listSmall) {
                int reetJiShu = Integer.parseInt(reet.get("JISHUCOUNT").toString());
                int reetOuShu = Integer.parseInt(reet.get("OUSHUCOUNT").toString());
                jiShu = reetJiShu + jiShu;
                ouShu = reetOuShu + ouShu;
            }
            params.put("ID", roomId);
            params.put("JISHUCOUNT", jiShu);
            params.put("OUSHUCOUNT", ouShu);
            boolean isParam = mainManageMapper.updateRoomCountData(params);
        }
        return true;
    }

    @Override
    public boolean reetTbl2() {
        String sql = "select * from room_tbl";
        List<HashMap<String,Object>> list = mainManageMapper.findRoomList(sql);
        for (HashMap<String,Object> hashMap:list) {
            HashMap<String,Object> params = new HashMap<String,Object>();
            //查询所以小局
            String roomId = hashMap.get("ID").toString();
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("ROOMID",roomId);
            int zhuang = (Integer) hashMap.get("ZHUANGCOUNT");
            int xian = (Integer) hashMap.get("XIANCOUNT");
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
            map.put("HECOUNT",hashMap.get("HECOUNT"));
            mainManageMapper.updateReetByRoomId(map);
        }
        return true;
    }

    @Override
    public boolean reetTbl3() {
        String sql = "SELECT * FROM reet_tbl";
        List<HashMap<String,Object>> list = mainManageMapper.findReetList(sql);
        for (HashMap<String,Object> hashMap:list) {
            //更新数据
            HashMap<String,Object> params = new HashMap<String,Object>();
            //2、判断奇数偶数
            String zhuang1 = hashMap.get("ZHUANG1").toString();
            String zhuang2 = hashMap.get("ZHUANG2").toString();
            String zhuang3 = hashMap.get("ZHUANG3").toString();
            String xian1 = hashMap.get("XIAN1").toString();
            String xian2 = hashMap.get("XIAN2").toString();
            String xian3 = hashMap.get("XIAN3").toString();
            int maxCount = 0;
            int minCount = 0;
            MaxMinBean maxMinBean = StringUtils.setMaxMin(zhuang1,zhuang2,zhuang3,xian1,xian2,xian3,maxCount,minCount);
            params.put("MAXCOUNT", maxMinBean.getMaxCount());
            params.put("MINCOUNT", maxMinBean.getMinCount());
            params.put("ID", hashMap.get("ID").toString());
            mainManageMapper.updateReetData(params);
        }
        return true;
    }

    /**
     * 初始化第一局概率
     * @param number 分页单位500 1000 1500
     * @return
     */
    @Override
    public boolean getParameter(String number) {
        Integer JISHU[] = {0,1,2,3,4,5,6};
        Integer OUSHU[] = {0,1,2,3,4,5,6};
        int pages = Integer.parseInt(number);
        Integer page[] = {pages,pages *2,0};
        String trent[] = {"全部","1","2","0"};
        for (int i=0;i<JISHU.length;i++) {
            for (int j = 0; j < OUSHU.length; j++) {
                HashMap<String,Object> map1 = new HashMap<String, Object>();
                map1.put("JIOU",JISHU[i] + "-" + OUSHU[j]);
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("JISHUCOUNT1", JISHU[i]);
                map.put("OUSHUCOUNT1", OUSHU[j]);
                map.put("VALUE1", "全部");
                map.put("VALUE", "0"); //查询全部
                map.put("pages", 0);
                for (int k=0;k<page.length;k++) {
                    map1.put("COUNT" + k,page[k]);
                    map.remove("allCount");
                    map.put("allCount", page[k]);
                    for (int z=0;z<page.length;z++) {
                        map.put("TRENT", trent[z]);
                        map1.put("TRENT" + z,trent[z]);
                        //查询所有条数
                        HashMap<String, Object> allSize = dataManageMapper.findMoreDataAllCount4(map);
                        map.remove("VALUE");
                        map.put("VALUE", "1"); //查询全部
                        HashMap<String, Object> zhangSize = dataManageMapper.findMoreDataAllCount4(map);
                        map.remove("VALUE");
                        map.put("VALUE", "2"); //查询全部
                        HashMap<String, Object> xianSize = dataManageMapper.findMoreDataAllCount4(map);
                        map.remove("VALUE");
                        map.put("VALUE", "3"); //查询全部
                        HashMap<String, Object> heSize = dataManageMapper.findMoreDataAllCount4(map);
                        Float total = Float.parseFloat(allSize.get("COUNT").toString());
                        Float zhuang = Float.parseFloat(zhangSize.get("COUNT").toString());
                        Float xian = Float.parseFloat(xianSize.get("COUNT").toString());
                        Float he = Float.parseFloat(heSize.get("COUNT").toString());
                        String zhuangGailv = Math.round(zhuang / total * 10000) / 100.00 + "%";
                        String xianGailv = Math.round(xian / total * 10000) / 100.00 + "%";
                        String heGailv = Math.round(he / total * 10000) / 100.00 + "%";
                        map1.put("TRENT" + z,trent[z]);
                        map1.put("TRENT" + z,trent[z]);
                        map1.put("TRENT" + z,trent[z]);
                    }

                }


            }
        }
        return true;
    }

}
