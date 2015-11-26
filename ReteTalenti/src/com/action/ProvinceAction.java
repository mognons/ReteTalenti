package com.action;

import java.util.List;

import com.dao.ProvinceDao;
import com.interceptor.UserAware;
import com.model.Provincia;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class ProvinceAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private ProvinceDao dao = new ProvinceDao();

	private List<Provincia> records;
	private String result;
	private String message;
	private Provincia record;
	private int totalRecordCount;
	private String jtStartIndex,jtPageSize;
	private String jtSorting;
	//

	public String list() {
		try {
			// Fetch Data from User Table
			records = dao.getAllProvince();
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
	public List<Provincia> getRecords() {
		return records;
	}
	public void setRecords(List<Provincia> records) {
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
	public Provincia getRecord() {
		return record;
	}
	public void setRecord(Provincia record) {
		this.record = record;
	}


}