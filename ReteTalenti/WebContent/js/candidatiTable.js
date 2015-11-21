$(document).ready(function () {
    $('#CandidatiTableContainer').jtable({
        title: "Candidati All'Emporio in ordine di graduatoria",
        paging: true, // Enable paging
        pageSize: 15, // Set page size (default: 10)
        sorting: false, // Enable sorting
        defaultSorting : 'COD_FISCALE ASC', //Set default sorting
        selecting: true, // Enable selecting
        multiselect: true, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: true, // Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'candidatiEmporioAction'
        },
		toolbar: {
			items: [
			{
				text: 'Rimuovi',
				icon: 'icons/Bad%20mark.png',
				tooltip: "Rimuove la candidatura all'emporio per uno o più assistiti",
				click: function () {
					return $.Deferred(function ($dfd) {
					var $selectedRows = $('#CandidatiTableContainer').jtable('selectedRows');
					$("#dialog").dialog({
						modal: true,
						buttons: [{
			        	  text: "Conferma",
				        	  click: function() {
				        		  $selectedRows.each(function () {
				        			  assistito = $(this).data('record');
				        			  console.log(assistito);
										$.ajax({
											url: 'rimuoviCandidaturaEmporioAction',
											type: 'POST',
											dataType: 'json',
											async : false,
											data: {
												cod_fiscale: assistito.cod_fiscale
											},
											success: function (data) {
												$dfd.resolve(data);
												$('#GraduatoriaTableContainer').jtable('reload');
												$('#CandidatiTableContainer').jtable('reload');
											},
											error: function () {
												$dfd.reject();
											}
										});
				        		  });
				        		  $(this).dialog( "close" );
				        	  }
				          },{
			        	  text: "Annulla",
				        	  click: function() {
				        		  $(this).dialog( "close" );
				        	  }
				          }],
				        open: function(){
				        	$("#dialog").html("Confermi l'operazione di rimozione della candidatura degli assistiti selezionati?")
				        }
					});
				})}
			}
			]
        }, // END OF TOOLBAR
        fields: {
            cod_fiscale: {
                key: true,
                title: 'Codice Fiscale',
                width: '10%',
                list: true
            },
            nome: {
                title: 'Nome',
                width: '10%',
                list: true
            },
            cognome: {
                title: 'Cognome',
                width: '20%',
                list: true
            },
            sesso: {
            	title: 'Sesso',
            	options: { 	'M': 'Maschio',
    						'F': 'Femmina', 
    						'-': 'Altro' },
                list: true
            },
            stato_civile: {
                title: 'Stato Civile',
                width: '20%',
                options: 'Choose_StatiCivili',
                list: true
            },
            luogo_nascita: {
                title: 'Luogo di Nascita',
                width: '20%',
                list: false
            },
            data_nascita: {
            	title: 'Data di Nascita',
				type: 'date',
				displayFormat: 'dd/mm/yy',
                list: true
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
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            citta_residenza: {
                title: 'Città Residenza',
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            cap: {
                title: 'CAP',
                list: false,
                edit: true,
                create: true
            },
            provincia: {
                title: 'Provincia Residenza',
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
                list: true
            },
            data_candidatura: {
            	title: 'Candidato dal',
            	type: 'date',				
            	displayFormat: 'dd/mm/yy',
                list: true,
                edit: false,
                create: false
            },
            punteggio_idb: {
            	title: 'IdB',
            	inputTitle: 'Indice di bisogno',
            	width: '5%',
                list: true,
                display: function(data) {
                    if (data.record.punteggio_idb < 10) {
                		html='<span style="color: red;">'+  data.record.punteggio_idb + '</span>';
                	} else if (data.record.punteggio_idb >= 10 && data.record.punteggio_idb < 15) {
                		html='<span style="color: yellow;">' +  data.record.punteggio_idb + '</span>';
                	} else 
                   		html='<span style="color: green;">' +  data.record.punteggio_idb + '</span>';
                    return html;
				},
            },
            telefono: {
                title: 'Telefono',
                list: false,
                edit: true,
                create: true
            },
            email: {
				title : 'Email',
				width : '15%',
				edit : true,
				create: true,
				list: false
            },
            num_documento: {
                title: 'Numero Documento',
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
        }
    });    //Load all records when page is first shown
    $('#CandidatiTableContainer').jtable("load");
});