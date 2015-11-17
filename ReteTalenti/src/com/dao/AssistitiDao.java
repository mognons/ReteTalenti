package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.Assistito;
import com.model.CalendarDTO;
import com.model.Groups;
import com.model.User;

public class AssistitiDao {
	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;
	public AssistitiDao() {
		dbConnection = DataAccessObject.getConnection();
	}
	
	public int createAssistito(Assistito assistito) {
		String insertAssistitoQuery = "INSERT INTO ASSISTITI (NOME,COGNOME,SESSO,STATO_CIVILE,LUOGO_NASCITA,DATA_NASCITA,NAZIONALITA,INDIRIZZO_RESIDENZA,CITTA_RESIDENZA,CAP,PROVINCIA,PERMESSO_SOGGIORNO,TELEFONO,EMAIL,NUM_DOCUMENTO,ENTE_ASSISTITI,DATA_INSERIMENTO,DATA_FINE_ASSISTENZA,DATA_CANDIDATURA,DATA_ACCETTAZIONE,DATA_DISMISSIONE,OPERATORE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int autoIncKeyFromFunc = 0;
		try {
			pStmt = dbConnection.prepareStatement(insertAssistitoQuery);
			pStmt.setString(1,assistito.getNome());
			pStmt.setString(2,assistito.getCognome());
			pStmt.setString(3,assistito.getSesso());
			pStmt.setString(4,assistito.getStato_civile());
			pStmt.setString(5,assistito.getLuogo_nascita());
			pStmt.setDate(6,(java.sql.Date)assistito.getData_nascita());
			pStmt.setString(7,assistito.getNazionalita());
			pStmt.setString(9,assistito.getIndirizzo_residenza());
			pStmt.setString(10,assistito.getCitta_residenza());
			pStmt.setString(11,assistito.getCap());
			pStmt.setInt(12,assistito.getProvincia());
			pStmt.setString(13,assistito.getPermesso_soggiorno());
			pStmt.setString(14,assistito.getTelefono());
			pStmt.setString(15,assistito.getEmail());
			pStmt.setString(16,assistito.getNum_documento());
			pStmt.setInt(17,assistito.getEnte_assistente());
			pStmt.setDate(18,assistito.getData_inserimento());
			pStmt.setDate(19,assistito.getData_fine_assistenza());
			pStmt.setDate(20,assistito.getData_candidatura());
			pStmt.setDate(21,assistito.getData_accettazione());
			pStmt.setDate(22,assistito.getData_dismissione());
			pStmt.setInt(23,assistito.getOperatore());
			pStmt.executeUpdate();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_Cod_fiscale()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
				System.out.println("Cod_fiscale inserito: " + autoIncKeyFromFunc);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}
	
	public void updateAssistito(Assistito assistito) {

		String updateQuery = 	"UPDATE ASSISTITI SET NOME=?, COGNOME=?,SESSO=?,STATO_CIVILE=?,LUOGO_NASCITA=?, "
								+ "DATA_NASCITA=?, NAZIONALITA=?,INDIRIZZO_RESIDENZA=?,CITTA_RESIDENZA=?,CAP=?,PROVINCIA=?, "
								+ "PERMESSO_SOGGIORNO=?,TELEFONO=?,EMAIL=?,NUM_DOCUMENTO=? "
								+ "WHERE COD_FISCALE=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, assistito.getNome());
			pStmt.setString(2, assistito.getCognome());
			pStmt.setString(3, assistito.getSesso());
			pStmt.setString(4, assistito.getStato_civile());
			pStmt.setString(5, assistito.getLuogo_nascita());
			pStmt.setDate(6, assistito.getData_nascita());
			pStmt.setString(7, assistito.getNazionalita());
			pStmt.setString(8, assistito.getIndirizzo_residenza());
			pStmt.setString(9, assistito.getCitta_residenza());
			pStmt.setString(10, assistito.getCap());
			pStmt.setInt(11, assistito.getProvincia());
			pStmt.setString(12, assistito.getPermesso_soggiorno());
			pStmt.setString(13, assistito.getTelefono());
			pStmt.setString(14, assistito.getEmail());
			pStmt.setString(15, assistito.getNum_documento());
			pStmt.setString(16, assistito.getCod_fiscale());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<Assistito> getAllAssistiti(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
		
		List<Assistito> assistiti = new ArrayList<Assistito>();
		String whereCondition1 = "AND 1=1 ";
		List<Groups> gruppiUtente = new ArrayList<Groups>();
		gruppiUtente = user.getGroups();
		Iterator<Groups> it = gruppiUtente.iterator();
		while (it.hasNext()) {
			// INSERT EVENTS
			Groups gruppo = it.next();
			int groupId = gruppo.getGroupId();
			if (groupId==3) 
				whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
		}
		System.out.println(whereCondition1);
		
		String query = 	"SELECT * FROM assistiti " 
						+ "WHERE 1=1 "
						+ whereCondition1
						+ "ORDER BY " + jtSorting + " "
						+ "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
						+ Integer.toString(jtStartIndex);
		try {
			pStmt = dbConnection.prepareStatement(query);
			//pStmt.setInt(1, user.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Assistito assistito = new Assistito();

				assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
				assistito.setNome(rs.getString("NOME"));
				assistito.setCognome(rs.getString("COGNOME"));
				assistito.setSesso(rs.getString("SESSO"));
				assistito.setStato_civile(rs.getString("STATO_CIVILE"));
				assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
				assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
				assistito.setNazionalita(rs.getString("NAZIONALITA"));
				assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
				assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
				assistito.setCap(rs.getString("CAP"));
				assistito.setProvincia(rs.getInt("PROVINCIA"));
				assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
				assistito.setTelefono(rs.getString("TELEFONO"));
				assistito.setEmail(rs.getString("EMAIL"));
				assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
				assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
				assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
				assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
				assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
				assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
				assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
				assistito.setOperatore(rs.getInt("OPERATORE"));
				assistiti.add(assistito);
			
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return assistiti;
	}
	
	public int getCountAssistiti(User user) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM ASSISTITI " 
						+ "WHERE NOME=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				totalRecord = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return totalRecord;
	}
	
	
	
	public void deleteAssistito(Assistito assistito) {

		String deleteQuery = "DELETE FROM ASSISTITI WHERE COD_FISCALE=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setString(1, assistito.getCod_fiscale());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
