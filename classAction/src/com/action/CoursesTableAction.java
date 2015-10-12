package com.action;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import com.dao.CoursesDao;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class CoursesTableAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private CoursesDao dao = new CoursesDao();

	/* jTable Variables */
	private List<Courses> records;
	private String result;
	private String message;
	private Courses record;
	private int totalRecordCount, jtStartIndex, jtPageSize;
	private String jtSorting;

	/* Model member */
	private int courseId;
	private String name;
	private String type;
	private String details;
	private Date startDate;
	private int duration;
	private boolean courseActive;

	public String cloneCourse() throws IOException {
		System.out.println("Entering cloneCourse()");
		try {
			Courses course = new Courses();
			course.setName(name);
			course.setType(type);
			course.setDetails(details);
			course.setStartDate(startDate);
			course.setDuration(duration);
			course.setCourseActive(courseActive);
			course.setCourseId(courseId);
			course.setCourseId(dao.cloneCourse(course));
			record = course;
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}
	
	public String list() {
		try {
			// Fetch Data from SQL Table
			records = dao.getAllCourses();
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
		try {
			Courses course = new Courses();
			course.setName(name);
			course.setType(type);
			course.setDetails(details);
			course.setStartDate(startDate);
			course.setDuration(duration);
			course.setCourseActive(courseActive);
			course.setCourseId(dao.createCourse(course));
			record = course;
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;

	}

	public String update() throws IOException {
		try {
			Courses course = new Courses();
			course.setCourseId(courseId);
			course.setName(name);
			course.setType(type);
			course.setDetails(details);
			course.setStartDate(startDate);
			course.setDuration(duration);
			course.setCourseActive(courseActive);
			dao.updateCourse(course);
			result = "OK";

		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() throws IOException {
		int deleteResult = 0;
		try {
			deleteResult = dao.deleteCourse(courseId);
			if (deleteResult !=-1)
				result = "OK";
			else {
				message = "Course has (almost) one class and cannot be deleted";
				result =" ERROR";
			}
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return Action.SUCCESS;
	}

	public List<Courses> getRecords() {
		return records;
	}

	public void setRecords(List<Courses> records) {
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

	public Courses getRecord() {
		return record;
	}

	public void setRecord(Courses record) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isCourseActive() {
		return courseActive;
	}

	public void setCourseActive(boolean courseActive) {
		this.courseActive = courseActive;
	}

}