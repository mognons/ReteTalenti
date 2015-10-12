<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery.jtable.js" type="text/javascript"></script>
<script src="js/classTable.js" type="text/javascript"></script>
<script src="js/documentsTable.js" type="text/javascript"></script>
<script type="text/JavaScript">
	var courses = [];
	var modules = [];
	var selectedCourse;
	var selectedModule;
	function loadTodos() {
	};
	function heute() {
		var today = new Date(); 
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		return (yyyy+"/"+mm+"/"+dd);	
	}

	function loadClass() {
		myIndex = $('input[name=groupsSelection]').filter(':checked').val();
		selectedCourse = courses[myIndex];
		selectedModule = modules[myIndex];
		jtableClass(selectedCourse, selectedModule, heute());
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
		alert(today());
		// Groups
		var userGroups = [];
		<s:iterator value="groups">
		myGroup = '<s:property value="groupName"/>';
		userGroups.push(myGroup);
		console.log(myGroup);
		</s:iterator>;
	});
</script>

</head>
<body>
	<div class="site-container">
		<div id="tabs">
			<ul>
				<li><a href="#today" onclick="resetJtable()">Today</a></li>
				<s:if test="hasEvents">
					<li><a href="#todoList" onclick="loadTodos()">ToDo list</a></li>
				</s:if>
				<s:if test="hasEvents">
					<li><a href="#classContainer" onclick="loadClass()">Class
							Register</a></li>
				</s:if>
				<s:if test="hasEvents">
					<li><a href="#DocumentsTableContainer"
						onclick="loadDocuments()">Lesson's docs</a></li>
				</s:if>
			</ul>
			<div id="today">
				<div style="text-align: left; color: #2779AA;">
					Welcome, <i> <s:property value="userFirstname" /> <s:property
							value="userLastname" />
					</i> <br> Lorem ipsum, terque quaterque, in aeterna lux vivebat
					homo homini lupus <br>
					<s:if test="hasEvents">
						<h3>Today lessons:</h3>
						<s:iterator value="todayEvents" var="event" status="rowstatus">
							<script type="text/JavaScript">
								var myCourse = '<s:property value="courseId"/>';
								var myModule = '<s:property value="moduleId"/>';
								courses.push(myCourse);
								modules.push(myModule);
							</script>
							<input type="radio" name="groupsSelection" id="groupsSelection"
								value="<s:property value="%{#rowstatus.index}" />"
								<s:if test="#rowstatus.first == true ">checked</s:if>>
							<a href="coursesLink"><s:property value="title" /></a> - <s:property
								value="description" />
							<br>
						</s:iterator>
					</s:if>
				</div>
			</div>
			<div id="todoList"></div>
			<div id="classContainer"></div>
			<div id="DocumentsTableContainer"></div>
		</div>
	</div>
</body>
</html>