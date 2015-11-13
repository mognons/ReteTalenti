$(document).ready(function() {
	$('#Uni_misuraTableContainer').jtable({
		title : 'Tabella Unità di Misura',
		paging : false, //Enable paging
        pageSize : 15, //Set page size (default: 10)           
        sorting : false, //Enable sorting
        defaultSorting : 'name ASC', //Set default sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: true, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
		actions : {
			listAction : 'listUni_misuraAction',
            createAction : 'createUni_misuraAction',
            updateAction : 'updateUni_misuraAction',
            deleteAction : 'deleteUni_misuraAction'
		},

		fields : {
			id : {
				key : true,
				list : false,
			},
			codice : {
				title : 'Codice',
				width : '20%',
				edit : true
			},
			descrizione : {
				title : 'Descrizione',
				width : '80%',
				edit : true
			}
		}
	});
	$('#Uni_misuraTableContainer').jtable('load');
});