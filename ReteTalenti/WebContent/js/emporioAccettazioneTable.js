$(document).ready(
		function() {
			// CUT HERE
			var assistito;
			function accettazione() {
				var codice_fiscale = $("#codice_fiscale").val();
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				$.Deferred(function($dfd) {
					$.ajax({
						traditional : true, // THIS IS MANDATORY FOR
						// ARRAYS
						url : 'EmporioInEmporioAction',
						type : 'POST',
						dataType : 'json',
						data : {
							cod_fiscale : codice_fiscale,
							data_accettazione : startDate,
							data_scadenza : endDate
						},
						success : function(data) {
							$dfd.resolve(data);
							console.log(data.message);
							if (data.message != null) {
								$('#errorMessage').html(
										'<span style="font-size:smaller;color:red">'
										+ data.message
										+ '</span>');
								$dfd.reject();
								return false;
							} else {
								$('#LoadRecordsButton').click();
								dialog.dialog("close");
								return true;
							}
							;
						},
						error : function(data) {
							$('#errorMessage').html(
									"<h3>" + data.message + "</h3	>");
							$dfd.reject();
						}
					});
				});
			}
			;
			var dialog = $("#dialog-form").dialog({
				title : "Accettazione in Emporio",
				autoOpen : false,
				height : 220,
				width : 400,
				modal : true,
				buttons : {
					"Accetta" : function() {
						if (accettazione())
							dialog.dialog("close");
					},
					Cancel : function() {
						dialog.dialog("close");
					}
				},
				close : function() {
					form[0].reset();
					// allFields.removeClass("ui-state-error");
				}
			});
			var form = dialog.find("form").on("submit",
					function(event) {
				event.preventDefault();
				console.log(form);
			});

			// CUT HERE
			$('#EmporioTableContainer').jtable(
					{
						title : "Graduatoria Provinciale Candidati",
						paging : true, // Enable paging
						pageSize: 15,
						pageSizes: [5,10,15],
						sorting : false, // Enable sorting
						defaultSorting : 'COD_FISCALE ASC', // Set
						selecting : true, // Enable selecting
						multiselect : false, // Allow
						selectingCheckboxes : true, // Show
						selectOnRowClick : false, // Enable
						pageSizeChangeArea : false,
						openChildAsAccordion : true,
						actions : {
							listAction : 'candidatiProvinciaEmporioAction'
						},
						toolbar : {
							items : [ {
								text : 'ACCETTAZIONE',
								icon : 'icons/Forward.png',
								tooltip : 'Accettazione di uno o più assisti',
								click : function() {
									var $selectedRows = $(
											'#EmporioTableContainer')
											.jtable(
											'selectedRows');
									$selectedRows
									.each(function() {
										assistito = $(this).data('record');
										var name = assistito.name;
										var startDate = assistito.startDate;
										if (assistito.data_fine_assistenza) {
											$("#dialog").dialog(
													{
														modal : true,
														buttons : [ {
															text : "Chiudi",
															click : function() {
																$(
																		this)
																		.dialog(
																				"close");
															}
														} ],
														open : function() {
															$(
																	"#dialog")
																	.html(
																	"Operazione <b>non</b> consentita per questo assistito")
														}
													});
										} else {
											$('#dialog-form').find('input[name="startDate"]').val(tomorrow());
											$('#dialog-form').find('input[name="endDate"]').val(tomorrow());
											$(
											'#dialog-form')
											.find(
											'input[name="codice_fiscale"]')
											.val(
													assistito.cod_fiscale);
											$(
											'#dialog-form')
											.find(
											'input[name="enteProvenienza"]')
											.val(
													enteUtente);
											dialog
											.dialog("open");
										}
									}); // End of EACH
								}
							} ]
						}, // END OF TOOLBAR
						fields : {
							ente_assistente : {
								title : 'Ente Assistente',
								options : 'Choose_Enti',
								list : true,
								edit : false,
								create : false
							},
							cod_fiscale : {
								key : true,
								title : 'Codice Fiscale',
								display : function(data) {
									if (recordObfuscation(data.record.ente_assistente)) {
										html = data.record.cod_fiscale;
									} else {
										var page = "'ShowSchedaAssistito?codice_fiscale="
											+ data.record.cod_fiscale
											+ "'";
										html = '<a href="javascript:showSchedaAssistito('
											+ page
											+ ')'
											+ '" target="_blank">'
											+ data.record.cod_fiscale
											+ '</a>';
									}
									return html;
								},
								inputTitle : 'Codice Fiscale'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required],funcCall[checkCF]',
									width : '10%',
									list : true,
									edit : false,
									create : true
							},
							nome : {
								title : 'Nome',
								inputTitle : 'Nome'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '10%',
									display : function(data) {
										if (data.record.data_fine_assistenza != null) {
											return '<span style="color:red; font-style: italic;" >'
											+ data.record.nome
											+ '</span>';
										} else {
											return data.record.nome;
										}
									},
									list : true,
									edit : true,
									create : true
							},
							cognome : {
								title : 'Cognome',
								inputTitle : 'Cognome'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '20%',
									display : function(data) {
										if (data.record.data_fine_assistenza != null) {
											return '<span style="color:red; font-style: italic;" >'
											+ data.record.cognome
											+ '</span>';
										} else {
											return data.record.cognome;
										}
									},
									list : true,
									edit : true,
									create : true
							},
							sesso : {
								title : 'Sesso',
								options : {
									'M' : 'Maschio',
									'F' : 'Femmina',
									'-' : 'Altro'
								},
								list : false,
								edit : true,
								create : true
							},
							stato_civile : {
								title : 'Stato Civile',
								inputTitle : 'Stato Civile'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '20%',
									options : 'Choose_StatiCivili',
									list : false,
									edit : true,
									create : true
							},
							luogo_nascita : {
								title : 'Luogo di Nascita',
								inputTitle : 'Luogo di Nascita'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '20%',
									list : false,
									edit : true,
									create : true
							},
							data_nascita : {
								title : 'Data di Nascita',
								inputTitle : 'Data di Nascita'
									+ ' <span style="color:red">*</span>',
									type : 'date',
									displayFormat : 'dd/mm/yy',
									inputClass : 'validate[required]',
									list : true,
									edit : true,
									create : true
							},
							data_candidatura : {
								title : 'Candidato il',
								type : 'date',
								displayFormat : 'dd/mm/yy',
								list : true,
								edit : false,
								create : false
							},
							nazionalita : {
								title : 'Nazionalità',
								options : 'Choose_Nazioni',
								list : false,
								edit : true,
								create : true
							},
							indirizzo_residenza : {
								title : 'Residenza',
								inputTitle : 'Indirizzo di Residenza'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '20%',
									list : false,
									edit : true,
									create : true
							},
							citta_residenza : {
								title : 'Città Residenza',
								inputTitle : 'Città di Residenza'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									width : '20%',
									list : false,
									edit : true,
									create : true
							},
							cap : {
								title : 'CAP',
								inputTitle : 'CAP'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									list : false,
									edit : true,
									create : true
							},
							provincia : {
								title : 'Provincia Residenza',
								inputTitle : 'Provincia Residenza'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									options : 'Choose_Province',
									list : false,
									edit : true,
									create : true
							},
							permesso_soggiorno : {
								title : 'Perm. Sogg.',
								width : '7%',
								type : 'checkbox',
								defaultValue : 'N',
								values : {
									'N' : 'No',
									'S' : 'Sì'
								},
								list : true,
								edit : true
							},
							punteggio_idb : {
								title : 'IdB',
								inputTitle : 'Indice di bisogno',
								width : '5%',
								list : true,
								edit : true,
								input : function(data) {
									if (!data.formType == "create") {
										html = '<input style="text-align:right; background-color: #F2F5F7;" size="3" readonly value="'
											+ data.record.punteggio_idb
											+ '"/>';
									} else {
										html = '<input style="text-align:right; background-color: #F2F5F7;" size="3" readonly value="0"/>';
									}
									return html;
								},
							},
							telefono : {
								title : 'Telefono',
								inputTitle : 'Telefono'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required,custom[phone]]',
									list : false,
									edit : true,
									create : true
							},
							email : {
								inputTitle : 'Indirizzo Email '
									+ '<span style="color:red">*</span>',
									title : 'Email',
									inputClass : 'validate[required,custom[email]]',
									width : '15%',
									edit : true,
									create : true,
									list : false
							},
							num_documento : {
								title : 'Numero Documento',
								inputTitle : 'Numero Documento'
									+ ' <span style="color:red">*</span>',
									inputClass : 'validate[required]',
									list : false,
									edit : true,
									create : true
							},
							data_fine_assistenza : {
								type : 'date',
								list : false,
								edit : false,
								create : false
							},
							data_inserimento : {
								type : 'date',
								list : false,
								edit : false,
								create : false
							},
							data_accettazione : {
								type : 'date',
								list : false,
								edit : false,
								create : false
							},
							data_dismissione : {
								type : 'date',
								list : false,
								edit : false,
								create : false
							}
						}
					});
			// Re-load records when user click 'load records' button.
			$('#LoadRecordsButton').click(function(e) {
				e.preventDefault();
				$('#EmporioTableContainer').jtable('load', {
					cf_search : $('#cf_search').val(),
					cognome_search : $('#cognome_search').val()
				});
			});

			$('#ResetButton').click(function(e) {
				e.preventDefault();
				$('#cf_search').val(null);
				$('#cognome_search').val(null);
				$('#EmporioTableContainer').jtable('load', {
					cf_search : $('#cf_search').val(),
					cognome_search : $('#cognome_search').val()
				});
			});

			// Load all records when page is first shown
			$('#LoadRecordsButton').click();
		});