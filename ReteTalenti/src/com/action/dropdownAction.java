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

	public String Province() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllProvince();
		System.out.println("Inside getAllProvince()");
		result = "OK";
		return Action.SUCCESS;
	}

	public String Enti() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllEnti();
		System.out.println("Inside getAllEnti()");
		result = "OK";
		return Action.SUCCESS;
	}

	public String UDM() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllUDM();
		System.out.println("Inside getAllUDM()");
		result = "OK";
		return Action.SUCCESS;
	}

	public String Nazioni() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllNazioni();
		System.out.println("Inside getAllNazioni()");
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