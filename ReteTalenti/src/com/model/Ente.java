/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author gminardi
 */
public class Ente {
    
    private Integer id;
    private String descrizione;
    private String responsabile;
    private String resp_email;
    private String resp_phone;
    private String provincia_ente;

    public Ente() {
    }

    public Ente(Integer id, String descrizione, String responsabile, String resp_email, String resp_phone, String provincia_ente) {
        this.id = id;
        this.descrizione = descrizione;
        this.responsabile = responsabile;
        this.resp_email = resp_email;
        this.resp_phone = resp_phone;
        this.provincia_ente = provincia_ente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getResp_email() {
        return resp_email;
    }

    public void setResp_email(String resp_email) {
        this.resp_email = resp_email;
    }

    public String getResp_phone() {
        return resp_phone;
    }

    public void setResp_phone(String resp_phone) {
        this.resp_phone = resp_phone;
    }

    public String getProvincia_ente() {
        return provincia_ente;
    }

    public void setProvincia_ente(String provincia_ente) {
        this.provincia_ente = provincia_ente;
    }
    
}
