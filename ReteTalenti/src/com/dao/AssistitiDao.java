package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.Assistito;
import com.model.Groups;
import com.model.Tuple;
import com.model.User;

public class AssistitiDao {

    private Connection dbConnection;
    private PreparedStatement pStmt;
    private Statement stmt;

    public AssistitiDao() {
        dbConnection = DataAccessObject.getConnection();
    }

    public void createAssistito(Assistito assistito) {
        String insertAssistitoQuery = "INSERT INTO ASSISTITI (NOME,COGNOME,SESSO,STATO_CIVILE, "
                + "LUOGO_NASCITA,DATA_NASCITA,NAZIONALITA,INDIRIZZO_RESIDENZA, "
                + "CITTA_RESIDENZA,CAP,PROVINCIA,PERMESSO_SOGGIORNO,TELEFONO,EMAIL, "
                + "NUM_DOCUMENTO,ENTE_ASSISTITI,DATA_INSERIMENTO,DATA_FINE_ASSISTENZA, "
                + "DATA_CANDIDATURA,DATA_ACCETTAZIONE,DATA_SCADENZA, DATA_DISMISSIONE,OPERATORE) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),null,null,null,null,null,?)";
        try {
            pStmt = dbConnection.prepareStatement(insertAssistitoQuery);
            pStmt.setString(1, assistito.getNome());
            pStmt.setString(2, assistito.getCognome());
            pStmt.setString(3, assistito.getSesso());
            pStmt.setInt(4, assistito.getStato_civile());
            pStmt.setString(5, assistito.getLuogo_nascita());
            pStmt.setDate(6, assistito.getData_nascita());
            pStmt.setString(7, assistito.getNazionalita());
            pStmt.setString(8, assistito.getIndirizzo_residenza());
            pStmt.setString(9, assistito.getCitta_residenza());
            pStmt.setString(10, assistito.getCap());
            pStmt.setInt(11, assistito.getProvincia());
            pStmt.setString(12, assistito.getPermesso_soggiorno());
            pStmt.setString(13, assistito.getTelefono());
            pStmt.setString(14, assistito.getEmail());
            pStmt.setString(15, assistito.getNum_documento());
            pStmt.setInt(16, assistito.getEnte_assistente());
            pStmt.setInt(17, assistito.getOperatore());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return;
    }

    public void updateAssistito(Assistito assistito) {

        String updateQuery = "UPDATE ASSISTITI SET NOME=?, COGNOME=?,SESSO=?,STATO_CIVILE=?,LUOGO_NASCITA=?, "
                + "DATA_NASCITA=?, NAZIONALITA=?,INDIRIZZO_RESIDENZA=?,CITTA_RESIDENZA=?,CAP=?,PROVINCIA=?, "
                + "PERMESSO_SOGGIORNO=?,TELEFONO=?,EMAIL=?,NUM_DOCUMENTO=? "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, assistito.getNome());
            pStmt.setString(2, assistito.getCognome());
            pStmt.setString(3, assistito.getSesso());
            pStmt.setInt(4, assistito.getStato_civile());
            pStmt.setString(5, assistito.getLuogo_nascita());
            pStmt.setDate(6, assistito.getData_nascita());
            pStmt.setString(7, assistito.getNazionalita());
            pStmt.setString(8, assistito.getIndirizzo_residenza());
            pStmt.setString(9, assistito.getCitta_residenza());
            pStmt.setString(10, assistito.getCap());
            pStmt.setInt(11, assistito.getProvincia());
            pStmt.setString(12, assistito.getPermesso_soggiorno());
            pStmt.setString(13, assistito.getTelefono());
            pStmt.setString(14, assistito.getEmail());
            pStmt.setString(15, assistito.getNum_documento());
            pStmt.setString(16, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateEnteAssistito(String codice_fiscale, int ente_destinazione) {

        String updateQuery = "UPDATE ASSISTITI "
        		+ "SET ENTE_ASSISTENTE=? "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setInt(1, ente_destinazione);
            pStmt.setString(2, codice_fiscale);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void riattivaAssistito(String codice_fiscale) {

        String updateQuery = "UPDATE ASSISTITI "
        		+ "SET DATA_FINE_ASSISTENZA=NULL "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, codice_fiscale);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Tuple verifyCF(String cf_search) {
        Tuple cfFound = new Tuple();
        String query = "SELECT * FROM CHECK_CF WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(query);
            pStmt.setString(1, cf_search);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                cfFound.setStringa1(rs.getString(1));
                cfFound.setStringa2(rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cfFound;
    }

    public List<Assistito> getAllAssistiti(int jtStartIndex, int jtPageSize, String jtSorting, User user, String cf_search, String cognome_search) {
        if (jtSorting == null) {
            jtSorting = "COD_FISCALE ASC";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";
        if (user.getGroupId() == 3) {
            whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        } else if (user.getGroupId() == 2) {
            whereCondition2 = "AND PROVINCIA_ENTE=" + user.getProvinciaEnte() + " ";
        }
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + whereCondition3 + " "
                + whereCondition4 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);

        try {
            pStmt = dbConnection.prepareStatement(query);
            //pStmt.setInt(1, user.getEnte());
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public List<Assistito> getGraduatoria(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
        if (jtSorting == null) {
            jtSorting = "PUNTEGGIO_IDB DESC, DATA_INSERIMENTO ASC ";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND PUNTEGGIO_IDB > 0 AND DATA_CANDIDATURA IS NULL AND DATA_ACCETTAZIONE IS NULL ";

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);

        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public List<Assistito> getCandidati(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
        if (jtSorting == null) {
            jtSorting = "PUNTEGGIO_IDB DESC, DATA_CANDIDATURA ASC";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_CANDIDATURA IS NOT NULL AND DATA_ACCETTAZIONE IS NULL ";

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public List<Assistito> getCandidatiProvincia(int jtStartIndex, int jtPageSize, String jtSorting, User user,
            String cf_search, String cognome_search) {
        if (jtSorting == null) {
            jtSorting = "PUNTEGGIO_IDB DESC, DATA_CANDIDATURA ASC";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";
        whereCondition1 = "AND PROVINCIA=" + user.getProvinciaEnte() + " ";
        whereCondition2 = "AND DATA_CANDIDATURA IS NOT NULL AND DATA_ACCETTAZIONE IS NULL ";
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + whereCondition3 + " "
                + whereCondition4 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public List<Assistito> getInseriti(int jtStartIndex, int jtPageSize, String jtSorting, User user) {
        if (jtSorting == null) {
            jtSorting = "PUNTEGGIO_IDB DESC, DATA_CANDIDATURA ASC";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_ACCETTAZIONE IS NOT NULL AND DATA_DISMISSIONE IS NULL ";

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN ENTI EMP ON A.EMPORIO=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("E.DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setEmporio(rs.getInt("EMPORIO"));
                assistito.setDesc_emporio(rs.getString("EMP.DESCRIZIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public List<Assistito> getAnagraficaEmporio(int jtStartIndex, int jtPageSize, String jtSorting, User user,
            String cf_search, String cognome_search) {
        if (jtSorting == null) {
            jtSorting = "PUNTEGGIO_IDB DESC, DATA_CANDIDATURA ASC";
        }

        List<Assistito> assistiti = new ArrayList<Assistito>();
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";

        whereCondition1 = "AND EMPORIO=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_ACCETTAZIONE IS NOT NULL AND DATA_DISMISSIONE IS NULL ";
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN ENTI EMP ON A.EMPORIO=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + whereCondition3 + " "
                + whereCondition4 + " "
                + "ORDER BY " + jtSorting + " "
                + "LIMIT " + Integer.toString(jtPageSize) + " OFFSET "
                + Integer.toString(jtStartIndex);
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Assistito assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("E.DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setEmporio(rs.getInt("EMPORIO"));
                assistito.setDesc_emporio(rs.getString("EMP.DESCRIZIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
                assistiti.add(assistito);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistiti;
    }

    public int getCountAssistiti(User user, String cf_search, String cognome_search) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";
        if (user.getGroupId() == 3) {
            whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        } else if (user.getGroupId() == 2) {
            whereCondition2 = "AND PROVINCIA_ENTE=" + user.getProvinciaEnte() + " ";
        }
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2
                + whereCondition3
                + whereCondition4;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public int getCountGraduatoria(User user) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND PUNTEGGIO_IDB > 0 AND DATA_CANDIDATURA IS NULL AND DATA_ACCETTAZIONE IS NULL ";

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public int getCountCandidati(User user) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_CANDIDATURA IS NOT NULL AND DATA_ACCETTAZIONE IS NULL ";

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public int getCountCandidatiProvincia(User user, String cf_search, String cognome_search) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";
        whereCondition1 = "AND PROVINCIA=" + user.getProvinciaEnte() + " ";
        whereCondition2 = "AND DATA_CANDIDATURA IS NOT NULL AND DATA_ACCETTAZIONE IS NULL ";
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + whereCondition3 + " "
                + whereCondition4;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public int getCountInseriti(User user) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        whereCondition1 = "AND ENTE_ASSISTENTE=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_ACCETTAZIONE IS NOT NULL AND DATA_DISMISSIONE IS NULL ";

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public int getCountAnagraficaEmporio(User user, String cf_search, String cognome_search) {
        int totalRecord = 0;
        String whereCondition1 = "AND 1=1 ";
        String whereCondition2 = "AND 1=1 ";
        String whereCondition3 = "AND 1=1 ";
        String whereCondition4 = "AND 1=1 ";
        whereCondition1 = "AND EMPORIO=" + user.getEnte() + " ";
        whereCondition2 = "AND DATA_ACCETTAZIONE IS NOT NULL AND DATA_DISMISSIONE IS NULL ";
        if (cf_search != null || cf_search != "") {
            whereCondition3 = "AND COD_FISCALE LIKE '" + cf_search + "%' ";
        }
        if (cognome_search != null || cognome_search != "") {
            whereCondition4 = "AND COGNOME LIKE '" + cognome_search + "%' ";
        }

        String query = "SELECT COUNT(*) FROM ASSISTITI A "
                + "WHERE 1=1 "
                + whereCondition1 + " "
                + whereCondition2 + " "
                + whereCondition3 + " "
                + whereCondition4;
        try {
            pStmt = dbConnection.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                totalRecord = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalRecord;
    }

    public Assistito getAssistito(String cf_search) {
        Assistito assistito = null;
        String query = "SELECT * FROM ASSISTITI A "
                + "LEFT JOIN ENTI E ON A.ENTE_ASSISTENTE=E.ID "
                + "LEFT JOIN PROVINCE P ON A.PROVINCIA=P.COD_PROVINCIA "
                + "LEFT JOIN NAZIONI N ON A.NAZIONALITA=N.CODICE "
                + "LEFT JOIN STATI_CIVILI S ON A.STATO_CIVILE=S.ID "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(query);
            pStmt.setString(1, cf_search);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                assistito = new Assistito();

                assistito.setCod_fiscale(rs.getString("COD_FISCALE"));
                assistito.setNome(rs.getString("NOME"));
                assistito.setCognome(rs.getString("COGNOME"));
                assistito.setSesso(rs.getString("SESSO"));
                assistito.setStato_civile(rs.getInt("STATO_CIVILE"));
                assistito.setDesc_stato_civile(rs.getString("S.DESCRIZIONE"));
                assistito.setLuogo_nascita(rs.getString("LUOGO_NASCITA"));
                assistito.setData_nascita(rs.getDate("DATA_NASCITA"));
                assistito.setNazionalita(rs.getString("NAZIONALITA"));
                assistito.setDenominazione(rs.getString("DENOMINAZIONE"));
                assistito.setIndirizzo_residenza(rs.getString("INDIRIZZO_RESIDENZA"));
                assistito.setCitta_residenza(rs.getString("CITTA_RESIDENZA"));
                assistito.setCap(rs.getString("CAP"));
                assistito.setProvincia(rs.getInt("PROVINCIA"));
                assistito.setSigla_autom(rs.getString("SIGLA_AUTOM"));
                assistito.setPermesso_soggiorno(rs.getString("PERMESSO_SOGGIORNO"));
                assistito.setTelefono(rs.getString("TELEFONO"));
                assistito.setEmail(rs.getString("EMAIL"));
                assistito.setNum_documento(rs.getString("NUM_DOCUMENTO"));
                assistito.setEnte_assistente(rs.getInt("ENTE_ASSISTENTE"));
                assistito.setDescrizione(rs.getString("DESCRIZIONE"));
                assistito.setData_inserimento(rs.getDate("DATA_INSERIMENTO"));
                assistito.setData_fine_assistenza(rs.getDate("DATA_FINE_ASSISTENZA"));
                assistito.setData_candidatura(rs.getDate("DATA_CANDIDATURA"));
                assistito.setData_accettazione(rs.getDate("DATA_ACCETTAZIONE"));
                assistito.setData_scadenza(rs.getDate("DATA_SCADENZA"));
                assistito.setData_dismissione(rs.getDate("DATA_DISMISSIONE"));
                assistito.setOperatore(rs.getInt("OPERATORE"));
                assistito.setPunteggio_idb(rs.getInt("PUNTEGGIO_IDB"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return assistito;
    }

    public void deleteAssistito(Assistito assistito) {

        String deleteQuery = "UPDATE ASSISTITI SET DATA_FINE_ASSISTENZA=NOW() "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(deleteQuery);
            pStmt.setString(1, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void candidaAssistito(Assistito assistito) {

        String updateQuery = "UPDATE ASSISTITI SET DATA_CANDIDATURA=NOW() "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void rimuoviCandidaturaAssistito(Assistito assistito) {

        String updateQuery = "UPDATE ASSISTITI SET DATA_CANDIDATURA=NULL "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void rimuoviEmporioAssistito(Assistito assistito) {

        String updateQuery = "UPDATE ASSISTITI "
                + "SET DATA_CANDIDATURA=NULL, DATA_ACCETTAZIONE=NULL, DATA_SCADENZA=NULL, DATA_DISMISSIONE=NOW() "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setString(1, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void inserisciEmporioAssistito(Assistito assistito) {
        String updateQuery = "UPDATE ASSISTITI "
                + "SET DATA_CANDIDATURA=NULL, DATA_ACCETTAZIONE=?, "
                + "DATA_SCADENZA=?, DATA_DISMISSIONE=NULL, EMPORIO=? "
                + "WHERE COD_FISCALE=?";
        try {
            pStmt = dbConnection.prepareStatement(updateQuery);
            pStmt.setDate(1, assistito.getData_accettazione());
            pStmt.setDate(2, assistito.getData_scadenza());
            pStmt.setInt(3, assistito.getEmporio());
            pStmt.setString(4, assistito.getCod_fiscale());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
