<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String[] record_id = request.getParameterValues("record_id");
	String params=new String();
	if(record_id==null){
		out.println("You don't have chosen any point!");
		return;
	}else{
		String[] record_id1s=record_id[0].split(",");
		String[] ra=request.getParameterValues("ra");
		String[] dec=request.getParameterValues("dec");
		
		for(int i=1;i<record_id1s.length; i++){
			params+=ra[Integer.parseInt(record_id1s[i])]+" "+dec[Integer.parseInt(record_id1s[i])]+",";
		}
	}
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Aladin Java Applet</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	
	$(function(){
		
		var targets=new Array();
		var target="<%=params%>";
		targets=target.split(",");
	      for(var i=0;i<targets.length-1;i++){
	    	var applet = document.aladin;
	      	applet.execAsyncCommand("get Aladin,Simbad "+targets[i]);
	      }
	});
      </script>

  </head>
  
  <body>
      
      <APPLET
         name="aladin" 
         archive="Aladin.jar"
         code="cds.aladin.Aladin"
         codebase="./resources/applet" style="width:100%;height:600px;">
         <PARAM name="script" value="reset;mview 4;grid on;help off">
      </APPLET>
  </body>
</html>
