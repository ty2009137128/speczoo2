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
    <meta name="viewport" content="width=device-width,intial-scale=1" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
    
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/jquery.multiselect.css" type="text/css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/jquery.multiselect.filter.css" type="text/css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/prettify.css" type="text/css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" type="text/css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/jquery.multiselect.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jpager/css/jPages.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jpager/css/animate.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jpager/js/jPages.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.multiselect.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.multiselect.filter.js"></script>
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
                perPage : 10,
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

	$("#fitsDownload").on("click",function(){
		alert("Make sure your 'username/fits'dictinary have this fits file,or you would get a empty file");
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
  				boxvalues = boxvalues + i+",";
  			}
  		}
  	return boxvalues;
  //	alert(boxvalues);
  
  	}
///下一步，将json转化为String类型，赋给condition控件，以传到后台

 // var parameter = getJsonByArray2(conditions);
 // alert(parameter);
 // alert(JSON.stringify(parameter));
function changeValue(){
	  document.getElementById("record_id").innerHTML =getallcheckedvalue();
	  document.getElementById("record_id").value = getallcheckedvalue();
  }
  
  function getCondition(){
	  var conditions = setCondition();
	 
	 
	  document.getElementById("condition").innerHTML =getJsonByArray2(conditions);
	  document.getElementById("condition").value = getJsonByArray2(conditions);
  }
  
 
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

  
  

  function downloadTable(){
	  	
		  changeValue();
		  form1.action="query/tableDownload";
	  }

  function downloadFits(){
	  	alert("Make sure your 'username/fits' have fits,or would get this table!");
		  changeValue();
		  form1.action="downloadAll.jsp";
	  }
  
  function showAll(){
		  changeValue();
		  form1.target="_blank"; 
		  form1.action="showAll.jsp";
    	 
      }

  function aladinALL(){
		  changeValue();
		  form1.target="_blank"; 
		  form1.action="aladin.jsp";
	  }
  
  function imageALL(){
	 // changeValue();
	  form1.target="_blank";
	  //var test=111;
	 // var ra=document.getElementById("ra");
	 // var dec=document.getElementsByName("dec").value;
	  //var paste=document.getElementsByName("pathMark").length; 
	  form1.action="images.jsp";
	  }
  
    </script>
</head>

<body>
    <jsp:include page="/jsp/layout/header.jsp"/>
    <div id="contents">
      <div id="search-service-div" class="wrapper clearfix">
        <div class="main">
        
            
          <h2>Convenient Query</h2>
<form action="query/querySearch" method="post" id="queryForm">
        <div id="t_tableInfoDiv">
         <select name="tableName">
              <option>Optional Tables</option>
            </select>
        </div>
        <div>	
       
        <div id="showDiv" >
            <div id="f_conditionDiv" >
                <div id="buttonDiv">
                    <select name="or_and" id="or_and" >
                        <option value="1">and</option>
                        <option value="0">or</option>
                    </select>
                    <input type="hidden" name="condition" id="condition" value=''/>
                	<input type="button" value="Add" id="addCondition"/>
                 	<input type="submit" value="submit" id="sb" class="btn1" onclick="getCondition()"/>
                	<input type="reset" value="reset" class="btn1" id="reset"/>
                </div>
                
            </div>
        </div>
 	</form>
 	</div> 
        
 <form action=" " method="post" name="form1" id="queryDataForm" > 
    <div id="QueryDataDiv" style=" height: 700px; margin-top: 10px; ">
        <div class="holder"></div>
        <c:if test="${!empty names}">
        <input type="submit" value="Download table" id="sb1" class="btn4" onclick="downloadTable()"/>
        <input type="submit" value="Download fits" id="sb2" class="btn4" onclick=" downloadFits()"/>
        <INPUT TYPE="submit" value="SpecView" class="btn4" onclick="showAll()"/>
        <c:if test="${haveRaAndDec}">
        <INPUT TYPE="submit" value="Images" class="btn4" onclick="imageALL()"/>
        <INPUT TYPE="submit" value="Aladin" class="btn4" onclick="aladinALL()"/>
        <INPUT TYPE="submit" value="Simbad" class="btn4" onclick="simbadALL()"/>
        </c:if>
        </c:if>

        <table class="table table-hover table-bordered">
            <thead>
            <tr>
            <c:if test="${!empty names}">
            <td><input type="checkbox" name="all"  id="all" onclick="checkall()"/>ALL</td>
            </c:if>      
                <c:forEach items="${names}" var="name">
                	<c:if test="${name!='Name'}">
                		<c:if test="${name!='RA_DEC'}">
                		<c:if test="${name!='PATH'}">
                		<th>${name}</th>
                		</c:if>
                		</c:if>
                	</c:if>
                    </c:forEach>
                    <c:if test="${havePATH}">
                      <th>FITS</th>
                      <th>VIEW</th>
                      </c:if>
                    <c:if test="${haveRaAndDec}">
                      <th>SDSS</th>
                    </c:if>
            </tr>
            
            </thead>
            <tbody id="tbody">
            <c:forEach items="${queryLmps}" var="map">
                <tr>      
                <td><input type="checkbox" name="pathMark" id="pathMark" value="${map.Name}"/></td>
                     <c:forEach items="${map}" var="m">
	                    <c:if test="${m.key!='Name'}">
                		<c:if test="${m.key!='Ra'}">
                		<c:if test="${m.key!='Dec'}">
                		<c:if test="${m.key!='PATH'}">
                		<th>${m.value}</th>
                		</c:if>
                		</c:if>
                		</c:if>
                		</c:if>
                    </c:forEach>
                    <input type="hidden" name="record_id" id="record_id" value=''/>
                    <input type="hidden" name="ra" id="ra" value="${map.Ra}"/>
                    <input type="hidden" name="dec" id="dec" value="${map.Dec}"/>
                    <input type="hidden" name="PATH" id="PATH" value="${map.PATH}"/>
                    <c:if test="${havePATH}">
                     <td><a href="fits/${map.PATH}">DOWNLOAD</a></td>
                      <td><a href="./specview.jsp?Name=${map.PATH}" target="_blank">SPECVIEW</a></td>
                      </c:if>
                      <c:if test="${haveRaAndDec}">
                    	<td><a href="http://skyserver.sdss3.org/dr8/en/tools/chart/navi.asp?ra=${map.Ra}&dec=${map.Dec}" target="_blank">IMAGE</a></td>
                    	</c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="errorDiv">
            <c:forEach items="${error}" var="message"><span class="error">${message}</span></c:forEach>
            </div>   
    </form>
     
       <div class="holder" style="float: right"></div>
    </div>
</div>
    </div>
    <jsp:include page="/jsp/layout/footer-login.jsp"/>   
</body>
</html>
