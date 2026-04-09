package com.example.backend.dto;

public class DepCodDesDto {
    private String depcod;
    private String depdes;

    public DepCodDesDto(String depcod, String depdes) {
        this.depcod = depcod;
        this.depdes = depdes;
    }

    public String getdepcod() {return depcod;}
    public void setdepcod(String depcod) {this.depcod = depcod;}

    public String getdepdes() {return depdes;}
    public void setdepdes(String depdes) {this.depdes = depdes;}
}
