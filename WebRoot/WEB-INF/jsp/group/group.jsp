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

    <title>User Group</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>

    <script type="text/javascript">
        function deleteGroup(id) {
            if (confirm("Are you sure to delete the group?")) {
                $.ajax({
                    url: "group/delete",
                    type: "get",
                    catch: false,
                    data: {"id": id},
                    dataType: "json",

                    success: function (data) {
                        if (data.result != 1) {
                            alert(data.message);
                        }
                    },

                    error: function (data) {
                        alert("There are some users belong to the group, please remove the users first!");
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
            <c:forEach items="${groups}" var="group">
                <tr>
                    <%--<td>${group.id }</td>--%>
                    <td>${group.name}</td>
                    <td>${group.dec}</td>
                    <td>
                        <a href="javascript:deleteGroup('${group.id }')"><span>Delete</span></a>
                        <a href="group/update/${group.id }"><span>Update</span></a>
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
