package com.example.backend.dto;

import java.util.List;

public class Operaciones {
    private Long numope;
    private String codope;
    private String signo;
    private String fase;
    private String area;
    private String agrupacion;
    private String nifter;
    private String nifend;
    private String cuenta;
    private String fechaentrada;
    private String fecope;
    private String gapuntes;
    private String documento;
    private String fechadocu;
    private String ordinal;
    private String fechapago;
    private String tipopago;
    private String tipoexp;
    private String nexp;
    private String fechaexp;
    private String areages;
    private String oficina;
    private Double importe;
    private Double impiva;
    private Double impdto;
    private String texto;
    private Long numcaja;
    private Integer anoprestamo;
    private String tipoprestamo;
    private String numprestamo;
    private Long terite;
    private Long endite;
    private Long numOpePrev;
    private String tipContrato;
    private String proContrato;
    private String criContrato;
    private String nExpElec;

    private List<Dto> dtoList;
    private List<Iva> ivaList;
    private List<Relacion> relacionList;
    private List<Linea> lineaList;

    public Long getNumope() { return numope; }
    public void setNumope(Long numope) { this.numope = numope; }

    public String getCodope() { return codope; }
    public void setCodope(String codope) { this.codope = codope; }

    public String getSigno() { return signo; }
    public void setSigno(String signo) { this.signo = signo; }

    public String getFase() { return fase; }
    public void setFase(String fase) { this.fase = fase; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getAgrupacion() { return agrupacion; }
    public void setAgrupacion(String agrupacion) { this.agrupacion = agrupacion; }

    public String getNifter() { return nifter; }
    public void setNifter(String nifter) { this.nifter = nifter; }

    public String getNifend() { return nifend; }
    public void setNifend(String nifend) { this.nifend = nifend; }

    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }

    public String getFechaentrada() { return fechaentrada; }
    public void setFechaentrada(String fechaentrada) { this.fechaentrada = fechaentrada; }

    public String getFecope() { return fecope; }
    public void setFecope(String fecope) { this.fecope = fecope; }

    public String getGapuntes() { return gapuntes; }
    public void setGapuntes(String gapuntes) { this.gapuntes = gapuntes; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getFechadocu() { return fechadocu; }
    public void setFechadocu(String fechadocu) { this.fechadocu = fechadocu; }

    public String getOrdinal() { return ordinal; }
    public void setOrdinal(String ordinal) { this.ordinal = ordinal; }

    public String getFechapago() { return fechapago; }
    public void setFechapago(String fechapago) { this.fechapago = fechapago; }

    public String getTipopago() { return tipopago; }
    public void setTipopago(String tipopago) { this.tipopago = tipopago; }

    public String getTipoexp() { return tipoexp; }
    public void setTipoexp(String tipoexp) { this.tipoexp = tipoexp; }

    public String getNexp() { return nexp; }
    public void setNexp(String nexp) { this.nexp = nexp; }

    public String getFechaexp() { return fechaexp; }
    public void setFechaexp(String fechaexp) { this.fechaexp = fechaexp; }

    public String getAreages() { return areages; }
    public void setAreages(String areages) { this.areages = areages; }

    public String getOficina() { return oficina; }
    public void setOficina(String oficina) { this.oficina = oficina; }

    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }

    public Double getImpiva() { return impiva; }
    public void setImpiva(Double impiva) { this.impiva = impiva; }

