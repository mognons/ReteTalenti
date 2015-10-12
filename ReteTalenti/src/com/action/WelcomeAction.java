package com.action;
 
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.interceptor.UserAware;
import com.model.CalendarDTO;
import com.model.Groups;
import com.model.User;
import com.dao.CalendarsDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class WelcomeAction extends ActionSupport implements UserAware, ModelDriven<User> {
 
    private static final long serialVersionUID = 8111120314704779336L;
	private List<Groups> userGroups = new ArrayList<Groups>();
	private CalendarsDao cDao = new CalendarsDao();
	private List<CalendarDTO> todayEvents;
	private Boolean hasEvents, hasTodos = false;

    @Override
    public String execute() {
    	setTodayEvents(new ArrayList<CalendarDTO>());
    	setUserGroups(user.getGroups());
    	DateTime dt = new DateTime();
    	DateTimeFormatter fmt = ISODateTimeFormat.date();
    	String today = fmt.print(dt);
    	todayEvents = cDao.retrieveEvents(-1, today, today);
    	if (todayEvents.size() != 0)
    		hasEvents = true;
    	// Start checking for future tasks
    	// Incoming courses needing schedule
    	// Incoming courses needing completion
    	// Unassigned courses to tutors (future)
    	// No class for courses
    	// Other checks
    	System.out.println("Reading ResourceBundle");
    	// Lets check configuration
		ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
		Enumeration <String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			System.out.println(key + ": " + value);
		}
		
        return SUCCESS;
    }
     
    private User user = new User();
    @Override
    public void setUser(User user) {
        this.user=user;
    }
     
    public User getUser(User user){
        return this.user;
    }
 
    @Override
    public User getModel() {
        return this.user;
    }

	public List<Groups> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<Groups> userGroups) {
		this.userGroups = userGroups;
	}

	public List<CalendarDTO> getTodayEvents() {
		return todayEvents;
	}

	public void setTodayEvents(List<CalendarDTO> todayEvents) {
		this.todayEvents = todayEvents;
	}

	public Boolean getHasEvents() {
		return hasEvents;
	}

	public void setHasEvents(Boolean hasEvents) {
		this.hasEvents = hasEvents;
	}

	public Boolean getHasTodos() {
		return hasTodos;
	}

	public void setHasTodos(Boolean hasTodos) {
		this.hasTodos = hasTodos;
	}
 
}