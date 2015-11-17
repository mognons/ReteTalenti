/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.action;

import com.interceptor.UserAware;
import com.model.Assistito;
import com.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author gminardi
 */
public class AssistitiTableAction extends ActionSupport implements UserAware {

    private AssistitiDao dao = new AssistitiDao();
    
    private List<Assistito> records;
    private String result;
    private String message;
    private Assistito record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;

    private User user = new User();
//

    private static final long serialVersionUID = 1L;
    private String cod_fiscale;
    private String nome;
    private String cognome;
    private String sesso;
    private String stato_civile;
    private String luogo_nascita;
    private Date data_nascita;

    private String nazionalita; //codice nazione fk codice della tabella nazioni varchar (4)
    private String denominazione;

    private String indirizzo_residenza;
    private String citta_residenza;
    private String cap;

    private int provincia;
    private String sigla_autom;

    private String permesso_soggiorno;
    private String telefono;
    private String email;
    private String num_documento;

    private int ente_assistente; //fk ID della tabella enti INT(11)
    private String descrizione;

    private Date data_inserimento;
    private Date data_fine_assistenza;
    private Date data_candidatura;
    private Date data_accettazione;
    private Date data_dismissione;

    private int operatore;
    private String username;

    public String list() {
        try {
            // Fetch Data from Assistiti Table
            records = dao.getAllAssistiti(jtStartIndex, jtPageSize, jtSorting.toUpperCase());
            result = "OK";
            totalRecordCount = dao.getRecordCount();

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
    
    public String searchAssistiti(String cod_fiscale, String cognome_search) {
        try {
            // Fetch Data from Assistiti Table
            records = dao.getAssistitiSearch(cod_fiscale, cognome_search);
            result = "OK";
           
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }    
        
        return SUCCESS;
        
    }

    public String create() throws IOException {
        
        // ***************** formattazione date... all'occorrenza **********************
        //
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        // java.util.Date date = formatter.parse(rs.getString("data_assunz"));
        // Contr.setData_assunz(formatter2.format(date));
        //
        // *******************************************************************
        
        Assistito record = new Assistito();
        
        record.setCod_fiscale(cod_fiscale);
        record.setNome(nome);
        record.setCognome(cognome);
        record.setSesso(sesso);
        record.setStato_civile(stato_civile);
        record.setLuogo_nascita(luogo_nascita);
        record.setData_nascita(data_nascita);
        
        //naz è il CODICE nazione varchar(4)
        nazionalita = dao.getNazionalita(denominazione);
        record.setNazionalita(nazionalita);
        
        record.setIndirizzo_residenza(indirizzo_residenza);
        record.setCitta_residenza(citta_residenza);
        record.setCap(cap);
        
        //provincia è il COD_PROVINCIA INT(11)
        provincia = dao.getCod_Provincia(sigla_autom);//naz è il CODICE nazione
        record.setProvincia(provincia);
        record.setPermesso_soggiorno(permesso_soggiorno);
        record.setTelefono(telefono);
        record.setEmail(email);
        record.setNum_documento(num_documento);
        
        //ente_assistente è l'ID INT(11) della tabella ENTI
        ente_assistente=dao.getEnte_Assistente(descrizione);
        record.setEnte_assistente(ente_assistente);
        
        record.setData_inserimento(data_inserimento);
        record.setData_fine_assistenza(data_fine_assistenza);
        record.setData_candidatura(data_candidatura);
        record.setData_accettazione(data_accettazione);
        record.setData_dismissione(data_dismissione);
        
        //operatore è l'ID dello username attivo
        operatore=user.getId();
        record.setOperatore(operatore);
                
        try {
            // Create new record
            dao.createAssistito(record);
            result = "OK";

        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }

    public String update() throws IOException {
        
        Assistito assistito=new Assistito();
                
        assistito.setCod_fiscale(cod_fiscale);
        assistito.setNome(nome);
        assistito.setCognome(cognome);
        assistito.setSesso(sesso);
        assistito.setStato_civile(stato_civile);
        assistito.setLuogo_nascita(luogo_nascita);
        assistito.setData_nascita(data_nascita);
        assistito.setNazionalita(nazionalita);
        assistito.setIndirizzo_residenza(indirizzo_residenza);
        assistito.setCitta_residenza(citta_residenza);
        assistito.setCap(cap);
        assistito.setProvincia(provincia);
        assistito.setPermesso_soggiorno(permesso_soggiorno);
        assistito.setTelefono(telefono);
        assistito.setEmail(email);
        assistito.setNum_documento(num_documento);
        assistito.setEnte_assistente(ente_assistente);
        assistito.setData_inserimento(data_inserimento);
        assistito.setData_fine_assistenza(data_fine_assistenza);
        assistito.setData_candidatura(data_candidatura);
        assistito.setData_accettazione(data_accettazione);
        assistito.setData_dismissione(data_dismissione);
        assistito.setOperatore(operatore);
        

        try {
            // Update existing record
            dao.updateAssistito(assistito);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return Action.SUCCESS;
    }

    public String delete() throws IOException {
        // Delete record
        
        System.out.println("Deleting assistito con codice fiscale " + cod_fiscale);
        record = new Assistito();
        record.setCod_fiscale(cod_fiscale);
        try {
            dao.deleteAssistito(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        result = "OK";
        return SUCCESS;
        
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

    public String getStato_civile() {
        return stato_civile;
    }

    public void setStato_civile(String stato_civile) {
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
        // TODO Auto-generated method stub

    }

}
