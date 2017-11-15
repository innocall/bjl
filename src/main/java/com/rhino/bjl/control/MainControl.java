package com.rhino.bjl.control;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.JsonUtil;
import com.rhino.bjl.utils.ParamUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            boolean isSucces = mainMessage.saveReetData(xian1,xian2,xian3,zhuang1,zhuang2,zhuang3,touzhuMoney,user.getID(),roomId,radio,zhuangdian,xiandian);
            if(!isSucces) {
                status = "400";
                msg = "保存失败";
            }
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
        String category = ParamUtils.getParameter(request, "category", "");
        String category2 = ParamUtils.getParameter(request, "category2", "");
        String startDate = ParamUtils.getParameter(request, "startDate", "");
        String endDate = ParamUtils.getParameter(request, "endDate", "");
        String query = ParamUtils.getParameter(request, "query", "");
        List<HashMap<String, Object>> yhgl = mainMessage.findReetList(category,category2,startDate,endDate,query ,start, limit);
        int count = mainMessage.findReetListCount(category,category2,startDate,endDate,query ,start, limit);
        param.put("yhgl", yhgl);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
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
