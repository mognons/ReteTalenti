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
    private String queryBase = "SELECT cod_fiscale AS 'Codice Fiscale',"
            + "nome AS 'Nome',"
            + "cognome AS 'Cognome',"
            + "sesso AS 'Genere', "
            + "S.descrizione AS 'Stato Civile',"
            + "luogo_nascita AS 'Luogo di Nascita',"
            + "DATE_FORMAT(data_nascita,'%d/%m/%Y') AS 'Data di Nascita', "
            + "N.DENOMINAZIONE AS 'Nazione', "
            + "indirizzo_residenza AS 'Indirizzo di Residenza',"
            + "citta_residenza AS 'Città di Residenza',"
            + "cap AS 'CAP',"
            + "P.sigla_autom AS 'Provincia',"
            + "permesso_soggiorno AS 'Perm. Sogg.',"
            + "telefono AS 'Recapito Telefonico', "
            + "email AS 'Indirizzo email',"
            + "num_documento AS 'Documento Identità', "
            + "E.descrizione AS 'Ente Assistente',"
            + "PE.DENOMINAZIONE AS 'Provincia Ente',"
            + "DATE_FORMAT(data_inserimento,'%d/%m/%Y') AS 'Inizio Assistenza',"
            + "DATE_FORMAT(data_fine_assistenza,'%d/%m/%Y') AS 'Fine Assistenza',"
            + "punteggio_idb AS 'Indice di Bisogno',"
            + "EN.descrizione as 'Emporio',"
            + "DATE_FORMAT(data_candidatura,'%d/%m/%Y') AS  'Candidatura',"
            + "DATE_FORMAT(data_accettazione,'%d/%m/%Y') AS  'Accettazione', "
            + "DATE_FORMAT(data_scadenza,'%d/%m/%Y') AS 'Scadenza',"
            + "DATE_FORMAT(data_dismissione,'%d/%m/%Y') AS 'Dimissione' "
            + "FROM ASSISTITI A "
            + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
            + "LEFT JOIN ENTI EN ON A.EMPORIO=EN.ID "
            + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
            + "LEFT JOIN PROVINCE PE ON E.PROVINCIA_ENTE=PE.COD_PROVINCIA "
            + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
            + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID ";
    
    private String orderBy = " ORDER BY PE.DENOMINAZIONE, E.DESCRIZIONE, A.COD_FISCALE";

    public ReportDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public ResultSet anagraficaCompleta() {

        ResultSet rs = null;
        String query = queryBase + orderBy;
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
        String query = queryBase + " WHERE A.ENTE_ASSISTENTE=" + user.getEnte() + orderBy;
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
        String query = queryBase + " WHERE E.PROVINCIA_ENTE= " + user.getProvinciaEnte() + orderBy;
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet graduatoriaProvinciale(User user) {

        ResultSet rs = null;
        String query = queryBase + " WHERE E.PROVINCIA_ENTE= " + user.getProvinciaEnte()
                + " AND PUNTEGGIO_IDB > 0 AND DATA_CANDIDATURA IS NULL AND DATA_ACCETTAZIONE IS NULL"
                + " ORDER BY PUNTEGGIO_IDB DESC, DATA_CANDIDATURA ASC";

        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet ritiri() {
        ResultSet rs = null;
        String query = 	"SELECT id_eccedenza AS 'Id Eccedenza',"
		                + "EC.prodotto,"
		                + "E.descrizione 'Ente Richiedente',"
		                + "qta_prenotata 'Quantità Prenotata',"
		                + "DATE_FORMAT(data_ritiro,'%d/%m/%Y') 'Data Ritiro',"
		                + "ora_ritiro 'Ora Ritiro',"
		                + "IF(ritiro_effettuato,'S','N') 'Ritiro Effettuato', "
		                + "DATE_FORMAT(I.timestamp,'%d/%m/%Y-%T') 'Data/Ora Inserimento',"
		                + "I.operatore AS 'Operatore' "
		                + "FROM impegni I "
		                + "LEFT JOIN eccedenze EC on I.id_eccedenza=EC.id "
		                + "LEFT JOIN enti E on I.ente_richiedente=E.id "
		                + "ORDER BY ID_ECCEDENZA, DATA_RITIRO";
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet eccedenze() {

        ResultSet rs = null;
        String query = "SELECT E.id AS 'Id Eccedenza', "
                + "en.descrizione as 'Ente Cedente',"
                + "prodotto 'Prodotto',"
                + "U.codice 'UdM ',"
                + "qta as 'Quantità',"
                + "DATE_FORMAT(scadenza,'%d/%m/%Y') 'Scadenza',"
                + "DATE_FORMAT(timestamp,'%d/%m/%Y/ %T') AS 'Data/Ora Inserimento',"
                + "operatore AS 'Operatore' "
                + "from eccedenze E "
                + "left join enti en on E.ente_cedente = en.id "
                + "left join uni_misura U on E.udm = U.id "
                + "ORDER BY E.ID";
        try {
            pStmt = dbConnection.prepareStatement(query);
            rs = pStmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

}
