<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Expert platform-SpecView</title>
</head>
	<meta name="viewport" content="width=device-width,intial-scale=1" >
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">

     function SetText(){ 
    	 //var ss="99999.9999"
    	     var ss=document.speczoo.getRedShift();
			if(ss==null){
					ss="99999.9999";
				}
    	     document.getElementById("z").innerHTML =ss;
    	     document.getElementById("z").value = ss;
    	     var nn=document.speczoo.getShowName();
    	     if(nn=="Specview"){
					nn="Specview;QSO"
        	     }
        	   if(nn==null)
        	   {nn="Specview;QSO";} 
        	    
    	    //var nn="Specview;QSO";
    	     document.getElementById("showName").innerHTML =nn;
    	     document.getElementById("showName").value = nn;
	     	     
	}
    function saveToDB(){
    	 SetText();
    	 
		form1.action="spectrum/specview";
     }
  
    
    </script>
<body>

<%
	
	String FileName= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/fits/"+request.getParameter("Name");
	//String FileName=request.getParameter("Name");
	
%>
<jsp:include page="/jsp/layout/header.jsp"/>
<div style="width:1310px;height:600px" id="spec">
 <form action=" " method="post" name="form1" id="spectrumForm">
  <table width="1305" height="600px" border="0">
    <tr>
      <td width="1150px">
      <td width="1150px" height="600px" valign="top">
	      <div > 
	      	<applet code="spv.SpecviewApplet" codebase="./resources/applet" archive="speczoo14.jar" type="applet" width="1150px" height="600px"
	             id="speczoo">
	            <param name="Name" value='<%=FileName%>'>
	            <param name="userName" value="${sessionScope.loginUser.username}">
			</applet>
			</div>
	  </td>
     <td width="100px" ><table width="100px" >
        <tr>
          <td width="100px" height="81">
              <div >
               <h4>Z: </h4>
                  <input type="text" width="100px" id="z" name="z" />
              </div>
			  <div >
           		<h4>Name: </h4>
				<input type="text" width="100px" id="showName" name="showName" />
			  </div>
            </td>
        </tr>
        <tr>
          <td height="200px">
            
            <div align="right" class="spec-div">
              <input type="button" name="Submit" class="btn4" value="Change" onclick="SetText()"/>       
            </div>
            <h4>Note: </h4>
            <p>
              <textarea id="info" name="info" rows="10" style="height: 250px;"></textarea>
            </p>
            <p>&nbsp;</p>
            <div align="right" class="spec-div">
              <input type="submit" name="saved" id="saved" class="btn3" value="Save" onclick="saveToDB()"/>
            </div></td>
            
        </tr>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.loginUser.username}"/>

      </table></td>
    </tr>
  </table>
</form>
</div>
</body>
</html>
