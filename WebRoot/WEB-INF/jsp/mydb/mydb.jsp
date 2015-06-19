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
    <title>My DB</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
<!--    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/main.css">-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/uploadify/uploadify.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/mydb.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jqueryUi/css/jquery-ui.css">

    <%--<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-1.8.3.js"></script>--%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqueryUi/js/jquery-1.10.2.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jqueryUi/js/jquery-ui.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript">


        $(function () {

            $( "#dialog" ).dialog({
                autoOpen: false,
                show: {
                    effect: "explode",
                    duration: 1000
                },
                hide: {
                    effect: "explode",
                    duration: 1000
                },
                width:500,
                height:340,

               position:  { my: "left top", at: "right bottom", of: window  }

            });
           /*  $( "#opener" ).one("click",function() {
                $( "#dialog" ).dialog( "open" );
            }); */
            $("#lookFileFormat").click(function(){
                $( "#dialog" ).dialog( "open" );
            });




            $("#file_upload").uploadify({
                swf: $("#ctx").val() + "/resources/js/uploadify/uploadify.swf",
                uploader: $("#ctx").val() + "/mydb/mydbUpload",
                auto: false,
                fileObjName: "file",
                fileSizeLimit: "100000",
                fileTypeExts: "*.txt;*.csv;*.xml",
                method: "post",
                multi: false,
                formData: {'tableName': $("input[name='tableName']").val(), 'sessionId': $("#sessionId").val()},
                buttonText: "SELECT FILES ",
                queueID: "file_upload_queue",
                onUploadStart: function (file) {
                    // alert('onUploadStart');
                    $("#file_upload").uploadify("settings", "formData", { 'tableName': $("input[name='tableName']").val(), 'sessionId': $("#sessionId").val()});
                },
                onUploadSuccess: function (file, data, response) {
                    var resultdata = jQuery.parseJSON(data);
                    if (resultdata.result == 1) {
                        alert("Uploaded successfully....");
                        getListMydbElement();
                        $("input[name='tableName']").val("");
                    } else {//上传失败
                        alert(resultdata.message+",请检查数据格式");
                    }
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {
                    alert(errorString + "," + errorMsg);
                }

            });
            $("#btupload").on("click", function () {
                $("#file_upload").uploadify("upload", "*");
            });
            $("#btcancel").on("click", function () {
                $("#file_upload").uploadify("cancel", "*");
            });
            $("table").on("mouseover", "tr", function () {
                $(this).addClass("trbackground");
            });
            $("table").on("mouseout", "tr", function () {
                $(this).removeClass("trbackground");
            });
            $("#downLoadAll").on("click",function(event){
            	//alert($(this).attr("tableName"));
            	event.preventDefault();
            	//$.get("mydb/");
            	var tableNames = "";
            	
            	$("input[name='downloadCheckbox']:checked").each(function(){
            		//alert($(this).attr("tableName"));
            		var tableName = $(this).attr("tableName")+"&";
            		tableNames+=tableName;
            	});
            	alert(tableNames);
            	$.ajax({
            		type:"GET",
            		url:"mydb/zipDownLoad/"+tableNames,
        			async : false,
        			success :function(data){
        				$(data).each(function(){
        					if(this.result == 0)
							{
								alert(this.message);
							}else if(this.result == 2){
								window.location.href = "mydb/data/download/"+"data"+"?fileSource=zip";
							}else if(this.result == 1)
							{
								alert(this.message);
							}
							
        				});
        			}
            	});
            	
            });

            $("#clearAll").on("click",function(event){
            	//alert($(this).attr("tableName"));
            	event.preventDefault();
            	//$.get("mydb/");
            	var tableNames = "";
            	
            	$("input[name='downloadCheckbox']:checked").each(function(){
            		//alert($(this).attr("tableName"));
            		var tableName = $(this).attr("tableName")+"&";
            	
            		tableNames+=tableName;
            		
            	});
            	
            	alert(tableNames);
            	$.ajax({
            		type:"GET",
            		url:"mydb/clearSelectedTables/"+tableNames,
        			async : false,
        			success :function(data){
        				$(data).each(function(){
        					
								window.location.href = "mydb/mydbUpload";
							
        				});
        			}
            	});
            	
            });            
            $("input[name='All']").on("click",function(){
            	//alert($(this).prop("checked"));true/false
            	if($(this).prop("checked")){
	            	$("input[name='downloadCheckbox']").prop("checked","checked");
            	}else{
            		$("input[name='downloadCheckbox']").removeAttr("checked");
            	}
            });
            $("#mydbInfo").on("click","table tbody tr td input[name='publicCheckBox']",function(){
            	//alert($(this).attr("tableName"));
            	var currentObj = $(this);
            	var modifyStatus = 0;
            	var tableName=$(this).attr("tableName");
            	$.get("mydb/updateTableStatus/"+tableName,{},function(data){
            		$(data).each(function(){
            			//alert(this.result);
            			modifyStatus =  this.result;
            			if(this.result == 0){
            				alert("You cannot modify, not your uploaded data.....");
            				currentObj.attr("disabled",true);
            				if(currentObj.attr("checked") == "checked"){
            					currentObj.prop("checked","checked");
            				}else{
            					currentObj.removeAttr("checked");
            				}
            				
            			}
            			
            		});
            		//$(this).end().attr("disabled",true);
            	});
            });
            $("#downLoadDiv a span").on("click", function (event) {
                var href = $("#downLoadDiv a").prop("href");
                //alert(href+","+href.length);
                if (href.length <= 48) {
                    alert("Not selected table.....");
                    event.preventDefault();
                }
            });

        });

       

        getListMydbElement();
        function getListMydbElement() {
            $("#mydbInfo table tbody tr").remove();
            $.get("mydb/listMydbInfo", {}, function (data) {
                $(data).each(function () {
                	//此处参数太长，修改意见把this对象作为参数传递
                    var node = getTrNode(this.tableName, this.rowNum, this.id,this.isPublic,this.username,this.updateDate);
                    $("#mydbInfo table tbody").append(node);
                });
            }, "json");
        }

        function getTrNode(tableName, rowNum, id,isPublic,username,updateDate) {
        	var realTableName = tableName;
        	var showTableName = splitUsernameTableName(tableName);
            var node = "";
            var td1 = "<td style='text-align: left;'><input type='checkbox' name='downloadCheckbox' tableName='"+realTableName+"'/><a href=mydb/showTable/" + realTableName + ">" + showTableName + "</td>";
            var td2 = "<td style='text-align: left;'>" + rowNum + "</td>";
            //var td2="<td><input type='checkbox' name='downloadCheckbox' tableName='"+realTableName+"'/></td>";
            
            var td3 = "<td style='text-align: left;'><a href=mydb/deleteTable?id=" + id + ">delete<a></td>";
            var td4 = '';
            if(isPublic ==1 ){
            	td4 = "<td ><input type='checkbox' name='publicCheckBox' tableName='"+realTableName+"' value="+isPublic+" checked/></td>"
            }else{
            	td4 = "<td ><input type='checkbox' name='publicCheckBox' tableName='"+realTableName+"' value="+isPublic+"/></td>"
            }
            
            node = node.concat("<tr title="+username+":"+updateDate+">").concat(td1).concat(td2).concat(td4).concat("</tr>");
            return node;
        }
        
        function splitUsernameTableName(tableName){
        	var showTableName = tableName.substring(tableName.lastIndexOf("_")+1);
        	return showTableName;
        }
    </script>

</head>

<body>

<div id="dialog" title="File Format">
    <p>
        <span>
            1.First line of the file must be a field name, each field must be separated by Spaces or ",";<br/>
            2.Begin the second line for the value of each field, and the number must be the same as the number of fields;<br/>
            Such as：
            <img src="<%=request.getContextPath()%>/resources/image/filegeshi.JPG"/>
        </span>

    </p>
</div>
        
    <jsp:include page="/jsp/layout/header.jsp"/>
    <div id="contents">
      <div class="wrapper clearfix">
        <div id="leftbar">
        <div id="uploadDiv">
       		<h4>Table Upload</h4>
            <div >
            <span id="tn">Table name:</span><input type="text" name="tableName" id="opener"  value="Enter your table name" onfocus="this.value='';" onblur="if(this.value.length < 1) this.value='Enter your table name';" />
            </div>
            <div style="color: white; 30; position: relative; top: 5px;">
            <input type="file" name="file" id="file_upload" class="btn4" style="position: relative; top: 5px;">
            </div>
            <div id="file_upload_queue"></div>
            <input type="button" style="cursor:pointer" value="Start upload" id="btupload" class="btn4">
            <input type="button" value="Cancel" id="btcancel" class="btn4">
            <input type="button" value="File Format" id="lookFileFormat" class="btn4">
        </div>
        <div id="mydbInfo" style="margin-top: 10px;">
        <h4>Table   Management</h4>
            <table >
                <thead>
                <tr style="color:#53B0F5;">
                    <td>Table name</td>
                    <td>Line</td>
                   <!--  <td>Delete</td>  -->
                    <td>IsPublic</td>
                </tr>
                </thead>
                <tbody>

                </tbody>
                <tfoot>
                <tr>
                	<td style="text-align: left;border-right: 0px;"><input type="checkbox" name="All"/>&nbsp;&nbsp;<a style="color:#53B0F5;">ALL</a></td>
                	<td style="text-align: left;border-right: 0px;"><input type="button" value="DownLoad" id="downLoadAll" class="btn4"></td>
                    <td colspan="4" style="border-left: 0px;"><input type="button" value="Delete" id="clearAll" class="btn4"></td>
                <tr>
                </tfoot>
                 <c:forEach items="${downloadPath}" var="downloadPath">
                    <th>${downloadPath}</th>
                </c:forEach>
            </table>
        </div>

      </div>
        
        <div id="tableDataDiv" style="width: 640px; height: 680px; margin-top: 10px; overflow: auto;">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <c:forEach items="${names}" var="name">
                        <c:if test="${name ne 'RECORD_ID'}">
                        <td>${name}</td>
                        </c:if>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pager.datas}" var="map">
                    <tr>
                        <c:forEach items="${map}" var="m">
                            <c:if test="${m.key ne 'RECORD_ID'}">
                            <td>${m.value }</td>
                            </c:if>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <c:if test="${!empty pager.datas}">
                <tr>
                    <td colspan="${fn:length(names)-1}" style="text-align:right;margin-right:10px;">
                        <jsp:include page="/jsp/pager.jsp">
                            <jsp:param value="${pager.total }" name="totalRecord"/>
                            <jsp:param value="mydb/showTable/${tableName }" name="url"/>
                        </jsp:include>
                    </td>
                </tr>
                </c:if>
                </tfoot>
            </table>
            <c:if test="${!empty pager.datas}">
            <div id="downLoadDiv">
                <a href="mydb/download/${tableName}"><span>Download data</span></a>
            </div>
             </c:if>
        </div>
        <div id="errorDiv">
            <c:forEach items="${error}" var="e">
                ${e}
            </c:forEach>
        </div>
    </div>
    </div>
    <jsp:include page="/jsp/layout/footer-login.jsp"/>   

<input type="hidden" id="ctx" value="<%= request.getContextPath()%>">
<input type="hidden" id="sessionId" value="${pageContext.session.id}"/>


</body>
</html>
