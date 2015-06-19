
<html>
<head>
 	
</head>
<body>
<%
	
	//String FileName= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/fits/"+request.getParameter("Name");
	String FileName=request.getParameter("Name");
%>
	<jsp:plugin code="spv.SpecviewApplet" codebase="./resources/applet" archive="speczoo8.jar" type="applet" width="1200"
            height="675">
            <jsp:params>
            <jsp:param name="Name" value='<%=FileName%>'/>
            <jsp:param name="userName" value="${sessionScope.loginUser.username}"/>
            </jsp:params>
            
	</jsp:plugin>

<!-- 
	   
	<jsp:plugin code="appletMain.AppletTest.class" codebase="./resources/applet" type="applet" width="800"
            height="600">
            <jsp:params>
            <jsp:param name="Name" value="../fits/spec-55859-F5902_sp01-001.fits"/>
            <jsp:param name="image" value="../image/top.jpg"/>
            </jsp:params>
            
	</jsp:plugin>
   -->
  
</body>
</html>

</html>

 
