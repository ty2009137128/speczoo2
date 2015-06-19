<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>File upload</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/mydb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/uploadify/uploadify.css">

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="<%= request.getContextPath() %>/resources/js/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript">


        $(function () {
            $("#file_upload").uploadify({
                swf: $("#ctx").val() + "/resources/js/uploadify/uploadify.swf",
                uploader: $("#ctx").val() + "/file/upload",
                auto: false,
                fileObjName: "file",
                fileSizeLimit: "100000",
                queueID: "file_upload_queue",
                buttonText: "Select file",
                onQueueComplete: function () {
                    alert("Uploaded successfully");
                }

                //  height        : 30,
                //  width         : 120
                //  formData:设置上传的表单数据
                //	fileTypeExts：‘*.gif; *.jpg ; *.png’
            });
            $("#btupload").on("click", function () {
                $("#file_upload").uploadify("upload", "*");
            });
            $("#btcancel").on("click", function () {
                $("#file_upload").uploadify("cancel", "*");
            });

        });

    </script>
</head>

<body>
<div id="uploadDiv" style="margin-top: 30px; margin-left: 15%;">
    <div id="file_upload_queue">
        <input type="file" id="file_upload" name="file"><br>
        <input type="button" value="Start upload" id="btupload">
        <input type="button" value="Cancel" id="btcancel">
    </div>
</div>

<input type="hidden" id="ctx" value="<%= request.getContextPath()%>">
</body>
</html>
