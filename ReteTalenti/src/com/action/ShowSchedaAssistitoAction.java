package com.action;
 
import java.util.ArrayList;
import java.util.List;

import com.dao.AssistitiDao;
import com.dao.EntiDao;
import com.dao.NoteAssistitoDao;
import com.dao.NucleiFamiliariDao;
import com.interceptor.UserAware;
import com.model.Assistito;
import com.model.Ente;
import com.model.NoteAssistito;
import com.model.NucleoFamiliare;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
 
public class ShowSchedaAssistitoAction extends ActionSupport implements UserAware, ModelDriven<User> {
 
    private static final long serialVersionUID = 8111120314704779336L;
    private User user;
    private String codice_fiscale, origin;
    private Ente ente_riferimento = new Ente();
    private Assistito assistito = new Assistito();
    private List<NucleoFamiliare> conviventi = new ArrayList<NucleoFamiliare>();
    private List<NoteAssistito> note = new ArrayList<NoteAssistito>();
    private AssistitiDao a_dao = new AssistitiDao();
    private EntiDao e_dao = new EntiDao();
    private NoteAssistitoDao n_dao = new NoteAssistitoDao();
    private NucleiFamiliariDao c_dao = new NucleiFamiliariDao();
 
    public String execute(){
    	user = new User();
    	assistito = a_dao.getAssistito(codice_fiscale);
    	conviventi = c_dao.getAllConviventi(0, 9999, "COGNOME ASC", codice_fiscale);
    	note = n_dao.getNoteAssistito(0, 9999, codice_fiscale);
    	ente_riferimento = e_dao.getEnte(assistito.getEnte_assistente());
        return SUCCESS;
    }
 
    @Override
    public void setUser(User user) {
        this.user=user;
    }
     
    public User getUser(User user){
        return this.user;
    }
 
    @Override
    public User getModel() {
        return this.user;
    }

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public User getUser() {
		return user;
	}

	public Assistito getAssistito() {
		return assistito;
	}

	public void setAssistito(Assistito assistito) {
		this.assistito = assistito;
	}


	public Ente getEnte_riferimento() {
		return ente_riferimento;
	}

	public void setEnte_riferimento(Ente ente_riferimento) {
		this.ente_riferimento = ente_riferimento;
	}




	public List<NucleoFamiliare> getConviventi() {
		return conviventi;
	}




	public void setConviventi(List<NucleoFamiliare> conviventi) {
		this.conviventi = conviventi;
	}




	public List<NoteAssistito> getNote() {
		return note;
	}




	public void setNote(List<NoteAssistito> note) {
		this.note = note;
	}
 
}