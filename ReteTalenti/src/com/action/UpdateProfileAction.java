package com.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.dao.UsersDao;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class UpdateProfileAction extends ActionSupport implements SessionAware, ModelDriven<User> {
 
    private static final long serialVersionUID = -3394545299120377549L;
    private String errorMsg;
	private UsersDao dao = new UsersDao();
    private User record = new User();
    private Map<String, Object> sessionAttributes = null;
 
    private String nomeutente, emailUtente, telefonoUtente;
    private String newPassword;
    private String newPassword2;
    private String oldPassword;
	
	@Override
	public void validate() {
		System.out.println("Validating");
		if (emailUtente == null || emailUtente.length() == 0) {
			addFieldError( "emailUtente", "Indirizzo Email obbligatorio" );
		}
		
		if (oldPassword != null && oldPassword.length() != 0) {
			// Verify username + password against DB
			try {
				if(!dao.verifyLogin(nomeutente, oldPassword)) 
				{
					addFieldError( "oldPassword", "Wrong password" );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (newPassword.length() != 0) {
			if (oldPassword != null && oldPassword.length() == 0) {
				addFieldError( "oldPassword", "Old password is required" );
			}
			
			if (!newPassword.equals(newPassword2)) {
				addFieldError( "newPassword2", "New password does not match" );
			}
		}
		
	}
	
    public String execute() throws Exception {
		System.out.println("Inside UpdateProfile");
		System.out.println(nomeutente);
		record = new User();
		record = dao.getUserData(nomeutente);
		record.setUserEmail(emailUtente);
		record.setUserPhone(telefonoUtente);
		record.setPassword(newPassword);
    	try {    			
    		if (newPassword.length() == 0)
    			dao.updateUser(record);
    		else
    			dao.updateUserFull(record);
    		User user = dao.getUserData(nomeutente);
            sessionAttributes.put("USER", user);
            errorMsg="";
            return SUCCESS;
			
		} catch (Exception e) {
			errorMsg = e.getMessage();
			System.err.println(e.getMessage());
			return ERROR;
		}
    }
         
 
    @Override
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
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


	public String getNomeutente() {
		return nomeutente;
	}

	public void setNomeutente(String nomeutente) {
		this.nomeutente = nomeutente;
	}

	public String getEmailUtente() {
		return emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public String getTelefonoUtente() {
		return telefonoUtente;
	}

	public void setTelefonoUtente(String telefonoUtente) {
		this.telefonoUtente = telefonoUtente;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return null;
	}
     
}