package com.example.backend.dto;

import com.example.backend.sqlserver2.model.Cge;

public class CentroGestorLogin {
    private Cge cge;
    private Integer depint;
    private Integer depalm;
    private Integer depcom;

    public CentroGestorLogin(Cge cge, Integer depint, Integer depalm, Integer depcom) {
        this.cge = cge;
        this.depint = depint;
        this.depalm = depalm;
        this.depcom = depcom;
    }

    public Cge getCge() { return cge; }
    public Integer getDepint() { return depint; }
    public Integer getDepalm() { return depalm; }
    public Integer getDepcom() { return depcom; }
}
