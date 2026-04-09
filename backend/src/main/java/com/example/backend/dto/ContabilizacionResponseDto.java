package com.example.backend.dto;

public class ContabilizacionResponseDto {
    private String opeext;   
    private String opesical;    
    private String nap;         
    private String referencia;   
    private String importe;
    private String ejercicio;
    private String organica;
    private String funcional;
    private String economica;
    private boolean exito;
    private String mensaje;

    public String getOpeext() { return opeext; }
    public void setOpeext(String opeext) { this.opeext = opeext; }

    public String getOpesical() { return opesical; }
    public void setOpesical(String opesical) { this.opesical = opesical; }

    public String getNap() { return nap; }
    public void setNap(String nap) { this.nap = nap; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getImporte() { return importe; }
    public void setImporte(String importe) { this.importe = importe; }

    public String getEjercicio() { return ejercicio; }
    public void setEjercicio(String ejercicio) { this.ejercicio = ejercicio; }

    public String getOrganica() { return organica; }
    public void setOrganica(String organica) { this.organica = organica; }

    public String getFuncional() { return funcional; }
    public void setFuncional(String funcional) { this.funcional = funcional; }

    public String getEconomica() { return economica; }
    public void setEconomica(String economica) { this.economica = economica; }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}