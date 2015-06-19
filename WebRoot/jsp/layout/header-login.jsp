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
        <a href="home">Home</a>
      </li>
      <li>
        <a href="gallery.jsp">Schema</a>
      </li>
      <li>
        <a href="query/search">Query</a>
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
      <li>
        <a href="contact.jsp">Help</a>
      </li>
      <li>
        <a href="user/logout">Logout</a>
      </li>
    </ul>
  </div>
</div>

