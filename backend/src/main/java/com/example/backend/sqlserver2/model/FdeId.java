package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class FdeId implements Serializable {
    private Integer ENT;
    private String EJE;
    private Integer FACNUM;
    private String FDEREF;

    public FdeId() {}
    public FdeId(Integer ENT, String EJE, Integer FACNUM, String FDEREF) {
        this.ENT = ENT;
        this.EJE = EJE;
        this.FACNUM = FACNUM;
        this.FDEREF = FDEREF;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FdeId fdeId = (FdeId) o;
        return Objects.equals(ENT, fdeId.ENT) &&
               Objects.equals(EJE, fdeId.EJE) &&
               Objects.equals(FACNUM, fdeId.FACNUM) &&
               Objects.equals(FACNUM, fdeId.FDEREF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, EJE, FACNUM, FDEREF);
    }
}
