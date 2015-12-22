$(document).ready(function () {
	var today = new Date();
	var tomorrow = new Date((new Date()).valueOf() + 1000*3600*24);
	
	function ISOtoEuro(d) {
		d = d.substring(0,10);
		var dateParts = d.split("-");
		if(dateParts==d)
			dateParts = d.split("/");
		if(dateParts==d)
			dateParts = d.split(".");
		return (dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]);
	};
	function heute() {
		var today = new Date(); 
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (yyyy+"-"+mm+"-"+dd+"T00:00:00");
	};
	function morgen() {
		var today = new Date((new Date()).valueOf() + 1000*3600*24);
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (yyyy+"-"+mm+"-"+dd+"T00:00:00");
	};
    $('#ImpegniTableContainer').jtable({
        title: 'Lista ritiri prenotati',
        paging: true, // Enable paging
		pageSize: 15,
		pageSizes: [5,10,15],
        sorting: false, // Enable sorting
        selecting: false, // Enable selecting
        multiselect: false, // Allow multiple selecting
        selectingCheckboxes: true, // Show checkboxes on first column
        selectOnRowClick: false, // Enable this to only select using
									// checkboxes
        pageSizeChangeArea: false,
        openChildAsAccordion: true,
        actions: {
            listAction: 'listRitiriAction'
//            	,
//            updateAction: 'updateImpegniAction'
        },
        fields: {
            id: {
                key: true,
                list: false,
            },
            ente_cedente: {
                title: 'Ente Cedente',
                width: '15%',
                options: 'Choose_Enti',
                list: true,
                edit: false
            },
            prodotto: {
                title: 'Prodotto',
                width: '45%',
                list: true,
                edit: false
            },
            desc_udm: {
                title: 'UDM',
                list: true,
                edit: false
            },
            qta_prenotata: {
            	title: 'Quantità',
                list: true
            },
            data_ritiro: {
            	title: 'Ritiro previsto',
				type: 'date',
				displayFormat: 'dd/mm/yy',
				display: function(data) {
					console.log(heute(), morgen());
					console.log(data.record.data_ritiro);
					if (data.record.data_ritiro < heute()) {
						html = '<span style="color:red; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else if (data.record.data_ritiro == heute()) {
						html = '<span style="color:orange; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else if (data.record.data_ritiro == morgen()) {
						html = '<span style="color:black; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else {
						html = '<span style="color:green; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					}
					return html;	
				},
                list: true
            },
            ora_ritiro: {
            	title: 'Orario Ritiro',
                list: true
            }
        }
    });
    $('#ImpegniTableContainer').jtable('load');
});