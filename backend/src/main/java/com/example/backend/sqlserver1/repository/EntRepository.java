package com.example.backend.sqlserver1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver1.model.Ent;
import com.example.backend.dto.EntNomProjection;

@Repository
public interface EntRepository extends JpaRepository<Ent, Integer> {
    
    //fetching entidad name
    Optional<EntNomProjection> findProjectedByENTCOD(Integer ENTCOD);
}