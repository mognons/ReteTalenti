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
			<li <s:if test="(enteEmporio)" >class="ui-state-disabled"</s:if>>Gestione Assistiti
				<ul>
					<li <s:if test="(enteEmporio)" >class="ui-state-disabled"</s:if> 
						onclick="window.location='assistitiLink';">Anagrafica Assistiti</li>
					<li <s:if test="(enteEmporio || groupId > 2)" >class="ui-state-disabled"</s:if>
						onclick="window.location='candidatureLink';">Candidatura Emporio</li>
					<li <s:if test="(enteEmporio || groupId > 2)" >class="ui-state-disabled"</s:if>
						onclick="window.location='rimozioneEmporioLink';">Rimozione Emporio</li>
				</ul>
			</li>
			<li>-</li>
			<li <s:if test="!(enteEmporio)" >class="ui-state-disabled"</s:if>>Gestione Emporio  
				<ul>
					<li onclick="window.location='accettazioneEmporioLink';">Accettazione Emporio</li>
					<li onclick="window.location='anagraficaEmporioLink';">Anagrafica Emporio</li>
					<li onclick="window.location='dismissioneEmporioLink';">Dimissione Emporio</li>
				</ul>
			</li>
			<li>-</li>
			<li>Gestione Eccedenze
				<ul>
					<li onclick="window.location='eccedenzeLink';">Segnalazione
						Eccedenze</li>
					<li onclick="window.location='impegniLink';">Prenotazione
						Ritiri</li>
					<li onclick="window.location='ritiriLink';">Elenco Ritiri Prenotati</li>
				</ul>
			</li>
			<li>-</li>
			<li>Report
				<ul>
				<li>Assistiti
					<ul>
						<li <s:if test="(groupId !=4)" >class="ui-state-disabled"</s:if>
							onclick="window.location='anagraficaCompletaReport.action';">Elenco Regionale</li>
						<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
							onclick="window.location='anagraficaXProvinciaEnteUserReport.action';">Elenco per Provincia</li>
						<li onclick="window.location='anagraficaEnteUserReport.action';">Elenco per Ente</li>
						<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
							onclick="window.location='graduatoriaProvinciale.action';">Graduatoria Provinciale</li>
					</ul>
				</li>
				<li>Eccedenze
					<ul>
						<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
							onclick="window.location='eccedenzeReport.action';">Elenco Eccedenze</li>
                        <li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
							onclick="window.location='ritiriCompletoReport.action';">Elenco Prenotazioni</li>
					</ul>
				</li>
				</ul>
			</li>
			<s:if test="(#isAdmin || #isAdminEnte)">
				<li>-</li>
				<li>Sistema
					<ul>
						<li onclick="window.location='usersLink';">Gestione Utenti</li>
						<s:if test="(#isAdmin)">
							<li onclick="window.location='entiLink';">Tabella Enti</li>
							<li onclick="window.location='nazioniLink';">Tabella Nazioni</li>
							<li onclick="window.location='provinceLink';">Tabella Province</li>
							<li onclick="window.location='uni_misuraLink';">Tabella Unità Misura</li>
							<li onclick="window.location='messagesLink';">
								<img src="icons/Message.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Messaggi</span></li>
						</s:if>
					</ul>
				</li>
			</s:if>
			<li>-</li>
			<li  onclick="window.location='Logout';">
				<img src="images/Power-Shutdown.png" align="bottom" height="16" width="16">
				<span style="vertical-align: top">Logout</span>
			</li>
		</ul>
	</div>

</body>
</html>