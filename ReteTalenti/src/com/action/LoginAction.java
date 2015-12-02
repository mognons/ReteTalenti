package com.action;
 
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.model.User;
import com.dao.MessagesDao;
import com.dao.UsersDao;
import com.jdbc.DataAccessObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class LoginAction extends ActionSupport implements SessionAware, ModelDriven<User>{
 
    private static final long serialVersionUID = -3369875299120377549L;
    private String errorMsg, errorMessage;
    private UsersDao dao = new UsersDao();
    private MessagesDao m_dao = new MessagesDao();
    private String cognome;
    private int openTab = 0;
    private Boolean loginOk;
 
    @Override
    public String execute() throws Exception {
    	if (user.getUsername() == null)
    		return INPUT;
    	
    	try {
    		loginOk = dao.verifyLogin(user.getUsername(), user.getPassword());
    	} catch (Exception e) {
    		errorMessage = "Database unreachable or unavailable. Contact SYSADMIN";
    		DataAccessObject.closeConnection();
    		return ERROR;
    	}
        if (loginOk) {
            user = dao.getUserData(user.getUsername());
            sessionAttributes.put("USER", user);
        	int actualMessageId = m_dao.getLastIdOfValidMessages(user);
        	System.err.print(actualMessageId);
        	sessionAttributes.put("LASTMSGID", actualMessageId);
            errorMsg="";
            return SUCCESS;
        }
        else {
        	errorMsg="Login Error: invalid username and/or password";
            return INPUT;
        }
    }
	public void validate() {
		if (user.getUsername() != null) {
	    	if (user.getUsername().length() == 0) {
				addFieldError( "username", "Field is required." );
			}
			if (user.getPassword().length() == 0) {
				addFieldError( "password", "Field is required." );
			}
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
     
	
}