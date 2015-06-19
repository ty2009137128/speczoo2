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

    <title>Query</title>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
      $(document).ready(function() {
        $("input[type='reset']").click(function() {
          $("input[type='text']").removeAttr("value");
        });

        var dataSource = "";
        $("#tags").autocomplete({
          source: "query/getOwnTable"

        });
        $(".pager_link").on("click", function(event) {

          var pageInfo = $(this).attr("href");
          var str = pageInfo.match(/pager.offset=\d+/).toString();
          // var pattern=/[0-9]*[1-9][0-9]*/;
          // var pageoffset = str.match(pattern);
          //  alert(str.match(/\d+/));
          var pageoffset = str.match(/\d+/);
          $("#pageoffset").val(pageoffset);
          event.preventDefault();
          $("form:first").submit();

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
      	var boxarray = document.getElementsByName("attachmentPath");
      	for(var i = 0; i < boxarray.length; i++) {
      		boxarray[i].checked = true;
      	}
      }
      //取消选中全部复选框
      function discheckallbox() {
      	var boxarray = document.getElementsByName("attachmentPath");
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
      	var boxarray = document.getElementsByName("attachmentPath");
      	for(var i = 0; i < boxarray.length; i++) {
      		if(!boxarray[i].checked) {
      			return false;
      		}
      	}
      	return true;
      }
      //是否全部没有选中
      function isalldischecked() {
      	var boxarray = document.getElementsByName("attachmentPath");
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
      	var boxarray = document.getElementsByName("attachmentPath");
      	for(var i = 0; i < boxarray.length; i++) {
      		if(boxarray[i].checked) {
      			var boxvalue = boxarray[i].value;
      			if(boxvalues == "") {
      				boxvalues = boxvalue;
      			}else {
      				boxvalues = boxvalues + "," + boxvalue;
      			}
      		}
      	}
      	return boxvalues;
      }
      //如果只需要得到其中选中项的id值的集合，方法如下，得到的值为（1,2,3,…）
   
      	var pathsarry = document.getElementsByName("attachmentPath");
      	session.setAttribute("paths", pathsarry);
      
      function checkFile() {   
          if($("#all").attr("checked")){   
              $("input[name='attachmentPath']").attr("checked",true);   
          }else {   
              $("input[name='attachmentPath']").attr("checked",false);   
          }   
      }   

      function showAll(){
    	  document.form1.action="showAll.jsp";
        	 
          }

     
      function downAll(){
    	  form1.action="fits/downloadAll";
        	
          }   
            
    </script>
  </head>

  <body>

    <jsp:include page="/jsp/layout/header.jsp"/>
    <div id="contents">
      <div id="search-service-div" class="wrapper clearfix">
        <div class="main">
          <%--<input type="text" value="${sessionScope.loginUser.id}" id="userId"/>--%>
          <form action="query/search" method="post">
            <input type="hidden" id="pageoffset" name="pager.offset"/>
            <div id="filedDiv">
              <table>
                <tr>
                  <td><label for="tags"><b>TableName:</b></label></td>
                  <td colspan="5" align="left">
                    <input id="tags" type="text" name="tableName" value="${tableName}" style="heitht: 24px; width: 200px; font-size: 18px;"/>
<!--                    <a href="query/query">Advanced query>></a>-->
                  </td>
                </tr>
                <tr>
                  <td width="100px">ra:</td><td width="200px"><input type="text" name="ra" value="${sp.ra }"></td>
                  <td width="100px">raMin:</td><td width="200px"><input type="text" name="raMin" value="${sp.raMin }"></td>
                  <td width="100px">raMax:</td><td width="200px"><input type="text" name="raMax" value="${sp.raMax }"></td>
                </tr>
                <tr>
                  <td>dec:</td><td><input type="text" name="dec" value="${sp.dec }"></td>
                  <td>decMin:</td><td><input type="text" name="decMin" value="${sp.decMin }"></td>
                  <td>decMax:</td><td><input type="text" name="decMax" value="${sp.decMax }"></td>
                </tr>
                <tr>
                  <td>mjd:</td><td><input type="text" name="mjd" value="${sp.mjd }"></td>
                  <td>mjdMin:</td><td><input type="text" name="mjdMin" value="${sp.mjdMin }"></td>
                  <td>mjdMax:</td><td><input type="text" name="mjdMax" value="${sp.mjdMax }"></td>
                </tr>
                <tr>
                  <td>snu:</td><td><input type="text" name="snu" value="${sp.snu }"></td>
                  <td>snuMin:</td><td><input type="text" name="snuMin" value="${sp.snuMin }"></td>
                  <td>snuMax:</td><td><input type="text" name="snuMax" value="${sp.snuMax }"></td>
                </tr>
                <tr>
                  <td>sng:</td><td><input type="text" name="sng" value="${sp.sng }"></td>
                  <td>sngMin:</td><td><input type="text" name="sngMin" value="${sp.sngMin }"></td>
                  <td>sngMax:</td><td><input type="text" name="sngMax" value="${sp.sngMax }"></td>
                </tr>
                <tr>
                  <td>snr:</td><td><input type="text" name="snr" value="${sp.snr }"></td>
                  <td>snrMin:</td><td><input type="text" name="snrMin" value="${sp.snrMin }"></td>
                  <td>snrMax:</td><td><input type="text" name="snrMax" value="${sp.snrMax }"></td>
                </tr>
                <tr>
                  <td>sni:</td><td><input type="text" name="sni" value="${sp.sni }"></td>
                  <td>sniMin:</td><td><input type="text" name="sniMin" value="${sp.sniMin }"></td>
                  <td>sniMax:</td><td><input type="text" name="sniMax" value="${sp.sniMax }"></td>
                </tr>
                <tr>
                  <td>snz:</td><td><input type="text" name="snz" value="${sp.snz }"></td>
                  <td>snzMin:</td><td><input type="text" name="snzMin" value="${sp.snzMin }"></td>
                  <td>snzMax:</td><td><input type="text" name="snzMax" value="${sp.snzMax }"></td>
                </tr>
                <tr>
                  <td>z :</td><td><input type="text" name="z" value="${sp.z }"></td>
                  <td>zMin: </td><td><input type="text" name="zMin" value="${sp.zMin }"> </td>
                  <td>zMax: </td><td><input type="text" name="zMax" value="${sp.zMax }"></td>
                </tr>
                <tr>
                  <td>planId:</td><td><input type="text" name="planId" value="${sp.planId }"></td>
                  <td><span>objtype: </td><td><input type="text" name="objtype" value="${sp.objtype }"></td>
                  <td><span>class: </td><td><input type="text" name="clz" value="${sp.clz }"></td>
                  <td>
                </tr>
                <tr><td colspan="6">
                    <label><input type="radio" name="or_and" value="0">or Query</label>
                    <label><input type="radio" name="or_and" value="1">and Query</label>
                    <input type="submit" value="Submit" class="btn1">
                    <input type="reset" value="Reset" class="btn1">
                  </td></tr>
              </table>
            </div>
            <div id="submitDiv">

            </div>
          </form>
          <div id="errorDiv">
            <c:forEach items="${ errorMsg}" var="message"><span class="error">${message }</span></c:forEach>

            </div>
 		<form  action=" " method="post" name="form1">
            <div id="DataDiv">
            <c:if test="${isFithas}">
              <table id="DataTable" class="table table-hover table-bordered">
                <thead>
                  <tr>
                  
                  <td><input type="checkbox" name="all" id="all" onchange="checkall()">ALL</td>
                    <th>planId</th>
                    <th>mjd</th>
                    <th>ra</th>
                    <th>dec</th>
                    <th>objtype</th>
                    <th>snu</th>
                    <th>sng</th>
                    <th>snr</th>
                    <!--                    <th>sni</th>
                                        <th>snz</th>
                                        <th>class</th>
                                        <th>z</th>-->
                    <th>file</th>
                    <th>show</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${ses.datas }" var="s">
                    <tr>
                    <td><input type="checkbox" name="attachmentPath" value="${s.path}"> </td>
                   
                      <td bordercolor="#009F00">${s.planId}</td>
                      <td>${s.mjd }</td>
                      <td>${s.ra }</td>
                      <td>${s.dec }</td>
                      <td>${s.objtype}</td>
                      <td>${s.snu }</td>
                      <td>${s.sng }</td>
                      <td>${s.snr }</td>
<!--                  <td>${s.sni }</td>
                      <td>${s.snz }</td>
                      <td>${s.clz }</td>
                      <td>${s.z }</td>-->
                      <td><a href="fits/${s.path}">FITS</a></td>
                      <td><a href="./appletindex.jsp?Name=${s.path}">SPEC</a></td>
                    </tr>
                  </c:forEach>
                </tbody>
                <tfoot align="right" style="border-right: 100px;">
                  <tr>
                  <input type="submit" value="Download" class="btn5" onclick="downAll()"/> 
                   <INPUT TYPE="submit" value="Show" class="btn5" onclick="showAll()"/>
                    <td colspan="15" style="text-align:right;margin-right:10px;">
                      <jsp:include page="/jsp/pager.jsp">
                        <jsp:param value="${ses.total }" name="totalRecord"/>
                        <jsp:param value="query/search" name="url"/>
                      </jsp:include>
                    </td>
                  </tr>
                </tfoot>
              </table>
            </c:if>
            <c:if test="${!isFithas}">
              <table  id="DataTable" class="table table-hover table-bordered" >
                <thead>
                  <tr>
                   <c:if test="${!empty names}">
 				<td><input id="box" name="box" type="checkbox" value="id|username" 
 onclick="checkonebox()"/><a>ALL</a></td>
 					</c:if>
                    <c:forEach items="${names}" var="name">
                      <td>${name}</td>
                      
                    </c:forEach>
                    <c:if test="${!empty names}">
                      <td>file</td>
                      <td>show</td>
                      
                    </c:if>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pager.datas}" var="map">
                    <tr>
                      <td><input id="box" name="box" type="checkbox" value="" 
 onclick="checkonebox()"/></td>
                      <c:forEach items="${map}" var="m">
                        <td>${m.value}</td>
                        </c:forEach>
                      <c:if test="${!empty names}">
                      <td><a href="fits/${s.path}">FiTS</a></td>
                      <td><a href="./appleindex.jsp">SPECVIEW</a></td>
                      </c:if>
                      </c:forEach>
                    </tr>
                  
                </tbody>
                <tfoot>
                  <c:if test="${!empty pager.datas}">
                    <tr>
                      <td colspan="${fn:length(names) + 2}" style="text-align:right;margin-right:10px;">
                        <jsp:include page="/jsp/pager.jsp">
                          <jsp:param value="${pager.total }" name="totalRecord"/>
                          <jsp:param value="query/search" name="url"/>
                        </jsp:include>
                      </td>
                    </tr>
                  </c:if>
                </tfoot>

              </table>

            </c:if>

          </div>
          </form>
          
          <c:forEach items="${errorMsg}" var="msg">
            ${msg}
          </c:forEach>
          <c:out value="${error}"></c:out>
          </div>
        </div>
      </div>
    <jsp:include page="/jsp/layout/footer-login.jsp"/>   
  </body>
</html>
