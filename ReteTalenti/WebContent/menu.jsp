<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/JavaScript">
	
</script>

</head>



<style>
.ui-menu {
	width: 180px;
}
a, a:visited, a:hover, a:active {
  color: inherit;
}
.special-link, .special-link:visited, .special-link:hover, .special-link:active {
  color: inherit;
}
</style>


<body>
	<!-- Builds Auth switches  -->
	<s:iterator value="groups">
		<s:if test="%{groupName.contains('Amministratori Sistema')}">
			<s:set var="isAdmin" value="%{'true'}" />
		</s:if>
		<s:elseif test="%{groupName.contains('Amministratori Ente')}">
			<s:set var="isAdminEnte" value="%{'true'}" />
		</s:elseif>
		<s:elseif test="%{groupName.contains('Operatori Ente')}">
			<s:set var="isUser" value="%{'true'}" />
		</s:elseif>
		<s:elseif test="%{groupName.contains('Students')}">
			<s:set var="isStudent" value="%{'true'}" />
		</s:elseif>
	</s:iterator>

	<div style="position: relative; left: 0px; top: 0px; z-index: 10">
		<ul id="verticalMenu">
			<li>Anagrafica Assistiti
				<ul>
					<li class="ui-state-disabled">Anagrafica Assistiti</li>
					<li class="ui-state-disabled">Inserimento Emporio</li>
					<li class="ui-state-disabled">Altro...</li>
				</ul>
			</li>
			<li>Gestione Eccedenze
				<ul>
					<li class="ui-state-disabled">Segnalazione Eccedenze</li>
					<li class="ui-state-disabled">Prenotazione Ritiri</li>
				</ul>
			</li>
			<li>-</li>
			<li>Report
				<ul>
					<li class="ui-state-disabled">Interrogazione Giacenze</li>
					<li class="ui-state-disabled">Elenco Assistiti</li>
					<li class="ui-state-disabled">Graduatoria Emporio</li>
				</ul>
			</li>
			<s:if test="%{#isAdmin}">
				<li>Sistema
					<ul>
						<li onclick="window.location='usersLink';">Gestione Utenti</li>
						<li class="ui-state-disabled">Tabella Indici di Bisogno</li>
						<li class="ui-state-disabled">Tabella Unità Misura</li>	
						<li class="ui-state-disabled">Tabella Enti</li>	
					</ul>
				</li>
			</s:if>
			<li>-</li>
			<li><a href="Logout">Logout</a></li>
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#verticalMenu").menu()
		});
	</script>
</body>
</html>