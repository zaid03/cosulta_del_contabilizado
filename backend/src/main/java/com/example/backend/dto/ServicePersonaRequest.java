package com.example.backend.dto;

import java.util.List;

public class ServicePersonaRequest {
    private Integer ent;
    private String eje;
    private String depcod;
    private List<String> personas;

    public Integer getEnt() {return ent;}
    public void setEnt(Integer ent) {this.ent = ent;}

    public String getEje() { return eje; }
    public void setEje(String eje) {this.eje = eje;}

    public String getDepcod() {return depcod;}
    public void setDepcod(String depcod) {this.depcod = depcod;}

    public List<String> getPersonas() {return personas;}
    public void setPersonas(List<String> personas) {this.personas = personas;}
}
