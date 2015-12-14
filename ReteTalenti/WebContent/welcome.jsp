<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/JavaScript">
	var today = new Date(); 
	function heute() {
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		return (yyyy+"/"+mm+"/"+dd);	
	}

	function identifyAction(message) {
		var action = message.action.split('_')[0];
		var URL =  message.action.split('_').pop();
		// Build up URL complete with parameters
		URL=URL+"?messageID=" + message.id;
		if (message.key1 != null)				
			URL = URL + "&key1=" + message.key1;
		if (message.key2 != 0)				
			URL = URL + "&key2=" + message.key2;
		if (message.key3 != null)				
			URL = URL + "&key3=" + message.key3;
		console.log(message);
		if (message.ente==0)
			return;
// 		alert("Aspetta");
		var dialog_text = null;
		switch (action) {
			case "READMSG":
				dialog_text = 
					"<center>Hai letto: </center><i>" + message.message_text + "</i>?";
				break;
			case "EXECUTE":
				dialog_text = 
					"<center>Vuoi eseguire: </center><i>" + message.message_text + "</i>?";
				break;
			default:
		}
		if (dialog_text != null) {
			$("#dialog").dialog({
				modal: true,
				buttons: [
				  {
	        	  text: "Conferma",
		        	  click: function() {
			        	  $(this).dialog("close");
			      		  window.location.replace(URL);		
		        	  }
		          },
		          {
	        	  text: "Annulla",
		        	  click: function() {
			        	  $(this).dialog("close");
		        	  }
		          }
		        ],
		        open: function(){
		        	  $("#dialog").html(dialog_text);
		        }
			});
		} else {
			window.location.replace(URL);			
		}
	};

	$(document).ready(function() {

		$("#tabs").tabs({
			active : 0
		});
	});
</script>
<style>

h3 {
	border-bottom: 1px solid gray;
	padding: 1px 10px 1px 5px;
	margin-bottom: 3px;
}
</style>

<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
</head>
<body>
<s:set name="currentDate" value="%{javascript:heute();}" />
<div class="site-container">
		<div id="tabs">
			<ul>
				<li><a href="#today">Oggi è il <s:date
							name="currentDate" format="dd/MM/yyyy" />
					</a>
				</li>
			</ul>
			<div id="today">
				<div style="text-align:left; color: #2779AA; font-style:italic;">
					Benvenuto, <s:property value="user.userFirstname"/> <s:property
							value="user.userLastname"/>.
					<s:if test="hasEvents">
					Sono presenti alcuni messaggi a cui prestare attenzione
					</s:if>
					<s:else><br>Non sono presenti messaggi...</s:else>
				</div>
				<s:if test="hasEvents">
					<h3></h3>
					<div id="MessagesTableContainer"></div>
					<script src="js/messagesTable.js" type="text/javascript"></script>
				</s:if>
		</div>
		<div id="dialog" title="Informazione dal sistema"></div>
	</div>
</div>	
</body>
</html>