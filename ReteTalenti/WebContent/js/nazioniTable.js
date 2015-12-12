$(document).ready(function() {
	$('#NazioniTableContainer').jtable({
		title: 'Elenco Nazioni',
		paging: true,
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, //Enable sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: false, //Show checkboxes on first column
        selectOnRowClick: false,
		toolbarSearch:true,
		toolbarReset:false,
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
				list: true,
				searchable: true, // default is false, if set to true then a text input is created
//				sqlName: 'NOME_DELLA_COLONNA'
			},
			sigla : {
				title : 'Sigla',
				width : '20%',
				searchable: true
			}
		}
	});
/*	$('table.jtable > thead:first').
		append('<tr><th><input type="text"></input></th>'+
				'<th><input type="text"></input></th>'+
				'<th><input type="text"></input></th></tr>');  */
	$('#NazioniTableContainer').jtable('load');
});