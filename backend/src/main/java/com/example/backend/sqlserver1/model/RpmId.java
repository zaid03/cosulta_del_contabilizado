package com.example.backend.sqlserver1.model;

import java.io.Serializable;
import java.util.Objects;

public class RpmId implements Serializable{

    private static final long serialVersionUID = 1L;

    private String PERCOD;
    private Integer APLCOD;
    private String MNUCOD;

    public RpmId() {}

    public RpmId(String PERCOD, Integer APLCOD, String MNUCOD) {
        this.PERCOD = PERCOD;
        this.APLCOD = APLCOD;
        this.MNUCOD = MNUCOD;
    }

    public String getPERCOD() { return PERCOD; }
    public void setPERCOD(String PERCOD) { this.PERCOD = PERCOD; }

    public Integer getAPLCOD() { return APLCOD; }
    public void setAPLCOD(Integer APLCOD) { this.APLCOD = APLCOD; }

    public String getMNUCOD() { return MNUCOD; }
    public void setMNUCOD(String MNUCOD) { this.MNUCOD = MNUCOD; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RpmId)) return false;
        RpmId that = (RpmId) o;
        return Objects.equals(PERCOD, that.PERCOD)
            && Objects.equals(APLCOD, that.APLCOD)
            && Objects.equals(MNUCOD, that.MNUCOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PERCOD, APLCOD, MNUCOD);
    }
}
