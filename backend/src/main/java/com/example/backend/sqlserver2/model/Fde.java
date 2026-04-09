package com.example.backend.sqlserver2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(FdeId.class)
@Table(name = "FDE", schema = "dbo")
public class Fde {
    @Id
    private Integer ENT;

    @Id
    private String EJE;

    @Id
    private Integer FACNUM;

    @Id
    private String FDEREF;

    private String FDEOPE;

    private String FDEORG;

    private String FDEFUN;

    private String FDEECO;

    private String FDESUB;

    private Double FDEIMP;

    private Double FDEDIF;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "ENT", referencedColumnName = "ENT", insertable = false, updatable = false),
        @JoinColumn(name = "EJE", referencedColumnName = "EJE", insertable = false, updatable = false),
        @JoinColumn(name = "FACNUM", referencedColumnName = "FACNUM", insertable = false, updatable = false)
    })
    private Fac fac;

    public Fac getFac() { return fac; }
    public void setFac(Fac fac) { this.fac = fac; }

    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }
    
    public String getEJE() { return EJE; }
    public void setEJE(String EJE){ this.EJE = EJE; }

    public Integer getFACNUM() { return FACNUM; }
    public void setFACNUM(Integer FACNUM) { this.FACNUM = FACNUM; }

    public String getFDEREF() { return FDEREF; }
    public void setFDEREF(String FDEREF) { this.FDEREF = FDEREF; }

    public String getFDEOPE() { return FDEOPE; }
    public void setFDEOPE(String FDEOPE) { this.FDEOPE = FDEOPE; }

    public String getFDEORG() { return FDEORG; }
    public void setFDEORG(String FDEORG) { this.FDEORG = FDEORG; }

    public String getFDEFUN() { return FDEFUN; }
    public void setFDEFUN(String FDEFUN) { this.FDEFUN = FDEFUN; }

    public String getFDEECO() { return FDEECO; }
    public void setFDEECO(String FDEECO) { this.FDEECO = FDEECO; }

    public String getFDESUB() { return FDESUB; }
    public void setFDESUB(String FDESUB) { this.FDESUB = FDESUB; }

    public Double getFDEIMP() { return FDEIMP; }
    public void setFDEIMP(Double FDEIMP) { this.FDEIMP = FDEIMP; }

    public Double getFDEDIF() { return FDEDIF; }
    public void setFDEDIF(Double FDEDIF) { this.FDEDIF = FDEDIF; }
}
