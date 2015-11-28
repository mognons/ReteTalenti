package com.action;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.interceptor.UserAware;
import com.model.Groups;
import com.model.User;
import com.dao.MessagesDao;
import com.opensymphony.xwork2.ActionSupport;
 
public class WelcomeAction extends ActionSupport implements UserAware {
 
    private static final long serialVersionUID = 8111120314704779336L;
	private List<Groups> userGroups = new ArrayList<Groups>();
	private Boolean hasEvents, hasTodos = false;
	private Date currentDate;
	private String today, result;
	private MessagesDao dao = new MessagesDao();
	private User user = new User();

    @Override
    public String execute() {
    	currentDate = new Date();
    	System.out.println(user.getEnte());
    	if (dao.getCountOfValidMessages(user)!=0) {
    		hasEvents = true;
    	}
        return SUCCESS;
    }
     

	public List<Groups> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<Groups> userGroups) {
		this.userGroups = userGroups;
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

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
 
}