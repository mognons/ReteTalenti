package com.action;

import com.utilities.*;
import com.utilities.ResultSetToExcel.FormatType;
import com.dao.ReportDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;

public class ReportAction extends ActionSupport implements UserAware{

    private static final long serialVersionUID = 1173542L;
    private ReportDao dao = new ReportDao();
    public String filename;
    public InputStream excelStream;
    
    private User user;

    public String getExcel() {
        FormatType formati[] = new FormatType[]{FormatType.INTEGER, FormatType.TEXT, FormatType.TEXT, FormatType.TEXT};
        ByteArrayOutputStream out;
//		ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.getStudentsRS(),formati,"Report");
        filename = "report_test.xls";
        try {
            System.out.println("Inside getInputStream()");
            out = new ByteArrayOutputStream();
//	        resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
        }

        return SUCCESS;
    }

    public String anagraficaCompleta() {
        FormatType formati[] = new FormatType[]{
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.INTEGER, // IDB 
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT};
        ByteArrayOutputStream out;
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.anagraficaCompleta(), formati, "Report");
        filename = "anagrafica_completa.xls";
        try {
            System.out.println("Inside getInputStream() - anagraficaCompleta");
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            return ERROR;
        }

        return SUCCESS;
    }

    public String anagraficaEnteUser() {
        FormatType formati[] = new FormatType[]{
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.INTEGER, // IDB 
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT};
        ByteArrayOutputStream out;
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.anagraficaXEnteUser(user), formati, "Report");
        filename = "anagrafica_xenteuser.xls";
        try {
            System.out.println("Inside getInputStream() - anagraficaXEnteUser");
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            return ERROR;
        }

        return SUCCESS;
    }
    
    public String anagraficaXProvinciaEnteUser() {
        FormatType formati[] = new FormatType[]{
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.INTEGER, // IDB 
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT};
        ByteArrayOutputStream out;
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.anagraficaXProvinciaEnteUser(user), formati, "Report");
        filename = "anagrafica_xprovinciaenteuser.xls";
        try {
            System.out.println("Inside getInputStream() - anagraficaXProvinciaEnteUser");
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            return ERROR;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
