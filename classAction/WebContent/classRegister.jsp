<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/jquery-ui.structure.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

<!-- jQuery script file. -->
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery-ui.min.js" type="text/javascript"></script>


<script src="js/jquery.jtable.js" type="text/javascript"></script>
<script src="js/classTable.js" type="text/javascript"></script>
<script type="text/JavaScript">
	function getRequests() {
	    var s1 = location.search.substring(1, location.search.length).split('&'),
	        r = {}, s2, i;
	    for (i = 0; i < s1.length; i += 1) {
	        s2 = s1[i].split('=');
	        r[decodeURIComponent(s2[0]).toLowerCase()] = decodeURIComponent(s2[1]);
	    }
	    return r;
	};

	
	function loadClass() {
		var requestParam = getRequests();
		var courseId = requestParam["courseid"];
		var moduleId = requestParam["moduleid"];
		var startDate = requestParam["startdate"];
		console.log(startDate);
/*		console.log(requestParam["courseid"]);
		console.log(requestParam["moduleid"]);
 */		jtableClass(courseId, moduleId, startDate);
		return true;
	};


	$(document).ready(function() {
		loadClass();
	});
</script>

</head>
<body>
<div class="site-container">		
	<div id="classContainer"></div>
</div>
</body>
</html>