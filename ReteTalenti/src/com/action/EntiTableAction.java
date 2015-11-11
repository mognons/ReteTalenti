package com.action;

import java.io.IOException;
import java.util.List;


import com.utilities.RandomString;
import com.utilities.sendMail;
import com.dao.CrudDao;
import com.interceptor.UserAware;
import com.model.Ente;
import com.model.User;
import com.opensymphony.xwork2.Action;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;

 
public class EntiTableAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private CrudDao dao = new CrudDao();

	private List<Ente> records;
	private String result;
	private String message;
	private User record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int id, ente;
	private String userFirstname, userLastname, userEmail, username, userPhone, password;

	public String list() {
		try {
			// Fetch Data from Enti Table
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
		User record = new User();
		record.setUsername(username);
		record.setPassword(password);
		record.setUserFirstname(userFirstname);
		record.setUserLastname(userLastname);
		record.setUserEmail(userEmail);
		record.setUsername(username);
		record.setUserPhone(userPhone);
		record.setEnte(ente);
		if (dao.verifyUsername(username)) {
			try {
				System.out.println("Creating "+username);
				dao.createUser(record);
				result = "OK";
			} catch (Exception e) {
				message = e.getMessage();
				System.err.println(e.getMessage());
				result = "ERROR";
			}
		} else {
			System.out.println("Username already exists");
			message = "Impossibile creare un nuovo utente: username <b>" + username + "</b> già inserito";
			result = "ERROR";
		}
		return SUCCESS;
	}

	public String update() throws IOException {
		User User = new User();

		User.setUserFirstname(userFirstname);
		User.setUserLastname(userLastname);
		User.setUserEmail(userEmail);
		User.setUsername(username);
		User.setUserPhone(userPhone);
		User.setEnte(ente);
		System.out.println("Updating "+username);

		try {
			// Update existing record
			dao.updateUser(User);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
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

	public int getEnte() {
		return ente;
	}

	public void setEnte(int ente) {
		this.ente = ente;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}