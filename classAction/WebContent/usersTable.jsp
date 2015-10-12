<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>jTable in Struts 2</title>
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
<script src="js/usersTable.js" type="text/javascript"></script>

</head>
<body>
	<div id="dialog" title="System message"></div>
	<div id="UsersTableContainer"></div>
</body>
</html>