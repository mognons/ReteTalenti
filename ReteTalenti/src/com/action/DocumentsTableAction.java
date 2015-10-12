package com.action;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.DocumentsDao;
import com.model.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class DocumentsTableAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 123423L;
	
	private DocumentsDao dao = new DocumentsDao();

	/* jTable Variables */
	private List<Documents> records;
	private String result;
	private String message;
	private Documents record;
	private int totalRecordCount,jtStartIndex,jtPageSize;
	private String jtSorting;
	private HttpServletRequest servletRequest;

	/* Model member */
	private int documentId;
	private int courseId, moduleId;
	private String name, documentPath, documentName, oldDocument;
	
	
	public String list() {
		try {
			System.out.println("Calling list() method of class ModulesTableAction");
			// Fetch Data from SQL Table
			records = dao.getDocuments(courseId, moduleId, jtStartIndex,jtPageSize);
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
		String destPath = "documents/"+courseId+"/"+moduleId + "/" + documentPath;
		 
		record = new Documents();
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setName(name);
		record.setDocumentPath(destPath);
		
		try {
			// Create new record
			record.setDocumentId(dao.addDocuments(record));
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String update() {
		
		record = new Documents();
		String destPath = "documents/"+courseId+"/"+moduleId + "/";

		System.out.println("documentPath in Update() : " + documentPath);
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setDocumentId(documentId);
		record.setName(name);
		if (documentPath.startsWith(destPath))
			record.setDocumentPath(documentPath);
		else
			record.setDocumentPath(destPath+documentPath);
		
		try {
			// Create new record
			dao.updateDocuments(record);
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public String delete() {
		System.out.println("Entering delete method of class DocumentsTableAction");
		record = new Documents();
		record.setCourseId(courseId);
		record.setModuleId(moduleId);
		record.setDocumentId(documentId);
		String filePath1 = servletRequest.getSession().getServletContext().getRealPath("/");

		try {
			documentPath = dao.deleteDocuments(record);
			// Delete document from file system
			System.out.println("Document fold path: "+filePath1);
			System.out.println("Document to be deleted: "+documentPath);
			File file = new File(filePath1 + documentPath);
			file.delete();
			result = "OK";
		} catch (Exception e) {	
			result = "ERROR";
			message = e.getMessage();
			System.err.println("Error:" + e.getMessage());
		}
		return Action.SUCCESS;
	}

	public List<Documents> getRecords() {
		return records;
	}

	public void setRecords(List<Documents> records) {
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

	public Documents getRecord() {
		return record;
	}

	public void setRecord(Documents record) {
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

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
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

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getOldDocument() {
		return oldDocument;
	}

	public void setOldDocument(String oldDocument) {
		this.oldDocument = oldDocument;
	}


	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
	}
	
}