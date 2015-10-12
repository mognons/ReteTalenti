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
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet"
	type="text/css" />

<!-- jTable script file. -->
<script src="js/jquery-1.8.2.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="js/jquery.jtable.js" type="text/javascript"></script>

<!-- jQuery Export plugin -->
<script type="text/javascript" src="js/tableExport.js"></script>
<script type="text/javascript" src="js/jquery.base64.js"></script>
<!-- PNG Export -->
<script type="text/javascript" src="js/html2canvas.js"></script>
<!--  PDF Export -->
<script type="text/javascript" src="js/jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="js/jspdf/jspdf.js"></script>
<script type="text/javascript" src="js/jspdf/libs/base64.js"></script>

<!-- User defined Jtable js file -->
<script src="js/userDefinedJtable.js" type="text/javascript"></script><body>
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