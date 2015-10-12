package com.model;
import java.sql.Date;
public class Courses {
	private int courseId;
	private String name;
	private String type;
	private String details;
	private Date startDate;
	private int duration;
	private boolean courseActive;
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
