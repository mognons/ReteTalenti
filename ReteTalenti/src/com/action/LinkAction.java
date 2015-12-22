package com.action;
 
import org.apache.log4j.Logger;

import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class LinkAction extends ActionSupport implements UserAware, ModelDriven<User> {
	static final Logger LOGGER = Logger.getLogger(LinkAction.class);

    private static final long serialVersionUID = 8111129314704779336L;
    private User user;
    private String menuItem;
    
    public String execute() {
    	LOGGER.info("User " + user.getUsername() + " is calling " + menuItem);
    	return Action.SUCCESS;
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

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}
 
}