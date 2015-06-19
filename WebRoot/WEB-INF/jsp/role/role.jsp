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

    <title>User Role</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

 	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/menu.js"></script>

    <script type="text/javascript">
        function deleteRole(id) {
            if (confirm("Are you sure to delete the role?")) {
                $.ajax({
                    url: "role/delete",
                    type: "get",
                    catch: false,
                    data: {"id": id},
                    dataType: "text",

                    success: function (msg) {
                        if (msg == "failure") {
                            alert("Delete failed, please retry later!");
                        }
                    },

                    error: function (data) {
                        alert("There are some users belong to the role, please remove the users first!");
                    },

                    complete: function () {
                        window.location.reload();
                    }
                });
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

        <table>
            <thead>
            <tr>
                <%--<td>Id</td>--%>
                <td>Name</td>
                <td>Description</td>
                <td>Action</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <%--<td>${role.id }</td>--%>
                    <td>${role.name}</td>
                    <td>${role.dec}</td>
                    <td>
                        <a href="javascript:deleteRole('${role.id }')"><span>Delete</span></a>
                        <a href="role/update/${role.id }"><span>Update</span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</div>
</div>
</body>
</html>
