package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
		String insertQuery = 	"INSERT INTO ECCEDENZE "
								+ "(ENTE_CEDENTE, PRODOTTO, UDM, QTA, SCADENZA, TIMESTAMP, OPERATORE) "
								+ "VALUES (?,?,?,?,?,NOW(),?)";
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
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateEccedenza(Eccedenza eccedenza) {
		String updateQuery = 	"UPDATE ECCEDENZE SET " 
								+ "PRODOTTO=?, UDM=?, QTA=?, SCADENZA=? " 
								+ "WHERE ID=?";
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
		String query = 	"SELECT ECC.*, COALESCE(IMP.QTA_PRENOTATA,0)  QTA_PRENOTATA FROM ECCEDENZE ECC "
						+ "LEFT JOIN (SELECT ID_ECCEDENZA, SUM(QTA_PRENOTATA) AS QTA_PRENOTATA FROM IMPEGNI "
						+ "GROUP BY ID_ECCEDENZA ) IMP ON ECC.ID=IMP.ID_ECCEDENZA "
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

	public List<Eccedenza> getAvailableEccedenze(int jtStartIndex, int jtPageSize, String jtSorting, String jtFilter, 
			User user) {
		List<Eccedenza> eccedenze = new ArrayList<Eccedenza>();
		String query = 	"SELECT * FROM ("
						+ "SELECT ECC.*, (QTA-COALESCE(IMP.QTA_PRENOTATA,0))  QTA_RESIDUA , OWN_IMP, CAN_EDIT "
						+ "FROM ECCEDENZE ECC " 
						+ "LEFT JOIN (SELECT ID_ECCEDENZA, SUM(QTA_PRENOTATA) AS QTA_PRENOTATA FROM IMPEGNI "
						+ "GROUP BY ID_ECCEDENZA ) IMP ON ECC.ID=IMP.ID_ECCEDENZA "
						+ "LEFT JOIN (SELECT ID_ECCEDENZA AS OWN_IMP, NOT(RITIRO_EFFETTUATO) AS CAN_EDIT FROM IMPEGNI "
						+ "WHERE ENTE_RICHIEDENTE=?) O_IMP ON ECC.ID=O_IMP.OWN_IMP "
						+ "INNER JOIN ENTI E ON E.ID=ECC.ENTE_CEDENTE "
						+ "WHERE ENTE_CEDENTE<>? "
						+ "AND PROVINCIA_ENTE=? "
						+ "AND SCADENZA>=NOW() ) ECCE "
						+ "WHERE QTA_RESIDUA <>0 "
						+ jtFilter 
						+ " ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			pStmt.setInt(2, user.getEnte());
			pStmt.setInt(3, user.getProvinciaEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Eccedenza eccedenza = new Eccedenza();

				eccedenza.setId(rs.getInt("ID"));
				eccedenza.setEnte_cedente(rs.getInt("ENTE_CEDENTE"));
				eccedenza.setProdotto(rs.getString("PRODOTTO"));
				eccedenza.setUdm(rs.getInt("UDM"));
				eccedenza.setQta(rs.getInt("QTA"));
				eccedenza.setScadenza(rs.getDate("SCADENZA"));
				eccedenza.setQta_residua(rs.getInt("QTA_RESIDUA"));
				eccedenza.setCan_edit(rs.getBoolean("CAN_EDIT"));
				eccedenza.setOwn_impegno(rs.getInt("OWN_IMP"));;
				eccedenze.add(eccedenza);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return eccedenze;
	}

	public int getCountAvailableEccedenze(String jtFilter, User user) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM ("
						+ "SELECT ECC.*, (QTA-COALESCE(IMP.QTA_PRENOTATA,0))  QTA_RESIDUA FROM ECCEDENZE ECC " 
						+ "LEFT JOIN (SELECT ID_ECCEDENZA, SUM(QTA_PRENOTATA) AS QTA_PRENOTATA FROM IMPEGNI "
						+ "GROUP BY ID_ECCEDENZA ) IMP ON ECC.ID=IMP.ID_ECCEDENZA "
						+ "WHERE ENTE_CEDENTE<>? "
						+ " AND SCADENZA>=NOW() "
						+ ") ECCE "
						+ "WHERE QTA_RESIDUA <>0 " + jtFilter;
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