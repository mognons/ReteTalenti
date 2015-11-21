<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/validationEngine.jquery.css" rel="stylesheet"
	type="text/css" />

<!-- Import Javascript files for validation engine (in Head section of HTML) -->
<script type="text/javascript" src="scripts/jquery.validationEngine.js"></script>
<script type="text/javascript"
	src="scripts/jquery.validationEngine-it.js"></script>
<script src="scripts/jquery.jtable.js" type="text/javascript"></script>

<!-- User defined Jtable js file -->
<script src="scripts/jquery.jtable.it.js" type="text/javascript"></script>

<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<style>
.col1 {
	float: left;
	padding: 5px 10px 5px 5px;
	width: 45%;
}

.col2 {
	float: right;
	padding: 5px 5px 5px 10px;
	width: 45%;
}
</style>

<script type="text/JavaScript">
	// Groups
	var enteUtente = '<s:property value="ente"/>';
	var gruppoUtente = '<s:property value="groupId"/>';

	function loadTodos() {

	};
	function checkCF(field, rules, i, options) {
		var codiceFiscale = field.val();
		var valido = validaCodiceFiscale(codiceFiscale);
		var ajaxData = "";
		if (!valido) { // Validità formale NON verificata
			return options.allrules.codicefiscale.alertText;
		} else {
			ajaxData = CFIsUnique(codiceFiscale);
			if (ajaxData.status) {
				openDialog(ajaxData); // Codice già inserito in RT
				return options.allrules.codicefiscale.alertText;
			} else {
				return;
			}	
		}
	};

	function CFIsUnique(cf) {
		var ajaxCallData;
		$.ajax({
			url : 'checkCFIsUniqueAssistitiAction.action',
			type : 'POST',
			async : false,
			dataType : 'json',
			data : {
				cf_search : cf
			},
			success : function(data) {
				ajaxCallData = data;
			},
			error : function() {
				ajaxCallData = [{status:false}];
				alert('Ajax error');
			}
		})
		return ajaxCallData;
	};

	function recordObfuscation(ente) {
		// Ritorna TRUE se l'ente passato da jTable, record per record, è diverso dall'ente di appartenenza dell'utente, impedendo
		// quindi la modifica o cancellazione del record stesso
		return (!(ente == enteUtente) || (gruppoUtente==1));
	};

	function addRecordObfuscation() {
		// Ritorna TRUE se l'ente passato da jTable, record per record, è diverso dall'ente di appartenenza dell'utente, impedendo
		// quindi la modifica o cancellazione del record stesso
		return (gruppoUtente==1);
	};

	function validaCodiceFiscale(cf) {
		var validi, i, s, set1, set2, setpari, setdisp;
		if (cf == '')
			return '';
		cf = cf.toUpperCase();
		if (cf.length != 16)
			return false;
		validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (i = 0; i < 16; i++) {
			if (validi.indexOf(cf.charAt(i)) == -1)
				return false;
		}
		set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
		setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
		s = 0;
		for (i = 1; i <= 13; i += 2)
			s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
		for (i = 0; i <= 14; i += 2)
			s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
		if (s % 26 != cf.charCodeAt(15) - 'A'.charCodeAt(0))
			return false;
		return true;
	};
	
	function openPage(page) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = page;
		(function() {
			$('#pop-up').dialog({
				modal : true,
				resizable : false,
				draggable : false,
				width : '800',
				height : '400',
				title : 'Calcolo punteggio Indice di Bisogno'
			});
		})();
	};

	function showSchedaAssistito(page) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = page;
		(function() {
			$('#pop-up').dialog({
				modal : true,
				resizable : false,
				draggable : false,
				scrollable: true,
				width : '1024',
				height : '800',
				title : 'Scheda Anagrafica',
			    buttons: {
			    	"Chiudi": function() {
			    		$('#pop-up').dialog("close"); 
			    	},
			    	"Stampa": function() {
			    		window.frames["modalDialogBox"].print(); 
			    	} 
				} 
			});
		})();
	};

	
	function openDialog(errorMessage) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = "showCFDetails.action?codice_fiscale="+ errorMessage.cf_search+"&origin="+ errorMessage.origin;
		(function() {
			$('#pop-up').dialog({
				modal : true,
				resizable : false,
				draggable : false,
				width : '800',
				height : '600',
				title : 'Codice Fiscale già inserito in Rete Talenti',
			    buttons: { "Chiudi": function() { $(this).dialog("close"); } } 
			});
		})();
	};
</script>

</head>
<body>
	<div id="filtering"
		class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
		<form>
			Codice Fiscale <input type="text" name="cf_search"
				id="cf_search" /> 
			Cognome <input type="text"
				name="cognome_search" id="cognome_search" />
			<button type="submit" id="LoadRecordsButton">Ricerca</button>
			<button type="submit" id="ResetsButton">Tutti</button>
		</form>
	</div>
	<div id="pop-up" style="display: none;">
		<iframe style="width: 98%; height: 98%;" id="modalDialogBox" src=""></iframe>
	</div>
	<div id="dialog" title="System message"></div>
	<div id="AssistitiTableContainer"></div>
	<script src="js/assistitiTable.js" type="text/javascript"></script>
</body>
</html>