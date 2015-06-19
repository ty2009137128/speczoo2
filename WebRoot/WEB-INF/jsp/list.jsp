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
            $("#listDiv").width($("table").width());
            $("#listDiv").css({ background: "#f7f029" });
            //
            $(".content").on("dblclick", function (event) {
                $(".content").val("");
            });
            /*  $(".content").on("mouseout",function(){
             if($(this).val() == ""||$(this).val().length == 0)
             $(this).val("请输入"+$(this).prev("span").text()+"的值");
             });

             $("input[name='bt']").on("click",function(event){
             $.post($("#url").val(),{
             ra:$("input[name='ra']").val(),
             dec:$("input[name='dec']").val(),
             snu:$("input[name='snu']").val()
             });
             });*/
        });

    </script>
</head>

<body>
<div id="listDiv">
    <table>
        <thead id="thead">
        <tr>
            <td>recordId</td>
            <td>ra</td>
            <td>dec</td>
            <td>snu</td>
        </tr>
        </thead>
        <tbody id="tbody">
        <c:forEach items="${omls.datas }" var="oml">
            <tr>
                <td>${oml.recordId }</td>
                <td>${oml.ra }</td>
                <td>${oml.dec }</td>
                <td>${oml.snu }</td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="4" style="text-align:right;margin-right:10px;">
                <jsp:include page="/jsp/pager.jsp">
                    <jsp:param value="${omls.total }" name="totalRecord"/>
                    <jsp:param value="list" name="url"/>
                </jsp:include>
            </td>
        </tr>
        </tfoot>
    </table>
    <div id="select">
        <form action="list" method="post">
            <span>ra:</span><input type="text" value="输入ra的值" name="ra" class="content">&nbsp;
            <span>dec:</span><input type="text" value="输入dec的值" name="dec" class="content">&nbsp;
            <span>snu:</span><input type="text" value="输入snu的值" name="snu" class="content"> &nbsp;
            <input type="submit" value="查询" name="bt">
        </form>
    </div>
</div>
<!--
 <input type="hidden" id="url" value="list"> -->
</body>
</html>
