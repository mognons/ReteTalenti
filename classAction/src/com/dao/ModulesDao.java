package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.*;
import java.sql.Statement;

public class ModulesDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;

	public ModulesDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void updateModules(Modules modules) {
		String insertQuery = "UPDATE MODULES SET "
				+ "NAME=?, DETAILS=?, DURATION=?, ISACTIVE=? "
				+ "WHERE COURSEID=? AND MODULEID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, modules.getName());
			pStmt.setString(2, modules.getDetails());
			pStmt.setInt(3, modules.getDuration());
			pStmt.setBoolean(4, modules.isModuleActive());
			pStmt.setInt(5, modules.getCourseId());
			pStmt.setInt(6, modules.getModuleId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void relocateModules(Modules modules, String direction) {
		int deltaSEQ = 15;
		System.out.println("Actual value for SEQ: " + modules.getSeq());
		if (modules.getSeq() == 10 && direction.equalsIgnoreCase("UP"))
			return;
		if (direction.equalsIgnoreCase("UP"))
			deltaSEQ = -deltaSEQ;
		String insertQuery = "UPDATE MODULES SET " + "SEQ=SEQ+? "
				+ "WHERE COURSEID=? AND MODULEID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, deltaSEQ);
			pStmt.setInt(2, modules.getCourseId());
			pStmt.setInt(3, modules.getModuleId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// Now rebuild sequence
		String rebuild = "UPDATE modules m JOIN (SELECT m.moduleid, m.courseid, (@rownum:=@rownum+1) * 10 AS newSEQ "
				+ "FROM modules m, (SELECT @rownum := 0) r "
				+ "WHERE COURSEID=? ORDER BY seq) new ON m.courseid=new.courseid and m.moduleid=new.moduleid "
				+ "SET m.SEQ=new.newSEQ";
		try {
			pStmt = dbConnection.prepareStatement(rebuild);
			pStmt.setInt(1, modules.getCourseId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public int addModules(Modules modules) throws Exception {
		int autoIncKeyFromFunc = -1;
		// Get last SEQ from modules of courseId
		int nextSequence = 10;
		String lastSEQ = "SELECT COALESCE(MAX(SEQ),0) FROM MODULES WHERE COURSEID=?";
		try {
			pStmt = dbConnection.prepareStatement(lastSEQ);
			pStmt.setInt(1, modules.getCourseId());
			ResultSet sequence = pStmt.executeQuery();

			if (sequence.next()) {
				nextSequence = sequence.getInt(1) + 10;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		//
		String insertQuery = "INSERT INTO MODULES (COURSEID, SEQ, NAME,DETAILS,DURATION,ISACTIVE) "
				+ "VALUES (?,?,?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			stmt = dbConnection.createStatement();
			pStmt.setInt(1, modules.getCourseId());
			pStmt.setInt(2, nextSequence);
			pStmt.setString(3, modules.getName());
			pStmt.setString(4, modules.getDetails());
			pStmt.setInt(5, modules.getDuration());
			pStmt.setBoolean(6, modules.isModuleActive());
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

	public void deleteModules(Modules modules) {
		String insertQuery = "DELETE FROM MODULES  "
				+ "WHERE COURSEID=? AND MODULEID=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, modules.getCourseId());
			pStmt.setInt(2, modules.getModuleId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		String rebuild = "UPDATE modules m JOIN (SELECT m.moduleid, m.courseid, (@rownum:=@rownum+1) * 10 AS newSEQ "
				+ "FROM modules m, (SELECT @rownum := 0) r "
				+ "WHERE COURSEID=? ORDER BY seq) new ON m.courseid=new.courseid and m.moduleid=new.moduleid "
				+ "SET m.SEQ=new.newSEQ";
		try {
			pStmt = dbConnection.prepareStatement(rebuild);
			pStmt.setInt(1, modules.getCourseId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<Modules> getModules(int courseId, int jtStartIndex,
			int jtPageSize) {
		List<Modules> modules = new ArrayList<Modules>();

		String query = "SELECT * FROM MODULES WHERE COURSEID=? ORDER BY SEQ";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(pStmt.toString());
			while (rs.next()) {
				Modules mymodule = new Modules();

				mymodule.setCourseId(rs.getInt("COURSEID"));
				mymodule.setModuleId(rs.getInt("MODULEID"));
				mymodule.setName(rs.getString("NAME"));
				mymodule.setDetails(rs.getString("DETAILS"));
				mymodule.setDuration(rs.getInt("DURATION"));
				mymodule.setModuleActive(rs.getBoolean("ISACTIVE"));
				mymodule.setSeq(rs.getInt("SEQ"));
				modules.add(mymodule);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return modules;
	}

}