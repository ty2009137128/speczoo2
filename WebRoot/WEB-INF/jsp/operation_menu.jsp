<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/menu.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
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