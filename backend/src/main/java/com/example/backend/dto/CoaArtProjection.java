package com.example.backend.dto;

import java.math.BigDecimal;

public interface CoaArtProjection {
    ArtInfo getArt();
    BigDecimal getCOAPRE();

    interface ArtInfo {
        Integer getAFACOD();
        Integer getASUCOD();
        Integer getARTCOD();
        String getARTDES();
    }
}