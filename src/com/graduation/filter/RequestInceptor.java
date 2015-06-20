package com.graduation.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.graduation.model.User;
import com.graduation.web.SessionContext;

//没有起作用，不知道为什么，改为在Systemcontextfilter中处理
public class RequestInceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		String sessionId =  request.getParameter("sessionId");
		if(null != sessionId && !"".equals(sessionId.trim())){
			session = SessionContext.getSession(sessionId);
		}
		User loginUser = (User) session.getAttribute("loginUser");
		if(null == loginUser){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}
	

}
