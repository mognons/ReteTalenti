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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gminardi
 */
public class IndiceBisognoDao {

    private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;

    public IndiceBisognoDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public int createIndiceBisogno(IndiceBisogno indiceBisogno) {
        int autoIncKeyFromFunc = -1;
        String insertIndiceBisognoQuery = "INSERT INTO INDICE_BISOGNO (ISEE_EURO, CC_EURO, CA_EURO, CS_EURO, STATO_DISOC, SPESE_IMP, "
                + "URGENZA, ISEE_PUNTI, ENTRATE_NC_PUNTI, STATO_DISOC_PUNTI, SPESE_IMP_PUNTI, URGENZA_PUNTI, TOTALEPUNTI, "
                + "CF_ASSISTITO_IB, DATA_INSERIMENTO) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";

        try {
            pStmt = dbConnection.prepareStatement(insertIndiceBisognoQuery);
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

            pStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return autoIncKeyFromFunc;
    }

    public void updateIndiceBisogno(IndiceBisogno indiceBisogno) {
        String updateQuery = "UPDATE INDICE_BISOGNO SET "
                + "ISEE_EURO=?, CC_EURO=?, CA_EURO=?, CS_EURO=?, STATO_DISOC=?, SPESE_IMP=?,"
                + "URGENZA=?, ISEE_PUNTI=?, ENTRATE_NC_PUNTI=?, STATO_DISOC_PUNTI=?, SPESE_IMP_PUNTI=?, URGENZA_PUNTI=?, TOTALEPUNTI=?, "
                + "WHERE ID=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setInt(1, indiceBisogno.getIsee_euro());
            pStmt.setInt(1, indiceBisogno.getCc_euro());
            pStmt.setInt(1, indiceBisogno.getCa_euro());
            pStmt.setInt(1, indiceBisogno.getCs_euro());
            pStmt.setInt(1, indiceBisogno.getStato_disoc());
            pStmt.setInt(1, indiceBisogno.getSpese_imp());
            pStmt.setInt(1, indiceBisogno.getUrgenza());
            pStmt.setInt(1, indiceBisogno.getIsee_punti());
            pStmt.setInt(1, indiceBisogno.getEntrate_nc_punti());
            pStmt.setInt(1, indiceBisogno.getStato_disoc_punti());
            pStmt.setInt(1, indiceBisogno.getSpese_imp_punti());
            pStmt.setInt(1, indiceBisogno.getUrgenza_punti());
            pStmt.setInt(1, indiceBisogno.getTotalepunti());

            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteIndiceBisogno(int indicebisognoId) {
        String deleteQuery = "DELETE FROM INDICE_BISOGNO WHERE ID = ?";
        try {
            pStmt = dbConnection.prepareStatement(deleteQuery);
            pStmt.setInt(1, indicebisognoId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getIndiciBisognoRecordCount() {
        String countQuery = "SELECT COUNT(ID) AS TOTALREC FROM INDICE_BISOGNO";
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

    public List<IndiceBisogno> getAllIndiciBisogno(int jtStartIndex, int jtPageSize,
            String jtSorting) {
        List<IndiceBisogno> indicibisogno = new ArrayList<IndiceBisogno>();

        String query = "SELECT * FROM INDICE_BISOGNO " + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                IndiceBisogno indicebisogno = new IndiceBisogno();
                indicebisogno.setId(rs.getInt("id"));
                indicebisogno.setIsee_euro(rs.getInt("isee_euro"));
                indicebisogno.setCc_euro(rs.getInt("cc_euro"));
                indicebisogno.setCa_euro(rs.getInt("ca_euro"));
                indicebisogno.setCs_euro(rs.getInt("cs_euro"));
                indicebisogno.setStato_disoc(rs.getInt("stato_disoc"));
                indicebisogno.setSpese_imp(rs.getInt("spese_imp"));
                indicebisogno.setUrgenza(rs.getInt("urgenza"));
                indicebisogno.setIsee_punti(rs.getInt("isee_punti"));
                indicebisogno.setEntrate_nc_punti(rs.getInt("entrate_nc_punti"));
                indicebisogno.setStato_disoc_punti(rs.getInt("stato_disoc_punti"));
                indicebisogno.setSpese_imp_punti(rs.getInt("spese_imp_punti"));
                indicebisogno.setUrgenza_punti(rs.getInt("urgenza_punti"));
                indicebisogno.setTotalepunti(rs.getInt("totalepunti"));
                indicebisogno.setCf_assistito_ib(rs.getString("cf_assistito_ib"));

                indicibisogno.add(indicebisogno);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return indicibisogno;
    }

    public IndiceBisogno getIndiceBisogno(int id) {
        IndiceBisogno indicebisogno = new IndiceBisogno();
        String newQuery = "SELECT * FROM INDICE_BISOGNO WHERE ID=? ";

        try {
            pStmt = dbConnection.prepareStatement(newQuery);
            pStmt.setInt(1, id);
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
