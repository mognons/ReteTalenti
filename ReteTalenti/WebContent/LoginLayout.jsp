<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Add conditional for IE7 + 8 support ��-->
<!--[if lte IE 8]>
<script src="scripts/html5.js"></script>
<![endif]-->
<link href="css/jquery-ui.structure.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!-- jQuery script file. -->
<script src="scripts/jquery-2.1.4.js" type="text/javascript"></script>
<script src="scripts/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="scripts/datepicker-it.js"></script>

<script type="text/javascript">
$(document).ready(function() {
  var bodyHeight = $("body").height();
  var vwptHeight = $(document).height();
  if (vwptHeight > bodyHeight) {
    $("footer#colophon").css("position","absolute").css("bottom",0);
  }
});
</script>

<style>

footer {
        float:left;
		padding:1px 0;
		min-width:100%;
}
	
body {
    margin: 0;
    padding: 0;
}
    
</style>

<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body> 
<!-- 	<div class="site-container"> -->
<header><tiles:insertAttribute  name="header" /></header>
		<div><tiles:insertAttribute name="body" /></div>
<!-- 	</div> -->
<footer id="colophon" >
		<div class="main-footer">&copy;2015 - <a href="http://www.acliverona.it" target="_new">ACLI Verona</a> - Developed by  
		<a href="http://www.5020.it" target="_new">5020-cinquantaventi</a></div>
</footer></body>
</html>