<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="css/validationEngine.jquery.css" rel="stylesheet"
	type="text/css" />
<link href="css/jquery-ui.structure.css" rel="stylesheet"
	type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/theme.css" rel="stylesheet" type="text/css" />
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet"
	type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="scripts/jquery.js" type="text/javascript"></script>
<script src="scripts/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="scripts/jquery.validationEngine.js"></script>
<script type="text/javascript"
	src="scripts/jquery.validationEngine-it.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/JavaScript">

var formMode = 'CALC';
$(document).ready(function() {
	$('#calcoloIDB').validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
	$('#calcoloIDB').submit(function () {
	});
});

function go() {
	var event = jQuery.Event( "submit" );
	// Validate Form
	if ($('#calcoloIDB').validationEngine('validate')) {
		// Calculate Data
		calcoloPunteggio();
		// Submit form
		if (formMode == 'SUBMIT') {
			$('#calcoloIDB').trigger(event);
			window.parent.$('.ui-dialog-content:visible').dialog('close');
		} else {
			formMode = 'SUBMIT';
			$('#submitButton').val("Aggiorna");
		}
	}

};

function calcoloPunteggio() {
	var isee = $('#calcoloIDB').find('input[name=isee_euro]').val();
	var cc = $('#calcoloIDB').find('input[name=cc_euro]').val();
	var ca = $('#calcoloIDB').find('input[name=ca_euro]').val();
	var cs = $('#calcoloIDB').find('input[name=cs_euro]').val();
	var stato_disoc = $('#calcoloIDB').find('select[name=stato_disoc] option:selected').val();
	var spese_imp = $('#calcoloIDB').find('input[name=spese_imp]').val();
	var urgenza = $('#calcoloIDB').find('select[name=urgenza] option:selected').val();
	var isee_punti = 0;
	var entrate_nc_punti = 0;
	var stato_disoc_punti = 0;
	var spese_imp_punti = 0;
	var urgenza_punti = 0;
	var totale_punti = 0;
  //**************CALCOLO PUNTI ISEE *************************************
	if (isee >= 0 && isee<=1000) {isee_punti=14;}
	else if (isee >= 1001 && isee<=2000){isee_punti=13;}
	else if (isee >= 2001 && isee<=4000){isee_punti=12;}
	else if (isee >= 4001 && isee<=5000){isee_punti=11;}
	else if (isee >= 5001 && isee<=6000){isee_punti=10;}
	else if (isee >= 6001 && isee<=7000){isee_punti=9;}
  //*************** CALCOLO PUNTI ENTRATE NON CONTEMPLATE ***************************************
	var entrate_nc = parseInt(cc) + parseInt(ca) + parseInt(cs);
	if (entrate_nc >= 1 && entrate_nc<=1000) {entrate_nc_punti=-1;}
	else if (entrate_nc >= 1001 && entrate_nc<=2000){entrate_nc_punti=-2;}
	else if (entrate_nc >= 2001 && entrate_nc<=3000){entrate_nc_punti=-3;}
	else if (entrate_nc >= 3001 && entrate_nc<=4000){entrate_nc_punti=-4;}
	else if (entrate_nc >= 4001 && entrate_nc<=5000){entrate_nc_punti=-5;}
	else if (entrate_nc >= 5001 && entrate_nc<=6000){entrate_nc_punti=-6;}
	else if (entrate_nc >= 6001 && entrate_nc<=7000){entrate_nc_punti=-7;}
	else if (entrate_nc > 7000){entrate_nc_punti=-8;}

//*************** CALCOLO SPESE IMPREVISTE 
	if (spese_imp >= 1 && spese_imp<=500) {spese_imp_punti=-1;}
	else if (spese_imp >= 501 && spese_imp<=1000){spese_imp_punti=-2;}
	else if (spese_imp >= 1001 && spese_imp<=2000){spese_imp_punti=-3;}
	else if (spese_imp > 2000){spese_imp_punti=-4;}
	
	if (spese_imp === 0) {spese_imp_punti=1;}
	else if (spese_imp===1) {spese_imp_punti=2;}
	else if (spese_imp===2) {spese_imp_punti=3;}
	else if (spese_imp===3) {spese_imp_punti=4;}
//*************** CALCOLO URGENZA *************************************	
	urgenza_punti = urgenza;
	stato_disoc_punti = stato_disoc;
	totale_punti= 	parseInt(isee_punti) + 
					parseInt(entrate_nc_punti) + 
					parseInt(stato_disoc_punti) + 
					parseInt(spese_imp_punti) + 
					parseInt(urgenza_punti);
	$('#calcoloIDB').find('input[name=isee_punti]').val(isee_punti);
	$('#calcoloIDB').find('input[name=entrate_nc_punti]').val(entrate_nc_punti);
	$('#calcoloIDB').find('input[name=stato_disoc_punti]').val(stato_disoc_punti);
	$('#calcoloIDB').find('input[name=spese_imp_punti]').val(spese_imp_punti);
	$('#calcoloIDB').find('input[name=urgenza_punti]').val(urgenza_punti);
	$('#calcoloIDB').find('input[name=totalepunti]').val(totale_punti);
};
</script>


