package com.action;

import java.io.IOException;
import java.util.List;

import com.dao.Stati_civiliDao;
import com.interceptor.UserAware;

import com.model.Stato_civile;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class Stati_civiliTableAction extends ActionSupport implements UserAware{
	
	private static final long serialVersionUID = 1L;
    private Stati_civiliDao dao = new Stati_civiliDao();
    private List<Stato_civile> records;
    private String result;
    //
    private String message;
	private Stato_civile record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;
    //
    private int id;
    private String descrizione;
    private User user = new User();
    
    public String list() {
    	jtSorting = "DESCRIZIONE ASC";
        try {
            // Fetch Data from Enti Table
            records = dao.getAllStati_civili(jtStartIndex, jtPageSize, jtSorting);
            result = "OK";
            totalRecordCount = dao.getStati_civiliRecordCount();

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
    
    public String create() throws IOException {
        record = new Stato_civile();
        record.setDescrizione(descrizione);
      
        if (dao.verifyStato_civile(descrizione)) {
            try {
                record.setId(dao.createStato_civile(record));
                result = "OK";
            } catch (Exception e) {
                message = e.getMessage();
                System.err.println(e.getMessage());
                result = "ERROR";
            }
        } else {
            message = "Impossibile creare un nuovo ststo civile: descrizione stato civile <b>" + descrizione + "</b> già inserita";
            result = "ERROR";
        }
        return SUCCESS;
    }
    public String update() throws IOException {
        record = new Stato_civile();

        record.setId(id);
        record.setDescrizione(descrizione);
       

        try {
            // Update existing record
            dao.updateStato_civile(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
    
    public String delete() throws IOException {
        try {
            // Update existing record
            dao.deleteStato_civile(id);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
    }

	public Stati_civiliDao getDao() {
		return dao;
	}

	public void setDao(Stati_civiliDao dao) {
		this.dao = dao;
	}

	public List<Stato_civile> getRecords() {
		return records;
	}

	public void setRecords(List<Stato_civile> records) {
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

	public Stato_civile getRecord() {
		return record;
	}

	public void setRecord(Stato_civile record) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    

    

}
