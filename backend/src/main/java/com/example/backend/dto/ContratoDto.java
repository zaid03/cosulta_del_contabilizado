package com.example.backend.dto;

import java.time.LocalDateTime;

public class ContratoDto {
    private Integer concod;
    private String conlot;
    private String condes;
    private LocalDateTime confin;
    private LocalDateTime conffi;
    private Integer conblo;
    private Integer tercod;
    private String ternom;

    private ContratoDto(Builder builder) {
        this.concod = builder.concod;
        this.conlot = builder.conlot;
        this.condes = builder.condes;
        this.confin = builder.confin;
        this.conffi = builder.conffi;
        this.conblo = builder.conblo;
        this.tercod = builder.tercod;
        this.ternom = builder.ternom;
    }

    public static class Builder {
        private Integer concod;
        private String conlot;
        private String condes;
        private LocalDateTime confin;
        private LocalDateTime conffi;
        private Integer conblo;
        private Integer tercod;
        private String ternom;

         public Builder concod(Integer concod) {
            this.concod = concod;
            return this;
        }

        public Builder conlot(String conlot) {
            this.conlot = conlot;
            return this;
        }

        public Builder condes(String condes) {
            this.condes = condes;
            return this;
        }

        public Builder confin(LocalDateTime confin) {
            this.confin = confin;
            return this;
        }

        public Builder conffi(LocalDateTime conffi) {
            this.conffi = conffi;
            return this;
        }

        public Builder conblo(Integer conblo) {this.conblo = conblo; return this;}

        public Builder tercod(Integer tercod) {this.tercod = tercod; return this;}

        public Builder ternom(String ternom) {this.ternom = ternom; return this;}

        public ContratoDto build() {return new ContratoDto(this);}
    }

    public Integer getConcod() { return concod; }
    public String getConlot() { return conlot; }
    public String getCondes() { return condes; }
    public LocalDateTime getConfin() { return confin; }
    public LocalDateTime getConffi() { return conffi; }
    public Integer getConblo() { return conblo; }
    public Integer getTercod() { return tercod; }
    public String getTernom() { return ternom; }
}