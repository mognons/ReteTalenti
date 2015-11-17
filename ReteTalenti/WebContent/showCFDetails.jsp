<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="site-container">
		<div class="container">
			<h1>
				<s:property value="user.username"></s:property>
			</h1>
			<h2>
				<s:property value="origin"></s:property>
			</h2>
		</div>
	</div>
</body>
</html>