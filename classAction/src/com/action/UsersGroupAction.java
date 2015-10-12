package com.action;

import java.io.IOException;
import java.util.*;

import com.dao.CrudDao;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class UsersGroupAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private CrudDao dao = new CrudDao();

	private List<Groups> records;
	private String result;
	private String message;
	private UserGroup newUserGroup;
	private Groups record;
	//
	private int id;
	private int groupId;
	private String groupName;

	public String list() {
		try {
			// Fetch Data from User Table
			records = dao.getGroupsByUser(id);
			result = "OK";			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create()  {
		newUserGroup = new UserGroup();
		record = new Groups();
		newUserGroup.setUserId(id);
		newUserGroup.setGroupId(groupId);
		record.setGroupId(groupId);
		record.setGroupName(dao.getGroupById(groupId).getGroupName());
		try {
			// Create new record
			dao.addUserGroup(newUserGroup);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		System.out.println(result);
		return Action.SUCCESS;

	}


	public String delete() throws IOException {
		System.out.println("Deleting group association for userId " + id); 
		try {
			dao.deleteUserGroup(id, groupId);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}

		return Action.SUCCESS;
	}


	/* Getter(s) and Setter(s) */

	public List<Groups> getRecords() {
		return records;
	}

	public void setRecords(List<Groups> records) {
		this.records = records;
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

	public Groups getRecord() {
		return record;
	}

	public void setRecord(Groups record) {
		this.record = record;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	
}