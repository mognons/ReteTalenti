package com.action;
 
import java.util.ArrayList;
import java.util.List;

import com.interceptor.UserAware;
import com.model.User;
import com.model.Groups;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class MenuAction extends ActionSupport implements UserAware, ModelDriven<User> {
 
    private static final long serialVersionUID = 8111129314704779336L;
    private User user;
    
    public String execute() {
    	System.out.println("Inside menuAction");
    	System.out.println(user.getUsername());
    	return "success";
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