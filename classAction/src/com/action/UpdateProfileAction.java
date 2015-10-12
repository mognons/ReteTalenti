package com.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.dao.CrudDao;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class UpdateProfileAction extends ActionSupport implements SessionAware, ModelDriven<User>{
 
    private static final long serialVersionUID = -3394545299120377549L;
    private String errorMsg;
	private CrudDao dao = new CrudDao();
    private User user = new User();
    private String newPassword;
    private String newPassword2;
    private String oldPassword;
	
	@Override
	public void validate() {
		System.out.print("Validating");
		if (user.getUserFirstname().length() == 0) {
			addFieldError( "userFirstname", "First name is required." );
		}
		if (user.getUserLastname().length() == 0) {
			addFieldError( "userLastname", "Last name is required." );
		}
		if (user.getUserEmail().length() == 0) {
			addFieldError( "userEmail", "Email address is required." );
		}
		
		if (oldPassword.length() != 0) {
			// Verify username + password against DB
			try {
				if(!dao.verifyLogin(user.getUsername(), oldPassword)) 
				{
					addFieldError( "oldPassword", "Wrong password" );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (newPassword.length() != 0) {
			if (oldPassword.length() == 0) {
				addFieldError( "oldPassword", "Old password is required" );
			}
			
			if (!newPassword.equals(newPassword2)) {
				addFieldError( "newPassword2", "New password does not match" );
			}
		}
		
	}
	
    public String execute(){
		System.out.println("Inside UpdateProfile");
		System.out.println(user.getUsername());
		System.out.println(newPassword);
		user.setPassword(newPassword);
    	try {    			
    		if (newPassword.length() == 0)
    			dao.updateUserSimple(user);
    		else
    			dao.updateUserFull(user);
            errorMsg="";
            return SUCCESS;
			
		} catch (Exception e) {
			errorMsg = e.getMessage();
			System.err.println(e.getMessage());
			return Action.ERROR;
		}
    }
         
 
    @Override
    public void setSession(Map<String, Object> sessionAttributes) {
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


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getNewPassword2() {
		return newPassword2;
	}


	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
     
}