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
    <title>Update User</title>

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
            var username = $('#username').val().trim();
            var password = $('#sfPassword').val().trim();
            var confirmPwd = $('#confirmPwd').val().trim();

            var checkedRoleNum = 0;
            $('input[name=roleIds]').each(function () {
                if ($(this).attr("checked")) {
                    checkedRoleNum++;
                }
            })

            var checkedGroupNum = 0;
            $("input[name=groupIds]").each(function () {
                if ($(this).attr("checked")) {
                    checkedGroupNum++;
                }
            })

            if (!checkUserName(username)) {
                flag = false;
            } else if (!checkPswConfirmPsw(password, confirmPwd)) {
                flag = false;
            } else if (checkedRoleNum == 0) {
                alert("Please select a role.");
                flag = false;
            } else if (checkedGroupNum == 0) {
                alert("Please select a group.");
                flag = false;
            }

            if (!flag) {
                event.preventDefault();
            }
        }

        function initPassword(id, username) {
            if (confirm("Are you sure to reset " + username + "'s password?")) {
                $.ajax({
                    url: "user/initPassword",
                    type: "get",
                    catch: false,
                    data: {"id": id},
                    dataType: "text",

                    success: function (msg) {
                        if (msg == "success") {
                            alert("Succeed in resetting " + username + "'s password.");
                        } else {
                            alert("Reset password failed.");
                        }
                    },

                    error: function (data) {
                        alert("Reset password failed for some error occurred, please try later!");
                    }
                })
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
        <h3 class="admin_link_bar">
            <jsp:include page="inc.jsp"></jsp:include>
        </h3>

        <div id="addUser">
            <sf:form method="post" modelAttribute="userDto" id="updateForm" action="user/update">
                <sf:hidden path="id" id="userId"></sf:hidden>
                <table cellspacing="0" cellPadding="0">
                    <thead>
                    <tr>
                        <td colspan="2">Update User</td>
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
                        <td class="rightTd">Password:</td>
                        <td><sf:password path="password" size="30" id="sfPassword" showPassword='true'/>
                            <sf:errors cssClass="errorContainer" path="password"/></td>
                    </tr>
                    <tr>
                        <td class="rightTd">Confirm Password:</td>
                        <td><sf:password path="confirmPwd" size="30" id="confirmPwd" showPassword='true'/></td>
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
                        <td class="rightTd">Status:</td>
                        <td>
                            <sf:select path="status">
                                <sf:option value="1">Enable</sf:option>
                                <sf:option value="0">Disable</sf:option>
                            </sf:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="rightTd">User Role:</td>
                        <td>
                            <sf:checkboxes items="${roles}" itemLabel="name" itemValue="id" path="roleIds"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="rightTd">User Group:</td>
                        <td>
                            <sf:checkboxes items="${groups}" itemLabel="name" itemValue="id" path="groupIds"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="centerTd">
                            <input type="submit" value="Save" onclick="checkParams(event)"/>
                            <input type="reset" value="Reset"/>
                            <span class="error">${errorMsg}</span>
                        </td>
                    </tr>
                </table>
                <a href="javascript:initPassword('${userDto.id}','${userDto.username}');"><span>Init Password</span></a>
            </sf:form>
        </div>
    </div>
</div>
</div>
</div>
</body>
</html>
