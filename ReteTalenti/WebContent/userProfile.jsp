<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
				<form action="updateUser">
				<table>		
					<tr>
					<td>
						<label for="emailUtente">Indirizzo email</label>
					</td><td>
						<input type="text" size="40" id="emailUtente" name="emailUtente" value="<s:property value="userEmail"></s:property>"/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="telefonoUtente">Numero di telefono</label>
					</td><td>
						<input type="text" size="20" id="telefonoUtente" name="telefonoUtente" value="<s:property value="userPhone"></s:property>"/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="oldPassword">Password precedente</label>
					</td><td>
						<input type="password" size="32" id="oldPassword" name="oldPassword" value=""/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="newPassword">Nuova password</label>
					</td><td>
					<input type="password" size="32" id="newPassword" name="newPassword" value=""/>
					</td>
					</tr>
					<tr>
					<td>
						<label for="newPassword2">Conferma nuova password</label>
					</td><td>
						<input type="password" size="32" id="newPassword2" name="newPassword2" value=""/>
					<input type="hidden" id="nomeutente" name="nomeutente" value="<s:property value="username"></s:property>"/>
					<s:submit value="Aggiorna Profilo"></s:submit>
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