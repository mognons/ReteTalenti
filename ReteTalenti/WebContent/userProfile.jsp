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
				<s:form action="updateUser">
					<s:textfield name="userFirstname" label="Nome" required="true"
						size="45"></s:textfield>
					<s:textfield name="userLastname" label="Cognome" required="true"
						size="45"></s:textfield>
					<s:textfield name="userEmail" label="Indirizzo Email"
						required="true" size="30"></s:textfield>
					<s:textfield name="userPhone" label="Recapito telefonico"
						required="true" size="30"></s:textfield>
					<s:textfield name="oldPassword" label="Password precedente"
						type="password" size="30"></s:textfield>
					<s:textfield name="newPassword" label="Nuova password"
						type="password" size="30"></s:textfield>
					<s:textfield name="newPassword2" label="Conferma nuova password"
						type="password" size="30"></s:textfield>
					<s:submit value="Aggiorna Profilo"></s:submit>
				</s:form>
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