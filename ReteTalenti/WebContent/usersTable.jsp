<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
 
<!-- Import Javascript files for validation engine (in Head section of HTML) -->
<script type="text/javascript" src="scripts/jquery.validationEngine.js"></script>
<script type="text/javascript" src="scripts/jquery.validationEngine-it.js"></script>
<script src="scripts/jquery.jtable.js" type="text/javascript"></script>

<!-- User defined Jtable js file -->
<script src="scripts/jquery.jtable.it.js" type="text/javascript"></script>
<script src="js/usersTable.js" type="text/javascript"></script>
<style>
.rosso label {
    font-weight: bold;
}
.rosso  {
    color: #e32;
    content: ' *';
    display:inline;
}

</style>
</head>
<body>
	<div id="dialog" title="System message"></div>
	<div id="UsersTableContainer"></div>
</body>
</html>