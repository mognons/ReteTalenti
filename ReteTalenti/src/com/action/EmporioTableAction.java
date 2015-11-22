/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.action;

import com.dao.AssistitiDao;
import com.action.NoteTableAction;
import com.interceptor.UserAware;
import com.model.Assistito;
import com.model.Tuple;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author gminardi
 */
public class EmporioTableAction extends ActionSupport implements UserAware, ModelDriven<User>{

    private AssistitiDao dao = new AssistitiDao();
    
    private List<Assistito> records;
    private String result;
    private String message;
    private Assistito record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;

    private User user = new User();
    private NoteTableAction noteAction;
//

    private static final long serialVersionUID = 1L;
    private String cf_search, cognome_search;
    private String origin;
    Boolean status;
    private String cod_fiscale;
    private String nome;
    private String cognome;
    private String sesso;
    private String luogo_nascita;
    private Date data_nascita;

    private String nazionalita; //codice nazione fk codice della tabella nazioni varchar (4)
    private String denominazione;

    private String indirizzo_residenza;
    private String citta_residenza;
    private String cap;

    private int provincia, stato_civile;
    private String sigla_autom;

    private String permesso_soggiorno;
    private String telefono;
    private String email;
    private String num_documento;
    private String accettazione, scadenza;

    private int ente_assistente; //fk ID della tabella enti INT(11)
    private String descrizione;

    private int punteggio_idb, emporio;
    private Date data_inserimento;
    private Date data_fine_assistenza;
    private Date data_candidatura;
    private Date data_accettazione;
    private Date data_scadenza;
    private Date data_dismissione;

    private int operatore;
    private String username;

