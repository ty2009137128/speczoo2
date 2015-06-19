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
    <title>Change Password</title>

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
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/user_util.js"></script>

    <script type="text/javascript">
        function checkParams(event) {
            var flag = true;
            var originalPsw = $('#originalPsw').val().trim();
            var newPsw = $('#newPsw').val().trim();
            var confirmPsw = $('#confirmPsw').val().trim();

            if(originalPsw == ''){
                alert("Please enter original password.");
                flag = false;
            }else if(newPsw == ''){
                alert("Please enter new password.");
                flag = false;
            }else if(confirmPsw == ''){
                alert("Please enter confirm password.");
                flag = false;
            }else if (newPsw != confirmPsw) {
                alert("The new password and confirm password are not the same!");
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
        <div id="addUser">
            <form method="post" action="user/changePassword">
                <table cellspacing="0" cellPadding="0">
                    <thead>
                    <tr>
                        <td colspan="2">Change Password</td>
                    </tr>
                    </thead>
                    <tr>
                        <td class="rightTd" width="200px">Original Password</td>
                        <td class="leftTd">
                            <input type="password" name="originalPsw" size="30" maxlength="20" id="originalPsw"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="rightTd">New Password</td>
                        <td class="leftTd">
                            <input type="password" name="newPsw" size="30" maxlength="20" id="newPsw"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="rightTd">Confirm Password</td>
                        <td>
                            <input type="password" size="30" maxlength="20" id="confirmPsw"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="centerTd">
                            <input type="submit" value="Save" onclick="checkParams(event)"/>
                            <input type="reset" value="Reset"/>
                            <span class="error"> ${errorMsg}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
