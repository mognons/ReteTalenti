package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.Nazione;

public class NazioniDao {

	private Connection dbConnection;

	public NazioniDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public List<Nazione> getAllNazioni(String jtStartIndex, String jtPageSize) {
		List<Nazione> nazioni = new ArrayList<Nazione>();

		String query = "SELECT * FROM NAZIONI ORDER BY DENOMINAZIONE " + "LIMIT " + jtPageSize
				+ " OFFSET " + jtStartIndex;
		
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Nazione nazione = new Nazione();

				nazione.setCodice(rs.getString("CODICE"));
				nazione.setDenominazione(rs.getString("DENOMINAZIONE"));
				nazione.setSigla(rs.getString("SIGLA"));
				nazioni.add(nazione);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return nazioni;
	}

	public Integer getCountNazioni() {
		int totalRecordCount = 0;
		List<Nazione> nazioni = new ArrayList<Nazione>();

		String query = "SELECT COUNT(1) FROM NAZIONI";
		;
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				totalRecordCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return totalRecordCount;
	}

	public Nazione getNazioneByCodice(String codice) {

		Nazione nazione = new Nazione();
		String query = "SELECT * FROM NAZIONI WHERE CODICE=? ORDER BY DENOMINAZIONE";
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, codice);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				nazione.setCodice(rs.getString("CODICE"));
				nazione.setDenominazione(rs.getString("DENOMINAZIONE"));
				nazione.setSigla(rs.getString("SIGLA"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return nazione;
	}

}