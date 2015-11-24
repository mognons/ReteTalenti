package com.dao;

import com.jdbc.DataAccessObject;
import com.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDao {

    private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;
    private String queryBase = "SELECT cod_fiscale AS 'codice fiscale',"
                + "nome,"
                + "cognome,"
                + "sesso, "
                + "S.descrizione AS 'stato civile',"
                + "luogo_nascita AS 'luogo di nascita',"
                + "data_nascita AS 'data di nascita', "
                + "N.sigla, "
                + "indirizzo_residenza 'indirizzo di residenza',"
                + "citta_residenza AS 'citta di residenza',"
                + "cap,"
                + "P.sigla_autom AS provincia,"
                + "permesso_soggiorno AS 'perm. sogg.',"
                + "telefono,"
                + "email,"
                + "num_documento AS 'num. documento', "
                + "E.descrizione AS 'ente',"
                + "punteggio_idb AS 'Indice bisogno',"
                + "EN.descrizione as 'emporio',"
                + "DATE_FORMAT(data_inserimento,'%d/%m/%Y') AS 'iscrizione',"
                + "DATE_FORMAT(data_fine_assistenza,'%d/%m/%Y') AS 'fine assistenza',"
                + "DATE_FORMAT(data_candidatura,'%d/%m/%Y') AS  'candidatura',"
                + "DATE_FORMAT(data_accettazione,'%d/%m/%Y') AS  'accettazione', "
                + "DATE_FORMAT(data_scadenza,'%d/%m/%Y') AS 'scadenza',"
                + "DATE_FORMAT(data_dismissione,'%d/%m/%Y') AS 'dimissione' "
                + "FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN ENTI EN ON A.EMPORIO=EN.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID";

    public ReportDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public ResultSet anagraficaCompleta() {

        ResultSet rs = null;
        String query = queryBase;
        System.out.println(query);
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet anagraficaXEnteUser(User user) {

        ResultSet rs = null;
        String query = queryBase + " WHERE A.ENTE_ASSISTENTE=" + user.getEnte();
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet anagraficaXProvinciaEnteUser(User user) {

        ResultSet rs = null;
        String query = queryBase+ " WHERE A.PROVINCIA= " + user.getProvinciaEnte();
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

}
