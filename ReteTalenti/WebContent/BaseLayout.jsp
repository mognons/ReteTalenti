<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="css/jquery-ui.structure.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!-- Add conditional for IE7 + 8 support €“-->
<!--[if lte IE 8]>
<script src="scripts/html5.js"></script>
<![endif]-->
<!-- jQuery script file. -->
<script src="scripts/jquery-2.1.4.js" type="text/javascript"></script>
<script src="scripts/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="scripts/datepicker-it.js"></script>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">

	function stickyFooter() {
		var bodyHeight = $("body").height();
		var vwptHeight = $(window).height();
		if (vwptHeight > bodyHeight) {
			$("footer#footer").css("position", "absolute").css("bottom", 0);
		} 
		else {
			$("footer#footer").css("position", "").css("bottom", "");
		}
	}
	$(document).ready(function() {
		stickyFooter();
	});
	$(window).resize(function() {
		stickyFooter();
	});
	$(window).scroll(function() {
		stickyFooter();
	});
</script>

<style>

header, footer {
        float:left;
		padding:1px 0;
		min-width:100%;
}
	

body {
    margin: 0;
    padding: 0;
}
    
</style>
</head>
<body>
<header><tiles:insertAttribute  name="header" /></header>
		<table width="100%" align="center">
			<tr valign="top">
				<td style="width:192px;"><tiles:insertAttribute name="menu" /></td>
				<td align="center"><tiles:insertAttribute name="body" /></td>
			</tr>
		</table>
<!-- 	</div> -->
<footer id="footer" >
		<div class="main-footer">&copy;2015 - <a href="http://www.acliverona.it" target="_new">ACLI Verona</a> - Developed by  
		<a href="http://www.5020.it" target="_new">5020-cinquantaventi</a></div>
</footer>
</body>
</html>