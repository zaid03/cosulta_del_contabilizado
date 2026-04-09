package com.example.backend.dto;

public class PersonaDto {
    private String percod;
    private String pernom;

    public PersonaDto(String percod, String pernom) {
        this.percod = percod;
        this.pernom = pernom;
    }

    public String getPercod() { return percod; }
    public String getPernom() { return pernom; }
}
