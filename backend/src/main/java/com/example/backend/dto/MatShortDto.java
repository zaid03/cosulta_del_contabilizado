package com.example.backend.dto;

public class MatShortDto {
    private Integer mtacod;
    private String mtades;

    public MatShortDto(Integer mtacod, String mtades) {
        this.mtacod = mtacod;
        this.mtades = mtades;
    }

    public Integer getMtacod() {
        return mtacod;
    }

    public void setMtacod(Integer mtacod) {
        this.mtacod = mtacod;
    }

    public String getMtades() {
        return mtades;
    }

    public void setMtades(String mtades) {
        this.mtades = mtades;
    }
}