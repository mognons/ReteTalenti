package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dao.ModulesDao;
import com.dao.DocumentsDao;
import com.jdbc.DataAccessObject;
import com.model.*;

public class CoursesDao {

	private Connection dbConnection;
	private PreparedStatement pStmt, pModules, pDocuments;
	private Statement stmt;
	
	private ModulesDao modulesDao = new ModulesDao();
	private DocumentsDao documentsDao = new DocumentsDao();
	
	public CoursesDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public int createCourse(Courses course) throws Exception {
		int autoIncKeyFromFunc = -1;

		String insertQuery = "INSERT INTO COURSES "
				+ "(NAME, TYPE, DETAILS, STARTDATE, DURATION, ISACTIVE) "
				+ "VALUES (?,?,?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			stmt = dbConnection.createStatement();
			pStmt.setString(1, course.getName());
			pStmt.setString(2, course.getType());
			pStmt.setString(3, course.getDetails());
			pStmt.setDate(4, course.getStartDate());
			pStmt.setInt(5, course.getDuration());
			pStmt.setBoolean(6, course.isCourseActive());
			pStmt.executeUpdate();

			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			} else {
				throw new Exception("Focca la bindella");
			}

			System.out.println("Key returned from "
					+ "'SELECT LAST_INSERT_ID()': " + autoIncKeyFromFunc);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateCourse(Courses course) {
		String updateQuery = "UPDATE COURSES SET  "
				+ "NAME=?, TYPE=?, DETAILS=?, STARTDATE=?, DURATION=?, ISACTIVE=? "
				+ "WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, course.getName());
			pStmt.setString(2, course.getType());
			pStmt.setString(3, course.getDetails());
			pStmt.setDate(4, course.getStartDate());
			pStmt.setInt(5, course.getDuration());
			pStmt.setBoolean(6, course.isCourseActive());
			pStmt.setInt(7, course.getCourseId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public int deleteCourse(int courseId) {
		String query = "SELECT * FROM CLASSES WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return -1;
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		query = "DELETE FROM COURSES WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// Delete from Calendar
		query = "DELETE FROM CALENDAR WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

	public List<Courses> getAllCourses() {
		List<Courses> courses = new ArrayList<Courses>();

		String query = "SELECT * FROM COURSES ORDER BY STARTDATE DESC, NAME";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Courses course = new Courses();

				course.setCourseId(rs.getInt("COURSEID"));
				course.setName(rs.getString("NAME"));
				course.setType(rs.getString("TYPE"));
				course.setDetails(rs.getString("DETAILS"));
				course.setStartDate(Date.valueOf(rs.getDate("STARTDATE").toString()));
				course.setDuration(rs.getInt("DURATION"));
				;
				course.setCourseActive(rs.getBoolean("ISACTIVE"));
				courses.add(course);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return courses;
	}

	public int cloneCourse(Courses course) throws Exception {
		int newCourseId = -1;
		int newModuleId = -1;
		int oldModuleId = -1;

		String insertQuery = "INSERT INTO COURSES (NAME,TYPE,DETAILS,STARTDATE,DURATION, ISACTIVE) "
							+ "SELECT NAME,TYPE,DETAILS,CURDATE(),DURATION,0 FROM COURSES "
							+ "WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			stmt = dbConnection.createStatement();
			pStmt.setInt(1, course.getCourseId());
			pStmt.executeUpdate();

			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				newCourseId = rs.getInt(1);
			} else {
				throw new Exception("Focca la bindella");
			}

			System.out.println("Key returned from "
					+ "'SELECT LAST_INSERT_ID()': " + newCourseId);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		String selectModules = "SELECT * FROM MODULES WHERE COURSEID=? ORDER BY SEQ";
		String selectDocuments = "SELECT * FROM DOCUMENTS WHERE MODULEID=? AND COURSEID=?";
		
		try {
			pModules = dbConnection.prepareStatement(selectModules);
			pModules.setInt(1, course.getCourseId());
			ResultSet moduleRS = pModules.executeQuery();
			while (moduleRS.next()) {
				Modules modules = new Modules();
				oldModuleId = moduleRS.getInt("MODULEID");
				modules.setCourseId(newCourseId);
				modules.setName(moduleRS.getString("NAME"));
				modules.setDetails(moduleRS.getString("DETAILS"));
				modules.setDuration(moduleRS.getInt("DURATION"));
				modules.setModuleActive(moduleRS.getBoolean("ISACTIVE"));
				modules.setSeq(moduleRS.getInt("SEQ"));
				newModuleId = modulesDao.addModules(modules);
				// Now copy all the documents from oldModuleId to newModuleId
				pDocuments = dbConnection.prepareStatement(selectDocuments);
				pDocuments.setInt(1, oldModuleId);
				pDocuments.setInt(2, course.getCourseId());
				ResultSet documentRS = pDocuments.executeQuery();
				while (documentRS.next()) {
					Documents documents = new Documents();
					documents.setCourseId(newCourseId);
					documents.setModuleId(newModuleId);
					documents.setDocumentPath(documentRS.getString("DOCUMENTPATH"));
					documents.setName(documentRS.getString("name"));
					documentsDao.addDocuments(documents);
				}

			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return newCourseId;
	}

}