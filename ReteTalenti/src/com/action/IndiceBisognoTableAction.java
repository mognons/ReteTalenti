/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.action;

import com.dao.IndiceBisognoDao;
import com.interceptor.UserAware;
import com.model.IndiceBisogno;
import com.model.User;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author gminardi
 */
public class IndiceBisognoTableAction extends ActionSupport implements UserAware {
    
    private static final long serialVersionUID = 1L;
    private IndiceBisognoDao dao = new IndiceBisognoDao();
    private List<IndiceBisogno> records;
    private String result;
    private String message;
    private IndiceBisogno record;
    private int totalRecordCount, jtStartIndex, jtPageSize;
    private String jtSorting;
    //
    private int id;
    private int isee_euro;
    private int cc_euro;
    private int ca_euro;
    private int cs_euro;
    private int stato_disoc;
    private int spese_imp;
    private int urgenza;
    
    private int isee_punti;
    private int entrate_nc_punti;
    private int stato_disoc_punti;
    private int spese_imp_punti;
    private int urgenza_punti;
    private int totalepunti;
    private String cf_assistito_ib;
    private Date data_inserimento;
    
    private User user = new User();
    
    public String list() {
        jtSorting = "DESCRIZIONE ASC";
        try {
            // Fetch Data from Indici Bisogno
            records = dao.getAllIndiciBisogno(jtStartIndex, jtPageSize, jtSorting);
            result = "OK";
            totalRecordCount = dao.getIndiciBisognoRecordCount();
            
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
    
    public String create() throws IOException {
        
        record = new IndiceBisogno();
        record.setIsee_euro(isee_euro);
        record.setCa_euro(ca_euro);
        record.setCc_euro(cc_euro);
        record.setCs_euro(cs_euro);
//        record.setData_inserimento(data_inserimento);
        record.setSpese_imp(spese_imp);
        record.setStato_disoc(stato_disoc);
        record.setUrgenza(urgenza);
        
        record.setIsee_punti(isee_punti);
        record.setEntrate_nc_punti(entrate_nc_punti);
        record.setStato_disoc_punti(stato_disoc_punti);
        record.setSpese_imp_punti(spese_imp_punti);
        record.setUrgenza_punti(urgenza_punti);
        record.setTotalepunti(totalepunti);
        
        record.setCf_assistito_ib(cf_assistito_ib);
        
        try {
            System.out.println("Creating " + cf_assistito_ib);
            record.setId(dao.createIndiceBisogno(record));
            result = "OK";
        } catch (Exception e) {
            message = e.getMessage();
            System.err.println(e.getMessage());
            result = "ERROR";
        }
        
        return SUCCESS;
    }
    
    public String update() throws IOException {
        
        record = new IndiceBisogno();
        
        record.setIsee_euro(isee_euro);
        record.setCa_euro(ca_euro);
        record.setCc_euro(cc_euro);
        record.setCs_euro(cs_euro);
        record.setSpese_imp(spese_imp);
        record.setStato_disoc(stato_disoc);
        record.setUrgenza(urgenza);
        
        record.setEntrate_nc_punti(entrate_nc_punti);
        record.setSpese_imp_punti(spese_imp_punti);
        record.setStato_disoc_punti(stato_disoc_punti);
        record.setUrgenza_punti(urgenza_punti);
        record.setIsee_punti(isee_punti);
        record.setTotalepunti(totalepunti);
           
        
        System.out.println("Updating " + cf_assistito_ib);
        
        try {
            // Update existing record
            dao.updateIndiceBisogno(record);
            result = "OK";
        } catch (Exception e) {
            result = "ERROR";
            message = e.getMessage();
            System.err.println(e.getMessage());
        }
        return SUCCESS;
    }
    
    public String delete() throws IOException {
        System.out.println("Deleting descrizione " + cf_assistito_ib);
        try {
            // Update existing record
            dao.deleteIndiceBisogno(id);
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
    
    public IndiceBisognoDao getDao() {
        return dao;
    }
    
    public void setDao(IndiceBisognoDao dao) {
        this.dao = dao;
    }
    
    public List<IndiceBisogno> getRecords() {
        return records;
    }
    
    public void setRecords(List<IndiceBisogno> records) {
        this.records = records;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public IndiceBisogno getRecord() {
        return record;
    }
    
    public void setRecord(IndiceBisogno record) {
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
    
    public int getIsee_euro() {
        return isee_euro;
    }
    
    public void setIsee_euro(int isee_euro) {
        this.isee_euro = isee_euro;
    }
    
    public int getCc_euro() {
        return cc_euro;
    }
    
    public void setCc_euro(int cc_euro) {
        this.cc_euro = cc_euro;
    }
    
    public int getCa_euro() {
        return ca_euro;
    }
    
    public void setCa_euro(int ca_euro) {
        this.ca_euro = ca_euro;
    }
    
    public int getCs_euro() {
        return cs_euro;
    }
    
    public void setCs_euro(int cs_euro) {
        this.cs_euro = cs_euro;
    }
    
    public int getStato_disoc() {
        return stato_disoc;
    }
    
    public void setStato_disoc(int stato_disoc) {
        this.stato_disoc = stato_disoc;
    }
    
    public int getSpese_imp() {
        return spese_imp;
    }
    
    public void setSpese_imp(int spese_imp) {
        this.spese_imp = spese_imp;
    }

    public int getUrgenza() {
        return urgenza;
    }

    public void setUrgenza(int urgenza) {
        this.urgenza = urgenza;
    }
    
    
    public int getIsee_punti() {
        return isee_punti;
    }
    
    public void setIsee_punti(int isee_punti) {
        this.isee_punti = isee_punti;
    }
    
    public int getEntrate_nc_punti() {
        return entrate_nc_punti;
    }
    
    public void setEntrate_nc_punti(int entrate_nc_punti) {
        this.entrate_nc_punti = entrate_nc_punti;
    }
    
    public int getStato_disoc_punti() {
        return stato_disoc_punti;
    }
    
    public void setStato_disoc_punti(int stato_disoc_punti) {
        this.stato_disoc_punti = stato_disoc_punti;
    }
    
    public int getSpese_imp_punti() {
        return spese_imp_punti;
    }
    
    public void setSpese_imp_punti(int spese_imp_punti) {
        this.spese_imp_punti = spese_imp_punti;
    }
    
    public int getUrgenza_punti() {
        return urgenza_punti;
    }
    
    public void setUrgenza_punti(int urgenza_punti) {
        this.urgenza_punti = urgenza_punti;
    }
    
    public int getTotalepunti() {
        return totalepunti;
    }
    
    public void setTotalepunti(int totalepunti) {
        this.totalepunti = totalepunti;
    }
    
    public String getCf_assistito_ib() {
        return cf_assistito_ib;
    }
    
    public void setCf_assistito_ib(String cf_assistito_ib) {
        this.cf_assistito_ib = cf_assistito_ib;
    }
    
    public Date getData_inserimento() {
        return data_inserimento;
    }
    
    public void setData_inserimento(Date data_inserimento) {
        this.data_inserimento = data_inserimento;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
}
