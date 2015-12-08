$(document).ready(function () {
    $('#Uni_misuraTableContainer').jtable({
        title: 'Tabella Unità di Misura',
        paging: false, //Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, //Enable sorting
        defaultSorting: 'name ASC', //Set default sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: true, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        actions: {
            listAction: 'listUni_misuraAction',
            createAction: 'createUni_misuraAction',
            updateAction: 'updateUni_misuraAction',
            deleteAction: 'deleteUni_misuraAction'
        },
        fields: {
            id: {
                key: true,
                list: false,
            },
            codice: {
                title: 'Codice',
                width: '20%',
                inputClass: 'validate[required]',
                edit: true
            },
            descrizione: {
                title: 'Descrizione',
                inputClass: 'validate[required]',
                width: '80%',
                edit: true
            }
        },
        //Initialize validation logic when a form is created
        formCreated: function (event, data) {
            data.form.find('input[name=descrizione]').css('width', '200px');
            data.form.validationEngine('attach',{promptPosition : "bottomLeft", scroll: false});
        },
        //Validate form when it is being submitted
        formSubmitting: function (event, data) {
            return data.form.validationEngine('validate');
        },
        //Dispose validation logic when form is closed
        formClosed: function (event, data) {
            data.form.validationEngine('hide');
            data.form.validationEngine('detach');
        }
    });
    $('#Uni_misuraTableContainer').jtable('load');
});