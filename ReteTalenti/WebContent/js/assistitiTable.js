﻿$(document).ready(function () {
	// CUT HERE
	var assistito;
	function trasferisciAssistito() {
		var startDate = $("#startDate").val();
		var enteDestinazione = $("#enteDestinazione").val();
		var motivazione = $("#motivazione").val();
		var nome = $("#nome").val();
		var cognome = $("#cognome").val();
		var cod_fiscale = $("#cod_fiscale").val();
		var ente_assistente = $("#ente_assistente").val();
		$.Deferred(function ($dfd) {
			$.ajax({
				traditional: true, // THIS IS MANDATORY FOR ARRAYS
				url: 'trasferisciAssistitiAction',
				type: 'POST',
				dataType: 'json',
				data: {
					startDate: startDate,
					enteDestinazione: enteDestinazione,
					motivazione: motivazione,
					nome: nome,
					cognome: cognome,
					cod_fiscale: cod_fiscale,
					ente_assistente: ente_assistente
				},
				success: function (data) {
					$dfd.resolve(data);
					if (data.message) {
						$('#errorMessage').html("<h3>"+data.message+"</h3	>");
					} else {
						dialog.dialog("close");	
					};
				},
				error: function () {
					alert("si è rotto");
					$dfd.reject();
				}
			});
		});
	};
	
	var dialog = $("#dialog-form").dialog({
		title: "Trasferimento Assistito ad altro Ente",
		autoOpen : false,
		height : 450,
		width : 400,
		modal : true,
		buttons : {
			"Trasferisci" : trasferisciAssistito,
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
			// allFields.removeClass("ui-state-error");
		}
	});
	var form = dialog.find( "form" ).on( "submit", function( event ) {
		event.preventDefault();
		addUser();
	});
	// CUT HERE
	
	
	
	
    $('#AssistitiTableContainer').jtable({
        title: 'Gestione Anagrafica Assistiti',
        paging: true, // Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, // Enable sorting
        defaultSorting : 'COD_FISCALE ASC', //Set default sorting
        selecting: (gruppoUtente==2),
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
		toolbarSearch:true,
		toolbarReset:true,
        jqueryuiTheme: false,
        actions: {
            listAction: 'listAssistitiAction',
            createAction: 'createAssistitiAction',
            updateAction: 'updateAssistitiAction'
        },
		toolbar: {
			items: [{
				text: 'Ri-attiva',
				icon: 'icons/Yes.png',
				cssClass: 'RIAT',
				tooltip: "Riattiva l'assistenza per il record selezionato",
				click: function () {
					return $.Deferred(function ($dfd) {
						var $selectedRows = $('#AssistitiTableContainer').jtable('selectedRows');
						$selectedRows.each(function () {
							var record = $(this).data('record');
							if (recordObfuscation(record.ente_assistente) || (!record.data_fine_assistenza)) {
								$("#dialog").dialog({
									modal: true,
									buttons: [{
						        	  text: "Chiudi",
							        	  click: function() {
							        		  $(this).dialog( "close" );
							        	  }
							          }],
							        open: function(){
							        	  $("#dialog").html("Operazione <b>non</b> consentita per questo assistito")
							        }
								});
							} else {
								$.ajax({
									url: 'riattivaAssistitiAction',
									type: 'POST',
									dataType: 'json',
									data: {
										cod_fiscale: record.cod_fiscale										
									},
									success: function (data) {
										$dfd.resolve(data);
										$("#dialog").dialog({
											modal: true,
											buttons: [
											   {
												   text: "Chiudi",
											       click: function() {
											       	  $(this).dialog( "close" );
											       }
											   }
											],
									        open: function(){
									        	  $("#dialog").html("Assistenza riattivata con successo")
									        }
										});
										$('#AssistitiTableContainer').jtable('reload');
									},
									error: function () {
										$dfd.reject();
									}
								});
							}
						})
					})}
			},
			{
				text: 'Termina',
				icon: 'icons/No.png',
				cssClass: 'DISA',
				tooltip: "Termina l'assistenza per il record selezionato",
				click: function () {
					return $.Deferred(function ($dfd) {
						var $selectedRows = $('#AssistitiTableContainer').jtable('selectedRows');
						$selectedRows.each(function () {
							var record = $(this).data('record');
							if (recordObfuscation(record.ente_assistente) || (record.data_fine_assistenza)) {
								$("#dialog").dialog({
									modal: true,
									buttons: [{
						        	  text: "Chiudi",
							        	  click: function() {
							        		  $(this).dialog( "close" );
							        	  }
							          }],
							        open: function(){
							        	  $("#dialog").html("Operazione <b>non</b> consentita per questo assistito")
							        }
								});
							} else {
								$.ajax({
									url: 'disattivaAssistitiAction',
									type: 'POST',
									dataType: 'json',
									data: {
										cod_fiscale: record.cod_fiscale										
									},
									success: function (data) {
										$dfd.resolve(data);
										$("#dialog").dialog({
											modal: true,
											buttons: [
											   {
												   text: "Chiudi",
											       click: function() {
											       	  $(this).dialog( "close" );
											       }
											   }
											],
									        open: function(){
									        	  $("#dialog").html("Assistenza Terminata con successo")
									        }
										});
										$('#AssistitiTableContainer').jtable('reload');

									},
									error: function () {
										$dfd.reject();
									}
								});
							}
						})
					})}
			},
			{
				text: 'Trasferimento',
				icon: 'icons/Forward.png',
				tooltip: 'Inizia la procedura di trasferimento di un assistito ad altro ente',
				cssClass: 'TRAS',
				click: function () {
					var $selectedRows = $('#AssistitiTableContainer').jtable('selectedRows');
					$selectedRows.each(function () {
						assistito = $(this).data('record');
						var name = assistito.name;
						var startDate = assistito.startDate;
						if (recordObfuscation(assistito.ente_assistente) || (assistito.data_fine_assistenza)) {
							$("#dialog").dialog({
								modal: true,
								buttons: [{
					        	  text: "Chiudi",
						        	  click: function() {
						        		  $(this).dialog( "close" );
						        	  }
						          }],
						        open: function(){
						        	  $("#dialog").html("Operazione <b>non</b> consentita per questo assistito")
						        }
							});
						} else {
							$('#dialog-form').find('input[name="startDate"]').val(tomorrow());
//							$('#dialog-form').find('input[name="enteDestinazione"]').val();
							$('#dialog-form').find('textarea[name="motivazione"]').val("Motivo del trasferimento");
							$('#dialog-form').find('input[name="cod_fiscale"]').val(assistito.cod_fiscale);
							$('#dialog-form').find('input[name="nome"]').val(assistito.nome);
							$('#dialog-form').find('input[name="cognome"]').val(assistito.cognome);
							$('#dialog-form').find('input[name="ente_assistente"]').val(assistito.ente_assistente);
							dialog.dialog( "open" );
						}
					}); // End of EACH
				}
			}
			]
        }, // END OF TOOLBAR
        fields: {
            // CHILD TABLE DEFINITION FOR "NUCLEO FAMILIARE"
            nucleo_familiare: {
                title: ' ',
                width: '1%',
                sorting: false,
                edit: false,
                create: false,
                display: function (assistito) {
                	if (recordObfuscation(assistito.record.ente_assistente) || (assistito.record.data_fine_assistenza)) 
                		{return '<center><b>-</b></center>';}
                    // Create an image that will be used to open child table
                    var $img = $('<CENTER><img src="icons/People.png" width="16" height="16" title="Nucleo familiare"/></center>');
                    $img.find('img').qtip({
        			    position: {
        			        viewport: $(window)
        			    }
        			});
                    // Open child table when user clicks the image
                    $img.click(function () {
                        $('#AssistitiTableContainer').jtable('openChildTable',$img.closest('tr'),
                        {
                        	title: 'Nucleo familiare di ' + assistito.record.nome + ' ' + assistito.record.cognome,
                            paging: true, // Enable paging
                            pageSize: 5, // Set page size (default: 10)
                            pageSizeChangeArea: false,
					        defaultSorting : 'CODICE_FISCALE ASC', //Set default sorting
							selecting: true,
							multiselect: false, 
							selectingCheckboxes: true, 
							selectOnRowClick: true,
                            actions: {
                                listAction: 'listNucleiFamiliariAction?cf_assistito_nf=' + assistito.record.cod_fiscale,
                                createAction: 'createNucleiFamiliariAction?cf_assistito_nf=' + assistito.record.cod_fiscale,
                                updateAction: 'updateNucleiFamiliariAction?cf_assistito_nf=' + assistito.record.cod_fiscale,
                                deleteAction: 'deleteNucleiFamiliariAction?cf_assistito_nf=' + assistito.record.cod_fiscale
                            },                                    
                            fields: {
                                codice_fiscale: {
                                	title: 'Codice Fiscale',
                                	key: true,
                                    inputTitle: 'Codice Fiscale' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required,funcCall[checkCF]]',
                                    width: '10%',
                                    list: true,
                                    create: true
                                },
                                nome: {
                                    title: 'Nome',
                                    inputTitle: 'Nome' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required]',
                                    width: '10%',
                                    list: true,
                                    edit: true,
                                    create: true
                                },
                                cognome: {
                                    title: 'Cognome',
                                    inputTitle: 'Cognome' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required]',
                                    width: '20%',
                                    list: true,
                                    edit: true,
                                    create: true
                                },
                                data_nascita: {
                                	title: 'Data Nascita',
                                    inputTitle: 'Data Nascita' + ' <span style="color:red">*</span>',
                					type: 'date',
                					displayFormat: 'dd/mm/yy',
                	                inputClass: 'validate[required] datepicker',
                                    list: true,
                                    edit: true,
                                    create: true
                                },
                                sesso: {
                                	title: 'Sesso',
                                	options: { 	'M': 'Maschio',
                        						'F': 'Femmina', 
                        						'-': 'Altro' },
                                    list: true,
                                    edit: true,
                                    create: true
                                },
                                tipo_parentela: {
                                	title: 'Relazione',
                                	options: 'Choose_GradiParentela',
                                    list: true,
                                    edit: true,
                                    create: true
                                }
                            },        
                            formCreated: function (event, data) {
                                data.form.validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
                                data.form.find('input[name=nome]').css('width', '200px');
                                data.form.find('input[name=cognome]').css('width', '200px');
                                data.form.find('select[name=sesso]').css('width', '100px');
                                data.form.find('select[name=tipo_parentela]').css('width', '150px');
                            },
                            // Validate form when it is being submitted
                            formSubmitting: function (event, data) {
                                return data.form.validationEngine('validate');
                            },
                            // Dispose validation logic when form is closed
                            formClosed: function (event, data) {
                                data.form.validationEngine('hide');
                                data.form.validationEngine('detach');
                            }, 
                            recordsLoaded: function(event, data) {
                          	  if (assistito.record.data_fine_assistenza) {
                          	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
                          	  }
                            },
                            rowInserted: function(event, data){
	                         	if (assistito.record.data_fine_assistenza) {
	                                data.row.find('.jtable-edit-command-button').hide();
	                                data.row.find('.jtable-delete-command-button').hide();
	                            }
	                        }
                        },
                        function (data) { // opened handler
                        	data.childTable.jtable('load');
                        }
                        );
                    });
                    return $img;
                }
            },
            idb: {  // Accesso alla form (pop-up) di calcolo dell'indice di bisogno
                title: '',
                width: '1%',
                edit: false,
                create: false,
                display: function (assistito) {
                	if (recordObfuscation(assistito.record.ente_assistente) || (assistito.record.data_fine_assistenza))  
                		{return '<center><b>-</b></center>';}
                	var $img = $('<center><img src="icons/Dollar.png" width="16" height="16" title="Calcolo Indice Di Bisogno"/></center>');
                    $img.find('img').qtip({
        			    position: {
        			        viewport: $(window)
        			    }
        			});
                   // Open Foreign Form
                    $img.click(function () {
                    	openPage('getDataIDBAction.action?cf_assistito_ib=' + assistito.record.cod_fiscale
                    			+ '&nome=' + assistito.record.nome
                    			+ '&cognome=' + assistito.record.cognome);
                    });
                    return $img;
                }
            },
            // CHILD TABLE DEFINITION FOR "NOTE ASSISTITO"
            note: {
                title: '',
                width: '1%',
                sorting: false,
                edit: false,
                create: false,
                display: function (assistito) {
                	if (recordObfuscation(assistito.record.ente_assistente) || (assistito.record.data_fine_assistenza))  
                		{return '<center><b>-</b></center>';}
                    // Create an image that will be used to open child table
                    var $img = $('<center><img src="icons/Notes.png" width="16" height="16" title="Annotazioni"/></center>');
                    $img.find('img').qtip({
        			    position: {
        			        viewport: $(window)
        			    }
        			});
                    // Open child table when user clicks the image
                    $img.click(function () {
                        $('#AssistitiTableContainer').jtable('openChildTable',$img.closest('tr'),
                        {
                        	title: 'Annotazioni per ' + assistito.record.nome + ' ' + assistito.record.cognome,
                            paging: true, // Enable paging
                            pageSize: 5, // Set page size (default: 10)
                            pageSizeChangeArea: false,
					        defaultSorting : 'DATA_NOTE DESC', //Set default sorting
                            actions: {
                                listAction: 'listNoteAction?cf_assistito_note=' + assistito.record.cod_fiscale ,
/*                                updateAction: 'updateNoteAction?cf_assistito_note=' + assistito.record.cod_fiscale, */
                                createAction: 'createNoteAction?cf_assistito_note=' + assistito.record.cod_fiscale,
                                deleteAction: 'deleteNoteAction?cf_assistito_nf=' + assistito.record.cod_fiscale
                            },                                    
                            fields: {
                                id: {
                                	title: '',
                                	key: true,
                                    list: false
                                },
                                operatore: {
                                	title: 'Operatore',
                                	options: 'Choose_Utenti',
                                	width: '13%',
                                	list: true,
                                	edit: false,
                                	create: false
                                },
                                data_note: {
                                	title: 'Data ins.',
                    				type: 'date',
                    				displayFormat: 'dd/mm/yy',
                    				width: '7%',
                                    list: true,
                                    edit: false,
                                    create: false
                                },
                                note_libere: {
                                    title: 'Nota',
                                    inputTitle: 'Note' + ' <span style="color:red">*</span>' + 
                                    			'<span style="color:gray; font-size:x-small;">(' + 
                                    			'<span id="charCount" ' +
                                    			'></span> su 1000)</span>',
                                    inputClass: 'validate[required], maxSize[1000]',
                                    width: '80%',
                                    type: 'textarea',
                                    list: true,
                                    edit: true,
                                    create: true
                                }
                            }, 
                            recordsLoaded: function(event, data) {
                            	  if (assistito.record.data_fine_assistenza) {
                            	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
                            	  }
                              },
                            rowInserted: function(event, data){
	                         	if (assistito.record.data_fine_assistenza) {
	                                data.row.find('.jtable-edit-command-button').hide();
	                                data.row.find('.jtable-delete-command-button').hide();
	                              }
	                        },
                            formCreated: function (event, data) {
                                $('#Edit-note_libere').on('change keyup paste',function() {
                                	var $text = $(this).val();
                                	$('#charCount').html($text.length);
                                });
                                data.form.validationEngine('attach',{promptPosition : "topLeft", scroll: true});
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
                        },
                        function (data) { // opened handler
                        	data.childTable.jtable('load');
                        }
                        );
                    });
                    return $img;
                }
            },
            ente_assistente: {
            	title: 'Ente Assistente',
            	options: 'Choose_Enti',
            	width: '15%',
				searchable: true, // default is false, if set to true then a text input is created
				sqlOperator: '=',
//				sqlName: 'NOME_DELLA_COLONNA'
            	list: !onlyLocalData,
            	edit: false,
            	create: false
            },
            cod_fiscale: {
                key: true,
                title: 'Codice Fiscale',
                display: function(data) {
                	if (recordObfuscation(data.record.ente_assistente)) {
                		html = data.record.cod_fiscale;
                	} else {
	                	var page = "'ShowSchedaAssistito?codice_fiscale="+ data.record.cod_fiscale + "'";
						html = '<a href="javascript:showSchedaAssistito(' + page + ')'  
						+ ';" >' 
						+ data.record.cod_fiscale 
						+ '</a>';
                	}
					return html;
				},
                inputTitle: 'Codice Fiscale' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required],funcCall[checkCF]',
                width: '10%',
				searchable: true, // default is false, if set to true then a text input is created
//				sqlOperator: '=',
//				sqlName: 'NOME_DELLA_COLONNA'
                list: true,
                edit: false,
                create: true
            },
            nome: {
                title: 'Nome',
                inputTitle: 'Nome' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
                display: function(data) {
                	if (data.record.data_fine_assistenza!=null) {
                		return '<span style="color:red; font-style: italic;" >'+  data.record.nome + '</span>';
                	} else {
                		return data.record.nome;
                	}
                },
				searchable: true, // default is false, if set to true then a text input is created
//				sqlOperator: '=',
//				sqlName: 'NOME_DELLA_COLONNA'
                list: true,
                edit: true,
                create: true
            },
            cognome: {
                title: 'Cognome',
                inputTitle: 'Cognome' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '30%',
                display: function(data) {
                	if (data.record.data_fine_assistenza!=null) {
                		return '<span style="color:red; font-style: italic;" >'+  data.record.cognome + '</span>';
                	} else {
                		return data.record.cognome;
                	}
                },
				searchable: true, // default is false, if set to true then a text input is created
//				sqlOperator: '=',
//				sqlName: 'NOME_DELLA_COLONNA'
                list: true,
                edit: true,
                create: true
            },
            sesso: {
            	title: 'Sesso',
            	options: { 	'M': 'Maschio',
    						'F': 'Femmina', 
    						'-': 'Altro' },
    			searchable: true,
                list: onlyLocalData,
                edit: true,
                create: true
            },
            stato_civile: {
                title: 'Stato Civile',
                inputTitle: 'Stato Civile' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                options: 'Choose_StatiCivili',
                list: false,
                edit: true,
                create: true
            },
            luogo_nascita: {
                title: 'Luogo di Nascita',
                inputTitle: 'Luogo di Nascita' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                list: false,
                edit: true,
                create: true
            },
            data_nascita: {
            	title: 'Data di Nascita',
                inputTitle: 'Data di Nascita' + ' <span style="color:red">*</span>',
				type: 'date',
				width: '10%',
				displayFormat: 'dd/mm/yy',
                inputClass: 'validate[required] datepicker',
				searchable: true, // default is false, if set to true then a text input is created
				sqlOperator: '>=',
//				sqlName: 'NOME_DELLA_COLONNA'
                list: true,
                edit: true,
                create: true
            },
            nazionalita: {
            	title: 'Nazionalità',
            	options: 'Choose_Nazioni',
            	list: false,
                edit: true,
                create: true
            },
            indirizzo_residenza: {
                title: 'Residenza',
                inputTitle: 'Indirizzo di Residenza' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                list: false,
                edit: true,
                create: true
            },
            citta_residenza: {
                title: 'Città Residenza',
                inputTitle: 'Città di Residenza' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                list: false,
                edit: true,
                create: true
            },
            cap: {
                title: 'CAP',
                inputTitle: 'CAP' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                list: false,
                edit: true,
                create: true
            },
            provincia: {
                title: 'Provincia Residenza',
                inputTitle: 'Provincia Residenza' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                options: 'Choose_Province',
                list: false,
                edit: true,
                create: true
            },
            permesso_soggiorno: {
            	title: 'Perm. Sogg.',
            	width: '7%',
				type: 'checkbox',
				defaultValue: 'N',
				values:  {'N' : 'No' ,'S' : 'Sì'},
				searchable: true,
//				sqlOperator: '=',
				list: true,
                edit: true
            },
            punteggio_idb: {
            	title: 'IdB',
            	inputTitle: 'Indice di bisogno',
            	width: '3%',
				searchable: true, // default is false, if set to true then a text input is created
				sqlOperator: '>=',
//				sqlName: 'NOME_DELLA_COLONNA'
                list: true,
                edit: true,
                display: function(data) {
                	if (data.record.punteggio_idb==0)
                		return '';
                	else
                		return '<span style="align:right">' + data.record.punteggio_idb + '</span>';
                },
                input: function(data) {
                    if (data.formType!="create") {
                		html='<input style="text-align:right; background-color: #F2F5F7;" size="3" readonly value="'+  data.record.punteggio_idb + '"/>';
                	} else {
                		html='<input style="text-align:right; background-color: #F2F5F7;" size="3" readonly value="0"/>';
                	}
                	return html;
				},
            },
            accesso_emporio: {
	            title: ' ',
	            width: '5%',
	            edit: false,
	            create: false,
	            display: function (assistito) {
	            	if (assistito.record.data_accettazione==null)
	            		{return '<center><b></b></center>';}
	                var $img = $('<CENTER><img src="icons/shop_basket.png" width="16" height="16" title="Utente dell\'emporio"/></CENTER>');
	                $img.find('img').qtip({
	            	    position: {
	            	        viewport: $(window)
	            	    }
	            	});
	                $img.click(function () {
	                	var page = "ShowSchedaAssistitoEmporio?codice_fiscale="+ assistito.record.cod_fiscale;
	                	showSchedaAssistito(page);
	                });
	                return $img;
	            }
            },
            telefono: {
                title: 'Telefono',
                inputTitle: 'Telefono' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required,custom[phone]]',
                list: false,
                edit: true,
                create: true
            },
            email: {
				inputTitle: 'Indirizzo Email ' + '<span style="color:red">*</span>',
				title : 'Email',
				inputClass: 'validate[required,custom[email]]',
				width : '15%',
				edit : true,
				create: true,
				list: false
            },
            num_documento: {
                title: 'Numero Documento',
                inputTitle: 'Numero Documento' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                list: false,
                edit: true,
                create: true
            },
            data_fine_assistenza: {
            	type: 'date',
                list: false,
                edit: false,
                create: false
            },
            data_inserimento: {
            	type: 'date',
                list: false,
                edit: false,
                create: false
            },
            data_candidatura: {
            	type: 'date',
                list: false,
                edit: false,
                create: false
            },
            data_accettazione: {
            	type: 'date',
                list: false,
                edit: false,
                create: false
            },
            data_dismissione: {
            	type: 'date',
                list: false,
                edit: false,
                create: false
            }
        },
        recordsLoaded: function(event, data) {
	      	if (addRecordObfuscation()) {
	      	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
	      	}
	     	if (gruppoUtente>2) {
	       	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.TRAS').remove();
	      	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.RIAT').remove();
	      	     $('#AssistitiTableContainer').find('.jtable-toolbar-item.DISA').remove();
	      	}
	        $(function() {
	        	$('#AssistitiTableContainer').find('.jtable-command-button').each(function() {
	    	        var tipContent = $(this).attr('oldtitle');
	    	        $(this).qtip({
	    	            content: tipContent,
	    	    	    position: {
	    	    	        viewport: $(window)
	    	    	    }
	    	        });
	    	    });
	        });
        },
        rowInserted: function(event, data){
        	if (recordObfuscation(data.record.ente_assistente) || (data.record.data_fine_assistenza)) {
              data.row.find('.jtable-edit-command-button').hide();
              data.row.find('.jtable-delete-command-button').hide();
            }
        },
        // Initialize validation logic when a form is created
        formCreated: function (event, data) {
            data.form.find('input[name=nome]').css('width', '200px');
            data.form.find('input[name=cognome]').css('width', '200px');
            data.form.find('input[name=luogo_nascita]').css('width', '200px');
            data.form.find('input[name=indirizzo_residenza]').css('width', '200px');
            data.form.find('input[name=citta_residenza]').css('width', '200px');
            data.form.find('input[name=cap]').css('width', '50px');
            data.form.find('input[name=num_documento]').css('width', '200px');
            data.form.find('input[name=email]').css('width', '200px');
            data.form.find('select[name=provincia]').css('width', '150px');
            data.form.find('select[name=stato_civile]').css('width', '150px');
            data.form.find('select[name=sesso]').css('width', '150px');
            data.form.find('select[name=nazionalita]').css('width', '150px');
            data.form.children(':lt(9)').wrapAll('<div class="col1"/>');
            data.form.children(':gt(0)').wrapAll('<div class="col2"/>');
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
 
    //Load all records when page is first shown
    $('.jtable-toolbar-item').qtip({
	    position: {
	        viewport: $(window)
	    }
	});

    $('#AssistitiTableContainer').jtable('load');
});