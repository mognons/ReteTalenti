$(document).ready(function () {
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
		return (dd+"/"+mm+"/"+yyyy);
	};
	function tomorrow() {
		var today = new Date((new Date()).valueOf() + 1000*3600*24);
		var dd = today.getDate(); 
		var mm = today.getMonth()+1;//January is 0! 
		var yyyy = today.getFullYear(); 
		if(dd<10){dd='0'+dd}; 
		if(mm<10){mm='0'+mm};
		//return (yyyy+"/"+mm+"/"+dd);	
		return (dd+"/"+mm+"/"+yyyy);
	};
    $('#ImpegniTableContainer').jtable({
        title: 'Lista ritiri prenotati',
        paging: true, // Enable paging
        pageSize: 15, // Set page size (default: 10)
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
            },
            prodotto: {
                title: 'Prodotto',
                width: '45%',
                list: true
            },
            desc_udm: {
                title: 'UDM',
                list: true,
            },
            qta_prenotata: {
            	title: 'Quantità',
                list: true,
            },
            data_ritiro: {
            	title: 'Ritiro previsto',
				type: 'date',
				displayFormat: 'dd/mm/yy',
				display: function(data) {
					console.log(heute());
					console.log(data.record.data_ritiro);
					if (ISOtoEuro(data.record.data_ritiro) < heute()) {
						html = '<span style="color:red; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else if (ISOtoEuro(data.record.data_ritiro) == heute()) {
						html = '<span style="color:orange; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else if (ISOtoEuro(data.record.data_ritiro) == tomorrow()) {
						html = '<span style="color:black; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					} else {
						html = '<span style="color:green; font-weight: bold;">' + ISOtoEuro(data.record.data_ritiro)+ '</span>';
					}
					return html;	
				},
                list: true,
            },
            ora_ritiro: {
            	title: 'Orario Ritiro',
                list: true
            }
        }
    });
    $('#ImpegniTableContainer').jtable('load');
});