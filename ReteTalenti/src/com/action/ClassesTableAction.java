package com.action;

import java.sql.Date;
import java.util.List;

import com.dao.ClassesDao;
import com.interceptor.UserAware;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class ClassesTableAction extends ActionSupport implements UserAware {
	private static final long serialVersionUID = 1L;
	
	private ClassesDao dao = new ClassesDao();

	/* jTable Variables */
	private List<Classes> records;
	private String result;
	private String message;
	private Classes record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	
	/* Model member */
	private int courseId,studentId;
	private Date joinDate;
	private boolean userActive;
	
	public String list() {
		try {
			System.out.println("Calling list() method of class ClassesTableAction");
			// Fetch Data from SQL Table
			records = dao.getClassStudents(courseId,jtStartIndex,jtPageSize);
			result = "OK";
			System.out.println(records.size());
			//totalRecordCount = records.size();
					
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() {
		System.out.println(courseId);
		System.out.println(studentId);
		System.out.println(joinDate);
		System.out.println(userActive);
		
		record = new Classes();
		record.setCourseId(courseId);
		record.setStudentId(studentId);
		record.setJoinDate(joinDate);
		record.setUserActive(userActive);
		try {
			// Create new record
			dao.addClasses(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		System.out.println(result);
		return Action.SUCCESS;
	}

	public String update() {
		
		record = new Classes();
		record.setCourseId(courseId);
		record.setStudentId(studentId);
		record.setJoinDate(joinDate);
		record.setUserActive(userActive);
		try {
			// Create new record
			dao.updateClasses(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() {
		record = new Classes();
		record.setCourseId(courseId);
		record.setStudentId(studentId);
		record.setJoinDate(joinDate);
		record.setUserActive(userActive);
		try {
			// Create new record
			dao.deleteClasses(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public List<Classes> getRecords() {
		return records;
	}

	public void setRecords(List<Classes> records) {
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

	public Classes getRecord() {
		return record;
	}

	public void setRecord(Classes record) {
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

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public boolean isUserActive() {
		return userActive;
	}

	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	
}