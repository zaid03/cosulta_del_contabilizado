package com.example.backend.dto;

import java.time.LocalDateTime;

public interface FdeFacTerProjection {
    Integer getFACNUM();
    String getFDEREF();
    String getFDEOPE();
    String getFDEORG();
    String getFDEFUN();
    String getFDEECO();
    String getFDESUB();
    Double getFDEIMP();
    Double getFDEDIF();

    FacInfo getFac();
    interface FacInfo {
        String getCGECOD();
        String getFACDOC();
        java.time.LocalDateTime getFACFCO();
        Integer getTERCOD();
        Double getFACIMP();
        Integer getFACANN();
        Integer getFACFAC();
        java.time.LocalDateTime getFACDAT();

        TerInfo getTer();
    }

    interface TerInfo {
        String getTERNOM();
        String getTERNIF();
    }
}