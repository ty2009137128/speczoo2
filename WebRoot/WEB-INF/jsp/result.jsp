<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Save Result</title>

 <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.8.3.js"></script>
<script type="text/javascript">  
var times=4;
go();
	function go()
	{
		
		window.setTimeout('go()',3000);
		
		times=times-1;
		 time.innerHTML =times;
		 window.history.go(-1);
		 
	}
</script>
</head>
<body>
<table>
<tr>
<td>Save Successfully !!!</td>
<td>This page will return to the previous page in </td>
<td> <a class="STYLE1" id= "time" style="color:red"> 3</a> </td>
<td>Seconds </td>
</tr>
</table>
</body>
</html>