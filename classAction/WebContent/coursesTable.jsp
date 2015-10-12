<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/spectrum.css" />

<script src="js/jquery.jtable.js" type="text/javascript"></script>
<script src='js/spectrum.js'></script>

<script type="text/JavaScript">
	var courses = [];
	var modules = [];
	var selectedCourse;
	var selectedModule;
	// Groups
	var userGroups = [];
	<s:iterator value="groups">
	myGroup = '<s:property value="groupName"/>';
	userGroups.push(myGroup);
	</s:iterator>;

	
	function loadTodos() {
	};

	function loadClass() {
		myIndex = $('input[name=groupsSelection]').filter(':checked').val();
		selectedCourse = courses[myIndex];
		selectedModule = modules[myIndex];
		jtableClass(selectedCourse, selectedModule);
		return true;
	};

	function loadDocuments() {
		myIndex = $('input[name=groupsSelection]').filter(':checked').val();
		selectedCourse = courses[myIndex];
		selectedModule = modules[myIndex];
		jtableDocuments(selectedCourse, selectedModule);
		return true;
	};

	function resetJtable() {
		$('#classContainer').jtable('destroy');
		$('#DocumentsTableContainer').jtable('destroy');
	};

	$(document).ready(function() {
		var myUsername = "";
		var openTab = '<s:property value="openTab"/>';
		if (openTab == '')
			openTab = 0;

		$("#tabs").tabs({
			active : openTab
		});
	});
</script>

<style>
label, input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}
 #days { margin-top: 2em; }

</style>


</head>
<body>
	<div id="CoursesTableContainer"></div>
	<div id="dialog" title="System message"></div>
	<div id="dialog-form" title="Create Course Calendar" class="ui-widget">
		<form action="#">
			<fieldset>
  				<div id="errorMessage"></div>
				<label for="startDate">Start date</label> 
				<input type="text" name="startDate" id="startDate"	class="text ui-widget-content ui-corner-all">
				<input type="hidden" id="courseId" name="courseId"/>
				<label for="colorSelect">Choose events color</label>
				<input type='text' name="colorSelect" id="colorSelect">
  				<input type="hidden" id="calendarColor" name="calendarColor" value="#dfe3ee">
  				<input type="hidden" id="courseName" name="courseName">
				<div id="days">
					<p>Please select working days for this course</p>
					<input type="checkbox" id="monday" value="1"><label for="monday" class="text ui-widget-content ui-corner-all">Mon</label>
					<input type="checkbox" id="tuesday" value="2"><label for="tuesday" class="text ui-widget-content ui-corner-all">Tue</label>
					<input type="checkbox" id="wednesday" value="3"><label for="wednesday" class="text ui-widget-content ui-corner-all">Wed</label>
					<input type="checkbox" id="thursday" value="4"><label for="thursday" class="text ui-widget-content ui-corner-all">Thu</label>
					<input type="checkbox" id="friday" value="5"><label for="friday" class="text ui-widget-content ui-corner-all">Fri</label>
				</div>
				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>
	<script>
		$(function() {
			$("#startDate").datepicker({
				dateFormat: 'yy-mm-dd',
				beforeShowDay: $.datepicker.noWeekends,
				firstDay: 1
			});
		});
		$(function() {
		    $( "#check" ).button();
		    $( "#days" ).buttonset();
		  });
		$("#colorSelect").spectrum({
			change: function(color) {
				$('#calendarColor').val(color);
			},
    		color: "#f00",
    		showPaletteOnly: true,
    	    showPalette: true,
    	    color: '#dfe3ee',
    	    preferredFormat: "hex",
    	    hideAfterPaletteSelect:true,
    	    palette: [
    	        ['#dfe3ee', '#8b9dc3', '#ccff00','#fd482f','#a0d6b4'],
    	        ['#efbbff', '#d896ff', '#be29ec', '#800080','#660066'],
    	        ['#b3cde0', '#6497b1', '#005b96', '#03396c','#011f4b']
    	    ]
		});
	</script>
	<script src="js/coursesTable.js" type="text/javascript"></script>
</body>
</html>