package com.example.backend.dto;

import java.time.LocalDateTime;

public class FacturaConsultaRequestDto {
    private String pwd;
    private String publicKey;
    private String org;
    private String ent;
    private String eje;
    private String usu;
    private String tipoRegistro;
    private String cge;
    private Integer tipoDocumento;
    private String tercero;
    private LocalDateTime fecRegDesde;
    private LocalDateTime fecRegHasta;
    private String docProveedor;
    private LocalDateTime fecDocDesde;
    private LocalDateTime fecDocHasta;
    private String situacionIgual;
    private String estado;
    private String webserviceUrl;

    public String getPwd() {return pwd;}

    public String getPublicKey() {return publicKey;}

    public String getOrg() {return org;}

    public String getEnt() {return ent;}

    public String getEje() {return eje;}

    public String getUsu() {return usu;}

    public String getTipoRegistro() {return tipoRegistro;}

    public String getCge() {return cge;}

    public Integer getTipoDocumento() {return tipoDocumento;}

    public String getTercero() {return tercero;}

    public LocalDateTime getFecRegDesde() {return fecRegDesde;}

    public LocalDateTime getFecRegHasta() {return fecRegHasta;}

    public String getDocProveedor() {return docProveedor;}

    public LocalDateTime getFecDocDesde() {return fecDocDesde;}

    public LocalDateTime getFecDocHasta() {return fecDocHasta;}

    public String getSituacionIgual() {return situacionIgual;}

    public String getEstado() {return estado;}

    public String getWebserviceUrl() {return webserviceUrl;}
}
