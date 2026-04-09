package com.example.backend.sqlserver2.model;

import jakarta.persistence.*;

@Entity
@IdClass(CfgId.class)
@Table(name = "CFG", schema = "dbo")
public class Cfg {

    @Id
    @Column(nullable = false)
    private Integer ENT;

    @Id
    @Column(nullable = false)
    private String EJE;

    @Column(nullable = false)
    private Integer CFGEST;

    private String CFGFPG;

    private String CFGOPG;

    private String CFGTPG;

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}

    public Integer getCFGEST() {return CFGEST;}
    public void setCFGEST(Integer CFGEST) {this.CFGEST = CFGEST;}

    public String getCFGFPG() {return CFGFPG;}
    public void setCFGFPG(String CFGFPG) {this.CFGFPG = CFGFPG;}

    public String getCFGOPG() {return CFGOPG;}
    public void setCFGOPG(String CFGOPG) {this.CFGOPG = CFGOPG;}

    public String getCFGTPG() {return CFGTPG;}
    public void setCFGTPG(String CFGTPG) {this.CFGTPG = CFGTPG;}
}