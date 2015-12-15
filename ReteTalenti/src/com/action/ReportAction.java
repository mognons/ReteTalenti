package com.action;

import com.utilities.*;
//import com.utilities.RSToExcel.FormatType;
import com.utilities.RSToExcel.FormatType;
import com.dao.ReportDao;
import com.interceptor.UserAware;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;
import java.util.ResourceBundle;

public class ReportAction extends ActionSupport implements UserAware {

    private static final long serialVersionUID = 1173542L;
    private ReportDao dao = new ReportDao();
    private String errorMessage;
    private Boolean Assistiti = false;
    private Boolean NoteAssistiti = false;
    private Boolean Eccedenze = false;
    private Boolean Utenti = false;
    private Boolean Reference = false;
    private int ente;
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
        	errorMessage = "Error generating report because " + e.getMessage();
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
        	errorMessage = "Error generating report because " + e.getMessage();
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
        	errorMessage = "Error generating report because " + e.getMessage();
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
        	errorMessage = "Error generating report because " + e.getMessage();
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
        	errorMessage = "Error generating report because " + e.getMessage();
        	return ERROR;
        }
        formati = null;
        formati_r = null;
        return SUCCESS;
    }
    
    public String export() {
    	Boolean first = true;
    	
    	ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
		int exportPageSize = Integer.parseInt(rb.getString("exportPageSize"));//LIMIT
    	int totRowNum = dao.getCountNoteAssistiti();
    	
    	int numFogli = totRowNum%exportPageSize>0 ? (totRowNum / exportPageSize)+1:(totRowNum / exportPageSize);
    	
    	RSToExcel RSToExcel = null;
    	System.out.println("Filename : "+ filename);
    	if (!filename.contains(".xls")) {
    		filename = filename.concat(".xls");
    	}
    	System.out.println("Filename : "+ filename);
        ByteArrayOutputStream out;
              
        
        if (Assistiti) {
        	if (first) {
                RSToExcel = new RSToExcel(dao.tabellaAssistiti(ente), "Assistiti");
                first = false;
        	} else {
                RSToExcel.addRSToExcel(dao.tabellaAssistiti(ente), "Assistiti");        		
        	}
            RSToExcel.addRSToExcel(dao.tabellaNucleoFamiliare(ente), "NucleiFamiliari");
            RSToExcel.addRSToExcel(dao.tabellaIndiciBisogno(ente), "IndiciBisogno");
            int myoffset=0;
        	for (int i = 0; i < numFogli; i++) {
        		myoffset=(exportPageSize*i)+1;
        		int k = i+1;
        		RSToExcel.addRSToExcel(dao.tabellaNoteAssistiti(ente, exportPageSize, myoffset),"NoteAssistiti_" + k);
        	}
        }
        if (Eccedenze) {
        	if (first) {
                RSToExcel = new RSToExcel(dao.tabellaEccedenze(ente), "Eccedenze");
                first = false;
        	} else {
                RSToExcel.addRSToExcel(dao.tabellaEccedenze(ente), "Eccedenze");        		
        	}
            RSToExcel.addRSToExcel(dao.tabellaImpegni(ente), "Impegni");
        }
        if (Utenti) {
        	if (first) {
                RSToExcel = new RSToExcel(dao.tabellaUsers(ente), "Users");
                first = false;
        	} else {
                RSToExcel.addRSToExcel(dao.tabellaUsers(ente), "Users");        		
        	}
            RSToExcel.addRSToExcel(dao.tabellaGroups(), "Groups");
        }
        if (Reference) {
        	if (first) {
                RSToExcel = new RSToExcel(dao.tabellaEnti(), "Enti");
                first = false;
        	} else {
                RSToExcel.addRSToExcel(dao.tabellaEnti(), "Enti");        		
        	}
            RSToExcel.addRSToExcel(dao.tabellaMessages(), "Messages");
            RSToExcel.addRSToExcel(dao.tabellaStatiCivili(), "StatiCivili");
            RSToExcel.addRSToExcel(dao.tabellaGradiParentela(), "GradiParentela");
            RSToExcel.addRSToExcel(dao.tabellaNazioni(), "Nazioni");
            RSToExcel.addRSToExcel(dao.tabellaProvince(), "Province");
            RSToExcel.addRSToExcel(dao.tabellaUniMisura(), "UniMisura");
        }
        try {
            out = new ByteArrayOutputStream();
            RSToExcel.generate(out);
            excelStream = new ByteArrayInputStream(out.toByteArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace(); //log to logs
        	errorMessage = "Error generating export because " + e.getMessage();
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getAssistiti() {
		return Assistiti;
	}

	public void setAssistiti(Boolean assistiti) {
		Assistiti = assistiti;
	}

	public Boolean getEccedenze() {
		return Eccedenze;
	}

	public void setEccedenze(Boolean eccedenze) {
		Eccedenze = eccedenze;
	}

	public Boolean getUtenti() {
		return Utenti;
	}

	public void setUtenti(Boolean utenti) {
		Utenti = utenti;
	}

	public Boolean getReference() {
		return Reference;
	}

	public void setReference(Boolean reference) {
		Reference = reference;
	}

	public int getEnte() {
		return ente;
	}

	public void setEnte(int ente) {
		this.ente = ente;
	}

}