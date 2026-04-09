package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class DepId implements Serializable {
    private Integer ENT;
    private String EJE;
    private String DEPCOD;

    public DepId() {}

    public DepId(Integer ENT, String EJE, String DEPCOD) {
        this.ENT = ENT;
        this.EJE = EJE;
        this.DEPCOD = DEPCOD;
    }

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}

    public String getDEPCOD() {return DEPCOD;}
    public void setDEPCOD(String DEPCOD) {this.DEPCOD = DEPCOD;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepId depId = (DepId) o;
        return Objects.equals(ENT, depId.ENT) &&
               Objects.equals(EJE, depId.EJE) &&
               Objects.equals(DEPCOD, depId.DEPCOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, EJE, DEPCOD);
    }
}