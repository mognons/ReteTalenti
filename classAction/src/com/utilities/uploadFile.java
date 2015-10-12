package com.utilities;

import java.io.File;

import org.json.simple.JSONObject;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;


public class uploadFile extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3977095432339542469L;
	private File file;
	private String contentType;
	private String filename;
	private int courseId, moduleId;
	private String result;
	private HttpServletRequest servletRequest;


	public String execute() {
		// ...
		System.out.println("Entering class uploadFile, method execute()");
		System.out.println("Filename: " + filename);
		System.out.println("ContentType: " + contentType);
		System.out.println("CourseId: " + courseId);
		System.out.println("moduleId: " + moduleId);
		
		String destPath = "documents/"+courseId+"/"+moduleId;
		result = "OK";
	       try {
	            String filePath1 = servletRequest.getSession().getServletContext().getRealPath("/");
	            System.out.println("Destination path:" + filePath1+destPath);
	            File fileToCreate = new File(filePath1+destPath, this.filename);
	            FileUtils.copyFile(this.file, fileToCreate);
	            System.out.println("File copied...");
	        } catch (Exception e) {
	            e.printStackTrace();
	            addActionError(e.getMessage());
	            return Action.SUCCESS;
	        }
		return Action.SUCCESS;
	}

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
 
    }
	
	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
	
}
