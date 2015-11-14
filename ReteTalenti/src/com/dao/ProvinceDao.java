package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.DataAccessObject;
import com.model.Provincia;

public class ProvinceDao {

	private Connection dbConnection;

	public ProvinceDao() {
		dbConnection = DataAccessObject.getConnection();
	}

	public List<Provincia> getAllProvince() {
		List<Provincia> Province = new ArrayList<Provincia>();

		String query = "SELECT * FROM PROVINCE ORDER BY DENOMINAZIONE";
		System.out.println(query);
		
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Provincia Provincia = new Provincia();

				Provincia.setCod_provincia(rs.getInt("COD_PROVINCIA"));
				Provincia.setDenominazione(rs.getString("DENOMINAZIONE"));
				Provincia.setSigla_autom(rs.getString("SIGLA_AUTOM"));
				Province.add(Provincia);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return Province;
	}

	public Provincia getProvinciaByCodice(int codice) {

		Provincia Provincia = new Provincia();
		String query = "SELECT * FROM PROVINCE WHERE CODICE=? ORDER BY DENOMINAZIONE";
		try {
			PreparedStatement pStmt = dbConnection.prepareStatement(query);
			pStmt.setInt(1, codice);
			ResultSet rs = pStmt.executeQuery(query);
			while (rs.next()) {
				Provincia.setCod_provincia(rs.getInt("COD_PROVINCIA"));
				Provincia.setDenominazione(rs.getString("DENOMINAZIONE"));
				Provincia.setSigla_autom(rs.getString("SIGLA_AUTOM"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return Provincia;
	}

}