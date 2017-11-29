package com.rhino.bjl.control;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.DateUtils;
import com.rhino.bjl.utils.JsonUtil;
import com.rhino.bjl.utils.ParamUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value="/private/main")
public class MainControl extends BaseControl {

    @Autowired
    private IMainMessage mainMessage;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("main/index");
        return mav;
    }

    /**
     * 数据统计
     * @return
     */
    @RequestMapping(value = "/dataStatistics",method = RequestMethod.GET)
    public ModelAndView dataStatistics(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("main/dataStatistics");
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        //查询用户余额
       HashMap<String, Object> map = mainMessage.findParamMsgByUserId(user.getID());
       mav.addObject("paramMap",map);
        return mav;
    }

    /**
     * 数据分析
     * @return
     */
    @RequestMapping(value = "/dataAnalysis",method = RequestMethod.GET)
    public ModelAndView dataAnalysis() {
        ModelAndView mav = new ModelAndView("main/dataAnalysis");
        return mav;
    }

    /**
     * 大局分析
     * @return
     */
    @RequestMapping(value = "/rootAnalysis",method = RequestMethod.GET)
    public ModelAndView rootAnalysis() {
        ModelAndView mav = new ModelAndView("main/rootAnalysis");
        return mav;
    }

    /**
     * 充值
     * @return
     */
    @RequestMapping(value = "/chongzhi",method = RequestMethod.POST)
    public void chongzhi(HttpServletRequest request,HttpServletResponse response) {
        String status = "200";
        String msg = "充值成功";
        String userId = ParamUtils.getParameter(request, "id", "");
        int money = ParamUtils.getIntParameter(request, "money", 1000000);
        //更新数据
        boolean isSucces = mainMessage.updateUserMoneyByUserId(userId,money);
        if(!isSucces) {
            status = "400";
            msg = "充值失败";
        }
        PrintWriter out = null;
        printMsgToPage(response, status, msg, out);
    }

    /**
     * 清空流水
     * @return
     */
    @RequestMapping(value = "/clearMoney",method = RequestMethod.POST)
    public void clearMoney(HttpServletRequest request,HttpServletResponse response) {
        String status = "200";
        String msg = "清空成功";
        String userId = ParamUtils.getParameter(request, "id", "");
        //更新数据
        boolean isSucces = mainMessage.updateZhuangMoneyByUserId(userId);
        if(!isSucces) {
            status = "400";
            msg = "清空失败";
        }
        PrintWriter out = null;
        printMsgToPage(response, status, msg, out);
    }

    /**
     * 提交单局数据
     * @return
     */
    @RequestMapping(value = "/submitDate",method = RequestMethod.POST)
    public void submitDate(HttpServletRequest request,HttpServletResponse response) {
        String status = "200";
        String msg = "提交成功";
        String xian1 = ParamUtils.getParameter(request, "xian1", "");
        String xian2 = ParamUtils.getParameter(request, "xian2", "");
        String xian3 = ParamUtils.getParameter(request, "xian3", "");
        String zhuang1 = ParamUtils.getParameter(request, "zhuang1", "");
        String zhuang2 = ParamUtils.getParameter(request, "zhuang2", "");
        String zhuang3 = ParamUtils.getParameter(request, "zhuang3", "");
        String userMoney = ParamUtils.getParameter(request, "userMoney", "");
        String touzhuMoney = ParamUtils.getParameter(request, "touzhuMoney", "0");
        String juCount = ParamUtils.getParameter(request, "juCount", "0");
        String roomId = ParamUtils.getParameter(request, "roomId", "");
        String zhuangCount = ParamUtils.getParameter(request, "zhuangCount", "0");
        String xianCount = ParamUtils.getParameter(request, "xianCount", "0");
        String heCount = ParamUtils.getParameter(request, "heCount", "0");
        String zhuangDuiCount1 = ParamUtils.getParameter(request, "zhuangDuiCount1", "0");
        String radio = ParamUtils.getParameter(request, "radio", "-1");
        String xianDuiCount = ParamUtils.getParameter(request, "xianDuiCount", "0");
        String zhuangdian = ParamUtils.getParameter(request, "zhuangdian", "0");
        String xiandian = ParamUtils.getParameter(request, "xiandian", "0");
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        boolean isParam = true;
        //判断大局是否存在
        if (StringUtils.isBlank(roomId)) {
             roomId = mainMessage.saveRoomData(userMoney,juCount,user.getID(),zhuangCount,xianCount,heCount,zhuangDuiCount1,xianDuiCount);
             logger.info("创建房间：" + roomId);
        } else {
            //房间已经存在，根据ID更新就可以
            logger.info("房间已经存在，根据ID更新就可以：" + roomId);
            boolean isSucces = mainMessage.updateRoomData(userMoney,juCount,user.getID(),zhuangCount,xianCount,heCount,zhuangDuiCount1,xianDuiCount,roomId);
            if (!isSucces) {
                isParam = false;
                status = "400";
            }
        }
        if (StringUtils.isNotBlank(roomId) && isParam) {
            logger.info("保存每一局数据：" + roomId);
            //保存每一局数据
            msg = roomId;
            //更新数据
            String id = mainMessage.saveReetData(xian1,xian2,xian3,zhuang1,zhuang2,zhuang3,touzhuMoney,user.getID(),roomId,radio,zhuangdian,xiandian,juCount);
            if(StringUtils.isBlank(id)) {
                status = "400";
                msg = "保存失败";
            }
            //更新钱；
            boolean isSucces = mainMessage.updateUserMoneyByUserId(user.getID(),Integer.parseInt(userMoney));
        }
        PrintWriter out = null;
        printMsgToPage(response, status, msg, out);
    }


    @RequestMapping(value = "reetAllData", method = RequestMethod.POST)
    public ResponseEntity<String> reetAllData(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        int start = ParamUtils.getIntParameter(request, "start", 0);
        int limit = ParamUtils.getIntParameter(request, "limit", 80);
        String category = ParamUtils.getParameter(request, "category", "全部");
        String startDate = ParamUtils.getParameter(request, "startDate", "2015-09-09");
        String endDate = ParamUtils.getParameter(request, "endDate",  DateUtils.getDate5());
        String query = ParamUtils.getParameter(request, "query", "");
        String jishu = ParamUtils.getParameter(request, "jishu", "");
        String oushu = ParamUtils.getParameter(request, "oushu", "");
        String ling = ParamUtils.getParameter(request, "ling", "");
        List<HashMap<String, Object>> yhgl = mainMessage.findReetList(category,jishu,oushu,ling,startDate,endDate,query ,start, limit);
        int count = mainMessage.findReetListCount(category,jishu,oushu,ling,startDate,endDate,query ,start, limit);
        param.put("yhgl", yhgl);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }


    @RequestMapping(value = "findReetById", method = RequestMethod.POST)
    public ResponseEntity<String> findReetById(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        String roomId = ParamUtils.getParameter(request, "roomId", "");
        List<HashMap<String, Object>> reetList = mainMessage.findReetByRoomId(roomId);
        param.put("reetList", reetList);
        param.put("count", reetList.size());
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "roomAllData", method = RequestMethod.POST)
    public ResponseEntity<String> roomAllData(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        int start = ParamUtils.getIntParameter(request, "start", 0);
        int limit = ParamUtils.getIntParameter(request, "limit", 80);
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        List<HashMap<String, Object>> yhgl = mainMessage.findRoomList(start, limit,user.getID());
        int count = mainMessage.findRoomListCount(start, limit,user.getID());
        param.put("yhgl", yhgl);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "deleteRoomById", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteRoomById(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        String roomId = ParamUtils.getParameter(request, "roomId", "");
        if(StringUtils.isNotBlank(roomId)) {
            boolean isParam = mainMessage.deleteRootById(roomId);
            if(isParam) {
                param.put("success", true);
            } else {
                param.put("success", false);
            }
        } else {
            param.put("success", false);
        }
        String json = JsonUtil.toJsonString(param);
        return json;
    }

    @RequestMapping(value = "saveRoom",method = {RequestMethod.POST})
    public ResponseEntity<String> saveRoom(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html",
                Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        HashMap param = new HashMap();
        String juCount = ParamUtils.getParameter(request, "TOTALCOUNT", "");
        String zhuangCount = ParamUtils.getParameter(request, "ZHUANGCOUNT", "");
        String xianCount = ParamUtils.getParameter(request, "XIANCOUNT", "");
        String heCount = ParamUtils.getParameter(request, "HECOUNT", "");
        String zhuangDuiCount1 = ParamUtils.getParameter(request, "ZHUANGDUICOUNT", "");
        String xianDuiCount = ParamUtils.getParameter(request, "XIANDUICOUNT", "");
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        String roomId = mainMessage.saveRoomData("10000000",juCount,user.getID(),zhuangCount,xianCount,heCount,zhuangDuiCount1,xianDuiCount);
        if (StringUtils.isBlank(roomId)) {
            param.put("msg", "添加失败！");
        } else {
            param.put("msg", "添加成功！");
            param.put("success", true);
            param.put("juCount",juCount);
            param.put("zhuangCount",zhuangCount);
            param.put("xianCount",xianCount);
            param.put("heCount",heCount);
            param.put("zhuangDuiCount1",zhuangDuiCount1);
            param.put("xianDuiCount",xianDuiCount);
            param.put("STRARTTIME", DateUtils.getDate5());
            param.put("id",roomId);
        }

        String json = JsonUtil.toMapJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "saveReet",method = {RequestMethod.POST})
    public ResponseEntity<String> saveReet(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html",
                Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        HashMap param = new HashMap();
        String ZHUANG1 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "ZHUANG1", ""));
        String ZHUANG2 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "ZHUANG2", ""));
        String ZHUANG3 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "ZHUANG3", ""));
        String XIAN1 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "XIAN1", ""));
        String XIAN2 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "XIAN2", ""));
        String XIAN3 = com.rhino.bjl.utils.StringUtils.getCountFormat(ParamUtils.getParameter(request, "XIAN3", ""));
        String roomId = ParamUtils.getParameter(request, "roomId", "");
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        int zhuangdian = getDianShu(ZHUANG1, ZHUANG2, ZHUANG3);
        int xiandian = getDianShu(XIAN1, XIAN2, XIAN3);
        int juCount = mainMessage.findCountReetByRoomId(roomId);
        juCount = juCount + 1;
        String  id = mainMessage.saveReetData(XIAN1, XIAN2, XIAN3, ZHUANG1,ZHUANG2,ZHUANG3,"0",user.getID(),roomId,"-1",zhuangdian + "",xiandian + "",juCount + "");
        if (StringUtils.isBlank(id)) {
            param.put("msg", "添加失败！");
        } else {
            HashMap<String,Object> map =  mainMessage.findRoomById(roomId);
            mainMessage.updateRoomData(map.get("MONEY") + "",juCount + "",user.getID(),map.get("ZHUANGCOUNT") + "",map.get("XIANCOUNT") + "",map.get("HECOUNT") + "",map.get("ZHUANGDUICOUNT") + "",map.get("XIANDUICOUNT") + "",roomId);
            param.put("msg", "添加成功！");
            param.put("success", true);
            param.put("id",id);
            param.put("ZHUANG1",ZHUANG1);
            param.put("ZHUANG2",ZHUANG2);
            param.put("ZHUANG3",ZHUANG3);
            param.put("XIAN1",XIAN1);
            param.put("XIAN2",XIAN2);
            param.put("XIAN3",XIAN3);
            param.put("ROOMID",roomId);
            param.put("POINT",juCount);
            param.put("TOUZHU","-1");
            param.put("ZHUANGVALUE",zhuangdian);
            param.put("XIANVALUE",xiandian);
            param.put("TIME",DateUtils.getDate5());
        }
        String json = JsonUtil.toMapJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }


    @RequestMapping(value = "deleteReetById", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteReetById(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        String id = ParamUtils.getParameter(request, "id", "");
        if(StringUtils.isNotBlank(id)) {
            boolean isParam = mainMessage.deleteReetById(id);
            if(isParam) {
                param.put("success", true);
            } else {
                param.put("success", false);
            }
        } else {
            param.put("success", false);
        }
        String json = JsonUtil.toJsonString(param);
        return json;
    }

    /**
     * 实时批量提交单局数据
     * @return
     */
    @RequestMapping(value = "/submitRootDate",method = RequestMethod.POST)
    public void submitRootDate(HttpServletRequest request,HttpServletResponse response) {
        String status = "200";
        String msg = "提交成功";
        String data = ParamUtils.getParameter(request, "data", "");
        String zhuangCount = ParamUtils.getParameter(request, "zhuangCount", "");
        String xianCount = ParamUtils.getParameter(request, "xianCount", "");
        String heCount = ParamUtils.getParameter(request, "heCount", "");
        String zhuangDuiCount1 = ParamUtils.getParameter(request, "zhuangDuiCount1", "");
        String xianDuiCount = ParamUtils.getParameter(request, "xianDuiCount", "");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(data);
        ManageUser user = (ManageUser) request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
        String juCount = jsonArray.getJSONArray(0).size() + "";
        //判断大局是否存在
        String roomId = mainMessage.saveRoomData("",juCount,user.getID(),zhuangCount,xianCount,heCount,zhuangDuiCount1,xianDuiCount);
        logger.info("创建房间：" + roomId);
        //更新数据
        if (StringUtils.isNotBlank(roomId)) {
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            for (int i=0;i<jsonArray1.size();i++) {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                mainMessage.saveReetData(com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"XIAN1"),com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"XIAN2")
                        ,com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"XIAN3"),com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"ZHUANG1"),
                        com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"ZHUANG2"),com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"ZHUANG3"),"",user.getID(),roomId,"-1",
                        com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"ZHUANGVALUE"),com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"XIANVALUE"),com.rhino.bjl.utils.StringUtils.getJsonString(jsonObject,"POINT"));
            }
        }else {
            status = "400";
            msg = "保存失败";
        }
        PrintWriter out = null;
        printMsgToPage(response, status, msg, out);
    }

    private int getDianShu(String ZHUANG1, String ZHUANG2, String ZHUANG3) {
        int zhuangdian;
        if (StringUtils.isNotBlank(ZHUANG3)) {
            int i = com.rhino.bjl.utils.StringUtils.showData(Integer.parseInt(ZHUANG1)) +com.rhino.bjl.utils.StringUtils.showData(Integer.parseInt(ZHUANG2)) + com.rhino.bjl.utils.StringUtils.showData(Integer.parseInt(ZHUANG3));
            zhuangdian = com.rhino.bjl.utils.StringUtils.getCount(i);
        } else {
            int i = com.rhino.bjl.utils.StringUtils.showData(Integer.parseInt(ZHUANG1)) +com.rhino.bjl.utils.StringUtils.showData(Integer.parseInt(ZHUANG2));
            zhuangdian = com.rhino.bjl.utils.StringUtils.getCount(i);
        }
        return zhuangdian;
    }


    private void printMsgToPage(HttpServletResponse response, String status, String msg, PrintWriter out) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            out.print("{\"status\":\""+status+"\",\"msg\":\""+msg+"\"}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out != null) {
                out.close();
            }
        }
    }


}
