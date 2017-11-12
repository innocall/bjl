package com.rhino.bjl.control;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.server.IMainMessage;
import com.rhino.bjl.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