    public Double getImpdto() { return impdto; }
    public void setImpdto(Double impdto) { this.impdto = impdto; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Long getNumcaja() { return numcaja; }
    public void setNumcaja(Long numcaja) { this.numcaja = numcaja; }

    public Integer getAnoprestamo() { return anoprestamo; }
    public void setAnoprestamo(Integer anoprestamo) { this.anoprestamo = anoprestamo; }

    public String getTipoprestamo() { return tipoprestamo; }
    public void setTipoprestamo(String tipoprestamo) { this.tipoprestamo = tipoprestamo; }

    public String getNumprestamo() { return numprestamo; }
    public void setNumprestamo(String numprestamo) { this.numprestamo = numprestamo; }

    public Long getTerite() { return terite; }
    public void setTerite(Long terite) { this.terite = terite; }

    public Long getEndite() { return endite; }
    public void setEndite(Long endite) { this.endite = endite; }

    public Long getNumOpePrev() { return numOpePrev; }
    public void setNumOpePrev(Long numOpePrev) { this.numOpePrev = numOpePrev; }

    public String getTipContrato() { return tipContrato; }
    public void setTipContrato(String tipContrato) { this.tipContrato = tipContrato; }

    public String getProContrato() { return proContrato; }
    public void setProContrato(String proContrato) { this.proContrato = proContrato; }

    public String getCriContrato() { return criContrato; }
    public void setCriContrato(String criContrato) { this.criContrato = criContrato; }

    public String getNExpElec() { return nExpElec; }
    public void setNExpElec(String nExpElec) { this.nExpElec = nExpElec; }

    public List<Dto> getDtoList() { return dtoList; }
    public void setDtoList(List<Dto> dtoList) { this.dtoList = dtoList; }

    public List<Iva> getIvaList() { return ivaList; }
    public void setIvaList(List<Iva> ivaList) { this.ivaList = ivaList; }

    public List<Relacion> getRelacionList() { return relacionList; }
    public void setRelacionList(List<Relacion> relacionList) { this.relacionList = relacionList; }

    public List<Linea> getLineaList() { return lineaList; }
    public void setLineaList(List<Linea> lineaList) { this.lineaList = lineaList; }

    public static class Dto {
        private Long numdto;
        private String dtocuenta;
        private Integer dtoeje;
        private String dtoorg;
        private String dtofun;
        private String dtoeco;
        private Double dtoimp;
        private Double dtosaldo;
        private Double dtobase;
        private Double dtosaldobase;
        private Double dtopretencion;
        private Double dtonumopecan;
        private Long dtolinopecan;
        private String dtotipoopecan;
        private String dtotexto;
        private String dtonif;
        private Integer dtoanodevengo;
        private String dtoclave;
        private String dtolobtencion;
        private String dtocargo;
        private Double dtobase1;
        private Double dtobase2;
        private Double dtobase3;
        private Double dtoiva1;
        private Double dtoiva2;
        private Double dtoiva3;
        private Double dtotiva1;
        private Double dtotiva2;
        private Double dtotiva3;
        private Double dtoporcent1;
        private Double dtoporcent2;
        private Double dtoporcent3;
        
                public Long getNumdto() { return numdto; }
        public void setNumdto(Long numdto) { this.numdto = numdto; }

        public String getDtocuenta() { return dtocuenta; }
        public void setDtocuenta(String dtocuenta) { this.dtocuenta = dtocuenta; }

        public Integer getDtoeje() { return dtoeje; }
        public void setDtoeje(Integer dtoeje) { this.dtoeje = dtoeje; }

        public String getDtoorg() { return dtoorg; }
        public void setDtoorg(String dtoorg) { this.dtoorg = dtoorg; }

        public String getDtofun() { return dtofun; }
        public void setDtofun(String dtofun) { this.dtofun = dtofun; }

        public String getDtoeco() { return dtoeco; }
        public void setDtoeco(String dtoeco) { this.dtoeco = dtoeco; }

        public Double getDtoimp() { return dtoimp; }
        public void setDtoimp(Double dtoimp) { this.dtoimp = dtoimp; }

        public Double getDtosaldo() { return dtosaldo; }
        public void setDtosaldo(Double dtosaldo) { this.dtosaldo = dtosaldo; }

        public Double getDtobase() { return dtobase; }
        public void setDtobase(Double dtobase) { this.dtobase = dtobase; }

        public Double getDtosaldobase() { return dtosaldobase; }
        public void setDtosaldobase(Double dtosaldobase) { this.dtosaldobase = dtosaldobase; }

        public Double getDtopretencion() { return dtopretencion; }
        public void setDtopretencion(Double dtopretencion) { this.dtopretencion = dtopretencion; }

        public Double getDtonumopecan() { return dtonumopecan; }
        public void setDtonumopecan(Double dtonumopecan) { this.dtonumopecan = dtonumopecan; }

        public Long getDtolinopecan() { return dtolinopecan; }
        public void setDtolinopecan(Long dtolinopecan) { this.dtolinopecan = dtolinopecan; }

        public String getDtotipoopecan() { return dtotipoopecan; }
        public void setDtotipoopecan(String dtotipoopecan) { this.dtotipoopecan = dtotipoopecan; }

        public String getDtotexto() { return dtotexto; }
        public void setDtotexto(String dtotexto) { this.dtotexto = dtotexto; }

        public String getDtonif() { return dtonif; }
        public void setDtonif(String dtonif) { this.dtonif = dtonif; }

        public Integer getDtoanodevengo() { return dtoanodevengo; }
        public void setDtoanodevengo(Integer dtoanodevengo) { this.dtoanodevengo = dtoanodevengo; }

        public String getDtoclave() { return dtoclave; }
        public void setDtoclave(String dtoclave) { this.dtoclave = dtoclave; }

        public String getDtolobtencion() { return dtolobtencion; }
        public void setDtolobtencion(String dtolobtencion) { this.dtolobtencion = dtolobtencion; }

        public String getDtocargo() { return dtocargo; }
        public void setDtocargo(String dtocargo) { this.dtocargo = dtocargo; }

        public Double getDtobase1() { return dtobase1; }
        public void setDtobase1(Double dtobase1) { this.dtobase1 = dtobase1; }

        public Double getDtobase2() { return dtobase2; }
        public void setDtobase2(Double dtobase2) { this.dtobase2 = dtobase2; }

        public Double getDtobase3() { return dtobase3; }
        public void setDtobase3(Double dtobase3) { this.dtobase3 = dtobase3; }

        public Double getDtoiva1() { return dtoiva1; }
        public void setDtoiva1(Double dtoiva1) { this.dtoiva1 = dtoiva1; }

        public Double getDtoiva2() { return dtoiva2; }
        public void setDtoiva2(Double dtoiva2) { this.dtoiva2 = dtoiva2; }

        public Double getDtoiva3() { return dtoiva3; }
        public void setDtoiva3(Double dtoiva3) { this.dtoiva3 = dtoiva3; }

        public Double getDtotiva1() { return dtotiva1; }
        public void setDtotiva1(Double dtotiva1) { this.dtotiva1 = dtotiva1; }

        public Double getDtotiva2() { return dtotiva2; }
        public void setDtotiva2(Double dtotiva2) { this.dtotiva2 = dtotiva2; }

        public Double getDtotiva3() { return dtotiva3; }
        public void setDtotiva3(Double dtotiva3) { this.dtotiva3 = dtotiva3; }

        public Double getDtoporcent1() { return dtoporcent1; }
        public void setDtoporcent1(Double dtoporcent1) { this.dtoporcent1 = dtoporcent1; }

        public Double getDtoporcent2() { return dtoporcent2; }
        public void setDtoporcent2(Double dtoporcent2) { this.dtoporcent2 = dtoporcent2; }

        public Double getDtoporcent3() { return dtoporcent3; }
        public void setDtoporcent3(Double dtoporcent3) { this.dtoporcent3 = dtoporcent3; }
    }

    public static class Iva {
        private Double ivabase1;
        private Double ivabase2;
        private Double ivabase3;
        private Double ivasbase1;
        private Double ivasbase2;
        private Double ivasbase3;
        private Double ivativa1;
        private Double ivativa2;
        private Double ivativa3;
        private Double ivaporcent1;
        private Double ivaporcent2;
        private Double ivaporcent3;
        private Double ivaimp1;
        private Double ivaimp2;
        private Double ivaimp3;
        private Double ivabexenta;
        private String ivaciv1;
        private String ivaciv2;
        
                public Double getIvabase1() { return ivabase1; }
        public void setIvabase1(Double ivabase1) { this.ivabase1 = ivabase1; }

        public Double getIvabase2() { return ivabase2; }
        public void setIvabase2(Double ivabase2) { this.ivabase2 = ivabase2; }

        public Double getIvabase3() { return ivabase3; }
        public void setIvabase3(Double ivabase3) { this.ivabase3 = ivabase3; }

        public Double getIvasbase1() { return ivasbase1; }
        public void setIvasbase1(Double ivasbase1) { this.ivasbase1 = ivasbase1; }

        public Double getIvasbase2() { return ivasbase2; }
        public void setIvasbase2(Double ivasbase2) { this.ivasbase2 = ivasbase2; }

        public Double getIvasbase3() { return ivasbase3; }
        public void setIvasbase3(Double ivasbase3) { this.ivasbase3 = ivasbase3; }

        public Double getIvativa1() { return ivativa1; }
        public void setIvativa1(Double ivativa1) { this.ivativa1 = ivativa1; }

        public Double getIvativa2() { return ivativa2; }
        public void setIvativa2(Double ivativa2) { this.ivativa2 = ivativa2; }

        public Double getIvativa3() { return ivativa3; }
        public void setIvativa3(Double ivativa3) { this.ivativa3 = ivativa3; }

        public Double getIvaporcent1() { return ivaporcent1; }
        public void setIvaporcent1(Double ivaporcent1) { this.ivaporcent1 = ivaporcent1; }

        public Double getIvaporcent2() { return ivaporcent2; }
        public void setIvaporcent2(Double ivaporcent2) { this.ivaporcent2 = ivaporcent2; }

        public Double getIvaporcent3() { return ivaporcent3; }
        public void setIvaporcent3(Double ivaporcent3) { this.ivaporcent3 = ivaporcent3; }

        public Double getIvaimp1() { return ivaimp1; }
        public void setIvaimp1(Double ivaimp1) { this.ivaimp1 = ivaimp1; }

        public Double getIvaimp2() { return ivaimp2; }
        public void setIvaimp2(Double ivaimp2) { this.ivaimp2 = ivaimp2; }

        public Double getIvaimp3() { return ivaimp3; }
        public void setIvaimp3(Double ivaimp3) { this.ivaimp3 = ivaimp3; }

        public Double getIvabexenta() { return ivabexenta; }
        public void setIvabexenta(Double ivabexenta) { this.ivabexenta = ivabexenta; }

        public String getIvaciv1() { return ivaciv1; }
        public void setIvaciv1(String ivaciv1) { this.ivaciv1 = ivaciv1; }

        public String getIvaciv2() { return ivaciv2; }
        public void setIvaciv2(String ivaciv2) { this.ivaciv2 = ivaciv2; }
    }

    public static class Relacion {
        private String tipoRelacion;
        private Integer annoRelacion;
        private Integer ordenRelacion;
        
                public String getTipoRelacion() { return tipoRelacion; }
        public void setTipoRelacion(String tipoRelacion) { this.tipoRelacion = tipoRelacion; }

        public Integer getAnnoRelacion() { return annoRelacion; }
        public void setAnnoRelacion(Integer annoRelacion) { this.annoRelacion = annoRelacion; }

        public Integer getOrdenRelacion() { return ordenRelacion; }
        public void setOrdenRelacion(Integer ordenRelacion) { this.ordenRelacion = ordenRelacion; }
    }

    public static class Linea {
        private Integer nlinea;
        private Long opeasc;
        private Long lineasc;
        private String lincta;
        private Integer prya;
        private String pryt;
        private Integer pryo;
        private String pryn;
        private Integer pryx;
        private Integer lineje;
        private String linorg;
        private String linfun;
        private String lineco;
        private Long referencia;
        private Double limporte;
        private Double saldo;
        private Double saldop;
        private String lincte;
        private String linpam;
        
                public Integer getNlinea() { return nlinea; }
        public void setNlinea(Integer nlinea) { this.nlinea = nlinea; }

        public Long getOpeasc() { return opeasc; }
        public void setOpeasc(Long opeasc) { this.opeasc = opeasc; }

        public Long getLineasc() { return lineasc; }
        public void setLineasc(Long lineasc) { this.lineasc = lineasc; }

        public String getLincta() { return lincta; }
        public void setLincta(String lincta) { this.lincta = lincta; }

        public Integer getPrya() { return prya; }
        public void setPrya(Integer prya) { this.prya = prya; }

        public String getPryt() { return pryt; }
        public void setPryt(String pryt) { this.pryt = pryt; }

        public Integer getPryo() { return pryo; }
        public void setPryo(Integer pryo) { this.pryo = pryo; }

        public String getPryn() { return pryn; }
        public void setPryn(String pryn) { this.pryn = pryn; }

        public Integer getPryx() { return pryx; }
        public void setPryx(Integer pryx) { this.pryx = pryx; }

        public Integer getLineje() { return lineje; }
        public void setLineje(Integer lineje) { this.lineje = lineje; }

        public String getLinorg() { return linorg; }
        public void setLinorg(String linorg) { this.linorg = linorg; }

        public String getLinfun() { return linfun; }
        public void setLinfun(String linfun) { this.linfun = linfun; }

        public String getLineco() { return lineco; }
        public void setLineco(String lineco) { this.lineco = lineco; }

        public Long getReferencia() { return referencia; }
        public void setReferencia(Long referencia) { this.referencia = referencia; }

        public Double getLimporte() { return limporte; }
        public void setLimporte(Double limporte) { this.limporte = limporte; }

        public Double getSaldo() { return saldo; }
        public void setSaldo(Double saldo) { this.saldo = saldo; }

        public Double getSaldop() { return saldop; }
        public void setSaldop(Double saldop) { this.saldop = saldop; }

        public String getLincte() { return lincte; }
        public void setLincte(String lincte) { this.lincte = lincte; }

        public String getLinpam() { return linpam; }
        public void setLinpam(String linpam) { this.linpam = linpam; }
    }
}