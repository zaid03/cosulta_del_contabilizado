package com.example.backend.sqlserver2.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(FacId.class)
@Table(name = "FAC", schema = "dbo")
public class Fac {
    @Id
    @Column(nullable = false)
    private Integer ENT;

    @Id
    @Column(nullable = false)
    private String EJE;

    @Id
    @Column(nullable = false)
    private Integer FACNUM;

    @Column(nullable = true)
    private Integer TERCOD;

    @Column(nullable = true)
    private String CGECOD;

    @Column(nullable = true)
    private String FACOBS;

    @Column(nullable = true)
    private double FACIMP;

    @Column(nullable = true)
    private double FACIEC;

    @Column(nullable = true)
    private double FACIDI;

    @Column(nullable = true)
    private String FACTDC;

    @Column(nullable = true)
    private Integer FACANN;

    @Column(nullable = true)
    private Integer FACFAC;

    @Column(nullable = true)
    private String FACDOC;

    @Column(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime FACDAT;

    @Column(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime FACFCO;

    @Column(nullable = true)
    private String FACADO;

    @Column(nullable = true)
    private String FACTXT;

    @Column(nullable = true)
    private LocalDateTime FACFRE;
    
    @Column(nullable = true)
    private String CONCTP;

    @Column(nullable = true)
    private String CONCPR;

    @Column(nullable = true)
    private String CONCCR;

    @Column(nullable = true)
    private Integer FACOCT;

    @Column(nullable = true)
    private String FACFPG;

    @Column(nullable = true)
    private String FACOPG;

    @Column(nullable = true)
    private String FACTPG;

    @Column(nullable = true)
    private double FACDTO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "ENT", referencedColumnName = "ENT", insertable = false, updatable = false),
        @JoinColumn(name = "TERCOD", referencedColumnName = "TERCOD", insertable = false, updatable = false)
    })
    private Ter ter;
    
    public Ter getTer() { return ter; }
    public void setTer(Ter ter) { this.ter = ter; }

    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }

    public String getEJE() { return EJE; }
    public void setEJE(String EJE){ this.EJE = EJE; }

    public Integer getFACNUM() { return FACNUM; }
    public void setFACNUM(Integer FACNUM) { this.FACNUM = FACNUM; }

    public Integer getTERCOD() { return TERCOD; }
    public void setTERCOD(Integer TERCOD) { this.TERCOD = TERCOD; }

    public String getCGECOD() { return CGECOD; }
    public void setCGECOD(String CGECOD) { this.CGECOD = CGECOD; }

    public String getFACOBS() { return FACOBS; }
    public void setFACOBS(String FACOBS) { this.FACOBS = FACOBS; }

    public double getFACIMP() { return FACIMP; }
    public void setFACIMP(double FACIMP) { this.FACIMP = FACIMP; }

    public double getFACIEC() { return FACIEC; }
    public void setFACIEC(double FACIEC) { this.FACIEC = FACIEC; }

    public double getFACIDI() { return FACIDI; }
    public void setFACIDI(double FACIDI) { this.FACIDI = FACIDI; }

    public String getFACTDC() { return FACTDC; }
    public void setFACTDC(String FACTDC) { this.FACTDC = FACTDC; }

    public Integer getFACANN() { return FACANN; }
    public void setFACANN(Integer FACANN) { this.FACANN = FACANN; }

    public Integer getFACFAC() { return FACFAC; }
    public void setFACFAC(Integer FACFAC) { this.FACFAC = FACFAC; }

    public String getFACDOC() { return FACDOC; }
    public void setFACDOC(String FACDOC) { this.FACDOC = FACDOC; }

    public LocalDateTime getFACDAT() { return FACDAT; }
    public void setFACDAT(LocalDateTime FACDAT) { this.FACDAT = FACDAT; }

    public LocalDateTime getFACFCO() { return FACFCO; }
    public void setFACFCO(LocalDateTime FACFCO) { this.FACFCO = FACFCO; }

    public String getFACADO() { return FACADO; }
    public void setFACADO(String FACADO) { this.FACADO = FACADO; }

    public String getFACTXT() { return FACTXT; }
    public void setFACTXT(String FACTXT) { this.FACTXT = FACTXT; }

    public LocalDateTime getFACFRE() { return FACFRE; }
    public void setFACFRE(LocalDateTime FACFRE) { this.FACFRE = FACFRE; }

    public String getCONCTP() { return CONCTP; }
    public void setCONCTP(String CONCTP) { this.CONCTP = CONCTP; }

    public String getCONCPR() { return CONCPR; }
    public void setCONCPR(String CONCPR) { this.CONCPR = CONCPR; }

    public String getCONCCR() { return CONCCR; }
    public void setCONCCR(String CONCCR) { this.CONCCR = CONCCR; }

    public Integer getFACOCT() { return FACOCT; }
    public void setFACOCT(Integer FACOCT) { this.FACOCT = FACOCT; }

    public String getFACFPG() { return FACFPG; }
    public void setFACFPG(String FACFPG) { this.FACFPG = FACFPG; }

    public String getFACOPG() { return FACOPG; }
    public void setFACOPG(String FACOPG) { this.FACOPG = FACOPG; }

    public String getFACTPG() { return FACTPG; }
    public void setFACTPG(String FACTPG) { this.FACTPG = FACTPG; }
    
    public double getFACDTO() { return FACDTO; }
    public void setFACDTO(double FACDTO) { this.FACDTO = FACDTO; }
}