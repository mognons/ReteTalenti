<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/jquery-ui.structure.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">
		<s:if test="(convivente.nome!=null)">
			<h3>Convivente</h3>
			<table>
				<tbody>
					<tr>
						<td>Codice Fiscale</td>
						<td><s:property value="%{convivente.cf_assistito_nf}"/></td>
					</tr>
					<tr>
						<td>Nome</td>
						<td><s:property value="%{convivente.nome}"/></td>
					</tr>
					<tr>
						<td>Cognome</td>
						<td><s:property value="%{convivente.cognome}"/></td>
					</tr>
					<tr>
						<td>Parentela</td>
						<td><s:property value="%{convivente.desc_tipo_parentela}"/></td>
					</tr>
					<tr>
						<td>Data di Nascita</td>
						<td><s:property value="%{convivente.data_nascita}"/></td>
					</tr>
			</table>
		</s:if>
			<h3>Assistito</h3>
			<table>
				<tbody>
					<s:if test="(convivente.nome!=null)">
						<tr>
							<td>Codice Fiscale</td>
							<td><s:property value="%{assistito.cod_fiscale}"/></td>
						</tr>
						</s:if>
					<tr>
						<td>Nome</td>
						<td><s:property value="%{assistito.nome}"/></td>
					</tr>
					<tr>
						<td>Cognome</td>
						<td><s:property value="%{assistito.cognome}"/></td>
					</tr>
					<tr>
						<td>Nato il</td>
						<td><s:property value="%{assistito.data_nascita}"/></td>
					</tr>
				</tbody>
			</table>


			<h3>Ente di riferimento</h3>
			<table>
				<tbody>
					<tr>
						<td>Denominazione</td>
						<td><s:property value="%{ente_riferimento.descrizione}"/></td>
					</tr>
					<tr>
						<td>Responsabile</td>
						<td><s:property value="%{ente_riferimento.responsabile}"/></td>
					</tr>
					<tr>
						<td>Telefono</td>
						<td><s:property value="%{ente_riferimento.resp_phone}"/></td>
					</tr>
					<tr>
						<td>Posta Elettronica</td>
						<td><a href="mailto:<s:property value="%{ente_riferimento.resp_email}"/>"><s:property value="%{ente_riferimento.resp_email}"/></a></td>
					</tr>
				</tbody>
			</table>
</div>

</body>
</html>