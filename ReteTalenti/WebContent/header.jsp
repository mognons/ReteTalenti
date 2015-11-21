<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE h2 PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="main-header" style="position: relative">
		<table border="0" width="100%">
			<tr>
				<td style="vertical-align:bottom" width="15%" ><a href="home.action"><img
						alt="ReteTalenti" src="images/logoReteTalenti.png" height="75"
						width="160"></a></td>
				<td style="vertical-align:bottom" width="40%">
					<h2>
						<a href="profileLink">
							<span style="color:#FFF2B3; font-size:larger;">
								<s:property value="user.userFirstname"></s:property>
								<s:property value="user.userLastname"></s:property>
							</span>
							<s:if test="user.ente">
					 			<span style="color:#F3971F; font-size:smaller;">
					 				<s:property value="user.descrizioneEnte"/>
					 			</span>
							</s:if>
						</a>
					</h2>
				</td>
				<td align="right"><a href="http://www.regione.veneto.it" target="_new"><img
						alt="ReteTalenti" src="images/stemmacolore.png" height="75"
						width="90"></a></td>
			</tr>
		</table>
	</div>
</body>
</html>