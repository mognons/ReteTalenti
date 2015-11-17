$(document).ready(function () {
    $('#AssistitiTableContainer').jtable({
        title: 'Gestione Anagrafica Assistiti',
        paging: true, // Enable paging
        pageSize: 15, // Set page size (default: 10)
        sorting: false, // Enable sorting
        selecting: true, // Enable selecting
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using
									// checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listAssistitiAction',
            createAction: 'createAssistitiAction',
            updateAction: 'updateAssistitiAction',
            deleteAction: 'deleteAssistitAction'
        },
        fields: {
            // CHILD TABLE DEFINITION FOR "NUCLEO FAMILIARE"
            nucleo_familiare: {
                title: '',
                width: '1%',
                sorting: false,
                edit: false,
                create: false,
                display: function (userData) {
                    // Create an image that will be used to open child table
                    var $img = $('<span align="CENTER"><img src="icons/People.png" width="16" height="16" title="Nucleo familiare"/></span>');
                    // Open child table when user clicks the image
                    $img.click(function () {
                        $('#AssistitiTableContainer').jtable('openChildTable',$img.closest('tr'),
                        {
                        	title: 'Nucleo familiare di ' + userData.record.nome + ' ' + userData.record.cognome,
                            paging: true, // Enable paging
                            pageSize: 5, // Set page size (default: 10)
                            pageSizeChangeArea: false,
					        defaultSorting : 'CODICE_FISCALE ASC', //Set default sorting
							selecting: true,
							multiselect: false, 
							selectingCheckboxes: true, 
							selectOnRowClick: true,
                            actions: {
                                listAction: 'listNucleiFamiliariAction?cf_assistito_nf=' + userData.record.codice_fiscale,
                                createAction: 'createNucleiFamiliariAction?cf_assistito_nf=' + userData.record.codice_fiscale,
                                updateAction: 'updateNucleiFamiliariAction?cf_assistito_nf=' + userData.record.codice_fiscale,
                                deleteAction: 'deleteNucleiFamiliariAction?cf_assistito_nf=' + userData.record.codice_fiscale
                            },                                    
                            fields: {
                                codice_fiscale: {
                                	title: 'Codice Fiscale',
                                	key: true,
                                    inputTitle: 'Codice Fiscale' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required]',
                                    width: '10%',
                                    list: true
                                },
                                nome: {
                                    key: true,
                                    title: 'Nome',
                                    inputTitle: 'Nome' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required]',
                                    width: '10%',
                                    list: true
                                },
                                cognome: {
                                    key: true,
                                    title: 'Cognome',
                                    inputTitle: 'Cognome' + ' <span style="color:red">*</span>',
                                    inputClass: 'validate[required]',
                                    width: '20%',
                                    list: true
                                },
                                data_nascita: {
                                	title: 'Data Nascita',
                					type: 'date',
                					displayFormat: 'dd/mm/yy',
                                    list: true,
                                    edit: true,
                                    create: true
                                },
                                tipo_parentela: {
                                	title: 'Legame',
                                	options: { 	'C': 'Coniuge',
                                				'F': 'Figlio/a', 
                                				'N': 'Convinvente' },
                                    list: true
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
            ente_assistente: {
            	title: 'Ente Assistente',
            	options: 'Choose_Enti',
            	list: true,
            	edit: false,
            	create: false
            },
            cod_fiscale: {
                key: true,
                title: 'Codice Fiscale',
                inputTitle: 'Codice Fiscale' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '10%',
                list: true,
                edit: true,
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
            sesso: {
            	title: 'Sesso',
            	options: { 	'M': 'Maschio',
    						'F': 'Femmina', 
    						'-': 'Altro' },
                list: false,
                edit: true,
                create: true
            },
            stato_civile: {
                title: 'Stato Civile',
                inputTitle: 'Stato Civile' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            luogo_nascita: {
                title: 'Luogo di Nascita',
                inputTitle: 'Luogo di Nascita' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            data_nascita: {
            	title: 'Data di Nascita',
                inputTitle: 'Data di Nascita' + ' <span style="color:red">*</span>',
				type: 'date',
				displayFormat: 'dd/mm/yy',
                inputClass: 'validate[required]',
                list: true,
                edit: true,
                create: true
            },
            nazionalità: {
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
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            citta_residenza: {
                title: 'Città Residenza',
                inputTitle: 'Città di Residenza' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
                list: false,
                edit: true,
                create: true
            },
            cap: {
                title: 'CAP',
                inputTitle: 'CAP' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
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
            	title: 'Permesso Soggiorno',
				type: 'checkbox',
				defaultValue: ' ',
				values:  {' ' : 'No' ,'x' : 'Sì'},
                list: true,
                edit: true
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
            numero_documento: {
                title: 'Numero Documento',
                inputTitle: 'Numero Documento' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required,custom[phone]]',
                list: false,
                edit: true,
                create: true
            }
        },
        rowInserted: function(event, data){
        	if (recordObfuscation(data.record.ente_cedente)) {
              data.row.find('.jtable-edit-command-button').hide();
              data.row.find('.jtable-delete-command-button').hide();
            }
        },
        // Initialize validation logic when a form is created
        formCreated: function (event, data) {
            $(".jtable-input-field-container").slice(0,1).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(1,2).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(2,3).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(3,4).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(4,5).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(5,6).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(6,7).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(7,8).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(8,9).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(9,10).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(10,11).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(11,12).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(12,13).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(13,14).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(14,15).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(15,16).wrapAll('<div class="col2"/>');
            $(".jtable-input-field-container").slice(16,17).wrapAll('<div class="col1"/>');
            $(".jtable-input-field-container").slice(17,18).wrapAll('<div class="col2"/>');
            data.form.find('input[name=nome]').css('width', '300px');
            //data.form.find('input[name=dummy]').css('width', '0px');
            data.form.find('input[name=cognome]').css('width', '300px');
            data.form.validationEngine();
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
    //Re-load records when user click 'load records' button.
    $('#LoadRecordsButton').click(function (e) {
        e.preventDefault();
        $('#AssistitiTableContainer').jtable('load', {
            codice_fiscale: $('#codice_fiscale').val(),
            cognome_search: $('#cognome_search').val()
        });
    });

    $('#ResetButton').click(function (e) {
        e.preventDefault();
        $('#codice_fiscale').val('');
        $('#cognome_search').val('');
        $('#AssistitiTableContainer').jtable('load', {
            codice_fiscale: $('#codice_fiscale').val(),
            cognome_search: $('#cognome_search').val()
        });
    });

    //Load all records when page is first shown
    $('#LoadRecordsButton').click();
});