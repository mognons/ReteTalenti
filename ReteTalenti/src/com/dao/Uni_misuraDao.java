package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;

import com.model.Uni_misura;

public class Uni_misuraDao {
	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;
	public Uni_misuraDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public int createUni_misura(Uni_misura unitaMisura) {
		String insertUni_misuraQuery = "INSERT INTO UNI_MISURA (CODICE,DESCRIZIONE) VALUES(?,?)";
		int autoIncKeyFromFunc = 0;
		try {
			pStmt = dbConnection.prepareStatement(insertUni_misuraQuery);
			pStmt.setString(1, unitaMisura.getCodice());
			pStmt.setString(2, unitaMisura.getDescrizione());
			pStmt.executeUpdate();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateUni_misura(Uni_misura unitaMisura) {

		String updateQuery = "UPDATE UNI_MISURA SET CODICE=?, DESCRIZIONE=?  WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, unitaMisura.getCodice());
			pStmt.setString(2, unitaMisura.getDescrizione());
			pStmt.setInt(3, unitaMisura.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<Uni_misura> getAllUni_misura() {
		List<Uni_misura> uni_misura = new ArrayList<Uni_misura>();

		String query = "SELECT * FROM UNI_MISURA ORDER BY CODICE";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Uni_misura uniMisura = new Uni_misura();

				uniMisura.setId(rs.getInt("ID"));
				uniMisura.setCodice(rs.getString("CODICE"));
				uniMisura.setDescrizione(rs.getString("DESCRIZIONE"));
				uni_misura.add(uniMisura);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return uni_misura;
	}

	public void deleteUni_misura(int Uni_misuraId) {
		String deleteQuery = "DELETE FROM UNI_MISURA WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, Uni_misuraId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}



	public Uni_misura getUni_misuraById(int id) {
		String countQuery = "SELECT * FROM UNI_MISURA WHERE ID=?";
		Uni_misura uniMisura = new Uni_misura();
		try {
			pStmt = dbConnection.prepareStatement(countQuery);
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				uniMisura.setId(rs.getInt("ID"));
				uniMisura.setCodice(rs.getString("CODICE"));
				uniMisura.setDescrizione(rs.getString("DESCRIZIONE"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return uniMisura;
	}

}
