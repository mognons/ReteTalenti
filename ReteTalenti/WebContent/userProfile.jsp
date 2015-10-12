<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
	<div class="content-container">
		<div id="tabs">
			<ul>
				<li><a href="#userprofile">User profile</a></li>
			</ul>
			<div id="userprofile">
				<h2>
					Update profile for user <i> <s:property value="userFirstname"></s:property>
						<s:property value="userLastname"></s:property>
					</i>
				</h2>
				<h3 style="color: red">
					<s:property value="errorMsg"></s:property>
				</h3>
				<s:form action="updateUser">
					<s:textfield name="username" label="User" readonly="true"></s:textfield>
					<s:textfield name="userFirstname" label="First Name"
						required="true" size="30"></s:textfield>
					<s:textfield name="userLastname" label="Last Name" required="true"
						size="30"></s:textfield>
					<s:textfield name="userEmail" label="Email address" required="true"
						size="30"></s:textfield>
					<s:textfield name="oldPassword" label="Old password"
						type="password" size="30"></s:textfield>
					<s:textfield name="newPassword" label="New password"
						type="password" size="30"></s:textfield>
					<s:textfield name="newPassword2" label="Confirm new password"
						type="password" size="30"></s:textfield>
					<s:submit value="Update details"></s:submit>
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