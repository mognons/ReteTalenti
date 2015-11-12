$(document).ready(function() {
	$('#NazioniTableContainer').jtable({
		title : 'Elenco Nazioni',
		paging : true, //Enable paging
        pageSize : 15, //Set page size (default: 10)           
        sorting : false, //Enable sorting
        selecting: false, //Enable selecting
        multiselect: false, //Allow multiple selecting
        selectingCheckboxes: true, //Show checkboxes on first column
        selectOnRowClick: false, //Enable this to only select using checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
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
	$('#NazioniTableContainer').jtable('load');
});