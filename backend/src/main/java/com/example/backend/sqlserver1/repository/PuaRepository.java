package com.example.backend.sqlserver1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver1.model.Pua;
import com.example.backend.sqlserver1.model.PuaId;

@Repository
public interface PuaRepository extends JpaRepository<Pua, PuaId> {

    //fetching entidades
    List<Pua> findByUSUCODAndAPLCOD(String USUCOD, Integer APLCOD);
}
