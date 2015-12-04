<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>

<style>
.ui-menu {
	width: 190px;
}

ul ul{
    display:none;
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

	<div style="height:42px; position:relative; left: 0px; top: 0px; z-index: 10">
		<ul id="verticalMenu">
			<li <s:if test="(enteEmporio)" >class="ui-state-disabled"</s:if>>
			<img src="icons/group.png" align="bottom" height="16" width="16">
				<span style="vertical-align: top">Gestione Assistiti</span>
				<ul>
					<li <s:if test="(enteEmporio)" >class="ui-state-disabled"</s:if> 
						onclick="window.location='assistitiLink';">Anagrafica</li>
					<li <s:if test="(enteEmporio || groupId > 2)" >class="ui-state-disabled"</s:if>
						onclick="window.location='candidatureLink';">Candidatura Emporio</li>
					<li <s:if test="(enteEmporio || groupId > 2)" >class="ui-state-disabled"</s:if>
						onclick="window.location='rimozioneEmporioLink';">Rimozione Emporio</li>
				</ul>
			</li>
			<li>-</li>
			<li <s:if test="!(enteEmporio)" >class="ui-state-disabled"</s:if>> 
				<img src="icons/shop_basket.png" align="bottom" height="16" width="16">
				<span style="vertical-align: top">Gestione Emporio</span>
				<ul>
					<li onclick="window.location='accettazioneEmporioLink';">Accettazione</li>
					<li onclick="window.location='anagraficaEmporioLink';">Anagrafica</li>
					<li onclick="window.location='dismissioneEmporioLink';">Dimissione</li>
				</ul>
			</li>
			<li>-</li>
			<li> <img src="icons/Food-Bunch-Ingredients-icon.png" align="bottom" height="16" width="16">
				<span style="vertical-align: top">Gestione Eccedenze</span>
				<ul>
					<li onclick="window.location='eccedenzeLink';">Segnalazione</li>
					<li onclick="window.location='impegniLink';">Prenotazione</li>
					<li onclick="window.location='ritiriLink';">Elenco Prenotazioni</li>
				</ul>
			</li>
			<li>-</li>
			<li> <img src="icons/report_data_online.png" align="bottom" height="16" width="16">
				<span style="vertical-align: top">Report</span>
				<ul>
					<li>Report Assistiti
						<ul>
							<li <s:if test="(groupId == 2 || groupId == 3)" >class="ui-state-disabled"</s:if>
								onclick="window.location='anagraficaCompletaReport.action';">Elenco Regionale</li>
							<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
								onclick="window.location='anagraficaXProvinciaEnteUserReport.action';">Elenco per Provincia</li>
							<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
								onclick="window.location='anagraficaEnteUserReport.action';">Elenco per Ente</li>
							<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
								onclick="window.location='graduatoriaProvincialeReport.action';">Graduatoria Provinciale</li>
						</ul>
					</li>
					<li <s:if test="(groupId >2)" >class="ui-state-disabled"</s:if>
								onclick="window.location='situazioneEccedenzeReport.action';">
								Elenco Eccedenze</li>
				</ul>
			</li>
			<s:if test="(#isAdmin || #isAdminEnte)">
				<li>-</li>
				<li>
					<img src="icons/Settings.png" align="bottom" height="18" width="18">
					<span style="vertical-align: top">Sistema</span>
					<ul>
						<li onclick="window.location='usersLink';">
							<img src="icons/User%20group.png" align="bottom" height="16" width="16">
							<span style="vertical-align: top">Utenti</span>
						</li>
						<s:if test="(#isAdmin)">
							<li onclick="window.location='entiLink';">
								<img src="icons/Database.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Enti</span>
							</li>
							<li onclick="window.location='nazioniLink';">
								<img src="icons/Database.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Nazioni</span>
							</li>
							<li onclick="window.location='provinceLink';">
								<img src="icons/Database.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Province</span>
							</li>
							<li onclick="window.location='uni_misuraLink';">
								<img src="icons/Database.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Unità di Misura</span>
							</li>
							<li onclick="window.location='messagesLink';">
								<img src="icons/Message.png" align="bottom" height="16" width="16">
								<span style="vertical-align: top">Messaggi</span></li>
						</s:if>
					</ul>
				</li>
			</s:if>
			<li>-</li>
			<li  onclick="window.location='Logout';">
				<img src="icons/Power-Shutdown.png" align="bottom" height="20" width="20">
				<span style="vertical-align: top">Logout</span>
			</li>
		</ul>
	</div>

</body>
</html>