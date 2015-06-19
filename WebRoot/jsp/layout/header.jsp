<%-- 
    Document   : head
    Created on : Jul 10, 2014, 10:21:07 AM
    Author     : xy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="header">
  <div class="wrapper clearfix">
    <div id="logo">
      <a href="index.jsp"><img src="resources/images2/logo.png" alt="LOGO"></a>
    </div>
    <ul id="navigation">
      <!--<li class="selected">-->
      <li>
        <a href="index.jsp">Home</a>
      </li>
      <li>
        <a href="query/search">Data</a>
      </li>
      <li>
        <a href="blog.jsp">Tools</a>
      </li>
      <li>
        <a href="gallery.jsp">Schema</a>
      </li>


      <c:choose>
        <c:when test="${sessionScope.loginUser.username eq null}">

          <li>
            <a href="#">Astronomy</a>
          </li>
          <li>
            <a href="about.jsp">LAMOST</a>
          </li>
          <li>
            <a href="#">Download</a>
          </li>
        </c:when>
        <c:otherwise>
          <li>
            <a href="query/query">Query</a>
          </li>
          <c:choose>
            <c:when test="${sessionScope.loginUser.username eq 'admin'}">
              <li><a href="sql/sqlsearch">SQLs</a></li>
              </c:when>
            </c:choose>
          <li>
            <a href="mydb/mydbUpload">MyDB</a>
          </li>
          <c:choose>
            <c:when test="${sessionScope.loginUser.username eq 'admin'}">
              <li><a href="user/users"><span>Account</span></a></li>
              </c:when>
              <c:otherwise>
              <li><a href="user/myInfo"><span>Account</span></a></li>
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      <li>
        <a href="contact.jsp">Help</a>
      </li>

      <c:choose>
        <c:when test="${sessionScope.loginUser.username eq null}">
          <li>
            <a href="user/login">Login</a>
          </li>
        </c:when>
        <c:otherwise>
          <li>
            <a href="user/logout">Logout</a>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</div>

