package com.model;

import java.sql.Date;

public class Eccedenza {
	int id;
	int ente_cedente;
	String prodotto;
	int udm;
	int qta;
	int qta_residua;
	int own_impegno;
	Boolean can_edit;
	Date scadenza;
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

	public Boolean getCan_edit() {
		return can_edit;
	}
	public void setCan_edit(Boolean can_edit) {
		this.can_edit = can_edit;
	}
	public int getOwn_impegno() {
		return own_impegno;
	}
	public void setOwn_impegno(int own_impegno) {
		this.own_impegno = own_impegno;
	}
	
	

}
