<%@ page language="java" pageEncoding="UTF-8" %>
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
    <title>Update My Info</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/user_util.js"></script>

    <script type="text/javascript">
        function checkParams(event) {
            var flag = true;
            var username = $('#username').val().trim();

            if (!checkUserName(username)) {
                flag = false;
            }

            if (!flag) {
                event.preventDefault();
            }
        }
    </script>
</head>

<body>
<jsp:include page="/jsp/layout/header.jsp"/>
<div id="container">
<div id="search-service-div" class="wrapper clearfix">
<div class="main">
    <%@ include file="../operation_menu.jsp" %>

    <div id="showDataDiv">
        <c:if test="${sessionScope.loginUser.username} == 'admin'">
            <h3 class="admin_link_bar">
                <jsp:include page="inc.jsp"></jsp:include>
            </h3>
        </c:if>

        <div id="addUser">
            <sf:form method="post" modelAttribute="userDto" id="updateForm" action="user/updateMyInfo">
                <sf:hidden path="id" id="userId"></sf:hidden>
                <table cellspacing="0" cellPadding="0">
                    <thead>
                    <tr>
                        <td colspan="2">Update My Info</td>
                    </tr>
                    </thead>
                    <tr>
                        <td class="rightTd" width="200px">User Name:</td>
                        <td class="leftTd"><sf:input path="username" size="30"/>
                            <sf:errors cssClass="errorContainer" path="username"/></td>
                    </tr>
                    <tr>
                        <td class="rightTd">Nickname:</td>
                        <td class="leftTd"><sf:input path="nickname" size="30"/></td>
                    </tr>
                    <tr>
                        <td class="rightTd">Phone Number:</td>
                        <td><sf:input path="phone" size="30"/></td>
                    </tr>
                    <tr>
                        <td class="rightTd">Email:</td>
                        <td><sf:input path="email" size="30"/><sf:errors path="email"/></td>
                    </tr>
                    <tr>
                        <td class="rightTd">Company:</td>
                        <td><sf:input path="company" size="30"/><sf:errors path="company"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="centerTd">
                            <input type="submit" value="Save" onclick="checkParams(event)"/>
                            <input type="reset" value="Reset"/>
                            <span class="error">${errorMsg}</span>
                        </td>
                    </tr>
                </table>
            </sf:form>
        </div>
    </div>
</div>
</div>
</div>
</body>
</html>
