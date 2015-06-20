package com.graduation.filter;


import com.graduation.common.Pager;
import com.graduation.common.SystemContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class SystemContextFilter implements Filter {
    private Integer pageSize;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        /*HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null && !request.getRequestURI().endsWith("/user/login")){
			String sessionId = request.getParameter("sessionId");
			if(sessionId == null){
				response.sendRedirect(request.getContextPath()+"/user/login");
				return ;
			}
			HttpSession sessionctx = SessionContext.getSession(sessionId);
			loginUser = (User)sessionctx.getAttribute("loginUser");
			if(loginUser == null)
			{	response.sendRedirect(request.getContextPath()+"/user/login");
				return ;
			}
		}*/
        Integer offset = 0;
        try {
            offset = Integer.parseInt(req.getParameter("pager.offset"));
        } catch (NumberFormatException e) {
        }
        try {
            SystemContext.setOrder(req.getParameter("order"));
            SystemContext.setSort(req.getParameter("sort"));
            SystemContext.setPageOffset(offset);
            SystemContext.setPageSize(pageSize);
           // SystemContext.setRealPath(request.getSession().getServletContext().getRealPath("/resources/fits/"));
            SystemContext.setRealPath(request.getSession().getServletContext().getRealPath("")+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"fits"+System.getProperty("file.separator"));
            chain.doFilter(req, resp);
        } finally {
            SystemContext.removeOrder();
            SystemContext.removeSort();
            SystemContext.removePageOffset();
            SystemContext.removePageSize();
            SystemContext.removeRealPath();
        }
    }

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        try {
            pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
        } catch (NumberFormatException e) {
            pageSize = Pager.DEFAULT_PAGESIZE;
        }
    }

}
