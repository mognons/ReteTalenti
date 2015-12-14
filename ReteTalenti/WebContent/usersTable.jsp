<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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

<script type="text/JavaScript">
	// Groups
	var enteUtente = '<s:property value="ente"/>';
	var gruppoUtente = '<s:property value="groupId"/>';
</script>
</head>
<body>
	<div id="dialog" title="System message"></div>
	<div id="UsersTableContainer"></div>
	<script src="js/usersTable.js" type="text/javascript"></script>
</body>
</html>