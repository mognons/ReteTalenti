<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/jquery-ui.structure.css" rel="stylesheet"
	type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet"
	type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		<h1>Dati Anagrafici</h1>
		<table>
			<tbody>
				<tr>
				</tr>
				<tr>
					<td class="label">Codice fiscale</td>
					<td class="dato"><s:property value="%{assistito.cod_fiscale}" /></td>
					<td style="font-size: x-small;"><s:if test='assistito.permesso_soggiorno=="S"'>(permesso di soggiorno)</s:if></td>
				</tr>
				<tr>
					<td class="label">Nome</td>
					<td  class="dato"><s:property value="%{assistito.nome}" /></td>
					<td class="label">Cognome</td>
					<td  class="dato"><s:property value="%{assistito.cognome}" /></td>
				</tr>
				<tr>
					<td class="label">Sesso</td>
					<td  class="dato"><s:if test='sesso=="M"'>Maschio</s:if>
							<s:elseif test='assistito.sesso=="F"'>Femmina</s:elseif>
							<s:else>Non specificato</s:else>
					</td>
					<td class="label">Stato Civile</td>
					<td  class="dato"><s:property value="assistito.desc_stato_civile" /></td>
				</tr>
				<tr>
					<td class="label">Nato a </td>
					<td class="dato"><s:property value="assistito.luogo_nascita" /></td>
					<td class="label">Il </td>
					<td class="dato"><s:date name="assistito.data_nascita" format="dd/MM/yyyy"/></td>
				</tr>
				<tr>
					<td class="label">Nazionalità</td>
					<td class="dato"><s:property value="assistito.denominazione" /></td>
					<td class="label">Documento numero</td>
					<td class="dato"><s:property value="%{assistito.num_documento}" /></td>
				</tr>
				<tr>
					<td class="label">Residenza</td>
					<td class="dato"><s:property value="%{assistito.indirizzo_residenza}" /></td>
				</tr>
				<tr>
					<td></td>
					<td class="dato"><s:property value="%{assistito.cap}" /> - <s:property value="%{assistito.citta_residenza}"/>
						(<s:property value="%{assistito.sigla_autom}"/>)
					</td>
				</tr>
				<tr>
					<td class="label">Telefono</td>
					<td class="dato"><s:property value="%{assistito.telefono}" /></td>
					<td class="label">Indirizzo eMail</td>
					<td class="dato"><a href="mailto:<s:property value="%{assistito.email}"/>"><s:property
								value="%{assistito.email}" /></a></td>
				</tr>
			</tbody>
		</table>

			<h1>Emporio</h1>
			<table>
				<tr>
					<td class="label">Emporio</td>
					<td class="dato"><s:property value="assistito.desc_emporio"/></td>
				</tr>
				<tr>
					<td class="label">Accettato dal </td>
					<td class="dato"><s:date name="assistito.data_accettazione" format="dd/MM/yyyy"/></td>
					<td class="label">Fino al</td>
					<td class="dato"><s:property value="assistito.data_scadenza" /></td>
				</tr>
				<tr>
					<td class="label">Indice di Bisogno</td>
					<td class="dato"><s:property value="assistito.punteggio_idb"/></td>
				</tr>
			</table>
	</div>
</body>
</html>