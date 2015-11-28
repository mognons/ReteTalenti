package com.action;

import java.io.IOException;
import java.util.List;




import com.utilities.RandomString;
import com.utilities.sendMail;
import com.dao.UsersDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class UsersTableAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private UsersDao dao = new UsersDao();

	private User loggedUser = new User();
	private List<User> records;
	private String result;
	private String message;
	private User record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int id, ente, groupId;
	private String userFirstname, userLastname, userEmail, username, userPhone, password;

	public String list() {
		try {
			// Fetch Data from User Table
			if (loggedUser.getGroupId() != 1) { // NOT a SysAdmin...
				records = dao.getOwnUsers(loggedUser);
				totalRecordCount = dao.getCountOwnUsers(loggedUser);
			} else {
				records = dao.getAllUsers();
				totalRecordCount = dao.getUsersRecordCount();
			}
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() throws Exception {
		record = new User();
		record.setUsername(username);
		record.setPassword(password);
		record.setUserFirstname(userFirstname);
		record.setUserLastname(userLastname);
		record.setUserEmail(userEmail);
		record.setUsername(username);
		record.setUserPhone(userPhone);
		record.setEnte(ente);
		record.setGroupId(groupId);
		if (dao.verifyUsername(username)) {
			try {
				dao.createUser(record);
				result = "OK";
			} catch (Exception e) {
				message = e.getMessage();
				System.err.println(e.getMessage());
				result = "ERROR";
			}
		} else {
			message = "Impossibile creare un nuovo utente: username <b>" + username + "</b> già inserito";
			result = "ERROR";
		}
		return SUCCESS;
	}

	public String update() throws IOException {
		record = new User();

		record.setUsername(username);
		record.setUserFirstname(userFirstname);
		record.setUserLastname(userLastname);
		record.setUserEmail(userEmail);
		record.setUserPhone(userPhone);
		record.setEnte(ente);
		record.setGroupId(groupId);

		try {
			// Update existing record
			dao.updateUser(record);
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
	   	String mailRecipient = userEmail;
    	String msgBody = "Gentile " + userFirstname + " " + userLastname + ", \n\n" + 
    					"la vostra password è stata resettata al seguente valore: " + newPassword + "." + "\n\n" + 
    					"Fate cortesemente LOGIN a ReteTalenti e modificata la vostra password il più velocemente possibile. "
    					+ "\n\n"
    					+ "Grazie."
    					+ "\n\nReteTalenti System Administrator";
    	sendMail sm = new sendMail();
    	try {
	    	sm.send("Password reset", msgBody, mailRecipient);
			dao.updateUserPassword(username, newPassword);
			message = "Password resettata con successo al valore: " + newPassword;
			result = "OK";
			return Action.SUCCESS;
    	} catch (Exception e) {
			message = "Errore nell'invio della mail: password <b>NON</b> modificata";
			System.err.println(e.getMessage());
			result = "ERROR";
			return Action.SUCCESS;
    	}
	}

	public String delete() throws IOException {
		try {
			// Update existing record
			dao.deleteUser(username);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage().toString();
			System.err.println(e.getMessage());
		}
		result = "OK";
		return Action.SUCCESS;
	}
	
    public String UList() {
        try {
            // Fetch Data from Enti Table
            records = dao.getUsersByEnte(id);
            result = "OK";

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
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
	public void setUser(User loggedUser) {
		this.loggedUser = loggedUser;
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}