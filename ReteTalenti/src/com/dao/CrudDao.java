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
				user.setUserPhone(rs.getString("USERPHONE"));
				user.setEnte(rs.getInt("ENTE"));
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


}