<style>
.okButton {
	-moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
	-webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
	box-shadow: 0px 1px 0px 0px #f0f7fa;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #33bdef
		), color-stop(1, #019ad2));
	background: -moz-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -webkit-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -o-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: -ms-linear-gradient(top, #33bdef 5%, #019ad2 100%);
	background: linear-gradient(to bottom, #33bdef 5%, #019ad2 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#33bdef',
		endColorstr='#019ad2', GradientType=0);
	background-color: #33bdef;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #057fd0;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	padding: 6px 24px;
	text-decoration: none;
	text-shadow: 0px -1px 0px #5b6178;
}

.okButton:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #019ad2
		), color-stop(1, #33bdef));
	background: -moz-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -webkit-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -o-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: -ms-linear-gradient(top, #019ad2 5%, #33bdef 100%);
	background: linear-gradient(to bottom, #019ad2 5%, #33bdef 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#019ad2',
		endColorstr='#33bdef', GradientType=0);
	background-color: #019ad2;
}

.okButton:active {
	position: relative;
	top: 1px;
}

input[readonly] {
	background-color: #F2F5F7;
	color: red;
	border: none;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="ui-tabs-panel ui-widget-content ui-corner-bottom">
		<s:set var="codice_fiscale" value="recordIDB.cf_assistito_ib" />
		<span style="color: #2D89EF; font-weight: bold; font-size: 14pt;">Soggetto:
			<s:property value="nome" /> <s:property value="cognome" /> (<s:property
				value="recordIDB.cf_assistito_ib" />)
		</span>
		<form method="POST" id="calcoloIDB" name="calcoloIDB" id="calcoloIDB"
			action="updateIDBAction?cf_assistito_ib=<s:property value="codice_fiscale"/>&nome=<s:property value="nome"/>&cognome=<s:property value="cognome"/>"
			>
			<input type="hidden" name="id"
				value="<s:property value="recordIDB.id"/>">
			<table>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr valign="top">
					<td colspan="2" style="font-weight: bold;">DATI RICHIESTI</td>
					<td colspan="2" ALIGN="left" style="font-weight: bold;">CALCOLO
						PUNTEGGIO</td>
				</tr>
				<tr>
					<td style="font-weight: bold;">1. ISEE - Valore in EURO</td>
					<td><input name="isee_euro"
						value="<s:property value="%{recordIDB.isee_euro}"/>"
						class="validate[required,custom[integer]]"
						onChange="formMode = 'CALC';" /></td>
					<td>---</td>
					<td ALIGN="right"><input name="isee_punti" readonly size="2"
						value="<s:property value="%{recordIDB.isee_punti}"/>" style="text-align:right;"/></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">2. Contributo Comune per spese
						documentate</td>
					<td><input name="cc_euro"
						value="<s:property value="%{recordIDB.cc_euro}"/>"
						class="validate[required,custom[integer]]" /></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">3. Contributo affitto regione
						per spese documentate</td>
					<td><input name="ca_euro"
						value="<s:property value="%{recordIDB.ca_euro}"/>"
						class="validate[required,custom[integer]]" /></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">4. Contributo spot privato
						sociale per spese documentate</td>
					<td><input name="cs_euro"
						value="<s:property value="%{recordIDB.cs_euro}"/>"
						class="validate[required,custom[integer]]" /></td>
					<td>---</td>
					<td ALIGN="right"><input name="entrate_nc_punti" readonly
						size="2"
						value="<s:property value="%{recordIDB.entrate_nc_punti}"/>" style="text-align:right;"/></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">5. Stato di disoccupazione di
						lunga durata (mesi)</td>
					<td><select name="stato_disoc">
							<option
								<s:if test="(recordIDB.stato_disoc==1)">selected="selected"</s:if>
								value="1">1-3 mesi</option>
							<option
								<s:if test="(recordIDB.stato_disoc==2)">selected="selected"</s:if>
								value="2">4-6 mesi</option>
							<option
								<s:if test="(recordIDB.stato_disoc==3)">selected="selected"</s:if>
								value="3">7-9 mesi</option>
							<option
								<s:if test="(recordIDB.stato_disoc==4)">selected="selected"</s:if>
								value="4">Oltre 9 mesi</option>
							<option
								<s:if test="(recordIDB.stato_disoc==5)">selected="selected"</s:if>
								value="5">Mai percepita</option>
					</select></td>
					<td>---</td>
					<td ALIGN="right"><input name="stato_disoc_punti" readonly
						size="2"
						value="<s:property value="%{recordIDB.stato_disoc_punti}"/>" style="text-align:right;"/></td>
				<tr>
					<td style="font-weight: bold;">6. Spese impreviste e
						straordinarie"</td>
					<td><input name="spese_imp"
						value="<s:property value="%{recordIDB.spese_imp}"/>"
						class="validate[required,custom[integer]]" /></td>
					<td>---</td>
					<td ALIGN="right"><input name="spese_imp_punti" readonly
						size="2"
						value="<s:property value="%{recordIDB.spese_imp_punti}"/>" style="text-align:right;"/></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">7. Carattere di urgenza e
						condizione socio-economica"</td>
					<td><select name="urgenza">
							<option
								<s:if test="(recordIDB.urgenza==0)">selected="selected"</s:if>
								value="0">Situazione cronica</option>
							<option
								<s:if test="(recordIDB.urgenza==1)">selected="selected"</s:if>
								value="1">Miglioramento irrisorio</option>
							<option
								<s:if test="(recordIDB.urgenza==2)">selected="selected"</s:if>
								value="2">Miglioramento scarso</option>
							<option
								<s:if test="(recordIDB.urgenza==3)">selected="selected"</s:if>
								value="3">Miglioramento qualche</option>
							<option
								<s:if test="(recordIDB.urgenza==4)">selected="selected"</s:if>
								value="4">Miglioramento discrete</option>
							<option
								<s:if test="(recordIDB.urgenza==5)">selected="selected"</s:if>
								value="5">Miglioramento ottime</option>
					</select></td>
					<td>---</td>
					<td ALIGN="right"><input name="urgenza_punti" readonly
						size="2" value="<s:property value="%{recordIDB.urgenza_punti}"/>" style="text-align:right;"/></td>
				</tr>
				<tr>
					<td COLSPAN="3" ALIGN="right">TOTALE</td>
					<td COLSPAN="1" ALIGN="right"><input name="totalepunti"
						readonly size="2"
						value="<s:property value="%{recordIDB.totalepunti}"/>" style="text-align:right;"/></td>
				</tr>
				<tr>
					<td COLSPAN="4" ALIGN="right"><input type="button" value="Calcola"
						id="submitButton" class="okButton" onClick="go()"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>