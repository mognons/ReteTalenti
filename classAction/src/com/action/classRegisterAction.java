package com.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dao.CrudDao;
import com.model.classRegister;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class classRegisterAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private CrudDao dao = new CrudDao();

	private List<classRegister> records;
	private classRegister record;
	private String result;
	private String message;

	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int studentId, courseId, moduleId, registerId, registerValue;
	private String registerDate, entryType, absenceReason;

	public String list() {
		try {
			// Fetch Data from Student Table
			records = new ArrayList<classRegister>();
			System.out.println("Inside registerClass_list() with courseId/date: " + courseId+ " / " + registerDate);
			records = dao.getStudentsInClass(registerDate,courseId,moduleId);
			result = "OK";
			totalRecordCount = records.size();
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() throws IOException {
		return Action.SUCCESS;
	}

	public String update() throws IOException {
		classRegister event = new classRegister();
		if (entryType.equals("P"))
			event.setAbsenceReason("");
		else
			event.setAbsenceReason(absenceReason);
		event.setRegisterId(registerId);
		event.setEntryType(entryType);
		event.setRegisterValue(registerValue);

		try {
			// Update existing record
			dao.updateClassRegister(event);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() throws IOException { // NO DELETE IN THIS CLASS
		return Action.SUCCESS;
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
	
	public int getStudentId() {
		return studentId;
	}

	public Object getRecord() {
		return record;
	}

	public void setRecord(classRegister record) {
		this.record = record;
	}

	public List<classRegister> getRecords() {
		return records;
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public void setRecords(List<classRegister> records) {
		this.records = records;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public int getRegisterValue() {
		return registerValue;
	}

	public void setRegisterValue(int registerValue) {
		this.registerValue = registerValue;
	}

	public String getAbsenceReason() {
		return absenceReason;
	}

	public void setAbsenceReason(String absenceReason) {
		this.absenceReason = absenceReason;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}