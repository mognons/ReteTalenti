/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.jdbc.DataAccessObject;
import com.model.Ente;
import com.model.Groups;
import com.model.Student;
import com.model.User;
import com.utilities.MD5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntiDao {

    private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;
    private MD5 enc = new MD5();

    public EntiDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public void createEnte(Ente ente) {
        String insertEnteQuery = "INSERT INTO ENTI (DESCRIZIONE, RESPONSABILE, RESP_EMAIL, RESP_PHONE, PROVINCIA_ENTE) VALUES (?,?,?,?,?)";
        try {
            pStmt = dbConnection.prepareStatement(insertEnteQuery);
            pStmt.setString(1, ente.getDescrizione());
            pStmt.setString(2, ente.getResponsabile());
            pStmt.setString(3, ente.getResp_email());
            pStmt.setString(4, ente.getResp_phone());
            pStmt.setString(5, ente.getProvincia_ente());
            pStmt.executeUpdate();
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateEnte(Ente ente) {
        String updateQuery = "UPDATE ENTE SET DESCRIZIONE=?, RESPONSABILE=?, RESP_EMAIL=?, RESP_PHONE=?, PROVINCIA_ENTE=? WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, ente.getDescrizione());
            pStmt.setString(2, ente.getResponsabile());
            pStmt.setString(3, ente.getResp_email());
            pStmt.setString(4, ente.getResp_phone());
            pStmt.setString(5, ente.getProvincia_ente());
            pStmt.setInt(6, ente.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int deleteEnte(int enteId) {
        String query = "SELECT * FROM ENTI WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(query);
            pStmt.setInt(1, enteId);
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

    public User getUser(String username) {
        User user = new User();
        List<Groups> groups = new ArrayList<Groups>();
        System.out.println("Inside getUser with " + username);
        String newQuery = "SELECT U.ID, U.USERNAME, U.USERFIRSTNAME, U.USERLASTNAME, "
                + "U.USEREMAIL, U.USERPHONE, U.ENTE, E.DESCRIZIONE, G.GROUPID, G.GROUPNAME "
                + "FROM USERS U ,USERGROUP UG , GROUPS G, ENTI E "
                + "WHERE U.USERNAME=? AND "
                + "U.ID = UG.USERID AND "
                + "U.ENTE = E.ID AND "
                + "G.GROUPID = UG.GROUPID";

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

    public boolean verifyEnte(String descrizione) {
        String countQuery = "SELECT ID FROM ENTI WHERE DESCRIZIONE=?";
        int result = 0;
        try {
            pStmt = dbConnection.prepareStatement(countQuery);
            pStmt.setString(1, descrizione);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return (result == 0);
    }

}
