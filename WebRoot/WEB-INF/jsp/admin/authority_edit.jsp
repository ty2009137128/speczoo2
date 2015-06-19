<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>Authority Management</title>
	
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
 <!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/mydb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/user.css">
     --> 
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/menu.js"></script>

    <script type="text/javascript">

    </script>
</head>

<body>
<jsp:include page="/jsp/layout/header.jsp"/>
<div id="contents">
<div id="search-service-div" class="wrapper clearfix">
<div class="main">
    <%@ include file="../operation_menu.jsp" %>

    <div id="showDataDiv">
        <h3 class="admin_link_bar">
            <jsp:include page="../user/inc.jsp"></jsp:include>
        </h3>

        <form action="user/authority" method="post"/>
        <input type="hidden" name="userId" value="${userId}"/>
        <table>
            <thead>
            <tr>
                <th>Authority name</th>
                <th>Authorize</th>
                <th>Deauthorize</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allAuthorityTypes}" var="authorityType">
                <tr>
                    <td>${authorityType.authorityName}</td>

                    <c:set var="authorityTypeSelectedFlag" value="0" scope="page"></c:set>
                    <c:forEach items="${authorityTypeIndexes}" var="authorityTypeIndex">
                        <c:if test="${authorityTypeIndex == authorityType.index}">
                            <c:set var="authorityTypeSelectedFlag" value="1" scope="page"></c:set>
                        </c:if>
                    </c:forEach>

                    <td>
                        <c:choose>
                            <c:when test="${authorityTypeSelectedFlag eq '1'}">
                                <input type="radio" value="1" name="${authorityType.name}" checked="checked"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" value="1" name="${authorityType.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${authorityTypeSelectedFlag eq '0'}">
                                <input type="radio" value="0" name="${authorityType.name}" checked="checked"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" value="0" name="${authorityType.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <%--<tr>--%>
                <%--<th>Action</th>--%>
                <%--<th><input type="radio" name="selectAll"/>&nbsp;Select All</th>--%>
                <%--<th><input type="radio" name="selectAll"/>&nbsp;Select All</th>--%>
            <%--</tr>--%>
            <tr>
                <td colspan="3">
                    <input type="submit" value="Submit"/>&nbsp;
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
            </tfoot>
        </table>
        </form>
    </div>
</div>
</div>
</div>
</body>
</html>
