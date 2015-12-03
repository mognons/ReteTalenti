$(document).ready(function () {
	$('#MessagesTableContainer').jtable(
			{
				title : 'Messaggi sistema ReteTalenti',
				paging : true, // Enable paging
				pageSize : 15, // Set page size (default: 10)
				sorting : false, // Enable sorting
				selecting : true, // Enable selecting
				multiselect : true, // Allow multiple selecting
				selectingCheckboxes : true, // Show checkboxes on first column
				selectOnRowClick : true, // Enable this to only select using checkboxes
				pageSizeChangeArea : false,
				openChildAsAccordion : true,
				actions : {
					listAction : 'listMessageAction?listMode=ALL',
					deleteAction : 'deleteMessageAction',
					createAction : 'createMessageAction'
				},
				toolbar: {
					items: [
					{
						text: 'LETTO/DA LEGGERE',
						icon: 'icons/Yes.png',
						tooltip: "Marca i messaggi letti come non letti e viceversa",
						click: function () {
							return $.Deferred(function ($dfd) {
							var $selectedRows = $('#MessagesTableContainer').jtable('selectedRows');
							console.log($selectedRows);
							$("#dialog").dialog({
								modal: true,
								buttons: [{
					        	  text: "Conferma",
					        	  click: function() {
					        		  $selectedRows.each(function (e) {
					        			  var self = this;
					        			  var record = $(self).data('record');
											$.ajax({
												url: 'changeStatusMessageAction',
												type: 'POST',
												dataType: 'json',
												async : false,
												data: {
													messageID: record.id
												},
												success: function (data) {
													$dfd.resolve(data);
												},
												error: function () {
													$dfd.reject();
												}
											});
					        		  });
					        		  $('#MessagesTableContainer .jtable-row-selected').removeClass('jtable-row-selected');
					        		  $('#MessagesTableContainer').jtable('reload');
					        		  $(this).dialog('close');
					        	  }
						        },{
						        	  text: "Annulla",
						        	  click: function() {
						        		  $(this).dialog( "close" );
						        	  }
						          }
						        ],
						        open: function(){
						        	$("#dialog").html("Confermi l'operazione?")
						        }
							});
						})}
					}
					]
		        }, // END OF TOOLBAR
				fields : {
					id : {
						key : true,
						list : false,
					},
					tag : {
						title : "Categoria",
						width : '1%',
						options: {'A_INFO':'Info', 'A_HALT':'Allarme'},
						create: true,						
						display : function(data) {
							var $messaggio = data.record;
							var $_action = $messaggio.action.split('_');
							var $url = $_action.pop();
							var $action = ""
							$action = $_action[0];
							switch ($action) {
								case "READMSG":
									var $img = "icons/check-mark-icon.png";
									var $text = 'Informazione generica';
									break;
								case "EXECUTE":
									var $img = "icons/run-icon.png";
									var $text = "Azione";
									break;
								case "FOLLOW":
									var $img = "icons/link-icon.png";
									var $text = "Link";
									break;
								default:
									var $img = "icons/warning-icon.png";
									var $text = "Allarme di sistema";
							}
		                    var $html = $('<center><img src="' + $img + '" ' 
		                    			+ 'width="20" height="20" title="' + $text + '"/></center>');
		                    return $html;
						}
					},
					message_text : {
						title : "Testo del Messaggio",
                    	inputTitle: 'Testo del Messaggio' + ' <span style="color:red">*</span>',
						width : '50%',
						inputClass: 'validate[required]',
                        input: function (data) {
                        	return '<textarea name="message_text" rows="4" cols="50"></textarea>';
                        },
						create : true
					},
					ente : {
						title: 'Destinatario',
						width: '10%',
						options: 'Choose_MessageDestination',
						list: true,
						create: true
					},
					action : {
						title: 'Tipologia',
						width: '10%',
						options: function(data) {
							if (gruppoUtente==1) 
								return {'WARNING_':'Permanente','READMSG_markReadMessageAction':'Standard'};
							else
								return {'READMSG_markReadMessageAction':'Standard'};
						},
						list: false,
						create: true
					},
					start_date : {
						title: 'Inizio validità',
                    	inputTitle: 'Inizio validità' + ' <span style="color:red">*</span>',
						type: 'date',
						inputClass: 'validate[required, future[now]] datepicker',
						displayFormat: 'dd/mm/yy',
						create: true
					},
					end_date : {
						title: 'Fine validità',
                    	inputTitle: 'Inizio validità' + ' <span style="color:red">*</span>',
						type: 'date',
						displayFormat: 'dd/mm/yy',
						inputClass: 'validate[required, future[now]] datepicker',
						create: true
					},
					message_read : {
						title: 'Letto',
						width: '2%',
						display: function(data) {
							if (data.record.message_read == true) {
								var $img = "icons/Yes.png";
							} else {
								var $img = "icons/No.png";
							}
		                    var $html = $('<center><img src="' + $img + '" ' 
	                    			+ 'width="16" height="16"/></center>');
		                    return $html;
						},
						create: false
					}
				},
		        rowInserted: function(event, data){
		        	if (data.record.tag != 'A_INFO' && data.record.tag != 'A_HALT') {
		              data.row.find('.jtable-edit-command-button').hide();
		              data.row.find('.jtable-delete-command-button').hide();
		            }
		        },
		        // Initialize validation logic when a form is created
		        formCreated: function (event, data) {
		            data.form.validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
		        },
		        // Validate form when it is being submitted
		        formSubmitting: function (event, data) {
		            return data.form.validationEngine('validate');
		        },
		        // Dispose validation logic when form is closed
		        formClosed: function (event, data) {
		            data.form.validationEngine('hide');
		            data.form.validationEngine('detach');
		        }
			});
	$('#MessagesTableContainer').jtable('load');	
});