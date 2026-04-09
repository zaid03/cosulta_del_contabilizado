package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class CgeId implements Serializable {
    private Integer ENT;
    private String EJE;
    private String CGECOD;

    public CgeId() {}

    public CgeId(Integer ENT, String EJE, String CGECOD) {
        this.ENT = ENT;
        this.EJE = EJE;
        this.CGECOD = CGECOD;
    }

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}

    public String getCGECOD() {return CGECOD;}
    public void setCGECOD(String CGECOD) {this.CGECOD = CGECOD;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CgeId cgeId = (CgeId) o;
        return Objects.equals(ENT, cgeId.ENT) &&
               Objects.equals(EJE, cgeId.EJE) &&
               Objects.equals(CGECOD, cgeId.CGECOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, EJE, CGECOD);
    }
}