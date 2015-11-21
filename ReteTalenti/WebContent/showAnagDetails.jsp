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
}

td.dato {
	font-weight:bold;
}
</style>
</head>
<body>
	<div class="site-container">
		<h2><s:property value="assistito.descrizione" /></h2>
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
				</tr>
				<tr>
					<td class="dato"><s:property value="%{assistito.indirizzo_residenza}" /></td>
				</tr>
				<tr>
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
				<tr>
					<td>DATA INSERIMENTO</td>
					<td class="dato"><s:date name="assistito.data_inserimento" format="dd/MM/yyyy"/></td>
				</tr>
				<tr>
					<td>DATA FINE ASSISTENZA</td>
					<td class="dato"><s:property value="%{assistito.data_fine_assistenza}" /></td>
				</tr>
				<tr>
					<td>DATA CANDIDATURA</td>
					<td class="dato"><s:property value="%{assistito.data_candidatura}" /></td>
				</tr>
				<tr>
					<td>DATA DISMISSIONE</td>
					<td class="dato"><s:property value="%{assistito.data_dismissione}" /></td>
				</tr>
			</tbody>
		</table>

		<s:if test="note.size()!=0">
			<h3>Note</h3>
			<table>
				<thead>
					<tr>
						<th align="left">Data</th>
						<th>Annotazione</th>
					</tr>
				</thead>
				<s:iterator value="note">
					<tr>
						<td style="font-size: x-small;" width="5%"><s:date name="data_note" format="dd/MM/yyyy"/></td>
						<td width="95%"><s:property value="%{note_libere}" /></td>
					</tr>
				</s:iterator>
			</table>
		</s:if>

		<s:if test="(conviventi.size()!=0)">
			<h3>Stato di famiglia</h3>
			<table>
				<thead>
					<tr>
						<th align="left">Nome</th>
						<th>Cognome</th>
						<th>Data Di Nascita</th>
						<th>Sesso</th>
						<th>Relazione</th>
					</tr>
				</thead>
				<s:iterator value="conviventi">
					<tr>
						<td><s:property value="nome" /></td>
						<td><s:property value="cognome" /></td>
						<td><s:date name="data_nascita" format="dd/MM/yyyy"/></td>
						<td><s:if test='sesso=="M"'>Maschio</s:if>
							<s:elseif test='sesso=="F"'>Femmina</s:elseif>
							<s:else>Non specificato</s:else>	
						</td>
						<td><s:property value="desc_tipo_parentela" /></td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
	</div>
</body>
</html>