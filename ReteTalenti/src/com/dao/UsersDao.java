package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.jdbc.DataAccessObject;
import com.model.*;
import com.utilities.MD5;

public class UsersDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private MD5 enc = new MD5();

	public UsersDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void createUser(User user) {
		String insertUserQuery =  "INSERT INTO USERS (ID, USERNAME, "
								+ "USERFIRSTNAME, USERLASTNAME, USEREMAIL, PASSWORD, USERPHONE, ENTE) VALUES (?,?,?,?,?,?,?,?)";
		String inserUserGroupQuery = "INSERT INTO USERGROUP (USERID, GROUPID) VALUES (?, 3)";
		if (verifyUsername(user.getUsername())) {
			try {
				int nextUserId = getNextUserID();
				pStmt = dbConnection.prepareStatement(insertUserQuery);
				pStmt.setInt(1, nextUserId);
				pStmt.setString(2, user.getUsername());
				pStmt.setString(3, user.getUserFirstname());
				pStmt.setString(4, user.getUserLastname());
				pStmt.setString(5, user.getUserEmail());
				pStmt.setString(6, enc.crypt(user.getPassword()));
				pStmt.setString(7, user.getUserPhone());
				pStmt.setInt(8, user.getEnte());
				pStmt.executeUpdate();
				pStmt = dbConnection.prepareStatement(inserUserGroupQuery);
				pStmt.setInt(1, nextUserId);
				pStmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new Error("Username " + user.getUsername()
					+ " already exists");
		}
	}

	public void updateUserFull(User user) {
		String updateQuery = 
				"UPDATE USERS SET  "
				+ "PASSWORD=?, USERFIRSTNAME=?, USERLASTNAME=?, USEREMAIL=?, USERPHONE=?, ENTE=?"
				+ " WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, enc.crypt(user.getPassword()));
			pStmt.setString(2, user.getUserFirstname());
			pStmt.setString(3, user.getUserLastname());
			pStmt.setString(4, user.getUserEmail());
			pStmt.setString(5, user.getUserPhone());
			pStmt.setInt(6, user.getEnte());
			pStmt.setString(7, user.getUsername());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateUser(User user) {
		String updateQuery = 
				"UPDATE USERS SET"
				+ " USERFIRSTNAME=?, USERLASTNAME=?, USEREMAIL=?, USERPHONE=?, ENTE=?"
				+ " WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, user.getUserFirstname());
			pStmt.setString(2, user.getUserLastname());
			pStmt.setString(3, user.getUserEmail());
			pStmt.setString(4, user.getUserPhone());
			pStmt.setInt(5, user.getEnte());
			pStmt.setString(6, user.getUsername());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}


	public void updateUserPassword(String username, String password) {
		String updateQuery = 
				"UPDATE USERS SET  "
				+ "PASSWORD=?"
				+ " WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, enc.crypt(password));
			pStmt.setString(2, username);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}


	public int getUsersRecordCount() {
		String countQuery = "SELECT COUNT(ID) AS TOTALREC FROM USERS";
		int result = 0;
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(countQuery);
			while (rs.next()) {
				result = rs.getInt("TOTALREC");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}


	public boolean verifyUsername(String username) {
		String countQuery = "SELECT ID FROM USERS WHERE USERNAME=?";
		int result = 0;
		try {
			pStmt = dbConnection.prepareStatement(countQuery);
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return (result == 0);
	}

	public boolean verifyLogin(String username, String password) throws Exception {
		System.out.println("Inside verifyLogin");
		int result = 0;
		if (username.isEmpty() || password.isEmpty()) {
			throw new Exception("No valid credential given");
		} else {
			String countQuery = "SELECT ID FROM USERS U, USERGROUP UG " +
								"WHERE U.ID = UG.USERID " + // Added to enforce user must belong to group(s) in order to login
								"AND USERNAME=? AND PASSWORD=?";
			try {
				pStmt = dbConnection.prepareStatement(countQuery);
				pStmt.setString(1, username);
				pStmt.setString(2, enc.crypt(password));
				ResultSet rs = pStmt.executeQuery();
				while (rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				throw new Exception("SQL Error");
			}
			
		}
		return (result != 0);
	}

	public int getNextUserID() {
		String countQuery = "SELECT COALESCE(MAX(ID), COUNT(ID)) AS TT FROM USERS";
		int result = 0;
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(countQuery);
			while (rs.next()) {
				result = rs.getInt("TT") + 1;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}


	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		String query = "SELECT * FROM USERS ORDER BY USERNAME";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				User user = new User();

				user.setId(rs.getInt("ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setUserFirstname(rs.getString("USERFIRSTNAME"));
				user.setUserLastname(rs.getString("USERLASTNAME"));
				user.setUserEmail(rs.getString("USEREMAIL"));
				user.setUserPhone(rs.getString("USERPHONE"));
				user.setEnte(rs.getInt("ENTE"));
				users.add(user);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return users;
	}

	public User getUser(String username) {
		User user = new User();
		List<Groups> groups = new ArrayList<Groups>();
		System.out.println("Inside getUser with " + username);
		String newQuery = "SELECT U.ID, U.USERNAME, U.USERFIRSTNAME, U.USERLASTNAME, " + 
						  "U.USEREMAIL, U.USERPHONE, U.ENTE, E.DESCRIZIONE, G.GROUPID, G.GROUPNAME " + 
						  "FROM USERS U ,USERGROUP UG , GROUPS G, ENTI E " +
						  "WHERE U.USERNAME=? AND " +
					 	  "U.ID = UG.USERID AND "+
					 	  "U.ENTE = E.ID AND "+
						  "G.GROUPID = UG.GROUPID";
		
		try {
			pStmt = dbConnection.prepareStatement(newQuery);
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setUserFirstname(rs.getString(3));
				user.setUserLastname(rs.getString(4));
				user.setUserEmail(rs.getString(5));
				user.setUserPhone(rs.getString(6));
				user.setEnte(rs.getInt(7));
				user.setDescrizioneEnte(rs.getString(8));
				Groups myGroup = new Groups();
				myGroup.setGroupId(rs.getInt(9));
				myGroup.setGroupName(rs.getString(10));
				groups.add(myGroup);
			}
			user.setGroups(groups);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return user;
	}

	public List<Groups> getGroupsByUser(int userId) {
		List<Groups> groups = new ArrayList<Groups>();
		String query = "SELECT GROUPS.GROUPID, GROUPS.GROUPNAME FROM USERS,GROUPS,USERGROUP"
				+ " WHERE 1=1"
				+ " AND USERS.ID = USERGROUP.USERID"
				+ " AND GROUPS.GROUPID = USERGROUP.GROUPID"
				+ " AND USERS.ID="
				+ Integer.toString(userId);
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Groups group = new Groups();

				group.setGroupId(rs.getInt("GROUPID"));
				group.setGroupName(rs.getString("GROUPNAME"));
				groups.add(group);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return groups;
	}

	public List<User> getUsersByEnte(int id) {
		List<User> users = new ArrayList<User>();
		String query = "SELECT * FROM USERS WHERE ENTE=" + Integer.toString(id);
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				User user = new User();

				user.setUsername(rs.getString("USERNAME"));
				user.setUserFirstname(rs.getString("USERFIRSTNAME"));
				user.setUserLastname(rs.getString("USERLASTNAME"));
				users.add(user);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return users;
	}
	
	public Groups getGroupById(int groupId) {
		Groups group = new Groups();
		String query = "SELECT GROUPS.GROUPID, GROUPS.GROUPNAME FROM GROUPS"
				+ " WHERE 1=1" + " AND GROUPS.GROUPID="
				+ Integer.toString(groupId);
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				group.setGroupId(rs.getInt("GROUPID"));
				group.setGroupName(rs.getString("GROUPNAME"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return group;
	}

	public void addUserGroup(UserGroup userGroup) throws Exception {
		String insertQuery = "INSERT INTO USERGROUP(USERID,GROUPID) VALUES (?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, userGroup.getUserId());
			pStmt.setInt(2, userGroup.getGroupId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public void deleteUser(String username) {
		int tempId = 0;
		String query = "SELECT ID FROM USERS WHERE USERNAME=?";		
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setString(1, username);
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				tempId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		String deleteQuery = "DELETE FROM USERS WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setString(1, username);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		deleteQuery = "DELETE FROM USERGROUP WHERE USERID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, tempId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public void deleteUserGroup(int userId, int groupId) {
		String deleteQuery = "DELETE FROM USERGROUP WHERE USERID=? AND GROUPID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, userId);
			pStmt.setInt(2, groupId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}


}