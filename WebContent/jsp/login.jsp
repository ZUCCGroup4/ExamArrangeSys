<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">  
    function login(){  
        var form = document.forms[0];  
        form.action = "/ExamArrangeSys/login";  
        form.method="post";  
        form.submit();  
    }  
</script>
</head>
<body>
	<div>
		<h2>登录</h2>
		<form name="userLogin" action="">
			<input name="userid" id="userid" type="text" /> 
			<input name="pwd" id="pwd" type="password" />
			<p>
				<input type="button" value="登录" onclick="login()"> &nbsp; 
				<input type="reset" value="重置">
			</p>
			<div id="done"></div>
		</form>
	</div>

</body>
</html>