package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;

import com.model.Grado_parentela;

public class Gradi_parentelaDao {
	
	private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;
    
    public Gradi_parentelaDao() {
        dbConnection = DataAccessObject.getConnection();
    }
    
    public int createGradi_parentela(Grado_parentela gradi_parentela) {
    	int autoIncKeyFromFunc = -1;
        String insertEnteQuery =  "INSERT INTO Gradi_parentela (DESCRIZIONE) " +
        							"VALUES (?)";
        try {
            pStmt = dbConnection.prepareStatement(insertEnteQuery);
            pStmt.setString(1, gradi_parentela.getDescrizione());
            
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
    
    public void updateGradi_parentela(Grado_parentela gradi_parentela) {
        String updateQuery = 	"UPDATE Gradi_parentela SET DESCRIZIONE=? "
        						+ "WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, gradi_parentela.getDescrizione());
            
            pStmt.setInt(2, gradi_parentela.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void deleteGradi_parentela(int gradi_parentelaId) {
        String deleteQuery = "DELETE FROM Gradi_parentela WHERE ID = ?";
        try {
            pStmt = dbConnection.prepareStatement(deleteQuery);
            pStmt.setInt(1, gradi_parentelaId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public int getGradi_parentelaRecordCount() {
        String countQuery = "SELECT COUNT(ID) AS TOTALREC FROM Gradi_parentela";
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
    
    public List<Grado_parentela> getAllGradi_parentela(int jtStartIndex, int jtPageSize,
            String jtSorting) {
        List<Grado_parentela> gradi_parentela = new ArrayList<Grado_parentela>();

        String query = "SELECT * FROM Gradi_parentela " + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Grado_parentela grado_parentela = new Grado_parentela();

                grado_parentela.setId(rs.getInt("ID"));
                grado_parentela.setDescrizione(rs.getString("DESCRIZIONE"));
                
                gradi_parentela.add(grado_parentela);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return gradi_parentela;
    }
    
    public Grado_parentela getGradi_parentela(int id_gradi_parentela) {
        Grado_parentela gradi_parentela = new Grado_parentela();
        String newQuery = "SELECT * FROM Gradi_parentela WHERE ID=? ";
        System.out.println("Gradi_parentela id: " + id_gradi_parentela);
        try {
            pStmt = dbConnection.prepareStatement(newQuery);
            pStmt.setInt(1, id_gradi_parentela);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                gradi_parentela.setId(rs.getInt(1));
                gradi_parentela.setDescrizione(rs.getString(2));
                
            }
        } catch (SQLException e) {
            System.err.println("Errore in getEnte: " + e.getMessage());
        }
        return gradi_parentela;
    }
    
    public boolean verifyGradi_parentela(String descrizione) {
        String countQuery = "SELECT ID FROM Gradi_parentela WHERE DESCRIZIONE=?";
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
