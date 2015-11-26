$(document).ready(function () {
	$('#MessagesTableContainer').jtable(
			{
				title : 'Messaggi dal sistema ReteTalenti',
				paging : true, // Enable paging
				pageSize : 5, // Set page size (default: 10)
				sorting : false, // Enable sorting
				selecting : false, // Enable selecting
				multiselect : false, // Allow multiple selecting
				selectingCheckboxes : false, // Show checkboxes on first column
				selectOnRowClick : false, // Enable this to only select using
				// checkboxes
				pageSizeChangeArea : false,
				openChildAsAccordion : true,
				actions : {
					listAction : 'listMessageAction?listMode=VALID'
				},
				fields : {
					id : {
						key : true,
						list : false,
					},
					tag : {
						title : "",
						width : '1%',						
						display : function(data) {
							var $messaggio = data.record;
							var $_action = $messaggio.action.split('_');
							var $url = $_action.pop();
							var $action = ""
							$action = $_action[0];
							switch ($action) {
								case "READMSG":
									var $img = "icons/check-mark-icon.png";
									var $text = 'Segna il messaggio come letto';
									break;
								case "EXECUTE":
									var $img = "icons/run-icon.png";
									var $text = "Esegui l'azione richiesta";
									break;
								case "FOLLOW":
									var $img = "icons/link-icon.png";
									var $text = "Vai alla pagina indicata";
									break;
								default:
									var $img = "icons/warning-icon.png";
									var $text = "Informazione importante dal sistema";
							}
		                    var $html = $('<center><img src="' + $img + '" ' 
		                    			+ 'width="20" height="20" title="' + $text + '"/></center>');
		                    $html.click(function () {
		                    	if ($url)
		                    		identifyAction($messaggio);
		                    });
		                    return $html;
						}
					},
					message_text : {
						title : "Messaggio",
						width : '50%',
						create : true
					},
					document : {
						title : 'Document',
						width : '30%',
						list : false,
						display : function(data) {
							myFilename = data.record.documentPath;
							html = '<a href="' + data.record.documentPath
							+ ' " target="_blank"">'
							+ myFilename.split("/").pop() + '</a>';
							return html;
						}
					}
				}
			});
	$('#MessagesTableContainer').jtable('load');	
});