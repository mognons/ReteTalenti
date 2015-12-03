package com.action;
 
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.dao.MessagesDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class HeaderAction extends ActionSupport implements SessionAware, UserAware, ModelDriven<User> {
 
    private static final long serialVersionUID = 8111120314704779336L;
    public User user;
    private Boolean newMessages = false;
    private Map<String, Object> sessionAttributes = null;
    private MessagesDao dao = new MessagesDao();
    
    public String execute(){
    	int lastMessageId = (int) sessionAttributes.get("LASTMSGID");
    	int actualMessageId = dao.getLastIdOfValidMessages(user);
    	sessionAttributes.put("LASTMSGID", actualMessageId);
    	if (lastMessageId > actualMessageId) {
    		newMessages = true;
    	}
        return SUCCESS;
    }
     
    @Override
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }
 
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

	public Boolean getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(Boolean newMessages) {
		this.newMessages = newMessages;
	}
 
}