package com.model;
import java.util.List;

import com.model.Groups;

public class User {
	private int id;
	private String username;
	private String password;
	private String userFirstname;
	private String userLastname;
	private String userEmail;
	private String userPhone;
	private int ente;
	private String descrizioneEnte;
	private List<Groups> groups;
        
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserFirstname() {
		return userFirstname;
	}
	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}
	public String getUserLastname() {
		return userLastname;
	}
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getEnte() {
		return ente;
	}

	public void setEnte(int ente) {
		this.ente = ente;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}
}
