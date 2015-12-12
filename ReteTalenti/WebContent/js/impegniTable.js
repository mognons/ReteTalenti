$(document).ready(function () {
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
    $('#ImpegniTableContainer').jtable({
        title: 'Gestione Prenotazione Eccedenze',
        paging: true, // Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, // Enable sorting
        selecting: false, // Enable selecting
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using checkboxes
		toolbarSearch:true,
		toolbarReset:true,
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listAvailableEccedenzeAction'
        },
        fields: {
            id: {
                key: true,
                list: false,
            },
            // CHILD TABLE DEFINITION FOR "IMPEGNI"
            impegni: {
                title: '',
                width: '1%',
                sorting: false,
                edit: false,
                create: false,
                display: function (eccedenzaData) {
                    var $img = $('<span align="CENTER"><img src="icons/Delivery.png" width="16" height="16" title="Ritiri prenotati"/></span>');
                    // Open child table when user clicks the image
                    $img.click(function () {
                        $('#ImpegniTableContainer').jtable('openChildTable',$img.closest('tr'),
                        {
                        	title: 'Prenotazioni per il ritiro di <i>' + eccedenzaData.record.prodotto+'</i>',
                            paging: true, // Enable paging
                            pageSize: 5, // Set page size (default: 10)
                            pageSizeChangeArea: false,
					        defaultSorting : 'DATA_RITIRO ASC', //Set default sorting
							selecting: false,
							multiselect: false, 
							selectingCheckboxes: true, 
							selectOnRowClick: true,
                            actions: {
                                listAction: 'listOwnByEccedenzaImpegniAction?id_eccedenza=' + eccedenzaData.record.id,
                                createAction: 'createImpegniAction?id_eccedenza=' + eccedenzaData.record.id,
                                updateAction: 'updateImpegniAction?id_eccedenza=' + eccedenzaData.record.id
                            },                                    
                            fields: {
                                id: {
                                	title: 'Identificativo',
                                	key: true,
                                    list: false
                                },
                                ente_richiedente: {
                                	title: 'Ente',
									options: 'Choose_Enti',
									type: 'hidden',
									edit: false,
									create: false
                                },
                                qta_prenotata: {
                                	title: 'Quantità',
                                	inputTitle: 'Quantità' + ' <span style="color:red">*</span>',
                                    list: true,
									edit: true,
									create: true,
									defaultValue: eccedenzaData.record.qta_residua,
                					inputClass: 'validate[required,min[1],max['
                								+eccedenzaData.record.qta_residua +']'
                                },
                                dummy_data: {
                                	list: false,
                                	create: true,
                                	edit: true,
                                	input: function (data) {
                                		return 	'<input type="hidden" id ="data_scadenza" name="data_scadenza" value="' 
                                				+ ISOtoEuro(eccedenzaData.record.scadenza) + '" />';
                                    }
                                },
                                data_ritiro: {
                                	title: 'Ritiro previsto',
                                	inputTitle: 'Ritiro previsto' + ' <span style="color:red">*</span>',
                					type: 'date',
                					displayFormat: 'dd/mm/yy',
                					inputClass: 'validate[required,future[now],past[data_scadenza]] datepicker',
									defaultValue: tomorrow(),
                                    list: true,
                                    edit: true
                                },
                                ora_ritiro: {
                                	title: 'Orario Ritiro',
                                	inputTitle: 'Orario Ritiro'  + ' <span style="color:red">*</span>',
                   					inputClass: 'validate[required,custom[timeh24]]',
                                    list: true
                                },
                                ritiro_effettuato: {
                                	title: 'Ritiro effettuato',
                					type: 'checkbox',
                					defaultValue: false,
                					values:  {false : 'No' ,true : 'Sì'},
                                    list: true,
                                    edit: false,
                                    create: false
                                }
                            },
                            recordsLoaded: function(event, data) {
                            	  var rowCount = data.records.length;
                            	  if (rowCount>=1){
                            	     $('#ImpegniTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
                            	  }
                            },
                            rowInserted: function(event, data){
                            	if (data.record.ritiro_effettuato) {
                                  data.row.find('.jtable-edit-command-button').hide();
                                  data.row.find('.jtable-delete-command-button').hide();
                                }
                            },
                            // Initialize validation logic when a form is created
                            formCreated: function (event, data) {
                                data.form.validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
                                data.form.css('width','200px');
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
                            closeRequested: function(event, data) {
                          	  	$('#ImpegniTableContainer').jtable('reload');                            	
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
            id: {
            	key: true,
            	list: false
            },
            ente_cedente: {
                title: 'Ente Cedente',
                width: '15%',
                options: 'Choose_Enti',
                width: '15%',
				searchable: true, // default is false, if set to true then a text input is created
				sqlOperator: '=',
                list: true,
                edit: false,
                create: false
            },
            prodotto: {
                title: 'Prodotto',
                width: '45%',
                inputTitle: 'Prodotto' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
				searchable: true, // default is false, if set to true then a text input is created
//				sqlName: 'NOME_DELLA_COLONNA'
               list: true
            },
            udm: {
                title: 'UDM',
                inputTitle: 'Unità di misura' + ' <span style="color:red">*</span>',
                options: 'Choose_UDM',
                width: '15%',
                list: true,
                edit: true,
                create: true
            },
            qta: {
                title: 'Quantità',
                width: '5%',
                list: true,
                edit: true,
                create: true
            },
            qta_residua: {
                title: 'Disponibile',
                width: '5%',
                list: true
            },
            scadenza: {
                title: 'Scadenza',
                type: 'date',
				displayFormat: 'dd/mm/yy',
                inputClass: 'validate[required]',
				searchable: true, // default is false, if set to true then a text input is created
				sqlOperator: '<=',
//				sqlName: 'NOME_DELLA_COLONNA'
                width: '10%'
            }
        },
        rowInserted: function(event, data){
        	if (data.record.qta != data.record.qta_residua) {
              data.row.find('.jtable-edit-command-button').hide();
              data.row.find('.jtable-delete-command-button').hide();
            }
        },
        // Initialize validation logic when a form is created
        formCreated: function (event, data) {
            data.form.find('input[name=prodotto]').css('width', '300px');
            // data.form.parent().css('width', '400px');
            // data.form.parent().css('height','200px');
            //$(".jtable-input-field-container").slice(1,2).wrapAll("");
            // Slice Parameters are Start Stop
            //$(".jtable-input-field-container").slice(2,5).wrapAll("");
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
    $('#ImpegniTableContainer').jtable('load');
});