/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.jdbc.DataAccessObject;
import com.model.Ente;
import com.model.User;

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
    
    public EntiDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public int createEnte(Ente ente) {
    	int autoIncKeyFromFunc = -1;
        String insertEnteQuery =  "INSERT INTO ENTI (DESCRIZIONE, RESPONSABILE, RESP_EMAIL, RESP_PHONE, PROVINCIA_ENTE, ENTE_EMPORIO) " +
        							"VALUES (?,?,?,?,?,?)";
        try {
            pStmt = dbConnection.prepareStatement(insertEnteQuery);
            pStmt.setString(1, ente.getDescrizione());
            pStmt.setString(2, ente.getResponsabile());
            pStmt.setString(3, ente.getResp_email());
            pStmt.setString(4, ente.getResp_phone());
            pStmt.setInt(5, ente.getProvincia_ente());
            pStmt.setBoolean(6, ente.getEnte_emporio());
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

    public void updateEnte(Ente ente) {
        String updateQuery = 	"UPDATE ENTI SET DESCRIZIONE=?, RESPONSABILE=?, RESP_EMAIL=?, "
        						+ "RESP_PHONE=?, PROVINCIA_ENTE=?, ENTE_EMPORIO=?  "
        						+ "WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, ente.getDescrizione());
            pStmt.setString(2, ente.getResponsabile());
            pStmt.setString(3, ente.getResp_email());
            pStmt.setString(4, ente.getResp_phone());
            pStmt.setInt(5, ente.getProvincia_ente());
            pStmt.setBoolean(6, ente.getEnte_emporio());
            pStmt.setInt(7, ente.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteEnte(int enteId) {
        String deleteQuery = "DELETE FROM ENTI WHERE ID = ?";
        try {
            pStmt = dbConnection.prepareStatement(deleteQuery);
            pStmt.setInt(1, enteId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public int getEntiRecordCount() {
        String countQuery = "SELECT COUNT(ID) AS TOTALREC FROM ENTI";
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

    public List<Ente> getOtherEnti(User utente, Boolean allEnti) {
        List<Ente> enti = new ArrayList<Ente>();
        String whereCondition1;
        if (allEnti)
        	whereCondition1 = "AND 1=1";
        else
        	whereCondition1 = "AND PROVINCIA_ENTE=" + utente.getProvinciaEnte();
        
        String query = 	"SELECT * FROM ENTI "
        				+ "WHERE ID<>? "
        				+ whereCondition1;
        try {
            PreparedStatement pStmt = dbConnection.prepareStatement(query);
            pStmt.setInt(1, utente.getEnte());
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Ente ente = new Ente();

                ente.setId(rs.getInt("ID"));
                ente.setDescrizione(rs.getString("DESCRIZIONE"));
                ente.setResponsabile(rs.getString("RESPONSABILE"));
                ente.setResp_email(rs.getString("RESP_EMAIL"));
                ente.setResp_phone(rs.getString("RESP_PHONE"));
                ente.setProvincia_ente(rs.getInt("PROVINCIA_ENTE"));
                ente.setEnte_emporio(rs.getBoolean("ENTE_EMPORIO"));
                enti.add(ente);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return enti;
    }

    public List<Ente> getAllEnti(int jtStartIndex, int jtPageSize,
            String jtSorting) {
        List<Ente> enti = new ArrayList<Ente>();

        String query = "SELECT * FROM ENTI " + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Ente ente = new Ente();

                ente.setId(rs.getInt("ID"));
                ente.setDescrizione(rs.getString("DESCRIZIONE"));
                ente.setResponsabile(rs.getString("RESPONSABILE"));
                ente.setResp_email(rs.getString("RESP_EMAIL"));
                ente.setResp_phone(rs.getString("RESP_PHONE"));
                ente.setProvincia_ente(rs.getInt("PROVINCIA_ENTE"));
                ente.setEnte_emporio(rs.getBoolean("ENTE_EMPORIO"));
                enti.add(ente);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return enti;
    }

    public Ente getEnte(int id_ente) {
        Ente ente = new Ente();
        String newQuery = "SELECT * FROM ENTI WHERE ID=? ";
        System.out.println("Ente id: " + id_ente);
        try {
            pStmt = dbConnection.prepareStatement(newQuery);
            pStmt.setInt(1, id_ente);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                ente.setId(rs.getInt(1));
                ente.setDescrizione(rs.getString(2));
                ente.setResponsabile(rs.getString(3));
                ente.setResp_email(rs.getString(4));
                ente.setResp_phone(rs.getString(5));
                ente.setProvincia_ente(rs.getInt(6));
                ente.setEnte_emporio(rs.getBoolean(7));
            }
        } catch (SQLException e) {
            System.err.println("Errore in getEnte: " + e.getMessage());
        }
        return ente;
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