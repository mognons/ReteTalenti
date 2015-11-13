package com.model;

public class Uni_misura {
	private Integer id;
	private String codice;
	private String descrizione;
	
	// costruttore di default
	public Uni_misura(){
		
	}
	
	// costruttore con argomento
	
	public Uni_misura(Integer id, String codice, String descrizione) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
	}

	
	// getter and setter
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
	

}
