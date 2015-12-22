$(document).ready(function () {
    $('#EmporioTableContainer').jtable({
        title: "Assistiti inseriti nell'Emporio",
        paging: true, // Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, // Enable sorting
        defaultSorting : 'COD_FISCALE ASC', //Set default sorting
        selecting: true, // Enable selecting
        multiselect: true, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: true, // Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'inseritiEmporioAction'
        },
		toolbar: {
			items: [
			{
				text: 'Rimuovi',
				icon: 'icons/Bad%20mark.png',
				tooltip: "Rimuove dall'emporio uno o più assistiti",
				click: function () {
					return $.Deferred(function ($dfd) {
					var $selectedRows = $('#EmporioTableContainer').jtable('selectedRows');
					$("#dialog").dialog({
						modal: true,
						buttons: [{
			        	  text: "Conferma",
				        	  click: function() {
				        		  $selectedRows.each(function () {
				        			  assistito = $(this).data('record');
				        			  console.log(assistito);
										$.ajax({
											url: 'EmporioOutEmporioAction',
											type: 'POST',
											dataType: 'json',
											async : false,
											data: {
												cod_fiscale: assistito.cod_fiscale,
												accettazione: ISOtoEuro(assistito.data_accettazione),
												scadenza: ISOtoEuro(assistito.data_scadenza)
											},
											success: function (data) {
												$dfd.resolve(data);
												$('#EmporioTableContainer').jtable('reload');
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
				        	$("#dialog").html("Confermi l'operazione di rimozione dall'emporio per gli assistiti selezionati?")
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
            emporio: {
            	title: 'Emporio',
            	options: 'Choose_Enti'
            },
            data_accettazione: {
            	title: 'Dal',
            	type: 'date',				
            	displayFormat: 'dd/mm/yy',
                list: true,
                edit: false,
                create: false
            },
            data_scadenza: {
            	title: 'Al',
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
            }
        } 
    });    //Load all records when page is first shown
    $('#EmporioTableContainer').jtable("load");
});