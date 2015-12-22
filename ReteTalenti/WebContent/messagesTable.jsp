<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/JavaScript">
// Groups
var enteUtente = '<s:property value="ente"/>';
var gruppoUtente = '<s:property value="groupId"/>';


	function ISOtoEuro(d) {
		d = d.substring(0,10);
		var dateParts = d.split("-");
		if(dateParts==d)
			dateParts = d.split("/");
		if(dateParts==d)
			dateParts = d.split(".");
		return (dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
	};
	
	
	var today = new Date();
	function heute() {
		var dd = today.getDate();
		var mm = today.getMonth() + 1;//January is 0! 
		var yyyy = today.getFullYear();
		if (dd < 10) {
			dd = '0' + dd
		}
		;
		if (mm < 10) {
			mm = '0' + mm
		}
		;
		return (yyyy + "/" + mm + "/" + dd);
	}

	$(document).ready(function() {

	});
</script>
<style>
h3 {
	border-bottom: 1px solid gray;
	padding: 1px 10px 1px 5px;
	margin-bottom: 3px;
}
</style>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
</head>
<body>
	<s:set name="currentDate" value="%{javascript:heute();}" />
	<div id="MessagesTableContainer"></div>
	<div id="dialog" title="Informazione dal sistema"></div>
	<script src="js/messagesFullTable.js" type="text/javascript"></script>
</body>
</html>