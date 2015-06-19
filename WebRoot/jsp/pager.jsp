<%@page import="com.graduation.common.SystemContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<pg:pager export="curPage=pageNumber"
          items="${param.totalRecord }"
          maxPageItems="<%=SystemContext.getPageSize() %>"
          url="${param.url }">
    <span style="float:right;padding:6px;">
    In All
    <pg:last>
        ${pageNumber } Page[${param.totalRecord } Records], Each page show <%=SystemContext.getPageSize() %> records,
    </pg:last>
    <c:forEach items="${param.params }" var="p">
        <pg:param name="${p }"/>
    </c:forEach>
    <pg:first>
        <a href="${pageUrl }" class="pager_link">First Page</a>
    </pg:first>
    <pg:prev>
        <a href="${pageUrl }" class="pager_link">Last Page</a>
    </pg:prev>
    <pg:pages>
        <c:if test="${curPage eq pageNumber }">
            [${pageNumber }]
        </c:if>
        <c:if test="${curPage != pageNumber }">
            <a href="${pageUrl }" class="pager_link">${pageNumber }</a>
        </c:if>
    </pg:pages>
    <pg:next>
        <a href="${pageUrl }" class="pager_link">Next Page</a>
    </pg:next>
    <pg:last>
        <a href="${pageUrl }" class="pager_link">End Page</a>
    </pg:last>
</pg:pager>
</span>