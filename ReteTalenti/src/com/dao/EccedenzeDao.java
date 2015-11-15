package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.interceptor.UserAware;
import com.jdbc.DataAccessObject;
import com.model.Eccedenza;
import com.model.User;

public class EccedenzeDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;

	public EccedenzeDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public int createEccedenza(Eccedenza eccedenza) {
		int autoIncKeyFromFunc = -1;
		String insertQuery = "INSERT INTO ECCEDENZE "
				+ "(ENTE_CEDENTE, PRODOTTO, UDM, QTA, SCADENZA, TIMESTAMP, OPERATORE) " + "VALUES (?,?,?,?,?,NOW(),?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, eccedenza.getEnte_cedente());
			pStmt.setString(2, eccedenza.getProdotto());
			pStmt.setInt(3, eccedenza.getUdm());
			pStmt.setInt(4, eccedenza.getQta());
			pStmt.setDate(5, eccedenza.getScadenza());
			pStmt.setString(6, eccedenza.getOperatore());
			pStmt.executeUpdate();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
				System.out.println("Eccedenza, ID inserito: " + autoIncKeyFromFunc);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateEccedenza(Eccedenza eccedenza) {
		String updateQuery = "UPDATE ECCEDENZE SET " + "PRODOTTO=?, UDM=?, QTA=?, SCADENZA=? " + "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, eccedenza.getProdotto());
			pStmt.setInt(2, eccedenza.getUdm());
			pStmt.setInt(3, eccedenza.getQta());
			pStmt.setDate(4, (java.sql.Date) eccedenza.getScadenza());
			pStmt.setInt(5, eccedenza.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<Eccedenza> getOwnEccedenze(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
		List<Eccedenza> eccedenze = new ArrayList<Eccedenza>();
		String query = 	"select ecc.*, coalesce(imp.qta_prenotata,0)  qta_prenotata from eccedenze ecc "
						+ "left join (SELECT id_eccedenza, sum(qta_prenotata) as qta_prenotata from impegni "
						+ "group by id_eccedenza ) imp on ecc.id=imp.id_eccedenza "
						+ "WHERE ENTE_CEDENTE=? "
						+ "ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Eccedenza eccedenza = new Eccedenza();

				eccedenza.setId(rs.getInt("ID"));
				eccedenza.setEnte_cedente(rs.getInt("ENTE_CEDENTE"));
				eccedenza.setProdotto(rs.getString("PRODOTTO"));
				eccedenza.setUdm(rs.getInt("UDM"));
				eccedenza.setQta(rs.getInt("QTA"));
				eccedenza.setScadenza(rs.getDate("SCADENZA"));
				eccedenza.setQta_residua(eccedenza.getQta() - rs.getInt("QTA_PRENOTATA"));
				eccedenze.add(eccedenza);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return eccedenze;
	}

	public int getCountOwnEccedenze(User user) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM ECCEDENZE " 
						+ "WHERE ENTE_CEDENTE=?";
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

	public void deleteEccedenza(Eccedenza eccedenza) {

		String deleteQuery = "DELETE FROM ECCEDENZE WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, eccedenza.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}