$(document).ready(function () {
    $('#IndiceBisognoTableContainer').jtable({
        title: 'Tabella Indice di Bisogno',
        paging: true, // Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, // Enable sorting
        selecting: false, // Enable selecting
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listIndiceBisognoAction',
            createAction: 'createIndiceBisognoAction',
            updateAction: 'updateIndiceBisognoAction',
            deleteAction: 'deleteIndiceBisognoAction'
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            data_inserimento: {
                title: 'Data',
                list: true
            },
            cf_assistito_ib: {
                title: 'Codice Fiscale Assistito',
                width: '20%',
                inputClass: 'validate[required]',
                list: true,
                edit: true,
                create: true
            },
            isee_euro: {
                title: 'ISEE',
                inputClass: 'validate[required]',
                list: true,
                edit: true,
                create: true
            },
            cc_euro: {
                title: 'Contrib. Comune Spese docum.',
                inputClass: 'validate[required,custom[email]]',
                width: '30%',
                list: true,
                edit: true,
                create: true
            },
            ca_euro: {
                title: 'Contrib. Affitto Regione',
                inputClass: 'validate[required,custom[phone]]',
                width: '30%',
                list: true,
                edit: true,
                create: true
            },
            cs_euro: {
                title: 'Contrib. Spot Privato Sociale',
                width: '20%',
                options: 'Choose_Province',
                edit: true
            },
            stato_disoc: {
                title: 'Stato Disoc.',
                width: '5%',
                type: 'checkbox',
                defaultValue: false,
                edit: true
            },
            spese_imp: {
                title: 'Spese Impreviste',
                width: '5%',
                defaultValue: false,
                edit: true
            },
            urgenza: {
                title: 'Condizione Urgenza',
                width: '5%',
                defaultValue: false,
                edit: true
            },
            isee_punti: {
                title: 'P1',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            },
            entrate_nc_punti: {
                title: 'P2',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            },
            stato_disoc_punti: {
                title: 'P3',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            },
            spese_imp_punti: {
                title: 'P4',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            },
            urgenza_punti: {
                title: 'P5',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            },
            totalepunti: {
                title: 'TOT PUNTI',
                width: '5%',
                defaultValue: false,
                list: true,
                edit: false
            }


        },
        recordsLoaded: function (event, data) {
            if (addRecordObfuscation()) {
                $('#IndiceBisognoTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
            }
        },
        rowInserted: function (event, data) {
            if (recordObfuscation(data.record.cf_assistito_ib)) {
                data.row.find('.jtable-edit-command-button').hide();
                data.row.find('.jtable-delete-command-button').hide();
            }
        },
        // Initialize validation logic when a form is created
        formCreated: function (event, data) {
            data.form.validationEngine('attach', {promptPosition: "bottomLeft", scroll: false});
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
//    $('#LoadRecordsButton').click(function (e) {
//        e.preventDefault();
//        $('#IndiceBisognoTableContainer').jtable('load', {
//            cf_search: $('#cf_search').val(),
//            cognome_search: $('#cognome_search').val()
//        });
//    });

//    $('#ResetButton').click(function (e) {
//        e.preventDefault();
//        $('#codice_fiscale').val('');
//        $('#cognome_search').val('');
//        $('#IndiceBisognoTableContainer').jtable('load', {
//            cf_search: $('#codice_fiscale').val(),
//            cognome_search: $('#cognome_search').val()
//        });
//    });

    //Load all records when page is first shown
    $('#LoadRecordsButton').click();
});