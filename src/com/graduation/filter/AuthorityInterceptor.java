package com.graduation.filter;

import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.ResultType;
import com.graduation.model.User;
import com.graduation.util.AuthorityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Stephen on 5/22/2014.
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    public static final int INT = 58;
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AuthorityMethod authorityMethod = handlerMethod.getMethodAnnotation(AuthorityMethod.class);

        if (null == authorityMethod) {
            //No permission restrict, so the user can access.
            return true;
        }

        logger.debug("authorityMethod", authorityMethod.toString());

        User user = (User) request.getSession().getAttribute("loginUser");

        if(user == null){
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

        boolean flag = false;
        for (AuthorityType authorityType : authorityMethod.authorityTypes()) {
            if (AuthorityUtil.hasAuthority(authorityType.getIndex(), user.getAuthority()) == true) {
                flag = true;
                break;
            }
        }

        //No Permission.
        if (!flag) {
            if (authorityMethod.resultType() == ResultType.PAGE) {
                response.sendRedirect(request.getContextPath() + "/prompt.jsp");
            } else if (authorityMethod.resultType() == ResultType.JSON) {
                //ajax login.
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                pw.println("{\"result\":false,\"code\":12,\"errorMessage\":\"NOT AUTHORITY\"}");
                pw.flush();
                pw.close();
            }

            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
