$(document).ready(function() {
	$('#StudentTableContainer').jtable({
		title : 'Students List',
		paging : true, //Enable paging
        pageSize : 15, //Set page size (default: 10)           
        sorting : true, //Enable sorting
        defaultSorting : 'name ASC', //Set default sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: true, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
		actions : {
			listAction : 'listStudentAction',
            createAction : 'createStudentAction',
            updateAction : 'updateStudentAction',
            deleteAction : 'deleteStudentAction'
		},

		fields : {
			studentId : {
				key : true,
				list : false,
			},
			name : {
				title : 'Name',
				width : '50%',
				edit : true
			},
			department : {
				title : 'Annotations',
				width : '30%',
				edit : true
			},
			emailId : {
				title : 'Email',
				width : '20%',
				edit : true
			}
		}
	});
	$('#StudentTableContainer').jtable('load');
});