<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Login</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css">
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/user_util.js"></script>

    <script type="text/javascript">
        function checkCheckCode() {
            var checkCode = $('input[name=checkCode]').val().trim();

            if (checkCode.length == 4) {
                $.ajax({
                    url: "user/checkCheckCode",
                    catch: false,
                    type: "get",
                    data: {"checkCode": checkCode},
                    dataType: "text",

                    success: function (msg) {
                        if (msg == "right") {
                            $('#imgResult').attr("src", "resources/image/right.png");
                            $('#imgResult').show();
                        } else if (msg == "error") {
                            $('#imgResult').attr("src", "resources/image/error.png");
                            $('#imgResult').show();
                        } else {
                            $('#imgResult').hide();
                        }
                    }
                })
            } else {
                $('#imgResult').hide();
            }
        }

        function checkLoginData(event) {
            var username = $("input[name=username]").val().trim();
            var password = $("input[name=password]").val().trim();
            var checkCode = $('input[name=checkCode]').val().trim();
            var flag = true;

            if (!checkUserName(username)) {
                flag = false;
            } else if (!checkPassword(password)) {
                flag = false;
            }

            if (!flag) {
                event.preventDefault();
            }
        }
    </script>
</head>

<body>
<div id="loginDiv">
    <form action="user/login" method="post">
        <table id="loginTable">
            <tr>
                <td>User Name</td>
                <td align="left">
                    <input type="text" name="username" size="25" maxlength="10"/>
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td align="left">
                    <input type="password" name="password" size="25" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <td>Check Code</td>
                <td>
                    <input type="text" name="checkCode" size="12" onkeyup="checkCheckCode()" maxlength="4"/>
                </td>
                <td>
                    <img alt="Waiting" src="user/getCheckCode" onclick="this.src = this.src + '?id = ' + Math.random()"
                         style="width: 80px; height: 25px;margin-left: -115px;cursor:pointer;"/>
                    <img style="display:none; width:15px;height: 15px;" id="imgResult" src=""/>
                </td>
            </tr>
            <tr align="right">
                <td colspan="2" style="text-align: center;">
                    <input style="width: 57px; height: 24px;" type="submit" value="Login"
                           onclick="checkLoginData(event)"/>
                    <input style="width: 57px; height: 24px; margin-left: 30px;" type="reset" value="Reset"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><span class="error">${error}</span></td>
            </tr>
        </table>
    </form>

</div>
</body>
</html>
