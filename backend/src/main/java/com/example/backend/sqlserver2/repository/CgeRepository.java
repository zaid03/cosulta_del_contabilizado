package com.example.backend.sqlserver2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.sqlserver2.model.Cge;
import com.example.backend.sqlserver2.model.CgeId;

@Repository
public interface CgeRepository extends JpaRepository<Cge, CgeId> {
    //fetching description for services
    Optional<Cge> findFirstByENTAndEJEAndCGECOD(Integer ent, String eje, String cgecod);

    //selecting centro gestor for login
    List<Cge> findByENTAndEJEAndCGECODIn(Integer ent, String eje, List<String> cgecods);
}