package com.action;

import java.io.IOException;
import java.util.List;




import com.utilities.RandomString;
import com.utilities.sendMail;
import com.dao.CrudDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class UsersTableAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private CrudDao dao = new CrudDao();

	private List<User> records;
	private String result;
	private String message;
	private User record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int id;
	private String userFirstname, userLastname, userEmail, username;

	public String list() {
		try {
			// Fetch Data from User Table
			records = dao.getAllUsers();
			result = "OK";
			totalRecordCount = dao.getUsersRecordCount();
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() throws IOException {
		return Action.SUCCESS;
	}

	public String update() throws IOException {
		User User = new User();

		User.setUserFirstname(userFirstname);
		User.setUserLastname(userLastname);
		User.setUserEmail(userEmail);
		User.setUsername(username);
		System.out.println("Updating "+username);

		try {
			// Update existing record
			dao.updateUserSimple(User);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String resetPassword()  throws IOException {
		RandomString rs = new RandomString(12);
		String newPassword = rs.nextString();
		dao.updateUserPassword(username, newPassword);
	   	String mailRecipient = userEmail;
    	String msgBody = "Dear " + userFirstname + " " + userLastname + ", \n\n" + 
    					"your password has been reset to: " + newPassword + "." + "\n\n" + 
    					"Please login into classAction and change your password as soon as possible";
    	sendMail sm = new sendMail();
    	sm.main("Password reset", msgBody, mailRecipient);
		message = "Password succesfully reset to: " + newPassword;
		System.out.println(message);
		result = "OK";
		return Action.SUCCESS;
	}

	public String delete() throws IOException {
		System.out.println("Deleting user " + id);
		try {
			// Update existing record
			dao.deleteUser(id);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}result = "OK";
		return Action.SUCCESS;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	
	public int getJtStartIndex() {
		return jtStartIndex;
	}
	
	public void setJtStartIndex(int jtStartIndex) {
		this.jtStartIndex = jtStartIndex;
	}
	
	public void setJtPageSize(int jtPageSize) {
		this.jtPageSize = jtPageSize;
	}
	
	public int getJtPageSize() {
		return jtPageSize;
	}
	
	public String getJtSorting() {
		return jtSorting;
	}
	
	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public User getRecord() {
		return record;
	}

	public void setRecord(User record) {
		this.record = record;
	}

	public List<User> getRecords() {
		return records;
	}

	public String getResult() {
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setRecords(List<User> records) {
		this.records = records;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}
}