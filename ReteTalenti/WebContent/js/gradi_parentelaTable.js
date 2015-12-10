$(document).ready(function () {
    $('#Gradi_parentelaContainer').jtable({
        title: 'Gradi di parentela',
        paging: true, //Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, //Enable sorting
        selecting: true, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: true, //Show checkboxes on first column
        selectOnRowClick: false, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listGradi_parentelaAction',
            createAction: 'createGradi_parentelaAction',
            updateAction: 'updateGradi_parentelaAction',
            deleteAction: 'deleteGradi_parentelaAction'
        },
        fields: {
            id: {
                key: true,
                list: false
            },
            descrizione: {
                title: 'Descrizione',
                width: '15%',
                inputClass: 'validate[required]',
                list: true,
                edit: true,
                create: true
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
        $('#Gradi_parentelaContainer').jtable('load');
    });