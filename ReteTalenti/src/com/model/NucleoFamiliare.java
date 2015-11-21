package com.model;

import java.sql.Date;

public class NucleoFamiliare {
	String codice_fiscale;
	String nome;
	String cognome;
	String sesso;
	Date data_nascita;
	int tipo_parentela;
	String cf_assistito_nf;
	String desc_tipo_parentela;
	
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
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
	public Date getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getCf_assistito_nf() {
		return cf_assistito_nf;
	}
	public void setCf_assistito_nf(String cf_assistito_nf) {
		this.cf_assistito_nf = cf_assistito_nf;
	}
	public String getDesc_tipo_parentela() {
		return desc_tipo_parentela;
	}
	public void setDesc_tipo_parentela(String desc_tipo_parentela) {
		this.desc_tipo_parentela = desc_tipo_parentela;
	}
	public void setTipo_parentela(int tipo_parentela) {
		this.tipo_parentela = tipo_parentela;
	}
	
	public int getTipo_parentela() {
		return this.tipo_parentela;
	}
}
