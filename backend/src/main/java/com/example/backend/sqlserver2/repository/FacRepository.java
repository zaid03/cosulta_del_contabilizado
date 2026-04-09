package com.example.backend.sqlserver2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.backend.sqlserver2.model.Fac;
import com.example.backend.sqlserver2.model.FacId;

@Repository
public interface FacRepository extends JpaRepository<Fac, FacId>, JpaSpecificationExecutor<Fac>{

}