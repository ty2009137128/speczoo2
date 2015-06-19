<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <title>SpecZoo Home</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/index.css">
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
    </script>
</head>

<body>
<div id="container"> 
<div id="top">
   
</div>
<div id="content">
    <div id="nav_list">
        <ul>
            <li><a href="homepage"><span>Home</span></a></li>
            <li><a href="tool/tools"><span>Tools</span></a></li>
            <li><a href="user/login"><span>SpecZoo</span></a></li>
			<li><a href="http://data.lamost.org/dr2/"><span>Lamost</span></a></li>
			<li><a href=""><span>Contact Us</span></a></li>
			<li><a href=""><span>Download</span></a></li>
			<c:choose>
                <c:when test="${sessionScope.loginUser.username eq 'admin'}">
 			<li><a href="sql/sqlsearch"><span>Login</span></a></li>
 			 	</c:when>
             </c:choose>
             <li><a href=""><span>Help</span></a></li>
        </ul>
    </div>
</div>
</div>
</body>
</html>

