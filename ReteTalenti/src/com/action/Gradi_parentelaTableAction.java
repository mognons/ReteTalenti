package com.action;

import java.io.IOException;
import java.util.List;
import com.dao.Gradi_parentelaDao;
import com.interceptor.UserAware;
import com.model.Grado_parentela;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class Gradi_parentelaTableAction extends ActionSupport implements UserAware {
	
	    private static final long serialVersionUID = 1L;
	    private Gradi_parentelaDao dao = new Gradi_parentelaDao();
	    private List<Grado_parentela> records;
	    private String result;
	    //
	    private String message;
		private Grado_parentela record;
	    private int totalRecordCount, jtStartIndex, jtPageSize;
	    private String jtSorting;
	    //
	    private int id;
	    private String descrizione;
	    private User user = new User();
	    
	    
	    public String list() {
	    	jtSorting = "DESCRIZIONE ASC";
	        try {
	            // Fetch Data from Enti Table
	            records = dao.getAllGradi_parentela(jtStartIndex, jtPageSize, jtSorting);
	            result = "OK";
	            totalRecordCount = dao.getGradi_parentelaRecordCount();

	        } catch (Exception e) {
	            result = "ERROR";
	            message = e.getMessage();
	            System.err.println(e.getMessage());
	        }
	        return SUCCESS;
	    }
	    
	    public String create() throws IOException {
	        record = new Grado_parentela();
	        record.setDescrizione(descrizione);
	      
	        if (dao.verifyGradi_parentela(descrizione)) {
	            try {
	                record.setId(dao.createGradi_parentela(record));
	                result = "OK";
	            } catch (Exception e) {
	                message = e.getMessage();
	                System.err.println(e.getMessage());
	                result = "ERROR";
	            }
	        } else {
	            message = "Impossibile creare un nuovo grado parentela: descrizione grado parentela <b>" + descrizione + "</b> già inserita";
	            result = "ERROR";
	        }
	        return SUCCESS;
	    }
	    
	    public String update() throws IOException {
	        record = new Grado_parentela();

	        record.setId(id);
	        record.setDescrizione(descrizione);
	       

	        try {
	            // Update existing record
	            dao.updateGradi_parentela(record);
	            result = "OK";
	        } catch (Exception e) {
	            result = "ERROR";
	            message = e.getMessage();
	            System.err.println(e.getMessage());
	        }
	        return SUCCESS;
	    }
	    
	    public String delete() throws IOException {
	        try {
	            // Update existing record
	            dao.deleteGradi_parentela(id);
	            result = "OK";
	        } catch (Exception e) {
	            result = "ERROR";
	            message = e.getMessage();
	            System.err.println(e.getMessage());
	        }
	        result = "OK";
	        return SUCCESS;
	    }

		public Gradi_parentelaDao getDao() {
			return dao;
		}

		public void setDao(Gradi_parentelaDao dao) {
			this.dao = dao;
		}

		public List<Grado_parentela> getRecords() {
			return records;
		}

		public void setRecords(List<Grado_parentela> records) {
			this.records = records;
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

		public void setMessage(String message) {
			this.message = message;
		}

		public Grado_parentela getRecord() {
			return record;
		}

		public void setRecord(Grado_parentela record) {
			this.record = record;
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

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	    
	    
	    

	   
	


}
