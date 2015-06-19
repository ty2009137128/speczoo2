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
    <title>Account</title>

    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/menu.js"></script>

    <script type="text/javascript">
        function deleteUser(id) {
            if (confirm("Are you sure to delete the user?")) {
                $.ajax({
                    url: "user/delete",
                    type: "get",
                    catch: false,
                    data: {"id": id},
                    dataType: "text",

                    success: function (msg) {
                        if (msg == "failure") {
                            alert("Delete failed!");
                        }
                    },

                    error: function (data) {
                        alert("Delete failed for some error occurred!");
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
    <div id="contents">
      <div id="search-service-div" class="wrapper clearfix">
        <div id="leftbar_list">
          <div id="userInfoDiv">
            <c:if test="${sessionScope.loginUser.username eq 'admin'}">
              <ul id="userInfoUl">
                <h3><span>System</span></h3>
                <li><a href="user/users"><span>Users management</span></a></li>
                <li><a href="role/roles"><span>Role management</span></a></li>
                <li><a href="group/groups"><span>Group management</span></a></li>
              </ul>
            </c:if>

            <ul id="privateUl">
              <h3><span>Private</span></h3>
              <li><a href="user/myInfo"><span>My info</span></a></li>
              <li><a href="user/updateMyInfo"><span>Modify my info</span></a></li>
              <li><a href="user/changePassword"><span>Change password</span></a></li>
            </ul>
          </div>
        </div>
        <div class="main">

    <div id="showDataDiv">
        <h3 class="admin_link_bar">
            <jsp:include page="inc.jsp"></jsp:include>
        </h3>

              <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th>User Name</th>
                <th>Nickname</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Company</th>
                <th>Status</th>
                <th>
                    <span>Action</span>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pager.datas}" var="user">
                <tr>
                    <td>${user.username }</td>
                    <td>${user.nickname }</td>
                    <td>${user.phone }</td>
                    <td>${user.email }</td>
                    <td>${user.company }</td>
                    <td>
                        <c:if test="${user.status eq 1}">Enable</c:if>
                        <c:if test="${user.status eq 0}">Disable</c:if>
                    <td>
                        <a href="javascript:deleteUser('${user.id}');"><span>Delete</span></a>
                        <a href="user/update/${user.id}"><span>Update</span></a>
                        <a href="user/authority/${user.id}"><span>Authority</span></a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
            <tfoot>
            <tr>
                  <td colspan="8" style="text-align:right;margin-right:10px;font-size: 14px;">
                    <jsp:include page="/jsp/pager.jsp">
                        <jsp:param value="${pager.total }" name="totalRecord"/>
                        <jsp:param value="user/users" name="url"/>
                    </jsp:include>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
      </div>
    </div>
    <jsp:include page="/jsp/layout/footer-login.jsp"/>   

</body>
</html>
