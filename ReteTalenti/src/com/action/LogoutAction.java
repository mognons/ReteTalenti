package com.action;
 
import java.util.Map;
 
import org.apache.struts2.interceptor.SessionAware;
 
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class LogoutAction extends ActionSupport implements SessionAware, ModelDriven<User>{
 
    private static final long serialVersionUID = -3369875299120377549L;
    private String errorMsg;
 
    @Override
    public String execute() {
            sessionAttributes.remove("USER");
            return INPUT;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
     
	
}