package com.model;

public class Message {
	private int id;
	private int ente;
	private String tag;
	private String message_text;
	private String action;
	private String key1;
	private int key2;
	private java.sql.Date key3;
	private java.sql.Date start_date;
	private java.sql.Date end_date;
	private Boolean message_read;
	private java.util.Date timestamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEnte() {
		return ente;
	}
	public void setEnte(int ente) {
		this.ente = ente;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public int getKey2() {
		return key2;
	}
	public void setKey2(int key2) {
		this.key2 = key2;
	}
	public java.sql.Date getKey3() {
		return key3;
	}
	public void setKey3(java.sql.Date key3) {
		this.key3 = key3;
	}
	public java.sql.Date getStart_date() {
		return start_date;
	}
	public void setStart_date(java.sql.Date start_date) {
		this.start_date = start_date;
	}
	public java.sql.Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(java.sql.Date end_date) {
		this.end_date = end_date;
	}
	public Boolean getMessage_read() {
		return message_read;
	}
	public void setMessage_read(Boolean message_read) {
		this.message_read = message_read;
	}
	public java.util.Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}
}
