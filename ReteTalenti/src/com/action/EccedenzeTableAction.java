package com.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.dao.EccedenzeDao;
import com.dao.EntiDao;
import com.dao.Uni_misuraDao;
import com.interceptor.UserAware;
import com.model.Eccedenza;
import com.model.Ente;
import com.model.Message;
import com.model.Uni_misura;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.utilities.sendMail;

public class EccedenzeTableAction extends ActionSupport implements UserAware, ModelDriven<User> {

    private static final long serialVersionUID = 1L;
    private EccedenzeDao dao = new EccedenzeDao();
    private EntiDao e_dao = new EntiDao();
    private Uni_misuraDao u_dao = new Uni_misuraDao();
    private List<Eccedenza> records;
    private List<Ente> entiDestinatari;
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
    	jtSorting = "SCADENZA ASC";
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

    public String listAvailable() {
    	jtSorting = "SCADENZA ASC";
        try {
            // Fetch Data from Enti Table
            records = dao.getAvailableEccedenze(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountAvailableEccedenze(user);

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String create() throws IOException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Uni_misura udm1 = u_dao.getUni_misuraById(udm);
        String mailRecipient;
        Ente enteCedente = e_dao.getEnte(user.getEnte());
		ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
		Boolean invioEmail = "true".equalsIgnoreCase(rb.getString("sendEccedenze"));
        String message_text = 	
        		user.getDescrizioneEnte() + 
        		" ha segnalato un'eccedenza di <b>"
				+ prodotto + "</b> per un totale di <b>"
				+ qta+ " " + udm1.getDescrizione() + "</b> con scadenza "
				+ sdf.format(scadenza) + ".\n"
				+ "Contattare il responsabile <i>" + enteCedente.getResponsabile()
				+ "</i> al numero di telefono <b>" + enteCedente.getResp_phone()
				+ "</b> oppure via email all'indirizzo <b>" + enteCedente.getResp_email() + "</b>.";
        
    	String mail_body = message_text.replaceAll("\\<[^>]*>","");
    	mail_body = mail_body + "\n\nMessaggio inviato automaticamente dal sistema ReteTalenti.";
        record = new Eccedenza();
        record.setEnte_cedente(user.getEnte());
        record.setProdotto(prodotto);
        record.setUdm(udm);
        record.setQta(qta);
        record.setQta_residua(qta);
        record.setScadenza(scadenza);
        record.setOperatore(user.getUsername());
    	MessageAction mess = new MessageAction();
    	sendMail sm = new sendMail();
        try {
            record.setId(dao.createEccedenza(record));
            entiDestinatari = e_dao.getOtherEnti(user, false);
            Iterator<Ente> enti = entiDestinatari.iterator();
            while (enti.hasNext()) {
            	Message messaggio = new Message();
            	Ente enteDestinatario = enti.next();
            	messaggio.setEnte(enteDestinatario.getId());
            	messaggio.setTag("ECCEDENZE");
            	messaggio.setAction("FOLLOW_impegniLink.action");
            	messaggio.setMessage_text(message_text);
            	messaggio.setKey1(null);
            	messaggio.setKey2(0);
            	messaggio.setKey3(null);
            	messaggio.setStart_date(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            	messaggio.setEnd_date(scadenza);
            	mess.createMessage(messaggio);
            	mailRecipient = enteDestinatario.getResp_email();
            	/* Invio email */
            	try {
            		if (invioEmail)
        	    		sm.send("Segnalazione eccedenza", mail_body, mailRecipient);
            	} catch (Exception e) {
            		//
            	}
            }
            result = "OK";
        } catch (Exception e) {
            message = e.getMessage();
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
        record.setQta_residua(qta);
        record.setScadenza(scadenza);
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
