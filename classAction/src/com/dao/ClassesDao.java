package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.*;

public class ClassesDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	public ClassesDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void updateClasses(Classes classes) {
		String insertQuery = "UPDATE CLASSES SET " + 
							 "JOINDATE=?, ISACTIVE=? " + 
							 "WHERE COURSEID=? AND STUDENTID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setDate(1, classes.getJoinDate());
			pStmt.setBoolean(2, classes.isUserActive());
			pStmt.setInt(3, classes.getCourseId());
			pStmt.setInt(4, classes.getStudentId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public void addClasses(Classes classes) {
		String insertQuery = "INSERT INTO CLASSES (COURSEID,STUDENTID, JOINDATE, ISACTIVE) " +
							 "VALUES (?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, classes.getCourseId());
			pStmt.setInt(2, classes.getStudentId());
			pStmt.setDate(3, classes.getJoinDate());
			pStmt.setBoolean(4, classes.isUserActive());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void deleteClasses(Classes classes) {
		String insertQuery = "DELETE FROM CLASSES  " + 
							 "WHERE COURSEID=? AND STUDENTID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, classes.getCourseId());
			pStmt.setInt(2, classes.getStudentId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public void deleteStudent(int userId) {
		String deleteQuery = "DELETE FROM STUDENT WHERE STUDENTID = ?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, userId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<Classes> getClassStudents(int courseId, int jtStartIndex, int jtPageSize) {
		List<Classes> classes = new ArrayList<Classes>();

		String query = "SELECT * FROM CLASSES WHERE COURSEID=?  ORDER BY JOINDATE DESC";
		try {			
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(pStmt.toString());
			while (rs.next()) {
				Classes myclass = new Classes();

				myclass.setCourseId(rs.getInt("COURSEID"));
				myclass.setStudentId(rs.getInt("STUDENTID"));
				myclass.setJoinDate(rs.getDate("JOINDATE"));
				myclass.setUserActive(rs.getBoolean("ISACTIVE"));
				System.out.println(myclass.getStudentId());
				classes.add(myclass);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return classes;
	}


}