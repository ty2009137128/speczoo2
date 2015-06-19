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

    <title>My JSP 'show.jsp' starting page</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css">

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            alert($("form").serialize());
        });
    </script>
</head>

<body>
<p id="results"><b>Results: </b></p>

<form>
    <select name="single">
        <option>Single</option>
        <option>Single2</option>
    </select>
    <select name="multiple" multiple="multiple">
        <option selected="selected">Multiple</option>
        <option>Multiple2</option>
        <option selected="selected">Multiple3</option>
    </select><br/>
    <input type="checkbox" name="check" value="check1"/> check1
    <input type="checkbox" name="check" value="check2" checked="checked"/> check2
    <input type="radio" name="radio" value="radio1" checked="checked"/> radio1
    <input type="radio" name="radio" value="radio2"/> radio2
</form>

</body>
</html>
