package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class CfgId implements Serializable {
    private Integer ENT;
    private String EJE;

    /**
     * No-argument constructor required by JPA/Hibernate for composite key classes.
     * <p>
     * As an {@code @IdClass}, this class must have a no-arg constructor for proper serialization
     * and deserialization of composite primary keys. Applications should NOT explicitly call this
     * constructor; instead, construct instances with appropriate field values or let JPA handle it.
     * </p>
     */
    public CfgId() {
        // Left intentionally empty - Serializable IdClass requires a no-arg constructor for JPA
    }

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public String getEJE() {return EJE;}
    public void setEJE(String EJE) {this.EJE = EJE;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfgId cfgId = (CfgId) o;
        return Objects.equals(ENT, cfgId.ENT) &&
               Objects.equals(EJE, cfgId.EJE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, EJE);
    }
}