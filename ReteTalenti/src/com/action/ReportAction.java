package com.action;

import com.utilities.*;
import com.utilities.ResultSetToExcel.FormatType;
import com.dao.ReportDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;

public class ReportAction extends ActionSupport implements UserAware {

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
            out = new ByteArrayOutputStream();
//	        resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
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
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
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
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
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
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
        return SUCCESS;
    }

    public String graduatoriaProvinciale() {
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
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.graduatoriaProvinciale(user), formati, "Report");
        filename = "graduatoria_provinciale.xls";
        try {
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
        return SUCCESS;
    }

    public String ritiriCompleto() {

        FormatType formati[] = new FormatType[]{
            FormatType.INTEGER,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.INTEGER,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT
        };
        ByteArrayOutputStream out;
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.ritiriCompleto(), formati, "Report");
        filename = "ritiri_completo.xls";
        try {
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
        return SUCCESS;
    }
    
    public String eccedenze() {
        FormatType formati[] = new FormatType[]{
            FormatType.INTEGER, 
            FormatType.TEXT, 
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.INTEGER,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT
     };
            
        ByteArrayOutputStream out;
        ResultSetToExcel resultSetToExcel = new ResultSetToExcel(dao.eccedenze(),formati,"Report");
        filename = "eccedenze.xls";
        try {
            out = new ByteArrayOutputStream();
            resultSetToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
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
