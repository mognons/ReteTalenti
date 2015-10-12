<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="js/jquery.qtip.js"></script>
<script type="text/javascript" src='js/moment.min.js'></script>
<script type="text/javascript" src='js/fullcalendar.js'></script>
<script type="text/javascript" src='js/tinycolor.js'></script>
<script type="text/javascript" src='js/jquery.ui-contextmenu.js'></script>
<link type="text/css" rel="stylesheet" href="css/fullcalendar.css" />
<link type="text/css" rel="stylesheet" href="css/jquery.qtip.css" />
<style>
#wrap {
	width: 1100px;
	margin: 0 auto;
}

.myheader {
	background-color: #FBFCFC;
}

.course-selection {
	float: left;
	width: 99.8%;
	margin: 4px 0px 12px 0px;
	padding: 4px 0px;
	border: 2px solid #E3F1F9;
	background: #eee;
	text-align: left;
}

.external-events {
	float: left;
	width: 100%;
	margin: 6px 0px;
	border: 1px solid #AED0EA;
	background: #EEEEEE;
	background-color: #EEEEEE;
	text-align: left;
	box-shadow: 6px 6px 4px #888888;
}

.external-events table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	height: 60%;
	margin: 0px;
	padding: 0px;
	background-color: #F3F6F8;
}

.external-events tr:hover td {
	background-color: #FBFCFC;
}

.external-events .fc-event {
	margin: 2px 0;
	color: black;
	background-color: #E6F3F9;
	cursor: pointer;
}

.external-events td {
	vertical-align: middle;
	border: 1px solid #000000;
	border-width: 0px 1px 1px 0px;
	text-align: center;
	padding: 3px;
	font-size: 14px;
	font-family: Arial;
	font-weight: normal;
	color: #000000;
}

.external-events tr:last-child td {
	border-width: 0px 1px 0px 0px;
}

.external-events tr td:last-child {
	border-width: 0px 0px 1px 0px;
}

.external-events tr:last-child td:last-child {
	border-width: 0px 0px 0px 0px;
}

.external-events tr:first-child td {
	background-color: #E6F3F9;
	border: 0px solid #DDDDDD;
	text-align: center;
	border-width: 0px 0px 0px 0px;
	font-size: 14px;
	font-family: Arial;
	font-weight: bold;
	color: black;
}

.external-events tr:first-child td:first-child {
	border-width: 0px 0px 0px 0px;
}

.external-events tr:first-child td:last-child {
	border-width: 0px 0px 1px 0px;
}
</style>

