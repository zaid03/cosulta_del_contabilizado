package com.example.backend.dto;

public class MenuDto {  
    private String MNUCOD;

    public MenuDto(String MNUCOD) {
        this.MNUCOD = MNUCOD;
    }

    public String getMNUCOD() {
        return MNUCOD;
    }

    public void setMNUCOD(String MNUCOD) {
        this.MNUCOD = MNUCOD;
    }
}