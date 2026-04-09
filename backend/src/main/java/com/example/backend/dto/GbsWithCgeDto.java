package com.example.backend.dto;

import java.time.LocalDateTime;

public class GbsWithCgeDto {
    private String cgecod;
    private String cgedes;
    private String cgecic;
    private String gbsref;
    private String gbsope;
    private String gbsorg;
    private String gbsfun;
    private String gbseco;
    private LocalDateTime gbsfop;
    private Double gbsimp;
    private Double gbsibg;
    private Double gbsius;
    private Double gbsico;
    private Double gbsiut;
    private Double gbsict;
    private Double gbs413;

    private GbsWithCgeDto(Builder builder) {
        this.cgecod = builder.cgecod;
        this.cgedes = builder.cgedes;
        this.cgecic = builder.cgecic;
        this.gbsref = builder.gbsref;
        this.gbsope = builder.gbsope;
        this.gbsorg = builder.gbsorg;
        this.gbsfun = builder.gbsfun;
        this.gbseco = builder.gbseco;
        this.gbsfop = builder.gbsfop;
        this.gbsimp = builder.gbsimp;
        this.gbsibg = builder.gbsibg;
        this.gbsius = builder.gbsius;
        this.gbsico = builder.gbsico;
        this.gbsiut = builder.gbsiut;
        this.gbsict = builder.gbsict;
        this.gbs413 = builder.gbs413;
    }

    public static class Builder {
        private String cgecod;
        private String cgedes;
        private String cgecic;
        private String gbsref;
        private String gbsope;
        private String gbsorg;
        private String gbsfun;
        private String gbseco;
        private LocalDateTime gbsfop;
        private Double gbsimp;
        private Double gbsibg;
        private Double gbsius;
        private Double gbsico;
        private Double gbsiut;
        private Double gbsict;
        private Double gbs413;

        public Builder cgecod(String cgecod) { this.cgecod = cgecod; return this; }
        public Builder cgedes(String cgedes) { this.cgedes = cgedes; return this; }
        public Builder cgecic(String cgecic) { this.cgecic = cgecic; return this; }
        public Builder gbsref(String gbsref) { this.gbsref = gbsref; return this; }
        public Builder gbsope(String gbsope) { this.gbsope = gbsope; return this; }
        public Builder gbsorg(String gbsorg) { this.gbsorg = gbsorg; return this; }
        public Builder gbsfun(String gbsfun) { this.gbsfun = gbsfun; return this; }
        public Builder gbseco(String gbseco) { this.gbseco = gbseco; return this; }
        public Builder gbsfop(LocalDateTime gbsfop) { this.gbsfop = gbsfop; return this; }
        public Builder gbsimp(Double gbsimp) { this.gbsimp = gbsimp; return this; }
        public Builder gbsibg(Double gbsibg) { this.gbsibg = gbsibg; return this; }
        public Builder gbsius(Double gbsius) { this.gbsius = gbsius; return this; }
        public Builder gbsico(Double gbsico) { this.gbsico = gbsico; return this; }
        public Builder gbsiut(Double gbsiut) { this.gbsiut = gbsiut; return this; }
        public Builder gbsict(Double gbsict) { this.gbsict = gbsict; return this; }
        public Builder gbs413(Double gbs413) { this.gbs413 = gbs413; return this; }

        public GbsWithCgeDto build() {
            return new GbsWithCgeDto(this);
        }
    }

    public String getCgecod() { return cgecod; }
    public void setCgecod(String cgecod) { this.cgecod = cgecod; }

    public String getCgedes() { return cgedes; }
    public void setCgedes(String cgedes) { this.cgedes = cgedes; }

    public String getCgecic() { return cgecic; }
    public void setCgecic(String cgecic) { this.cgecic = cgecic; }

    public String getGbsref() { return gbsref; }
    public void setGbsref(String gbsref) { this.gbsref = gbsref; }

    public String getGbsope() { return gbsope; }
    public void setGbsope(String gbsope) { this.gbsope = gbsope; }

    public String getGbsorg() { return gbsorg; }
    public void setGbsorg(String gbsorg) { this.gbsorg = gbsorg; }

    public String getGbsfun() { return gbsfun; }
    public void setGbsfun(String gbsfun) { this.gbsfun = gbsfun; }

    public String getGbseco() { return gbseco; }
    public void setGbseco(String gbseco) { this.gbseco = gbseco; }

    public LocalDateTime getGbsfop() { return gbsfop; }
    public void setGbsfop(LocalDateTime gbsfop) { this.gbsfop = gbsfop; }

    public Double getGbsimp() { return gbsimp; }
    public void setGbsimp(Double gbsimp) { this.gbsimp = gbsimp; }

    public Double getGbsibg() { return gbsibg; }
    public void setGbsibg(Double gbsibg) { this.gbsibg = gbsibg; }

    public Double getGbsius() { return gbsius; }
    public void setGbsius(Double gbsius) { this.gbsius = gbsius; }

    public Double getGbsico() { return gbsico; }
    public void setGbsico(Double gbsico) { this.gbsico = gbsico; }

    public Double getGbsiut() { return gbsiut; }
    public void setGbsiut(Double gbsiut) { this.gbsiut = gbsiut; }

    public Double getGbsict() { return gbsict; }
    public void setGbsict(Double gbsict) { this.gbsict = gbsict; }

    public Double getGbs413() { return gbs413; }
    public void setGbs413(Double gbs413) { this.gbs413 = gbs413; }
}
