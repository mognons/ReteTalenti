package com.action;

import com.utilities.*;
//import com.utilities.RSToExcel.FormatType;
import com.utilities.RSToExcel.FormatType;
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

    public String anagraficaCompleta() {
        FormatType formati[] = new FormatType[]{
            FormatType.TEXT, //cf
            FormatType.TEXT, // Nome
            FormatType.TEXT, // Cognome
            FormatType.TEXT, // Genere
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT, // Data di nascita
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT, // CAP
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT, // Documento
            FormatType.TEXT, // Ente Assistente
            FormatType.TEXT, // Provincia Ente
            FormatType.TEXT, 
            FormatType.TEXT,
            FormatType.INTEGER, // IDB 
            FormatType.TEXT, // Emporio
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT,
            FormatType.TEXT};
        ByteArrayOutputStream out;
        RSToExcel RSToExcel = new RSToExcel(dao.anagraficaCompleta(), formati, "Report");
        filename = "anagrafica_completa.xls";
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
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
                FormatType.TEXT, //cf
                FormatType.TEXT, // Nome
                FormatType.TEXT, // Cognome
                FormatType.TEXT, // Genere
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Data di nascita
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // CAP
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Documento
                FormatType.TEXT, // Ente Assistente
                FormatType.TEXT, // Provincia Ente
                FormatType.TEXT, 
                FormatType.TEXT,
                FormatType.INTEGER, // IDB 
                FormatType.TEXT, // Emporio
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT};
        ByteArrayOutputStream out;
        RSToExcel RSToExcel = new RSToExcel(dao.anagraficaXEnteUser(user), formati, "Report");
        filename = "anagrafica_ente.xls";
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
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
                FormatType.TEXT, //cf
                FormatType.TEXT, // Nome
                FormatType.TEXT, // Cognome
                FormatType.TEXT, // Genere
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Data di nascita
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // CAP
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Documento
                FormatType.TEXT, // Ente Assistente
                FormatType.TEXT, // Provincia Ente
                FormatType.TEXT, 
                FormatType.TEXT,
                FormatType.INTEGER, // IDB 
                FormatType.TEXT, // Emporio
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT};
        ByteArrayOutputStream out;
        RSToExcel RSToExcel = new RSToExcel(dao.anagraficaXProvinciaEnteUser(user), formati, "Report");
        filename = "anagrafica_provinciale.xls";
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
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
                FormatType.TEXT, //cf
                FormatType.TEXT, // Nome
                FormatType.TEXT, // Cognome
                FormatType.TEXT, // Genere
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Data di nascita
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // CAP
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT, // Documento
                FormatType.TEXT, // Ente Assistente
                FormatType.TEXT, // Provincia Ente
                FormatType.TEXT, 
                FormatType.TEXT,
                FormatType.INTEGER, // IDB 
                FormatType.TEXT, // Emporio
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT};
        ByteArrayOutputStream out;
        RSToExcel RSToExcel = new RSToExcel(dao.graduatoriaProvinciale(user), formati, "Graduatoria");
        filename = "graduatoria_provinciale.xls";
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
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

    public String situazioneEccedenze() {
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
        RSToExcel RSToExcel = new RSToExcel(dao.eccedenze(), formati,"Eccedenze");
        FormatType formati_r[] = new FormatType[]{
                FormatType.INTEGER,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.INTEGER,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT,
                FormatType.TEXT};

        RSToExcel.addRSToExcel(dao.ritiri(), formati_r,"Ritiri");
        filename = "situazione_eccedenze.xls";
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
            formati = null;
            return ERROR;
        }
        formati = null;
        formati_r = null;
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
