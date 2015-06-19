<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false"%> 
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

    <title>Expert platform - Query</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
    <%--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/query.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main.css"--%>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jpager/css/style.css">--%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jpager/css/jPages.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jpager/css/animate.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jpager/js/jPages.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/query.js"></script>
    <script type="text/javascript">
//        alert('jinru javascriopt');
    /*    $(document).on("click","tr td .pager_link", function(event){
            var pageInfo = $(this).attr("href");
            var str =  pageInfo.match(/pager.offset=\d+/).toString();
            var pageoffset = str.match(/\d+/);
            $("#pageoffset").val(pageoffset);
            event.preventDefault();
            $("form:first").submit();

         });
*/
        $(function(){
            $("div.holder").jPages({
                containerID : "tbody",
                previous : "←",
                next : "→",
                perPage : 15,
                delay : 20,
                keyBrowse:true,
                callback:function( pages, items ){
                    //alert(pages.current+","+items.count);
                }
            });
            $("input[type='reset']").on("click",function(){
                $("textarea").html("");
            });


        });




function checkall() {
  	var ischecked = document.getElementById("all").checked;
  	if(ischecked) {
  		checkallbox();
  	}else {
  		discheckallbox();
  	}
  }
  //选中全部复选框
  function checkallbox() {
  	var boxarray = document.getElementsByName("pathMark");
  	for(var i = 0; i < boxarray.length; i++) {
  		boxarray[i].checked = true;
  	}
  }
  //取消选中全部复选框
  function discheckallbox() {
  	var boxarray = document.getElementsByName("pathMark");
  	for(var i = 0; i < boxarray.length; i++) {
  		boxarray[i].checked = false;
  	}
  }

  //点击某个复选框，如果所有复选框都选中，“全选/全不选”复选框也选中
  //否则如果所有复选框都取消选中，“全选/全不选”复选框也取消选中
  function checkonebox() {
  	if(isallchecked()) {
  		document.getElementById("all").checked = true;
  	}
  	if(isalldischecked()) {
  		document.getElementById("all").checked = false;
  	}
  }

  //是否全部选中
  function isallchecked() {
  	var boxarray = document.getElementsByName("pathMark");
  	for(var i = 0; i < boxarray.length; i++) {
  		if(!boxarray[i].checked) {
  			return false;
  		}
  	}
  	return true;
  }
  //是否全部没有选中
  function isalldischecked() {
  	var boxarray = document.getElementsByName("pathMark");
  	for(var i = 0; i < boxarray.length; i++) {
  		if(boxarray[i].checked) {
  			return false;
  		}
  	}
  	return true;
  }
  //得到选中项的值的集合，结果为“1|小明,2|小王,3|小李”的形式
  function getallcheckedvalue() {
  	var boxvalues = "";
  	var boxarray = document.getElementsByName("pathMark");
  	for(var i = 0; i < boxarray.length; i++) {
  		if(boxarray[i].checked) {
  			//var boxvalue = boxarray[i].value;
  				boxvalues = boxvalues + "," + i;
  			}
  		}
  	return boxvalues;
  //	alert(boxvalues);
  
  	}
  function changeValue(){
	  document.getElementById("record_id").innerHTML =getallcheckedvalue();
	  document.getElementById("record_id").value = getallcheckedvalue();
  }
  
  	
  function doSubmit(){
	 var form = document.getElementById("sqlDataForm");
	 form.pathmark = getallcheckedvalue();
	
	  form.submit();
  }
  doSubmit();
  //如果只需要得到其中选中项的id值的集合，方法如下，得到的值为（1,2,3,…）


  	
  
  function checkFile() {   
      if($("#all").attr("checked")){   
          $("input[name='pathMark']").attr("checked",true);   
      }else {   
          $("input[name='pathMark']").attr("checked",false);   
      }   
  }   

  function showout(){
	out.println("you have chosen the point!");
	  }

  
  
  function changeval(){
	  
	  var check = document.getElementsByName("pathMark");
	  for(var i = 0; i<check.length; i++){
		     if(check[i].checked == true){
			           check[i].value = "1";
			           
			 }else{
					 check[i].value = "0";
				}
		}
	  }

  function downloadTable(){
		  changeValue();
		  form1.action="sql/tableDownload";
	  }

  function downloadFits(){
		  changeValue();
		  form1.action="sql/sqldownload";
	  }

    </script>
</head>

<body>
    <jsp:include page="/jsp/layout/header.jsp"/>
    <div id="contents">
      <div id="search-service-div" class="wrapper clearfix">
        <div class="main">
          <h2>Query Constructed</h2>
 <!--  	<form action="query/query" method="post"> --> 
        <div id="t_tableInfoDiv">
            <select name="tableName">
              <option>Optional Tables</option>
            </select>
        </div>	 
        <div id="showDiv">
            <div id="f_conditionDiv">
                <div id="buttonDiv">
                    <select name="or_and">
                        <option value="1">and</option>
                        <option value="0">or</option>
                    </select>
                <input type="button" value="Add" id="addCondition"/>
                    <input type="button" value="submit" id="sb"/>
                    <input type="button" value="reset" id="reset"/>
                </div>
            </div>
        </div>
<!-- 	</form> -->
        
 <form action=" " method="post" name="form1" id="queryDataForm" > 
    <div id="QueryDataDiv" style="width: 1024px; height: 700px; margin-top: 10px; overflow: auto;">
    	
        
        <div class="holder"></div>
        <c:if test="${!empty names}">
        <input type="submit" value="Download table" id="sb2" onclick="downloadTable()"/>
        <c:if test="${!haveFits}">
        <input type="submit" value="Download fits" id="sb1" onclick="downloadFits()"/>
        </c:if>
        </c:if>

        <table class="table table-hover table-bordered">
            <thead>
            <tr>
            <c:if test="${!empty names}">
            <td><input type="checkbox" name="all"  id="all" onclick="checkall()"/>ALL</td>
            </c:if>      
                <c:forEach items="${names}" var="name">
                    <th>${name}</th>
                </c:forEach> 
                <c:if test="${haveFits}">
                      <th>file</th>
                      <th>show</th>
                    </c:if>
            </tr>
            </thead>
            <tbody id="tbody">
            <c:forEach items="${queryLmps}" var="map">
                <tr>      
                <td><input type="checkbox" name="pathMark" id="pathMark" /></td>
                    <c:forEach items="${map}" var="m">
                        <td>${m.value }</td>
                    </c:forEach>
                    <input type="hidden" name="record_id" id="record_id" value=''/>
                    <c:if test="${haveFits}">
                     <td><a href="fits/${m.value}">FITS</a></td>
                      <td><a href="./appleindex.jsp">SPEC</a></td>
                      </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </form> 
     
<!--  
            <div id="tableShowDiv">
            <table class="table table-hover table-bordered">
                    <thead>
                    </thead>
                    <tbody id="tbody">                    
                    </tbody>
                </table>
            </div>
     -->       

                <div class="holder" style="float: right"></div>
    </div>
</div>
    </div>
    <jsp:include page="/jsp/layout/footer-login.jsp"/>   
</body>
</html>
