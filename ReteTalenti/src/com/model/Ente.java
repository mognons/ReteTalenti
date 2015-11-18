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
    private int provincia_ente;
    private Boolean ente_emporio;


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

    public int getProvincia_ente() {
        return provincia_ente;
    }

    public void setProvincia_ente(int provincia_ente) {
        this.provincia_ente = provincia_ente;
    }

	public Boolean getEnte_emporio() {
		return ente_emporio;
	}

	public void setEnte_emporio(Boolean ente_emporio) {
		this.ente_emporio = ente_emporio;
	}
    
}
