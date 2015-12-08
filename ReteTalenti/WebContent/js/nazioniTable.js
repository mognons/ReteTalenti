$(document).ready(function() {
	$('#NazioniTableContainer').jtable({
		title: 'Elenco Nazioni',
		paging: true,
		pageSize: 20, //Set page size (default: 10) 
		pageSizes: [5,10,15,18],
        sorting: false, //Enable sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: false,
		actions : {
			listAction : 'listNazioniAction',
		},
		fields : {
			codice : {
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
			sigla : {
				title : 'Sigla',
				width : '20%'
			}
		}
	});
/*	$('table.jtable > thead:first').
		append('<tr><th><input type="text"></input></th>'+
				'<th><input type="text"></input></th>'+
				'<th><input type="text"></input></th></tr>');  */
	$('#NazioniTableContainer').jtable('load');
});