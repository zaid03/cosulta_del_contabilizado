package com.example.backend.sqlserver1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PUA")
@IdClass(PuaId.class)
public class Pua {

    @Id
    @Column(name = "USUCOD", nullable = false)
    private String USUCOD;

    @Id
    @Column(name = "APLCOD", nullable = false)
    private Integer APLCOD;

    @Id
    @Column(name = "ENTCOD", nullable = false)
    private Integer ENTCOD;

    @Column(name = "PERCOD", nullable = false)
    private String PERCOD;

    /**
     * No-argument constructor required by JPA/Hibernate ORM framework.
     * <p>
     * This constructor is used by the ORM framework when loading entities from the database.
     * Applications should NOT explicitly call this constructor; instead, use entity managers
     * to load or create instances.
     * </p>
     */
    public Pua() {
        // Left intentionally empty - JPA requires a no-arg constructor
    }

    public String getUSUCOD() {return USUCOD;}
    public void setUSUCOD(String USUCOD) {this.USUCOD = USUCOD;}

    public Integer getAPLCOD() {return APLCOD;}
    public void setAPLCOD(Integer APLCOD) {this.APLCOD = APLCOD;}

    public Integer getENTCOD() {return ENTCOD;}
    public void setENTCOD(Integer ENTCOD) {this.ENTCOD = ENTCOD;}

    public String getPERCOD() {return PERCOD;}
    public void setPERCOD(String PERCOD) {this.PERCOD = PERCOD;}
}
