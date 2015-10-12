package com.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dao.CrudDao;
import com.model.Student;
import com.model.classRegister;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class StudentTableAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private CrudDao dao = new CrudDao();

	private List records;
	private Student record;
	private String result;
	private String message;

	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	//
	private int studentId;
	private String name;
	private String department;
	private String emailId;
	private int courseId, moduleId;

	public String list() {
		try {
			// Fetch Data from Student Table
			records = dao.getAllStudents(jtStartIndex,jtPageSize,jtSorting.toUpperCase());
			result = "OK";
			totalRecordCount = dao.getRecordCount();
			
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String create() throws IOException {
		Student record = new Student();
		int studentId = dao.getNextID();
		record.setStudentId(studentId);
		record.setName(name);
		record.setDepartment(department);
		record.setEmailId(emailId);
		try {
			// Create new record
			dao.addStudent(record);
			result = "OK";

		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String update() throws IOException {
		Student student = new Student();

		student.setStudentId(studentId);
		student.setName(name);
		student.setDepartment(department);
		student.setEmailId(emailId);

		try {
			// Update existing record
			dao.updateStudent(student);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() throws IOException {
		// Delete record
		int deleteResult;
		try {
			deleteResult = dao.deleteStudent(studentId);
			if (deleteResult != -1)
				result = "OK";
			else {
				result = "ERROR";
				message = "Cannot delete a student when joining one or more classes";
			}
				
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
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

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public String getEmailId() {
		return emailId;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Object getRecord() {
		return record;
	}

	public void setRecord(Student record) {
		this.record = record;
	}

	public List getRecords() {
		return records;
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public void setRecords(List records) {
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
	
	
}