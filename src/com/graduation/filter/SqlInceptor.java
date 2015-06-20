package com.graduation.filter;

import com.graduation.model.User;
import com.graduation.web.SessionContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//没有起作用，不知道为什么，改为在Systemcontextfilter中处理
public class SqlInceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser ) {
            response.sendRedirect(request.getContextPath() + System.getProperty("file.separator"));
            return false;
        }
        if(!"admin".equals(loginUser.getUsername())) {
            response.sendRedirect(request.getContextPath() + "/prompt.jsp");
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
