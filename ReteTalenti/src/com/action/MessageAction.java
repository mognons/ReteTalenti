package com.action;

import java.sql.Date;
import java.util.List;

import com.dao.AssistitiDao;
import com.dao.MessagesDao;
import com.interceptor.UserAware;
import com.model.Message;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class MessageAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private MessagesDao dao = new MessagesDao();
	private AssistitiDao a_dao = new AssistitiDao();

	private List<Message> records;
	private String result;
	private String message;
	private Message record;
	private int totalRecordCount;
	private int jtStartIndex,jtPageSize;
	private String jtSorting;
	private User user = new User();
	private int messageID, id;
	private String key1;
	private int key2;
	private java.sql.Date key3;
	
	private String tag, message_text, action;
	private String listMode;
	private java.sql.Date start_date, end_date;
	private Boolean message_read;

	public String list() {
		try {
			if (listMode.equals("ALL")) {
				records = dao.getAllMessages(jtStartIndex, jtPageSize);
				totalRecordCount = dao.getCountOfAllMessages();
			} else {
				records = dao.getValidMessages(jtStartIndex, jtPageSize, user);
				totalRecordCount = dao.getCountOfValidMessages(user);
			}
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String create() {
		record = new Message();
		record.setEnte(0); // Broadcast
		record.setTag(tag);
		record.setMessage_text(message_text);
		record.setAction(action);
		record.setStart_date(start_date);
		record.setEnd_date(end_date);
		
		try {
			dao.insertNewMessage(record);;
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String delete() {
		try {
			dao.deleteMessage(id);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String markRead() {
		try {
			dao.markMessageAsRead(messageID);
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String createMessage(Message messaggio) {
		try {
			// Fetch Data from User Table
			dao.insertNewMessage(messaggio);;
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
/* External public methods for executing actions via messages */
/* 1. Completamento operazione di trasferimento               */
	public String completaTrasferimento() {
		String nota = "Trasferimento completato in data odierna";
		// 1. Mark message as read
		markRead();
		// 2. Execute Action
		a_dao.updateEnteAssistito(key1, key2);
		// 3. Annotate variation
		NoteTableAction annotazione = new NoteTableAction();
		annotazione.annotazione(key1, nota);
		annotazione = null;
		return Action.SUCCESS;
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
 
	public String getJtSorting() {
		return jtSorting;
	}
	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}
 
	public List<Message> getRecords() {
		return records;
	}
	public void setRecords(List<Message> records) {
		this.records = records;
	}
	public Message getRecord() {
		return record;
	}
	public void setRecord(Message record) {
		this.record = record;
	}

	public int getJtStartIndex() {
		return jtStartIndex;
	}

	public void setJtStartIndex(int jtStartIndex) {
		this.jtStartIndex = jtStartIndex;
	}

	public int getJtPageSize() {
		return jtPageSize;
	}

	public void setJtPageSize(int jtPageSize) {
		this.jtPageSize = jtPageSize;
	}

	public User getUser() {
		return user;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public int getKey2() {
		return key2;
	}

	public void setKey2(int key2) {
		this.key2 = key2;
	}

	public Date getKey3() {
		return key3;
	}

	public void setKey3(Date key3) {
		this.key3 = key3;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage_text() {
		return message_text;
	}

	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public java.sql.Date getStart_date() {
		return start_date;
	}

	public void setStart_date(java.sql.Date start_date) {
		this.start_date = start_date;
	}

	public java.sql.Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(java.sql.Date end_date) {
		this.end_date = end_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getListMode() {
		return listMode;
	}

	public void setListMode(String listMode) {
		this.listMode = listMode;
	}

	public Boolean getMessage_read() {
		return message_read;
	}

	public void setMessage_read(Boolean message_read) {
		this.message_read = message_read;
	}
}