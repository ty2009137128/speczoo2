<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="top">
    <div id="loginUserInfo">
        <c:choose>
            <c:when test="${sessionScope.loginUser.username eq null}">
                <a href="user/login">Log in</a>
            </c:when>
            <c:otherwise>
                <a href="user/myInfo"> ${sessionScope.loginUser.username}</a>
                &nbsp;|&nbsp;
                <a href="user/logout">Log out</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

