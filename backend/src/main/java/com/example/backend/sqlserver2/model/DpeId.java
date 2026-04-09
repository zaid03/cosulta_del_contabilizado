package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class DpeId implements Serializable {
    private Integer ENT;
    private String EJE;
    private String DEPCOD;
    private String PERCOD;

    public DpeId() {}

    public DpeId(Integer ENT, String EJE, String DEPCOD, String PERCOD) {
        this.ENT = ENT;
        this.EJE = EJE;
        this.DEPCOD = DEPCOD;
        this.PERCOD = PERCOD;
    }

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}

    public String getDEPCOD() {return DEPCOD;}
    public void setDEPCOD(String DEPCOD) {this.DEPCOD = DEPCOD;}

    public String getPERCOD() {return PERCOD;}
    public void setPERCOD(String PERCOD) {this.PERCOD = PERCOD;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DpeId dpeId = (DpeId) o;
        return Objects.equals(ENT, dpeId.ENT) &&
               Objects.equals(EJE, dpeId.EJE) &&
               Objects.equals(DEPCOD, dpeId.DEPCOD) &&
               Objects.equals(PERCOD, dpeId.PERCOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, EJE, DEPCOD, PERCOD);
    }
}