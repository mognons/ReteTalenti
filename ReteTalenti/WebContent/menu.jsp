<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>

<style>
.ui-menu {
	width: 220px;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#verticalMenu").menu()
	});
</script>

<body>
	<!-- Builds Auth switches  -->
	<s:if test="%{groupId == 1}">
		<s:set var="isAdmin" value="%{'true'}" />
	</s:if>
	<s:elseif test="%{groupId == 2}">
		<s:set var="isAdminEnte" value="%{'true'}" />
	</s:elseif>
	<s:elseif test="%{groupId == 3}">
		<s:set var="isUser" value="%{'true'}" />
	</s:elseif>
	<s:elseif test="%{groupId == 4}">
		<s:set var="isRegione" value="%{'true'}" />
	</s:elseif>

	<div style="position: relative; left: 0px; top: 0px; z-index: 10">
		<ul id="verticalMenu">
			<li>Gestione Assistiti
				<ul>
					<li onclick="window.location='assistitiLink';">Anagrafica
						Assistiti</li>
					<li class="ui-state-disabled">Altro...</li>
				</ul>
			</li>
			<li>-</li>
			<li>Gestione Emporio
				<ul>
					<li class="ui-state-disabled">Candidatura Emporio</li>
					<li class="ui-state-disabled">Inserimento Emporio</li>
					<li class="ui-state-disabled">Rimozione Emporio</li>
					<li class="ui-state-disabled">Altro...</li>
				</ul>
			</li>
			<li>-</li>
			<li>Gestione Eccedenze
				<ul>
					<li onclick="window.location='eccedenzeLink';">Segnalazione
						Eccedenze</li>
					<li onclick="window.location='impegniLink';">Prenotazione
						Ritiri</li>
				</ul>
			</li>
			<li>-</li>
			<li>Report
				<ul>
					<li class="ui-state-disabled">Elenco Eccedenze</li>
					<li class="ui-state-disabled">Elenco Prenotazioni</li>
					<li class="ui-state-disabled">Elenco Assistiti</li>
					<li class="ui-state-disabled">Graduatoria Emporio</li>
				</ul>
			</li>
			<li>-</li>
			<s:if test="%{#isAdmin}">
				<li>Sistema
					<ul>
						<li onclick="window.location='usersLink';">Gestione Utenti</li>
						<li onclick="window.location='entiLink';">Tabella Enti</li>
						<li onclick="window.location='nazioniLink';">Tabella Nazioni</li>
						<li onclick="window.location='provinceLink';">Tabella
							Province</li>
						<li onclick="window.location='uni_misuraLink';">Tabella Unità
							Misura</li>
					</ul>
				</li>
			</s:if>
			<li>-</li>
			<li><a href="Logout">Logout</a></li>
		</ul>
	</div>

</body>
</html>