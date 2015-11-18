package com.action;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.dao.NucleiFamiliariDao;
import com.interceptor.UserAware;
import com.model.NucleoFamiliare;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class NucleiFamiliariTableAction extends ActionSupport implements UserAware, ModelDriven<User> {

	private static final long serialVersionUID = 1L;
	private NucleiFamiliariDao dao = new NucleiFamiliariDao();
	private List<NucleoFamiliare> records;
	private String result;

	private String message;
	private NucleoFamiliare record;
	private int totalRecordCount, jtStartIndex, jtPageSize;
	private String jtSorting;
	//
	private String codice_fiscale, cf_assistito_nf, nome, cognome, sesso, tipo_parentela;
	private Date data_nascita;
	private User user = new User();

	public String list() {
		jtSorting = "COGNOME ASC";
		System.out.println("cf_assistito " + cf_assistito_nf);
		try {
			records = dao.getAllConviventi(jtStartIndex, jtPageSize, jtSorting, cf_assistito_nf);
			result = "OK";
			totalRecordCount = dao.getCountConviventi(cf_assistito_nf);

		} catch (Exception e) {
			result = ERROR;
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return SUCCESS;
	}

	public String create() throws IOException {
		record = new NucleoFamiliare();
		record.setCodice_fiscale(codice_fiscale);
		record.setNome(nome);
		record.setCognome(cognome);
		record.setData_nascita(data_nascita);
		record.setSesso(sesso);
		record.setTipo_parentela(tipo_parentela);
		record.setCf_assistito_nf(cf_assistito_nf);
		System.out.println("Create Convivente "+ record.getCognome());
		try {
			dao.createConvivente(record);
			result = "OK";
		} catch (Exception e) {
			message = e.getMessage();
			System.err.println(e.getMessage());
			result = "ERROR";
		}
		return SUCCESS;
	}

	public String update() throws IOException {
		record = new NucleoFamiliare();
		record.setCodice_fiscale(codice_fiscale);
		record.setNome(nome);
		record.setCognome(cognome);
		record.setData_nascita(data_nascita);
		record.setSesso(sesso);
		record.setTipo_parentela(tipo_parentela);
		record.setCf_assistito_nf(cf_assistito_nf);
		try {
			// Update existing record
			dao.updateConvivente(record);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		return SUCCESS;
	}

	public String delete() throws IOException {
		record = new NucleoFamiliare();
		record.setCodice_fiscale(cf_assistito_nf);
		try {
			dao.deleteConvivente(record);
			result = "OK";
		} catch (Exception e) {
			result = "ERROR";
			message = e.getMessage();
			System.err.println(e.getMessage());
		}
		result = "OK";
		return SUCCESS;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getJtStartIndex() {
		return jtStartIndex;
	}

	public void setJtStartIndex(int jtStartIndex) {
		this.jtStartIndex = jtStartIndex;
	}

	public int getJtPageSize() {
		return jtPageSize;
	}

	public void setJtPageSize(int jtPageSize) {
		this.jtPageSize = jtPageSize;
	}

	public String getJtSorting() {
		return jtSorting;
	}

	public void setJtSorting(String jtSorting) {
		this.jtSorting = jtSorting;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<NucleoFamiliare> getRecords() {
		return records;
	}

	public void setRecords(List<NucleoFamiliare> records) {
		this.records = records;
	}

	public NucleoFamiliare getRecord() {
		return record;
	}

	public void setRecord(NucleoFamiliare record) {
		this.record = record;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getCf_assistito_nf() {
		return cf_assistito_nf;
	}

	public void setCf_assistito_nf(String cf_assistito_nf) {
		this.cf_assistito_nf = cf_assistito_nf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTipo_parentela() {
		return tipo_parentela;
	}

	public void setTipo_parentela(String tipo_parentela) {
		this.tipo_parentela = tipo_parentela;
	}

	public java.sql.Date getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(java.sql.Date data_nascita) {
		this.data_nascita = data_nascita;
	}

	public User getUser() {
		return user;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
