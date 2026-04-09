package com.example.backend.sqlserver2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
@Entity
@IdClass(DpeId.class)
@Table(name = "DPE", schema = "dbo")
public class Dpe {
    @Id
    @Column(nullable = false)
    private Integer ENT;

    @Id
    @Column(nullable = false)
    private String EJE;

    @Id
    @Column(nullable = false)
    private String DEPCOD;

    @Id
    @Column(nullable = false)
    private String PERCOD;

    private Integer DPERES;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "ENT", referencedColumnName = "ENT", insertable = false, updatable = false),
        @JoinColumn(name = "EJE", referencedColumnName = "EJE", insertable = false, updatable = false),
        @JoinColumn(name = "DEPCOD", referencedColumnName = "DEPCOD", insertable = false, updatable = false)
    })
    private Dep dep;
    public Dep getDep() { return dep; }
    public void setDep(Dep dep) { this.dep = dep; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERCOD", referencedColumnName = "PERCOD", insertable = false, updatable = false)

    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }

    public String getEJE() { return EJE; }
    public void setEJE(String EJE){ this.EJE = EJE; }

    public String getDEPCOD() { return DEPCOD; }
    public void setDEPCOD(String DEPCOD) { this.DEPCOD = DEPCOD; }

    public String getPERCOD() { return PERCOD; }
    public void setPERCOD(String PERCOD) { this.PERCOD = PERCOD; }

    public Integer DPERES() { return DPERES; }
    public void setDPERES(Integer DPERES) { this.DPERES = DPERES; }
}
