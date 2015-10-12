function jtableDocuments(corso, modulo) {
	$('#DocumentsTableContainer').jtable(
		{
			title : 'Courses List',
			paging : true, // Enable paging
			pageSize : 5, // Set page size (default: 10)
			sorting : false, // Enable sorting
			selecting : false, // Enable selecting
			multiselect : false, // Allow multiple selecting
			selectingCheckboxes : true, // Show checkboxes on first column
			selectOnRowClick : false, // Enable this to only select using
			// checkboxes
			pageSizeChangeArea : false,
			openChildAsAccordion : true,
			title : 'Documents for selected course/module',
			actions : {
				listAction : 'listDocumentsAction'
			},
			fields : {
				documentId : {
					key : true,
					list : false,
				},
				name : {
					title : "Description",
					width : '70%',
					create : true,
					edit : true,
					list : true
				},
				documentPath : {
					type : 'hidden'
				},
				document : {
					title : 'Document',
					width : '30%',
					list : true,
					create : true,
					edit : true,
					display : function(data) {
						myFilename = data.record.documentPath;
						html = '<a href="' + data.record.documentPath
								+ ' " target="_blank"">'
								+ myFilename.split("/").pop() + '</a>';
						return html;
					}
				}
			}
		});
		$('#DocumentsTableContainer').jtable('load', { courseId: corso, moduleId: modulo});	
}