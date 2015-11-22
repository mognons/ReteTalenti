/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author gminardi
 */
public class Assistito {
    
    private String cod_fiscale;
    private String nome;
    private String cognome;
    private String sesso;
    private int stato_civile;
    private String desc_stato_civile; // da TAB.STATI_CIVILI
    private String luogo_nascita;
    private Date data_nascita;

    private String nazionalita;
    private String denominazione; // da TAB.NAZIONI

    private String indirizzo_residenza;
    private String citta_residenza;
    private String cap;

    private int provincia;
    private String sigla_autom; // da TAB.PROVINCE

    private String permesso_soggiorno;
    private String telefono;
    private String email;
    private String num_documento;

    private int ente_assistente;
    private String descrizione; // da TAB.ENTI
    
    private int punteggio_idb;

    private Date data_inserimento;
    private Date data_fine_assistenza;
    private Date data_candidatura;
    private Date data_accettazione;
    private Date data_scadenza;
    private Date data_dismissione;
    private int emporio;
    private String desc_emporio;
    private int operatore;
    private String username;

    public Assistito() {
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

	public String getDesc_stato_civile() {
		return desc_stato_civile;
	}

	public void setDesc_stato_civile(String desc_stato_civile) {
		this.desc_stato_civile = desc_stato_civile;
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

	public String getDesc_emporio() {
		return desc_emporio;
	}

	public void setDesc_emporio(String desc_emporio) {
		this.desc_emporio = desc_emporio;
	}
    
}
