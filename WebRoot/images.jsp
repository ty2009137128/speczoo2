<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] ra=request.getParameterValues("ra");
String[] dec=request.getParameterValues("dec");
String[] paste=request.getParameterValues("pathMark");
String params=new String();
for(int i=0;i<paste.length; i++){
		params+=paste[i]+"%20"+ra[i]+"%20"+dec[i]+"%0d";
		
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SDSS Images</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	
			var paste="<%=params%>" ;
			window.location="http://skyserver.sdss.org/dr10/en/tools/chart/listinfo.aspx?paste="+ paste+"";
		
	

</script>
  </head>
  
</html>
