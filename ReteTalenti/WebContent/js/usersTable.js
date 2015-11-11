$(document).ready(function() {
	$('#UsersTableContainer').jtable({
		title : 'Users List',
		paging : true, //Enable paging
        pageSize : 10, //Set page size (default: 10)           
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
                                $("#dialog").dialog({
//                                	  dialogClass: "alert",
                                	  modal: true,
                                	  buttons: [
                                	    {
                                	      text: "Dismiss",
                                	      click: function() {
                                	        $( this ).dialog( "close" );
                                	      }
                                	    }
                                	  ],
                                	  open: function(){
                                		  $("#dialog").html("Resetting password for user <b>"+ name + "</b><br/>was successfull!")
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
			id : {
				key : true,
				list : false,
			},
            //CHILD TABLE DEFINITION FOR "GROUPS"
            Groups: {
                title: '',
                width: '5%',
                sorting: false,
                edit: false,
                create: false,
                display: function (userData) {
                    //Create an image that will be used to open child table
                    var $img = $('<img src="icons/myspace.png" width="24" height="24" title="Edit groups membership numbers" />');
                    //Open child table when user clicks the image
                    $img.click(function () {
                        $('#UsersTableContainer').jtable('openChildTable',
                                $img.closest('tr'),
                                {
                                    title: 'Groups membership for ' + userData.record.username,
                                    actions: {
                                        listAction: 'listGroups?id=' + userData.record.id,
                                        deleteAction: 'deleteGroups?id=' + userData.record.id,
                                        createAction: 'createGroups?id=' + userData.record.id
                                    },
                                    fields: {
                                        groupId: {
                                            key: true,
                                            create: true,
                                            edit: false,
                                            list: true,
                                            options: 'Choose_Gruppi'
//                                            type: 'hidden',
//                                            defaultValue: userData.record.id
                                        },
                                        groupName: {
                                            key: false,
                                            create: false,
                                            edit: false,
                                            list: false
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
			username : {
				title : 'Username',
				width : '20%',
				inputClass: 'validate[required]',
				edit : false,
				create: true
			},
			password : {
				title : 'Password',
				type : 'password',
				inputClass: 'validate[required]',
				list : false,
				edit : false,
				create : true
			},
			userFirstname : {
				title : 'Nome',
				inputClass: 'validate[required]',
				width : '20%',
				edit : true
			},
			userLastname : {
				title : 'Cognome',
				inputClass: 'validate[required]',
				width : '20%',
				edit : true
			},
			userEmail : {
				title : 'Email',
				inputClass: 'validate[required,custom[email]]',
				width : '20%',
				inputWIdth: '200',
				edit : true
			},
			userPhone : {
				title : 'Telefono',
				inputClass: 'validate[required]',
				width : '15%',
				edit : true
			},
			ente : {
				title : 'Ente',
				width : '20%',
				edit : true,
				options: 'Choose_Enti'
			}
		},
        //Initialize validation logic when a form is created
        formCreated: function (event, data) {
        	data.form.find('input[name=userEmail]').css('width','200px');
        	data.form.parent().css('width','400px');
        	// data.form.parent().css('height','600px');
        	//$(".jtable-input-field-container").slice(0,2).wrapAll("");
        	// Slice Parameters are Start Stop
        	//$(".jtable-input-field-container").slice(2,5).wrapAll("");
            data.form.validationEngine();
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