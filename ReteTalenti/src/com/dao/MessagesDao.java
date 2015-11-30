package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.Message;
import com.model.User;

public class MessagesDao {

	private Connection dbConnection;

	public MessagesDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public List<Message> getValidMessages(int jtStartIndex, int jtPageSize, User loggedUser) {
		List<Message> messaggi = new ArrayList<Message>();

		String query = 	"SELECT * FROM MESSAGES "
						+ "WHERE MESSAGE_READ=FALSE "
						+ "AND (END_DATE>=NOW() OR END_DATE IS NULL) "
						+ "AND START_DATE<=NOW() "
						+ "AND (ENTE=? OR ENTE=0) "
						+ "ORDER BY TAG, START_DATE DESC "               
						+ "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
		                + Integer.toString(jtStartIndex);
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, loggedUser.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Message messaggio = new Message();
				messaggio.setId(rs.getInt("ID"));
				messaggio.setEnte(rs.getInt("ENTE"));
				messaggio.setTag(rs.getString("TAG"));
				messaggio.setMessage_text(rs.getString("MESSAGE_TEXT"));
				messaggio.setAction(rs.getString("ACTION"));
				messaggio.setKey1(rs.getString("KEY1"));
				messaggio.setKey2(rs.getInt("KEY2"));
				messaggio.setKey3(rs.getDate("KEY3"));
				messaggio.setStart_date(rs.getDate("START_DATE"));
				messaggio.setEnd_date(rs.getDate("END_DATE"));
				messaggio.setMessage_read(rs.getBoolean("MESSAGE_READ"));
				messaggio.setTimestamp(rs.getTimestamp("TIMESTAMP"));
				messaggi.add(messaggio);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return messaggi;
	}
	 
	public int getCountOfValidMessages(User loggedUser) {
		int recordCount = 0;
		String query = 	"SELECT COUNT(1) FROM MESSAGES "
						+ "WHERE MESSAGE_READ=FALSE "
						+ "AND (END_DATE>=NOW() OR END_DATE IS NULL) "
						+ "AND START_DATE<=NOW() "
						+ "AND (ENTE=? OR ENTE=0) "
						+ "ORDER BY START_DATE DESC";
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, loggedUser.getEnte());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return recordCount;
	}
	
	public List<Message> getAllMessages(int jtStartIndex, int jtPageSize) {
		List<Message> messaggi = new ArrayList<Message>();

		String query = 	"SELECT * FROM MESSAGES "
						+ "ORDER BY TAG, START_DATE DESC "               
						+ "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
		                + Integer.toString(jtStartIndex);
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				Message messaggio = new Message();
				messaggio.setId(rs.getInt("ID"));
				messaggio.setEnte(rs.getInt("ENTE"));
				messaggio.setTag(rs.getString("TAG"));
				messaggio.setMessage_text(rs.getString("MESSAGE_TEXT"));
				messaggio.setAction(rs.getString("ACTION"));
				messaggio.setKey1(rs.getString("KEY1"));
				messaggio.setKey2(rs.getInt("KEY2"));
				messaggio.setKey3(rs.getDate("KEY3"));
				messaggio.setStart_date(rs.getDate("START_DATE"));
				messaggio.setEnd_date(rs.getDate("END_DATE"));
				messaggio.setMessage_read(rs.getBoolean("MESSAGE_READ"));
				messaggi.add(messaggio);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return messaggi;
	}
	 
	public int getCountOfAllMessages() {
		int recordCount = 0;
		String query = 	"SELECT COUNT(1) FROM MESSAGES "
						+ "ORDER BY START_DATE DESC";
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return recordCount;
	}
	
	public void markMessageAsRead(int messageID) {
		String query = 	"UPDATE MESSAGES "
						+ "SET MESSAGE_READ=TRUE "
						+ "WHERE ID=?";
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, messageID);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return;
	}
	
	public void insertNewMessage(Message messaggio) {
		String query = 	"INSERT INTO MESSAGES "
						+ "(ENTE,TAG,MESSAGE_TEXT,ACTION,KEY1,KEY2,KEY3,START_DATE,END_DATE,MESSAGE_READ,TIMESTAMP) "
						+ "VALUES "
						+ "(?,?,?,?,?,?,?,?,?,FALSE,NOW())";
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, messaggio.getEnte());
			pStmt.setString(2,messaggio.getTag());
			pStmt.setString(3, messaggio.getMessage_text());
			pStmt.setString(4, messaggio.getAction());
			pStmt.setString(5, messaggio.getKey1());
			pStmt.setInt(6, messaggio.getKey2());
			pStmt.setDate(7, messaggio.getKey3());
			pStmt.setDate(8, messaggio.getStart_date());
			pStmt.setDate(9, messaggio.getEnd_date());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return;
	}
	
	public void deleteMessage(int messageID) {
		String query = 	"DELETE FROM MESSAGES "
						+ "WHERE ID=?";
		
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, messageID);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return;
	}
	
}