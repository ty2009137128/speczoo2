<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
        });
    </script>
</head>

<body>

<div id="filedDiv">
    <form action="search" method="post">
        <table>
            <tr>
                <td><span>ra:</span><input type="text" name="ra" value="${sp.ra }"></td>
                <td>
                    <span>raMin:</span><input type="text" name="raMin" value="${sp.raMin }"><br>
                    <span>raMax:</span><input type="text" name="raMax" value="${sp.raMax }">
                </td>
            </tr>
            <tr>
                <td><span>dec:</span><input type="text" name="dec" value="${sp.dec }"></td>
                <td>
                    <span>decMin:</span><input type="text" name="decMin" value="${sp.decMin }"><br>
                    <span>decMax:</span><input type="text" name="decMax" value="${sp.decMax }">
                </td>
            </tr>
            <tr>
                <td><span>mjd:</span><input type="text" name="mjd" value="${sp.mjd }"></td>
                <td>
                    <span>mjdMin:</span><input type="text" name="mjdMin" value="${sp.mjdMin }"><br>
                    <span>mjdMax:</span><input type="text" name="mjdMax" value="${sp.mjdMax }">
                </td>
            </tr>
            <tr>
                <td><span>snu:</span><input type="text" name="snu" value="${sp.snu }"></td>
                <td>
                    <span>snuMin:</span><input type="text" name="snuMin" value="${sp.snuMin }"><br>
                    <span>snuMax:</span><input type="text" name="snuMax" value="${sp.snuMax }">
                </td>
            </tr>
            <tr>
                <td><span>sng:</span><input type="text" name="sng" value="${sp.sng }"></td>
                <td>
                    <span>sngMin:</span><input type="text" name="sngMin" value="${sp.sngMin }"><br>
                    <span>sngMax:</span><input type="text" name="sngMax" value="${sp.sngMax }">
                </td>
            </tr>
            <tr>
                <td><span>snr:</span><input type="text" name="snr" value="${sp.snr }"></td>
                <td>
                    <span>snrMin:</span><input type="text" name="snrMin" value="${sp.snrMin }"><br>
                    <span>snrMax:</span><input type="text" name="snrMax" value="${sp.snrMax }">
                </td>
            </tr>
            <tr>
                <td><span>sni:</span><input type="text" name="sni" value="${sp.sni }"></td>
                <td>
                    <span>sniMin:</span><input type="text" name="sniMin" value="${sp.sniMin }"><br>
                    <span>sniMax:</span><input type="text" name="sniMax" value="${sp.sniMax }">
                </td>
            </tr>
            <tr>
                <td><span>snz:</span><input type="text" name="snz" value="${sp.snz }"></td>
                <td>
                    <span>snzMin:</span><input type="text" name="snzMin" value="${sp.snzMin }"><br>
                    <span>snzMax:</span><input type="text" name="snzMax" value="${sp.snzMax }">
                </td>
            </tr>
            <tr>
                <td><span>z :</span><input type="text" name="z" value="${sp.z }"></td>
                <td>
                    <span>zMin: </span><input type="text" name="zMin" value="${sp.zMin }"><br>
                    <span>zMax: </span><input type="text" name="zMax" value="${sp.zMax }">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <span>planId:</span><input type="text" name="planId" value="${sp.planId }">
                    &nbsp;&nbsp;
                    <span>objtype: </span><input type="text" name="objtype" value="${sp.objtype }"><br>
                    <span>clz: </span><input type="text" name="clz" value="${sp.clz }">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" value="Reset" class="btclass"> &nbsp;
                    <input type="submit" value="Submit" class="btclass"> &nbsp;
                <td>
            </tr>
        </table>
    </form>
</div>
<div id="errorDiv">
    <c:forEach items="${ errorMsg}" var="message"><span class="error">${message }</span></c:forEach>

</div>
<div id="dataDiv">
    <table id="dataTable">
        <thead>
        <tr>
            <td>recordId</td>
            <td>planId</td>
            <td>mjd</td>
            <td>ra</td>
            <td>dec</td>
            <td>objtype</td>
            <td>snu</td>
            <td>sng</td>
            <td>snr</td>
            <td>sni</td>
            <td>snz</td>
            <td>clz</td>
            <td>z</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ses.datas }" var="s">
            <tr>
                <td>${s.recordId}</td>
                <td>${s.planId}</td>
                <td>${s.mjd }</td>
                <td>${s.ra }</td>
                <td>${s.dec }</td>
                <td>${s.objtype}</td>
                <td>${s.snu }</td>
                <td>${s.sng }</td>
                <td>${s.snr }</td>
                <td>${s.sni }</td>
                <td>${s.snz }</td>
                <td>${s.clz }</td>
                <td>${s.z }</td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="13" style="text-align:right;margin-right:10px;">
                <jsp:include page="/jsp/pager.jsp">
                    <jsp:param value="${ses.total }" name="totalRecord"/>
                    <jsp:param value="search" name="url"/>
                </jsp:include>
            </td>
        </tr>
        </tfoot>
    </table>
</div>
</body>
</html>
