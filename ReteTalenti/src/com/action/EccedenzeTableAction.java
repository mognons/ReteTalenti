package com.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.dao.EccedenzeDao;
import com.interceptor.UserAware;
import com.model.Eccedenza;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class EccedenzeTableAction extends ActionSupport implements UserAware, ModelDriven<User> {

    private static final long serialVersionUID = 1L;
    private EccedenzeDao dao = new EccedenzeDao();
    private List<Eccedenza> records;
    private String result;

    private String message;
    private Eccedenza record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;
    //
    private int id, ente_cedente, udm, qta, qta_residua;
    private String prodotto, operatore;
    private java.sql.Date scadenza;
    private java.util.Date timestamp;
    private User user = new User();

    public String listOwn() {
    	jtSorting = "PRODOTTO ASC";
        try {
            // Fetch Data from Enti Table
            records = dao.getOwnEccedenze(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountOwnEccedenze(user);

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String listOthers() {
    	jtSorting = "PRODOTTO ASC";
        try {
            // Fetch Data from Enti Table
            records = dao.getOwnEccedenze(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountOwnEccedenze(user);

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }


    public String create() throws IOException {
        record = new Eccedenza();
        record.setEnte_cedente(user.getEnte());
        record.setProdotto(prodotto);
        record.setUdm(udm);
        record.setQta(qta);
        record.setScadenza(scadenza);
        record.setOperatore(user.getUsername());
            try {
                System.out.println("Creating eccedenza for " + prodotto);
                record.setId(dao.createEccedenza(record));
                result = "OK";
            } catch (Exception e) {
                message = e.getMessage();
				System.err.println("Porcaccia EVA");
                System.err.println(e.getMessage());
                result = "ERROR";
            }
        return SUCCESS;
    }

    public String update() throws IOException {
        record = new Eccedenza();

        record.setId(id);
        record.setEnte_cedente(ente_cedente);
        record.setProdotto(prodotto);
        record.setUdm(udm);
        record.setQta(qta);
        record.setScadenza(scadenza);
        System.out.println("Updating eccedenza for " + prodotto);

        try {
            // Update existing record
            dao.updateEccedenza(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String delete() throws IOException {
        System.out.println("Deleting eccedenza " + id);
        record = new Eccedenza();
        record.setId(id);
        try {
            dao.deleteEccedenza(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
    }

    
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}


	public List<Eccedenza> getRecords() {
		return records;
	}


	public void setRecords(List<Eccedenza> records) {
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


	public Eccedenza getRecord() {
		return record;
	}


	public void setRecord(Eccedenza record) {
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


	public int getEnte_cedente() {
		return ente_cedente;
	}


	public void setEnte_cedente(int ente_cedente) {
		this.ente_cedente = ente_cedente;
	}


	public int getUdm() {
		return udm;
	}


	public void setUdm(int udm) {
		this.udm = udm;
	}


	public int getQta() {
		return qta;
	}


	public void setQta(int qta) {
		this.qta = qta;
	}


	public String getProdotto() {
		return prodotto;
	}


	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}


	public Date getScadenza() {
		return scadenza;
	}


	public void setScadenza(java.sql.Date scadenza) {
		this.scadenza = scadenza;
	}


	public String getOperatore() {
		return operatore;
	}


	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getQta_residua() {
		return qta_residua;
	}

	public void setQta_residua(int qta_residua) {
		this.qta_residua = qta_residua;
	}

}
