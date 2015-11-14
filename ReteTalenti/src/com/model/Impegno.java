package com.model;

import java.util.Date;

public class Impegno {
	int id;
	int id_eccedenza; // this is eccedenza.id
	int ente_richiedente; // this is ente.id
	int qta_prenotata;
	Date data_ritiro;
	boolean ritiro_effettuato;
	Date timestamp;
	String operatore; // this is user.username
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_eccedenza() {
		return id_eccedenza;
	}
	public void setId_eccedenza(int id_eccedenza) {
		this.id_eccedenza = id_eccedenza;
	}
	public int getEnte_richiedente() {
		return ente_richiedente;
	}
	public void setEnte_richiedente(int ente_richiedente) {
		this.ente_richiedente = ente_richiedente;
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
	public boolean isRitiro_effettuato() {
		return ritiro_effettuato;
	}
	public void setRitiro_effettuato(boolean ritiro_effettuato) {
		this.ritiro_effettuato = ritiro_effettuato;
	}
	public Date getTimestamp() {
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
	
	
}
