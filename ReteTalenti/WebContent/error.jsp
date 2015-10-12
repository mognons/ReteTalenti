<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jTable in Struts 2</title>
<!-- jTable metro styles. -->
<link href="css/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/metro/site.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />


<body>
	<div class="site-container">
		<div class="main-header" style="position: relative">
			<h1>
				<a href="./welcome.action">Main<span class="light">Menu</span></a>
			</h1>
			<h2>
				Welcome,  <s:property value="output"></s:property>  
			</h2>
		</div>
	</div>
</body>
</html>