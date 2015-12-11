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
import com.model.Stato_civile;

public class Stati_civiliDao {
	
	private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;
    
    public Stati_civiliDao() {
        dbConnection = DataAccessObject.getConnection();
    }
    
    
    public int createStato_civile(Stato_civile stato_civile) {
    	int autoIncKeyFromFunc = -1;
        String insertEnteQuery =  "INSERT INTO STATI_CIVILI (DESCRIZIONE) " +
        							"VALUES (?)";
        try {
            pStmt = dbConnection.prepareStatement(insertEnteQuery);
            pStmt.setString(1, stato_civile.getDescrizione());
            
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
    
    public void updateStato_civile(Stato_civile stato_civile) {
        String updateQuery = 	"UPDATE STATI_CIVILI SET DESCRIZIONE=? "
        						+ "WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, stato_civile.getDescrizione());
            
            pStmt.setInt(2, stato_civile.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void deleteStato_civile(int stato_civileId) {
        String deleteQuery = "DELETE FROM STATI_CIVILI WHERE ID = ?";
        try {
            pStmt = dbConnection.prepareStatement(deleteQuery);
            pStmt.setInt(1, stato_civileId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public int getStati_civiliRecordCount() {
        String countQuery = "SELECT COUNT(ID) AS TOTALREC FROM Stati_civili";
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
    

    public List<Stato_civile> getAllStati_civili(int jtStartIndex, int jtPageSize,
            String jtSorting) {
        List<Stato_civile> stato_civile = new ArrayList<Stato_civile>();

        String query = "SELECT * FROM STATI_CIVILI " + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Stato_civile stat_civile = new Stato_civile();

                stat_civile.setId(rs.getInt("ID"));
                stat_civile.setDescrizione(rs.getString("DESCRIZIONE"));
                
                stato_civile.add(stat_civile);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return stato_civile;
    }
    
    public Stato_civile getStato_civile(int id_stato_civile) {
        Stato_civile stato_civile = new Stato_civile();
        String newQuery = "SELECT * FROM STATI_CIVILI WHERE ID=? ";
        System.out.println("Stato_civile id: " + id_stato_civile);
        try {
            pStmt = dbConnection.prepareStatement(newQuery);
            pStmt.setInt(1, id_stato_civile);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                stato_civile.setId(rs.getInt(1));
                stato_civile.setDescrizione(rs.getString(2));
                
            }
        } catch (SQLException e) {
            System.err.println("Errore in getEnte: " + e.getMessage());
        }
        return stato_civile;
    }
    
    public boolean verifyStato_civile(String descrizione) {
        String countQuery = "SELECT ID FROM STATI_CIVILI WHERE DESCRIZIONE=?";
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
