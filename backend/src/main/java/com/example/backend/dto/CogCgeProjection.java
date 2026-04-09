package com.example.backend.dto;

public interface CogCgeProjection {
    String getCGECOD();
    CogCge getCge();
    String getCOGOPD();
    Double getCOGIMP();
    Double getCOGAIP();

    interface CogCge {
        String getCGEDES();
        String getCGEORG();
        String getCGEFUN();
    }
}
