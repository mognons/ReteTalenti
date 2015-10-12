package com.interceptor;
 
import com.model.User;
 
 
public interface UserAware {
 
    public void setUser(User user);
}