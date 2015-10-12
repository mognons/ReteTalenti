$(document).ready(function() {
	// CUT HERE
	var courseRecord;
	function generateCalendar() {
		var courseId = $("#courseId").val(); 
		var courseName = $("#courseName").val(); 
		var calendarColor = $("#calendarColor").val();
		var startDate = $("#startDate").val();
		var selected = [];
		$("#days :checked").each(function() {
			selected.push($(this).val());
		});
		$.Deferred(function ($dfd) {
			$.ajax({
				traditional: true, // THIS IS MANDATORY FOR ARRAYS
				url: 'CalendarEvents_generateCalendar',
				type: 'POST',
				dataType: 'json',
				data: {
					courseId: courseId,
					courseName: courseName,
					calendarColor: calendarColor,
					startDate: startDate,
					selectedDays: selected
				},
				success: function (data) {
					$dfd.resolve(data);
					if (data.message) {
						console.log(data.message);
						$('#errorMessage').html("<h3>"+data.message+"</h3	>");
					} else {
						dialog.dialog("close");	
					};
				},
				error: function () {
					alert("si è rotto");
					$dfd.reject();
				}
			});
		});
	};
	var dialog = $("#dialog-form").dialog({
		title: "Generate new calendar: previous events will be deleted",
		autoOpen : false,
		height : 450,
		width : 400,
		modal : true,
		buttons : {
			"Generate Events" : generateCalendar,
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
			// allFields.removeClass("ui-state-error");
		}
	});
	var form = dialog.find( "form" ).on( "submit", function( event ) {
		event.preventDefault();
		addUser();
	});
	// CUT HERE
	$('#CoursesTableContainer').jtable({
		title : 'Courses List',
		paging : true, // Enable paging
		pageSize : 10, // Set page size (default: 10)
		sorting : false, // Enable sorting
		selecting: true, // Enable selecting
		multiselect: false, // Allow multiple selecting
		selectingCheckboxes: true, // Show checkboxes on first column
		selectOnRowClick: false, // Enable this to only select using
		// checkboxes
		pageSizeChangeArea: false,
		openChildAsAccordion: true,
		actions : {
			listAction : 'listCoursesAction',
			createAction : 'createCoursesAction',
			updateAction : 'updateCoursesAction',
			deleteAction : 'deleteCoursesAction'
		},
		toolbar: {
			items: [{
				text: 'Clone Course',
				icon: 'icons/Copy.png',
				click: function () {
					return $.Deferred(function ($dfd) {
						var $selectedRows = $('#CoursesTableContainer').jtable('selectedRows');
						$selectedRows.each(function () {
							var record = $(this).data('record');
							record.startDate = null;
							$.ajax({
								url: 'CloneCourse',
								type: 'POST',
								dataType: 'json',
								data: record,
								success: function (data) {
									$dfd.resolve(data);
									$("#dialog").dialog({
										modal: true,
										buttons: [
										          {
										        	  text: "Dismiss",
										        	  click: function() {
										        		  $( this ).dialog( "close" );
										        	  }
										          }
										          ],
										          open: function(){
										        	  $("#dialog").html("Course succesfully cloned!")
										          }
									});
									$('#CoursesTableContainer').jtable('reload');
								},
								error: function () {
									$dfd.reject();
								}
							});
						}
						);
					})}
			},
			{
				text: 'Generate Calendar',
				icon: 'icons/Calendar.png',
				click: function () {
					var $selectedRows = $('#CoursesTableContainer').jtable('selectedRows');
					$selectedRows.each(function () {
						courseRecord = $(this).data('record');
						var name = courseRecord.name;
						var startDate = courseRecord.startDate;
						if (!courseRecord.courseActive) {
							$("#dialog").dialog({
								modal: true,
								buttons: [
								          {
								        	  text: "Dismiss",
								        	  click: function() {
								        		  $( this ).dialog( "close" );
								        	  }
								          }
								          ],
								          open: function(){
								        	  $("#dialog").html("Selected course is <b>NOT</b> active." +
								        	  "<br>Calendar generation must be performed only on active courses")
								          }
							});
						} else {
							if (startDate.substr(0,5)=="/Date") {
								tempDate = startDate.substr(6,13)
								myDate = new Date(parseInt(tempDate));
								startDate = myDate.toISOString();
							}
							$('#dialog-form').find('input[name="startDate"]').val(startDate.substr(0,10));
							$('#dialog-form').find('input[name="courseName"]').val(courseRecord.name);
							$('#dialog-form').find('input[name="name"]').val(name);
							$('#dialog-form').find('input[name="courseId"]').val(courseRecord.courseId);
							dialog.dialog( "open" );
						}
					}); // End of EACH
				}
			}
			]},
			fields : {
				courseId : {
					key : true,
					list : false,
				},
				// CHILD TABLE DEFINITION FOR "STUDENTS"
				students: {
					title: 'Students',
					width: '5%',
					sorting: false,
					edit: false,
					create: false,
					list: true,
					display: function (courseData) {
						if (!courseData.record.courseActive) {return '<center><b>-</b></center>';}
						// Create an image that will be used to open child table
						var $img = $('<img src="icons/People.png" title="Edit students list" />');
						var _create =  (jQuery.inArray('Tutors', userGroups)>-1) ? 'createClassesAction?courseId=' + courseData.record.courseId : '';
						var _update =  (jQuery.inArray('Tutors', userGroups)>-1) ? 'updateClassesAction?courseId=' + courseData.record.courseId : '';
						var _delete =  (jQuery.inArray('Tutors', userGroups)>-1) ? 'deleteClassesAction?courseId=' + courseData.record.courseId : '';
						// Open child table when user clicks the image
						$img.click(function () {
							$('#CoursesTableContainer').jtable('openChildTable',
									$img.closest('tr'),
									{
								title: 'Students membership for <b>' + courseData.record.name + '</b>',
								actions: {
									listAction: 'listClassesAction?courseId=' + courseData.record.courseId,
									updateAction: _update,
									deleteAction: _delete,
									createAction: _create
								},
								fields: {
									studentId: {
										title: 'Student',
										width: '80&',
										key: true,
										create: true,
										edit: false,
										list: true,
										options: 'Choose_Students'
									},
									joinDate: {
										title: "Join date",
										width: '10%',
										type: 'date',
										displayFormat: 'dd/mm/yy',
										key: false,
										create: true,
										edit: true,
										list: true
									},
									userActive: {
										title: "Status",
										width: '10%',
										type: 'checkbox',
										values:  {false : 'Inactive' ,true : 'Active'},
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
				modules: {
					title: 'Modules',
					width: '5%',
					sorting: false,
					edit: false,
					create: false,
					list: true,
					display: function (courseData) {
						if (!courseData.record.courseActive) {return '<center><b>-</b></center>';}
						// Create an image that will be used to open child table
						var $img = $('<img src="icons/Database.png" align="center" title="Edit lessons modules" />');
						// Open child table when user clicks the image
						$img.click(function () {
							$('#CoursesTableContainer').jtable('openChildTable',$img.closest('tr'),
									{
								title: 'Modules for <b>' + courseData.record.name + '</b>',
						        sorting : false, //Enable sorting
						        defaultSorting : 'sequence ASC', //Set default sorting
								selecting: true,
								multiselect: false, 
								selectingCheckboxes: true, 
								selectOnRowClick: false,
								toolbar: {
									items: [
									{
//										icon: 'css/metro/column-asc.png',
										icon: 'icons/Up.png',
										text: 'UP',
										click: function () {
											return $.Deferred(function ($dfd) {
												var $selectedRows = $('#CoursesTableContainer>.jtable-main-container>.jtable>tbody>.jtable-child-row .jtable-row-selected');
												$selectedRows.each(function () {
													var record = $(this).data('record');
													console.log(record);
													$.ajax({
														url: 'relocateModulesAction',
														type: 'POST',
														dataType: 'json',
														data: {
															record: record,
															direction: 'UP',
															courseId: record.courseId,
															moduleId: record.moduleId,
															seq: record.seq
														},
														success: function (data) {
															console.log("Ajax success...");
															$dfd.resolve(data);
															$('.jtable-child-table-container').jtable('reload');
														},
														error: function () {
															$dfd.reject();
														}
													});
												}
												);
											})}
									},
									{                                        	
//										icon: 'css/metro/column-desc.png',
										icon: 'icons/Down.png',
										text: 'DOWN',
										click: function () {
											return $.Deferred(function ($dfd) {
												var $selectedRows = $('#CoursesTableContainer>.jtable-main-container>.jtable>tbody>.jtable-child-row .jtable-row-selected');
												//var $selectedRows = $('.jtable-child-table-container').childTable.jtable('selectedRows');
												$selectedRows.each(function () {
													var record = $(this).data('record');
													$.ajax({
														url: 'relocateModulesAction',
														type: 'POST',
														dataType: 'json',
														data: {
															record: record,
															courseId: record.courseId,
															moduleId: record.moduleId,
															direction: 'DOWN',
															seq: record.seq
														},
														success: function (data) {
															$dfd.resolve(data);
															$('.jtable-child-table-container').jtable('reload');
														},
														error: function () {
															$dfd.reject();
														}
													});
												}
												);
											})}
									}
									]},
									actions: {
										listAction: 'listModulesAction?courseId=' + courseData.record.courseId,
										updateAction: 'updateModulesAction?courseId=' + courseData.record.courseId,
										deleteAction: 'deleteModulesAction?courseId=' + courseData.record.courseId,
										createAction: 'createModulesAction?courseId=' + courseData.record.courseId
									},
									fields: {
										documents: {
											title: '',
											width: '5%',
											sorting: false,
											edit: false,
											create: false,
											list: true,
											display: function (moduleData) {
												if (!moduleData.record.moduleActive) {return '<center><b>-</b></center>';}
												// Create an image that will be
												// used to open child table
												var $img = $('<img src="icons/Notes.png" title="Documents list" />');
												// Open child table when user
												// clicks the image
												$img.click(function () {
													$('#CoursesTableContainer').jtable('openChildTable',
															$img.closest('tr'),
															{
														title: 'Documents for <b>' + courseData.record.name + ' - ' + moduleData.record.name + '</b>',
														actions: {
															listAction: 'listDocumentsAction?courseId=' + 
															courseData.record.courseId 	+
															'&moduleId=' + moduleData.record.moduleId,
															updateAction: 'updateDocumentsAction?courseId=' + 
															courseData.record.courseId 	+
															'&moduleId=' + moduleData.record.moduleId,
															deleteAction: 'deleteDocumentsAction?courseId=' + 
															courseData.record.courseId 	+
															'&moduleId=' + moduleData.record.moduleId,
															createAction: 'createDocumentsAction?courseId=' + 
															courseData.record.courseId 	+
															'&moduleId=' + moduleData.record.moduleId
														},
														formSubmitting: function(event, data) {
															filename = $('input[type=file]').val().split('\\').pop();
															if (filename!="")
																($("#" + data.form.attr("id")).find('input[name="documentPath"]').val(filename));
														},
														fields: {
															documentId: {
																key: true,
																list: false,
															},
															name: {
																title: "Description",
																width: '70%',
																create: true,
																edit: true,
																list: true
															},
															documentPath: {
																type: 'hidden'
															},
															oldDocument: {
																type: 'hidden',
																edit: true,
																create: true,
																input: function(data) {
																	// console.log(data.record);
																	if (data.record) {
																		myFilename = data.record.documentPath;
																		html = '<INPUT id="oldDocument" value="'+
																		data.record.documentPath +'"/';
																		return html;
																	}
																}
															},
															document: {
																title: 'Document',
																width: '30%',
																list: true,
																create: true,
																edit: true,
																display: function(data) {
																	myFilename = data.record.documentPath;
																	html = '<a href="'+ data.record.documentPath +' " target="_blank"">' + 
																	myFilename.split("/").pop() + 
																	'</a>';
																	return html;
																},
																input: function(data) {
																	html = '<form target="iframeTarget" class="formUploadFile" ' +
																	'action="uploadFile?courseId=' + courseData.record.courseId +
																	'&moduleId=' + moduleData.record.moduleId
																	+ '"'+ 
																	'method="post" ' + 
																	'enctype="multipart/form-data"> ' + 
																	'<input type="file" onchange="this.form.submit()" ' + 
																	'name="upload"/> </form> ' + 
																	'<iframe class="upload-iframe" style="display: none;" src="#" name="iframeTarget"></iframe>';
																	return html;
																}
															},
															documentName: {
																type: 'hidden',
																input: function(data) {
																	html = '<input type ="hidden" id="documentPath" name="documentPath" />';
																	return html;
																}
															}
														}
															}, function (data) { 
																data.childTable.jtable('load');
															});
												});
												// Return image to show on the
												// person row
												return $img;
											}
										}, // End of Documents
										moduleId: {
											width: '00&',
											key: true,
											list: false,
										},
										seq: {
											type: 'hidden'
										},
										name: {
											title: "Name",
											width: '30%',
											create: true,
											edit: true,
											list: true
										},
										details: {
											title: "Details",
											width: '60%',
											key: false,
											create: true,
											edit: true,
											list: true
										},
										duration: {
											title: 'Duration',
											width: '5%',
											key: false,
											create: true,
											list: true,
											edit: true
										},
										moduleActive: {
											type: 'hidden',
											defaultValue: 'true'
										}
									}
									}, function (data) { // opened handler
										data.childTable.jtable('load');
									});
						});
						// Return image to show on the person row
						return $img;
					}
				}, // End of Modules
				name: {
					title: 'Name',
					wifth: '30%',
					key: false,
					create: true,
					edit: true,
					list: true
				},
				type: {
					title: 'Type',
					width: '10%',
					options: {'O': 'Overview', 'B': 'Basic', 'A': 'Advanced','E': 'Expert'}, 
					key: false,
					create: true,
					edit: true,
					list: true
				},
				details: {
					title: "Details",
					width: '44%',
					key: false,
					create: true,
					edit: true,
					list: true
				},
				startDate: {
					title: "Start",
					width: '15%',
					type: 'date',
					displayFormat: 'dd/mm/yy',
					key: false,
					create: true,
					edit: true,
					list: true
				},
				duration: {
					title:"Days",
					width: '1%',
					key: false,
					create: true,
					edit: true,
					list: true
				},
				courseActive: {
					title: 'Status',
					type: 'checkbox',
					defaultValue: true,
					values:  {false : 'Inactive' ,true : 'Active'},
					key: false,
					create: false,
					edit: true,
					list: false
				}
			}
	});
	$('#CoursesTableContainer').jtable('load');
});