/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.jdbc.DataAccessObject;
import com.model.IndiceBisogno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gminardi
 */
public class IndiceBisognoDao {

    private Connection dbConnection;
    private PreparedStatement pStmt;
    public IndiceBisognoDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public void updateAssistito(String codice_fiscale, int idb) {
    	System.err.println("CF: " + codice_fiscale + " IDB: " + idb);
    	String update = "UPDATE ASSISTITI "
    					+ "SET PUNTEGGIO_IDB=? "
    					+ "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(update);
            pStmt.setInt(1, idb);
            pStmt.setString(2, codice_fiscale);
            pStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void createIndiceBisogno(IndiceBisogno indiceBisogno) throws Exception {
        String insert = "INSERT INTO INDICI_BISOGNO (ISEE_EURO, CC_EURO, CA_EURO, CS_EURO, STATO_DISOC, SPESE_IMP, "
                + "URGENZA, ISEE_PUNTI, ENTRATE_NC_PUNTI, STATO_DISOC_PUNTI, SPESE_IMP_PUNTI, URGENZA_PUNTI, TOTALEPUNTI, "
                + "CF_ASSISTITO_IB, DATA_INSERIMENTO, OPERATORE) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)";
        try {
            pStmt = dbConnection.prepareStatement(insert);
            pStmt.setInt(1, indiceBisogno.getIsee_euro());
            pStmt.setInt(2, indiceBisogno.getCc_euro());
            pStmt.setInt(3, indiceBisogno.getCa_euro());
            pStmt.setInt(4, indiceBisogno.getCs_euro());
            pStmt.setInt(5, indiceBisogno.getStato_disoc());
            pStmt.setInt(6, indiceBisogno.getSpese_imp());
            pStmt.setInt(7, indiceBisogno.getUrgenza());
            pStmt.setInt(8, indiceBisogno.getIsee_punti());
            pStmt.setInt(9, indiceBisogno.getEntrate_nc_punti());
            pStmt.setInt(10, indiceBisogno.getStato_disoc_punti());
            pStmt.setInt(11, indiceBisogno.getSpese_imp_punti());
            pStmt.setInt(12, indiceBisogno.getUrgenza_punti());
            pStmt.setInt(13, indiceBisogno.getTotalepunti());
            pStmt.setString(14, indiceBisogno.getCf_assistito_ib());
            pStmt.setInt(15,indiceBisogno.getOperatore());

            pStmt.executeUpdate();
			updateAssistito(indiceBisogno.getCf_assistito_ib(), indiceBisogno.getTotalepunti());

        } catch (SQLException e) {
        	System.err.println("Errore in create IDB");
            System.err.println(e.getMessage());
        }
        return;
    }

    public void updateIndiceBisogno(IndiceBisogno indiceBisogno) {
        String updateQuery = "UPDATE INDICI_BISOGNO SET "
                			+ "ISEE_EURO=?, CC_EURO=?, CA_EURO=?, CS_EURO=?, "
                			+ "STATO_DISOC=?, SPESE_IMP=?, URGENZA=?, "
                			+ "ISEE_PUNTI=?, ENTRATE_NC_PUNTI=?, STATO_DISOC_PUNTI=?, "
                			+ "SPESE_IMP_PUNTI=?, URGENZA_PUNTI=?, TOTALEPUNTI=? "
                			+ "WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setInt(1, indiceBisogno.getIsee_euro());
            pStmt.setInt(2, indiceBisogno.getCc_euro());
            pStmt.setInt(3, indiceBisogno.getCa_euro());
            pStmt.setInt(4, indiceBisogno.getCs_euro());
            
            pStmt.setInt(5, indiceBisogno.getStato_disoc());
            pStmt.setInt(6, indiceBisogno.getSpese_imp());
            pStmt.setInt(7, indiceBisogno.getUrgenza());
            
            pStmt.setInt(8, indiceBisogno.getIsee_punti());
            pStmt.setInt(9, indiceBisogno.getEntrate_nc_punti());
            pStmt.setInt(10, indiceBisogno.getStato_disoc_punti());
            pStmt.setInt(11, indiceBisogno.getSpese_imp_punti());
            pStmt.setInt(12, indiceBisogno.getUrgenza_punti());
            pStmt.setInt(13, indiceBisogno.getTotalepunti());
            pStmt.setInt(14, indiceBisogno.getId());
            pStmt.executeUpdate();
			updateAssistito(indiceBisogno.getCf_assistito_ib(), indiceBisogno.getTotalepunti());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

 
    public IndiceBisogno getIndiceBisogno(String codice_fiscale) {
        IndiceBisogno indicebisogno = new IndiceBisogno();
        String newQuery = "SELECT * FROM INDICI_BISOGNO WHERE cf_assistito_ib=? ";

        try {
            pStmt = dbConnection.prepareStatement(newQuery);
            pStmt.setString(1, codice_fiscale);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                indicebisogno.setId(rs.getInt(1));
                indicebisogno.setIsee_euro(rs.getInt(2));
                indicebisogno.setCc_euro(rs.getInt(3));
                indicebisogno.setCa_euro(rs.getInt(4));
                indicebisogno.setCs_euro(rs.getInt(5));
                indicebisogno.setStato_disoc(rs.getInt(6));
                indicebisogno.setSpese_imp(rs.getInt(7));
                indicebisogno.setUrgenza(rs.getInt(8));
                indicebisogno.setIsee_punti(rs.getInt(9));
                indicebisogno.setEntrate_nc_punti(rs.getInt(10));
                indicebisogno.setStato_disoc_punti(rs.getInt(11));
                indicebisogno.setSpese_imp_punti(rs.getInt(12));
                indicebisogno.setUrgenza_punti(rs.getInt(13));
                indicebisogno.setTotalepunti(rs.getInt(14));
                indicebisogno.setCf_assistito_ib(rs.getString(15));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return indicebisogno;
    }
}