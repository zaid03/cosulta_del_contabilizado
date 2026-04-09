package com.example.backend.dto;

public class ContabilizacionRequestDto {
    private String pwd;
    private String publicKey;
    private String org;
    private String ent;
    private String eje;
    private String usu;
    private String webserviceUrl;

    private Integer facnum;
    private String cgecod;
    private String fechaContable;  
    private Boolean esContrato;    
    
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    public String getOrg() { return org; }
    public void setOrg(String org) { this.org = org; }

    public String getEnt() { return ent; }
    public void setEnt(String ent) { this.ent = ent; }

    public String getEje() { return eje; }
    public void setEje(String eje) { this.eje = eje; }

    public String getUsu() { return usu; }
    public void setUsu(String usu) { this.usu = usu; }

    public String getWebserviceUrl() { return webserviceUrl; }
    public void setWebserviceUrl(String webserviceUrl) { this.webserviceUrl = webserviceUrl; }

    public Integer getFacnum() { return facnum; }
    public void setFacnum(Integer facnum) { this.facnum = facnum; }

    public String getCgecod() { return cgecod; }
    public void setCgecod(String cgecod) { this.cgecod = cgecod; }

    public String getFechaContable() { return fechaContable; }
    public void setFechaContable(String fechaContable) { this.fechaContable = fechaContable; }

    public Boolean getEsContrato() { return esContrato; }
    public void setEsContrato(Boolean esContrato) { this.esContrato = esContrato; }
}