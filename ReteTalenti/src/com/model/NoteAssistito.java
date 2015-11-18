package com.model;

import java.sql.Date;

public class NoteAssistito {
	int id;
	String note_libere;
	Date data_note;
	String cf_assistito_note;
	int operatore;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNote_libere() {
		return note_libere;
	}
	public void setNote_libere(String note_libere) {
		this.note_libere = note_libere;
	}
	public Date getData_note() {
		return data_note;
	}
	public void setData_note(Date data_note) {
		this.data_note = data_note;
	}
	public String getCf_assistito_note() {
		return cf_assistito_note;
	}
	public void setCf_assistito_note(String cf_assistito_note) {
		this.cf_assistito_note = cf_assistito_note;
	}
	public int getOperatore() {
		return operatore;
	}
	public void setOperatore(int operatore) {
		this.operatore = operatore;
	}
	
	
}
