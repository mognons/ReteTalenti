/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author gminardi
 */
public class IndiceBisogno {
    
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

    public IndiceBisogno() {
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
    
    
    
    
    
    
    
    
    
}
