$(document).ready(function () {
    $('#EntiTableContainer').jtable({
        title: 'Gestione Enti',
        paging: true, //Enable paging
        pageSize: 10, //Set page size (default: 10)           
        sorting: false, //Enable sorting
        selecting: true, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: true, //Show checkboxes on first column
        selectOnRowClick: false, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listEntiAction',
            createAction: 'createEntiAction',
            updateAction: 'updateEntiAction',
            deleteAction: 'deleteEntiAction'
        },
        
        fields: {
            id: {
                key: true,
                list: false
            },
            //CHILD TABLE DEFINITION FOR "GROUPS"
            Groups: {
                title: '',
                width: '1%',
                sorting: false,
                edit: false,
                create: false,
                display: function (userData) {
                    //Create an image that will be used to open child table
                    var $img = $('<img align="center" src="icons/myspace.png" width="24" height="24" title="Utenti del gruppo" />');
                    //Open child table when user clicks the image
                    $img.click(function () {
                        $('#EntiTableContainer').jtable('openChildTable',
                                $img.closest('tr'),
                                {
                                    title: 'Utenti dell\'ente ' + userData.record.descrizione,
                                    actions: {
                                        listAction: 'UListUserAction?id=' + userData.record.id
                                    },
                                    fields: {
                                        username: {
                                            title: 'Username',
                                            key: true,
                                            create: true,
                                            edit: false,
                                            list: true
                                        },
                                        userFirstname: {
                                            title: 'Nome',
                                            key: false,
                                            create: false,
                                            edit: false,
                                            list: true
                                        },
                                        userLastname: {
                                            title: 'Cognome',
                                            key: false,
                                            create: false,
                                            edit: false,
                                            list: true
                                        }
                                    }
                                }, function (data) { //opened handler
                            data.childTable.jtable('load');
                        });
                    });
                    //Return image to show on the person row
                    return $img;
                }
            },
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
            },
            responsabile: {
                title: 'Responsabile',
                width: '25%',
                inputClass: 'validate[required]',
                list: true,
                edit: true,
                create: true
            },
            resp_email: {
                title: 'Email',
                inputClass: 'validate[required,custom[email]]',
                width: '20%',
                list: true,
                edit: true,
                create: true
            },
            resp_phone: {
                title: 'Telefono',
                inputClass: 'validate[required,custom[phone]]',
                width: '10%',
                list: true,
                edit: true,
                create: true
            },
            provincia_ente: {
                title: 'Provincia',
                width: '20%',
                options: 'Choose_Province',
                edit: true
            },
            ente_emporio: {
                title: 'Emporio',
                width: '10%',
				type: 'checkbox',
				values:  {false : 'No' ,true : 'Sì'},
				list: true,
				edit: true,
				create: true
            }
        },
        //Initialize validation logic when a form is created
        formCreated: function (event, data) {
            data.form.find('input[name=descrizione]').css('width', '200px');
            data.form.find('input[name=responsabile]').css('width', '200px');
            data.form.find('input[name=resp_email]').css('width', '200px');
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
    $('#EntiTableContainer').jtable('load');
});