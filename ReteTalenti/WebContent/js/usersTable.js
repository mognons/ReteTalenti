$(document).ready(function() {
	$('#UsersTableContainer').jtable({
		title : 'Gestione Utenti ReteTalenti',
		paging : true, //Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting : false, //Enable sorting
        selecting: true, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: true, //Show checkboxes on first column
        selectOnRowClick: false, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
		actions : {
			listAction : 'listUserAction',
			createAction : 'createUserAction',
            updateAction : 'updateUserAction',
            deleteAction : 'deleteUserAction'
		},
        toolbar: {
            items: [{
                text: 'Reset Password',
                click: function () {
                	return $.Deferred(function ($dfd) {
                	var $selectedRows = $('#UsersTableContainer').jtable('selectedRows');
                	$selectedRows.each(function () {
                	    var record = $(this).data('record');
                	    var userId = record.id;
                	    var name = record.username;
 
                	    $.ajax({
                            url: 'ResetPassword',
                            type: 'POST',
                            dataType: 'json',
                            data: record,
                            success: function (data) {
                                $dfd.resolve(data);
                                console.log(data);
                                $("#dialog").dialog({
                                	  modal: true,
                                	  buttons: [
                                	    {
                                	      text: "Chiudi",
                                	      click: function() {
                                	        $( this ).dialog( "close" );
                                	      }
                                	    }
                                	  ],
                                	  open: function(){
                                		  $("#dialog").html(data.message)
                                	  }
                                	});
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                		}
                	);
                })}
            }]
        },
		fields : {
			username : {
				key: true,
				title : 'Username',
				inputTitle : 'Username ' + '<span style="color:red">*</span>',
				width : '5%',
				inputClass: 'validate[required]',
				edit : false,
				create: true
			},
			password : {
				title : 'Password',
				type: 'password',
				inputTitle : 'Password ' + '<span style="color:red">*</span>',
				inputClass: 'validate[required]',
				list: false,
				edit : false,
				create: true
			},
			userFirstname : {
				title : 'Nome',
				inputTitle : 'Nome ' + '<span style="color:red">*</span>',
				inputClass: 'validate[required]',
				width : '15%',
				edit : true
			},
			userLastname : {
				title : 'Cognome',
				inputTitle : 'Cognome ' + '<span style="color:red">*</span>',
				inputClass: 'validate[required]',
				width : '15%',
				edit : true
			},
			userEmail : {
				inputTitle: 'Indirizzo Email ' + '<span style="color:red">*</span>',
				title : 'Email',
				inputClass: 'validate[required,custom[email]]',
				width : '15%',
				edit : true
			},
			userPhone : {
				title : 'Telefono',
				inputTitle : 'Numero di Telefono ' + '<span style="color:red">*</span>',
				inputClass: 'validate[required,custom[phone]]',
				width : '7%',
				edit : true
			},
			ente : {
				title : 'Ente',
				inputTitle : 'Scegliere un Ente ' + '<span style="color:red">*</span>',
				defaultValue: enteUtente,
				width : '20%',
				edit : true,
				options: 'Choose_Enti',
			},
			groupId: {
            	title: 'Gruppo Utenti',
				width : '23%',
                create: true,
                edit: true,
                list: true,
                options: 'Choose_Gruppi'
            },
		},
        //Initialize validation logic when a form is created
        formCreated: function (event, data) {
        	data.form.find('input[name=userFirstname]').css('width','200px');
        	data.form.find('input[name=userLastname]').css('width','200px');
        	data.form.find('input[name=userEmail]').css('width','200px');
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
	$('#UsersTableContainer').jtable('load');
});