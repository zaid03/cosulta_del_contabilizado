package com.example.backend.sqlserver2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@IdClass(DepId.class)
@Table(name = "DEP", schema = "dbo")
public class Dep {
    
    @Id
    @Column(nullable = false)
    private Integer ENT;

    @Id
    @Column(nullable = false)
    private String EJE;

    @Id
    @Column(nullable = false)
    private String DEPCOD;

    @Column(nullable = false)
    private String DEPDES;

    private String DEPHOR;

    private Integer DEPTIP;

    private Integer DEPTFP;

    private String DEPFPA;

    private Integer DEPSNU;

    private Integer DEPTAU;

    private Integer DEPALM;

    private Integer DEPCOM;

    private Integer DEPINT;

    private String CCOCOD;

    private String CGECOD;

    private String DEPD1C;

    private String DEPD1D;

    private String DEPD2C;

    private String DEPD2D;

    private String DEPD3C;

    private String DEPD3D;

    private String DEPDCO;

    private String DEPDEN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "ENT", referencedColumnName = "ENT", insertable = false, updatable = false),
        @JoinColumn(name = "EJE", referencedColumnName = "EJE", insertable = false, updatable = false),
        @JoinColumn(name = "CGECOD", referencedColumnName = "CGECOD", insertable = false, updatable = false)
    })
    private Cge cge;

    @JsonIgnore
    @OneToMany(mappedBy = "dep", fetch = FetchType.LAZY)
    private List<Dpe> dpes;

    public Cge getCge() { return cge; }
    public void setCge(Cge cge) { this.cge = cge; }

    public List<Dpe> getDpes() { return dpes; }
    public void setDpes(List<Dpe> dpes) { this.dpes = dpes; }


    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }

    public String getEJE() { return EJE; }
    public void setEJE(String EJE){ this.EJE = EJE; }

    public String getDEPCOD() { return DEPCOD; }
    public void setDEPCOD(String DEPCOD) { this.DEPCOD = DEPCOD; }

    public String getDEPDES() { return DEPDES; }
    public void setDEPDES(String DEPDES) { this.DEPDES = DEPDES; }

    public String getDEPHOR() { return DEPHOR; }
    public void setDEPHOR(String DEPHOR) { this.DEPHOR = DEPHOR; }

    public Integer getDEPTIP() { return DEPTIP; }
    public void  setDEPTIP(Integer DEPTIP) { this.DEPTIP = DEPTIP;}

    public Integer getDEPTFP() { return DEPTFP; }
    public void setDEPTFP(Integer DEPTFP) { this.DEPTFP = DEPTFP; }

    public String getDEPFPA() { return DEPFPA; }
    public void setDEPFPA(String DEPFPA) { this.DEPFPA = DEPFPA; }

    public Integer getDEPSNU() { return DEPSNU; }
    public void setDEPSNU(Integer DEPSNU) { this.DEPSNU = DEPSNU; }

    public Integer getDEPTAU() { return DEPTAU; }
    public void setDEPTAU(Integer DEPTAU) { this.DEPTAU = DEPTAU; }

    public Integer getDEPALM() { return DEPALM; }
    public void setDEPALM(Integer DEPALM) { this.DEPALM = DEPALM; }

    public Integer getDEPCOM() { return DEPCOM; }
    public void setDEPCOM(Integer DEPCOM) { this.DEPCOM = DEPCOM; }

    public Integer getDEPINT() { return DEPINT; }
    public void setDEPINT(Integer DEPINT) { this.DEPINT = DEPINT; }

    public String getCCOCOD() { return CCOCOD; }
    public void setCCOCOD(String CCOCOD) { this.CCOCOD = CCOCOD; }

    public String getCGECOD() { return CGECOD; }
    public void setCGECOD(String CGECOD) { this.CGECOD = CGECOD; }

    public String getDEPD1C() { return DEPD1C; }
    public void setDEPD1C(String DEPD1C) { this.DEPD1C = DEPD1C; }

    public String getDEPD1D() { return DEPD1D; }
    public void setDEPD1D(String DEPD1D) { this.DEPD1D = DEPD1D; }

    public String getDEPD2C() { return DEPD2C; }
    public void setDEPD2C(String DEPD2C) { this.DEPD2C = DEPD2C; }

    public String getDEPD2D() { return DEPD2D; }
    public void setDEPD2D(String DEPD2D) { this.DEPD2D = DEPD2D; }

    public String getDEPD3C() { return DEPD3C; }
    public void setDEPD3C(String DEPD3C) { this.DEPD3C = DEPD3C; }

    public String getDEPD3D() { return DEPD3D; }
    public void setDEPD3D(String DEPD3D) { this.DEPD3D = DEPD3D; }

    public String getDEPDCO() { return DEPDCO; }
    public void setDEPDCO(String DEPDCO) { this.DEPDCO = DEPDCO; }

    public String getDEPDEN() { return DEPDEN; }
    public void setDEPDEN(String DEPDEN) { this.DEPDEN = DEPDEN; }
}