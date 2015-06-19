<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>My Info</title>

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
    <script type="text/javascript">
    </script>
</head>

<body>
<jsp:include page="/jsp/layout/header.jsp"/>
<div id="container">
<div id="search-service-div" class="wrapper clearfix">
<div class="main">
    <%@ include file="../operation_menu.jsp" %>

    <div id="showDataDiv">
        <table cellspacing="0" cellPadding="0">
            <thead>
            <tr>
                <th colspan="2">My Information</th>
            </tr>
            </thead>
            <tr>
                <td class="rightTd" width="200px">User Name:</td>
                <td class="leftTd">${userInfo.username}</td>
            </tr>
            <tr>
                <td class="rightTd">Nickname:</td>
                <td class="leftTd">${userInfo.nickname}</td>
            </tr>
            <tr>
                <td class="rightTd">Phone Number:</td>
                <td class="leftTd">${userInfo.phone}</td>
            </tr>
            <tr>
                <td class="rightTd">Email:</td>
                <td class="leftTd">${userInfo.email}</td>
            </tr>
            <tr>
                <td class="rightTd">Company:</td>
                <td class="leftTd">${userInfo.company}</td>
            </tr>
            <tr>
                <td class="rightTd">Status:</td>
                <td>
                    <c:choose>
                        <c:when test="${userInfo.status eq 1}">Enable</c:when>
                        <c:otherwise>Disable</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="rightTd">User Role:</td>
                <td class="leftTd">
                    <c:forEach items="${roleNames}" var="roleName">
                        ${roleName}&nbsp;&nbsp;
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td class="rightTd">User Group:</td>
                <td class="leftTd">
                    <c:forEach items="${groupNames}" var="groupName">
                        ${groupName}&nbsp;&nbsp;
                    </c:forEach>
                </td>
            </tr>
        </table>
    </div>
</div>
</div>
</div>
</body>
</html>
