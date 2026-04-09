package com.example.backend.sqlserver2.model;

import java.io.Serializable;
import java.util.Objects;

public class TerId implements Serializable{
    private Integer ENT;
    private Integer TERCOD;

    public TerId() {}
    public TerId(Integer ENT, Integer TERCOD) {
        this.ENT = ENT;
        this.TERCOD = TERCOD;
    }

    public Integer getENT() {return ENT;}
    public void setENT(Integer ENT) {this.ENT = ENT;}

    public Integer getTERCOD() {return TERCOD;}
    public void setTERCOD(Integer TERCOD) {this.TERCOD = TERCOD;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TerId)) return false;
        TerId terId = (TerId) o;
        return Objects.equals(ENT, terId.ENT) &&
               Objects.equals(TERCOD, terId.TERCOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ENT, TERCOD);
    }
}
