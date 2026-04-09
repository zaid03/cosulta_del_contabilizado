package com.example.backend.dto;

import java.util.List;

public class PersonaServiceRequest {
    private Integer ent;
    private String eje;
    private String percod;
    private List<String> services;

    public Integer getEnt() { return ent; }
    public void setEnt(Integer ent) { this.ent = ent; }

    public String getEje() { return eje; }
    public void setEje(String eje) { this.eje = eje; }

    public String getPercod() { return percod; }
    public void setPercod(String percod) { this.percod = percod; }

    public List<String> getServices() { return services; }
    public void setServices(List<String> services) { this.services = services; }
}