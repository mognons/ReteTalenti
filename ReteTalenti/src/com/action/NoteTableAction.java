package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.DocumentsDao;
import com.dao.NoteAssistitoDao;
import com.interceptor.UserAware;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class NoteTableAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 123423L;
	
	private NoteAssistitoDao dao = new NoteAssistitoDao();

	/* jTable Variables */
	private List<NoteAssistito> records;
	private User user = new User();
	private String result;
	private String message;
	private NoteAssistito record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	private HttpServletRequest servletRequest;

	/* jTable variables / fields */
	private String note_libere, cf_assistito_note;
	private int id;
	
	
	public String list() {
		try {
			System.out.println("Calling list() method of class ModulesTableAction");
			records = dao.getNoteAssistito(jtStartIndex, jtPageSize, cf_assistito_note);
			result = "OK";
			totalRecordCount = 1;
					
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() {

		try {
			// Create new record
			//record.setDocumentId(dao.addDocuments(record));
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String update() {
		
		record = new NoteAssistito();
		record.setId(id);
		record.setNote_libere(note_libere);
		record.setCf_assistito_note(cf_assistito_note);
		record.setOperatore(user.getId());
		
		try {
			// Create new record
			//dao.updateDocuments(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() {
		return Action.SUCCESS;
	}


	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public List<NoteAssistito> getRecords() {
		return records;
	}

	public void setRecords(List<NoteAssistito> records) {
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

	public NoteAssistito getRecord() {
		return record;
	}

	public void setRecord(NoteAssistito record) {
		this.record = record;
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

	public int getJtPageSize() {
		return jtPageSize;
	}

	public void setJtPageSize(int jtPageSize) {
		this.jtPageSize = jtPageSize;
	}

	public String getJtSorting() {
		return jtSorting;
	}

	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}

	public String getNote_libere() {
		return note_libere;
	}

	public void setNote_libere(String note_libere) {
		this.note_libere = note_libere;
	}

	public String getCf_assistito_note() {
		return cf_assistito_note;
	}

	public void setCf_assistito_note(String cf_assistito_note) {
		this.cf_assistito_note = cf_assistito_note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	
}