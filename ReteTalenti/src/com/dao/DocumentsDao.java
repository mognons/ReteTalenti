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

public class DocumentsDao {

	private Connection dbConnection;
	private PreparedStatement pStmt,pQuery;
	private Statement stmt;
	
	public DocumentsDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void updateDocuments(Documents documents) {
		String updateQuery;
		updateQuery = 	"UPDATE DOCUMENTS SET " + 
						"NAME=?, DOCUMENTPATH=? " + 
						"WHERE COURSEID=? AND MODULEID=? AND DOCUMENTID=?";

		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, documents.getName());
			pStmt.setString(2, documents.getDocumentPath());
			pStmt.setInt(3, documents.getCourseId());
			pStmt.setInt(4, documents.getModuleId());
			pStmt.setInt(5, documents.getDocumentId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public int addDocuments(Documents documents) throws Exception {
		int autoIncKeyFromFunc = -1;
		
		String insertQuery = "INSERT INTO DOCUMENTS (COURSEID, MODULEID, NAME, DOCUMENTPATH) " +
				 "VALUES (?,?,?,?)";
			try {
				pStmt = dbConnection.prepareStatement(insertQuery);
				stmt = dbConnection.createStatement();
				pStmt.setInt(1, documents.getCourseId());
				pStmt.setInt(2, documents.getModuleId());
				pStmt.setString(3, documents.getName());
				pStmt.setString(4,  documents.getDocumentPath());
				pStmt.executeUpdate();
				
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
			        autoIncKeyFromFunc = rs.getInt(1);
			    } else {
			        throw new Exception("Focca la bindella");
			    }

			    System.out.println("Key returned from " +
			                       "'SELECT LAST_INSERT_ID()': " +
			                       autoIncKeyFromFunc);
			    
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			return autoIncKeyFromFunc;
	}	
	

	public String deleteDocuments(Documents documents) throws Exception {
		String documentPath = null;
		String deleteQuery = "DELETE FROM DOCUMENTS  " + 
							 "WHERE COURSEID=? AND MODULEID=? AND DOCUMENTID=?";
		String query = "SELECT DOCUMENTPATH FROM DOCUMENTS  " + 
				 "WHERE COURSEID=? AND MODULEID=? AND DOCUMENTID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pQuery = dbConnection.prepareStatement(query);
			pStmt.setInt(1, documents.getCourseId());
			pStmt.setInt(2, documents.getModuleId());
			pStmt.setInt(3, documents.getDocumentId());
			pQuery.setInt(1, documents.getCourseId());
			pQuery.setInt(2, documents.getModuleId());
			pQuery.setInt(3, documents.getDocumentId());
			ResultSet rs = pQuery.executeQuery();
			System.out.println(pQuery.toString());
			if (rs.next()) {
				documentPath = rs.getString(1);
		    } else {
		    	documentPath = "FAILED";
		        throw new Exception("Focca la bindella");
		    }
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return documentPath;
	}	
		
	public List<Documents> getDocuments(int courseId, int moduleId, int jtStartIndex, int jtPageSize) {
		List<Documents> documents = new ArrayList<Documents>();

		String query = "SELECT * FROM DOCUMENTS WHERE COURSEID=? AND MODULEID=?";
		try {			
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.setInt(2, moduleId);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(pStmt.toString());
			while (rs.next()) {
				Documents document = new Documents();

				document.setCourseId(rs.getInt("COURSEID"));
				document.setModuleId(rs.getInt("MODULEID"));
				document.setDocumentId(rs.getInt("DOCUMENTID"));
				document.setName(rs.getString("NAME"));
				document.setDocumentPath(rs.getString("DOCUMENTPATH"));
				documents.add(document);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return documents;
	}


}