function jtableClass(corso, modulo, startDate) {
	$('#classContainer').jtable(
		{
			title : 'Students in the class',
			paging : false, // Enable paging
			pageSize : 15, // Set page size (default: 10)
			sorting : false, // Enable sorting
			defaultSorting : 'name ASC', // Set default sorting
			selecting : false, // Enable selecting
			multiselect : true, // Allow multiple selecting
			selectingCheckboxes : true, // Show checkboxes on first column
			selectOnRowClick : false, // Enable this to only select using // checkboxes
			pageSizeChangeArea : false,
			actions : {
				listAction : 'listclassRegisterAction',
				updateAction : 'updateclassRegisterAction'
			},

			fields : {
				studentId : {
					key : true,
					type : 'hidden'
				},
				// CHILD TABLE DEFINITION FOR "MARKS"
				results: {
					title: 'Marks',
					width: '5%',
					sorting: false,
					edit: false,
					create: false,
					list: true,
					display: function (registerData) {
						if (registerData.record.entryType == 'A') {return '<center><b>-</b></center>';}
						// Create an image that will be used to open child table
						var $img = $('<center><img src="icons/3d%20bar%20chart.png" title="Student\'s marks for today" /></center>');
						// Open child table when user clicks the image
						$img.click(function () {
							$('#classContainer').jtable('openChildTable',
									$img.closest('tr'),
									{
								title: 'Marks for student <i>' + registerData.record.name + '</i>',
								actions: {
									listAction: 'listMarksTableAction?courseId=' 
											+ registerData.record.courseId + '&moduleId=' + registerData.record.moduleId +
											'&studentId=' + registerData.record.studentId,
									updateAction: 'updateMarksTableAction?courseId=' 
											+ registerData.record.courseId + '&moduleId=' + registerData.record.moduleId + 
											'&studentId=' + registerData.record.studentId,
									createAction: 'createMarksTableAction?courseId='
											+ registerData.record.courseId + '&moduleId=' + registerData.record.moduleId + 
											'&studentId=' + registerData.record.studentId
								},
								fields: {
									markId: {
										type : 'hidden'
									},
									markType: {
										title: "Typology",
										width: '50%',
										options: ['Questions','Exercise','Ranking','Project task'],
										key: false,
										create: true,
										edit: true,
										list: true
									},
									markValue: {
										title: "Grade",
										width: '50%',
										options: {'1':'Very bad','2':'Below avg','3':'Average','4':'Better than avg','5':'Excellent'},
										key: false,
										create: true,
										edit: true,
										list: true
									}
								}
									}, function (data) { // opened handler
										data.childTable.jtable('load');
									});
						});
						// Return image to show on the person row
						return $img;
					}
				}, // End of Students
				courseId : {
					type : 'hidden'
				},
				moduleId : {
					type : 'hidden',
				},
				registerId: {
					type : 'hidden'
				},
				registerDate : {
					type : 'hidden'
				},
				name : {
					title : 'Name',
					width : '40%',
					edit : false
				},
				entryType: {
					title : 'Participating',
					options:  {'A' : 'Absent' , 'P' : 'Present', 'D':'Delayed In', 'E':'Early Out'},
					display: function(data) {
						if (data.record.entryType!='A')
							return '<center><img src="icons/Apply.png" title="Present"/></center>';
						else
							return '<center><img src="icons/Delete.png" title="Absent"/></center>';
					}
				},
				absenceReason : {
					title : 'Absence reason',
					width : '30%',
					edit : true
				}
			}
		});
		$('#classContainer').jtable('load', { courseId: corso, moduleId: modulo, registerDate: startDate});
}