package com.action;
 
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.model.User;
import com.dao.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class LoginAction extends ActionSupport implements SessionAware, ModelDriven<User>{
 
    private static final long serialVersionUID = -3369875299120377549L;
    private String errorMsg;
    private CrudDao dao = new CrudDao();
    private String cognome;
    private int openTab = 0;
 
    @Override
    public String execute() throws Exception {
        if (dao.verifyLogin(user.getUsername(), user.getPassword())) {
            user = dao.getUser(user.getUsername());
            sessionAttributes.put("USER", user);
            errorMsg="";
            return SUCCESS;
        }
        else {
        	errorMsg="Login Error: invalid username and/or password";
            return INPUT;
        }
    }
	public void validate() {
		if (user.getUsername().length() == 0) {
			addFieldError( "username", "Field is required." );
		}
		if (user.getPassword().length() == 0) {
			addFieldError( "password", "Field is required." );
		}

		
	} 
    private User user = new User();
    private Map<String, Object> sessionAttributes = null;
 
    @Override
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }
     
    @Override
    public User getModel() {
        return user;
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public int getOpenTab() {
		return openTab;
	}
	public void setOpenTab(int openTab) {
		this.openTab = openTab;
	}
     
	
}