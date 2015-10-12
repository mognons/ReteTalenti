package com.action;
import com.utilities.*;
import com.utilities.ResultSetToExcel.FormatType;
import com.dao.CrudDao;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;

public class ReportAction extends ActionSupport {
	private static final long serialVersionUID = 1173542L;
	private CrudDao dao = new CrudDao();
	public String filename;
	public InputStream excelStream;

	public String getExcel() {
		FormatType formati[] = new FormatType[] {FormatType.INTEGER,FormatType.TEXT,FormatType.TEXT,FormatType.TEXT};
		ByteArrayOutputStream out;
		ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.getStudentsRS(),formati,"Report");
		filename = "report_test.xls";
		try {
			System.out.println("Inside getInputStream()");
			out = new ByteArrayOutputStream();
	        resultSetToExcel.generate(out);
	        excelStream = new ByteArrayInputStream(out.toByteArray());
	        out.close();
		}
		catch (Exception e) {
			e.printStackTrace(); //log to logs
		}

		return SUCCESS;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	

}