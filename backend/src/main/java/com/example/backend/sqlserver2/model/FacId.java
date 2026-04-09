package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class FacId implements Serializable{
    private Integer ENT;
    private String EJE;
    private Integer FACNUM;

    public FacId(){}

    public FacId(Integer ENT, String EJE, Integer FACNUM){
        this.ENT = ENT;
        this.EJE = EJE;
        this.FACNUM = FACNUM;
    }

    public Integer getENT() { return ENT; }
    public void setENT(Integer ENT) { this.ENT = ENT; }
    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}
    public Integer getFACNUM() { return FACNUM; }
    public void setFACNUM(Integer FACNUM) { this.FACNUM = FACNUM; }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacId facId = (FacId) o;
        return  Objects.equals(ENT, facId.ENT) &&
                Objects.equals(EJE, facId.EJE) &&
                Objects.equals(FACNUM, facId.FACNUM);
    }

    @Override
    public int hashCode(){
        return Objects.hash(ENT, EJE, FACNUM);
    }
}