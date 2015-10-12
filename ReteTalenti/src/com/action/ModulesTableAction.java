package com.action;

import java.sql.Date;
import java.util.List;

import com.dao.ClassesDao;
import com.dao.ModulesDao;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class ModulesTableAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private ModulesDao dao = new ModulesDao();

	/* jTable Variables */
	private List<Modules> records;
	private String result;
	private String message;
	private Modules record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	
	/* Model member */
	private int courseId,moduleId, duration, seq;
	private String name, details, direction;
	private boolean moduleActive;
	
	public String list() {
		try {
			System.out.println("Calling list() method of class ModulesTableAction");
			// Fetch Data from SQL Table
			records = dao.getModules(courseId, jtStartIndex,jtPageSize);
			result = "OK";
			System.out.println(records.size());
			totalRecordCount = records.size();
					
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() {
		record = new Modules();
		record.setCourseId(courseId);
		record.setName(name);
		record.setDetails(details);
		record.setDuration(duration);
		record.setModuleActive(moduleActive);
		
		try {
			// Create new record
			record.setModuleId(dao.addModules(record));
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String update() {
		
		record = new Modules();
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setName(name);
		record.setDetails(details);
		record.setDuration(duration);
		record.setModuleActive(moduleActive);
		try {
			// Create new record
			dao.updateModules(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() {
		record = new Modules();
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setSeq(seq);
		try {
			// Create new record
			dao.deleteModules(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String relocate() {
		record = new Modules();
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setSeq(seq);
		System.out.println("Relocating moduleId: " + record.getModuleId());
		System.out.println("of courseId: " + record.getCourseId());
		System.out.println("Going..." + direction);
		try {
			dao.relocateModules(record, direction);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public List<Modules> getRecords() {
		return records;
	}

	public void setRecords(List<Modules> records) {
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

	public Modules getRecord() {
		return record;
	}

	public void setRecord(Modules record) {
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isModuleActive() {
		return moduleActive;
	}

	public void setModuleActive(boolean moduleActive) {
		this.moduleActive = moduleActive;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	
}