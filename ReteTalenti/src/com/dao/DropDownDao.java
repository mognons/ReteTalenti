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
	

	/* STUDENTS DROPDOWN */
	public List<JSONObject> getAllStudents() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT STUDENTID, NAME, DEPARTMENT FROM STUDENT ORDER BY NAME, DEPARTMENT";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("STUDENTID"));
				option.put("DisplayText", rs.getString("NAME")+" ("+rs.getString("DEPARTMENT") + ")");
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	/* COURSES DROPDOWN */
	public List<JSONObject> getAllCourses() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT COURSEID, NAME FROM COURSES ORDER BY NAME";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("COURSEID"));
				option.put("DisplayText", rs.getString("NAME"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	/* COURSES DROPDOWN */
	public List<JSONObject> getActiveCourses() {

		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		String query = "SELECT COURSEID, NAME FROM COURSES ORDER BY NAME WHERE ISACTIVE=TRUE";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject option = new JSONObject();
				option.put("Value", rs.getInt("COURSEID"));
				option.put("DisplayText", rs.getString("NAME"));
				options.add(option);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
}