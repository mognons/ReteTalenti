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

public class CrudDao {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private Statement stmt;
	private MD5 enc = new MD5();

	public CrudDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public void createUser(User user) {
		String insertUserQuery =  "INSERT INTO USERS (ID, USERNAME, "
								+ "USERFIRSTNAME, USERLASTNAME, USEREMAIL, PASSWORD) VALUES (?,?,?,?,?,?)";
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
		String insertQuery = "UPDATE USERS SET  "
				+ "USERFIRSTNAME=?, USERLASTNAME=?, USEREMAIL=?, PASSWORD=?"
				+ " WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, user.getUserFirstname());
			pStmt.setString(2, user.getUserLastname());
			pStmt.setString(3, user.getUserEmail());
			pStmt.setString(4, enc.crypt(user.getPassword()));
			pStmt.setString(5, user.getUsername());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateUserSimple(User user) {
		String insertQuery = "UPDATE USERS SET  "
				+ "USERFIRSTNAME=?, USERLASTNAME=?, USEREMAIL=?"
				+ " WHERE USERNAME=?";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setString(1, user.getUserFirstname());
			pStmt.setString(2, user.getUserLastname());
			pStmt.setString(3, user.getUserEmail());
			pStmt.setString(4, user.getUsername());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateUserPassword(String username, String password) {
		String updateQuery = "UPDATE USERS SET  "
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

	public void addStudent(Student student) {
		String insertQuery = "INSERT INTO STUDENT(STUDENTID, NAME, "
				+ "DEPARTMENT, EMAIL) VALUES (?,?,?,?)";
		try {
			pStmt = dbConnection.prepareStatement(insertQuery);
			pStmt.setInt(1, student.getStudentId());
			pStmt.setString(2, student.getName());
			pStmt.setString(3, student.getDepartment());
			pStmt.setString(4, student.getEmailId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public int deleteStudent(int studentId) {
		String query = "SELECT * FROM CLASSES WHERE STUDENTID=?";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, studentId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return -1;
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String deleteQuery = "DELETE FROM STUDENT WHERE STUDENTID = ?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, studentId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

	public int getRecordCount() {
		String countQuery = "SELECT COUNT(STUDENTID) AS TOTALREC FROM STUDENT";
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

	public int getNextID() {
		String countQuery = "SELECT COALESCE(MAX(STUDENTID), COUNT(STUDENTID)) AS TT FROM STUDENT";
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
			String countQuery = "SELECT ID FROM USERS WHERE USERNAME=? AND PASSWORD=?";
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

	public void updateStudent(Student student) {
		String updateQuery = "UPDATE STUDENT SET NAME = ?, "
				+ "DEPARTMENT = ?, EMAIL = ? WHERE STUDENTID = ?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, student.getName());
			pStmt.setString(2, student.getDepartment());
			pStmt.setString(3, student.getEmailId());
			pStmt.setInt(4, student.getStudentId());
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<Student> getAllStudents(int jtStartIndex, int jtPageSize,
			String jtSorting) {
		List<Student> students = new ArrayList<Student>();

		String query = "SELECT * FROM STUDENT " + "ORDER BY " + jtSorting + " "
				+ "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
				+ Integer.toString(jtStartIndex);
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Student student = new Student();

				student.setStudentId(rs.getInt("STUDENTID"));
				student.setName(rs.getString("NAME"));
				student.setDepartment(rs.getString("DEPARTMENT"));
				student.setEmailId(rs.getString("EMAIL"));
				students.add(student);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return students;
	}

	// REGISTER SECTION
	public List<classRegister> getStudentsInClass(String today, int courseId, int moduleId) {
		List<classRegister> registerEntries = new ArrayList<classRegister>();
    	//DateTime dt = new DateTime();
    	//DateTimeFormatter fmt = ISODateTimeFormat.date();
    	int hasRegister = 0;
    	//String today = fmt.print(dt);
    	String checkRegister =  "SELECT COUNT(*) FROM REGISTER R " + 
								"WHERE R.COURSEID=? AND R.REGISTERDATE=? ";
		try {
			pStmt = dbConnection.prepareStatement(checkRegister);
			pStmt.setInt(1, courseId);
			pStmt.setString(2, today);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				hasRegister = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		if (hasRegister == 0) {
			// Create register entries, one for each student in class
			String insert = "INSERT INTO REGISTER (REGISTERDATE,COURSEID,STUDENTID,ENTRYTYPE) " + 
							"SELECT ?, COURSEID, STUDENTID, 'P' FROM CLASSES " + 
							"WHERE COURSEID=?";

					try {
						pStmt = dbConnection.prepareStatement(insert);
						pStmt.setString(1, today);
						pStmt.setInt(2, courseId);
						pStmt.executeUpdate();
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
		}
			
		String query = 	"SELECT S.*, R.* " +
						"FROM CLASSES C ,REGISTER R , STUDENT S " + 
						"WHERE C.COURSEID=? AND R.REGISTERDATE=? " + 
						"AND R.COURSEID=C.COURSEID AND R.STUDENTID=C.STUDENTID " +
						"AND C.STUDENTID=S.STUDENTID " +
						"ORDER BY S.NAME ";
		try {
			pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, courseId);
			pStmt.setString(2, today);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				classRegister registerEntry = new classRegister();

				registerEntry.setStudentId(rs.getInt("STUDENTID"));
				registerEntry.setName(rs.getString("NAME"));
				registerEntry.setEntryType(rs.getString("ENTRYTYPE"));
				registerEntry.setCourseId(courseId);
				registerEntry.setModuleId(moduleId);
				registerEntry.setAbsenceReason(rs.getString("ANNOTATION"));
				registerEntry.setRegisterValue(rs.getInt("REGISTERVALUE"));
				registerEntry.setRegisterDate(rs.getString("REGISTERDATE"));
				registerEntry.setRegisterId(rs.getInt("ID"));
				registerEntries.add(registerEntry);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return registerEntries;
	}

	public void updateClassRegister(classRegister event) {
		String updateQuery = "UPDATE REGISTER SET ANNOTATION=?, ENTRYTYPE = ?, REGISTERVALUE=? " + 
							 "WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(updateQuery);
			pStmt.setString(1, event.getAbsenceReason());
			pStmt.setString(2, event.getEntryType());
			pStmt.setInt(3, event.getRegisterValue());
			pStmt.setInt(4, event.getRegisterId());
			pStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

// END OF REGISTER SECTION
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		String query = "SELECT * FROM USERS ORDER BY ID";
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
				users.add(user);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return users;
	}

	public ResultSet getStudentsRS() {
		String query = "SELECT * FROM STUDENT ORDER BY STUDENTID ";
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public User getUser(String username) {
		User user = new User();
		List<Groups> groups = new ArrayList<Groups>();
		System.out.println("Inside getUser with " + username);
		String newQuery = "SELECT U.ID, U.USERNAME, U.USERFIRSTNAME, U.USERLASTNAME, " + 
						  "U.USEREMAIL, G.GROUPID, G.GROUPNAME " + 
						  "FROM USERS U ,USERGROUP UG , GROUPS G " +
						  "WHERE U.USERNAME=? AND " +
					 	  "U.ID = UG.USERID AND "+
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
				Groups myGroup = new Groups();
				myGroup.setGroupId(rs.getInt(6));
				myGroup.setGroupName(rs.getString(7));
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

	public void deleteUser(int userId) {
		String deleteQuery = "DELETE FROM USERS WHERE ID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, userId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		deleteQuery = "DELETE FROM USERGROUP WHERE USERID=?";
		try {
			pStmt = dbConnection.prepareStatement(deleteQuery);
			pStmt.setInt(1, userId);
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