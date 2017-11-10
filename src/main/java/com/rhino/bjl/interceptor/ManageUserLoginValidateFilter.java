package com.rhino.bjl.interceptor;

import com.rhino.bjl.bean.ManageUser;
import com.rhino.bjl.constans.AppConstans;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 拦截器，拦截没有登录的用户
 * @author wu
 * @version 1.0.0
 */
public class ManageUserLoginValidateFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		ManageUser manageUser = (ManageUser)request.getSession().getAttribute(AppConstans.MANAGE_USER_SESSION);
		if(manageUser == null){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			//String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
			//String directUrl = new String(Base64.encodeBase64(url.getBytes()));
			HttpServletResponse response = (HttpServletResponse)res;
			//response.sendRedirect("../login/login?directUrl="+ directUrl);
			response.sendRedirect(basePath + "/index.jsp");
		}else{
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
