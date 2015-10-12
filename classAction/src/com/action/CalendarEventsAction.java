package com.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dao.CalendarsDao;
import com.dao.ModulesDao;
import com.interceptor.UserAware;
import com.model.CalendarDTO;
import com.model.Modules;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class CalendarEventsAction extends ActionSupport implements UserAware {

	private static final long serialVersionUID = 8111120314704779336L;

	public User user;
	public String startParam, endParam, courseId, courseName, calendarColor,
			startDate, message;
	private int eventId;
	
	private List<String> selectedDays;
	public CalendarDTO c;
	private CalendarsDao calendarsDao = new CalendarsDao();
	private ModulesDao modulesDao = new ModulesDao();
	public List<CalendarDTO> events = new ArrayList<CalendarDTO>();

	public String execute() {
		return Action.SUCCESS;
	}

	public String retrieveEvents() {
		events = calendarsDao.retrieveEvents(Integer.parseInt(courseId),
				startParam, endParam);
		return "success";
	}

	private DateTime nextAvailableDate(DateTime myDate) {
		// Must Add support for checking holidays events
		for (int i = 0; i < 366; i++) {
			if (selectedDays.contains(myDate.getDayOfWeek()) && calendarsDao.isDroppable(myDate.toString().substring(0, 10))) {
				break;
			}
			myDate = new DateTime(myDate).plusDays(1);
		}
		return myDate;
	}

	public String generateCalendar() throws Exception {
		List<Modules> modules;
		if (selectedDays == null) {
			message = "No weekdays selected for course lessons";
			return Action.SUCCESS;
		}
		Boolean didLoadHolidays = this.checkCalendarLoaded();
		DateTime myDate = new DateTime(startDate);
		// Delete all events
		try {
			calendarsDao.deleteAllEvents(Integer.parseInt(courseId));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		modules = modulesDao.getModules(Integer.parseInt(courseId), 0, 0);
		for (Modules myModule : modules) {
			for (int y = 1; y <= myModule.getDuration(); y++) {
				myDate = new DateTime(nextAvailableDate(myDate));
				CalendarDTO mycalendar = new CalendarDTO();
				mycalendar.setCourseId(myModule.getCourseId());
				mycalendar.setModuleId(myModule.getModuleId());
				mycalendar.setTitle(courseName + "\n" + myModule.getName());
				mycalendar.setDescription(myModule.getDetails());
				mycalendar.setStart(myDate.toString());
				mycalendar.setEnd(myDate.toString());
				mycalendar.setColor(calendarColor);
				mycalendar.setAllDay(true);
				// INSERT CALENDAR EVENTS HERE
				try {
					int id = calendarsDao.createEvent(mycalendar);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				myDate = myDate.plusDays(1);
			}

		}
		return Action.SUCCESS;
	}

	public String moveEvent() {
		System.out.println("moving eventId: " + eventId);
		System.out.println("to: " + startParam);
		System.out.println("for courseId: " + courseId);
		if (calendarsDao.isDroppable(startParam.substring(0, 10))) {
			eventId = 0;
		} else {
			eventId = -1;			
		}
		return Action.SUCCESS;
	}
	
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	private Boolean checkCalendarLoaded() throws Exception {
		// Read from configuration table, key lastYearHolidayLoaded
		int lastYear = calendarsDao.retrieveLastCalendarLoaded();
		// Get actual year and next year for best coverage
		DateTime now = new DateTime();
		int nextYear = now.getYear() + 1;
		// get holidays for computed years (next-lastLoaded)
		System.out.println("Last year: " + lastYear);
		System.out.println("Next yar: " + nextYear);
		
		for (int i=lastYear+1; i<nextYear+1; i++) {
			List<CalendarDTO> holidays = retrieveHolydays(i);
			Iterator<CalendarDTO> events = holidays.iterator();
			// generate Calendar events
			while (events.hasNext()) {
				// INSERT EVENTS
				CalendarDTO myEvent = (CalendarDTO) events.next();
				int id = calendarsDao.createEvent(myEvent);
			}
		}
		// update configuration table
		calendarsDao.updateLastCalendarLoaded(nextYear);
		
		return true;
	}
	
	
	public List<CalendarDTO> retrieveHolydays(int yearToRetrieve) throws Exception {
		String baseUrl = "http://kayaposoft.com/enrico/json/v1.0/?action=getPublicHolidaysForYear&year=" ;
		String completeUrl = baseUrl + Integer.toString(yearToRetrieve) + "&country=ita&region=";
		String json = readUrl(completeUrl);
		JSONParser parser = new JSONParser();
		List<CalendarDTO> holidayEvents = new ArrayList<CalendarDTO>();

		try {
			Object obj = parser.parse(json);
			JSONArray jsonObject = (JSONArray) obj;
			Iterator<JSONObject> holidays = jsonObject.iterator();
			while (holidays.hasNext()) {
				JSONObject holiday = (JSONObject) holidays.next();
				String eventDate = holiday.get("date").toString();
				Object myDate = parser.parse(eventDate);
				JSONObject singleHoliday = (JSONObject) myDate;
				CalendarDTO mycalendar = new CalendarDTO();
				Long month = (Long) singleHoliday.get("month");
				Long day = (Long) singleHoliday.get("day");
				String monthString = month.toString();
				String dayString = day.toString();
				
				String ISODate = singleHoliday.get("year") + "-" +
								"00".substring(monthString.length()) + monthString + "-" +
								"00".substring(dayString.length()) + dayString + "T00:00:00";
				mycalendar.setId(-1);
				mycalendar.setCourseId(-1);
				mycalendar.setModuleId(-1);
				mycalendar.setTitle(holiday.get("localName").toString());
				mycalendar.setDescription(holiday.get("localName").toString() + "<br>" + holiday.get("englishName").toString());
				mycalendar.setStart(ISODate);
				mycalendar.setEnd(ISODate);
				mycalendar.setColor("black");
				mycalendar.setAllDay(true);
				holidayEvents.add(mycalendar);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return holidayEvents;
	}
	
	public String getStartParam() {
		return startParam;
	}

	public void setStartParam(String startParam) {
		this.startParam = startParam;
	}

	public String getEndParam() {
		return endParam;
	}

	public void setEndParam(String endParam) {
		this.endParam = endParam;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCalendarColor() {
		return calendarColor;
	}

	public void setCalendarColor(String calendarColor) {
		this.calendarColor = calendarColor;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getSelectedDays() {
		return selectedDays;
	}

	public void setSelectedDays(List<String> selectedDays) {
		this.selectedDays = selectedDays;
	}

	public List<CalendarDTO> getEvents() {
		return events;
	}

	public void setEvents(List<CalendarDTO> events) {
		this.events = events;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub

	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

}