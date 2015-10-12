package com.action;

import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.struts2.interceptor.SessionAware;

import com.dao.CrudDao;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegistrationAction extends ActionSupport implements SessionAware,
		ModelDriven<User> {

	private static final long serialVersionUID = -3394545299120377549L;
	private String errorMsg;
	private CrudDao dao = new CrudDao();
	private int openTab = 1;

	public void validate() {
		if (user.getUserFirstname().length() == 0) {
			addFieldError("userFirstname", "Field is required.");
		}
		if (user.getUserLastname().length() == 0) {
			addFieldError("userLastname", "Field is required.");
		}
		if (user.getUserEmail().length() == 0) {
			addFieldError("userEmail", "Field is required.");
		}
		if (user.getPassword().length() == 0) {
			addFieldError("password", "Field is required.");
		}
		Boolean mailValid = true;
		try {
			InternetAddress emailAddr = new InternetAddress(user.getUserEmail());
			emailAddr.validate();
		} catch (AddressException ex) {
			mailValid = false;
		}

		if (!mailValid) {
			addFieldError("userEmail", "Wrong format for email address");			
		}
	}

	@Override
	public String execute() {

		if (dao.verifyUsername(user.getUserLastname())) {
			try {
				dao.createUser(user);
				errorMsg = "";
				return SUCCESS;

			} catch (Exception e) {
				errorMsg = e.getMessage();
				System.err.println(e.getMessage());
				return Action.ERROR;
			}
		} else {
			errorMsg = "Can't sign-up: username already exists";
			return ERROR;
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

	public int getOpenTab() {
		return openTab;
	}

	public void setOpenTab(int openTab) {
		this.openTab = openTab;
	}

}