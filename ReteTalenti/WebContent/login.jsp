<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/JavaScript">
	$(document).ready(function() {
	var myUsername = "";
	var openTab = '<s:property value="openTab"/>';
	if (openTab == '')
		openTab = 0;
//	$("#tabs").tabs();
	$("#tabs").tabs({
			  active: openTab
	});
	
	$("#userFirstname")
		.keyup(function() {
					$("#username").val($(this).val().toLowerCase().trim().replace(/\s/g,'_'));
				})
		.focusout(function() {
					myUsername = $("#username").val() + ".";
	});
				
	$("#userLastname").keyup(function() {
		$("#username").val(myUsername + $(this).val().toLowerCase().trim()).replace(/\s/g,'_');
		});
	});

</script>

</head>
<body>
	<div class="site-container">
		<div class="container">
			<div id="tabs">
				<ul>
					<li><a href="#login">Login</a></li>
					<li><a href="#register">Sign up</a></li>
				</ul>
				<div id="login">
					<h3 style="color: red">
						<s:property value="errorMsg"></s:property>
					</h3>
					<s:form action="login">
						<s:textfield name="username" label="User Name" required="true"></s:textfield>
						<s:textfield name="password" label="Password" type="password" required="true"></s:textfield>
						<s:submit value="Login"></s:submit>
					</s:form>
				</div>
				<div id="register">
					<h3 style="color: red">
						<s:property value="errorMsg"></s:property>
					</h3>
					<s:form action="RegistrationAction">
						<s:textfield id="username" name="username" label="User" size="30" readonly="true"></s:textfield>
						<s:textfield id="userFirstname" name="userFirstname"
							label="First Name" size="30" required="true"></s:textfield>
						<s:textfield id="userLastname" name="userLastname" label="Last Name" size="30" required="true"></s:textfield>
						<s:textfield name="userEmail" label="Email address" size="30" required="true"></s:textfield>
						<s:textfield name="password" label="Password" type="password"
							size="30" required="true"></s:textfield>
						<s:submit value="Sign Up"></s:submit>
					</s:form>

				</div>
			</div>
		</div>
	</div>

</body>
</html>