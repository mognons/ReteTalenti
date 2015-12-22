package com.action;

import java.util.ArrayList;
import java.util.List;

import com.dao.NazioniDao;
import com.interceptor.UserAware;
import com.model.Nazione;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class NazioniAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private NazioniDao dao = new NazioniDao();

	private List<Nazione> records;
	private String result;
	private String message;
	private String jtFilter = "";
	private User record;
	private int totalRecordCount;
	private String jtStartIndex,jtPageSize;
	private String jtSorting;
	//

	public String list() {
		System.out.println(jtFilter);

		try {
			// Fetch Data from User Table
			totalRecordCount = dao.getCountNazioni(jtFilter);
			records = dao.getAllNazioni(jtStartIndex, jtPageSize, jtFilter);
			result = "OK";
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}
	public List<Nazione> getRecords() {
		return records;
	}
	public void setRecords(List<Nazione> records) {
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
	public User getRecord() {
		return record;
	}
	public void setRecord(User record) {
		this.record = record;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getJtPageSize() {
		return jtPageSize;
	}
	public void setJtPageSize(String jtPageSize) {
		this.jtPageSize = jtPageSize;
	}
	public String getJtSorting() {
		return jtSorting;
	}
	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}
	public String getJtStartIndex() {
		return jtStartIndex;
	}
	public void setJtStartIndex(String jtStartIndex) {
		this.jtStartIndex = jtStartIndex;
	}
	public String getJtFilter() {
		return jtFilter;
	}
	public void setJtFilter(String jtFilter) {
		this.jtFilter = jtFilter;
	}

}