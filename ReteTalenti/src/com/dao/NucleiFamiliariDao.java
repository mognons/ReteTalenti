package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.NucleoFamiliare;

public class NucleiFamiliariDao {
	private Connection dbConnection;
	private PreparedStatement pStmt;
	public NucleiFamiliariDao() {
		dbConnection = DataAccessObject.getConnection();
	}
	
	public void createConvivente(NucleoFamiliare convivente) {
		String insertQuery = 	"INSERT INTO NUCLEO_FAMILIARE "
								+ "(CODICE_FISCALE,NOME,COGNOME,SESSO,DATA_NASCITA,"
								+ "TIPO_PARENTELA, CF_ASSISTITO_NF) VALUES(?,?,?,?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, convivente.getCodice_fiscale());
			pStmt.setString(2, convivente.getNome());
			pStmt.setString(3, convivente.getCognome());
			pStmt.setString(4, convivente.getSesso());
			pStmt.setDate(5, convivente.getData_nascita());
			pStmt.setInt(6, convivente.getTipo_parentela());
			pStmt.setString(7, convivente.getCf_assistito_nf());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return;
	}
	
	public void updateConvivente(NucleoFamiliare convivente) {

		String updateQuery = 	"UPDATE NUCLEO_FAMILIARE " 
								+ "SET NOME=?, COGNOME=?,SESSO=?, "
								+ "DATA_NASCITA=?, TIPO_PARENTELA=? "
								+ "WHERE CODICE_FISCALE=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, convivente.getNome());
			pStmt.setString(2, convivente.getCognome());
			pStmt.setString(3, convivente.getSesso());
			pStmt.setDate(4, convivente.getData_nascita());
			pStmt.setInt(5, convivente.getTipo_parentela());
			pStmt.setString(6, convivente.getCodice_fiscale());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<NucleoFamiliare> getAllConviventi(int jtStartIndex, int jtPageSize, String jtSorting, String cod_fiscale) {
		
		List<NucleoFamiliare> NucleoFamiliare = new ArrayList<NucleoFamiliare>();
		
		String query = 	"SELECT * FROM NUCLEO_FAMILIARE N " 
						+ "LEFT JOIN GRADI_PARENTELA G ON N.TIPO_PARENTELA=G.ID "
						+ "WHERE CF_ASSISTITO_NF=?"
						+ "ORDER BY " + jtSorting + " "
						+ "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
						+ Integer.toString(jtStartIndex);
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, cod_fiscale);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				NucleoFamiliare convivente = new NucleoFamiliare();

				convivente.setCodice_fiscale(rs.getString("CODICE_FISCALE"));
				convivente.setNome(rs.getString("NOME"));
				convivente.setCognome(rs.getString("COGNOME"));
				convivente.setSesso(rs.getString("SESSO"));
				convivente.setData_nascita(rs.getDate("DATA_NASCITA"));
				convivente.setTipo_parentela(rs.getInt("TIPO_PARENTELA"));
				convivente.setCf_assistito_nf(cod_fiscale);
				convivente.setDesc_tipo_parentela(rs.getString("DESCRIZIONE"));
				NucleoFamiliare.add(convivente);
			
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return NucleoFamiliare;
	}
	
	public int getCountConviventi(String cod_fiscale) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM NUCLEO_FAMILIARE " 
						+ "WHERE CF_ASSISTITO_NF=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, cod_fiscale);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				totalRecord = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return totalRecord;
	}
	
	public NucleoFamiliare getConvivente(String cod_fiscale) {
		
		NucleoFamiliare convivente = new NucleoFamiliare();
		
		String query = 	"SELECT * FROM NUCLEO_FAMILIARE N " 
						+ "LEFT JOIN GRADI_PARENTELA G ON N.TIPO_PARENTELA=G.ID "
						+ "WHERE CODICE_FISCALE=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, cod_fiscale);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {

				convivente.setCodice_fiscale(rs.getString("CODICE_FISCALE"));
				convivente.setNome(rs.getString("NOME"));
				convivente.setCognome(rs.getString("COGNOME"));
				convivente.setSesso(rs.getString("SESSO"));
				convivente.setData_nascita(rs.getDate("DATA_NASCITA"));
				convivente.setTipo_parentela(rs.getInt("TIPO_PARENTELA"));
				convivente.setCf_assistito_nf(cod_fiscale);
				convivente.setDesc_tipo_parentela(rs.getString("DESCRIZIONE"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return convivente;
	}
	
	public void deleteConvivente(NucleoFamiliare convivente) {

		String deleteQuery = "DELETE FROM NUCLEO_FAMILIARE WHERE CODICE_FISCALE=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setString(1, convivente.getCodice_fiscale());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
