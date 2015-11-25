$(document).ready(function () {
    $('#EmporioTableContainer').jtable({
        title: "Anagrafica Iscritti all'Emporio",
        paging: true, // Enable paging
        pageSize: 15, // Set page size (default: 10)
        sorting: false, // Enable sorting
        defaultSorting : 'COD_FISCALE ASC', //Set default sorting
        selecting: true, // Enable selecting
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'anagraficaEmporioEmporioAction'
        }, 
        fields: {
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
                display: function(data) {
                	if (false) {
                		html = data.record.cod_fiscale;
                	} else {
	                	var page = "'ShowSchedaAssistito?codice_fiscale="+ data.record.cod_fiscale + "'";
						html = '<a href="javascript:showSchedaAssistito(' + page + ')'  
						+ '" target="_blank">' 
						+ data.record.cod_fiscale 
						+ '</a>';
                	}
					return html;
				},
                inputTitle: 'Codice Fiscale' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required],funcCall[checkCF]',
                width: '10%',
                list: true,
                edit: false,
                create: true
            },
            nome: {
                title: 'Nome',
                inputTitle: 'Nome' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '10%',
                display: function(data) {
                	if (data.record.data_fine_assistenza!=null) {
                		return '<span style="color:red; font-style: italic;" >'+  data.record.nome + '</span>';
                	} else {
                		return data.record.nome;
                	}
                },
                list: true,
                edit: true,
                create: true
            },
            cognome: {
                title: 'Cognome',
                inputTitle: 'Cognome' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required]',
                width: '20%',
                display: function(data) {
                	if (data.record.data_fine_assistenza!=null) {
                		return '<span style="color:red; font-style: italic;" >'+  data.record.cognome + '</span>';
                	} else {
                		return data.record.cognome;
                	}
                },
                list: true,
                edit: true,
                create: true
            },
            data_accettazione: {
            	title: 'Inserito il',
            	type: 'date',
				displayFormat: 'dd/mm/yy',
                list: true 
            },
            data_scadenza: {
            	title: 'Fino al',
            	type: 'date',
				displayFormat: 'dd/mm/yy',
                list: true
            },
            punteggio_idb: {
            	title: 'IdB',
            	inputTitle: 'Indice di bisogno',
            	width: '5%',
                list: true
            },
            telefono: {
                title: 'Telefono',
                inputTitle: 'Telefono' + ' <span style="color:red">*</span>',
                inputClass: 'validate[required,custom[phone]]',
                list: false
            },
            email: {
				inputTitle: 'Indirizzo Email ' + '<span style="color:red">*</span>',
				title : 'Email',
				inputClass: 'validate[required,custom[email]]',
				width : '15%',
				edit : true,
				create: true,
				list: false
            }
        }
    });
    //Re-load records when user click 'load records' button.
    $('#LoadRecordsButton').click(function (e) {
        e.preventDefault();
        $('#EmporioTableContainer').jtable('load', {
            cf_search: $('#cf_search').val(),
            cognome_search: $('#cognome_search').val()
        });
    });

    $('#ResetButton').click(function (e) {
        e.preventDefault();
        $('#codice_fiscale').val(null);
        $('#cognome_search').val(null);
        $('#EmporioTableContainer').jtable('load', {
            cf_search: $('#cf_search').val(),
            cognome_search: $('#cognome_search').val()
        });
    });

    //Load all records when page is first shown
    $('#LoadRecordsButton').click();
});