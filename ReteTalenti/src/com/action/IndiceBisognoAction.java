package com.action;

import com.dao.IndiceBisognoDao;
import com.interceptor.UserAware;
import com.model.IndiceBisogno;
import com.model.User;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author gminardi
 */
public class IndiceBisognoAction extends ActionSupport implements UserAware {
    
    private static final long serialVersionUID = 1L;
    private IndiceBisognoDao dao = new IndiceBisognoDao();
    private String result;
    private String message;
    private IndiceBisogno recordIDB;
    private String jtSorting;
    //
    private int id;
    private int isee_euro;
    private int cc_euro;
    private int ca_euro;
    private int cs_euro;
    private int stato_disoc;
    private int spese_imp;
    private int urgenza;
    
    private int isee_punti;
    private int entrate_nc_punti;
    private int stato_disoc_punti;
    private int spese_imp_punti;
    private int urgenza_punti;
    private int totalepunti;
    private String cf_assistito_ib, nome, cognome;
    private Date data_inserimento;
    
    private User user = new User();
    
    public String getData() {
    	recordIDB = dao.getIndiceBisogno(cf_assistito_ib);
    	if (recordIDB.getCf_assistito_ib() != null) {
    	} else {
			recordIDB.setIsee_euro(0);
			recordIDB.setCc_euro(0);
			recordIDB.setCa_euro(0);
			recordIDB.setCs_euro(0);
			recordIDB.setStato_disoc(0);
			recordIDB.setSpese_imp(0);
			recordIDB.setUrgenza(0);
			recordIDB.setId(0);
			recordIDB.setIsee_punti(0);
			recordIDB.setEntrate_nc_punti(0);
			recordIDB.setStato_disoc_punti(0);
			recordIDB.setSpese_imp_punti(0);
			recordIDB.setUrgenza_punti(0);
			recordIDB.setTotalepunti(0);
			recordIDB.setCf_assistito_ib(cf_assistito_ib);
    	}
         return SUCCESS;
    }

    
    public String update() throws IOException {
        recordIDB = new IndiceBisogno();
        recordIDB.setId(id);
        recordIDB.setIsee_euro(isee_euro);
        recordIDB.setCa_euro(ca_euro);
        recordIDB.setCc_euro(cc_euro);
        recordIDB.setCs_euro(cs_euro);
        recordIDB.setSpese_imp(spese_imp);
        recordIDB.setStato_disoc(stato_disoc);
        recordIDB.setUrgenza(urgenza);
        recordIDB.setCf_assistito_ib(cf_assistito_ib);
        recordIDB.setEntrate_nc_punti(entrate_nc_punti);
        recordIDB.setSpese_imp_punti(spese_imp_punti);
        recordIDB.setStato_disoc_punti(stato_disoc_punti);
        recordIDB.setUrgenza_punti(urgenza_punti);
        recordIDB.setIsee_punti(isee_punti);
        recordIDB.setTotalepunti(totalepunti);
        if (id==0) {
            try {
                // Insert INTO recordIDB
                dao.createIndiceBisogno(recordIDB);
                result = "OK";
            } catch (Exception e) {
                result = "ERROR";
                message = e.getMessage();
                System.err.println("Eccezione a riga 100: IndiceBisognoAction");
            }
        } else {
            try {
                // Update existing recordIDB
                dao.updateIndiceBisogno(recordIDB);
                result = "OK";
            } catch (Exception e) {
                result = "ERROR";
                message = e.getMessage();
                System.err.println(e.getMessage());
            }
        }
        return SUCCESS;
    }
    
    
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    
    public IndiceBisognoDao getDao() {
        return dao;
    }
    
    public void setDao(IndiceBisognoDao dao) {
        this.dao = dao;
    }
    
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public IndiceBisogno getrecordIDB() {
        return recordIDB;
    }
    
    public void setrecordIDB(IndiceBisogno recordIDB) {
        this.recordIDB = recordIDB;
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
    
    public int getIsee_euro() {
        return isee_euro;
    }
    
    public void setIsee_euro(int isee_euro) {
        this.isee_euro = isee_euro;
    }
    
    public int getCc_euro() {
        return cc_euro;
    }
    
    public void setCc_euro(int cc_euro) {
        this.cc_euro = cc_euro;
    }
    
    public int getCa_euro() {
        return ca_euro;
    }
    
    public void setCa_euro(int ca_euro) {
        this.ca_euro = ca_euro;
    }
    
    public int getCs_euro() {
        return cs_euro;
    }
    
    public void setCs_euro(int cs_euro) {
        this.cs_euro = cs_euro;
    }
    
    public int getStato_disoc() {
        return stato_disoc;
    }
    
    public void setStato_disoc(int stato_disoc) {
        this.stato_disoc = stato_disoc;
    }
    
    public int getSpese_imp() {
        return spese_imp;
    }
    
    public void setSpese_imp(int spese_imp) {
        this.spese_imp = spese_imp;
    }

    public int getUrgenza() {
        return urgenza;
    }

    public void setUrgenza(int urgenza) {
        this.urgenza = urgenza;
    }
    
    
    public int getIsee_punti() {
        return isee_punti;
    }
    
    public void setIsee_punti(int isee_punti) {
        this.isee_punti = isee_punti;
    }
    
    public int getEntrate_nc_punti() {
        return entrate_nc_punti;
    }
    
    public void setEntrate_nc_punti(int entrate_nc_punti) {
        this.entrate_nc_punti = entrate_nc_punti;
    }
    
    public int getStato_disoc_punti() {
        return stato_disoc_punti;
    }
    
    public void setStato_disoc_punti(int stato_disoc_punti) {
        this.stato_disoc_punti = stato_disoc_punti;
    }
    
    public int getSpese_imp_punti() {
        return spese_imp_punti;
    }
    
    public void setSpese_imp_punti(int spese_imp_punti) {
        this.spese_imp_punti = spese_imp_punti;
    }
    
    public int getUrgenza_punti() {
        return urgenza_punti;
    }
    
    public void setUrgenza_punti(int urgenza_punti) {
        this.urgenza_punti = urgenza_punti;
    }
    
    public int getTotalepunti() {
        return totalepunti;
    }
    
    public void setTotalepunti(int totalepunti) {
        this.totalepunti = totalepunti;
    }
    
    public String getCf_assistito_ib() {
        return cf_assistito_ib;
    }
    
    public void setCf_assistito_ib(String cf_assistito_ib) {
        this.cf_assistito_ib = cf_assistito_ib;
    }
    
    public Date getData_inserimento() {
        return data_inserimento;
    }
    
    public void setData_inserimento(Date data_inserimento) {
        this.data_inserimento = data_inserimento;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

	public User getUser() {
		return user;
	}


	public IndiceBisogno getRecordIDB() {
		return recordIDB;
	}


	public void setRecordIDB(IndiceBisogno recordIDB) {
		this.recordIDB = recordIDB;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
    
}
