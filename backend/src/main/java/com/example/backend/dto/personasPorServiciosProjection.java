package com.example.backend.dto;

public interface personasPorServiciosProjection {
    String getPERCOD();                 
    PerInfo getPer();
    String getDEPCOD(); 
    DepInfo getDep();
    
    interface PerInfo {
        String getPERNOM();         
    }

    interface DepInfo {
        String getDEPDES();         
        Integer getDEPALM();        
        Integer getDEPCOM();       
        Integer getDEPINT();
        CgeInfo getCge();
    }
    
    interface CgeInfo {
        String getCGECOD();
        String getCGEDES();
    }
}