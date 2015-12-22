<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
	$(document).ready(function () {
		$("#exportData").validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
		var opzioni = [];
		var select = document.getElementById("ente");
		var el = document.createElement("option");
        el.textContent = 'Tutti gli enti';
        el.value = -1;
        select.appendChild(el);

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

</script>

<style>
.okButton {
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
	font-family:Verdana;
	font-size:15px;
	padding:6px 24px;
	text-decoration:none;
	text-shadow:0px 1px 0px #528ecc;
}
.okButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #378de5), color-stop(1, #79bbff));
	background:-moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5', endColorstr='#79bbff',GradientType=0);
	background-color:#378de5;
}
.okButton:active {
	position:relative;
	top:1px;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

</style>
</head>
<body>
	<div class="site-container">
		<div id="tabs">
			<ul>
				<li><a href="#tableExport">Tabelle da esportare</a></li>
			</ul>
			<div id="tableExport" align="left">
				<h3 style="color: red">
					<s:property value="errorMsg"></s:property>
				</h3>
				<form id="exportData" action="exportReport" onSubmit="javascript: return $(this).validationEngine('validate');">
				<table>		
					<tr>
					<td>
						<label for="filename">Nome del file (.xls)</label>
					</td>
					<td>
						<input 	type="text"
								class="validate[required, custom[fileName]]"
								size="40" 
								id="filename" 
								name="filename"
						/>
					</td>
					</tr>					
					<tr>
					<td>
						<label for="ente">Ente da esportare</label>
					</td>
					<td>
						<select	id="ente" 
								name="ente"
								>
						</select>
					</td>
					</tr>
					<tr>
					<td valign="middle">
						Contesti disponibili
					</td>
					<td>
						<fieldset>
						 <input class="validate[groupRequired[tables]]" type="checkbox" name="Assistiti" value="true" checked/>Assistiti
						 <br /> 
						 <input class="validate[groupRequired[tables]]" type="checkbox" name="Eccedenze" value="true" checked/>Eccedenze 
						 <br />
						 <input class="validate[groupRequired[tables]]" type="checkbox" name="Utenti" value="true" checked/>Utenti 
						 <br />
						 <input class="validate[groupRequired[tables]]" type="checkbox" name="Reference" value="true" checked/>Tabelle di Riferimento 
						</fieldset>
					</td>
					</tr>

					<tr>
					<td colspan="2" align="right">
						<input type="submit" class="okButton" name="export" id="export" value="Esporta"></input>
					</td>
					</tr>
				</table>	
				</form>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$("#tabs").tabs();
		});
	</script>
</body>
</html>