package com.example.backend.sqlserver2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@IdClass(CgeId.class)
@Table(name = "CGE", schema = "dbo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cge {

    @Id
    @Column(nullable = false)
    private Integer ENT;

    @Id
    @Column(nullable = false)
    private String EJE;

    @Id
    @Column(nullable = false)
    private String CGECOD;

    @Column(nullable = false)
    private String CGEDES;

    private String CGEORG;

    private String CGEFUN;

    private String CGEDAT;

    @Column(nullable = true)
    private Integer CGECIC;

    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }

    public String getEJE() { return EJE; }
    public void setEJE(String EJE){ this.EJE = EJE; }

    public String getCGECOD(){ return CGECOD; }
    public void setCGECOD(String CGECOD) { this.CGECOD = CGECOD; }

    public String getCGEDES() { return CGEDES; }
    public void setCGEDES(String CGEDES) { this.CGEDES = CGEDES; }

    public String getCGEORG() { return CGEORG; }
    public void setCGEORG(String CGEORG) { this.CGEORG = CGEORG; }

    public String getCGEFUN() { return CGEFUN; }
    public void setCGEFUN(String CGEFUN) { this.CGEFUN = CGEFUN; }

    public String getCGEDAT() { return CGEDAT; }
    public void setCGEDAT(String CGEDAT) { this.CGEDAT = CGEDAT; }

    public Integer getCGECIC() { return CGECIC; }
    public void setCGECIC(Integer CGECIC) { this.CGECIC = CGECIC; }
}