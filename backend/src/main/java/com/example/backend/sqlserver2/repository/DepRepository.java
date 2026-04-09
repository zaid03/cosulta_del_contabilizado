package com.example.backend.sqlserver2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver2.model.Dep;
import com.example.backend.sqlserver2.model.DepId;

@Repository
public interface DepRepository  extends JpaRepository<Dep, DepId> {
    //for selecting centro gestor in login
    List<Dep> findByENTAndEJEAndDEPCODIn(Integer ent, String eje, List<String> depcods);
}