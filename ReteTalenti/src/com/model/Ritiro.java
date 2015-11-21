package com.model;

import java.sql.Date;

public class Ritiro {
	int id;
	int ente_cedente;
	String desc_ente_cedente;
	String prodotto;
	int udm;
	String desc_udm;
	int qta;
	int qta_residua;
	Date scadenza;
	int qta_prenotata;
	Date data_ritiro;
	String ora_ritiro;
	boolean ritiro_effettuato;
	java.util.Date timestamp;
	String operatore; // this is user.username
	
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
	public String getProdotto() {
		return prodotto;
	}
	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
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
	public Date getScadenza() {
		return scadenza;
	}
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	public java.util.Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public int getQta_residua() {
		return qta_residua;
	}
	public void setQta_residua(int qta_residua) {
		this.qta_residua = qta_residua;
	}
	public int getQta_prenotata() {
		return qta_prenotata;
	}
	public void setQta_prenotata(int qta_prenotata) {
		this.qta_prenotata = qta_prenotata;
	}
	public Date getData_ritiro() {
		return data_ritiro;
	}
	public void setData_ritiro(Date data_ritiro) {
		this.data_ritiro = data_ritiro;
	}
	public String getOra_ritiro() {
		return ora_ritiro;
	}
	public void setOra_ritiro(String ora_ritiro) {
		this.ora_ritiro = ora_ritiro;
	}
	public boolean isRitiro_effettuato() {
		return ritiro_effettuato;
	}
	public void setRitiro_effettuato(boolean ritiro_effettuato) {
		this.ritiro_effettuato = ritiro_effettuato;
	}
	public String getDesc_ente_cedente() {
		return desc_ente_cedente;
	}
	public void setDesc_ente_cedente(String desc_ente_cedente) {
		this.desc_ente_cedente = desc_ente_cedente;
	}
	public String getDesc_udm() {
		return desc_udm;
	}
	public void setDesc_udm(String desc_udm) {
		this.desc_udm = desc_udm;
	}
	
	

}
