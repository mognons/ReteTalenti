package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.*;

public class MarksDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	public MarksDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void updateMarks(Marks marks) {
		String insertQuery = "UPDATE MARKS SET " + 
							 "MARKTYPE=?, MARKVALUE=? " + 
							 "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, marks.getMarkType());
			pStmt.setInt(2, marks.getMarkValue());
			pStmt.setInt(3, marks.getMarkId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public int addMarks(Marks marks) throws SQLException {
		int autoIncKeyFromFunc = 0;
		String insertQuery = "INSERT INTO MARKS (COURSEID,MODULEID,STUDENTID, MARKDATE, MARKTYPE,MARKVALUE) " +
							 "VALUES (?,?,?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, marks.getCourseId());
			pStmt.setInt(2, marks.getModuleId());
			pStmt.setInt(3, marks.getStudentId());
			pStmt.setString(4, marks.getMarkDate());
			pStmt.setString(5, marks.getMarkType());
			pStmt.setInt(6, marks.getMarkValue());
			pStmt.executeUpdate();
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			autoIncKeyFromFunc = rs.getInt(1);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void deleteMarks(Marks marks) {
		String insertQuery = "DELETE FROM MARKS  " + 
							 "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, marks.getMarkId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public List<Marks> getMarksByStudent(int courseId, int studentId) {
		List<Marks> Marks = new ArrayList<Marks>();

		String query = "SELECT * FROM MARKS WHERE COURSEID=? AND STUDENTID=? ORDER BY MARKDATE DESC";
		try {			
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.setInt(2, studentId);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(pStmt.toString());
			while (rs.next()) {
				Marks myMark = new Marks();
				myMark.setMarkId(rs.getInt("ID"));
				myMark.setCourseId(rs.getInt("COURSEID"));
				myMark.setModuleId(rs.getInt("MODULEID"));
				myMark.setStudentId(rs.getInt("STUDENTID"));
				myMark.setMarkDate(rs.getString("MARKDATE"));
				myMark.setMarkType(rs.getString("MARKTYPE"));
				myMark.setMarkValue(rs.getInt("MARKVALUE"));
				Marks.add(myMark);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return Marks;
	}

	public List<Marks> getMarksByStudentDate(int courseId, int moduleId, int studentId, String markDate) {
		List<Marks> Marks = new ArrayList<Marks>();

		String query = "SELECT * FROM MARKS WHERE COURSEID=? AND MODULEID=? AND STUDENTID=? AND MARKDATE=?";
		try {			
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.setInt(2, moduleId);
			pStmt.setInt(3, studentId);
			pStmt.setString(4, markDate);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(pStmt.toString());
			while (rs.next()) {
				Marks myMark = new Marks();
				myMark.setMarkId(rs.getInt("ID"));
				myMark.setCourseId(rs.getInt("COURSEID"));
				myMark.setModuleId(rs.getInt("MODULEID"));
				myMark.setStudentId(rs.getInt("STUDENTID"));
				myMark.setMarkDate(rs.getString("MARKDATE"));
				myMark.setMarkType(rs.getString("MARKTYPE"));
				myMark.setMarkValue(rs.getInt("MARKVALUE"));
				Marks.add(myMark);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return Marks;
	}


}