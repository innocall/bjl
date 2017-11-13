package com.rhino.bjl.control;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.constans.AppConstans;
import com.rhino.bjl.server.ILoginMessage;
import com.rhino.bjl.utils.JsonUtil;
import com.rhino.bjl.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wuxiaotie on 2017/5/16.
 * 用户登录控制模块
 */
@Controller
@RequestMapping(value="/login")
public class LoginControl extends BaseControl{

    @Autowired
    private ILoginMessage loginMessage;

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public @ResponseBody
    String manageLogin(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        String username = ParamUtils.getParameter(request, "username", "");
        String password = ParamUtils.getParameter(request, "password", "");
        Object[] verifyRes = loginMessage.login(username, password);
        Integer status = (Integer) verifyRes[0];
        if (status == 200) {
            ManageUser user = (ManageUser) verifyRes[1];
            param.put("success", true);
            request.getSession().setAttribute(AppConstans.MANAGE_USER_SESSION,user);
            request.getSession().setMaxInactiveInterval(5*60*60);  //5小时
        } else {
            param.put("success", false);
        }
        String json = JsonUtil.toJsonString(param);
        return json;
    }

}