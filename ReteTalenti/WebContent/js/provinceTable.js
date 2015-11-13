$(document).ready(function() {
	$('#ProvinceTableContainer').jtable({
		title : 'Elenco Province ReteTalenti',
		paging : false, //Enable paging
        pageSize : 10, //Set page size (default: 10)           
        sorting : false, //Enable sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: false, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,  
		actions : {
			listAction : 'listProvinceAction',
		},
		fields : {
			cod_provincia : {
				key: true,
				title : 'Codice',
				width : '5%',
				list : true
			},
			denominazione : {
				title : 'Denominazione',
				width : '75%',
				list: true
			},
			sigla_autom : {
				title : 'Sigla',
				width : '20%'
			}
		}
	});
	$('#ProvinceTableContainer').jtable('load');
});