<div id="content">
    <div id="nav_list">
        <ul>
            <li><a href="home"><span>Home</span></a></li>
            <li><a href="query/search"><span>Query</span></a></li>
            <c:if test="${!(sessionScope.loginUser.username eq null)}">
                <li><a href="mydb/mydbUpload"><span>My DB</span></a></li>
            </c:if>
            <c:choose>
                <c:when test="${sessionScope.loginUser.username eq 'admin'}">
                    <li><a href="sql/sqlsearch"><span>SQL Query</span></a></li>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${sessionScope.loginUser.username eq 'admin'}">
                    <li><a href="user/users"><span>Account</span></a></li>
                </c:when>
                <c:when test="${!(sessionScope.loginUser.username eq null)}">
                    <li><a href="user/myInfo"><span>Account</span></a></li>
                </c:when>
            </c:choose>
            <%--<li><a href="file/upload"><span>Upload file</span></a></li>--%>

            <%-- <li><a href="#"><span>Tools</span></a></li>
            <li><a href="#"><span>Datasets</span></a></li>
            <li><a href="query/search"><span>Projects</span></a></li>
            <li><a href="#"><span>SpecZoo</span></a></li>
            <li><a href="#"><span>LAMOST</span></a></li>
            <li><a href="#"><span>Help</span></a></li>
            <li><a href="#"><span>Contact Us</span></a></li> --%>
        </ul>
    </div>
</div>
