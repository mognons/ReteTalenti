package com.action;

import java.io.IOException;
import java.util.List;

import com.dao.EntiDao;
import com.interceptor.UserAware;
import com.model.Ente;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class EntiTableAction extends ActionSupport implements UserAware {

    private static final long serialVersionUID = 1L;
    private EntiDao dao = new EntiDao();
    private List<Ente> records;
    private String result;

    private String message;
    private Ente record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;
    //
    private int id, provincia_ente;
    private String descrizione, responsabile, resp_phone, resp_email;

    public String list() {
        try {
            // Fetch Data from Enti Table
            records = dao.getAllEnti(jtStartIndex, jtPageSize, jtSorting);
            result = "OK";
            totalRecordCount = dao.getEntiRecordCount();

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }


    public String create() throws IOException {
        record = new Ente();
        record.setDescrizione(descrizione);
        record.setResponsabile(responsabile);
        record.setResp_email(resp_email);
        record.setResp_phone(resp_phone);
        record.setProvincia_ente(provincia_ente);
        if (dao.verifyEnte(descrizione)) {
            try {
                System.out.println("Creating " + descrizione);
                //record.setId(0);
                dao.createEnte(record);
                result = "OK";
            } catch (Exception e) {
                message = e.getMessage();
				System.err.println("Porcaccia EVA");
                System.err.println(e.getMessage());
                result = "ERROR";
            }
        } else {
            System.out.println("Ente ESISTENTE!");
            message = "Impossibile creare un nuovo ente: descrizione ente <b>" + descrizione + "</b> già inserita";
            result = "ERROR";
        }
        return SUCCESS;
    }

    public String update() throws IOException {
        Ente record = new Ente();

        record.setId(id);
        record.setDescrizione(descrizione);
        record.setResponsabile(responsabile);
        record.setResp_email(resp_email);
        record.setResp_phone(resp_phone);
        record.setProvincia_ente(provincia_ente);
        System.out.println("Updating " + descrizione);

        try {
            // Update existing record
            dao.updateEnte(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String delete() throws IOException {
        System.out.println("Deleting descrizione " + id);
        try {
            // Update existing record
            dao.deleteEnte(id);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
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

    public void setJtPageSize(int jtPageSize) {
        this.jtPageSize = jtPageSize;
    }

    public int getJtPageSize() {
        return jtPageSize;
    }

    public String getJtSorting() {
        return jtSorting;
    }

    public void setJtSorting(String jtSorting) {
        this.jtSorting = jtSorting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ente getRecord() {
        return record;
    }

    public void setRecord(Ente record) {
        this.record = record;
    }

    public List<Ente> getRecords() {
        return records;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setRecords(List<Ente> records) {
        this.records = records;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getResp_phone() {
        return resp_phone;
    }

    public void setResp_phone(String resp_phone) {
        this.resp_phone = resp_phone;
    }

    public String getResp_email() {
        return resp_email;
    }

    public void setResp_email(String resp_email) {
        this.resp_email = resp_email;
    }

    public int getProvincia_ente() {
        return provincia_ente;
    }

    public void setProvincia_ente(int provincia_ente) {
        this.provincia_ente = provincia_ente;
    }


	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
