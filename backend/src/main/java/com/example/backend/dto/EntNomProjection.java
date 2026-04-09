package com.example.backend.dto;

public class EntNomProjection {
    private String ENTNOM;

    public EntNomProjection(String ENTNOM) {
        this.ENTNOM = ENTNOM;
    }

    public String getENTNOM() {return ENTNOM;};
    public void setENTNOM(String ENTNOM) {this.ENTNOM = ENTNOM;}
}
