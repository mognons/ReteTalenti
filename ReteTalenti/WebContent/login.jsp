<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery.validationEngine.js"></script>
<script type="text/javascript" src="scripts/jquery.validationEngine-it.js"></script>

<script type="text/javascript">
	$(document).ready(function () {
		$("#userData").validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
	});

</script>

<style>
.okButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	-webkit-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #79bbff), color-stop(1, #378de5));
	background:-moz-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-webkit-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-o-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-ms-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#79bbff', endColorstr='#378de5',GradientType=0);
	background-color:#79bbff;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #84bbf3;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Verdana;
	font-size:15px;
	padding:6px 24px;
	text-decoration:none;
	text-shadow:0px 1px 0px #528ecc;
}
.okButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #378de5), color-stop(1, #79bbff));
	background:-moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5', endColorstr='#79bbff',GradientType=0);
	background-color:#378de5;
}
.okButton:active {
	position:relative;
	top:1px;
}

</style>
<body>
	<div class="site-container">
		<div class="container">
			<div id="tabs">
				<ul>
					<li><a href="#login">Login</a></li>
				</ul>
				<div id="login">
					<h3 style="color: red">
						<s:property value="errorMsg"></s:property>
					</h3>
				<form id="userData" action="login" onSubmit="javascript: return $(this).validationEngine('validate');">
				<table>		
					<tr>
					<td>
						<label for="username">Nome utente</label>
					</td><td>
						<input 	type="text"
								class="validate[required]"
								size="30" 
								id="username" 
								name="username"
						/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="password">Password</label>
					</td><td>
					<input 	type="password"
							class="validate[condRequired[username]]"
							size="30" 
							id="password" 
							name="password" 
							value=""/>
					</td>
					</tr>

					<tr>
					<td colspan="2" align="right">
						<input type="submit" class="okButton" name="updateUser" value="Accedi"></input>
					</td>
					</tr>
				</table>	
				</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		var myUsername = "";
		$(function() {
			$("#tabs").tabs();
		});
	</script>
</body>
</html>