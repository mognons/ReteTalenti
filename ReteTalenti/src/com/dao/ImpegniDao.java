package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.interceptor.UserAware;
import com.jdbc.DataAccessObject;
import com.model.Eccedenza;
import com.model.Impegno;
import com.model.User;

public class ImpegniDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;

	public ImpegniDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public int createImpegno(Impegno impegno) {
		int autoIncKeyFromFunc = -1;
		String insertQuery = 	"INSERT INTO IMPEGNI "
								+ "(ID_ECCEDENZA, ENTE_RICHIEDENTE, QTA_PRENOTATA, DATA_RITIRO, ORA_RITIRO, RITIRO_EFFETTUATO, TIMESTAMP, OPERATORE) " 
								+ "VALUES (?,?,?,?,?,0,NOW(),?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, impegno.getId_eccedenza());
			pStmt.setInt(2, impegno.getEnte_richiedente());
			pStmt.setInt(3, impegno.getQta_prenotata());
			pStmt.setDate(4, impegno.getData_ritiro());
			pStmt.setString(5, impegno.getOra_ritiro());
			pStmt.setString(6, impegno.getOperatore());
			pStmt.executeUpdate();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
				System.out.println("Impegno, ID inserito: " + autoIncKeyFromFunc);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return autoIncKeyFromFunc;
	}

	public void updateImpegno(Impegno impegno) {
		String updateQuery = 	"UPDATE IMPEGNI SET " 
								+ "QTA_PRENOTATA=?, DATA_RITIRO=? " 
								+ "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setInt(1, impegno.getQta_prenotata());
			pStmt.setDate(2, impegno.getData_ritiro());
			pStmt.setInt(3, impegno.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateRitiroImpegno(Impegno impegno) {
		String updateQuery = 	"UPDATE IMPEGNI SET " 
								+ "RITIRO_EFFETTUATO=? " 
								+ "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setBoolean(1, impegno.isRitiro_effettuato());
			pStmt.setInt(2, impegno.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<Impegno> getOwnImpegni(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
		List<Impegno> impegni = new ArrayList<Impegno>();
		String query = 	"select * from impegni "
						+ "WHERE ENTE_RICHIEDENTE=? "
						+ "ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Impegno impegno = new Impegno();

				impegno.setId(rs.getInt("ID"));
				impegno.setEnte_richiedente(rs.getInt("ENTE_RICHIEDENTE"));
				impegno.setQta_prenotata(rs.getInt("QTA_PRENOTATA"));
				impegno.setData_ritiro(rs.getDate("DATA_RITIRO"));
				impegno.setOra_ritiro(rs.getString("ORA_RITIRO"));
				impegno.setRitiro_effettuato(rs.getBoolean("RITIRO_EFFETTUATO"));
				impegni.add(impegno);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return impegni;
	}

	public List<Impegno> getOwnImpegniByEccedenza(int jtStartIndex, int jtPageSize, String jtSorting, User user, int id_eccedenza) {
		List<Impegno> impegni = new ArrayList<Impegno>();
		String query = 	"select * from impegni "
						+ "WHERE ENTE_RICHIEDENTE=? "
						+ "AND ID_ECCEDENZA=? "
						+ "ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			pStmt.setInt(2, id_eccedenza);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Impegno impegno = new Impegno();

				impegno.setId(rs.getInt("ID"));
				impegno.setEnte_richiedente(rs.getInt("ENTE_RICHIEDENTE"));
				impegno.setQta_prenotata(rs.getInt("QTA_PRENOTATA"));
				impegno.setData_ritiro(rs.getDate("DATA_RITIRO"));
				impegno.setOra_ritiro(rs.getString("ORA_RITIRO"));
				impegno.setRitiro_effettuato(rs.getBoolean("RITIRO_EFFETTUATO"));
				impegni.add(impegno);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return impegni;
	}

	public List<Impegno> getAllImpegni(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
		List<Impegno> impegni = new ArrayList<Impegno>();
		String query = 	"select * from impegni "
						+ "WHERE ID_ECCEDENZA=? "
						+ "ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Impegno impegno = new Impegno();

				impegno.setId(rs.getInt("ID"));
				impegno.setEnte_richiedente(rs.getInt("ENTE_RICHIEDENTE"));
				impegno.setQta_prenotata(rs.getInt("QTA_PRENOTATA"));
				impegno.setData_ritiro(rs.getDate("DATA_RITIRO"));
				impegno.setRitiro_effettuato(rs.getBoolean("RITIRO_EFFETTUATO"));
				impegni.add(impegno);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return impegni;
	}

	public List<Impegno> getAllImpegniByEccedenza(int jtStartIndex, int jtPageSize, String jtSorting, int eccedenza) {
		List<Impegno> impegni = new ArrayList<Impegno>();
		System.out.println("getAllImpegnyByEccedenza: " + eccedenza);
		String query = 	"SELECT * FROM IMPEGNI WHERE ID_ECCEDENZA = ? "
						+ "ORDER BY " + jtSorting
						+ " LIMIT " + jtPageSize
						+ " OFFSET " + jtStartIndex;
		System.out.println(query);
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, eccedenza);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				System.out.println("Inside while of getAllImpegnyByEccedenza");
				Impegno impegno = new Impegno();
				impegno.setId(rs.getInt("ID"));
				impegno.setEnte_richiedente(rs.getInt("ENTE_RICHIEDENTE"));
				impegno.setQta_prenotata(rs.getInt("QTA_PRENOTATA"));
				impegno.setData_ritiro(rs.getDate("DATA_RITIRO"));
				impegno.setOra_ritiro(rs.getString("ORA_RITIRO"));
				impegno.setRitiro_effettuato(rs.getBoolean("RITIRO_EFFETTUATO"));
				impegni.add(impegno);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return impegni;
	}

	public int getCountImpegniByEccedenza(int eccedenza) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM IMPEGNI " 
						+ "WHERE ID_ECCEDENZA=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, eccedenza);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				totalRecord = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return totalRecord;
	}

	public int getCountOwnImpegni(User user) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM IMPEGNI " 
						+ "WHERE ENTE_RICHIEDENTE=?";
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

	public int getCountOwnImpegniByEccedenza(User user, int id_eccedenza) {
		int totalRecord = 0;

		String query = 	"SELECT COUNT(*) FROM IMPEGNI " 
						+ "WHERE ENTE_RICHIEDENTE=? "
						+ "AND ID_ECCEDENZA=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, user.getEnte());
			pStmt.setInt(2, id_eccedenza);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				totalRecord = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return totalRecord;
	}

	public void deleteImpegno(Impegno impegno) {

		String deleteQuery = "DELETE FROM IMPEGNI WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, impegno.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}