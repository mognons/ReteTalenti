package com.action;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.dao.ClassesDao;
import com.dao.MarksDao;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class MarksTableAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private MarksDao dao = new MarksDao();

	/* jTable Variables */
	private List<Marks> records;
	private Marks record;
	private String result;
	private String message;
	
	/* Model member */
	private int courseId,studentId, moduleId, markValue, markId;
	private String markDate;
	private String markType;
	
	public String list() {
    	DateTime dt = new DateTime();
    	DateTimeFormatter fmt = ISODateTimeFormat.date();
    	String today = fmt.print(dt);

		try {
			// Fetch Data from SQL Table
			
			records = dao.getMarksByStudentDate(courseId, moduleId, studentId, today);
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
    	DateTime dt = new DateTime();
    	DateTimeFormatter fmt = ISODateTimeFormat.date();
    	String today = fmt.print(dt);
		
		record = new Marks();
		record.setCourseId(courseId);
		record.setStudentId(studentId);
		record.setModuleId(moduleId);
		record.setMarkDate(today);
		record.setMarkType(markType);
		record.setMarkValue(markValue);
		try {
			// Create new record
			record.setMarkId(dao.addMarks(record));
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String update() {
		
		record = new Marks();
		record.setCourseId(courseId);
		record.setStudentId(studentId);
		record.setModuleId(moduleId);
		record.setMarkDate(markDate);
		record.setMarkType(markType);
		record.setMarkValue(markValue);
		
		try {
			// Create new record
			dao.updateMarks(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() { //unused
		return Action.SUCCESS;
	}

	public List<Marks> getRecords() {
		return records;
	}

	public void setRecords(List<Marks> records) {
		this.records = records;
	}

	public Marks getRecord() {
		return record;
	}

	public void setRecord(Marks record) {
		this.record = record;
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

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getMarkValue() {
		return markValue;
	}

	public void setMarkValue(int markValue) {
		this.markValue = markValue;
	}

	public String getMarkDate() {
		return markDate;
	}

	public void setMarkDate(String markDate) {
		this.markDate = markDate;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

	
}