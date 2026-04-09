package com.example.backend.dto;

public class MagShortDto {
    private Integer magcod;
    private String magnom;

    public MagShortDto(Integer magcod, String magnom) {
        this.magcod = magcod;
        this.magnom = magnom;
    }

    public Integer getMagcod() { return magcod; }
    public void setMagcod(Integer magcod) { this.magcod = magcod; }

    public String getMagnom() { return magnom; }
    public void setMagnom(String magnom) { this.magnom = magnom; }
}