<script type="text/javascript">
	function openDialog(startDate, course,module) {
		var popup = document.getElementById('modalDialogBox');
		popup.src = "classRegister.jsp?startDate="+startDate+"&courseId="+course+"&moduleId="+module;
	    (function () {
	        $('#pop-up').dialog({
	            modal: true,
	            resizable: false,
	            draggable: false,
	            width: '800',
	            height: '600',
	            title: 'Class'
	        });
	    })();
	};
	$(document).ready(function() {
		$('#calendar').contextmenu({
		    delegate: ".fc-event-h",
		    menu: [
		        {title: "Copy", cmd: "copy", uiIcon: "ui-icon-copy"},
		        {title: "----"},
		        {title: "More", children: [
		            {title: "Sub 1", cmd: "sub1"},
		            {title: "Sub 2", cmd: "sub1"}
		            ]}
		        ],
		    select: function(event, ui) {
		        alert("select " + ui.cmd + " on " + ui.target.text());
		    }
		});
		// Draggable events
		$('#external-events .fc-event').each(function() {

			// store data so the calendar knows to render an event upon drop
			$(this).data('event', {
				title: $.trim($(this).text()), // use the element's text as the event title
				stick: true, // maintain when user navigates (see docs on the renderEvent method)
				overlap: false,
				id: -1
			});

			// make the event draggable using jQuery UI
			$(this).draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});

		});
		// End of Draggable Events
		// INIZIO ESPERIMENTO
		var courses = [];
		var select = document.getElementById("myCourses");
		var el = document.createElement("option");
        el.textContent = "All Courses";
        el.value = "-1";
        select.appendChild(el);

		$.getJSON('Choose_Courses', function(data) {
			$(data.options).each(function() {
				courses.push({
					value : $(this).attr('Value'),
					text : $(this).attr('DisplayText')
				});
				el = document.createElement("option");
		        el.textContent = $(this).attr('DisplayText');
		        el.value = $(this).attr('Value');
		        select.appendChild(el);
			})
		});
		// FINE ESPERIMENTO
		$('#calendar').fullCalendar({
			editable : true,
			eventLimit : 2, // allow "more" link when more than two events
			aspectRatio : 2.6,
			theme : true,
			weekends : false,
			eventClick : function(calEvent, jsEvent, view) {
             		  openDialog(calEvent.start.format(), calEvent.courseId, calEvent.moduleId);
              	  }
			,
			timeFormat : 'H:mm',
			events : function(start, end, timezone, callback) {
				$.ajax({
					url : 'CalendarEvents_retrieveEvents.action',
					type : 'POST',
					async : false,
					dataType : 'json',
					data : {
						// our hypothetical feed requires UNIX timestamps
						startParam : start.format(),
						endParam : end.format(),
						courseId : $('#myCourses option:selected').val()
					},
					success : function(data) {
						var myEvents = [];
						var arrayEvents = data.events;
						$(arrayEvents).each(function() {
							myEvents.push({
								id : $(this).attr('id'),
								title : $(this).attr('title'),
								end : $(this).attr('end'),
								start : $(this).attr('start'),
								color : $(this).attr('color'),
								description : $(this).attr('description'),
								courseId : $(this).attr('courseId'),
								moduleId : $(this).attr('moduleId'),
								allDay : $(this).attr('allDay'),
								textColor: tinycolor.mostReadable($(this).attr('color'), ["#ffffff"],{includeFallbackColors:true}).toHexString()
							});
						});
						callback(myEvents);
						return false;	
					},
					error : function() {
						alert('Ajax error');
					}
				})
				return false;
			},
			editable : true,
			droppable: true, // this allows things to be dropped onto the calendar
			drop: function() {
				// is the "remove after drop" checkbox checked?
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(this).remove();
				}
			},
			eventDrop : function(event, delta, revertFunc) {
				//
				$.ajax({
					url : 'CalendarEvents_moveEvent',
					type : 'POST',
					async : false,
					dataType : 'json',
					data : {
						// our hypothetical feed requires UNIX timestamps
						startParam: event.start.format(),
						eventId:	event.id,
						courseId: 	event.courseId
					},
					success : function(data) {
						result = $(data).attr('eventId')
						if (result == -1) {
			                $("#dialog").dialog({
			                	  modal: true,
			                	  buttons: [
			                	    {
			                	    	text: "Dismiss",
			                	      	click: function() {
			                	        	$(this).dialog( "close" );
			                	      		revertFunc();
			                	      	}
			                	    }
			                	  ],
			                	  open: function(){
			                		  $("#dialog").html("Can't move event...");
			                	  }
			                	});
							revertFunc();
						}
					},
					error : function() {
						alert('Ajax error');
					}
				})
				return false;
			},
			eventRender : function(event, element) {
				if (event.description)
					element.qtip(
							{
								content : event.title + "<i><p>" + event.description + "</i></p>",
								position: {
							        my: 'top middle',  // Position my top left...
							        at: 'bottom middle', // at the bottom right of...
							        target: element // my target
							    },
							    style: {
							        classes: 'qtip-youtube'
							    }
							}		
					);
			}
		});

	});
</script>
<title>Insert title here</title>


</head>
<body>
	<!-- 	<div class='site-container'> -->
	<div id="dialog" title="System message"></div>
	<div id="pop-up" style="display:none;">
		<iframe style="width:98%;height:98%;" id="modalDialogBox" src=""></iframe>
	</div>
	<div id="selectionForm" class="course-selection">
		Available courses: &nbsp;<select id="myCourses"
			onchange="$('#calendar').fullCalendar( 'refetchEvents' );"></select>
	</div>
	<div id='calendar'></div>
	<div id="external-events" class="external-events">
		<table ALIGN="center">
			<tr>
				<td colspan="5" align="center">General events</td>
			</tr>
			<tr>
				<td><div class='fc-event'>HOLIDAY</div></td>
				<td><div class='fc-event'>VACATION</div></td>
				<td><div class='fc-event'>UNAVAILABLE</div></td>
				<td><div class='fc-event'>My Event 4</div></td>
				<td><div class='fc-event'>My Event 5</div></td>
			</tr>
		</table>
	</div>
	<!-- 	</div> -->
</body>
</html>