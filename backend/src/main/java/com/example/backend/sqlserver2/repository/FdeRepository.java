package com.example.backend.sqlserver2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.dto.FdeFacTerProjection;
import com.example.backend.sqlserver2.model.Fde;
import com.example.backend.sqlserver2.model.FdeId;

@Repository
public interface FdeRepository extends JpaRepository<Fde, FdeId> {
    //fetching applicaciones for a factura and contabilizar a factura
    List<Fde> findByENTAndEJEAndFACNUM(Integer ent, String eje, Integer facnum);

    //needed for quitar albaranes
    Optional<Fde> findByENTAndEJEAndFACNUMAndFDEECO(Integer ent, String eje, Integer facnum, String fdeeco);

    //selecting all facturas in consulta de del contabilizado
    List<FdeFacTerProjection> findByENTAndEJEAndFac_FACFCOIsNotNull(Integer ent, String eje);
}