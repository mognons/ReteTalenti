<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.label {
	font-style: italic;
	text-align: right;
}

td.dato {
	font-weight:bold;
}

h1 {
  	border-bottom: 1px solid gray;
	padding: 1px 10px 1px 5px;
	margin-bottom: 3px;
}
table {
	padding: 1px 1px 1px 1px;
}

th {
	text-align: left;
    color: gray;
	font-style: italic;
}
</style>

</head>
<body>
<div class="site-container">
		<s:if test="(convivente.nome!=null)">
			<h1>Convivente</h1>
			<table>
				<tbody>
					<tr>
						<td class="label">Codice Fiscale</td>
						<td class="dato"><s:property value="%{convivente.cf_assistito_nf}"/></td>
					</tr>
					<tr>
						<td class="label">Nome</td>
						<td class="dato"><s:property value="%{convivente.nome}"/></td>
					</tr>
					<tr>
						<td class="label">Cognome</td>
						<td class="dato"><s:property value="%{convivente.cognome}"/></td>
					</tr>
					<tr>
						<td class="label">Parentela</td>
						<td class="dato"><s:property value="%{convivente.desc_tipo_parentela}"/></td>
					</tr>
					<tr>
						<td class="label">Data di Nascita</td>
						<td class="dato"><s:property value="%{convivente.data_nascita}"/></td>
					</tr>
			</table>
		</s:if>
			<h1>Assistito</h1>
			<table>
				<tbody>
					<s:if test="(convivente.nome!=null)">
						<tr>
							<td class="label">Codice Fiscale</td>
							<td class="dato"><s:property value="%{assistito.cod_fiscale}"/></td>
						</tr>
						</s:if>
					<tr>
						<td class="label">Nome</td>
						<td class="dato"><s:property value="%{assistito.nome}"/></td>
					</tr>
					<tr>
						<td class="label">Cognome</td>
						<td class="dato"><s:property value="%{assistito.cognome}"/></td>
					</tr>
					<tr>
						<td class="label">Nato il</td>
						<td class="dato"><s:property value="%{assistito.data_nascita}"/></td>
					</tr>
				</tbody>
			</table>


			<h1>Ente di riferimento</h1>
			<table>
				<tbody>
					<tr>
						<td class="label">Denominazione</td>
						<td class="dato"><s:property value="%{ente_riferimento.descrizione}"/></td>
					</tr>
					<tr>
						<td class="label">Responsabile</td>
						<td class="dato"><s:property value="%{ente_riferimento.responsabile}"/></td>
					</tr>
					<tr>
						<td class="label">Telefono</td>
						<td class="dato"><s:property value="%{ente_riferimento.resp_phone}"/></td>
					</tr>
					<tr>
						<td class="label">Posta Elettronica</td>
						<td class="dato"><a href="mailto:<s:property value="%{ente_riferimento.resp_email}"/>"><s:property value="%{ente_riferimento.resp_email}"/></a></td>
					</tr>
				</tbody>
			</table>
</div>

</body>
</html>