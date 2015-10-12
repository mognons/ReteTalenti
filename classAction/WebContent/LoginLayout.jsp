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

<!-- jQuery script file. -->
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>


<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body> 
<!-- 	<div class="site-container"> -->
		<table align="center">
			<tr>
				<td height="30" colspan="2"><tiles:insertAttribute  name="header" /></td>
			</tr>
			<tr>
				<td colspan="2"><tiles:insertAttribute name="body" /></td>
			</tr>
			<tr>
				<td height="30" colspan="2" align="center"><tiles:insertAttribute name="footer" /></td>
			</tr>
		</table>
<!-- 	</div> -->
</body>
</html>