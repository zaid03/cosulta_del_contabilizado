package com.example.backend.sqlserver1.model;

import java.io.Serializable;
import java.util.Objects;

public class PuaId implements Serializable {
    private String USUCOD;
    private Integer APLCOD;
    private Integer ENTCOD;

    public PuaId() {}

    public PuaId(String USUCOD, Integer APLCOD, Integer ENTCOD) {
        this.USUCOD = USUCOD;
        this.APLCOD = APLCOD;
        this.ENTCOD = ENTCOD;
    }

    public String getUSUCOD() { return USUCOD; }
    public void setUSUCOD(String USUCOD) { this.USUCOD = USUCOD; }

    public Integer getAPLCOD() { return APLCOD; }
    public void setAPLCOD(Integer APLCOD) { this.APLCOD = APLCOD; }

    public Integer getENTCOD() { return ENTCOD; }
    public void setENTCOD(Integer ENTCOD) { this.ENTCOD = ENTCOD; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuaId)) return false;
        PuaId that = (PuaId) o;
        return Objects.equals(USUCOD, that.USUCOD)
            && Objects.equals(APLCOD, that.APLCOD)
            && Objects.equals(ENTCOD, that.ENTCOD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(USUCOD, APLCOD, ENTCOD);
    }
}