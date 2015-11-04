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
		<s:if test="%{groupName.contains('Administrators')}">
			<s:set var="isAdmin" value="%{'true'}" />
		</s:if>
		<s:elseif test="%{groupName.contains('Tutors')}">
			<s:set var="isTutor" value="%{'true'}" />
		</s:elseif>
		<s:elseif test="%{groupName.contains('Users')}">
			<s:set var="isUser" value="%{'true'}" />
		</s:elseif>
		<s:elseif test="%{groupName.contains('Students')}">
			<s:set var="isStudent" value="%{'true'}" />
		</s:elseif>
	</s:iterator>

	<div style="position: relative; left: 0px; top: 0px; z-index: 10">
		<ul id="verticalMenu">
			<li>Gestione Assistiti
				<ul>
					<li class="ui-state-disabled">Anagrafica Assistiti</li>
					<li class="ui-state-disabled">Tabella Causali</li>
					<li class="ui-state-disabled">Tabella Nazioni</li>
				</ul>
			</li>
			<li>Gestione Donatori
				<ul>
					<li class="ui-state-disabled">Anagrafica Donatori</li>
					<li class="ui-state-disabled">Tabella Causali Donazioni</li>
					<li class="ui-state-disabled">Tabella Categorie Merceologiche</li>
				</ul>
			</li>
			<li>Gestione Prodotti
				<ul>
					<li class="ui-state-disabled">Anagrafica Prodotti</li>
					<li class="ui-state-disabled">Tabella Famiglia Prodotti</li>
					<li class="ui-state-disabled">Tabella Unità Misura</li>
				</ul>
			</li>
			<li>Gestione Magazzini Enti
				<ul>
					<li class="ui-state-disabled">Tabella Enti</li>
					<li class="ui-state-disabled">Tabella Magazzini Beneficiari</li>
					<li class="ui-state-disabled">Tabella Causali Movimenti Magazzino</li>
				</ul>
			</li>
			<li>-</li>
			<li>Gestione Movimenti
				<ul>
					<li class="ui-state-disabled">Donazione Assistiti</li>
					<li class="ui-state-disabled">Carico Magazzini</li>
					<li class="ui-state-disabled">Trasferimento tra Magazzini</li>
				</ul>
			</li>
			<li>-</li>
			<li>Report
				<ul>
					<li class="ui-state-disabled">Interrogazione Giacenze</li>
				</ul>
			</li>
			<s:if test="%{#isAdmin}">
				<li>Sistema
					<ul>
						<li onclick="window.location='usersLink';">Gestione Utenti</li>
						<li class="ui-state-disabled">Tabella Indici di Bisogno</li>
						<li class="ui-state-disabled">Tabella Indici testa</li>
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