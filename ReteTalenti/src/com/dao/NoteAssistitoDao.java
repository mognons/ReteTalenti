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

public class NoteAssistitoDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;
	
	public NoteAssistitoDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void updateNotaAssistito(NoteAssistito nota) {
		String updateQuery;
		updateQuery = 	"UPDATE NOTE_ASSISTITI SET " + 
						"NOTE_LIBERE=? " + 
						"WHERE ID=?";

		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, nota.getNote_libere());
			pStmt.setInt(2, nota.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public int addNotaAssistito(NoteAssistito nota) throws Exception {
		int autoIncKeyFromFunc = -1;
		
		String insertQuery = "INSERT INTO NOTE_ASSISTITI "
							+ "(NOTE_LIBERE, DATA_NOTE, CF_ASSISTITO_NOTE, OPERATORE) "
							+ "VALUES (?,NOW(),?,?)";
			try {
				pStmt = dbConnection.prepareStatement(insertQuery);
				stmt = dbConnection.createStatement();
				pStmt.setString(1, nota.getNote_libere());
				pStmt.setString(2, nota.getCf_assistito_note());
				pStmt.setInt(3,  nota.getOperatore());
				pStmt.executeUpdate();
				
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

				if (rs.next()) {
			        autoIncKeyFromFunc = rs.getInt(1);
			    } else {
			        throw new Exception("Focca la bindella");
			    }
			    
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			return autoIncKeyFromFunc;
	}	
	

	public void deleteNotaAssistito(NoteAssistito nota) throws Exception {
		String deleteQuery = "DELETE FROM NOTE_ASSISTITI  " + 
							 "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, nota.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return;
	}	
		
	public List<NoteAssistito> getNoteAssistito(int jtStartIndex, int jtPageSize, String cf_assistito_note) {
		List<NoteAssistito> note = new ArrayList<NoteAssistito>();

		String query = "SELECT * FROM NOTE_ASSISTITI WHERE CF_ASSISTITO_NOTE=? ORDER BY DATA_NOTE DESC, ID DESC";
		try {			
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, cf_assistito_note);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				NoteAssistito nota = new NoteAssistito();

				nota.setId(rs.getInt("ID"));
				nota.setNote_libere(rs.getString("NOTE_LIBERE"));
				nota.setCf_assistito_note(rs.getString("CF_ASSISTITO_NOTE"));
				nota.setData_note(rs.getDate("DATA_NOTE"));
				nota.setOperatore(rs.getInt("OPERATORE"));
				
				note.add(nota);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return note;
	}
}