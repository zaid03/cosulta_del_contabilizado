package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.FdeFacTerProjection;
import com.example.backend.sqlserver2.repository.FdeRepository;

import java.util.List;

@Service
public class ContabilizadoSearch {
    @Autowired
    private FdeRepository fdeRepository;

    public List<FdeFacTerProjection> searchContabilizado (
        Integer ent,
        String eje,
        String proveedor,
        String centroGestor,
        String economica,
        Integer ano
    ) {
        List<FdeFacTerProjection> facturas = fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje).stream().filter(f -> (f.getFDEIMP() != null && f.getFDEIMP() > 0) || (f.getFDEDIF() != null && f.getFDEDIF() > 0)).toList();

        if (facturas != null && !facturas.isEmpty()) {
            if (proveedor != null && !proveedor.isEmpty()) {
                if (isNumbersOnly(proveedor)) {
                    facturas = filterByProveedorAll(facturas, proveedor);
                } else if (isMixed(proveedor)) {
                    facturas = filterByProveedorNombre(facturas, proveedor);
                }
            }
            if (centroGestor != null && !centroGestor.isEmpty()) {
                facturas = filterByCentroGestor(facturas, centroGestor);
            }
            if (economica != null && !economica.isEmpty()) {
                facturas = filterByEconomica(facturas, economica);
            }
            if (ano != null) {
                facturas = filterByAno(facturas, ano);
            }
        }
        return facturas;
    }

    private boolean isNumbersOnly(String text) {return text.matches("^[0-9]+$");}
    private boolean isMixed(String text) {return !isNumbersOnly(text);}

    private List<FdeFacTerProjection> filterByProveedorAll (
        List<FdeFacTerProjection> facturas,
        String proveedor
    ) {
        return facturas.stream().filter(f -> {
            if (f.getFac() == null || f.getFac().getTer() == null) return false;
            return (f.getFac().getTer().getTERNIF() != null && f.getFac().getTer().getTERNIF().toLowerCase().equals(proveedor.toLowerCase())) ||
                (f.getFac().getTer().getTERNOM() != null && f.getFac().getTer().getTERNOM().toLowerCase().contains(proveedor.toLowerCase()));
        }).toList();
    }

    private List<FdeFacTerProjection> filterByProveedorNombre (
        List<FdeFacTerProjection> facturas,
        String proveedor
    ) {
        return facturas.stream().filter(f -> 
            f.getFac() != null && f.getFac().getTer() != null &&
            f.getFac().getTer().getTERNOM() != null && 
            f.getFac().getTer().getTERNOM().toLowerCase().contains(proveedor.toLowerCase())
        ).toList();
    }

    private List<FdeFacTerProjection> filterByCentroGestor (
        List<FdeFacTerProjection> facturas,
        String centroGestor
    ) {
        return facturas.stream().filter(f -> 
            f.getFac() != null &&
            f.getFac().getCGECOD() != null && 
            f.getFac().getCGECOD().toLowerCase().equals(centroGestor.toLowerCase())
        ).toList();
    }
    private List<FdeFacTerProjection> filterByEconomica (
        List<FdeFacTerProjection> facturas,
        String economica
    ) {
        return facturas.stream().filter(f -> 
            (f.getFDEECO() != null && f.getFDEECO().toLowerCase().equals(economica.toLowerCase()))
        ).toList();
    }
    private List<FdeFacTerProjection> filterByAno (
        List<FdeFacTerProjection> facturas,
        Integer ano
    ) {
        return facturas.stream().filter(f -> 
            f.getFac() != null &&
            f.getFac().getFACANN() != null && 
            f.getFac().getFACANN().equals(ano)
        ).toList();
    }
}