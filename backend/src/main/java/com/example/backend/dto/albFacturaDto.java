package com.example.backend.dto;

import java.time.LocalDateTime;

public interface albFacturaDto {
    String getALBREF();
    LocalDateTime getALBDAT();
    Double getALBBIM();
    Integer getALBNUM();
    LocalDateTime getALBFRE();
    String getDEPCOD();
    String getALBCOM();
    String getCONCTP();
    String getCONCPR();
    String getCONCCR();
}