    public String graduatoria() {
        try {
            // Fetch Data from Assistiti Table
        	System.out.println("Getting graduatoria...");
            records = dao.getGraduatoria(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountGraduatoria(user);
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
   
    public String candidati() {
        try {
            // Fetch Data from Assistiti Table
        	System.out.println("Getting candidati...");
            records = dao.getCandidati(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountCandidati(user);
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
   
    public String inseriti() {
        try {
            // Fetch Data from Assistiti Table
        	System.out.println("Getting inseriti...");
            records = dao.getInseriti(jtStartIndex, jtPageSize, jtSorting, user);
            result = "OK";
            totalRecordCount = dao.getCountInseriti(user);
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
   
    public String candida() throws IOException {
        System.out.println("Candidatura assistito con codice fiscale " + cod_fiscale);
        record = new Assistito();
        record.setCod_fiscale(cod_fiscale);
        try {
            dao.candidaAssistito(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
    }

    public String rimuoviCandidatura() throws IOException {
        System.out.println("Rimozione candidatura assistito con codice fiscale " + cod_fiscale);
        record = new Assistito();
        record.setCod_fiscale(cod_fiscale);
        try {
            dao.rimuoviCandidaturaAssistito(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
        
    }

    public String EmporioOut() throws IOException {
        System.out.println("Rimozione da EMPORIO per assistito con codice fiscale " + cod_fiscale);
        record = new Assistito();
        record.setCod_fiscale(cod_fiscale);
        noteAction = new NoteTableAction();
        String nota = 	"Rimosso dall'emporio (accettato il "
						+ accettazione 
						+ " fino al " + 
						scadenza + ")";
        try {
            dao.rimuoviEmporioAssistito(record);
            noteAction.annotazione(cod_fiscale, nota);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
        
    }

    public String EmporioIn() throws IOException {
        System.out.println("Inserimento in EMPORIO per assistito con codice fiscale " + cod_fiscale);
        record = new Assistito();
        record.setCod_fiscale(cod_fiscale);
        record.setEmporio(emporio);
        record.setData_accettazione(data_accettazione);
        record.setData_scadenza(data_scadenza);
        System.err.println("dopo la record.set....");
        String nota = 	"Inserito in emporio dal " + 
        				dateToString(data_accettazione) 
        				+ " al " + 
        				dateToString(data_scadenza);
        System.out.println(nota);
        try {
            dao.inserisciEmporioAssistito(record);
            noteAction.annotazione(cod_fiscale, nota);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
    }

    public String dateToString(Date indate) {
    	String dateString = null;
    	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
    	try {
    		dateString = sdfr.format(indate);
    	} catch (Exception ex ) {
    		System.out.println(ex);
    	}
    	return dateString;
    }
    
    public AssistitiDao getDao() {
        return dao;
    }

    public void setDao(AssistitiDao dao) {
        this.dao = dao;
    }

    public List<Assistito> getRecords() {
        return records;
    }

    public void setRecords(List<Assistito> records) {
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

    public Assistito getRecord() {
        return record;
    }

    public void setRecord(Assistito record) {
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

    public String getCod_fiscale() {
        return cod_fiscale;
    }

    public void setCod_fiscale(String cod_fiscale) {
        this.cod_fiscale = cod_fiscale;
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

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public int getStato_civile() {
        return stato_civile;
    }

    public void setStato_civile(int stato_civile) {
        this.stato_civile = stato_civile;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getIndirizzo_residenza() {
        return indirizzo_residenza;
    }

    public void setIndirizzo_residenza(String indirizzo_residenza) {
        this.indirizzo_residenza = indirizzo_residenza;
    }

    public String getCitta_residenza() {
        return citta_residenza;
    }

    public void setCitta_residenza(String citta_residenza) {
        this.citta_residenza = citta_residenza;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public String getSigla_autom() {
        return sigla_autom;
    }

    public void setSigla_autom(String sigla_autom) {
        this.sigla_autom = sigla_autom;
    }

    public String getPermesso_soggiorno() {
        return permesso_soggiorno;
    }

    public void setPermesso_soggiorno(String permesso_soggiorno) {
        this.permesso_soggiorno = permesso_soggiorno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_documento() {
        return num_documento;
    }

    public void setNum_documento(String num_documento) {
        this.num_documento = num_documento;
    }

    public int getEnte_assistente() {
        return ente_assistente;
    }

    public void setEnte_assistente(int ente_assistente) {
        this.ente_assistente = ente_assistente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData_inserimento() {
        return data_inserimento;
    }

    public void setData_inserimento(Date data_inserimento) {
        this.data_inserimento = data_inserimento;
    }

    public Date getData_fine_assistenza() {
        return data_fine_assistenza;
    }

    public void setData_fine_assistenza(Date data_fine_assistenza) {
        this.data_fine_assistenza = data_fine_assistenza;
    }

    public Date getData_candidatura() {
        return data_candidatura;
    }

    public void setData_candidatura(Date data_candidatura) {
        this.data_candidatura = data_candidatura;
    }

    public Date getData_accettazione() {
        return data_accettazione;
    }

    public void setData_accettazione(Date data_accettazione) {
        this.data_accettazione = data_accettazione;
    }

    public Date getData_dismissione() {
        return data_dismissione;
    }

    public void setData_dismissione(Date data_dismissione) {
        this.data_dismissione = data_dismissione;
    }

    public int getOperatore() {
        return operatore;
    }

    public void setOperatore(int operatore) {
        this.operatore = operatore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setUser(User user) {
    	this.user = user;
    }

	public User getUser() {
		return user;
	}

	@Override
	public User getModel() {
		return null;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCf_search() {
		return cf_search;
	}

	public void setCf_search(String cf_search) {
		this.cf_search = cf_search;
	}

	public String getCognome_search() {
		return cognome_search;
	}

	public void setCognome_search(String cognome_search) {
		this.cognome_search = cognome_search;
	}


	public int getPunteggio_idb() {
		return punteggio_idb;
	}


	public void setPunteggio_idb(int punteggio_idb) {
		this.punteggio_idb = punteggio_idb;
	}


	public Date getData_scadenza() {
		return data_scadenza;
	}


	public void setData_scadenza(Date data_scadenza) {
		this.data_scadenza = data_scadenza;
	}

	public int getEmporio() {
		return emporio;
	}

	public void setEmporio(int emporio) {
		this.emporio = emporio;
	}

	public String getAccettazione() {
		return accettazione;
	}

	public void setAccettazione(String accettazione) {
		this.accettazione = accettazione;
	}

	public String getScadenza() {
		return scadenza;
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

}
