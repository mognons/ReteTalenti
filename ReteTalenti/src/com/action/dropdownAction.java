package com.action;

import java.util.*;
import org.json.simple.JSONObject;
import com.dao.DropDownDao;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class dropdownAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DropDownDao dao = new DropDownDao();

	private String result;
	private String message;
	
	private List<JSONObject> options;

	public String Gruppi() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllGroups();
		System.out.println("Inside getAllGroups()");
		result = "OK";
		return Action.SUCCESS;
	}


	public String Students() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllStudents();
		System.out.println("Inside getAllStudents()");
		result = "OK";
		return Action.SUCCESS;
	}

	public String Courses() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllCourses();
		System.out.println("Inside getAllCourse()");
		result = "OK";
		return Action.SUCCESS;
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

	public List<JSONObject> getoptions() {
		return options;
	}

	public void setoptions(List<JSONObject> options) {
		this.options = options;
	}

	
	
}