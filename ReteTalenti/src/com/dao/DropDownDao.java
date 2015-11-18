package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.jdbc.DataAccessObject;

public class DropDownDao {

	private Connection dbConnection;
	
	public DropDownDao() {
		dbConnection = DataAccessObject.getConnection();
	}

/* GROUPS DROPDOWN */
	public List<JSONObject> getAllUsers() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT ID, USERNAME FROM USERS";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("ID"));
				option.put("DisplayText", rs.getString("USERNAME"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	public List<JSONObject> getAllGroups() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT GROUPS.GROUPID, GROUPS.GROUPNAME FROM GROUPS";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("GROUPID"));
				option.put("DisplayText", rs.getString("GROUPNAME"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	public List<JSONObject> getAllEnti() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT ID, DESCRIZIONE FROM ENTI";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("ID"));
				option.put("DisplayText", rs.getString("DESCRIZIONE"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}

	public List<JSONObject> getAllUDM() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT ID, CODICE, DESCRIZIONE FROM UNI_MISURA";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("ID"));
				option.put("DisplayText", rs.getString("CODICE") + " - " + rs.getString("DESCRIZIONE"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	
	public List<JSONObject> getAllProvince() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT COD_PROVINCIA, DENOMINAZIONE FROM PROVINCE ORDER BY DENOMINAZIONE ";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("COD_PROVINCIA"));
				option.put("DisplayText", rs.getString("DENOMINAZIONE"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	
	public List<JSONObject> getAllNazioni() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT CODICE, DENOMINAZIONE FROM NAZIONI ORDER BY DENOMINAZIONE ";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getString("CODICE"));
				option.put("DisplayText", rs.getString("DENOMINAZIONE"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
}