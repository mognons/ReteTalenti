<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>System Error</title>
<body>
	<div class="site-container">
		<div class="main-header" style="position: relative">
			<h2>
				FATAL ERROR: <s:property value="errorMessage"/> 
			</h2>
		</div>
	</div>
</body>
</html>