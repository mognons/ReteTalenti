package com.action;

import java.util.*;
import org.json.simple.JSONObject;
import com.dao.DropDownDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class dropdownAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;

	private DropDownDao dao = new DropDownDao();

	private String result;
	private String message;
	private User user = new User();
	
	private List<JSONObject> options;

	public String StatiCivili() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllStatiCivili();
		result = "OK";
		return Action.SUCCESS;
	}

	public String GradiParentela() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllGradiParentela();
		result = "OK";
		return Action.SUCCESS;
	}

	public String Utenti() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllUsers();
		result = "OK";
		return Action.SUCCESS;
	}

	public String Gruppi() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllGroups();
		result = "OK";
		return Action.SUCCESS;
	}

	public String Province() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllProvince();
		result = "OK";
		return Action.SUCCESS;
	}

	public String Enti() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllEnti();
		result = "OK";
		return Action.SUCCESS;
	}

	public String EntiOther() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllEnti();
		result = "OK";
		return Action.SUCCESS;
	}

	public String MessageDestination() {
		options = new ArrayList<JSONObject> ();
		options = dao.getMessageDestination(user);
		result = "OK";
		return Action.SUCCESS;
	}

	public String UDM() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllUDM();
		result = "OK";
		return Action.SUCCESS;
	}

	public String Nazioni() {
		options = new ArrayList<JSONObject> ();
		options = dao.getAllNazioni();
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

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	
	
}