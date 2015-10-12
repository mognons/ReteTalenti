package com.action;
 
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class LinkAction extends ActionSupport implements UserAware, ModelDriven<User> {
 
    private static final long serialVersionUID = 8111129314704779336L;
    private User user;
    
     
    public String table() {
    	return "table";
    }
    
    public String report() {
    	return "report";
    }
    
    public String documents() {
    	return "nowhere";
    }
    
    public String nowhere() {
    	return "nowhere";
    }
    
    public String users() {
    	return "users";
    }
    
    public String courses() {
    	return "courses";
    }
    
    public String profile() {
    	return "profile";
    }
    
    public String calendar() {
    	return "calendar";
    }
    
    public String class_register() {
    	return "class_register";
    }
    
    public String filemanager() {
    	return "filemanager";
    }
    
    public String welcome() {
    	return "welcome";
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
 
}