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
<script type="text/javascript" src="scripts/jquery.validationEngine-it.js"></script>
<script src="scripts/jquery.jtable.js" type="text/javascript"></script>

<!-- User defined Jtable js file -->
<script src="scripts/jquery.jtable.it.js" type="text/javascript"></script>

<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<style>
.col1 {
	float: left;
	padding: 5px 10px 5px 5px;
	width: 45%;
}

.col2 {
	float: right;
	padding: 8px 5px 5px 10px;
	width: 45%;
}

input.text {
	margin-bottom: 12px;
	width: 50%;
	padding: .4em;
}

label {
  display: block;
  margin: 30px 0 0 0;
}

select {
  width: 300px;
}

textarea {
  width: 300px;
}

.overflow {
  height: 200px;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}
 #days { margin-top: 2em; }
 
.searchButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	-webkit-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #79bbff), color-stop(1, #378de5));
	background:-moz-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-webkit-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-o-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-ms-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#79bbff', endColorstr='#378de5',GradientType=0);
	background-color:#79bbff;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #84bbf3;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:13px;
	font-weight:bold;
	font-style:italic;
	padding:3px 20px;
	text-decoration:none;
	text-shadow:0px 1px 0px #528ecc;
}
.searchButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #378de5), color-stop(1, #79bbff));
	background:-moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5', endColorstr='#79bbff',GradientType=0);
	background-color:#378de5;
}
.searchButton:active {
	position:relative;
	top:1px;
}

.titleBar {
    background: url("icons/danger-icon.png") no-repeat;
    display: inline-block;
    background-size: 24px 24px; /* image's size */
    height: 24px; /* image's height */
    padding-left: 34px; /* image's width plus 10 px (margin between text and image) */
}

.titleBar span {
    height: 24px; /* image's height */
    display: table-cell;
    vertical-align: middle;
}

</style>

<script type="text/JavaScript">
	function ISOtoEuro(d) {
		d = d.substring(0,10);
		var dateParts = d.split("-");
		if(dateParts==d)
			dateParts = d.split("/");
		if(dateParts==d)
			dateParts = d.split(".");
		return (dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
	};
	function heute() {
		var today = new Date(); 
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (dd+"/"+mm+"/"+yyyy);
	};
	function tomorrow() {
		var today = new Date((new Date()).valueOf() + 1000*3600*24);
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (dd+"/"+mm+"/"+yyyy);
	};
	// Groups
	var enteUtente = '<s:property value="ente"/>';
	var gruppoUtente = '<s:property value="groupId"/>';

	
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
				ajaxCallData = [ {
					status : false
				} ];
				alert('Ajax error');
			}
		})
		return ajaxCallData;
	};

	function recordObfuscation(ente) {
		// Ritorna TRUE se l'ente passato da jTable, record per record, è diverso dall'ente di appartenenza dell'utente, impedendo
		// quindi la modifica o cancellazione del record stesso
		return (!(ente == enteUtente) || (gruppoUtente == 1));
	};

	function addRecordObfuscation() {
		return (gruppoUtente == 1);
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
				height : '500',
				title : 'Calcolo punteggio Indice di Bisogno'
			});
			$('#LoadRecordsButton').click();
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
				scrollable : true,
				width : '960',
				height : '600',
				title : 'Scheda Anagrafica',
				buttons : {
					"Chiudi" : function() {
						$('#pop-up').dialog("close");
					},
					"Stampa" : function() {
						window.frames["modalDialogBox"].print();
					}
				}
			});
		})();
	};

	function openDialog(errorMessage) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = "showCFDetails.action?codice_fiscale="
				+ errorMessage.cf_search + "&origin=" + errorMessage.origin;
		(function() {
			var $title = 'Codice Fiscale già inserito in Rete Talenti</span>';
			$('#pop-up').dialog({
				modal : true,
				resizable : false,
				draggable : false,
				width : '800',
				height : '600',
				buttons : {
					"Chiudi" : function() {
						$(this).dialog("close");
					}
				},
	            open: function(event, ui){
	                $(this).parent().find('.ui-dialog-titlebar').html('<span class="titleBar"><span>' + $title +'</span></span>');
	            }
			});
		})();
	};
	$(document).ready(function () {
	// Caricamento OPTIONS della SELECT da backend e senza STRUTTO!
	var opzioni = [];
	var select = document.getElementById("enteDestinazione");
	var el = document.createElement("option");

	$.getJSON('Choose_Enti', function(data) {
		$(data.options).each(function() {
			opzioni.push({
				value : $(this).attr('Value'),
				text : $(this).attr('DisplayText')
			});
			el = document.createElement("option");
	        el.textContent = $(this).attr('DisplayText');
	        el.value = $(this).attr('Value');
	        select.appendChild(el);
		})
	});
	});
	// FINE ESPERIMENTO
</script>

</head>
<body>
	<div id="filtering" class="ui-tabs-panel ui-widget-content ui-corner-bottom" align="LEFT">
		<form >
			<span class="jtable-title-text">Codice Fiscale</span>
			<input type="text" name="cf_search" id="cf_search" />
			<span class="jtable-title-text">Cognome</span>
			<input type="text" name="cognome_search" id="cognome_search" />
			<input type="submit" class="searchButton" id="LoadRecordsButton" value="Ricerca"/>
			<input type="button" class="searchButton" id="ResetButton" value="Tutti"/>
		</form>
	</div>
	<div id="pop-up" style="display: none;">
		<iframe style="width: 98%; height: 98%; background-color: #F2F5F7;" id="modalDialogBox" src=""></iframe>
	</div>
	<div id="dialog" title="Informazione dal sistema"></div>
	<div id="AssistitiTableContainer"></div>
	
	<div id="dialog-form" title="Trasferimento Assistito" class="ui-widget">
		<form action="#">
			<fieldset>
				<div id="errorMessage"></div>
				<label for="startDate">Data trasferimento</label>
				<input type="text"
					name="startDate" id="startDate"
					class="text ui-widget-content ui-corner-all">
				<label for="enteDestinazione">Scegliere un'ente</label> 
				<select name="enteDestinazione" id="enteDestinazione" class="text ui-widget-content ui-corner-all"></select>
				<label for="motivazione">Indicare la motivazione</label> 
				<textarea rows="4" cols="50"
					name="motivazione" id="motivazione"
					class="text ui-widget-content ui-corner-all"></textarea>	
				<input type="hidden" id="cod_fiscale" name="cod_fiscale" value="cod_fiscale"> 
				<input type="hidden" id="ente_assistente" name="ente_assistente" value="ente_assistente">
				<input type="hidden" id="nome" name="nome" value="nome">
				<input type="hidden" id="cognome" name="cognome" value="cognome">
				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>
		<script>
		$(function() {
			$("#startDate").datepicker({
				dateFormat: 'dd/mm/yy',
				beforeShowDay: $.datepicker.noWeekends,
				firstDay: 1
			});
		});
	  	$(function() {
			$( "#speed" ).selectmenu();
		});
	</script>
	<script src="js/assistitiTable.js" type="text/javascript"></script>
</body>
</html>