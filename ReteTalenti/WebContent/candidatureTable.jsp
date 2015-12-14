<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- User defined Jtable js file -->
<script src="js/graduatoriaTable.js" type="text/javascript"></script>
<script src="js/candidatiTable.js" type="text/javascript"></script>

<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<style>
h1 {
  	border-top: 1px solid gray;
  	border-bottom: 1px solid gray;
	padding: 20px 20px 20px 20px;
	margin-top: 5px;
	margin-bottom: 5px;
}
</style>

<script type="text/JavaScript">
	function ISOtoEuro(d) {
		d = d.substring(0,10);
		var dateParts = d.split("-");
		if(dateParts==d)
			dateParts = d.split("/");
		if(dateParts==d)
			dateParts = d.split(".");
		return (dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
	};
	function heute() {
		var today = new Date(); 
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (dd+"/"+mm+"/"+yyyy);
	};
	function tomorrow() {
		var today = new Date((new Date()).valueOf() + 1000*3600*24);
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (dd+"/"+mm+"/"+yyyy);
	};
	// Groups
	var enteUtente = '<s:property value="ente"/>';
	var gruppoUtente = '<s:property value="groupId"/>';


	function openPage(page) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = page;
		(function() {
			$('#pop-up').dialog({
				modal : true,
				resizable : false,
				draggable : false,
				width : '800',
				height : '400',
				title : 'Calcolo punteggio Indice di Bisogno'
			});
		})();
	};

</script>

</head>
<body>
	<div id="dialog" title="Informazione dal sistema"></div>
	<div id="GraduatoriaTableContainer"></div>
	<h1></h1>
	<div id="CandidatiTableContainer"></div>
</body>
</html>