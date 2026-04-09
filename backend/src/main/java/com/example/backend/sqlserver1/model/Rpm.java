package com.example.backend.sqlserver1.model;

import jakarta.persistence.*;

@Entity
@IdClass(RpmId.class)
@Table(name = "RPM", schema = "dbo")
public class Rpm {

    @Id
    @Column(name = "PERCOD", nullable = false)
    private String PERCOD;

    @Id
    @Column(name = "APLCOD", nullable = false)
    private Integer APLCOD;

    @Id
    @Column(name = "MNUCOD", nullable = false)
    private String MNUCOD;

    public String getPERCOD() {return PERCOD;}
    public void setPERCOD(String PERCOD) {this.PERCOD = PERCOD;}

    public Integer getAPLCOD() {return APLCOD;}
    public void setAPLCOD(Integer APLCOD) {this.APLCOD = APLCOD;}

    public String getMNUCOD() {return MNUCOD;}
    public void setMNUCOD(String MNUCOD) {this.MNUCOD = MNUCOD;}
}
