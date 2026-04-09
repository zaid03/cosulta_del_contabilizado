package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TerDto {

    @JsonProperty("TERWEB")
    private String TERWEB;

    @JsonProperty("TEROBS")
    private String TEROBS;

    @JsonProperty("TERBLO")
    private Integer TERBLO;

    @JsonProperty("TERACU")
    private Integer TERACU;

    @JsonProperty("TERNOM")
    private String TERNOM;

    @JsonProperty("TERALI")
    private String TERALI;

    @JsonProperty("TERNIF")
    private String TERNIF;

    @JsonProperty("TERDOM")
    private String TERDOM;

    @JsonProperty("TERCPO")
    private String TERCPO;

    @JsonProperty("TERTEL")
    private String TERTEL;

    @JsonProperty("TERFAX")
    private String TERFAX;

    @JsonProperty("TERCOE")
    private String TERCOE;

    @JsonProperty("TERPOB")
    private String TERPOB;

    @JsonProperty("PROCOD")
    private String PROCOD;

    @JsonProperty("TERAYT")
    private Integer TERAYT;

    public String getTERWEB() { return TERWEB; }
    public void setTERWEB(String TERWEB) { this.TERWEB = TERWEB; }

    public String getTEROBS() { return TEROBS; }
    public void setTEROBS(String TEROBS) { this.TEROBS = TEROBS; }

    public Integer getTERBLO() { return TERBLO; }
    public void setTERBLO(Integer TERBLO) { this.TERBLO = TERBLO; }

    public Integer getTERACU() { return TERACU; }
    public void setTERACU(Integer TERACU) { this.TERACU = TERACU; }

    public String getTERNOM() { return TERNOM; }
    public void setTERNOM(String TERNOM) { this.TERNOM = TERNOM; }

    public String getTERALI() { return TERALI; }
    public void setTERALI(String TERALI) { this.TERALI = TERALI; }

    public String getTERNIF() { return TERNIF; }
    public void setTERNIF(String TERNIF) { this.TERNIF = TERNIF; }

    public String getTERDOM() { return TERDOM; }
    public void setTERDOM(String TERDOM) { this.TERDOM = TERDOM; }

    public String getTERCPO() { return TERCPO; }
    public void setTERCPO(String TERCPO) { this.TERCPO = TERCPO; }

    public String getTERTEL() { return TERTEL; }
    public void setTERTEL(String TERTEL) { this.TERTEL = TERTEL; }

    public String getTERFAX() { return TERFAX; }
    public void setTERFAX(String TERFAX) { this.TERFAX = TERFAX; }

    public String getTERCOE() { return TERCOE; }
    public void setTERCOE(String TERCOE) { this.TERCOE = TERCOE; }

    public String getTERPOB() { return TERPOB; }
    public void setTERPOB(String TERPOB) { this.TERPOB = TERPOB; }

    public String getPROCOD() {return PROCOD;}
    public void setPROCOD(String PROCOD) {this.PROCOD = PROCOD;}

    public Integer getTERAYT() {return TERAYT;}
    public void setTERAYT(Integer TERAYT) {this.TERAYT = TERAYT;}
}
