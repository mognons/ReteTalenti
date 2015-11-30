package com.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.dao.EccedenzeDao;
import com.dao.ImpegniDao;
import com.interceptor.UserAware;
import com.model.Eccedenza;
import com.model.Impegno;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ImpegniTableAction extends ActionSupport implements UserAware, ModelDriven<User> {

    private static final long serialVersionUID = 1L;
    private ImpegniDao dao = new ImpegniDao();
    private List<Impegno> records;
    private String result;

    private String message;
    private Impegno record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;
    //
    private int id, id_eccedenza, ente_richiedente, qta_prenotata, qta_residua;
    private String prodotto, operatore, ora_ritiro;
    private java.sql.Date scadenza, data_ritiro;
    private java.util.Date timestamp;
    private boolean ritiro_effettuato;
    private User user = new User();

    public String listByEccedenza() {
    	jtSorting = "DATA_RITIRO ASC";
        try {
            // Fetch Data from Enti Table
            records = dao.getAllImpegniByEccedenza(jtStartIndex, jtPageSize, jtSorting, id_eccedenza);
            result = "OK";
            totalRecordCount = dao.getCountImpegniByEccedenza(id_eccedenza);
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String listOwnByEccedenza() {
    	jtSorting = "DATA_RITIRO DESC";
        try {
            // Fetch Data from Enti Table
            records = dao.getOwnImpegniByEccedenza(jtStartIndex, jtPageSize, jtSorting, user, id_eccedenza);
            result = "OK";
            totalRecordCount = dao.getCountOwnImpegniByEccedenza(user, id_eccedenza);

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }


    public String create() throws IOException {
        record = new Impegno();
        record.setId_eccedenza(id_eccedenza);
        record.setEnte_richiedente(user.getEnte());
        record.setQta_prenotata(qta_prenotata);
        record.setRitiro_effettuato(false);
        record.setData_ritiro(data_ritiro);
        record.setOra_ritiro(ora_ritiro);
        record.setOperatore(user.getUsername());
            try {
                record.setId(dao.createImpegno(record));
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
        record = new Impegno();
        record.setId(id);
        record.setId_eccedenza(id_eccedenza);
        record.setEnte_richiedente(ente_richiedente);
        record.setQta_prenotata(qta_prenotata);
        record.setRitiro_effettuato(false);
        record.setData_ritiro(data_ritiro);
        record.setOra_ritiro(ora_ritiro);
        try {
            // Update existing record
            dao.updateImpegno(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String updateRitiro() throws IOException {
        record = new Impegno();
        record.setId(id);
        record.setId_eccedenza(id_eccedenza);
        record.setEnte_richiedente(ente_richiedente);
        record.setQta_prenotata(qta_prenotata);
        record.setOra_ritiro(ora_ritiro);
        record.setData_ritiro(data_ritiro);
        record.setRitiro_effettuato(ritiro_effettuato);
        try {
            // Update existing record
            dao.updateRitiroImpegno(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String delete() throws IOException {
        record = new Impegno();
        record.setId(id);
        try {
            dao.deleteImpegno(record);
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

	public int getId_eccedenza() {
		return id_eccedenza;
	}

	public void setId_eccedenza(int id_eccedenza) {
		this.id_eccedenza = id_eccedenza;
	}

	public int getEnte_richiedente() {
		return ente_richiedente;
	}

	public void setEnte_richiedente(int ente_richiedente) {
		this.ente_richiedente = ente_richiedente;
	}

	public int getQta_prenotata() {
		return qta_prenotata;
	}

	public void setQta_prenotata(int qta_prenotata) {
		this.qta_prenotata = qta_prenotata;
	}

	public java.sql.Date getData_ritiro() {
		return data_ritiro;
	}

	public void setData_ritiro(java.sql.Date data_ritiro) {
		this.data_ritiro = data_ritiro;
	}

	public boolean isRitiro_effettuato() {
		return ritiro_effettuato;
	}

	public void setRitiro_effettuato(boolean ritiro_effettuato) {
		this.ritiro_effettuato = ritiro_effettuato;
	}

	public User getUser() {
		return user;
	}

	public void setRecord(Impegno record) {
		this.record = record;
	}

	public List<Impegno> getRecords() {
		return records;
	}

	public void setRecords(List<Impegno> records) {
		this.records = records;
	}

	public Impegno getRecord() {
		return record;
	}

	public String getOra_ritiro() {
		return ora_ritiro;
	}

	public void setOra_ritiro(String ora_ritiro) {
		this.ora_ritiro = ora_ritiro;
	}

}
