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
				edit : true
			},
			userFirstname : {
				title : 'First name',
				width : '30%',
				edit : true
			},
			userLastname : {
				title : 'Last name',
				width : '30%',
				edit : true
			},
			userEmail : {
				title : 'Email',
				width : '20%',
				edit : true
			}
		},
		formCreated: function(event, data) {
			// Whole form CSS styling, mainly dimensions
			data.form.css('width','800px').css('height','300px'); 
			// Single field CSS styling... 
			data.form.find('input[name=userEmail]').css('width','180px').css('color','blue');
			// Fields slicing
			$(".jtable-input-field-container").slice(0,1).wrapAll("<div class='row'/>"); 
			$(".jtable-input-field-container").slice(1,4).wrapAll("<div class='row'/>");
		}
	});
	$('#UsersTableContainer').jtable('load');
});