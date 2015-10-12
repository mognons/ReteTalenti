package com.model;

public class classRegister {
	private int registerId;
	private String registerDate;
	private int courseId;
	private int moduleId;
	private int studentId;
	private String name;
	private String entryType;
	private int registerValue;
	private String absenceReason;
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbsenceReason() {
		return absenceReason;
	}
	public void setAbsenceReason(String absenceReason) {
		this.absenceReason = absenceReason;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public int getRegisterValue() {
		return registerValue;
	}
	public void setRegisterValue(int registerValue) {
		this.registerValue = registerValue;
	}
	public int getRegisterId() {
		return registerId;
	}
	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	
	

}
