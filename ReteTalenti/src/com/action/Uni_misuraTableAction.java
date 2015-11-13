package com.action;

import java.io.IOException;
import java.util.List;


import com.dao.Uni_misuraDao;
import com.interceptor.UserAware;
import com.model.Uni_misura;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class Uni_misuraTableAction extends ActionSupport implements UserAware{

	/**
	 * @author Raoul Nono
	 */
	private static final long serialVersionUID = 1L;
	
	private Uni_misuraDao dao = new Uni_misuraDao();

	private List<Uni_misura> records;
	private String result;
	private String message;
	private Uni_misura record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int id;
	private String codice;
	private String descrizione;

	public String list() {
		try {
			// Fetch Data from User Table
			records = dao.getAllUni_misura();
			result = "OK";			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() throws IOException {
		record = new Uni_misura();
		record.setCodice(codice);
		record.setDescrizione(descrizione);
		// create new record
			try {
				System.out.println("Creating "+ codice);
				record.setId(dao.createUni_misura(record));
				result = "OK";
			} catch (Exception e) {
				message = e.getMessage();
				System.err.println(e.getMessage());
				result = "ERROR";
			}
			return SUCCESS;
		} 
		
	

	public String update() throws IOException {
		Uni_misura UniMisura = new Uni_misura();

		UniMisura.setId(id);
		UniMisura.setCodice(codice);
		UniMisura.setDescrizione(descrizione);
		System.out.println("Updating "+id);

		try {
			// Update existing record
			dao.updateUni_misura(UniMisura);
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
			dao.deleteUni_misura(id);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}result = "OK";
		return Action.SUCCESS;
	}

	public List<Uni_misura> getRecords() {
		return records;
	}

	public void setRecords(List<Uni_misura> records) {
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

	public Uni_misura getRecord() {
		return record;
	}

	public void setRecord(Uni_misura record) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	

}
