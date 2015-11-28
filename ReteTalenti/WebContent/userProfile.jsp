<%@ page language="java" contentType="text/html; charset=UTF-8"
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
</head>
<body>
	<div class="site-container">
		<div id="tabs">
			<ul>
				<li><a href="#userprofile">Profilo Utente</a></li>
			</ul>
			<div id="userprofile" align="left">
				<h3>
					Aggiornamento profilo per <i> <s:property value="userFirstname"></s:property>
						<s:property value="userLastname"></s:property>
						(<s:property value="username"></s:property>)
					</i>
				</h3>
				<h3 style="color: red">
					<s:property value="errorMsg"></s:property>
				</h3>
				<form id="userData" action="updateUser" onSubmit="javascript: return $(this).validationEngine('validate');">
				<table>		
					<tr>
					<td>
						<label for="emailUtente">Indirizzo email</label>
					</td><td>
						<input 	type="text"
								class="validate[required, custom[email]]"
								size="40" 
								id="emailUtente" 
								name="emailUtente"
								 value="<s:property value="userEmail"></s:property>"/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="telefonoUtente">Numero di telefono</label>
					</td><td>
						<input 	type="text" 
								class="validate[required, custom[phone]]"
								size="20" 
								id="telefonoUtente" 
								name="telefonoUtente" 
								value="<s:property 
								value="userPhone"></s:property>"/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="oldPassword">Password precedente</label>
					</td><td>
						<input type="password" size="20" id="oldPassword" name="oldPassword" value=""/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="newPassword">Nuova password</label>
					</td><td>
					<input 	type="password"
							class="validate[condRequired[oldPassword]]"
							size="20" 
							id="newPassword" 
							name="newPassword" 
							value=""/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="newPassword2">Conferma nuova password</label>
					</td><td>
						<input 	type="password"
								class="validate[condRequired[newPassword],equals[newPassword]]"
								size="20" 
								id="newPassword2" 
								name="newPassword2" 
								value=""/>
					</td>
					</tr>
					<tr>
					<td colspan="2" align="right">
						<input type="hidden" id="nomeutente" name="nomeutente" value="<s:property value="username"></s:property>"/>
						<input type="submit" class="okButton" name="updateUser" value="Aggiorna Profilo"></input>
					</td>
					</tr>
				</table>	
				</form>
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